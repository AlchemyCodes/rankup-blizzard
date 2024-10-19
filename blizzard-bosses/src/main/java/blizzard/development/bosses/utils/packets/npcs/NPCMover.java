package blizzard.development.bosses.utils.packets.npcs;

import blizzard.development.bosses.utils.PluginImpl;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

public class NPCMover {
    private final NPC npc;

    public NPCMover(NPC npc) {
        this.npc = npc;
    }

    public void moveTo(Player player, Location start, Location end) {
        new BukkitRunnable() {
            Location currentLocation = start.clone();
            Vector direction = end.toVector().subtract(start.toVector()).normalize();
            double distance = start.distance(end);
            double speed = 0.2;
            int steps = (int) (distance / speed);

            @Override
            public void run() {
                if (currentLocation.distance(end) < 0.1) {
                    this.cancel();
                } else {
                    Location nextLocation = currentLocation.clone().add(direction.clone().multiply(speed));

                    if (nextLocation.getBlock().getType().isSolid()) {
                        nextLocation.add(0, 0.1, 0);
                        if (nextLocation.getBlock().getType().isSolid()) {
                            nextLocation.add(0, 0.1, 0);
                        }
                    } else if (!nextLocation.clone().subtract(0, 1, 0).getBlock().getType().isSolid()) {
                        nextLocation.subtract(0, 0.1, 0);
                    }

                    currentLocation.add(direction.clone().multiply(speed));
                    npc.move(player, nextLocation);
                    npc.look(player, (float) currentLocation.getYaw(), (float) currentLocation.getPitch());
                    currentLocation = nextLocation;
                }
            }
        }.runTaskTimer(PluginImpl.getInstance().plugin, 0L, 1L);
    }
}
