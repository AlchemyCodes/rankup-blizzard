package blizzard.development.clans.inventories.secondary;

import com.github.stefvanschie.inventoryframework.gui.GuiItem;
import com.github.stefvanschie.inventoryframework.gui.type.ChestGui;
import com.github.stefvanschie.inventoryframework.pane.StaticPane;
import com.github.stefvanschie.inventoryframework.pane.util.Slot;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import blizzard.development.clans.database.storage.ClansData;
import blizzard.development.clans.enums.Roles;
import blizzard.development.clans.inventories.primary.ClansInventory;
import blizzard.development.clans.methods.ClansMethods;
import blizzard.development.clans.listeners.clans.ClansBankListener;
import blizzard.development.clans.utils.NumberFormat;

import java.util.Arrays;
import java.util.List;

public class AlliesInventory {

    public static void open(Player player) {
        ChestGui inventory = new ChestGui(4, "§8Clans - Banco");

        StaticPane pane = new StaticPane(0, 0, 9, 4);

        String clan = ClansMethods.getUserClan(player);

        GuiItem infoItem = new GuiItem(info(clan), event -> {
            event.setCancelled(true);
        });

        GuiItem depositItem = new GuiItem(deposit(), event -> {
            ClansData data = ClansMethods.getClan(clan);

            if (data == null) {
                player.sendMessage("§cVocê não está em um clan!");
                player.getOpenInventory().close();
                event.setCancelled(true);
                return;
            }

            ClansBankListener.addPendingTransaction(player, "deposit");
            List<String> messages = Arrays.asList(
                    "",
                    "§aDigite no chat a quantia de money que deseja depositar!",
                    "",
                    "§8Digite 'cancelar' para cancelar a transição.",
                    ""
            );
            for (String message : messages) {
                player.sendMessage(message);
            }
            player.closeInventory();
            event.setCancelled(true);
        });

        GuiItem withdrawItem = new GuiItem(withdraw(), event -> {
            ClansData data = ClansMethods.getClan(clan);

            if (data == null) {
                player.sendMessage("§cVocê não está em um clan!");
                player.getOpenInventory().close();
                event.setCancelled(true);
                return;
            }

            String playerRole = ClansMethods.getUser(player).getRole();

            Boolean isOwner = ClansMethods.isOwner(clan, player);
            Boolean leader = playerRole.equals(Roles.LEADER.getName());
            Boolean captain = playerRole.equals(Roles.CAPTAIN.getName());

            if (!isOwner && !leader && !captain) {
                player.sendMessage("§cVocê não tem permissão para sacar dinheiro do banco!");
                return;
            }

            ClansBankListener.addPendingTransaction(player, "withdraw");
            List<String> messages = Arrays.asList(
                    "",
                    "§aDigite no chat a quantia de money que deseja sacar!",
                    "",
                    "§8Digite 'cancelar' para cancelar a transição.",
                    ""
            );
            for (String message : messages) {
                player.sendMessage(message);
            }
            player.closeInventory();
            event.setCancelled(true);
        });

        GuiItem backItem = new GuiItem(back(), event -> {
            ClansInventory.open(player);
            event.setCancelled(true);
        });

        pane.addItem(infoItem, Slot.fromIndex(13));
        pane.addItem(depositItem, Slot.fromIndex(11));
        pane.addItem(withdrawItem, Slot.fromIndex(15));
        pane.addItem(backItem, Slot.fromIndex(31));

        inventory.addPane(pane);

        player.playSound(player.getLocation(), Sound.BLOCK_CHEST_OPEN, 0.5f, 0.5f);

        inventory.show(player);
    }

    public static ItemStack info(String clan) {
        ItemStack item = new ItemStack(Material.CHEST);

        ClansData data = ClansMethods.getClan(clan);
        long money = data.getMoney();

        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName("§eInformações");
        meta.setLore(Arrays.asList(
                "",
                "§7Saldo: §2$§7" + NumberFormat.formatNumber(money),
                ""
        ));
        item.setItemMeta(meta);

        return item;
    }

    public static ItemStack deposit() {
        ItemStack item = new ItemStack(Material.REDSTONE);

        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName("§cDepositar");
        meta.setLore(Arrays.asList(
                "§7Deposite dinheiro para seu clan",
                "",
                "§cClique para depositar."
        ));
        item.setItemMeta(meta);

        return item;
    }

    public static ItemStack withdraw() {
        ItemStack item = new ItemStack(Material.LIME_DYE);

        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName("§aSacar");
        meta.setLore(Arrays.asList(
                "§7Saque dinheiro do seu clan",
                "",
                "§aClique para sacar."
        ));
        item.setItemMeta(meta);

        return item;
    }

    public static ItemStack back() {
        ItemStack item = new ItemStack(Material.RED_DYE);

        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName("§cVoltar");
        meta.setLore(Arrays.asList(
                "§7Volte para o menu principal!"
        ));
        item.setItemMeta(meta);

        return item;
    }
}
