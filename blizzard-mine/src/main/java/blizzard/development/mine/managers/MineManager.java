package blizzard.development.mine.managers;

import blizzard.development.mine.database.cache.methods.PlayerCacheMethods;
import blizzard.development.mine.utils.apis.Cuboid;
import blizzard.development.mine.utils.locations.LocationUtils;
import blizzard.development.mine.utils.packets.PacketUtils;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class MineManager {
    private static final MineManager instance = new MineManager();

    public static MineManager getInstance() {
        return instance;
    }

    public void transformArea(Player player) {
        Location baseLocation = LocationUtils.getMineCenterLocation();
        int chunkX = baseLocation.getBlockX() >> 4;
        int chunkZ = baseLocation.getBlockZ() >> 4;

        Location point1 = new Location(baseLocation.getWorld(), chunkX << 4, baseLocation.getBlockY(), chunkZ << 4);
        Location point2 = point1.clone().add(0, -35, 0);

        Cuboid cuboid = new Cuboid(point1, point2);
        Material material = Material.getMaterial(PlayerCacheMethods.getInstance().getAreaBlock(player));

        PacketUtils.getInstance().sendMultiBlockPacket(player, cuboid, material);
    }
}