package blizzard.development.bosses.listeners.bosses;

import blizzard.development.bosses.utils.PluginImpl;
import blizzard.development.bosses.utils.items.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.inventory.ItemStack;

public class BossesAreaListener implements Listener {
    private Location pos1;
    private Location pos2;

    @EventHandler
    public void onAreaInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        ItemStack item = event.getItem();

        if (item == null) return;

        if (event.getAction() == Action.LEFT_CLICK_BLOCK || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            Location location = event.getClickedBlock() != null ? event.getClickedBlock().getLocation() : null;

            if (location == null) return;

            if (ItemBuilder.hasPersistentData(PluginImpl.getInstance().plugin, item, "blizzard.bosses.tools-area")) {
                if (event.getAction() == Action.LEFT_CLICK_BLOCK) {
                    setPos1(player, location);
                    event.setCancelled(true);
                } else if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
                    setPos2(player, location);
                    event.setCancelled(true);
                }
            }
        }
    }


    public void setPos1(Player player, Location location) {
        this.pos1 = location;
        player.sendMessage("§a§lYAY! §aPosição 1 definida para: §7" + formatLocation(location));
        checkAndSaveArea(player);
    }

    public void setPos2(Player player, Location location) {
        this.pos2 = location;
        player.sendMessage("§a§lYAY! §aPosição 2 definida para: §7" + formatLocation(location));
        checkAndSaveArea(player);
    }

    private void checkAndSaveArea(Player player) {
        if (pos1 != null && pos2 != null) {
            saveArea(player);
        }
    }

    public void saveArea(Player player) {
        PluginImpl.getInstance().Locations.getConfig().set("boss-area.pos1.world", pos1.getWorld().getName());
        PluginImpl.getInstance().Locations.getConfig().set("boss-area.pos1.x", pos1.getX());
        PluginImpl.getInstance().Locations.getConfig().set("boss-area.pos1.y", pos1.getY());
        PluginImpl.getInstance().Locations.getConfig().set("boss-area.pos1.z", pos1.getZ());

        PluginImpl.getInstance().Locations.getConfig().set("boss-area.pos2.world", pos2.getWorld().getName());
        PluginImpl.getInstance().Locations.getConfig().set("boss-area.pos2.x", pos2.getX());
        PluginImpl.getInstance().Locations.getConfig().set("boss-area.pos2.y", pos2.getY());
        PluginImpl.getInstance().Locations.getConfig().set("boss-area.pos2.z", pos2.getZ());

        PluginImpl.getInstance().Locations.saveConfig();
        player.sendActionBar("§a§lYAY! §aÁrea do boss salva com sucesso!");
    }

    public Location[] getArea() {
        if (PluginImpl.getInstance().Locations.getConfig().contains("boss-area")) {
            World world1 = Bukkit.getWorld(PluginImpl.getInstance().Locations.getConfig().getString("boss-area.pos1.world"));
            Location pos1 = new Location(
                    world1,
                    PluginImpl.getInstance().Locations.getConfig().getDouble("boss-area.pos1.x"),
                    PluginImpl.getInstance().Locations.getConfig().getDouble("boss-area.pos1.y"),
                    PluginImpl.getInstance().Locations.getConfig().getDouble("boss-area.pos1.z")
            );
            World world2 = Bukkit.getWorld(PluginImpl.getInstance().Locations.getConfig().getString("boss-area.pos2.world"));
            Location pos2 = new Location(
                    world2,
                    PluginImpl.getInstance().Locations.getConfig().getDouble("boss-area.pos2.x"),
                    PluginImpl.getInstance().Locations.getConfig().getDouble("boss-area.pos2.y"),
                    PluginImpl.getInstance().Locations.getConfig().getDouble("boss-area.pos2.z")
            );

            return new Location[]{pos1, pos2};
        }
        return null;
    }

    private String formatLocation(Location location) {
        return String.format("(%s, %.2f, %.2f, %.2f)", location.getWorld().getName(), location.getX(), location.getY(), location.getZ());
    }
}
