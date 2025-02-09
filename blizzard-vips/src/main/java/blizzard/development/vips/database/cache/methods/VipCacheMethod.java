package blizzard.development.vips.database.cache.methods;

import blizzard.development.vips.database.cache.VipCacheManager;
import blizzard.development.vips.database.storage.VipData;

public class VipCacheMethod {
    private static VipCacheMethod instance;

    private final VipCacheManager vipCache = VipCacheManager.getInstance();

    public String getNickname(String vipId) {
        VipData data = vipCache.getVipData(vipId);
        return (data != null) ? data.getNickname() : "";
    }

    public String getVipId(String vipId) {
        VipData data = vipCache.getVipData(vipId);
        return (data != null) ? data.getVipId() : "";
    }

    public String getVipName(String vipId) {
        VipData data = vipCache.getVipData(vipId);
        return (data != null) ? data.getVipName() : "";
    }

    public long getVipDuration(String vipId) {
        VipData data = vipCache.getVipData(vipId);
        return (data != null) ? data.getVipDuration() : 0;
    }

    public String getVipActivationDate(String vipId) {
        VipData data = vipCache.getVipData(vipId);
        return (data != null) ? data.getVipActivationDate() : "";
    }

    public void setVipName(String vipId, long duration) {
        VipData data = vipCache.getVipData(vipId);
        if (data != null) {
            data.setVipDuration(duration);
            vipCache.cacheVipData(vipId, data);
        }
    }

    public void setVipDuration(String vipId, long duration) {
        VipData data = vipCache.getVipData(vipId);
        if (data != null) {
            data.setVipDuration(duration);
            vipCache.cacheVipData(vipId, data);
        }
    }

    public void setVipActivationDate(String vipId, long duration) {
        VipData data = vipCache.getVipData(vipId);
        if (data != null) {
            data.setVipDuration(duration);
            vipCache.cacheVipData(vipId, data);
        }
    }


    public static VipCacheMethod getInstance() {
        if (instance == null) {
            instance = new VipCacheMethod();
        }
        return instance;
    }
}