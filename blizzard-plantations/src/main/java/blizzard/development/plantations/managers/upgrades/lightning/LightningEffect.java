package blizzard.development.plantations.managers.upgrades.lightning;

import blizzard.development.plantations.database.cache.methods.PlayerCacheMethod;
import blizzard.development.plantations.managers.PlantationManager;
import blizzard.development.plantations.utils.TextUtils;
import blizzard.development.plantations.utils.packets.PacketUtils;
import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.PacketContainer;
import org.bukkit.*;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;


public class LightningEffect {

    private static final PlayerCacheMethod playerCacheMethod = PlayerCacheMethod.getInstance();

    public static void startLightningEffect(Player player, Location location) {
        int centerX = location.getBlockX();
        int centerY = location.getBlockY();
        int centerZ = location.getBlockZ();

        for (int x = -1; x <= 1; x++) {
            for (int z = -1; z <= 1; z++) {
                Location packetLocation = new Location(
                    location.getWorld(),
                    centerX + x,
                    centerY,
                    centerZ + z
                );

                player.playSound(
                    packetLocation,
                    Sound.ENTITY_LIGHTNING_BOLT_IMPACT,
                    1.0f,
                    0.8f
                );

                PacketUtils.getInstance().sendEntityPacket(
                    packetLocation,
                    player,
                    EntityType.LIGHTNING
                );

                PacketUtils.getInstance().sendPacket(
                    player,
                    packetLocation.getBlock(),
                    Material.POTATOES
                );

                player.spawnParticle(
                    Particle.CLOUD,
                    packetLocation,
                    10
                );

                playerCacheMethod.setPlantations(
                    player,
                    25
                );

                PlantationManager.getInstance()
                    .growthDelay(
                        player,
                        packetLocation.getBlock(),
                        0,
                        1
                    );

            }
        }
    }
}
