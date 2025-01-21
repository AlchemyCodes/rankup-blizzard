package blizzard.development.mine.listeners.mine;

import blizzard.development.mine.database.cache.methods.PlayerCacheMethods;
import blizzard.development.mine.mine.enums.LocationEnum;
import blizzard.development.mine.utils.locations.LocationUtils;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.util.Vector;

public class MineJumpPadListener implements Listener {

    private final PlayerCacheMethods playerCacheMethods = PlayerCacheMethods.getInstance();

    @EventHandler
    public void onJump(PlayerMoveEvent event) {
        Player player = event.getPlayer();

        if (playerCacheMethods.isInMine(player)) {
            if (player.getLocation().subtract(0, 1, 0).getBlock().getType() == Material.SLIME_BLOCK) {
                Vector jumpBoost = new Vector(0, 3, 0);
                player.setVelocity(jumpBoost);

                player.playSound(player.getLocation(), Sound.ENTITY_FIREWORK_ROCKET_LAUNCH, 1.0f, 1.0f);
                player.spawnParticle(Particle.VILLAGER_HAPPY, player.getLocation(), 20, 0.5, 0.5, 0.5, 0);
            }
        }
    }
}
