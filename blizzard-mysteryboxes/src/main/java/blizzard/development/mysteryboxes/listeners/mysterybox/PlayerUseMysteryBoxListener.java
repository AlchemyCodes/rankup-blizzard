package blizzard.development.mysteryboxes.listeners.mysterybox;

import blizzard.development.mysteryboxes.inventories.MysteryBoxInventory;
import blizzard.development.mysteryboxes.mysteryboxes.events.PlayerUseMysteryBoxEvent;
import blizzard.development.mysteryboxes.mysteryboxes.item.MysteryBoxBuildItem;
import blizzard.development.mysteryboxes.utils.PluginImpl;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import static blizzard.development.mysteryboxes.builder.ItemBuilder.hasPersistentData;

public class PlayerUseMysteryBoxListener implements Listener {

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        Block block = event.getClickedBlock();
        Action action = event.getAction();

        PlayerUseMysteryBoxEvent mysteryBox = new PlayerUseMysteryBoxEvent(player, block, action);
        mysteryBox.callEvent();

        if (mysteryBox.isCancelled()) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onUseMysteryBox(PlayerUseMysteryBoxEvent event) {
        Player player = event.getPlayer();
        Block block = event.getBlock();
        Action action = event.getAction();

        ItemStack item = player.getInventory().getItemInMainHand();

        if (!(hasPersistentData(PluginImpl.getInstance().plugin, item, "caixamisteriosa-rara") ||
            hasPersistentData(PluginImpl.getInstance().plugin, item, "caixamisteriosa-lendaria") ||
            hasPersistentData(PluginImpl.getInstance().plugin, item, "caixamisteriosa-blizzard"))) {
            return;
        }

        if (!(action == Action.RIGHT_CLICK_BLOCK)) {
            player.sendActionBar("§c§lEI! §cClique no chão para poder abrir esta caixa.");
            event.setCancelled(true);
            return;
        }

        if (hasPersistentData(PluginImpl.getInstance().plugin, item, "caixamisteriosa-rara")) {
            MysteryBoxInventory mysteryBoxInventory = new MysteryBoxInventory();
            mysteryBoxInventory.open(player, block.getLocation(), item);

            event.setCancelled(true);
        }

        if (hasPersistentData(PluginImpl.getInstance().plugin, item, "caixamisteriosa-lendaria")) {
            MysteryBoxInventory mysteryBoxInventory = new MysteryBoxInventory();
            mysteryBoxInventory.open(player, block.getLocation(), item);

            event.setCancelled(true);
        }

        if (hasPersistentData(PluginImpl.getInstance().plugin, item, "caixamisteriosa-blizzard")) {
            MysteryBoxInventory mysteryBoxInventory = new MysteryBoxInventory();
            mysteryBoxInventory.open(player, block.getLocation(), item);

            event.setCancelled(true);
        }
    }

}
