package blizzard.development.monsters.inventories.tools;

import blizzard.development.monsters.database.storage.ToolsData;
import blizzard.development.monsters.inventories.tools.items.SwordItems;
import com.github.stefvanschie.inventoryframework.gui.type.ChestGui;
import com.github.stefvanschie.inventoryframework.pane.StaticPane;
import org.bukkit.entity.Player;

public class SwordInventory {
    private static SwordInventory instance;

    private final SwordItems items = SwordItems.getInstance();

    public void open(Player player, ToolsData toolsData) {
        //ChestGui inventory = new ChestGui(3, "§8Melhorias");
        ChestGui inventory = new ChestGui(3, toolsData.getId());


        StaticPane pane = new StaticPane(0, 0, 9, 3);

        inventory.addPane(pane);
        inventory.show(player);
    }

    public static SwordInventory getInstance() {
        if (instance == null) instance = new SwordInventory();
        return instance;
    }
}
