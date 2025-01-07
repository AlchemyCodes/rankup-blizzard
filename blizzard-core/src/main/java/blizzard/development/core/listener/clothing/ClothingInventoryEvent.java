package blizzard.development.core.listener.clothing;

import blizzard.development.core.Main;
import blizzard.development.core.builder.ItemBuilder;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

public class ClothingInventoryEvent implements Listener {
    @EventHandler
    public void onInteract(InventoryClickEvent event) {
        ItemStack itemStack = event.getCurrentItem();

        if (itemStack == null) return;

        String[] clothingsData = { "manto.couro", "manto.malha", "manto.ferro", "manto.diamante" };

        for (String clothing : clothingsData) {
            if (ItemBuilder.hasPersistentData(Main.getInstance(), itemStack, clothing)) {
                event.setCancelled(true);
                break;
            }
        }
    }
}
