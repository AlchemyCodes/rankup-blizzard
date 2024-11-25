package blizzard.development.spawners.listeners.spawners.mobs;

import blizzard.development.spawners.database.cache.getters.SpawnersCacheGetters;
import blizzard.development.spawners.database.cache.managers.SpawnersCacheManager;
import blizzard.development.spawners.database.cache.setters.SpawnersCacheSetters;
import blizzard.development.spawners.database.storage.SpawnersData;
import blizzard.development.spawners.tasks.spawners.mobs.SpawnersMobsTaskManager;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;

public class MobDeathListener implements Listener {

    private final SpawnersCacheManager manager = SpawnersCacheManager.getInstance();
    private final SpawnersCacheSetters setters = SpawnersCacheSetters.getInstance();
    private final SpawnersCacheGetters getters = SpawnersCacheGetters.getInstance();

    @EventHandler
    public void onMobDeath(EntityDeathEvent event) {
        if (event.getEntity().getKiller() == null) return;

        Player player = event.getEntity().getKiller();
        Entity mob = event.getEntity();

        if (mob.hasMetadata("blizzard_spawners-mob") && mob.hasMetadata("blizzard_spawners-id")) {
            event.getDrops().clear();

            String mobType = mob.getMetadata("blizzard_spawners-mob").get(0).asString();
            String spawnerId = mob.getMetadata("blizzard_spawners-id").get(0).asString();

            SpawnersData data = manager.getSpawnerData(spawnerId);

            if (data != null) {
                setters.setSpawnerMobAmout(data.getId(), 0.0);
                SpawnersMobsTaskManager.getInstance().syncMobAmount(spawnerId, 0.0);
                player.getInventory().addItem(new ItemStack(Material.DIAMOND));
                player.sendMessage("Você matou um " + mobType + " e fodase.");
            }
        }
    }
}
