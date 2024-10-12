package blizzard.development.clans.inventories.secondary;

import com.github.stefvanschie.inventoryframework.gui.GuiItem;
import com.github.stefvanschie.inventoryframework.gui.type.ChestGui;
import com.github.stefvanschie.inventoryframework.pane.StaticPane;
import com.github.stefvanschie.inventoryframework.pane.util.Slot;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import blizzard.development.clans.database.storage.ClansData;
import blizzard.development.clans.database.storage.PlayersData;
import blizzard.development.clans.inventories.primary.ClansInventory;
import blizzard.development.clans.inventories.primary.DefaultInventory;
import blizzard.development.clans.methods.ClansMethods;
import blizzard.development.clans.utils.gradient.TextUtil;

import java.util.Arrays;
import java.util.List;

public class InvitesInventory {

    public static void open(Player player) {
        ChestGui inventory = new ChestGui(4, "§8Clans - Convites");

        StaticPane pane = new StaticPane(0, 0, 9, 4);

        List<String> invites = ClansMethods.getInvites(player.getName());

        if (invites == null || invites.isEmpty()) {
            GuiItem cobwebItem = new GuiItem(none(), event -> {
                event.setCancelled(true);
            });
            pane.addItem(cobwebItem, Slot.fromIndex(13));
        } else {
            int[] slots = {11, 12, 13, 14, 15};
            for (int i = 0; i < invites.size() && i < slots.length; i++) {
                String clanTag = invites.get(i);
                ClansData clanData = ClansMethods.getClan(clanTag);
                if (clanData != null) {
                    GuiItem inviteItem = new GuiItem(invite(clanData), event -> {
                        event.setCancelled(true);
                        if (event.getClick() == ClickType.LEFT) {
                            accept(clanTag, player);
                            player.getOpenInventory().close();
                        } else if (event.getClick() == ClickType.RIGHT) {
                            decline(clanTag, player);
                            player.getOpenInventory().close();
                        }
                    });
                    pane.addItem(inviteItem, Slot.fromIndex(slots[i]));
                }
            }
        }

        GuiItem backItem = new GuiItem(back(), event -> {
            String clan = ClansMethods.getUserClan(player);
            event.setCancelled(true);
            if (clan == null) {
                DefaultInventory.open(player);
            } else {
                ClansInventory.open(player);
            }
        });

        pane.addItem(backItem, Slot.fromIndex(31));

        inventory.addPane(pane);

        player.playSound(player.getLocation(), Sound.BLOCK_CHEST_OPEN, 0.5f, 0.5f);

        inventory.show(player);
    }

    public static void accept(String clan, Player player) {
        if (ClansMethods.isClanTagAvailable(clan)) {
            player.sendMessage("§cEste clan já não existe mais!");
            return;
        }

        if (!ClansMethods.hasInviteByName(clan, player.getName())) {
            player.sendMessage("§cO convite está inválido ou expirado!");
            return;
        }

        PlayersData playersData = ClansMethods.getUser(player);

        if (playersData.getClan() != null) {
            if (playersData.getClan().equals(clan)) {
                player.sendMessage("§cVocê já está nesse clan!");
                return;
            } else {
                player.sendMessage("§cVocê já está em um clan, saia dele primeiro!");
                return;
            }
        }

        ClansData clansData = ClansMethods.getClan(clan);
        String name = clansData.getName();
        String tag = clansData.getTag();

        player.sendMessage("§aVocê entrou no clan §7" + name + " [" + tag + "] §acom sucesso!");

        List<String> members = ClansMethods.getMembers(clan);

        for (Player players : Bukkit.getOnlinePlayers()) {
            if (members.contains(players.getName())) {
                players.sendMessage(
                        TextUtil.parse("<#469536> [<#469536>+<#469536>] <#469536> <#469536>O jogador " + player.getName() + " entrou no clan. <#55ff55>")
                );
            }
        }

        ClansMethods.joinClan(clan, player);
        ClansMethods.removeInviteByName(clan, player.getName());
    }

    public static void decline(String clan, Player player) {
        if (!ClansMethods.hasInviteByName(clan, player.getName())) {
            player.sendMessage("§cO convite está inválido ou expirado!");
            return;
        }

        ClansData clansData = ClansMethods.getClan(clan);
        String name = clansData.getName();
        String tag = clansData.getTag();

        player.sendMessage("§aVocê recusou o convite do clan §7" + name + " [" + tag + "] §acom sucesso!");

        ClansMethods.removeInviteByName(clan, player.getName());
    }

    public static ItemStack invite(ClansData clanData) {
        ItemStack item = new ItemStack(Material.LEGACY_EMPTY_MAP);

        String name = clanData.getName();
        String tag = clanData.getTag();

        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName("§a" + name + " [" + tag + "]");
        meta.setLore(Arrays.asList(
                "§7Convite para adentrar ao clan",
                "",
                "§aClique esquerdo para aceitar",
                "§cClique direito para negar"
        ));
        item.setItemMeta(meta);

        return item;
    }

    public static ItemStack none() {
        ItemStack item = new ItemStack(Material.COBWEB);

        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName("§cNenhum Convite");
        meta.setLore(Arrays.asList(
                "§7Você não tem convites no momento."
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
