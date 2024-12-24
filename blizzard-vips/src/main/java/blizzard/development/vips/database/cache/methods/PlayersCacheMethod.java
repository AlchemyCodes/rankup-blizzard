package blizzard.development.vips.database.cache.methods;

import blizzard.development.vips.database.cache.PlayersCacheManager;
import blizzard.development.vips.database.storage.PlayersData;
import blizzard.development.vips.utils.RandomIdGenerator;
import org.bukkit.entity.Player;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class PlayersCacheMethod {
    private static PlayersCacheMethod instance;
    
    private final PlayersCacheManager cache = PlayersCacheManager.getInstance();

//    public int getId(Player player) {
//        PlayersData data = cache.getPlayerData(player);
//        return data.getId();
//    }
//
//    public String getNickname(Player player) {
//        PlayersData data = cache.getPlayerData(player);
//        return (data != null) ? data.getNickname() : "";
//    }
//
//    public String getVipActivationDate(Player player) {
//        PlayersData data = cache.getPlayerData(player);
//        return (data != null) ? data.getVipActivationDate() : "Sem Vip";
//    }
//
    public String getDate() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy 'às' HH:mm");

        return now.format(formatter);
    }
//
//    public void setVipActivationDate(Player player) {
//        PlayersData data = cache.getPlayerData(player);
//
//        if (data != null) {
//            data.setVipActivationDate(getDate());
//            cache.cachePlayerData(player.getName(), data);
//        }
//    }
//
//    public String getVipId(Player player) {
//        PlayersData data = cache.getPlayerData(player);
//        return (data != null) ? data.getVipId() : "Sem Vip";
//    }
//
//    public void setVipId(Player player) {
//        PlayersData data = cache.getPlayerData(player);
//
//        if (data != null) {
//            data.setVipId(RandomIdGenerator.generateVipId());
//            cache.cachePlayerData(player.getName(), data);
//        }
//    }
//
//    public String getVipName(Player player) {
//        PlayersData data = cache.getPlayerData(player);
//        return (data != null) ? data.getVipName() : "Sem Vip";
//    }
//
//    public void setVipName(Player player, String vipName) {
//        PlayersData data = cache.getPlayerData(player);
//        if (data != null) {
//            data.setVipName(vipName);
//            cache.cachePlayerData(player.getName(), data);
//        }
//    }
//
//    public long getVipDuration(Player player) {
//        PlayersData data = cache.getPlayerData(player);
//        return (data != null) ? data.getVipDuration() : 0L;
//    }
//
//    public void setDuration(Player player, long duration) {
//        PlayersData data = cache.getPlayerData(player);
//        if (data != null) {
//            data.setVipDuration(duration);
//            cache.cachePlayerData(player.getName(), data);
//        }
//    }
//
//    public void removeVip(Player player) {
//        PlayersData data = cache.getPlayerData(player);
//        if (data != null) {
//            data.setVipName("");
//            data.setVipId("");
//            data.setVipActivationDate("");
//            data.setVipDuration(0);
//            cache.cachePlayerData(player.getName(), data);
//        }
//    }
//
//    public boolean hasVip(Player player) {
//        return getVipId(player) != null;
//    }
//
//    public boolean hasSpecificVip(Player player, String vipName) {
//        return getVipName(player) != null && getVipName(player).equalsIgnoreCase(vipName);
//    }

    public static PlayersCacheMethod getInstance() {
        if (instance == null) {
            instance = new PlayersCacheMethod();
        }
        return instance;
    }


}
