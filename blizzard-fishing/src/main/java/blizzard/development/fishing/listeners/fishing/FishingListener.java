package blizzard.development.fishing.listeners.fishing;

import blizzard.development.fishing.database.cache.FishingCacheManager;
import blizzard.development.fishing.utils.PluginImpl;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.projectiles.ProjectileSource;

public final class FishingListener implements Listener {

    @EventHandler
    public void onRodProjectileLaunch(ProjectileLaunchEvent event) {
        final EntityType entityType = event.getEntityType();
        if (entityType != EntityType.FISHING_HOOK) {
            return;
        }

        final ProjectileSource shooter = event.getEntity().getShooter();
        if (!(shooter instanceof Player player)) {
            return;
        }

        final PlayerInventory inventory = player.getInventory();
        final ItemStack itemInMainHand = inventory.getItemInMainHand();
        if (itemInMainHand.getType() != Material.FISHING_ROD) {
            return;
        }

        final Block targetBlock = player.getTargetBlock(null, 50);
        if (!targetBlock.isLiquid()) {
            return;
        }

        FishingCacheManager.addFisherman(player);
        YamlConfiguration config = PluginImpl.getInstance().Messages.getConfig();
        player.sendTitle(config.getString("pesca.comecaPescar.title"), config.getString("pesca.comecaPescar.sub-title"));
    }

    @EventHandler
    public void onRodInteract(PlayerInteractEvent event) {
        if (event.getHand() != EquipmentSlot.HAND) {
            return;
        }

        if (!event.hasItem()) {
            return;
        }

        final Action action = event.getAction();
        if (!action.name().contains("RIGHT")) {
            return;
        }

        final ItemStack item = event.getItem();
        if (item == null || item.getType() != Material.FISHING_ROD) {
            return;
        }

        final Player player = event.getPlayer();
        if (!FishingCacheManager.isFishing(player)) {
            return;
        }

        FishingCacheManager.removeFisherman(player.getUniqueId());
        YamlConfiguration config = PluginImpl.getInstance().Messages.getConfig();
        player.sendTitle(config.getString("pesca.pararPescar.title"), config.getString("pesca.pararPescar.sub-title"));
    }

    @EventHandler
    public void onItemHeld(PlayerItemHeldEvent event) {
        final Player player = event.getPlayer();
        if (!FishingCacheManager.isFishing(player)) {
            return;
        }

        FishingCacheManager.removeFisherman(player.getUniqueId());
    }
}
