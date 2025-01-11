package blizzard.development.fishing.inventories.items;

import blizzard.development.fishing.database.cache.methods.PlayersCacheMethod;
import blizzard.development.fishing.tasks.items.FurnaceTask;
import blizzard.development.fishing.utils.PluginImpl;
import blizzard.development.fishing.utils.items.ItemBuilder;
import com.github.stefvanschie.inventoryframework.gui.GuiItem;
import com.github.stefvanschie.inventoryframework.gui.type.ChestGui;
import com.github.stefvanschie.inventoryframework.pane.StaticPane;
import com.github.stefvanschie.inventoryframework.pane.util.Slot;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;

public class FurnaceInventory {
    public static void openFurnace(Player player) {
        YamlConfiguration config = PluginImpl.getInstance().Messages.getConfig();

        ChestGui gui = new ChestGui(5, "Fornalha");

        StaticPane pane = new StaticPane(0, 0, 9, 5);

        GuiItem frozenFish = new GuiItem(frozenFish(player), event -> { event.setCancelled(true);
        });

        GuiItem inProgress = new GuiItem(inProgress(), event -> { event.setCancelled(true);
        });

        GuiItem unfrozeFish = new GuiItem(unfrozeFish(), event -> { event.setCancelled(true);
        });

        GuiItem fishInBucket = new GuiItem(fishInBucket(), event -> { event.setCancelled(true);
        });

        GuiItem startUnfreezing = new GuiItem(startUnfreezing(), event -> {
            event.setCancelled(true);

            if (FurnaceTask.isUnfreezing(player)) {
                player.sendActionBar(config.getString("fornalha.estaDescongelando"));
                return;
            }

            FurnaceTask.addPlayer(player);
            player.sendActionBar(config.getString("fornalha.comecouDescongelar"));
        });

        GuiItem stopUnfreezing = new GuiItem(stopUnfreezing(), event -> {
            event.setCancelled(true);

            if (!(FurnaceTask.isUnfreezing(player))) {
                player.sendActionBar(config.getString("fornalha.naoEstaDescongelando"));
                return;
            }

            FurnaceTask.removePlayer(player);
            player.sendActionBar(config.getString("fornalha.parouDescongelar"));
        });

        for (int i : new int[]{11,12,14,15}) {
            pane.addItem(inProgress, Slot.fromIndex(i));
        }

        pane.addItem(frozenFish, Slot.fromIndex(10));
        pane.addItem(unfrozeFish, Slot.fromIndex(13));
        pane.addItem(fishInBucket, Slot.fromIndex(16));
        pane.addItem(stopUnfreezing, Slot.fromIndex(30));
        pane.addItem(startUnfreezing, Slot.fromIndex(32));


        gui.addPane(pane);

        gui.show(player);
    }

    private static ItemStack frozenFish(Player player) {
        return new ItemBuilder("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNzY0YjExYWU1ZDM4NzdjZjA4OGU0Yjg0MjQ4MzM3ODNiZmE5NzNhMDM0OTUwYTc0ZDc4Yjg5ZjMzMTVkMzZiZCJ9fX0=")
                .setDisplayName("§bPeixe Congelados §7[" + PlayersCacheMethod.getInstance().getFrozenFish(player) +"]")
                .setLore(Arrays.asList
                        ("§7Esses são seus peixes congelados."))
                .build();
    }

    private static ItemStack inProgress() {
        return new ItemBuilder(Material.BLACK_STAINED_GLASS_PANE)
                .setDisplayName("")
                .setLore(Arrays.asList(""))
                .build();
    }

    private static ItemStack unfrozeFish() {
        return new ItemBuilder(Material.TROPICAL_FISH)
                .setDisplayName("§6Peixe")
                .setLore(Arrays.asList(
                        "§7Aqui é a segunda parte do processo",
                        "§7onde o peixe foi descongelado.")) .build();
    }

    private static ItemStack fishInBucket() {
        return new ItemBuilder(Material.TROPICAL_FISH_BUCKET)
                .setDisplayName("§6Peixe no balde")
                .setLore(Arrays.asList(
                        "§7Aqui é a última parte do processo",
                        "§7onde o peixe será colocado no seu balde.")) .build();
    }

    private static ItemStack stopUnfreezing() {
        return new ItemBuilder(Material.RED_DYE)
                .setDisplayName("§cParar")
                .setLore(Arrays.asList(
                        "§7Clique para parar",
                        "§7o descongelamento."))
                .build();
    }

    private static ItemStack startUnfreezing() {
        return new ItemBuilder(Material.GREEN_DYE)
                .setDisplayName("§aIniciar")
                .setLore(Arrays.asList(
                        "§7Clique para iniciar",
                        "§7o descongelamento."))
                .build();
    }

}

