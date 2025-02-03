package blizzard.development.monsters.inventories.tools.items;

public class SwordItems {
    private static SwordItems instance;

    public static SwordItems getInstance() {
        if (instance == null) instance = new SwordItems();
        return instance;
    }
}
