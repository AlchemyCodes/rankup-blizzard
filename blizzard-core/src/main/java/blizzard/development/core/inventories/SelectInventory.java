package blizzard.development.core.inventories;

import blizzard.development.core.Main;
import blizzard.development.core.builder.ItemBuilder;
import blizzard.development.core.clothing.ClothingType;
import blizzard.development.core.clothing.adapters.CommonClothingAdapter;
import blizzard.development.core.clothing.adapters.LegendaryClothingAdapter;
import blizzard.development.core.clothing.adapters.MysticClothingAdapter;
import blizzard.development.core.clothing.adapters.RareClothingAdapter;
import blizzard.development.core.database.cache.PlayersCacheManager;
import blizzard.development.core.tasks.TemperatureDecayTask;
import com.github.stefvanschie.inventoryframework.gui.GuiItem;
import com.github.stefvanschie.inventoryframework.gui.type.ChestGui;
import com.github.stefvanschie.inventoryframework.pane.StaticPane;
import com.github.stefvanschie.inventoryframework.pane.util.Slot;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import java.util.Arrays;

public class SelectInventory {
    public void open(Player player, ItemStack item) {
        ChestGui inventory = new ChestGui(3, "Confirma a ação?");
        StaticPane pane = new StaticPane(0, 0, 9, 3);

        GuiItem accept = new GuiItem(accept(), event -> {
            event.setCancelled(true);

            if (ItemBuilder.hasPersistentData((Plugin) Main.getInstance(), item, "ativador.lendario")) {
                activateClothing(player, item, new LegendaryClothingAdapter(), ClothingType.LEGENDARY, "lendária");
                return;
            }

            if (ItemBuilder.hasPersistentData((Plugin) Main.getInstance(), item, "ativador.mistico")) {
                activateClothing(player, item, new MysticClothingAdapter(), ClothingType.MYSTIC, "mística");
                return;
            }

            if (ItemBuilder.hasPersistentData((Plugin) Main.getInstance(), item, "ativador.raro")) {
                activateClothing(player, item, new RareClothingAdapter(), ClothingType.RARE, "rara");
                return;
            }

            if (ItemBuilder.hasPersistentData((Plugin) Main.getInstance(), item, "ativador.comum")) {
                activateClothing(player, item, new CommonClothingAdapter(), ClothingType.COMMON, "comum");
            }
        });

        GuiItem instruction = new GuiItem(instruction(player), event -> event.setCancelled(true));

        GuiItem cancel = new GuiItem(cancel(), event -> {
            event.setCancelled(true);
            player.closeInventory();
        });


        pane.addItem(accept, Slot.fromIndex(10));
        pane.addItem(instruction, Slot.fromIndex(13));
        pane.addItem(cancel, Slot.fromIndex(16));

        inventory.addPane(pane);
        inventory.show((HumanEntity) player);
    }


    private void activateClothing(Player player, ItemStack item, Object adapter, ClothingType clothingType, String clothingName) {
        item.setAmount(item.getAmount() - 1);

        if (adapter instanceof LegendaryClothingAdapter) {
            ((LegendaryClothingAdapter) adapter).active(player);
        } else if (adapter instanceof MysticClothingAdapter) {
            ((MysticClothingAdapter) adapter).active(player);
        } else if (adapter instanceof RareClothingAdapter) {
            ((RareClothingAdapter) adapter).active(player);
        } else if (adapter instanceof CommonClothingAdapter) {
            ((CommonClothingAdapter) adapter).active(player);
        }

        PlayersCacheManager.setPlayerClothing(player, clothingType);
        player.closeInventory();
        player.sendTitle("§b§lWOW!", "§bVocê ativou uma roupa da categoria " + clothingName + ".", 10, 70, 20);

        TemperatureDecayTask.stopPlayerRunnable(player);
        TemperatureDecayTask.startPlayerRunnable(player);
    }

    public ItemStack accept() {
        return (new ItemBuilder(Material.LIME_DYE))
                .setDisplayName("§aConfirmar ação.")
                .setLore(Arrays.asList("§7Você ativará o seu", "§7manto de proteção", "", "§aClique para confirmar."))
                .build();
    }

    public ItemStack instruction(Player player) {
        return (new ItemBuilder(Material.MINECART))
                .setDisplayName("§d§lATENÇÃO! §d" + player.getName() + ".")
                .setLore(Arrays.asList("§7Confirme com cautela,", "§7a ação é irreversível."))
                .build();
    }

    public ItemStack cancel() {
        return (new ItemBuilder(Material.RED_DYE))
                .setDisplayName("§cCancelar ação.")
                .setLore(Arrays.asList("§7Você cancelará a", "§7ativação do manto.", "", "§cClique para cancelar."))
                .build();
    }
}
