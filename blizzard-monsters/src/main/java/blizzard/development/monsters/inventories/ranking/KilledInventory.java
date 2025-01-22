package blizzard.development.monsters.inventories.ranking;

public class KilledInventory {
    private static KilledInventory instance;


    public static KilledInventory getInstance() {
        if (instance == null) instance = new KilledInventory();
        return instance;
    }
}
