package blizzard.development.vips.database.cache.methods;

import blizzard.development.vips.database.cache.PlayersCacheManager;
import blizzard.development.vips.database.storage.PlayersData;
import blizzard.development.vips.utils.RandomIdGenerator;
import blizzard.development.vips.utils.vips.VipUtils;

public class PlayersCacheMethod {
    private static PlayersCacheMethod instance;
    
    private final PlayersCacheManager cache = PlayersCacheManager.getInstance();

    public int getId(String vipId) {
        PlayersData data = cache.getPlayerData(vipId);
        return data.getId();
    }

    public String getNickname(String vipId) {
        PlayersData data = cache.getPlayerData(vipId);
        return (data != null) ? data.getNickname() : "";
    }

    public String getVipActivationDate(String vipId) {
        PlayersData data = cache.getPlayerData(vipId);
        return (data != null) ? data.getVipActivationDate() : "Sem Vip";
    }


    public void setVipActivationDate(String vipId) {
        PlayersData data = cache.getPlayerData(vipId);

        if (data != null) {
            data.setVipActivationDate(VipUtils.getInstance().getDate());
            cache.cachePlayerData(vipId, data);
        }
    }

    public String getVipId(String vipId) {
        PlayersData data = cache.getPlayerData(vipId);
        return (data != null) ? data.getVipId() : "Sem Vip";
    }

    public void setVipId(String vipId) {
        PlayersData data = cache.getPlayerData(vipId);

        if (data != null) {
            data.setVipId(RandomIdGenerator.generateVipId());
            cache.cachePlayerData(vipId, data);
        }
    }

    public String getVipName(String vipId) {
        PlayersData data = cache.getPlayerData(vipId);
        return (data != null) ? data.getVipName() : "Sem Vip";
    }

    public void setVipName(String vipId, String vipName) {
        PlayersData data = cache.getPlayerData(vipId);
        if (data != null) {
            data.setVipName(vipName);
            cache.cachePlayerData(vipId, data);
        }
    }

    public long getVipDuration(String vipId) {
        PlayersData data = cache.getPlayerData(vipId);
        return (data != null) ? data.getVipDuration() : 0L;
    }

    public void setVipDuration(String vipId, long duration) {
        PlayersData data = cache.getPlayerData(vipId);
        if (data != null) {
            data.setVipDuration(duration);
            cache.cachePlayerData(vipId, data);
        }
    }

    public void removeVip(String vipId) {
        PlayersData data = cache.getPlayerData(vipId);
        if (data != null) {
            data.setVipName("");
            data.setVipId("");
            data.setVipActivationDate("");
            data.setVipDuration(0);
            cache.cachePlayerData(vipId, data);
        }
    }

    public boolean hasVip(String vipId) {
        return getVipId(vipId) != null;
    }

    public boolean hasSpecificVip(String vipId, String vipName) {
        return getVipName(vipId) != null && getVipName(vipId).equalsIgnoreCase(vipName);
    }

    public static PlayersCacheMethod getInstance() {
        if (instance == null) {
            instance = new PlayersCacheMethod();
        }
        return instance;
    }


}
