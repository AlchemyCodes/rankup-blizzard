package blizzard.development.events.inventories;

import blizzard.development.currencies.api.CurrenciesAPI;
import blizzard.development.currencies.enums.Currencies;
import blizzard.development.events.managers.SumoManager;
import blizzard.development.events.utils.PluginImpl;
import blizzard.development.events.utils.items.ItemBuilder;
import com.github.stefvanschie.inventoryframework.gui.GuiItem;
import com.github.stefvanschie.inventoryframework.gui.type.ChestGui;
import com.github.stefvanschie.inventoryframework.pane.StaticPane;
import com.github.stefvanschie.inventoryframework.pane.util.Slot;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import java.util.List;
import java.util.Objects;


public class BuyInventory {
    public static void openBuyInventory(Player player) {
        ChestGui gui = new ChestGui(5, "Comprar");

        StaticPane pane = new StaticPane(0, 0, 9, 5);

        GuiItem useMoney = new GuiItem(useMoney(), event -> {
            event.setCancelled(true);

            SumoManager instance = SumoManager.getInstance();
            YamlConfiguration messagesConfig = PluginImpl.getInstance().Messages.getConfig();

            if (instance.isSumoActive) {
                player.sendMessage(Objects.requireNonNull(messagesConfig.getString("events.sumo.sumoActive")));
                return;
            }

            if (CurrenciesAPI.getInstance().getBalance(player, Currencies.COINS) < 15000) {
                player.sendMessage(Objects.requireNonNull(messagesConfig.getString("events.sumo.sumoNotEnoughMoney")));
                return;
            }

            instance.sendStartMessage(messagesConfig, player);
            instance.startSumo();
            player.closeInventory();
        });

        GuiItem useFlakes = new GuiItem(useFlakes(), event -> {
            event.setCancelled(true);

            SumoManager instance = SumoManager.getInstance();
            YamlConfiguration messagesConfig = PluginImpl.getInstance().Messages.getConfig();

            if (instance.isSumoActive) {
                player.sendMessage(Objects.requireNonNull(messagesConfig.getString("events.sumo.sumoActive")));
                return;
            }

            if (CurrenciesAPI.getInstance().getBalance(player, Currencies.FLAKES) < 2000) {
                player.sendMessage(Objects.requireNonNull(messagesConfig.getString("events.sumo.sumoNotEnoughFlakes")));
                return;
            }

            instance.sendStartMessage(messagesConfig, player);
            instance.startSumo();
            player.closeInventory();
        });


        pane.addItem(useMoney, Slot.fromIndex(12));
        pane.addItem(useFlakes, Slot.fromIndex(14));


        gui.addPane(pane);
        gui.show(player);
    }

    public static ItemStack useMoney() {
        return new ItemBuilder(Material.GREEN_STAINED_GLASS)
                .setDisplayName("§aMoney")
                .setLore(List.of(
                        "§7Compre o evento com money!",
                        "",
                        " §fCusto: §a15k Money",
                        "",
                        "§aClique para comprar."))
                .build();
    }

    public static ItemStack useFlakes() {
        return new ItemBuilder(Material.LIGHT_BLUE_STAINED_GLASS)
                .setDisplayName("§bFlocos")
                .setLore(List.of(
                        "§7Compre o evento com flocos!",
                        "",
                        " §fCusto: §b2k Flocos",
                        "",
                        "§bClique para comprar."))
                .build();
    }
}