package blizzard.development.fishing.tasks;

import blizzard.development.fishing.utils.PluginImpl;
import org.bukkit.*;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.List;

public class EventsTask implements Runnable {

    public EventsTask(Plugin main) {
        main.getServer()
                .getScheduler()
                .runTaskTimer(main, this, 0, 20L * main.getConfig().getInt("events.time"));
    }

    private BukkitRunnable geyserRunnable;
    public static Boolean isGeyserActive = false;

    @Override
    public void run() {
        geyserEvent();
    }

    public void geyserEvent() {
        PluginImpl instance = PluginImpl.getInstance();
        Plugin plugin = instance.plugin;

        YamlConfiguration config = PluginImpl.getInstance().Messages.getConfig();

        for (Player player : Bukkit.getOnlinePlayers()) {
            if (!worldHasPlayer(player)) {
                return;
            }

            isGeyserActive = true;

            List<String> messages = config.getStringList("eventos.começarEventoGeyser");
            messages.forEach(player::sendMessage);

            new BukkitRunnable() {
                @Override
                public void run() {
                    cancelGeyserEvent();
                    isGeyserActive = false;

                    List<String> messages = config.getStringList("eventos.pararEventoGeyser");
                    messages.forEach(player::sendMessage);

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
                if (!isGeyserActive) {
                    cancel();
                    return;
                }

                YamlConfiguration locationsConfig = PluginImpl.getInstance().Locations.getConfig();

                World world = Bukkit.getWorld(locationsConfig.getString("geyser.world"));
                Location coneLocation = new Location(
                        world,
                        locationsConfig.getDouble("geyser.x"),
                        locationsConfig.getDouble("geyser.y"),
                        locationsConfig.getDouble("geyser.z"));

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

        geyserRunnable.runTaskTimer(plugin, 0L, 2L);
    }

    public void cancelGeyserEvent() {
        if (geyserRunnable != null) {
            geyserRunnable.cancel();
            geyserRunnable = null;
        }
    }

    public boolean worldHasPlayer(Player player) {
        return player.getWorld().getName().equals("pesca");
    }

}
