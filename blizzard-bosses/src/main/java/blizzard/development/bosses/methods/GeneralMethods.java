package blizzard.development.bosses.methods;

import org.bukkit.entity.Player;

import java.util.HashMap;

public class GeneralMethods {
    public static final HashMap<Player, Boolean> playerWorldState = new HashMap<>();

    public static Boolean getPlayerWorldState(Player player) {
        return playerWorldState.containsKey(player);
    }

    public static void setPlayerInWorld(Player player) {
        playerWorldState.put(player, true);
    }

    public static void removePlayerFromWorld(Player player) {
        if (playerWorldState.containsKey(player)) {
            playerWorldState.remove(player, true);
        }
    }
}
