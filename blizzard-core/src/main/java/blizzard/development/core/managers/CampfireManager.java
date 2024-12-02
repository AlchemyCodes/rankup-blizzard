package blizzard.development.core.managers;

import java.util.HashMap;
import java.util.Map;
import com.comphenix.protocol.wrappers.BlockPosition;
import org.bukkit.entity.Player;

public class CampfireManager {
    private static final Map<Player, BlockPosition> virtualCampfires = new HashMap<>();

    public static boolean hasCampfire(Player player) {
        return virtualCampfires.containsKey(player);
    }

    public static void placeCampfire(Player player, BlockPosition position) {
        virtualCampfires.put(player, position);
    }

    public static boolean isPacketCampfire(int x, int y, int z) {
        return virtualCampfires.values().stream()
                .anyMatch(pos -> pos.getX() == x && pos.getY() == y && pos.getZ() == z);
    }

    public static void removeCampfire(Player player) {
        virtualCampfires.remove(player);
    }
}




