package blizzard.development.monsters.monsters.handlers.world;
import org.bukkit.entity.Player;

import java.util.HashMap;


public class MonstersWorldHandler {
    private static MonstersWorldHandler instance;

    private final HashMap<Player, Boolean> monstersWorldCache = new HashMap<>();

    public void addPlayer(Player player) {
        monstersWorldCache.put(player, true);
    }

    public void removePlayer(Player player) {
        monstersWorldCache.remove(player);
    }

    public boolean containsPlayer(Player player) {
        return monstersWorldCache.containsKey(player);
    }

    public static MonstersWorldHandler getInstance() {
        if (instance == null) instance = new MonstersWorldHandler();
        return instance;
    }
}
