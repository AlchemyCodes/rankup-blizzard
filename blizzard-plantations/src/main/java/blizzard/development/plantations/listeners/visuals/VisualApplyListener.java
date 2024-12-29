package blizzard.development.plantations.listeners.visuals;

import blizzard.development.plantations.Main;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import static blizzard.development.plantations.builder.ItemBuilder.hasPersistentData;

public class VisualApplyListener implements Listener {

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        ItemStack cursor = event.getCursor();
        ItemStack currentItem = event.getCurrentItem();

        if (cursor.getType() == Material.AIR) {
            return;
        }

        if (currentItem == null || currentItem.getType() == Material.AIR) {
            return;
        }

        player.sendMessage("§aCursor item: " + cursor.getType());
        player.sendMessage("§aCurrent item: " + currentItem.getType());


        if (!hasPersistentData(Main.getInstance(), cursor, "ferramenta")) {
            player.sendMessage("§cEste item não tem o dado persistente 'stone'!");
            return;
        }


        event.setCurrentItem(new ItemStack(Material.DIAMOND_HOE));
        event.getView().setCursor(null);
        event.setCancelled(true);

        player.sendMessage("O item foi transformado em um diamante! [teste swagviper]");
    }
}
