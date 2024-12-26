package blizzard.development.vips.database.cache;

import blizzard.development.vips.database.storage.KeysData;

import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class KeysCacheManager {
    private static KeysCacheManager instance;

    public final ConcurrentHashMap<String, KeysData> keysCache = new ConcurrentHashMap<>();

    public void cacheKeyData(String key, KeysData keyData) {
        keysCache.put(key, keyData);
    }

    public KeysData getKeyData(String key) {
        return keysCache.get(key);
    }

    public void removeKeyData(String key) {
        keysCache.remove(key);
    }

    public Collection<KeysData> getAllKeysData() {
        return keysCache.values();
    }

    public Collection<KeysData> getKeysByVipName(String vipName) {
        return keysCache.values().stream()
                .filter(keyData -> keyData.getVipName().equalsIgnoreCase(vipName))
                .collect(Collectors.toList());
    }

    public static KeysCacheManager getInstance() {
        if (instance == null) {
            instance = new KeysCacheManager();
        }
        return instance;
    }
}
