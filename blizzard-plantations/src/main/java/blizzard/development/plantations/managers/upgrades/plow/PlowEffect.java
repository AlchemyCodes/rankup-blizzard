package blizzard.development.plantations.managers.upgrades.plow;

import blizzard.development.plantations.api.cuboid.CuboidAPI;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.data.type.Farmland;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class PlowEffect {

    static List<Material> blocks = Arrays.asList(
            Material.COARSE_DIRT,
            Material.FARMLAND
    );

    public static void startPlowEffect(Location location) {
        Location loc1 = location.clone().add(3, 0, 3);
        Location loc2 = location.clone().add(-3, 0, -3);

        CuboidAPI cuboidAPI = new CuboidAPI(
                loc1,
                loc2
        );

        Iterator<Block> blockIterator = cuboidAPI.blockList();

        while (blockIterator.hasNext()) {
            Block block = blockIterator.next();

            if (!blocks.contains(block.getType())) return;

            block.setType(Material.FARMLAND);

            setMoisture(block);
            World world = block.getWorld();
            world.spawnParticle(Particle.COMPOSTER, block.getLocation().add(0.5, 1, 0.5), 3);
        }
    }

    private static Block setMoisture(Block block) {
        Farmland farmland = (Farmland) block.getBlockData();
        farmland.setMoisture(farmland.getMaximumMoisture());
        block.setBlockData(farmland);
        return block;
    }
}
