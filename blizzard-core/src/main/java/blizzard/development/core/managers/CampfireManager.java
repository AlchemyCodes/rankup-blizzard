package blizzard.development.core.managers;

import com.comphenix.protocol.wrappers.BlockPosition;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class CampfireManager {
    private static final Map<Player, Boolean> playerCampfireMap = new ConcurrentHashMap<>();
    private static final Map<Player, BlockPosition> playerCampfirePosition = new ConcurrentHashMap<>();

    public static void placeCampfire(Player player, Block block) {
        BlockPosition blockPosition = new BlockPosition(block.getX(), block.getY(), block.getZ());

        playerCampfireMap.put(player, true);
        playerCampfirePosition.put(player, blockPosition);
    }

    public static void removeCampfire(Player player) {
        if (!playerCampfireMap.getOrDefault(player, false)) return;

        playerCampfireMap.remove(player);
        playerCampfirePosition.remove(player);
    }

    public static boolean hasCampfire(Player player) {
        return playerCampfireMap.getOrDefault(player, false);
    }

}