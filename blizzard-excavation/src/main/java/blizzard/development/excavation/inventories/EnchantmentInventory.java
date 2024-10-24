package blizzard.development.excavation.inventories;

import blizzard.development.excavation.builder.ItemBuilder;
import blizzard.development.excavation.database.cache.methods.ExcavatorCacheMethod;
import com.github.stefvanschie.inventoryframework.gui.GuiItem;
import com.github.stefvanschie.inventoryframework.gui.type.ChestGui;
import com.github.stefvanschie.inventoryframework.pane.StaticPane;
import com.github.stefvanschie.inventoryframework.pane.util.Slot;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;

public class EnchantmentInventory {

    public void open(Player player, ExcavatorCacheMethod excavatorCacheMethod) {
        ChestGui inventory = new ChestGui(3, "§8Encantamentos");
        StaticPane pane = new StaticPane(0, 0, 9, 3);


        GuiItem efficiency = new GuiItem(efficiency(excavatorCacheMethod, player), event -> {
            event.setCancelled(true);
        });

        GuiItem durability = new GuiItem(durability(), event -> {
            event.setCancelled(true);
        });

        GuiItem agility = new GuiItem(agility(excavatorCacheMethod, player), event -> {
            event.setCancelled(true);
        });

        GuiItem extractor = new GuiItem(extractor(excavatorCacheMethod, player), event -> {
            event.setCancelled(true);
        });

        pane.addItem(efficiency, Slot.fromIndex(10));
        pane.addItem(durability, Slot.fromIndex(12));
        pane.addItem(agility, Slot.fromIndex(14));
        pane.addItem(extractor, Slot.fromIndex(16));

        inventory.addPane(pane);
        inventory.show(player);
    }

    private ItemStack efficiency(ExcavatorCacheMethod excavatorCacheMethod, Player player) {
        int efficiency = excavatorCacheMethod.effiencyEnchant(player.getName());

        return new ItemBuilder(Material.GOLDEN_PICKAXE)
                .setDisplayName("§6Eficiência §l" + efficiency)
                .setLore(Arrays.asList(
                        "§7Permite à sua escavadora",
                        "§7quebrar blocos mais rápido.",
                        "",
                        " §fNível: §6" + efficiency + "/2,000",
                        " §fChance: §62§l%",
                        "",
                        " §6Este encantamento é aprimorado",
                        " §6de forma automática",
                        ""
                ))
                .build();
    }

    private ItemStack durability() {
        return new ItemBuilder(Material.OBSIDIAN)
                .setDisplayName("§8Durabilidade §l∞")
                .setLore(Arrays.asList(
                        "§7Aumenta a resistência da",
                        "§7escavadora, evitando que quebre.",
                        "",
                        " §8O encantamento já está",
                        " §8no nível máximo.",
                        ""
                ))
                .build();
    }

    private ItemStack agility(ExcavatorCacheMethod excavatorCacheMethod, Player player) {
        int agility = excavatorCacheMethod.agilityEnchant(player.getName());

        return new ItemBuilder(Material.POTION)
                .setDisplayName("§dAgilidade §l" + agility)
                .setLore(Arrays.asList(
                        "§7Ganhe efeitos de velocidade",
                        "§7na área da escavação.",
                        "",
                        " §fNível: §d" + agility + "/2",
                        " §fChance: §d25§l%",
                        "",
                        " §dEste encantamento é aprimorado",
                        " §dde forma automática",
                        ""
                ))
                .addItemFlags(ItemFlag.HIDE_POTION_EFFECTS)
                .build();
    }

    private ItemStack extractor(ExcavatorCacheMethod excavatorCacheMethod, Player player) {
        int extractor = excavatorCacheMethod.extractorEnchant(player.getName());

        return new ItemBuilder(Material.HOPPER)
                .setDisplayName("§4Extrator §l" + extractor)
                .setLore(Arrays.asList(
                        "§7Extrai blocos em um buraco",
                        "§7cônico durante a escavação.",
                        "",
                        " §fNível: §4" + extractor + "/1,000",
                        " §fChance: §40,0002§l%",
                        "",
                        " §4Este encantamento é aprimorado",
                        " §4de forma automática",
                        ""
                ))
                .build();
    }


}
