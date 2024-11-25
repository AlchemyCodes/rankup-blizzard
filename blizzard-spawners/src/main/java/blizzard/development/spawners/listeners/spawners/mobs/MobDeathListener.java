package blizzard.development.spawners.listeners.spawners.mobs;

import blizzard.development.spawners.database.cache.managers.SpawnersCacheManager;
import blizzard.development.spawners.database.cache.setters.SpawnersCacheSetters;
import blizzard.development.spawners.database.storage.SpawnersData;
import blizzard.development.spawners.tasks.spawners.mobs.SpawnersMobsTaskManager;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;

public class MobDeathListener implements Listener {
    private final SpawnersCacheManager manager = SpawnersCacheManager.getInstance();
    private final SpawnersCacheSetters setters = SpawnersCacheSetters.getInstance();

    @EventHandler
    public void onMobDeath(EntityDeathEvent event) {
        if (event.getEntity().getKiller() == null) return;

        Player player = event.getEntity().getKiller();
        Entity mob = event.getEntity();

        if (mob.hasMetadata("blizzard_spawners-mob") && mob.hasMetadata("blizzard_spawners-id")) {
            event.getDrops().clear();

            String spawnerId = mob.getMetadata("blizzard_spawners-id").get(0).asString();

            SpawnersData data = manager.getSpawnerData(spawnerId);

            if (data != null) {
                addDrops(player, data, data.getMobAmount());
                resetMobsAmount(data, spawnerId);
            }
        }
    }

    public void resetMobsAmount(SpawnersData data, String id) {
        setters.setSpawnerMobAmout(data.getId(), 0.0);
        SpawnersMobsTaskManager.getInstance().syncMobAmount(id, 0.0);
    }

    public void addDrops(Player player, SpawnersData data, double amount) {
        ItemStack tool = player.getInventory().getItemInMainHand();
        int lootingLevel = tool.getEnchantments().getOrDefault(Enchantment.LOOT_BONUS_MOBS, 0);
        setters.addSpawnerDrops(data.getId(), (amount * (1 + lootingLevel)));
    }
}
