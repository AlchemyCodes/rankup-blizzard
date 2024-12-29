package blizzard.development.plantations.managers;

import com.comphenix.protocol.wrappers.BlockPosition;
import org.bukkit.entity.Player;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;

public class BlockManager {

    private static final ConcurrentHashMap<Player, Set<BlockPosition>> blockPosition = new ConcurrentHashMap<>();
    private static final ConcurrentHashMap<Player, Set<BlockPosition>> plantationsPosition = new ConcurrentHashMap<>();

    public static boolean hasFarmland(Player player) {
        Set<BlockPosition> positions = blockPosition.get(player);
        return positions != null && !positions.isEmpty();
    }

    public static boolean hasPlantation(Player player) {
        Set<BlockPosition> positions = plantationsPosition.get(player);
        return positions != null && !positions.isEmpty();
    }

    public static void placeFarmland(Player player, BlockPosition position) {
        blockPosition.computeIfAbsent(player, p -> new CopyOnWriteArraySet<>()).add(position);
    }

    public static boolean isFarmland(int x, int y, int z) {
        return blockPosition.values().stream()
            .filter(set -> set != null)
            .flatMap(Set::stream)
            .anyMatch(pos -> pos.getX() == x && pos.getY() == y && pos.getZ() == z);
    }

    public static void placePlantation(Player player, BlockPosition position) {
        plantationsPosition.computeIfAbsent(player, p -> new CopyOnWriteArraySet<>()).add(position);
    }

    public static boolean isPlantation(int x, int y, int z) {
        return plantationsPosition.values().stream()
            .filter(set -> set != null)
            .flatMap(Set::stream)
            .anyMatch(position -> position.getX() == x && position.getY() == y && position.getZ() == z);
    }

    public static void removeFarmland(Player player) {
        blockPosition.remove(player);
    }

    public static void removePlantation(BlockPosition position) {
        plantationsPosition.values().forEach(set -> {
            if (set != null) {
                set.remove(position);
            }
        });
    }

    public static void listPlantations(Player player) {
        Set<BlockPosition> positions = plantationsPosition.get(player);
        if (positions != null && !positions.isEmpty()) {
            BlockPosition position = positions.iterator().next();
            System.out.println("Player: " + player.getName() + " -> "
                + position.getX() + ", " + position.getY() + ", " + position.getZ());
        }
    }

    public static BlockPosition findMatchingPlantation(BlockPosition blockToCheck) {
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

//package blizzard.development.plantations.managers;
//
//import com.comphenix.protocol.wrappers.BlockPosition;
//import org.bukkit.entity.Player;
//
//import java.util.HashMap;
//import java.util.HashSet;
//import java.util.Map;
//import java.util.Set;
//
//public class BlockManager {
//
//    private static final Map<Player, Set<BlockPosition>> blockPosition = new HashMap<>();
//    private static final Map<Player, Set<BlockPosition>> plantationsPosition = new HashMap<>();
//
//    public static boolean hasFarmland(Player player) {
//        return blockPosition.containsKey(player) && !blockPosition.get(player).isEmpty();
//    }
//
//    public static boolean hasPlantation(Player player) {
//        return plantationsPosition.containsKey(player) && !plantationsPosition.get(player).isEmpty();
//    }
//
//    public static void placeFarmland(Player player, BlockPosition position) {
//        blockPosition.computeIfAbsent(player, p -> new HashSet<>()).add(position);
//    }
//
//    public static boolean isFarmland(int x, int y, int z) {
//        return blockPosition.values().stream()
//            .flatMap(Set::stream)
//            .anyMatch(pos -> pos.getX() == x && pos.getY() == y && pos.getZ() == z);
//    }
//
//    public static void placePlantation(Player player, BlockPosition position) {
//        plantationsPosition.computeIfAbsent(player, p -> new HashSet<>()).add(position);
//    }
//
//    public static boolean isPlantation(int x, int y, int z) {
//        return plantationsPosition.values().stream()
//            .flatMap(Set::stream)
//            .anyMatch(position -> position.getX() == x && position.getY() == y && position.getZ() == z);
//    }
//
//    public static void removeFarmland(Player player) {
//        blockPosition.remove(player);
//    }
//
//    public static void removePlantation(BlockPosition position) {
//        plantationsPosition.values().forEach(set -> set.remove(position));
//    }
//
//    public static void listPlantations(Player player) {
//        plantationsPosition.entrySet().stream()
//            .filter(entry -> entry.getKey().equals(player))
//            .forEach(entry -> {
//                BlockPosition position = entry.getValue().iterator().next();
//                System.out.println("Player: " + player.getName() + " -> "
//                    + position.getX() + ", " + position.getY() + ", " + position.getZ());
//            });
//    }
//
//    public static BlockPosition findMatchingPlantation(BlockPosition blockToCheck) {
//        for (Set<BlockPosition> plantations : plantationsPosition.values()) {
//            for (BlockPosition plantation : plantations) {
//                if (isNearby(plantation, blockToCheck)) {
//                    return plantation;
//                }
//            }
//        }
//        return null;
//    }
//
//    private static boolean isNearby(BlockPosition pos1, BlockPosition pos2) {
//        return Math.abs(pos1.getX() - pos2.getX()) <= 3 &&
//            Math.abs(pos1.getY() - pos2.getY()) <= 3 &&
//            Math.abs(pos1.getZ() - pos2.getZ()) <= 3;
//    }
//}
