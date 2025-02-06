package blizzard.development.monsters.database.cache.methods;

import blizzard.development.monsters.database.cache.managers.ToolsCacheManager;
import blizzard.development.monsters.database.storage.MonstersData;
import blizzard.development.monsters.database.storage.ToolsData;
import blizzard.development.monsters.monsters.enums.Tools;

public class ToolsCacheMethods {
    private static ToolsCacheMethods instance;

    private final ToolsCacheManager cache = ToolsCacheManager.getInstance();

    public void setSkin(String uuid, Tools skin) {
        ToolsData data = cache.getToolData(uuid);
        if (data != null) {
            data.setSkin(skin.getType());
            cache.cacheToolData(uuid, data);
        }
    }

    public Tools getSkin(String uuid) {
        ToolsData data = cache.getToolData(uuid);
        if (data != null) {
            return Tools.valueOf(
                    data.getSkin().toUpperCase()
            );
        }
        return null;
    }

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
