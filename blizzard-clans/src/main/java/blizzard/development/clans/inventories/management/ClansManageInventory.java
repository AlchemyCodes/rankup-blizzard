package blizzard.development.clans.inventories.management;

import com.github.stefvanschie.inventoryframework.gui.GuiItem;
import com.github.stefvanschie.inventoryframework.gui.type.ChestGui;
import com.github.stefvanschie.inventoryframework.pane.StaticPane;
import com.github.stefvanschie.inventoryframework.pane.util.Slot;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import blizzard.development.clans.database.cache.ClansCacheManager;
import blizzard.development.clans.database.storage.ClansData;
import blizzard.development.clans.enums.Roles;
import blizzard.development.clans.inventories.primary.ClansInventory;
import blizzard.development.clans.inventories.secondary.ConfirmationInventory;
import blizzard.development.clans.listeners.clans.ClansChangesListener;
import blizzard.development.clans.methods.ClansMethods;

import java.util.Arrays;
import java.util.List;

public class ClansManageInventory {

    public static void open(Player player) {
        ChestGui inventory = new ChestGui(4, "§8Clans - Gerenciamento");

        StaticPane pane = new StaticPane(0, 0, 9, 4);

        String clan = ClansMethods.getUserClan(player);

        GuiItem changeTagItem = new GuiItem(changeTag(), event -> {
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

            if (!isOwner && !leader) {
                player.sendMessage("§cVocê não tem permissão para mudar a tag do seu clan!");
                event.setCancelled(true);
                return;
            }

            ClansChangesListener.addPendingChange(player, "changeTag");
            List<String> messages = Arrays.asList(
                    "",
                    "§aDigite no chat a nova tag do seu clan!",
                    "",
                    "§8Digite 'cancelar' para cancelar a mudança.",
                    ""
            );
            for (String message : messages) {
                player.sendMessage(message);
            }
            player.closeInventory();
            event.setCancelled(true);
        });

        GuiItem changeNameItem = new GuiItem(changeName(), event -> {
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

            if (!isOwner && !leader) {
                player.sendMessage("§cVocê não tem permissão para mudar o nome do seu clan!");
                event.setCancelled(true);
                return;
            }

            ClansChangesListener.addPendingChange(player, "changeName");
            List<String> messages = Arrays.asList(
                    "",
                    "§aDigite no chat o novo nome do seu clan!",
                    "",
                    "§8Digite 'cancelar' para cancelar a mudança.",
                    ""
            );
            for (String message : messages) {
                player.sendMessage(message);
            }
            player.closeInventory();
            event.setCancelled(true);
        });


        GuiItem friendlyFireStateItem = new GuiItem(friendlyFireState(clan), event -> {
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
                player.sendMessage("§cVocê não tem permissão para mudar o estado do fogo amigo!");
                event.setCancelled(true);
                return;
            }

            Boolean state = data.isFriendlyfire();

            if (state) {
                ClansCacheManager.setFriendlyFireState(clan, false);
                player.sendMessage("§aVocê desligou o fogo amigo do seu clan com sucesso!");
                player.getOpenInventory().close();
                event.setCancelled(true);
            } else {
                ClansCacheManager.setFriendlyFireState(clan, true);
                player.sendMessage("§aVocê ligou o fogo amigo do seu clan com sucesso!");
                player.getOpenInventory().close();
                event.setCancelled(true);
            }
        });

        GuiItem disbandClanItem = new GuiItem(disbandClan(), event -> {
            if (clan == null) {
                player.sendMessage("§cVocê não pertence a nenhum clan!");
                player.getOpenInventory().close();
                event.setCancelled(true);
                return;
            }

            Boolean isOwner = ClansMethods.isOwner(clan, player);
            if (!isOwner) {
                player.sendMessage("§cVocê não é dono desse clan!");
                player.getOpenInventory().close();
                event.setCancelled(true);
                return;
            }

            ClansData clansData = ClansMethods.getClan(clan);

            if (clansData.getMoney() > 0) {
                player.sendMessage("§cVocê precisa tirar o dinheiro do banco antes de desfazer o clan!");
                player.getOpenInventory().close();
                event.setCancelled(true);
                return;
            }

            ConfirmationInventory.openDisbandConfirmation(player);
        });

        GuiItem backItem = new GuiItem(back(), event -> {
            ClansInventory.open(player);
            event.setCancelled(true);
        });

        pane.addItem(changeTagItem, Slot.fromIndex(10));
        pane.addItem(changeNameItem, Slot.fromIndex(11));
        pane.addItem(friendlyFireStateItem, Slot.fromIndex(15));
        pane.addItem(disbandClanItem, Slot.fromIndex(16));
        pane.addItem(backItem, Slot.fromIndex(31));

        inventory.addPane(pane);

        player.playSound(player.getLocation(), Sound.BLOCK_CHEST_OPEN, 0.5f, 0.5f);

        inventory.show(player);
    }

    public static ItemStack changeTag() {
        ItemStack item = new ItemStack(Material.OAK_HANGING_SIGN);

        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName("§6Mudar tag");
        meta.setLore(Arrays.asList(
                "§7Modifique a tag do seu clan",
                "",
                "§6Clique para modificar."
        ));
        item.setItemMeta(meta);

        return item;
    }

    public static ItemStack changeName() {
        ItemStack item = new ItemStack(Material.JUNGLE_HANGING_SIGN);

        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName("§6Mudar nome");
        meta.setLore(Arrays.asList(
                "§7Modifique o nome do seu clan",
                "",
                "§6Clique para modificar."
        ));
        item.setItemMeta(meta);

        return item;
    }

    public static ItemStack friendlyFireState(String clan) {
        ItemStack item = new ItemStack(Material.CAMPFIRE);

        ClansData data = ClansMethods.getClan(clan);

        Boolean state = data.isFriendlyfire();

        String stateString = state ? "§aLigado" : "§cDesligado";

        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName("§aFogo Amigo");
        meta.setLore(Arrays.asList(
                "§7Modifique o estado do Fogo amigo",
                "",
                " §8■ " + stateString,
                "",
                "§aClique para modificar."
        ));
        item.setItemMeta(meta);

        return item;
    }

    public static ItemStack disbandClan() {
        ItemStack item = new ItemStack(Material.BARRIER);

        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName("§cDesfazer");
        meta.setLore(Arrays.asList(
                "§7Desfaça o clan",
                "",
                "§cClique para desfazer."
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
