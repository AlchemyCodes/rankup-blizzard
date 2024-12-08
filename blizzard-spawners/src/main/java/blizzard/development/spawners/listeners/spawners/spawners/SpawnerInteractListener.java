package blizzard.development.spawners.listeners.spawners.spawners;

import blizzard.development.spawners.database.cache.getters.SpawnersCacheGetters;
import blizzard.development.spawners.database.cache.managers.SpawnersCacheManager;
import blizzard.development.spawners.database.storage.SpawnersData;
import blizzard.development.spawners.handlers.enums.States;
import blizzard.development.spawners.inventories.spawners.SpawnersInventory;
import blizzard.development.spawners.utils.LocationUtil;
import blizzard.development.spawners.utils.items.TextAPI;
import org.bukkit.Material;
import org.bukkit.block.Block;
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

                SpawnersInventory.getInstance().open(player, data.getId());
            }
        }
    }
}
