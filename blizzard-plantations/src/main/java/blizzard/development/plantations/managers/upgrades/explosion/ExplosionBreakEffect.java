package blizzard.development.plantations.managers.upgrades.explosion;

import blizzard.development.plantations.api.CuboidAPI;
import blizzard.development.plantations.database.cache.methods.PlayerCacheMethod;
import blizzard.development.plantations.tasks.PlantationRegenTask;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class ExplosionBreakEffect {

    public static final PlayerCacheMethod playerCacheMethod = new PlayerCacheMethod();

    static List<Material> plantations = Arrays.asList(
            Material.WHEAT,
            Material.POTATOES,
            Material.CARROTS,
            Material.BEETROOTS
    );

    public static void startExplosionBreak(Player player, Location location) {
        Location loc1 = location.clone().add(3, 0, 3);
        Location loc2 = location.clone().add(-3, 0, -3);

        CuboidAPI cuboidAPI = new CuboidAPI(
                loc1,
                loc2
        );

        int blockCount = 0;
        Iterator<Block> blockIterator = cuboidAPI.blockList();

        while (blockIterator.hasNext()) {
            Block block = blockIterator.next();

            if (!plantations.contains(block.getType())) continue;

            player.spawnParticle(Particle.COMPOSTER, block.getLocation().add(0, 1, 0), 1);
            block.setType(Material.AIR);
            block.breakNaturally();

            PlantationRegenTask.create(block, Material.WHEAT, 5);

            blockCount++;
        }

        playerCacheMethod.setPlantations(player, playerCacheMethod.getPlantations(player) + blockCount);
        player.sendMessage(playerCacheMethod.getPlantations(player) + "");
    }
}
