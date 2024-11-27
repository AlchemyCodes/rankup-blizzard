package blizzard.development.spawners.inventories.drops.items;

public class DropsItems {
    private static DropsItems instance;

    public static DropsItems getInstance() {
        if (instance == null) instance = new DropsItems();
        return instance;
    }
}
