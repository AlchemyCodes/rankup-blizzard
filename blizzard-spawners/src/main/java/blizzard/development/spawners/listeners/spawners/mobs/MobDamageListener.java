package blizzard.development.spawners.listeners.spawners.mobs;

import blizzard.development.spawners.database.cache.managers.SpawnersCacheManager;
import blizzard.development.spawners.database.storage.SpawnersData;
import blizzard.development.spawners.handlers.enums.States;
import blizzard.development.spawners.utils.items.TextAPI;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class MobDamageListener implements Listener {
    private final SpawnersCacheManager manager = SpawnersCacheManager.getInstance();

    @EventHandler
    public void onMobDamage(EntityDamageByEntityEvent event) {
        Entity damager = event.getDamager();
        Entity mob = event.getEntity();

        if (!(damager instanceof Player player)) return;

        if (mob.hasMetadata("blizzard_spawners-mob") && mob.hasMetadata("blizzard_spawners-id")) {
            String spawnerId = mob.getMetadata("blizzard_spawners-id").get(0).asString();

            SpawnersData data = manager.getSpawnerData(spawnerId);

            if (data == null) {
                player.sendActionBar(TextAPI.parse(
                        "§c§lEI §cOcorreu um erro ao interagir com esse spawner.")
                );
                return;
            }

            if (data.getState().equals(States.PRIVATE.getState())
                    && !damager.getName().equals(data.getNickname())
                    && !damager.hasPermission("blizzard.spawners.admin")
            ) {
                player.sendActionBar(TextAPI.parse(
                        "§c§lEI! §cVocê não pode interagir com esse spawner.")
                );
                event.setCancelled(true);
            }
        }
    }
}
