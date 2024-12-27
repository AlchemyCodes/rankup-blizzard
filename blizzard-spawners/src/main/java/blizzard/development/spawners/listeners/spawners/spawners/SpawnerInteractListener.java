package blizzard.development.spawners.listeners.spawners.spawners;

import blizzard.development.spawners.builders.ItemBuilder;
import blizzard.development.spawners.database.cache.getters.SpawnersCacheGetters;
import blizzard.development.spawners.database.cache.managers.SpawnersCacheManager;
import blizzard.development.spawners.database.storage.SpawnersData;
import blizzard.development.spawners.handlers.enums.spawners.States;
import blizzard.development.spawners.inventories.spawners.main.MainInventory;
import blizzard.development.spawners.utils.LocationUtil;
import blizzard.development.spawners.utils.PluginImpl;
import blizzard.development.spawners.utils.items.TextAPI;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

public class SpawnerInteractListener implements Listener {

    private final SpawnersCacheManager cache = SpawnersCacheManager.getInstance();

    @EventHandler
    public void onSpawnerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        Block spawnerBlock = event.getClickedBlock();

        if (spawnerBlock == null) return;

        if (event.getAction().isRightClick()) {
            if (spawnerBlock.getType().equals(Material.SPAWNER)) {

                String serializedLocation = LocationUtil.getSerializedLocation(spawnerBlock.getLocation());

                SpawnersData data = null;
                for (SpawnersData spawner : cache.spawnersCache.values()) {
                    if (spawner.getLocation().equals(serializedLocation)) {
                        data = spawner;
                        break;
                    }
                }

                if (!LocationUtil.interactVerify(player, spawnerBlock)) {
                    event.setCancelled(true);
                    return;
                }

                if (data == null) {
                    player.sendActionBar(TextAPI.parse("§c§lEI! §cEste é um spawner sem dados."));
                    event.setCancelled(true);
                    return;
                }

                if (ItemBuilder.hasPersistentData(PluginImpl.getInstance().plugin, player.getInventory().getItemInMainHand(), "blizzard.spawners-friendslimit"
                ) || ItemBuilder.hasPersistentData(PluginImpl.getInstance().plugin, player.getInventory().getItemInMainHand(), "blizzard.spawners-autosell")) {
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

                if (data.getState().equals(States.PRIVATE.getState())
                        && !player.getName().equals(data.getNickname())
                        && !player.hasPermission("blizzard.spawners.admin")
                        && !SpawnersCacheGetters.getInstance().getSpawnerFriends(data.getId()).contains(player.getName())
                ) {
                    player.sendActionBar(TextAPI.parse(
                            "§c§lEI! §cVocê não pode interagir com esse spawner.")
                    );
                    event.setCancelled(true);
                    return;
                }

                MainInventory.getInstance().open(player, data.getId());
            }
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
