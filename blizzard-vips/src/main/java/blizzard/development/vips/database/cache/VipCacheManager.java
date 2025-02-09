package blizzard.development.vips.database.cache;

import blizzard.development.vips.database.storage.VipData;

import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;

public class VipCacheManager {
    private static VipCacheManager instance;

    public final ConcurrentHashMap<String, VipData> vipCache = new ConcurrentHashMap<>();

    public void cacheVipData(String vipId, VipData vipData) {
        vipCache.put(vipId, vipData);
    }

    public VipData getVipData(String vipId) {
        return vipCache.get(vipId);
    }

    public void removeVipData(String vipId) {
        vipCache.remove(vipId);

    }

    public static VipCacheManager getInstance() {
        if (instance == null) {
            instance = new VipCacheManager();
        }
        return instance;
    }
}