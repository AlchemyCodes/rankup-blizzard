package blizzard.development.mine.managers;

import blizzard.development.mine.database.cache.PlayerCacheManager;
import blizzard.development.mine.database.cache.methods.PlayerCacheMethod;
import blizzard.development.mine.utils.Cuboid;
import blizzard.development.mine.utils.locations.LocationUtils;
import blizzard.development.mine.utils.packets.PacketUtils;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import java.util.Iterator;

public class MineManager {

    private static final MineManager instance = new MineManager();
    public static MineManager getInstance() {
        return instance;
    }

    public void transformArea(Player player) {
        Location point1 = LocationUtils.getMineCenterLocation().add(-15, -3, -15);
        Location point2 = LocationUtils.getMineCenterLocation().add(15, -60, 15);

        Cuboid cuboid = new Cuboid(point1, point2);

        Iterator<Block> blockIterator = cuboid.blockList();

        while (blockIterator.hasNext()) {
            Block block = blockIterator.next();

            PacketUtils
                .getInstance()
                .sendPacket(
                    player,
                    block,
                    Material.getMaterial(PlayerCacheMethod.getInstance().getAreaBlock(player))
                );
        }
    }
}
