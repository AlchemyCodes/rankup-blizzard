package blizzard.development.mine.managers.mine;

import blizzard.development.core.Main;
import blizzard.development.mine.database.cache.methods.PlayerCacheMethods;
import blizzard.development.mine.mine.enums.LocationEnum;
import blizzard.development.mine.utils.PluginImpl;
import blizzard.development.mine.utils.apis.Cuboid;
import blizzard.development.mine.utils.locations.LocationUtils;
import blizzard.development.mine.utils.packets.MinePacketUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class MineManager {
    private static final MineManager instance = new MineManager();

    public static MineManager getInstance() {
        return instance;
    }

    public void transformArea(Player player) {
        Location centerLocation = LocationUtils.getLocation(LocationEnum.CENTER.getName());

        int centerChunkX = centerLocation.getBlockX() >> 4;
        int centerChunkZ = centerLocation.getBlockZ() >> 4;

        int startChunkX = centerChunkX - 1;
        int startChunkZ = centerChunkZ - 1;

        Material material = Material.getMaterial(PlayerCacheMethods.getInstance().getAreaBlock(player));

        processChunkBatch(player, centerLocation, startChunkX, startChunkZ, material, 0);
    }

    private void processChunkBatch(Player player, Location centerLocation, int startChunkX, int startChunkZ, Material material, int batchIndex) {
        if (batchIndex >= 4) return;

        int xOffset = batchIndex / 2;
        int zOffset = batchIndex % 2;

        int currentChunkX = startChunkX + xOffset;
        int currentChunkZ = startChunkZ + zOffset;

        Location point1 = new Location(
                centerLocation.getWorld(),
                currentChunkX << 4,
                centerLocation.getBlockY(),
                currentChunkZ << 4
        );
        Location point2 = point1.clone().add(
                15,
                PluginImpl.getInstance().Config.getInt("mine.y-blocks"),
                15
        );

        Cuboid cuboid = new Cuboid(point1, point2);

        Bukkit.getScheduler().runTaskLater(Main.getInstance(), () -> {
            MinePacketUtils.getInstance().sendMultiBlockPacket(player, cuboid, material);
            processChunkBatch(player, centerLocation, startChunkX, startChunkZ, material, batchIndex + 1);
        }, 1L);
    }
}