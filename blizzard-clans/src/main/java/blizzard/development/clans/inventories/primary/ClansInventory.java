package blizzard.development.clans.inventories.primary;

import blizzard.development.clans.inventories.secondary.BankInventory;
import blizzard.development.clans.inventories.secondary.BaseInventory;
import blizzard.development.clans.inventories.secondary.ClansListInventory;
import blizzard.development.clans.inventories.secondary.ConfirmationInventory;
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
import blizzard.development.clans.inventories.management.ClansManageInventory;
import blizzard.development.clans.inventories.management.MembersManageInventory;
import blizzard.development.clans.inventories.secondary.ranking.MoneyRankingInventory;
import blizzard.development.clans.methods.ClansMethods;
import blizzard.development.clans.utils.NumberFormat;
import blizzard.development.clans.utils.skulls.LetterSkulls;

import java.text.DecimalFormat;
import java.util.Arrays;

public class ClansInventory {

    public static void open(Player player) {
        ChestGui inventory = new ChestGui(4, "§8Clans");

        StaticPane pane = new StaticPane(0, 0, 9, 4);

        String clan = ClansMethods.getUserClan(player);

        GuiItem infoItem = new GuiItem(info(clan), event -> {
            if (clan == null) {
                player.sendMessage("§cVocê não está em um clan!");
                player.getOpenInventory().close();
                event.setCancelled(true);
                return;
            }
            event.setCancelled(true);
        });

        GuiItem leaveItem = new GuiItem(leave(), event -> {
            if (clan == null) {
                player.sendMessage("§cVocê não está em um clan!");
                player.getOpenInventory().close();
                event.setCancelled(true);
                return;
            }
            ConfirmationInventory.openLeaveConfirmation(player);
            event.setCancelled(true);
        });

        GuiItem manageClanItem = new GuiItem(manageClan(), event -> {
            if (clan == null) {
                player.sendMessage("§cVocê não está em um clan!");
                player.getOpenInventory().close();
                event.setCancelled(true);
                return;
            }
            String playerRole = ClansMethods.getUser(player).getRole();

            Boolean isOwner = ClansMethods.isOwner(clan, player);
            Boolean leader = playerRole.equals(Roles.LEADER.getName());

            if (!isOwner && !leader) {
                player.sendMessage("§cVocê não tem permissão para gerenciar o seu clan!");
                event.setCancelled(true);
                player.getOpenInventory().close();
                return;
            }

            ClansManageInventory.open(player);
            event.setCancelled(true);
        });

        GuiItem manageMembersItem = new GuiItem(manageMembers(), event -> {
            if (clan == null) {
                player.sendMessage("§cVocê não está em um clan!");
                player.getOpenInventory().close();
                event.setCancelled(true);
                return;
            }
            String playerRole = ClansMethods.getUser(player).getRole();

            Boolean isOwner = ClansMethods.isOwner(clan, player);
            Boolean leader = playerRole.equals(Roles.LEADER.getName());
            Boolean captain = playerRole.equals(Roles.CAPTAIN.getName());
            Boolean reliable = playerRole.equals(Roles.RELIABLE.getName());

            if (!isOwner && !leader && !captain && !reliable) {
                player.sendMessage("§cVocê não tem permissão para gerenciar os membros do seu clan!");
                event.setCancelled(true);
                player.getOpenInventory().close();
                return;
            }
            MembersManageInventory.open(player, 0);
            event.setCancelled(true);
        });

        GuiItem baseItem = new GuiItem(base(), event -> {
            if (clan == null) {
                player.sendMessage("§cVocê não está em um clan!");
                player.getOpenInventory().close();
                event.setCancelled(true);
                return;
            }
            String playerRole = ClansMethods.getUser(player).getRole();

            Boolean isOwner = ClansMethods.isOwner(clan, player);
            Boolean leader = playerRole.equals(Roles.LEADER.getName());

            if (!isOwner && !leader) {
                player.sendMessage("§cVocê não tem permissão para gerenciar a base do seu clan!");
                event.setCancelled(true);
                player.getOpenInventory().close();
                return;
            }
            BaseInventory.open(player);
            event.setCancelled(true);
        });

        GuiItem bankItem = new GuiItem(bank(), event -> {
            if (clan == null) {
                player.sendMessage("§cVocê não está em um clan!");
                player.getOpenInventory().close();
                event.setCancelled(true);
                return;
            }
            BankInventory.open(player);
            event.setCancelled(true);
        });

        GuiItem clansItem = new GuiItem(clans(), event -> {
            if (clan == null) {
                player.sendMessage("§cVocê não está em um clan!");
                player.getOpenInventory().close();
                event.setCancelled(true);
                return;
            }
            ClansListInventory.open(player, 0);
            event.setCancelled(true);
        });

        GuiItem rankingItem = new GuiItem(ranking(), event -> {
            if (clan == null) {
                player.sendMessage("§cVocê não está em um clan!");
                player.getOpenInventory().close();
                event.setCancelled(true);
                return;
            }
            MoneyRankingInventory.open(player, 0);
            event.setCancelled(true);
        });

        pane.addItem(infoItem, Slot.fromIndex(10));
        pane.addItem(leaveItem, Slot.fromIndex(19));
        pane.addItem(manageClanItem, Slot.fromIndex(16));
        pane.addItem(manageMembersItem, Slot.fromIndex(15));
        pane.addItem(baseItem, Slot.fromIndex(13));
        pane.addItem(bankItem, Slot.fromIndex(12));
        pane.addItem(clansItem, Slot.fromIndex(21));
        pane.addItem(rankingItem, Slot.fromIndex(25));

        inventory.addPane(pane);

        player.playSound(player.getLocation(), Sound.BLOCK_CHEST_OPEN, 0.5f, 0.5f);

        inventory.show(player);
    }

    public static ItemStack info(String clan) {
        ItemStack item = LetterSkulls.getSkullByName(clan);

        ClansData data = ClansMethods.getClan(clan);

        DecimalFormat decimalFormat = new DecimalFormat("#.##");

        String date = data.getCreationDate();
        String tag = data.getTag();
        String name = data.getName();
        String owner = data.getOwner();
        int members = ClansMethods.getMembersCount(clan);
        int maxmembers = ClansCacheManager.getMaxClanMembers(clan);
        long money = data.getMoney();
        double kdr = Double.parseDouble(decimalFormat.format(data.getKdr()));

        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName("§aInformações");
        meta.setLore(Arrays.asList(
                "",
                "§a Informações do clan §7" + name + " [" + tag + "]",
                "",
                "§7 Criação: §a" + date,
                "§7 Nome: §a" + name,
                "§7 Tag: §a" + tag,
                "§7 Dono: §a" + owner,
                "§7 Membros: §a" + members + "§7/§a" + maxmembers,
                "§7 Saldo: §2$§7" + NumberFormat.formatNumber(money),
                "§7 KDR: §a" + kdr,
                ""
        ));
        item.setItemMeta(meta);

        return item;
    }

    public static ItemStack leave() {
        ItemStack item = new ItemStack(Material.ARROW);

        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName("§cSair");
        meta.setLore(Arrays.asList(
                "§7Saia do seu clan",
                "",
                "§cClique para sair."
        ));
        item.setItemMeta(meta);

        return item;
    }

    public static ItemStack manageClan() {
        ItemStack item = new ItemStack(Material.FIRE_CHARGE);

        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName("§6Gerenciar clan");
        meta.setLore(Arrays.asList(
                "§7Gerencie opções do seu clan",
                "",
                "§7Clique para gerenciar."
        ));
        item.setItemMeta(meta);

        return item;
    }

    public static ItemStack manageMembers() {
        ItemStack item = new ItemStack(Material.FLINT);

        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName("§8Gerenciar membros");
        meta.setLore(Arrays.asList(
                "§7Gerencie membros do seu clan",
                "",
                "§8Clique para gerenciar."
        ));
        item.setItemMeta(meta);

        return item;
    }

    public static ItemStack base() {
        ItemStack item = new ItemStack(Material.RED_BANNER);

        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName("§cBase");
        meta.setLore(Arrays.asList(
                "§7Visualize as opções da base do seu clan",
                "",
                "§cClique para visualizar."
        ));
        item.setItemMeta(meta);

        return item;
    }

    public static ItemStack bank() {
        ItemStack item = new ItemStack(Material.CHEST);

        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName("§eBanco");
        meta.setLore(Arrays.asList(
                "§7Visualize as opções do banco do seu clan",
                "",
                "§eClique para visualizar."
        ));
        item.setItemMeta(meta);

        return item;
    }

    public static ItemStack clans() {
        ItemStack item = new ItemStack(Material.DIAMOND_HORSE_ARMOR);

        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName("§bClans");
        meta.setLore(Arrays.asList(
                "§7Visualize todos os clans do servidor",
                "",
                "§bClique para visualizar."
        ));
        item.setItemMeta(meta);

        return item;
    }

    public static ItemStack ranking() {
        ItemStack item = new ItemStack(Material.GOLD_INGOT);

        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName("§6Destaques");
        meta.setLore(Arrays.asList(
                "§7Visualize os clans que mais",
                "§7se destacam no servidor",
                "",
                "§6Clique para visualizar."
        ));
        item.setItemMeta(meta);

        return item;
    }

}
