package blizzard.development.tops.inventories;

import blizzard.development.currencies.enums.Currencies;
import blizzard.development.tops.builders.ItemBuilder;
import com.github.stefvanschie.inventoryframework.gui.GuiItem;
import com.github.stefvanschie.inventoryframework.gui.type.ChestGui;
import com.github.stefvanschie.inventoryframework.pane.StaticPane;
import com.github.stefvanschie.inventoryframework.pane.util.Slot;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;

public class TopsInventory {
    private static TopsInventory instance;

    public void open(Player player) {
        ChestGui inventory = new ChestGui(4, "§8Destaques");

        StaticPane pane = new StaticPane(0, 0, 9, 4);

        GuiItem coinsItem = new GuiItem(coins(), event -> {
            // CurrenciesInventory.getInstance().open(player, "Almas");
            player.getInventory().close();
            event.setCancelled(true);
        });

        GuiItem mineItem = new GuiItem(mine(), event -> {
            // CurrenciesInventory.getInstance().open(player, "Almas");
            player.getInventory().close();
            event.setCancelled(true);
        });

        GuiItem flakesItem = new GuiItem(flakes(), event -> {
            CurrenciesInventory.getInstance().open(player, Currencies.FLAKES.getName());
            event.setCancelled(true);
        });

        GuiItem spawnersItem = new GuiItem(spawners(), event -> {
            // CurrenciesInventory.getInstance().open(player, "Almas");
            player.getInventory().close();
            event.setCancelled(true);
        });

        GuiItem seedsItem = new GuiItem(seeds(), event -> {
            // CurrenciesInventory.getInstance().open(player, "Almas");
            player.getInventory().close();
            event.setCancelled(true);
        });

        GuiItem fossilsItem = new GuiItem(fossils(), event -> {
            CurrenciesInventory.getInstance().open(player, Currencies.FOSSILS.getName());
            event.setCancelled(true);
        });

        GuiItem fishsItem = new GuiItem(fishs(), event -> {
            // CurrenciesInventory.getInstance().open(player, "Almas");
            player.getInventory().close();
            event.setCancelled(true);
        });

        GuiItem machinesItem = new GuiItem(machines(), event -> {
            // CurrenciesInventory.getInstance().open(player, "Almas");
            player.getInventory().close();
            event.setCancelled(true);
        });

        GuiItem timeItem = new GuiItem(time(), event -> {
            // CurrenciesInventory.getInstance().open(player, "Almas");
            player.getInventory().close();
            event.setCancelled(true);
        });

        GuiItem cashItem = new GuiItem(cash(), event -> {
            // CurrenciesInventory.getInstance().open(player, "Almas");
            player.getInventory().close();
            event.setCancelled(true);
        });

        GuiItem soulsItem = new GuiItem(souls(), event -> {
            CurrenciesInventory.getInstance().open(player, Currencies.SOULS.getName());
            event.setCancelled(true);
        });

        GuiItem ranksItem = new GuiItem(ranks(), event -> {
            // CurrenciesInventory.getInstance().open(player, "Almas");
            player.getInventory().close();
            event.setCancelled(true);
        });

        pane.addItem(coinsItem, Slot.fromIndex(10));
        pane.addItem(mineItem, Slot.fromIndex(11));
        pane.addItem(flakesItem, Slot.fromIndex(12));
        pane.addItem(spawnersItem, Slot.fromIndex(13));
        pane.addItem(seedsItem, Slot.fromIndex(14));
        pane.addItem(fossilsItem, Slot.fromIndex(15));
        pane.addItem(fishsItem, Slot.fromIndex(16));
        pane.addItem(machinesItem, Slot.fromIndex(20));
        pane.addItem(timeItem, Slot.fromIndex(21));
        pane.addItem(cashItem, Slot.fromIndex(22));
        pane.addItem(soulsItem, Slot.fromIndex(23));
        pane.addItem(ranksItem, Slot.fromIndex(24));

        inventory.addPane(pane);

        player.playSound(player.getLocation(), Sound.BLOCK_CHEST_OPEN, 0.5f, 0.5f);

        inventory.show(player);
    }

    public ItemStack coins() {
        return new ItemBuilder(Material.EMERALD)
                .setDisplayName("&aCoins")
                .setLore(Arrays.asList(
                        "&7Visualize os destaques de coins",
                        "",
                        "&aClique para visualizar."
                ))
                .addItemFlags(ItemFlag.HIDE_ATTRIBUTES)
                .build();
    }

    public ItemStack mine() {
        return new ItemBuilder(Material.STONE_PICKAXE)
                .setDisplayName("&8Mina")
                .setLore(Arrays.asList(
                        "&7Visualize os destaques da mina",
                        "",
                        "&8Clique para visualizar."
                ))
                .addItemFlags(ItemFlag.HIDE_ATTRIBUTES)
                .build();
    }

    public ItemStack flakes() {
        return new ItemBuilder(Material.SNOWBALL)
                .setDisplayName("&bFlocos")
                .setLore(Arrays.asList(
                        "&7Visualize os destaques de flocos",
                        "",
                        "&bClique para visualizar."
                ))
                .addItemFlags(ItemFlag.HIDE_ATTRIBUTES)
                .build();
    }

    public ItemStack spawners() {
        return new ItemBuilder(Material.SPAWNER)
                .setDisplayName("&2Spawners")
                .setLore(Arrays.asList(
                        "&7Visualize os destaques de spawners",
                        "",
                        "&2Clique para visualizar."
                ))
                .addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_ITEM_SPECIFICS)
                .build();
    }

    public ItemStack seeds() {
        return new ItemBuilder(Material.MELON_SEEDS)
                .setDisplayName("&cSementes")
                .setLore(Arrays.asList(
                        "&7Visualize os destaques de almas",
                        "",
                        "&cClique para visualizar."
                ))
                .addItemFlags(ItemFlag.HIDE_ATTRIBUTES)
                .build();
    }

    public ItemStack fossils() {
        return new ItemBuilder(Material.BONE)
                .setDisplayName("&fFósseis")
                .setLore(Arrays.asList(
                        "&7Visualize os destaques de fósseis",
                        "",
                        "&fClique para visualizar."
                ))
                .addItemFlags(ItemFlag.HIDE_ATTRIBUTES)
                .build();
    }

    public ItemStack fishs() {
        return new ItemBuilder(Material.TROPICAL_FISH)
                .setDisplayName("&6Peixes")
                .setLore(Arrays.asList(
                        "&7Visualize os destaques de peixes",
                        "",
                        "&6Clique para visualizar."
                ))
                .addItemFlags(ItemFlag.HIDE_ATTRIBUTES)
                .build();
    }

    public ItemStack machines() {
        return new ItemBuilder(Material.GOLD_BLOCK)
                .setDisplayName("&eMáquinas")
                .setLore(Arrays.asList(
                        "&7Visualize os destaques de máquinas",
                        "",
                        "&eClique para visualizar."
                ))
                .addItemFlags(ItemFlag.HIDE_ATTRIBUTES)
                .build();
    }

    public ItemStack time() {
        return new ItemBuilder(Material.CLOCK)
                .setDisplayName("&3Tempo")
                .setLore(Arrays.asList(
                        "&7Visualize os destaques de tempo",
                        "",
                        "&3Clique para visualizar."
                ))
                .addItemFlags(ItemFlag.HIDE_ATTRIBUTES)
                .build();
    }

    public ItemStack cash() {
        return new ItemBuilder(Material.PAPER)
                .setDisplayName("&2Reais")
                .setLore(Arrays.asList(
                        "&7Visualize os destaques de reais",
                        "",
                        "&2Clique para visualizar."
                ))
                .addItemFlags(ItemFlag.HIDE_ATTRIBUTES)
                .build();
    }

    public ItemStack souls() {
        return new ItemBuilder(Material.SOUL_SAND)
                .setDisplayName("&dAlmas")
                .setLore(Arrays.asList(
                        "&7Visualize os destaques de almas",
                        "",
                        "&dClique para visualizar."
                ))
                .addItemFlags(ItemFlag.HIDE_ATTRIBUTES)
                .build();
    }

    public ItemStack ranks() {
        return new ItemBuilder(Material.OAK_SIGN)
                .setDisplayName("&5Rank")
                .setLore(Arrays.asList(
                        "&7Visualize os destaques de ranks",
                        "",
                        "&5Clique para visualizar."
                ))
                .addItemFlags(ItemFlag.HIDE_ATTRIBUTES)
                .build();
    }

    public static TopsInventory getInstance() {
        if (instance == null) instance = new TopsInventory();
        return instance;
    }
}
