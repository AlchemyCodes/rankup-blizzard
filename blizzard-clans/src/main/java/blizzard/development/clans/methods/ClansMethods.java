package blizzard.development.clans.methods;

import org.bukkit.entity.Player;
import blizzard.development.clans.database.cache.ClansCacheManager;
import blizzard.development.clans.database.cache.PlayersCacheManager;
import blizzard.development.clans.database.dao.ClansDAO;
import blizzard.development.clans.database.dao.PlayersDAO;
import blizzard.development.clans.database.storage.ClansData;
import blizzard.development.clans.database.storage.PlayersData;
import blizzard.development.clans.enums.Roles;
import blizzard.development.clans.utils.PluginImpl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ClansMethods {

    private static final ClansDAO clansDAO = new ClansDAO();
    private static final PlayersDAO playersDAO = new PlayersDAO();

    // Getters

    public static ClansData getClan(String clan) {
        return ClansCacheManager.getClansData(clan);
    }

    public static PlayersData getUser(Player player) {
        return PlayersCacheManager.getPlayerData(player);
    }

    public static String getUserClan(Player player) {
        return PlayersCacheManager.getPlayerData(player).getClan();
    }

    public static String getUserClanByName(String player) {
        return PlayersCacheManager.getPlayerDataByName(player).getClan();
    }

    public static List<String> getMembers(String clan) {
        return ClansCacheManager.getMembers(clan);
    }

    public static List<String> getInvites(String player) {
        return PlayersCacheManager.getInvitesByName(player);
    }

    public static int getMembersCount(String clan) {
        return ClansCacheManager.getMembersCount(clan);
    }

    public static int getInvitesCount(String player) {
        return PlayersCacheManager.getInviteCount(player);
    }

    public static String getRoleEmojiByName(Player player) {

        String role = PlayersCacheManager.getPlayerData(player).getRole();

        if (role.equals(Roles.LEADER.getName())) {
            return "§6[" + Roles.LEADER.getEmoji() + "]";
        } else if (role.equals(Roles.CAPTAIN.getName())) {
            return "§2[" + Roles.CAPTAIN.getEmoji() + "]";
        } else if (role.equals(Roles.RELIABLE.getName())) {
            return "§b[" + Roles.RELIABLE.getEmoji() + "]";
        } else if (role.equals(Roles.MEMBER.getName())) {
            return "§7[" + Roles.MEMBER.getEmoji() + "]";
        } else {
            return null;
        }
    }

    public static String getRoleEmojiByNameWithPlayerName(String player) {

        String role = PlayersCacheManager.getPlayerDataByName(player).getRole();

        if (role.equals(Roles.LEADER.getName())) {
            return "§6[" + Roles.LEADER.getEmoji() + "]";
        } else if (role.equals(Roles.CAPTAIN.getName())) {
            return "§2[" + Roles.CAPTAIN.getEmoji() + "]";
        } else if (role.equals(Roles.RELIABLE.getName())) {
            return "§b[" + Roles.RELIABLE.getEmoji() + "]";
        } else if (role.equals(Roles.MEMBER.getName())) {
            return "§7[" + Roles.MEMBER.getEmoji() + "]";
        } else {
            return null;
        }
    }

    public static void setOwner(String clan, String owner) {
        ClansData clanData = ClansCacheManager.getClansData(clan);
        PlayersData playerData = PlayersCacheManager.getPlayerDataByName(owner);
        if (clanData != null && playerData != null) {
            clanData.setOwner(owner);
            playerData.setRole(Roles.LEADER.getName());
            ClansCacheManager.cacheClansData(clan, clanData);
            PlayersCacheManager.cachePlayerDataByName(owner, playerData);
        }
    }

    // Clans

    public static void depositMoney(String clan, long money) {
        ClansCacheManager.addMoney(clan, money);
    }

    public static void withdrawMoney(String clan, long money) {
        ClansCacheManager.removeMoney(clan, money);
    }

    public static boolean isClanTagAvailable(String tag) {
        return ClansCacheManager.isTagAvaible(tag);
    }

    public static boolean isClanNameAvailable(String name) {
        return ClansCacheManager.isNameAvaible(name);
    }

    public static boolean isClanTagBlacklisted(String tag) {
        ArrayList<String> tags = new ArrayList<>(PluginImpl.getInstance().Config.getConfig().getStringList("clans.tags-blacklist"));

        if (tags.contains(tag)) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean isClanNameBlacklisted(String name) {
        ArrayList<String> names = new ArrayList<>(PluginImpl.getInstance().Config.getConfig().getStringList("clans.names-blacklist"));

        if (names.contains(name)) {
            return true;
        } else {
            return false;
        }
    }

    public static void createClan(Player owner, String tag, String name) {
        ClansCacheManager.createClan(owner, tag, name);
        PlayersCacheManager.setClan(owner, tag);
        PlayersCacheManager.setRole(owner.getName(), Roles.LEADER.getName());
    }

    public static void deleteClan(String clan) {
        List<String> members = clansDAO.getMembers(clan);
        for (String member : members) {
            PlayersData data = PlayersCacheManager.getPlayerDataByName(member);
            if (data != null) {
                data.setClan(null);
                data.setRole(null);
                try {
                    playersDAO.updatePlayerData(data);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        ClansCacheManager.deleteClan(clan);
    }

    public static Boolean isOwner(String clan, Player player) {
        return ClansCacheManager.isOwner(clan, player.getName());
    }


    public static boolean isClanTagUnique(String tag) {
        List<String> existingTags = ClansCacheManager.getAllClanTags();
        return existingTags.stream().noneMatch(existingTag -> existingTag.equalsIgnoreCase(tag));
    }

    public static boolean isClanNameUnique(String name) {
        List<String> existingNames = ClansCacheManager.getAllClanNames();
        return existingNames.stream().noneMatch(existingName -> existingName.equalsIgnoreCase(name));
    }


    public static void sendInviteByName(String clan, String player) {
        PlayersCacheManager.addInviteByName(player, clan);
    }

    public static void removeInviteByName(String clan, String player){
        PlayersCacheManager.removeInviteByName(player, clan);
    }

    public static boolean hasInviteByName(String clan, String player) {
        List<String> invites = PlayersCacheManager.getInvitesByName(player);
        return invites != null && invites.contains(clan);
    }

    public static void joinClan(String clan, Player player) {
        if (clansDAO.findClansData(clan) != null) {
            ClansCacheManager.addMemberToClan(clan, player.getName());
            PlayersCacheManager.setClan(player, clan);
            PlayersCacheManager.setRole(player.getName(), Roles.MEMBER.getName());
        }
    }

    public static void joinClanIfStaff(String clan, Player player) {
        if (clansDAO.findClansData(clan) != null) {
            ClansCacheManager.addMemberToClan(clan, player.getName());
            PlayersCacheManager.setClan(player, clan);
            PlayersCacheManager.setRole(player.getName(), Roles.LEADER.getName());
        }
    }

    public static void leaveClan(String clan, Player player) {
        if (ClansCacheManager.isMemberInClan(clan, player.getName())) {
            ClansCacheManager.removeMemberFromClan(clan, player.getName());
            PlayersCacheManager.unSetClan(player);
            PlayersCacheManager.unSetRole(player);
        }
    }

    public static void leaveClanByName(String clan, String player) {
        if (ClansCacheManager.isMemberInClan(clan, player)) {
            ClansCacheManager.removeMemberFromClan(clan, player);
            PlayersCacheManager.unSetClanByName(player);
            PlayersCacheManager.unSetRoleByName(player);
        }
    }
}
