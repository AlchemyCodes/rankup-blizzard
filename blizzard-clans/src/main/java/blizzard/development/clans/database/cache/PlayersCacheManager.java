package blizzard.development.clans.database.cache;

import org.bukkit.entity.Player;
import blizzard.development.clans.database.dao.PlayersDAO;
import blizzard.development.clans.database.storage.PlayersData;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class PlayersCacheManager {
    public static final ConcurrentHashMap<String, PlayersData> playersCache = new ConcurrentHashMap<>();
    private static final PlayersDAO playersDAO = new PlayersDAO();

    public static PlayersData getPlayerData(Player player) {
        return getPlayerDataByName(player.getName());
    }

    public static void cachePlayerData(Player player, PlayersData playerData) {
        playersCache.put(player.getName(), playerData);
    }

    public static void cachePlayerDataByName(String player, PlayersData playerData) {
        playersCache.put(player, playerData);
    }

    public static PlayersData getPlayerDataByName(String playerName) {
        PlayersData data = playersCache.get(playerName);

        if (data == null) {
            data = playersDAO.findPlayerDataByName(playerName);
            if (data != null) {
                playersCache.put(playerName, data);
            }
        }

        return data;
    }

    public static void addInviteByName(String player, String clan) {
        PlayersData data = getPlayerDataByName(player);
        if (data != null) {
            String invites = data.getInvites();
            List<String> invitesList = invites != null && !invites.isEmpty() ? new ArrayList<>(Arrays.asList(invites.split(","))) : new ArrayList<>();
            if (!invitesList.contains(clan)) {
                invitesList.add(clan);
                data.setInvites(String.join(",", invitesList));
                playersCache.put(player, data);

            }
        }
    }

    public static void removeInviteByName(String player, String clan) {
        PlayersData data = getPlayerDataByName(player);
        if (data != null) {
            String invites = data.getInvites();
            if (invites != null && !invites.isEmpty()) {
                List<String> invitesList = new ArrayList<>(Arrays.asList(invites.split(",")));
                if (invitesList.contains(clan)) {
                    invitesList.remove(clan);
                    if (invitesList.isEmpty()) {
                        data.setInvites(null);
                    } else {
                        data.setInvites(String.join(",", invitesList));
                    }
                    playersCache.put(player, data);

                }
            }
        }
    }


    public static List<String> getInvitesByName(String playerName) {
        PlayersData data = getPlayerDataByName(playerName);
        if (data != null) {
            String invites = data.getInvites();
            if (invites != null && !invites.isEmpty()) {
                return Arrays.asList(invites.split(","));
            }
        }
        return new ArrayList<>();
    }

    public static int getInviteCount(String playerName) {
        PlayersData data = getPlayerDataByName(playerName);
        if (data != null) {
            String invites = data.getInvites();
            if (invites != null && !invites.isEmpty()) {
                return invites.split(",").length;
            }
        }
        return 0;
    }

    public static void setClan(Player player, String clan) {
        PlayersData data = getPlayerData(player);
        if (data != null) {
            data.setClan(clan);
            playersCache.put(player.getName(), data);

        }
    }

    public static void unSetClan(Player player) {
        PlayersData data = getPlayerData(player);
        if (data != null) {
            data.setClan(null);
            playersCache.put(player.getName(), data);

        }
    }

    public static void unSetClanByName(String player) {
        PlayersData data = getPlayerDataByName(player);
        if (data != null) {
            data.setClan(null);
            playersCache.put(player, data);

        }
    }

    public static void setRole(String player, String role) {
        PlayersData data = getPlayerDataByName(player);
        if (data != null) {
            data.setRole(role);
            playersCache.put(player, data);

        }
    }

    public static void unSetRole(Player player) {
        PlayersData data = getPlayerData(player);
        if (data != null) {
            data.setRole(null);
            playersCache.put(player.getName(), data);

        }
    }

    public static void unSetRoleByName(String player) {
        PlayersData data = getPlayerDataByName(player);
        if (data != null) {
            data.setRole(null);
            playersCache.put(player, data);
        }
    }

    public static int getKills(String player) {
        PlayersData data = getPlayerDataByName(player);
        if (data != null) {
            return data.getKills();
        }
        return 0;
    }

    public static int getDeaths(String player) {
        PlayersData data = getPlayerDataByName(player);
        if (data != null) {
            return data.getDeaths();
        }
        return 0;
    }

    public static void addKills(String player, int newKills) {
        PlayersData data = getPlayerDataByName(player);
        if (data != null) {
            int kills = data.getKills();
            data.setKills(kills + newKills);
            playersCache.put(player, data);
        }
    }

    public static void addDeaths(String player, int newDeaths) {
        PlayersData data = getPlayerDataByName(player);
        if (data != null) {
            int deaths = data.getDeaths();
            data.setDeaths(deaths + newDeaths);
            playersCache.put(player, data);
        }
    }

    public static double getKDR(String player) {
        PlayersData data = getPlayerDataByName(player);
        if (data != null) {
            int kills = data.getKills();
            int deaths = data.getDeaths();

            DecimalFormat decimalFormat = new DecimalFormat("#.##");

            if (deaths == 0) {
                return Double.parseDouble(decimalFormat.format(kills > 0 ? kills : 0.0));
            }

            return Double.parseDouble(decimalFormat.format((double) kills / deaths));
        }
        return 0.0;
    }
}