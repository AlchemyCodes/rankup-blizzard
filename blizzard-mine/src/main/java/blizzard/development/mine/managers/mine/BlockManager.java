package blizzard.development.mine.managers.mine;

import blizzard.development.mine.utils.apis.Cuboid;
import blizzard.development.mine.utils.packets.MinePacketUtils;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

public class BlockManager {
    private static final BlockManager instance = new BlockManager();
    private final Map<UUID, List<MineRegion>> playerRegions = new ConcurrentHashMap<>();
    private final Map<String, MineRegion> regionCache = new ConcurrentHashMap<>();

    public static BlockManager getInstance() {
        return instance;
    }

    public record MineRegion(Cuboid region, Material material, UUID owner) {

        public boolean contains(int x, int y, int z) {
                return region.isIn(new Location(region.getPoint1().getWorld(), x, y, z));
            }

            public String getKey() {
                Location p1 = region.getPoint1();
                Location p2 = region.getPoint2();
                return p1.getBlockX() + ":" + p1.getBlockY() + ":" + p1.getBlockZ() + "|" +
                        p2.getBlockX() + ":" + p2.getBlockY() + ":" + p2.getBlockZ();
            }
        }

    public void addRegion(Player player, Cuboid region, Material material) {
        UUID playerUUID = player.getUniqueId();
        MineRegion mineRegion = new MineRegion(region, material, playerUUID);

        playerRegions.computeIfAbsent(playerUUID, k -> new CopyOnWriteArrayList<>())
                .add(mineRegion);
        regionCache.put(mineRegion.getKey(), mineRegion);

        MinePacketUtils.getInstance().sendRegionPacket(player, mineRegion);
    }

    public boolean hasBlock(int x, int y, int z) {
        return regionCache.values().stream()
                .anyMatch(region -> region.contains(x, y, z));
    }

    public boolean hasBlock(Player player) {
        return playerRegions.containsKey(player.getUniqueId()) &&
                !playerRegions.get(player.getUniqueId()).isEmpty();
    }

    public void removeRegion(MineRegion region) {
        UUID owner = region.owner();
        playerRegions.computeIfPresent(owner, (k, regions) -> {
            regions.remove(region);
            return regions.isEmpty() ? null : regions;
        });
        regionCache.remove(region.getKey());
    }

    public MineRegion findRegionAt(Location location) {
        return regionCache.values().stream()
                .filter(region -> region.region().isIn(location))
                .findFirst()
                .orElse(null);
    }

    public List<MineRegion> getPlayerRegions(Player player) {
        return playerRegions.getOrDefault(player.getUniqueId(), new ArrayList<>());
    }
}
