package blizzard.development.clans.database.cache;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import blizzard.development.clans.database.dao.ClansDAO;
import blizzard.development.clans.database.storage.ClansData;
import blizzard.development.clans.utils.LocationUtil;
import blizzard.development.clans.utils.PluginImpl;


import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class ClansCacheManager {
    public static final ConcurrentHashMap<String, ClansData> clansCache = new ConcurrentHashMap<>();
    private static final ClansDAO clansDAO = new ClansDAO();

    public static void cacheClansData(String clan, ClansData clansData) {
        clansCache.put(clan, clansData);
    }

    public static ClansData getClansData(String clan) {
        return clansCache.get(clan);
    }

    public static List<ClansData> getAllClans() {
        return new ArrayList<>(clansCache.values());
    }

    public static List<ClansData> getAllClansByMoney() {
        List<ClansData> clans = new ArrayList<>(clansCache.values());
        clans.sort(Comparator.comparingLong(ClansData::getMoney).reversed());
        return clans;
    }

    public static List<ClansData> getAllClansByMembers() {
        List<ClansData> clans = new ArrayList<>(clansCache.values());
        clans.sort((clan1, clan2) -> {
            int membersCount1 = getMembersCount(clan1.getClan());
            int membersCount2 = getMembersCount(clan2.getClan());
            return Integer.compare(membersCount2, membersCount1);
        });
        return clans;
    }



    public static void removeClansData(String clan) {
        clansCache.remove(clan);
    }

    public static void createClan(Player owner, String tag, String name) {
        ClansData data = clansCache.get(tag);
        if (data == null) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yy");
            dateFormat.setTimeZone(TimeZone.getTimeZone("America/Sao_Paulo"));
            String creationDate = dateFormat.format(new Date());
            data = new ClansData(tag, owner.getName(), tag, name, owner.getName(), 20, 0, false, 0.0, null, null,
                    creationDate);
            cacheClansData(tag, data);
            try {
                clansDAO.createClansData(data);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }


    public static void deleteClan(String clan) {
        removeClansData(clan);
        try {
            clansDAO.deleteClan(clan);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static List<String> getMembers(String clan) {
        ClansData data = clansCache.get(clan);
        if (data != null) {
            String members = data.getMembers();
            if (members != null && !members.isEmpty()) {
                return Arrays.asList(members.split(","));
            }
        }
        return clansDAO.getMembers(clan);
    }

    public static void addMemberToClan(String clan, String member) {
        ClansData data = clansCache.get(clan);
        if (data != null) {
            List<String> membersList = new ArrayList<>(Arrays.asList(data.getMembers().split(",")));
            if (!membersList.contains(member)) {
                membersList.add(member);
                String updatedMembers = String.join(",", membersList);

                data.setMembers(updatedMembers);
                cacheClansData(clan, data);
            }
        }
    }

    public static void removeMemberFromClan(String clan, String member) {
        ClansData data = clansCache.get(clan);
        if (data != null) {
            List<String> membersList = new ArrayList<>(Arrays.asList(data.getMembers().split(",")));
            if (membersList.contains(member)) {
                membersList.remove(member);
                String updatedMembers = String.join(",", membersList);

                data.setMembers(updatedMembers);
                cacheClansData(clan, data);
            }
        }
    }

    public static boolean isMemberInClan(String clan, String member) {
        ClansData data = clansCache.get(clan);

        if (data != null) {
            String members = data.getMembers();
            if (members != null && !members.isEmpty()) {
                List<String> membersList = Arrays.asList(members.split(","));
                return membersList.contains(member);
            }
            return false;
        }

        return clansDAO.isMemberInClan(clan, member);
    }

    public static int getMembersCount(String clan) {
        ClansData data = clansCache.get(clan);
        if (data != null) {
            String members = data.getMembers();
            if (members != null && !members.isEmpty()) {
                return members.split(",").length;
            }
        }
        return 0;
    }

    public static int getMaxClanMembers(String clan) {
        ClansData data = clansCache.get(clan);
        if (data != null) {
            return data.getMax();
        }
        return 0;
    }

    public static void addMaxClanMembers(String clan, int quantity) {
        ClansData data = clansCache.get(clan);
        if (data != null) {
            int before = data.getMax();
            int after = before + quantity;

            int max = PluginImpl.getInstance().Config.getInt("clans.max-members");

            if (after > max) {
                return;
            }

            data.setMax(after);
            cacheClansData(clan, data);
        }
    }

    public static void addMoney(String clan, long money) {
        ClansData data = clansCache.get(clan);
        if (data != null) {
            long currentMoney = data.getMoney();
            long newMoney = currentMoney + money;

            data.setMoney(newMoney);
            cacheClansData(clan, data);
        }
    }

    public static void removeMoney(String clan, long money) {
        ClansData data = clansCache.get(clan);
        if (data != null) {
            long currentMoney = data.getMoney();
            long newMoney = currentMoney - money;

            data.setMoney(newMoney);
            cacheClansData(clan, data);
        }
    }

    public static void setFriendlyFireState(String clan, boolean state) {
        ClansData data = clansCache.get(clan);
        if (data != null) {
            data.setFriendlyfire(state);
            cacheClansData(clan, data);
        }
    }

    public static Location getClanBase(String clan) {
        ClansData data = clansCache.get(clan);
        if (data != null) {
            String serializedLocation = data.getBase();

            if (serializedLocation == null) {
                return null;
            }

            Location location = LocationUtil.deserializeLocation(serializedLocation);
            return location;
        }
        return null;
    }

    public static void setClanBase(String clan, Location location) {
        ClansData data = clansCache.get(clan);
        if (data != null) {
            String serializedLocation = LocationUtil.getSerializedLocation(location);

            data.setBase(serializedLocation);
            cacheClansData(clan, data);
        }
    }

    public static void unSetClanBase(String clan) {
        ClansData data = clansCache.get(clan);
        if (data != null) {
            data.setBase(null);
            cacheClansData(clan, data);
        }
    }

//    public static List<String> getAllies(String clan) {
//        ClansData data = clansCache.get(clan);
//        if (data != null) {
//            String allies = data.getAllies();
//            if (allies != null && !allies.isEmpty()) {
//                return Arrays.asList(allies.split(","));
//            }
//        }
//        return clansDAO.getMembers(clan);
//    }
//
//    public static void addAllyToClan(String clan, String ally) {
//        ClansData data = clansCache.get(clan);
//        if (data != null) {
//            List<String> alliesList = new ArrayList<>(Arrays.asList(data.getAllies().split(",")));
//            if (!alliesList.contains(ally)) {
//                alliesList.add(ally);
//                String updatedMembers = String.join(",", alliesList);
//
//                data.setAllies(updatedMembers);
//                cacheClansData(clan, data);
//            }
//        }
//    }

    public static void setClanName(String clan, String newName) {
        ClansData data = getClansData(clan);
        if (data != null) {
            data.setName(newName);
            cacheClansData(clan, data);
        }
    }

    public static void setClanTag(String clan, String newTag) {
        ClansData data = getClansData(clan);
        if (data != null) {
            data.setTag(newTag);
            cacheClansData(clan, data);
        }
    }

    public static boolean isTagAvaible(String tag) {
        for (ClansData data : clansCache.values()) {
            if (data.getTag().equalsIgnoreCase(tag)) {
                return false;
            }
        }

        return !clansDAO.clanTagExists(tag);
    }

    public static boolean isNameAvaible(String name) {
        for (ClansData data : clansCache.values()) {
            if (data.getName().equalsIgnoreCase(name)) {
                return false;
            }
        }

        return !clansDAO.clanTagExists(name);
    }

    public static List<String> getAllClanTags() {
        List<String> tags = new ArrayList<>();
        for (ClansData data : clansCache.values()) {
            tags.add(data.getTag());
        }

        if (tags.isEmpty()) {
            List<ClansData> clans = clansDAO.getAllClans();
            for (ClansData data : clans) {
                tags.add(data.getTag());
            }
        }

        return tags;
    }

    public static List<String> getAllClanNames() {
        List<String> names = new ArrayList<>();
        for (ClansData data : clansCache.values()) {
            names.add(data.getName());
        }

        if (names.isEmpty()) {
            List<ClansData> clans = clansDAO.getAllClans();
            for (ClansData data : clans) {
                names.add(data.getName());
            }
        }

        return names;
    }

    public static boolean isOwner(String clan, String member) {
        ClansData data = clansCache.get(clan);

        if (data != null) {
            return data.getOwner().equals(member);
        }

        ClansData daoData = clansDAO.findClansData(clan);
        return daoData != null && daoData.getOwner().equals(member);
    }

    public static double getKdr(String clan) {
        ClansData data = getClansData(clan);
        if (data != null) {
            DecimalFormat decimalFormat = new DecimalFormat("#.##");
            return Double.parseDouble(decimalFormat.format(data.getKdr()));
        }
        return 0.0;
    }


    public static void setKdr(String clan, double kdr) {
        ClansData data = getClansData(clan);
        if (data != null) {
            data.setKdr(kdr);
            clansCache.put(clan, data);
        }
    }

}

