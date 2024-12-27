package blizzard.development.monsters.database.cache.methods;

public class ToolsCacheMethods {
    private static ToolsCacheMethods instance;

    public static ToolsCacheMethods getInstance() {
        if (instance == null) instance = new ToolsCacheMethods();
        return instance;
    }
}
