package blizzard.development.monsters.database.cache.methods;

public class PlayersCacheMethods {
    private static PlayersCacheMethods instance;

    public static PlayersCacheMethods getInstance() {
        if (instance == null) instance = new PlayersCacheMethods();
        return instance;
    }
}
