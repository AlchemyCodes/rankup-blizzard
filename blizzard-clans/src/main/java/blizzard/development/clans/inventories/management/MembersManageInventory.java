package blizzard.development.clans.inventories.management;

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
import blizzard.development.clans.database.cache.ClansCacheManager;
import blizzard.development.clans.database.cache.PlayersCacheManager;
import blizzard.development.clans.database.storage.PlayersData;
import blizzard.development.clans.enums.Roles;
import blizzard.development.clans.inventories.primary.ClansInventory;
import blizzard.development.clans.inventories.secondary.RolesInventory;
import blizzard.development.clans.listeners.clans.ClansInviteListener;
import blizzard.development.clans.methods.ClansMethods;
import blizzard.development.clans.utils.skulls.SkullAPI;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class MembersManageInventory {

    private static final int ITEMS_PER_PAGE = 14;

    public static void open(Player player, int page) {
        ChestGui inventory = new ChestGui(6, "§8Clan - Membros");

        StaticPane pane = new StaticPane(0, 0, 9, 6);

        String clan = ClansMethods.getUserClan(player);

        List<String> members = ClansCacheManager.getMembers(clan);

        members.sort(Comparator.comparingInt(member -> {
            PlayersData playersData = PlayersCacheManager.getPlayerDataByName(member);
            if (playersData != null) {
                if (ClansCacheManager.isOwner(clan, member)) {
                    return Integer.MIN_VALUE;
                }
                Roles role = Roles.fromName(playersData.getRole());
                if (role != null) {
                    return -role.getPriority();
                }
            }
            return 1;
        }));


        int startIndex = page * ITEMS_PER_PAGE;
        int endIndex = Math.min(startIndex + ITEMS_PER_PAGE, members.size());

        int[] slots = {10, 11, 12, 13, 14, 15, 16, 19, 20, 21, 22, 23, 24, 25};

        for (int i = startIndex; i < endIndex; i++) {
            String member = members.get(i);
            ItemStack item = member(member);
            GuiItem guiItem = new GuiItem(item, event -> {
                String playerRole = ClansMethods.getUser(player).getRole();
                String memberRole = PlayersCacheManager.getPlayerDataByName(member).getRole();

                Boolean isOwner = ClansMethods.isOwner(clan, player);
                Boolean leader = playerRole.equals(Roles.LEADER.getName());
                Boolean captain = playerRole.equals(Roles.CAPTAIN.getName());

                if (!isOwner && !leader && !captain) {
                    player.sendMessage("§cVocê não tem permissão para gerenciar cargos!");
                    event.setCancelled(true);
                    return;
                }

                if (member.equals(player.getName())) {
                    player.sendMessage("§cVocê não pode se gerenciar!");
                    event.setCancelled(true);
                    return;
                }

                if (ClansCacheManager.getClansData(clan).getOwner().equals(member) && !isOwner) {
                    player.sendMessage("§cVocê não pode gerenciar o dono do clan!");
                    event.setCancelled(true);
                    return;
                }

                int playerRolePriority = Roles.fromName(playerRole).getPriority();
                int memberRolePriority = Roles.fromName(memberRole).getPriority();

                if (playerRolePriority <= memberRolePriority && !isOwner) {
                    player.sendMessage("§cVocê não pode gerenciar um membro com cargo maior ou igual ao seu!");
                    event.setCancelled(true);
                    return;
                }

                MemberManageInventory.open(player, member);
                event.setCancelled(true);
            });
            pane.addItem(guiItem, Slot.fromIndex(slots[i - startIndex]));
        }

        boolean hasPreviousPage = page > 0;
        boolean hasNextPage = endIndex < members.size();

        GuiItem previousItem = new GuiItem(previous(), event -> {
            event.setCancelled(true);
            if (hasPreviousPage) {
                open(player, page - 1);
            } else {
                ClansInventory.open(player);
            }
        });

        GuiItem laterItem = new GuiItem(later(), event -> {
            event.setCancelled(true);
            if (hasNextPage) {
                open(player, page + 1);
            }
        });

        GuiItem inviteItem = new GuiItem(invite(), event -> {
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
                player.sendMessage("§cVocê não tem permissão para convidar membros para seu clan!");
                event.setCancelled(true);
                player.getOpenInventory().close();
                return;
            }

            ClansInviteListener.addPendingInvite(player);
            List<String> messages = Arrays.asList(
                    "",
                    "§aDigite no chat o nome do jogador que deseja convidar!",
                    "",
                    "§8Digite 'cancelar' para cancelar o convite.",
                    ""
            );
            for (String message : messages) {
                player.sendMessage(message);
            }
            player.closeInventory();
            event.setCancelled(true);
        });

        GuiItem rolesItem = new GuiItem(roles(), event -> {
            RolesInventory.open(player);
            event.setCancelled(true);
        });

        if (hasNextPage) {
            pane.addItem(laterItem, Slot.fromIndex(43));
            pane.addItem(previousItem, Slot.fromIndex(37));
            pane.addItem(inviteItem, Slot.fromIndex(41));
            pane.addItem(rolesItem, Slot.fromIndex(39));
        } else {
            pane.addItem(previousItem, Slot.fromIndex(38));
            pane.addItem(inviteItem, Slot.fromIndex(42));
            pane.addItem(rolesItem, Slot.fromIndex(40));
        }

        inventory.addPane(pane);

        player.playSound(player.getLocation(), Sound.BLOCK_CHEST_OPEN, 0.5f, 0.5f);

        inventory.show(player);
    }

    private static ItemStack member(String member) {
        PlayersData playersData = PlayersCacheManager.getPlayerDataByName(member);

        if (playersData == null) {
            return null;
        }

        String roleTag = ClansMethods.getRoleEmojiByNameWithPlayerName(member);

        Player target = Bukkit.getPlayer(member);

        String status;

        if (target == null) {
            status = "§cOffline";
        } else {
            status = target.isOnline() ?  "§aOnline" : "§cOffline";
        }

        String role;

        if (ClansCacheManager.isOwner(playersData.getClan(), member)) {
            role = "Dono";
        } else {
            role = playersData.getRole();
        }

        ItemStack skull = SkullAPI.withName(new ItemStack(Material.PLAYER_HEAD), member);
        ItemMeta meta = skull.getItemMeta();
        meta.setDisplayName("§7" + playersData.getNickname() + "§8 - " + roleTag);
        meta.setLore(Arrays.asList(
                "",
                "§7 Status: §a" + status,
                "§7 Cargo: §a" + role,
                "§7 KDR: §a" + PlayersCacheManager.getKDR(member),
                "",
                "§aClique para gerenciar"
        ));
        skull.setItemMeta(meta);

        return skull;
    }

    private static ItemStack previous() {
        ItemStack item = new ItemStack(Material.RED_DYE);

        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName("§cAnterior");
        meta.setLore(Arrays.asList("§7Página anterior"));
        item.setItemMeta(meta);

        return item;
    }

    private static ItemStack later() {
        ItemStack item = new ItemStack(Material.LIME_DYE);

        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName("§aPróximo");
        meta.setLore(Arrays.asList("§7Próxima página"));
        item.setItemMeta(meta);

        return item;
    }

    private static ItemStack invite() {

        String value = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYjA1NmJjMTI0NGZjZmY5OTM0NGYxMmFiYTQyYWMyM2ZlZTZlZjZlMzM1MWQyN2QyNzNjMTU3MjUzMWYifX19";
        ItemStack skull = SkullAPI.fromBase64(SkullAPI.Type.BLOCK, value);

        ItemMeta meta = skull.getItemMeta();
        meta.setDisplayName("§aConvidar");
        meta.setLore(Arrays.asList(
                "§7Convide um membro para o clan",
                "",
                "§aClique para convidar."
        ));
        skull.setItemMeta(meta);

        return skull;
    }

    private static ItemStack roles() {

        String value = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYmQyM2RiZWVmNjY2MTVhMWNhMzhmZmUyYjc0NTY3ZDk2YTcwNmEzNWE3MTFmMzU1N2M4ZTE3ZWM1ZWRlODA4MSJ9fX0=";
        ItemStack skull = SkullAPI.fromBase64(SkullAPI.Type.BLOCK, value);

        ItemMeta meta = skull.getItemMeta();
        meta.setDisplayName("§6Cargos");
        meta.setLore(Arrays.asList(
                "§7Verifique os cargos disponíveis",
                "",
                "§6Clique para verificar."
        ));
        skull.setItemMeta(meta);

        return skull;
    }

}
