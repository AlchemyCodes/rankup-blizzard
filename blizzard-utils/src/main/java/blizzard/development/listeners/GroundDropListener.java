package blizzard.development.listeners;

import blizzard.development.Main;
import blizzard.development.providers.GroundDropProvider;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ItemMergeEvent;
import org.bukkit.event.entity.ItemSpawnEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.plugin.Plugin;

public class GroundDropListener implements Listener {
    private final GroundDropProvider provider;

    public GroundDropListener(Plugin plugin) {
        this.provider = new GroundDropProvider(plugin);
    }

    @EventHandler
    public void onItemSpawn(ItemSpawnEvent event) {
        EntityType entityType = event.getEntityType();
        if (entityType == EntityType.DROPPED_ITEM) {
            Item item = event.getEntity();
            event.setCancelled(!this.provider.provideGroundDrop(item));
        }
    }

    @EventHandler
    public void onItemMerge(ItemMergeEvent event) {
        Item item = event.getEntity();
        Item target = event.getTarget();
        if (this.provider.isDrop(target) && this.provider.isDrop(item)) {
            this.provider.merge(item, target);
            event.setCancelled(true);
        }
    }

    @EventHandler(
            ignoreCancelled = true,
            priority = EventPriority.HIGHEST
    )
    public void onItemPickup(PlayerPickupItemEvent event) {
        Item item = event.getItem();
        if (this.provider.isDrop(item)) {
            event.setCancelled(true);
            Player player = event.getPlayer();
            this.provider.provideDrop(player, item);
        }
    }

    public GroundDropListener(GroundDropProvider provider) {
        this.provider = provider;
    }
}