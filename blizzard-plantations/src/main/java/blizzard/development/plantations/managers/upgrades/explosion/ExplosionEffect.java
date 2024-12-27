package blizzard.development.plantations.managers.upgrades.explosion;

import blizzard.development.plantations.database.cache.methods.PlayerCacheMethod;
import blizzard.development.plantations.database.cache.methods.ToolCacheMethod;
import blizzard.development.plantations.managers.PlantationManager;
import blizzard.development.plantations.utils.TextUtils;
import blizzard.development.plantations.utils.packets.PacketUtils;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

public class ExplosionEffect {

    public static final PlayerCacheMethod playerCacheMethod = PlayerCacheMethod.getInstance();
    public static final ToolCacheMethod toolCacheMethod = ToolCacheMethod.getInstance();

    public static void startExplosionBreak(Player player, Location location) {
        int centerX = location.getBlockX();
        int centerY = location.getBlockY();
        int centerZ = location.getBlockZ();

        for (int x = -2; x <= 2; x++) {
            for (int z = -2; z <= 2; z++) {
                Location packetLocation = new Location(
                    location.getWorld(),
                    centerX + x,
                    centerY,
                    centerZ + z
                );


                player.playSound(
                    packetLocation,
                    Sound.ENTITY_TNT_PRIMED,
                    1.0f,
                    0.8f
                );

                PacketUtils.getInstance().sendPacket(
                    player,
                    packetLocation.getBlock(),
                    Material.POTATOES
                );

                player.spawnParticle(
                    Particle.FLAME,
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

        player.sendMessage("");
        player.sendMessage(TextUtils.parse(" <bold><#d90404>Expl<#f71919><#f71919>osão!<#d90404></bold> <#f03232>Confira o relatório:<#f03232>"));
        player.sendMessage(" §fA explosão quebrou §l25§f plantações.");
        player.sendMessage("");
    }
}