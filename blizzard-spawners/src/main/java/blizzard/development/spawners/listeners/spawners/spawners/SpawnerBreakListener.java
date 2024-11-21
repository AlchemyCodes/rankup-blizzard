package blizzard.development.spawners.listeners.spawners.spawners;

import blizzard.development.spawners.builders.DisplayBuilder;
import blizzard.development.spawners.builders.EffectsBuilder;
import blizzard.development.spawners.database.cache.managers.SpawnersCacheManager;
import blizzard.development.spawners.database.dao.SpawnersDAO;
import blizzard.development.spawners.database.storage.SpawnersData;
import blizzard.development.spawners.handlers.mobs.CowMob;
import blizzard.development.spawners.handlers.mobs.PigMob;
import blizzard.development.spawners.utils.*;
import blizzard.development.spawners.utils.items.TextAPI;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import java.util.concurrent.TimeUnit;

public class SpawnerBreakListener implements Listener {

    private final SpawnersDAO spawnersDAO = new SpawnersDAO();
    private final SpawnersCacheManager cache = SpawnersCacheManager.getInstance();
    private final CooldownUtils cooldown = CooldownUtils.getInstance();

    @EventHandler
    public void onSpawnerBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        Block spawnerBlock = event.getBlock();
        String cooldownName = "blizzard.spawners.break-cooldown";

        if (spawnerBlock.getType().equals(Material.SPAWNER)) {

            event.setExpToDrop(0);
            String serializedLocation = LocationUtil.getSerializedLocation(spawnerBlock.getLocation());

            SpawnersData data = null;
            for (SpawnersData spawner : cache.spawnersCache.values()) {
                if (spawner.getLocation().equals(serializedLocation)) {
                    data = spawner;
                    break;
                }
            }

            if (!player.getGameMode().equals(GameMode.SURVIVAL) && !player.hasPermission("blizzard.spawners.admin")) {
                player.sendActionBar(TextAPI.parse("§c§lEI! §cVocê precisa estar no modo sobrevivência para quebrar."));
                event.setCancelled(true);
                return;
            }

            if (data == null) {
                if (!player.isOp() && !player.hasPermission("blizzard.spawners.admin")) {
                    player.sendActionBar(TextAPI.parse("§c§lEI! §cEste é um spawner sem dados."));
                    event.setCancelled(true);
                }
                return;
            }

            if (!player.getName().equals(data.getNickname()) && !player.hasPermission("blizzard.spawners.admin")) {
                player.sendActionBar(TextAPI.parse("§c§lEI! §cVocê não é dono desse spawner."));
                event.setCancelled(true);
                return;
            }

            if (cooldown.isInCountdown(player, cooldownName) && !player.hasPermission("blizzard.spawners.admin")) {
                player.sendActionBar(TextAPI.parse("§c§lEI! §cAguarde um pouco antes de quebrar outro spawner."));
                event.setCancelled(true);
                return;
            }

            if (player.getInventory().firstEmpty() == -1) {
                player.sendActionBar(TextAPI.parse("§c§lEI! §cVocê precisa de pelo menos um slot vazio."));
                event.setCancelled(true);
                return;
            }

            removeSpawner(player, data.getId(), data.getType(), data.getAmount());

            cooldown.createCountdown(player, cooldownName, 500, TimeUnit.MILLISECONDS);
        }
    }

    private void removeSpawner(Player player, String id, String type, Double amount) {
        try {
            SpawnersData spawnerData = cache.getSpawnerData(id);
            if (spawnerData != null) {
                Location location = LocationUtil.deserializeLocation(spawnerData.getLocation());
                DisplayBuilder.removeSpawnerDisplay(location);
                EffectsBuilder.removeSpawnerEffect(location);
            }

            spawnersDAO.deleteSpawnerData(id);
            cache.removeSpawnerData(id);

            switch (type.toLowerCase()) {
                case "pigs", "pig", "porcos", "porco" -> PigMob.give(player, amount, 1);
                case "cows", "cow", "vacas", "vaca" -> CowMob.give(player, amount, 1);
            }

            String formattedAmount = NumberFormat.getInstance().formatNumber(amount);
            player.sendActionBar(TextAPI.parse("§a§lYAY! §aVocê removeu §fx" + formattedAmount + " §aspawner(s) de " + type + "§a!"));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}