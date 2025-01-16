package blizzard.development.mine.managers;

import com.comphenix.protocol.wrappers.BlockPosition;
import org.bukkit.entity.Player;

import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;

public class BlockManager {

    private static final BlockManager instance = new BlockManager();

    public static BlockManager getInstance() {
        return instance;
    }

    private final ConcurrentHashMap<Player, Set<BlockPosition>> blockPosition = new ConcurrentHashMap<>();

    public boolean hasBlock(Player player) {
        Set<BlockPosition> positions = blockPosition.get(player);
        return positions != null && !positions.isEmpty();
    }

    public void placeBlock(Player player, BlockPosition position) {
        blockPosition.computeIfAbsent(player, p -> new CopyOnWriteArraySet<>()).add(position);
    }

    public boolean isBlock(int x, int y, int z) {
        return blockPosition.values().stream()
            .filter(Objects::nonNull)
            .flatMap(Set::stream)
            .anyMatch(position -> position.getX() == x && position.getY() == y && position.getZ() == z);
    }

    public void removeBlock(BlockPosition position) {
        blockPosition.values().forEach(set -> {
            if (set != null) {
                set.remove(position);
            }
        });
    }

    public void listBlocks(Player player) {
        Set<BlockPosition> positions = blockPosition.get(player);
        if (positions != null && !positions.isEmpty()) {
            BlockPosition position = positions.iterator().next();
            System.out.println("Player: " + player.getName() + " -> "
                + position.getX() + ", " + position.getY() + ", " + position.getZ());
        }
    }

    public BlockPosition findMatchingBlock(BlockPosition blockToCheck) {
        for (Set<BlockPosition> plantations : blockPosition.values()) {
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

    private boolean isNearby(BlockPosition pos1, BlockPosition pos2) {
        return Math.abs(pos1.getX() - pos2.getX()) <= 3 &&
            Math.abs(pos1.getY() - pos2.getY()) <= 3 &&
            Math.abs(pos1.getZ() - pos2.getZ()) <= 3;
    }
}
