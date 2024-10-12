package blizzard.development.clans.inventories.secondary;

import blizzard.development.clans.utils.gradient.TextUtil;
import com.github.stefvanschie.inventoryframework.gui.GuiItem;
import com.github.stefvanschie.inventoryframework.gui.type.ChestGui;
import com.github.stefvanschie.inventoryframework.pane.StaticPane;
import com.github.stefvanschie.inventoryframework.pane.util.Slot;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import blizzard.development.clans.database.storage.ClansData;
import blizzard.development.clans.inventories.management.ClansManageInventory;
import blizzard.development.clans.inventories.primary.ClansInventory;
import blizzard.development.clans.methods.ClansMethods;

import java.util.Arrays;
import java.util.List;

public class ConfirmationInventory {

    public static void openLeaveConfirmation(Player player) {
        ChestGui inventory = new ChestGui(3, "§8Clans - Confirmação");

        StaticPane pane = new StaticPane(0, 0, 9, 3);

        GuiItem confirmItem = new GuiItem(confirm(), event -> {

            String clan = ClansMethods.getUserClan(player);

            if (clan == null) {
                player.sendMessage("§cVocê não está em nenhum clan!");
                player.getOpenInventory().close();
                event.setCancelled(true);
                return;
            }

            Boolean owner = ClansMethods.isOwner(clan, player);
            String name = ClansMethods.getClan(clan).getName();

            if (owner) {
                player.sendMessage("§cVocê não pode sair de um clan sendo dono, desfaça-o antes!");
                player.getOpenInventory().close();
                event.setCancelled(true);
                return;
            }

            player.sendMessage("§aVocê saiu do clan §7" + name + "§a com sucesso!");
            ClansMethods.leaveClan(clan, player);

            List<String> members = ClansMethods.getMembers(clan);

            for (Player players : Bukkit.getOnlinePlayers()) {
                if (members.contains(players.getName())) {
                    players.sendMessage(
                            TextUtil.parse("<#ff0000> [<#b81414>-<#b81414>] <#ff0000> <#ff0000>O jogador " + player.getName() + " saiu do clan. <#ec5353>"));
                }
            }

            event.setCancelled(true);
            player.getOpenInventory().close();
        });

        GuiItem declineItem = new GuiItem(decline(), event -> {
            player.sendMessage("§aVocê negou a saída do seu clan com sucesso!");
            ClansInventory.open(player);
            event.setCancelled(true);
        });

        pane.addItem(confirmItem, Slot.fromIndex(12));
        pane.addItem(declineItem, Slot.fromIndex(14));

        inventory.addPane(pane);

        player.playSound(player.getLocation(), Sound.BLOCK_CHEST_OPEN, 0.5f, 0.5f);

        inventory.show(player);
    }

    public static void openDisbandConfirmation(Player player) {
        ChestGui inventory = new ChestGui(3, "§8Clans - Confirmação");

        StaticPane pane = new StaticPane(0, 0, 9, 3);

        GuiItem confirmItem = new GuiItem(confirm(), event -> {

            String clan = ClansMethods.getUserClan(player);
            if (clan == null) {
                player.sendMessage("§cVocê não pertence a nenhum clan!");
                return;
            }

            Boolean isOwner = ClansMethods.isOwner(clan, player);
            if (!isOwner) {
                player.sendMessage("§cVocê não é dono desse clan!");
                return;
            }

            ClansData clanData = ClansMethods.getClan(clan);

            String name = clanData.getName();
            String tag = clanData.getTag();

            for (Player broadcast : Bukkit.getOnlinePlayers()) {
                broadcast.sendActionBar(
                        TextUtil.parse("<bold><#ff0000> YAY! <#ff0000></bold><#ff0000>O clan " + name + " [" + tag + "] foi deletado. <#ff5555>"));
            }

            player.sendMessage("§aVocê desfez o clan §7" + name + "§a!");

            ClansMethods.deleteClan(clan);

            event.setCancelled(true);
            player.getOpenInventory().close();
        });

        GuiItem declineItem = new GuiItem(decline(), event -> {
            player.sendMessage("§aVocê negou a exclusão do seu clan com sucesso!");
            ClansManageInventory.open(player);
            event.setCancelled(true);
        });

        pane.addItem(confirmItem, Slot.fromIndex(12));
        pane.addItem(declineItem, Slot.fromIndex(14));

        inventory.addPane(pane);

        player.playSound(player.getLocation(), Sound.BLOCK_CHEST_OPEN, 0.5f, 0.5f);

        inventory.show(player);
    }

    public static ItemStack confirm() {
        ItemStack item = new ItemStack(Material.LIME_WOOL);

        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName("§aConfirmar");
        meta.setLore(Arrays.asList(
                "§7Confirmer ação",
                "",
                "§aClique para confirmar."
        ));
        item.setItemMeta(meta);

        return item;
    }

    public static ItemStack decline() {
        ItemStack item = new ItemStack(Material.RED_WOOL);

        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName("§cNegar");
        meta.setLore(Arrays.asList(
                "§7Negar ação",
                "",
                "§cClique para negar."
        ));
        item.setItemMeta(meta);

        return item;
    }

}
