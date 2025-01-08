package blizzard.development.plantations.managers.upgrades.explosion;

import blizzard.development.plantations.api.SeedAPI;
import blizzard.development.plantations.database.cache.methods.PlayerCacheMethod;
import blizzard.development.plantations.database.cache.methods.ToolCacheMethod;
import blizzard.development.plantations.managers.PlantationManager;
import blizzard.development.plantations.utils.TextUtils;
import blizzard.development.plantations.utils.packets.PacketUtils;
import net.kyori.adventure.text.Component;
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

                SeedAPI seedAPI = SeedAPI.getInstance();
                seedAPI.addSeed(player, 25);

                PlantationManager.getInstance()
                    .growthDelay(
                        player,
                        packetLocation.getBlock(),
                        0,
                        1
                    );
            }
        }

        Component hoverText = TextUtils.parse("§a25 plantações quebradas.");
        Component mainMessage = TextUtils.parse("§8(Passe o mouse para mais detalhes)")
            .hoverEvent(hoverText);

        Component fullMessage = TextUtils.parse(" <bold><#d90404>Expl<#f71919><#f71919>osão!<#d90404></bold> §8✈ §f§l+§a25§l✿ §7♦ §fBônus: §71.1§lx ")
            .append(mainMessage);

        player.sendMessage(fullMessage);
    }
}