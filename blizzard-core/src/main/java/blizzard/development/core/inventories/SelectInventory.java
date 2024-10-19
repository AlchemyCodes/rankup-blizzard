package blizzard.development.core.inventories;

import blizzard.development.core.Main;
import blizzard.development.core.builder.ItemBuilder;
import blizzard.development.core.clothing.adapters.CommonClothingAdapter;
import blizzard.development.core.clothing.adapters.LegendaryClothingAdapter;
import blizzard.development.core.clothing.adapters.MysticClothingAdapter;
import blizzard.development.core.clothing.adapters.RareClothingAdapter;
import blizzard.development.core.database.cache.PlayersCacheManager;
import blizzard.development.core.clothing.ClothingType;
import com.github.stefvanschie.inventoryframework.gui.GuiItem;
import com.github.stefvanschie.inventoryframework.gui.type.ChestGui;
import com.github.stefvanschie.inventoryframework.pane.StaticPane;
import com.github.stefvanschie.inventoryframework.pane.util.Slot;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;

import static blizzard.development.core.builder.ItemBuilder.hasPersistentData;

public class SelectInventory {

    public void open(Player player, ItemStack item) {
        ChestGui inventory = new ChestGui(3, "Confirma a ação?");
        StaticPane pane = new StaticPane(0, 0, 9, 3);

        GuiItem accept = new GuiItem(accept(), event -> {
            event.setCancelled(true);

        if (hasPersistentData(Main.getInstance(), item, "ativador.lendario")) {
            item.setAmount(item.getAmount() - 1);

            LegendaryClothingAdapter legendaryClothingAdapter = new LegendaryClothingAdapter();
            legendaryClothingAdapter.active(player);

            PlayersCacheManager.setPlayerClothing(player, ClothingType.LEGENDARY);

            player.closeInventory();
            player.sendTitle(
                    "§b§lWOW!",
                    "§bVocê ativou uma roupa da categoria lendária.",
                    10,
                    70,
                    20
            );
            return;
        }

        if (hasPersistentData(Main.getInstance(), item, "ativador.mistico")) {
            item.setAmount(item.getAmount() - 1);

            MysticClothingAdapter mysticClothingAdapter = new MysticClothingAdapter();
            mysticClothingAdapter.active(player);

            PlayersCacheManager.setPlayerClothing(player, ClothingType.MYSTIC);

            player.closeInventory();
            player.sendTitle(
                    "§5§lWOW!",
                    "§5Você ativou uma roupa da categoria mística.",
                    10,
                    70,
                    20
            );
            return;
        }

        if (hasPersistentData(Main.getInstance(), item, "ativador.raro")) {
            item.setAmount(item.getAmount() - 1);

            RareClothingAdapter rareClothingAdapter = new RareClothingAdapter();
            rareClothingAdapter.active(player);

            PlayersCacheManager.setPlayerClothing(player, ClothingType.RARE);

            player.closeInventory();
            player.sendTitle(
                    "§a§lWOW!",
                    "§aVocê ativou uma roupa da categoria rara.",
                    10,
                    70,
                    20
            );
        }

        if (hasPersistentData(Main.getInstance(), item, "ativador.comum")) {
            item.setAmount(item.getAmount() -1);

            CommonClothingAdapter commonClothingAdapter = new CommonClothingAdapter();
            commonClothingAdapter.active(player);

            PlayersCacheManager.setPlayerClothing(player, ClothingType.COMMON);

            player.closeInventory();
            player.sendTitle(
                    "§8§lWOW!",
                    "§8Você ativou uma roupa da categoria comum.",
                    10,
                    70,
                    20
            );
        }
        });

        GuiItem instruction = new GuiItem(instruction(player), event -> {
            event.setCancelled(true);
        });

        GuiItem cancel = new GuiItem(cancel(), event -> {
            event.setCancelled(true);

            player.closeInventory();
        });

        pane.addItem(accept, Slot.fromIndex(10));
        pane.addItem(instruction, Slot.fromIndex(13));
        pane.addItem(cancel, Slot.fromIndex(16));

        inventory.addPane(pane);
        inventory.show(player);
    }

    public ItemStack accept() {
        return new ItemBuilder(Material.LIME_DYE)
                .setDisplayName("§aConfirmar ação.")
                .setLore(Arrays.asList(
                        "§7Você ativará o seu",
                        "§7manto de proteção",
                        "",
                        "§aClique para confirmar."
                ))
                .build();
    }

    public ItemStack instruction(Player player) {
        return new ItemBuilder(Material.MINECART)
                .setDisplayName("§d§lATENÇÃO! §d" + player.getName() + ".")
                .setLore(Arrays.asList(
                        "§7Confirme com cautela,",
                        "§7a ação é irreversível."
                ))
                .build();
    }

    public ItemStack cancel() {
        return new ItemBuilder(Material.RED_DYE)
                .setDisplayName("§cCancelar ação.")
                .setLore(Arrays.asList(
                        "§7Você cancelará a",
                        "§7ativação do manto.",
                        "",
                        "§cClique para cancelar."
                ))
                .build();
    }
}
