package blizzard.development.plantations.listeners.plantation;

import blizzard.development.plantations.database.cache.methods.PlayerCacheMethod;
import blizzard.development.plantations.inventories.manager.AreaUpgradeInventory;
import blizzard.development.plantations.utils.PluginImpl;
import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.util.Vector;

public class PlantationDisplayListener implements Listener {

    private static final double knockback_distance = 1.5;
    private static final double knockback_strenght = 0.5;

    @EventHandler
    public void onPlayerInteractWithDisplay(PlayerInteractAtEntityEvent event) {
        if (event.getRightClicked() instanceof ArmorStand armorStand) {
            armorStand.getPersistentDataContainer();
            if (armorStand.getPersistentDataContainer().has(new NamespacedKey(PluginImpl.getInstance().plugin, "blizzard.plantations.estufa-display"), PersistentDataType.STRING)) {
                Player player = event.getPlayer();

                AreaUpgradeInventory areaUpgradeInventory = new AreaUpgradeInventory();
                areaUpgradeInventory.open(player);
            }
        }
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        Location location = player.getLocation();
        player.getWorld().getNearbyEntities(location, knockback_distance, knockback_strenght, knockback_distance).forEach(entity -> {
            if (entity instanceof ArmorStand armorStand) {
                if (armorStand.getPersistentDataContainer().has(
                    new NamespacedKey(PluginImpl.getInstance().plugin, "blizzard.plantations.estufa-display"),
                    PersistentDataType.STRING)) {
                    Vector direction = player.getLocation().toVector()
                        .subtract(armorStand.getLocation().toVector())
                        .normalize()
                        .multiply(knockback_strenght)
                        .setY(0.1);
                    player.setVelocity(direction);
                }
            }
        });
    }

    @EventHandler
    public void onDrop(PlayerDropItemEvent event) {
        Player player = event.getPlayer();

        if (PlayerCacheMethod.getInstance().isInPlantation(player)) {
            event.setCancelled(true);
        }
    }
}
