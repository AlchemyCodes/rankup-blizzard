package blizzard.development.plantations.managers.upgrades.xray;

import blizzard.development.plantations.database.cache.methods.PlayerCacheMethod;
import blizzard.development.plantations.managers.PlantationManager;
import blizzard.development.plantations.utils.packets.PacketUtils;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

public class XrayEffect {


    private static final PlayerCacheMethod playerCacheMethod = PlayerCacheMethod.getInstance();

    public static void startXrayEffect(Player player, Location location) {
        int centerX = location.getBlockX();
        int centerY = location.getBlockY();
        int centerZ = location.getBlockZ();

        int distance = 7;
        int plantationsBroken = 0;

        for (int i = 0; i <= distance; i++) {
            createXrayEffect(player, location, centerX + i + 2, centerZ + i + 2);
            createXrayEffect(player, location, centerX - i, centerZ - i);

            createXrayEffect(player, location, centerX + i + 2, centerZ - i);
            createXrayEffect(player, location, centerX - i, centerZ + i + 2);

            plantationsBroken += 20;
        }

    }

    private static void createXrayEffect(Player player, Location origin, int targetX, int targetZ) {
        Location blockLocation = new Location(origin.getWorld(), targetX, origin.getBlockY(), targetZ);

        player.spawnParticle(
            Particle.BLOCK_CRACK,
            blockLocation.add(0.5, 0.1, 0.5),
            10,
            0, 0, 0,
            0,
            Material.COBBLESTONE.createBlockData()
        );

        player.playSound(
            blockLocation,
            Sound.ITEM_CROP_PLANT,
            1.0f,
            0.8f
        );

        PacketUtils.getInstance().sendPacket(
            player,
            blockLocation.getBlock(),
            Material.POTATOES
        );


        PlantationManager.getInstance().growthDelay(
            player,
            blockLocation.getBlock(),
            0,
            2
        );

        PacketUtils.getInstance().sendEntityPacket(
            blockLocation.add(0, 10, 0),
            player,
            EntityType.SNOWBALL
        );

        playerCacheMethod.setPlantations(player, 5);
    }
}
