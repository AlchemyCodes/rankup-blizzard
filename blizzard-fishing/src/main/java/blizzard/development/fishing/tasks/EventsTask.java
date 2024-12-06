package blizzard.development.fishing.tasks;

import blizzard.development.fishing.utils.PluginImpl;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

public class EventsTask implements Runnable {

    public EventsTask(Plugin main) {
        main.getServer()
                .getScheduler()
                .runTaskTimer(main, this, 0, 20L * main.getConfig().getInt("events.time"));
    }

    private BukkitRunnable geyserRunnable;
    public static Boolean isGeyserEventOn;

    @Override
    public void run() {
        geyserEvent();
    }

    public void geyserEvent() {
        PluginImpl instance = PluginImpl.getInstance();
        Plugin plugin = instance.plugin;

        isGeyserEventOn = true;

        for (Player player : Bukkit.getOnlinePlayers()) {
            player.sendMessage("Os gêiseres estão em erupção! Aproveite para pescar mais rápido!");

            new BukkitRunnable() {
                @Override
                public void run() {
                    cancelGeyserEvent();
                    isGeyserEventOn = false;
                    player.sendMessage("Os gêiseres não estão mais em erupção!");
                }
            }.runTaskLater(plugin, 20L * instance.Config.getConfig().getInt("events.geyser.duration"));
        }

        geyserRunnable = new BukkitRunnable() {
            double height = 0;
            double coneWidth = 20.0; // Cone width
            double coneHeight = 25.0; // Cone height
            int tickCounter = 0;

            private final Particle.DustOptions[] colorGradients = {
                    new Particle.DustOptions(Color.fromRGB(0, 119, 255), 8.0F),   // Blue
                    new Particle.DustOptions(Color.fromRGB(135, 206, 250), 6.0F), // Light Sky Blue
                    new Particle.DustOptions(Color.fromRGB(0, 191, 255), 7.0F),   // Deep Sky Blue
                    new Particle.DustOptions(Color.fromRGB(173, 216, 230), 5.0F)  // Light Blue
            };

            @Override
            public void run() {
                World world = Bukkit.getWorld("pesca");
                Location coneLocation = new Location(world, 3, -6, 21);

                double currentRadius = (height / coneHeight) * (coneWidth / 2);
                int particleDensity = (int) (50 + 25 * Math.sin(tickCounter * 0.1));

                for (int i = 0; i < particleDensity; i++) {
                    double r = currentRadius * Math.pow(Math.random(), 0.5);
                    double theta = Math.random() * 2 * Math.PI;

                    double x = r * Math.cos(theta);
                    double z = r * Math.sin(theta);

                    double distanceFromCenter = Math.sqrt(x * x + z * z);
                    double normalizedDistance = distanceFromCenter / currentRadius;
                    double velocityFactor = Math.pow(1.0 - normalizedDistance, 1.5);

                    Location particleLocation = coneLocation.clone().add(x, height, z);
                    Vector velocity = new Vector(0, -1.2 * (1 + Math.random() * 0.2), 0);

                    Particle.DustOptions currentColor = colorGradients[tickCounter % colorGradients.length];

                    assert world != null;
                    world.spawnParticle(
                            Particle.REDSTONE,
                            particleLocation,
                            2,
                            0, velocity.getX(), velocity.getY(), velocity.getZ(),
                            currentColor
                    );

                    if (Math.random() < 0.1) {
                        world.spawnParticle(
                                Particle.WATER_SPLASH,
                                particleLocation,
                                1,
                                0.2, 0.2, 0.2, 0.1
                        );
                    }
                }

                height = coneHeight * Math.sin(tickCounter * 0.05);
                tickCounter++;

                if (tickCounter > 200) {
                    tickCounter = 0;
                }
            }
        };

        geyserRunnable.runTaskTimer(plugin, 0L, 40L);
    }

    public void cancelGeyserEvent() {
        if (geyserRunnable != null) {
            geyserRunnable.cancel();
            geyserRunnable = null;
        }
    }

}
