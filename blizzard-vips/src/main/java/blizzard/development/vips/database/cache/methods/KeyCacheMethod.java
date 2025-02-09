package blizzard.development.vips.database.cache.methods;

import blizzard.development.vips.database.cache.KeyCacheManager;
import blizzard.development.vips.database.storage.KeyData;

public class KeyCacheMethod {
    private static KeyCacheMethod instance;

    private final KeyCacheManager keyCache = KeyCacheManager.getInstance();

    public String getKey(String key) {
        KeyData data = keyCache.getKeyData(key);
        return (data != null) ? data.getKey() : null;
    }

    public String getKeyVipName(String key) {
        KeyData data = keyCache.getKeyData(key);
        return (data != null) ? data.getVipName() : null;
    }

    public Long getKeyVipDuration(String key) {
        KeyData data = keyCache.getKeyData(key);
        return (data != null) ? data.getDuration() : null;
    }

    public static KeyCacheMethod getInstance() {
        if (instance == null) {
            instance = new KeyCacheMethod();
        }
        return instance;
    }
}