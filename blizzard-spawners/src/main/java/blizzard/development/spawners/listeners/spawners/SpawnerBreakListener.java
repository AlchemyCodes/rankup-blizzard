package blizzard.development.spawners.listeners.spawners;

import blizzard.development.spawners.database.cache.SpawnersCacheManager;
import blizzard.development.spawners.database.dao.SpawnersDAO;
import blizzard.development.spawners.database.storage.SpawnersData;
import blizzard.development.spawners.handlers.spawners.mobs.PigMob;
import blizzard.development.spawners.utils.LocationUtil;
import blizzard.development.spawners.utils.NumberFormat;
import blizzard.development.spawners.utils.items.apis.TextAPI;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class SpawnerBreakListener implements Listener {

    private final SpawnersDAO spawnersDAO = new SpawnersDAO();
    private final SpawnersCacheManager cache = SpawnersCacheManager.getInstance();

    @EventHandler
    public void onSpawnerBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        Block spawnerBlock = event.getBlock();

        if (spawnerBlock.getType().equals(Material.SPAWNER)) {
            String serializedLocation = LocationUtil.getSerializedLocation(spawnerBlock.getLocation());

            SpawnersData data = null;
            for (SpawnersData spawner : cache.spawnersCache.values()) {
                if (spawner.getLocation().equals(serializedLocation)) {
                    data = spawner;
                    break;
                }
            }

            if (data == null) {
                if (!player.isOp() && !player.hasPermission("blizzard.spawners.admin")) {
                    player.sendActionBar(TextAPI.parse("§c§lEI! §cEste é um spawner sem dados."));
                    event.setCancelled(true);
                }
                return;
            }

            removeSpawner(player, data.getId(), data.getType(), data.getAmount());
        }
    }

    // botar verificação de slot vazio / formatar os double / botar op direto com a db / n esquecer dos evento de clear e etc de plot

    private void removeSpawner(Player player, String id, String type, Double amount) {
        try {
            spawnersDAO.deleteSpawnerData(id);
            cache.removeSpawnerData(id);
            switch (type.toLowerCase()) {
                case "pigs", "pig", "porcos", "porco" -> PigMob.getInstance().give(player, amount, 1);
            }
            String formattedAmount = NumberFormat.getInstance().formatNumber(amount);
            player.sendActionBar(TextAPI.parse("§a§lYAY! §aVocê removeu §f x" + formattedAmount + " §aspawner(s) de " + type + "§a!"));
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }
}
