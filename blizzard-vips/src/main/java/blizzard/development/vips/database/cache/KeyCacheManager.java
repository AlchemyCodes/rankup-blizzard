package blizzard.development.vips.database.cache;

import blizzard.development.vips.database.storage.KeyData;

import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class KeyCacheManager {
    private static KeyCacheManager instance;

    public final ConcurrentHashMap<String, KeyData> keysCache = new ConcurrentHashMap<>();

    public void cacheKeyData(String key, KeyData keyData) {
        keysCache.put(key, keyData);
    }

    public KeyData getKeyData(String key) {
        return keysCache.get(key);
    }

    public void removeKeyData(String key) {
        keysCache.remove(key);
    }

    public Collection<KeyData> getAllKeysData() {
        return keysCache.values();
    }

    public Collection<KeyData> getKeysByVipName(String vipName) {
        return keysCache.values().stream()
                .filter(keyData -> keyData.getVipName().equalsIgnoreCase(vipName))
                .collect(Collectors.toList());
    }

    public static KeyCacheManager getInstance() {
        if (instance == null) {
            instance = new KeyCacheManager();
        }
        return instance;
    }
}