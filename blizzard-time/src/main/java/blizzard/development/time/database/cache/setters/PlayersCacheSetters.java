package blizzard.development.time.database.cache.setters;

import blizzard.development.time.database.cache.managers.PlayersCacheManager;
import blizzard.development.time.database.storage.PlayersData;
import org.bukkit.entity.Player;

import java.util.List;

public class PlayersCacheSetters {
    private static PlayersCacheSetters instance;

    final private PlayersCacheManager cache = PlayersCacheManager.getInstance();

    public void setPlayTime(Player player, long time) {
        PlayersData data = cache.getPlayerData(player);
        if (data != null) {
            long playTime = data.getPlayTime();
            data.setPlayTime(time);
            cache.cachePlayerData(player, data);
        }
    }

    public void addPlayTime(Player player, long time) {
        PlayersData data = cache.getPlayerData(player);
        if (data != null) {
            long playTime = data.getPlayTime();
            data.setPlayTime(playTime + time);
            cache.cachePlayerData(player, data);
        }
    }

    public void removePlayTime(Player player, long time) {
        PlayersData data = cache.getPlayerData(player);
        if (data != null) {
            long playTime = data.getPlayTime();
            data.setPlayTime(playTime - time);
            cache.cachePlayerData(player, data);
        }
    }

    public void setCompletedMissions(Player player, List<String> missions) {
        PlayersData data = cache.getPlayerData(player);
        if (data != null) {
            data.setCompletedMissions(missions);
            cache.cachePlayerData(player, data);
        }
    }

    public void addCompletedMission(Player player, List<String> mission) {
        PlayersData data = cache.getPlayerData(player);
        if (data != null) {
            List<String> completedMissions = data.getCompletedMissions();
            completedMissions.addAll(mission);
            data.setCompletedMissions(completedMissions);
            cache.cachePlayerData(player, data);
        }
    }

    public void removeCompletedMission(Player player, List<String> mission) {
        PlayersData data = cache.getPlayerData(player);
        if (data != null) {
            List<String> completedMissions = data.getCompletedMissions();
            completedMissions.removeAll(mission);
            data.setCompletedMissions(completedMissions);
            cache.cachePlayerData(player, data);
        }
    }

    public void setNotifiedMissions(Player player, List<String> missions) {
        PlayersData data = cache.getPlayerData(player);
        if (data != null) {
            data.setNotifiedMissions(missions);
            cache.cachePlayerData(player, data);
        }
    }

    public void addNotifiedMission(Player player, List<String> mission) {
        PlayersData data = cache.getPlayerData(player);
        if (data != null) {
            List<String> notifiedMissions = data.getNotifiedMissions();
            notifiedMissions.addAll(mission);
            data.setNotifiedMissions(notifiedMissions);
            cache.cachePlayerData(player, data);
        }
    }

    public void removeNotifiedMission(Player player, List<String> mission) {
        PlayersData data = cache.getPlayerData(player);
        if (data != null) {
            List<String> notifiedMissions = data.getNotifiedMissions();
            notifiedMissions.removeAll(mission);
            data.setNotifiedMissions(notifiedMissions);
            cache.cachePlayerData(player, data);
        }
    }

    public static PlayersCacheSetters getInstance() {
        if (instance == null) instance = new PlayersCacheSetters();
        return instance;
    }
}
