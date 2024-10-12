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
import blizzard.development.clans.methods.ClansMethods;
import blizzard.development.clans.utils.gradient.TextUtil;
import blizzard.development.clans.utils.skulls.SkullAPI;
import java.util.Arrays;
import java.util.List;

public class MemberManageInventory {

    public static void open(Player player, String target) {
        ChestGui inventory = new ChestGui(3, "§8" + target);

        StaticPane pane = new StaticPane(0, 0, 9, 3);

        String clan = ClansMethods.getUserClanByName(target);

        GuiItem promoteItem = new GuiItem(promote(), event -> {
            if (clan == null) {
                player.sendMessage("§cVocê não pertence a nenhum clan!");
                player.getOpenInventory().close();
                event.setCancelled(true);
                return;
            }

            String playerRole = ClansMethods.getUser(player).getRole();
            String clanOwner = ClansMethods.getClan(clan).getOwner();

            Boolean isOwner = ClansMethods.isOwner(clan, player);
            Boolean isLeader = playerRole.equals(Roles.LEADER.getName());
            Boolean isCaptain = playerRole.equals(Roles.CAPTAIN.getName());

            if (!isOwner && !isLeader && !isCaptain) {
                player.sendMessage("§cVocê não tem permissão para promover membros!");
                player.getOpenInventory().close();
                event.setCancelled(true);
                return;
            }

            if (!ClansCacheManager.isMemberInClan(clan, target)) {
                player.sendMessage("§cO jogador informado não está no clan!");
                player.getOpenInventory().close();
                event.setCancelled(true);
                return;
            }

            String memberRole = PlayersCacheManager.getPlayerDataByName(target).getRole();

            Boolean leader = memberRole.equals(Roles.LEADER.getName());
            Boolean captain = memberRole.equals(Roles.CAPTAIN.getName());
            Boolean reliable = memberRole.equals(Roles.RELIABLE.getName());
            Boolean member = memberRole.equals(Roles.MEMBER.getName());

            if (target.equals(player.getName())) {
                player.sendMessage("§cVocê não pode se promover!");
                player.getOpenInventory().close();
                event.setCancelled(true);
                return;
            }

            if (target.equals(clanOwner)) {
                player.sendMessage("§cVocê não pode gerenciar o dono do clan!");
                player.getOpenInventory().close();
                event.setCancelled(true);
                return;
            }

            int playerRolePriority = Roles.fromName(playerRole).getPriority();
            int memberRolePriority = Roles.fromName(memberRole).getPriority();

            if (playerRolePriority <= memberRolePriority && !isOwner) {
                player.sendMessage("§cVocê não pode promover um membro com cargo maior ou igual ao seu!");
                player.getOpenInventory().close();
                event.setCancelled(true);
                return;
            }

            if (isLeader && captain && !isOwner) {
                player.sendMessage("§cVocê não pode promover um Capitão para Líder!");
                player.getOpenInventory().close();
                event.setCancelled(true);
                return;
            }

            if (isCaptain && reliable && !isOwner) {
                player.sendMessage("§cVocê não pode promover um Confiável para Capitão!");
                player.getOpenInventory().close();
                event.setCancelled(true);
                return;
            }

            if (member) {
                PlayersCacheManager.setRole(target, Roles.RELIABLE.getName());
                player.sendMessage("§aVocê promoveu o jogador §7" + target + "§a para §7Confiável§a!");
                promoteMessage(player, target, "Confiável");
            } else if (reliable) {
                PlayersCacheManager.setRole(target, Roles.CAPTAIN.getName());
                player.sendMessage("§aVocê promoveu o jogador §7" + target + "§a para §7Capitão§a!");
                promoteMessage(player, target, "Capitão");
            } else if (captain) {
                PlayersCacheManager.setRole(target, Roles.LEADER.getName());
                player.sendMessage("§aVocê promoveu o jogador §7" + target + "§a para §7Líder§a!");
                promoteMessage(player, target, "Líder");
            } else if (leader) {
                player.sendMessage("§aO jogador §7" + target + "§a já está em cargo máximo§a!");
            } else {
                player.sendMessage("§aO jogador §7" + target + "§a já está em cargo máximo§a!");
            }
            event.setCancelled(true);
        });

        GuiItem demoteItem = new GuiItem(demote(), event -> {
            if (clan == null) {
                player.sendMessage("§cVocê não pertence a nenhum clan!");
                player.getOpenInventory().close();
                event.setCancelled(true);
                return;
            }

            String playerRole = ClansMethods.getUser(player).getRole();
            String clanOwner = ClansMethods.getClan(clan).getOwner();

            Boolean isOwner = ClansMethods.isOwner(clan, player);
            Boolean isLeader = playerRole.equals(Roles.LEADER.getName());
            Boolean isCaptain = playerRole.equals(Roles.CAPTAIN.getName());

            if (!isOwner && !isLeader && !isCaptain) {
                player.sendMessage("§cVocê não tem permissão para rebaixar membros!");
                player.getOpenInventory().close();
                event.setCancelled(true);
                return;
            }

            if (!ClansCacheManager.isMemberInClan(clan, target)) {
                player.sendMessage("§cO jogador informado não está no clan!");
                player.getOpenInventory().close();
                event.setCancelled(true);
                return;
            }

            String memberRole = PlayersCacheManager.getPlayerDataByName(target).getRole();

            Boolean leader = memberRole.equals(Roles.LEADER.getName());
            Boolean captain = memberRole.equals(Roles.CAPTAIN.getName());
            Boolean reliable = memberRole.equals(Roles.RELIABLE.getName());
            Boolean member = memberRole.equals(Roles.MEMBER.getName());

            if (target.equals(player.getName())) {
                player.sendMessage("§cVocê não pode se rebaixar!");
                player.getOpenInventory().close();
                event.setCancelled(true);
                return;
            }

            if (target.equals(clanOwner)) {
                player.sendMessage("§cVocê não pode gerenciar o dono do clan!");
                player.getOpenInventory().close();
                event.setCancelled(true);
                return;
            }

            int playerRolePriority = Roles.fromName(playerRole).getPriority();
            int memberRolePriority = Roles.fromName(memberRole).getPriority();

            if (playerRolePriority <= memberRolePriority && !isOwner) {
                player.sendMessage("§cVocê não pode rebaixar um membro com cargo maior ou igual ao seu!");
                player.getOpenInventory().close();
                event.setCancelled(true);
                return;
            }

            if (member) {
                player.sendMessage("§aO jogador §7" + target + "§a já está no menor cargo§a!");
            } else if (reliable) {
                PlayersCacheManager.setRole(target, Roles.MEMBER.getName());
                player.sendMessage("§aVocê rebaixou o jogador §7" + target + "§a para §7Membro§a!");
                demoteMessage(player, target, "Membro");
            } else if (captain) {
                PlayersCacheManager.setRole(target, Roles.RELIABLE.getName());
                player.sendMessage("§aVocê rebaixou o jogador §7" + target + "§a para §7Confiável§a!");
                demoteMessage(player, target, "Confiável");
            } else if (leader) {
                PlayersCacheManager.setRole(target, Roles.CAPTAIN.getName());
                player.sendMessage("§aVocê rebaixou o jogador §7" + target + "§a para §7Capitão§a!");
                demoteMessage(player, target, "Capitão");
            } else {
                player.sendMessage("§aO jogador §7" + target + "§a já está no menor cargo§a!");
            }
            event.setCancelled(true);
        });

        GuiItem kickItem = new GuiItem(kick(), event -> {
            if (clan == null) {
                player.sendMessage("§cVocê não pertence a nenhum clan!");
                return;
            }

            String playerRole = ClansMethods.getUser(player).getRole();
            String clanOwner = ClansMethods.getClan(clan).getOwner();

            Boolean isOwner = ClansMethods.isOwner(clan, player);
            Boolean leader = playerRole.equals(Roles.LEADER.getName());
            Boolean captain = playerRole.equals(Roles.CAPTAIN.getName());

            if (!isOwner && !leader && !captain) {
                player.sendMessage("§cVocê não tem permissão para expulsar membros!");
                player.getOpenInventory().close();
                event.setCancelled(true);
                return;
            }

            if (!ClansCacheManager.isMemberInClan(clan, target)) {
                player.sendMessage("§cO jogador informado não está no clan!");
                player.getOpenInventory().close();
                event.setCancelled(true);
                return;
            }

            if (target.equals(player.getName())) {
                player.sendMessage("§cVocê não pode se expulsar!");
                player.getOpenInventory().close();
                event.setCancelled(true);
                return;
            }

            if (target.equals(clanOwner)) {
                player.sendMessage("§cVocê não pode expulsar o dono do clan!");
                player.getOpenInventory().close();
                event.setCancelled(true);
                return;
            }

            String memberRole = PlayersCacheManager.getPlayerDataByName(target).getRole();

            int playerRolePriority = Roles.fromName(playerRole).getPriority();
            int memberRolePriority = Roles.fromName(memberRole).getPriority();

            if (playerRolePriority <= memberRolePriority && !isOwner) {
                player.sendMessage("§cVocê não pode expulsar um membro com cargo maior ou igual ao seu!");
                player.getOpenInventory().close();
                event.setCancelled(true);
                return;
            }

            ClansMethods.leaveClanByName(clan, target);
            player.sendMessage("§aVocê expulsou o jogador §7" + target + "§a do clan!");

            List<String> members = ClansMethods.getMembers(clan);

            for (Player players : Bukkit.getOnlinePlayers()) {
                if (members.contains(players.getName())) {
                    players.sendMessage(
                            TextUtil.parse("<#ff0000> [<#b81414>-<#b81414>] <#ff0000> <#ff0000>O jogador " + target + " foi retirado do clan. <#ec5353>"));
                }
            }

            Player clanMember = Bukkit.getPlayer(target);

            if (clanMember != null) {
                clanMember.sendMessage("§aVocê foi expulso do clan §7" + clan + "§a!");
            }

            player.getOpenInventory().close();
            event.setCancelled(true);
        });

        GuiItem ownerItem = new GuiItem(owner(), event -> {
            if (clan == null) {
                player.sendMessage("§cVocê não pertence a nenhum clan!");
                return;
            }

            Boolean isOwner = ClansMethods.isOwner(clan, player);

            if (!isOwner) {
                player.sendMessage("§cVocê não tem permissão para transferir a liderança!");
                player.getOpenInventory().close();
                event.setCancelled(true);
                return;
            }

            if (!ClansCacheManager.isMemberInClan(clan, target)) {
                player.sendMessage("§cO jogador informado não está no clan!");
                player.getOpenInventory().close();
                event.setCancelled(true);
                return;
            }

            ClansMethods.setOwner(clan, target);
            player.sendMessage("§aVocê transferiu a liderença do seu clan para o jogador §7" + target + "§a com sucesso!");
            player.getOpenInventory().close();

            Player targetPlayer = Bukkit.getPlayer(target);

            if (targetPlayer != null) {
                targetPlayer.sendMessage("§aVocê agora é o novo dono do clan" + clan);
            }

            ownerMessage(player, target);

            event.setCancelled(true);
        });

        GuiItem backItem = new GuiItem(back(), event -> {
            PlayersData data = ClansMethods.getUser(player);

            if (data.getClan() == null) {
                player.getOpenInventory().close();
                event.setCancelled(true);
                return;
            }

            MembersManageInventory.open(player, 0);
            event.setCancelled(true);
        });

        pane.addItem(promoteItem, Slot.fromIndex(11));
        pane.addItem(demoteItem, Slot.fromIndex(12));
        pane.addItem(kickItem, Slot.fromIndex(14));
        pane.addItem(ownerItem, Slot.fromIndex(15));
        pane.addItem(backItem, Slot.fromIndex(18));

        inventory.addPane(pane);

        player.playSound(player.getLocation(), Sound.BLOCK_CHEST_OPEN, 0.5f, 0.5f);

        inventory.show(player);
    }

    public static void ownerMessage(Player player, String target) {
        String clan = ClansMethods.getUserClan(player);

        if (clan != null) {
            List<String> members = ClansMethods.getMembers(clan);

            for (Player players : Bukkit.getOnlinePlayers()) {
                if (members.contains(players.getName())) {
                    players.sendMessage(
                            TextUtil.parse("<#469536> [<#469536>><#469536>] <#469536> <#469536>O jogador " + target + " é o novo dono do clan! <#55ff55>")
                    );
                }
            }
        }
    }

    public static void promoteMessage(Player player, String target, String role) {
        String clan = ClansMethods.getUserClan(player);

        if (clan != null) {
            List<String> members = ClansMethods.getMembers(clan);

            for (Player players : Bukkit.getOnlinePlayers()) {
                if (members.contains(players.getName())) {
                    players.sendMessage(
                            TextUtil.parse("<#469536> [<#469536>><#469536>] <#469536> <#469536>O jogador " + target + " foi promovido para " + role + " <#55ff55>")
                    );
                }
            }
        }
    }

    public static void demoteMessage(Player player, String target, String role) {
        String clan = ClansMethods.getUserClan(player);

        if (clan != null) {
            List<String> members = ClansMethods.getMembers(clan);

            for (Player players : Bukkit.getOnlinePlayers()) {
                if (members.contains(players.getName())) {
                    players.sendMessage(
                            TextUtil.parse("<#ff0000> [<#b81414><<#b81414>] <#ff0000> <#ff0000>O jogador " + target + " foi rebaixado para " + role + " <#ec5353>")
                    );
                }
            }
        }
    }

    private static ItemStack promote() {

        String value = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYjIyMWRhNDQxOGJkM2JmYjQyZWI2NGQyYWI0MjljNjFkZWNiOGY0YmY3ZDRjZmI3N2ExNjJiZTNkY2IwYjkyNyJ9fX0=";
        ItemStack skull = SkullAPI.fromBase64(SkullAPI.Type.BLOCK, value);

        ItemMeta meta = skull.getItemMeta();
        meta.setDisplayName("§aPromover");
        meta.setLore(Arrays.asList(
                "§7Promova o membro do clan!",
                "",
                "§aClique para promover."
        ));
        skull.setItemMeta(meta);

        return skull;
    }

    private static ItemStack demote() {

        String value = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYTM4NTJiZjYxNmYzMWVkNjdjMzdkZTRiMGJhYTJjNWY4ZDhmY2E4MmU3MmRiY2FmY2JhNjY5NTZhODFjNCJ9fX0=";
        ItemStack skull = SkullAPI.fromBase64(SkullAPI.Type.BLOCK, value);

        ItemMeta meta = skull.getItemMeta();
        meta.setDisplayName("§cRebaixar");
        meta.setLore(Arrays.asList(
                "§7Rebaixe o membro do clan!",
                "",
                "§cClique para rebaixar."
        ));
        skull.setItemMeta(meta);

        return skull;
    }

    private static ItemStack kick() {

        String value = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNGU0YjhiOGQyMzYyYzg2NGUwNjIzMDE0ODdkOTRkMzI3MmE2YjU3MGFmYmY4MGMyYzViMTQ4Yzk1NDU3OWQ0NiJ9fX0=";
        ItemStack skull = SkullAPI.fromBase64(SkullAPI.Type.BLOCK, value);

        ItemMeta meta = skull.getItemMeta();
        meta.setDisplayName("§cExpulsar");
        meta.setLore(Arrays.asList(
                "§7Expulse o membro do clan!",
                "",
                "§cClique para expulsar."
        ));
        skull.setItemMeta(meta);

        return skull;
    }

    private static ItemStack owner() {

        String value = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYmQyM2RiZWVmNjY2MTVhMWNhMzhmZmUyYjc0NTY3ZDk2YTcwNmEzNWE3MTFmMzU1N2M4ZTE3ZWM1ZWRlODA4MSJ9fX0=";
        ItemStack skull = SkullAPI.fromBase64(SkullAPI.Type.BLOCK, value);

        ItemMeta meta = skull.getItemMeta();
        meta.setDisplayName("§6Transferir Liderança");
        meta.setLore(Arrays.asList(
                "§7Transfira a liderança do",
                "§7clan para esse membro!",
                "",
                "§6Clique para transferir."
        ));
        skull.setItemMeta(meta);

        return skull;
    }

    public static ItemStack back() {
        ItemStack item = new ItemStack(Material.RED_DYE);

        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName("§cVoltar");
        meta.setLore(Arrays.asList(
                "§7Volte!"
        ));
        item.setItemMeta(meta);

        return item;
    }

}
