package blizzard.development.mine.managers;

import com.comphenix.protocol.wrappers.BlockPosition;
import org.bukkit.entity.Player;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;

public class BlockManager {

    private static final ConcurrentHashMap<Player, Set<BlockPosition>> blockPosition = new ConcurrentHashMap<>();
    private static final ConcurrentHashMap<Player, Set<BlockPosition>> plantationsPosition = new ConcurrentHashMap<>();


    public static boolean hasBlock(Player player) {
        Set<BlockPosition> positions = plantationsPosition.get(player);
        return positions != null && !positions.isEmpty();
    }

    public static void placeBlock(Player player, BlockPosition position) {
        blockPosition.computeIfAbsent(player, p -> new CopyOnWriteArraySet<>()).add(position);
    }

    public static void placePlantation(Player player, BlockPosition position) {
        plantationsPosition.computeIfAbsent(player, p -> new CopyOnWriteArraySet<>()).add(position);
    }

    public static boolean isBlock(int x, int y, int z) {
        return plantationsPosition.values().stream()
            .filter(set -> set != null)
            .flatMap(Set::stream)
            .anyMatch(position -> position.getX() == x && position.getY() == y && position.getZ() == z);
    }

    public static void removeBlock(BlockPosition position) {
        plantationsPosition.values().forEach(set -> {
            if (set != null) {
                set.remove(position);
            }
        });
    }

    public static void listBlocks(Player player) {
        Set<BlockPosition> positions = plantationsPosition.get(player);
        if (positions != null && !positions.isEmpty()) {
            BlockPosition position = positions.iterator().next();
            System.out.println("Player: " + player.getName() + " -> "
                + position.getX() + ", " + position.getY() + ", " + position.getZ());
        }
    }

    public static BlockPosition findMatchingBlock(BlockPosition blockToCheck) {
        for (Set<BlockPosition> plantations : plantationsPosition.values()) {
            if (plantations != null) {
                for (BlockPosition plantation : plantations) {
                    if (isNearby(plantation, blockToCheck)) {
                        return plantation;
                    }
                }
            }
        }
        return null;
    }

    private static boolean isNearby(BlockPosition pos1, BlockPosition pos2) {
        return Math.abs(pos1.getX() - pos2.getX()) <= 3 &&
            Math.abs(pos1.getY() - pos2.getY()) <= 3 &&
            Math.abs(pos1.getZ() - pos2.getZ()) <= 3;
    }
}
