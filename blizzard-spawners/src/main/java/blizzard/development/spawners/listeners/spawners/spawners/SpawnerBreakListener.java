package blizzard.development.spawners.listeners.spawners.spawners;

import blizzard.development.spawners.builders.DisplayBuilder;
import blizzard.development.spawners.builders.EffectsBuilder;
import blizzard.development.spawners.database.cache.getters.PlayersCacheGetters;
import blizzard.development.spawners.database.cache.managers.SpawnersCacheManager;
import blizzard.development.spawners.database.cache.setters.PlayersCacheSetters;
import blizzard.development.spawners.database.dao.SpawnersDAO;
import blizzard.development.spawners.database.storage.SpawnersData;
import blizzard.development.spawners.handlers.enums.Spawners;
import blizzard.development.spawners.handlers.mobs.MobsHandler;
import blizzard.development.spawners.managers.SpawnerAccessManager;
import blizzard.development.spawners.tasks.spawners.mobs.SpawnersMobsTaskManager;
import blizzard.development.spawners.utils.*;
import blizzard.development.spawners.utils.items.TextAPI;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import java.util.List;
import java.util.Set;
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

            Material itemInHand = player.getInventory().getItemInMainHand().getType();
            if (player.getGameMode().equals(GameMode.CREATIVE) && itemInHand != Material.AIR && !SpawnersUtils.getInstance().isPickaxe(itemInHand)) {
                player.sendActionBar(TextAPI.parse("§c§lEI! §cVocê só pode quebrar spawners com a mão ou uma picareta."));
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

            FileConfiguration config = PluginImpl.getInstance().Spawners.getConfig();
            boolean released = config.getBoolean("spawners." + getSpawnerType(data.getType()) + ".permitted-purchase", false);

            if (!released && !player.hasPermission("blizzard.spawners.admin")) {
                player.sendActionBar(TextAPI.parse(
                        "§c§lEI! §cEste spawner não está liberado.")
                );
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
            SpawnersMobsTaskManager.getInstance().stopTask(data.getId());

            cooldown.createCountdown(player, cooldownName, 1000, TimeUnit.MILLISECONDS);
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

            switch (type.toLowerCase()) {
                case "pigs", "pig", "porcos", "porco" -> MobsHandler.giveMobSpawner(player, Spawners.PIG, amount, 1, spawnerData.getSpeedLevel(), spawnerData.getLuckyLevel(), spawnerData.getExperienceLevel());
                case "cows", "cow", "vacas", "vaca" -> MobsHandler.giveMobSpawner(player, Spawners.COW, amount, 1, spawnerData.getSpeedLevel(), spawnerData.getLuckyLevel(), spawnerData.getExperienceLevel());
                case "mooshrooms", "mooshroom", "coguvacas", "coguvaca" -> MobsHandler.giveMobSpawner(player, Spawners.MOOSHROOM, amount, 1, spawnerData.getSpeedLevel(), spawnerData.getLuckyLevel(), spawnerData.getExperienceLevel());
                case "sheeps", "sheep", "ovelhas", "ovelha" -> MobsHandler.giveMobSpawner(player, Spawners.SHEEP, amount, 1, spawnerData.getSpeedLevel(), spawnerData.getLuckyLevel(), spawnerData.getExperienceLevel());
                case "zombies", "zombie", "zumbis", "zumbi" -> MobsHandler.giveMobSpawner(player, Spawners.ZOMBIE, amount, 1, spawnerData.getSpeedLevel(), spawnerData.getLuckyLevel(), spawnerData.getExperienceLevel());
            }

            final SpawnerAccessManager accessManager = SpawnerAccessManager.getInstance();

            List<String> inventoryUsers = accessManager.getInventoryUsers(id);
            if (inventoryUsers != null) {
                for (String userId : inventoryUsers) {
                    Player openUser = Bukkit.getPlayer(userId);
                    if (openUser != null) {
                        Bukkit.getScheduler().runTask(PluginImpl.getInstance().plugin, () -> openUser.getOpenInventory().close());
                    }
                    accessManager.removeInventoryUser(id, userId);
                }
            }

            spawnersDAO.deleteSpawnerData(id);
            cache.removeSpawnerData(id);

            String formattedAmount = NumberFormat.getInstance().formatNumber(amount);
            player.sendActionBar(TextAPI.parse("§a§lYAY! §aVocê removeu §fx" + formattedAmount + " §aspawner(s) de " + type + "§a!"));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getSpawnerType(String spawner) {
        return switch (spawner) {
            case "PIG", "pig", "PORCO", "porco" -> "pig";
            case "COW", "cow", "VACA", "vaca" -> "cow";
            case "MOOSHROOM", "mooshroom", "Coguvaca", "coguvaca" -> "mooshroom";
            case "SHEEP", "sheep", "OVELHA", "ovelha" -> "sheep";
            case "ZOMBIE", "zombie", "ZUMBI", "zumbi" -> "zombie";
            default -> null;
        };
    }
}