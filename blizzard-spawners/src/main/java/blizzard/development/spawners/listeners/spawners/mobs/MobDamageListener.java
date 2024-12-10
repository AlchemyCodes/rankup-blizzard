package blizzard.development.spawners.listeners.spawners.mobs;

import blizzard.development.spawners.database.cache.getters.SpawnersCacheGetters;
import blizzard.development.spawners.database.cache.managers.SpawnersCacheManager;
import blizzard.development.spawners.database.storage.SpawnersData;
import blizzard.development.spawners.handlers.enums.States;
import blizzard.development.spawners.utils.PluginImpl;
import blizzard.development.spawners.utils.items.TextAPI;
import org.bukkit.configuration.file.FileConfiguration;
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
                    && !damager.getName().equals(data.getNickname())
                    && !damager.hasPermission("blizzard.spawners.admin")
                    && !SpawnersCacheGetters.getInstance().getSpawnerFriends(data.getId()).contains(player.getName())
            ) {
                player.sendActionBar(TextAPI.parse(
                        "§c§lEI! §cVocê não pode interagir com esse spawner.")
                );
                event.setCancelled(true);
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
