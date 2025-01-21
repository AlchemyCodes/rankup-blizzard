package blizzard.development.mine.managers.enchantments.meteor;

import blizzard.development.mine.database.cache.methods.PlayerCacheMethods;
import blizzard.development.mine.database.cache.methods.ToolCacheMethods;
import blizzard.development.mine.utils.packets.MinePacketUtils;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

public class MeteorEffect {

    public static final PlayerCacheMethods playerCacheMethod = PlayerCacheMethods.getInstance();
    public static final ToolCacheMethods toolCacheMethod = ToolCacheMethods.getInstance();

    public static void startMeteorBreak(Player player, Location location, int width, int height, int depth) {
        int centerX = location.getBlockX();
        int centerY = location.getBlockY();
        int centerZ = location.getBlockZ();

        int halfWidth = width / 2;
        int halfHeight = height / 2;
        int halfDepth = depth / 2;

        for (int x = -halfWidth; x <= halfWidth; x++) {
            for (int y = -halfHeight; y <= halfHeight; y++) {
                for (int z = -halfDepth; z <= halfDepth; z++) {
                    Location packetLocation = new Location(
                        location.getWorld(),
                        centerX + x,
                        centerY + y,
                        centerZ + z
                    );

                    player.playSound(
                        packetLocation,
                        Sound.ENTITY_DRAGON_FIREBALL_EXPLODE,
                        1.0f,
                        0.8f
                    );

                    MinePacketUtils.getInstance()
                        .sendAirBlock(
                            player,
                            player.getWorld().getBlockAt(packetLocation)
                        );

                    player.spawnParticle(
                        Particle.FLAME,
                        packetLocation,
                        10
                    );
                }
            }
        }

//        Component hoverText = TextUtils.parse("§a25 plantações quebradas.");
//        Component mainMessage = TextUtils.parse("§8(Passe o mouse para mais detalhes)")
//            .hoverEvent(hoverText);
//
//        Component fullMessage = TextUtils.parse(" <bold><#d90404>Expl<#f71919><#f71919>osão!<#d90404></bold> §8✈ §f§l+§a25§l✿ §7♦ §fBônus: §71.1§lx ")
//            .append(mainMessage);
//
//        player.sendMessage(fullMessage);
    }
}
