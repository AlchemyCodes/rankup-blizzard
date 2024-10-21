package blizzard.development.core.listener.clothing;

import blizzard.development.core.Main;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import static blizzard.development.core.builder.ItemBuilder.hasPersistentData;

public class ClothingInventoryEvent implements Listener {

    @EventHandler
    public void onInteract(InventoryClickEvent event) {
        ItemStack itemStack = event.getCurrentItem();

        if (itemStack == null) return;

        String[] clothingsData = {
                "manto.couro",
                "manto.malha",
                "manto.ferro",
                "manto.diamante"
        };

        for (String clothing : clothingsData) {
            if (hasPersistentData(Main.getInstance(), itemStack, clothing)) {
                event.setCancelled(true);
            }
        }
    }
}
