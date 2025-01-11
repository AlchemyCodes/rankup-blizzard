package blizzard.development.monsters.database.cache.methods;

import blizzard.development.monsters.database.cache.managers.ToolsCacheManager;
import blizzard.development.monsters.database.storage.ToolsData;

public class ToolsCacheMethods {
    private static ToolsCacheMethods instance;

    private final ToolsCacheManager cache = ToolsCacheManager.getInstance();

    public Integer getDamage(String id) {
        ToolsData data = cache.getToolData(id);
        if (data != null) {
            return data.getDamage();
        }
        return null;
    }

    public static ToolsCacheMethods getInstance() {
        if (instance == null) instance = new ToolsCacheMethods();
        return instance;
    }
}
