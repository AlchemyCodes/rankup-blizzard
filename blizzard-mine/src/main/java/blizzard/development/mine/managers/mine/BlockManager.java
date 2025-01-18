package blizzard.development.mine.managers.mine;

import com.comphenix.protocol.wrappers.BlockPosition;
import org.bukkit.entity.Player;

import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;

public class BlockManager {

    private static final BlockManager instance = new BlockManager();

    private final Map<UUID, Set<BlockPosition>> blockPosition = new ConcurrentHashMap<>();

    private final Set<String> positionCache = ConcurrentHashMap.newKeySet();

    public static BlockManager getInstance() {
        return instance;
    }

    public boolean hasBlock(Player player) {
        Set<BlockPosition> positions = blockPosition.get(player.getUniqueId());
        return positions != null && !positions.isEmpty();
    }

    public void placeBlock(Player player, BlockPosition position) {
        UUID playerUUID = player.getUniqueId();
        blockPosition.computeIfAbsent(playerUUID, k -> new CopyOnWriteArraySet<>()).add(position);
        positionCache.add(createPositionKey(position));
    }

    public boolean isBlock(int x, int y, int z) {
        return positionCache.contains(createPositionKey(x, y, z));
    }

    private String createPositionKey(BlockPosition pos) {
        return createPositionKey(pos.getX(), pos.getY(), pos.getZ());
    }

    private String createPositionKey(int x, int y, int z) {
        return x + ":" + y + ":" + z;
    }

    public void removeBlock(BlockPosition position) {
        String posKey = createPositionKey(position);
        positionCache.remove(posKey);

        blockPosition.values().forEach(set -> {
            if (set != null) {
                set.removeIf(pos -> createPositionKey(pos).equals(posKey));
            }
        });
    }

    public void listBlocks(Player player) {
        Set<BlockPosition> positions = blockPosition.get(player.getUniqueId());
        if (positions != null && !positions.isEmpty()) {
            BlockPosition position = positions.iterator().next();
            System.out.println("Player: " + player.getName() + " -> "
                    + position.getX() + ", " + position.getY() + ", " + position.getZ());
        }
    }

    public BlockPosition findMatchingBlock(BlockPosition blockToCheck) {
        int checkX = blockToCheck.getX();
        int checkY = blockToCheck.getY();
        int checkZ = blockToCheck.getZ();

        for (int dx = -3; dx <= 3; dx++) {
            for (int dy = -3; dy <= 3; dy++) {
                for (int dz = -3; dz <= 3; dz++) {
                    String key = createPositionKey(checkX + dx, checkY + dy, checkZ + dz);
                    if (positionCache.contains(key)) {
                        return new BlockPosition(checkX + dx, checkY + dy, checkZ + dz);
                    }
                }
            }
        }
        return null;
    }
}