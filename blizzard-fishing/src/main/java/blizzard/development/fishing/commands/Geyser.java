//package blizzard.development.fishing.commands;
//
//import blizzard.development.fishing.utils.PluginImpl;
//import co.aikar.commands.BaseCommand;
//import co.aikar.commands.annotation.CommandAlias;
//import co.aikar.commands.annotation.Default;
//import org.bukkit.Bukkit;
//import org.bukkit.Location;
//import org.bukkit.Particle;
//import org.bukkit.World;
//import org.bukkit.entity.Player;
//import org.bukkit.scheduler.BukkitRunnable;
//import org.bukkit.Particle.DustOptions;
//import org.bukkit.Color;
//import org.bukkit.util.Vector;
//
//@CommandAlias("geyser")
//public class Geyser extends BaseCommand {
//
//    @Default
//    public void onCommand(Player player) {
//        new BukkitRunnable() {
//            double height = 0;
//            double coneWidth = 20.0; // Cone width
//            double coneHeight = 25.0; // Cone height
//            int tickCounter = 0;
//
//            // Add multiple color gradients for more visual interest
//            private final DustOptions[] colorGradients = {
//                    new DustOptions(Color.fromRGB(0, 119, 255), 8.0F),   // Blue
//                    new DustOptions(Color.fromRGB(135, 206, 250), 6.0F), // Light Sky Blue
//                    new DustOptions(Color.fromRGB(0, 191, 255), 7.0F),   // Deep Sky Blue
//                    new DustOptions(Color.fromRGB(173, 216, 230), 5.0F)  // Light Blue
//            };
//
//            @Override
//            public void run() {
//                World world = Bukkit.getWorld("pesca");
//                Location coneLocation = new Location(world, 3, -6, 21);
//
//                // Calculate the radius at the current height with slower rise
//                double currentRadius = (height / coneHeight) * (coneWidth / 2);
//
//                // Increase particle density and add variation
//                int particleDensity = (int) (50 + 25 * Math.sin(tickCounter * 0.1)); // Reduced frequency
//
//                for (int i = 0; i < particleDensity; i++) {
//                    // Generate point within a circle with improved distribution
//                    double r = currentRadius * Math.pow(Math.random(), 0.5);
//                    double theta = Math.random() * 2 * Math.PI;
//
//                    // Calculate coordinates with more natural spread
//                    double x = r * Math.cos(theta);
//                    double z = r * Math.sin(theta);
//
//                    // Calculate distance from center with smoother velocity transition
//                    double distanceFromCenter = Math.sqrt(x*x + z*z);
//                    double normalizedDistance = distanceFromCenter / currentRadius;
//
//                    // More nuanced velocity calculation
//                    double velocityFactor = Math.pow(1.0 - normalizedDistance, 1.5);
//
//                    Location particleLocation = coneLocation.clone().add(x, height, z);
//
//                    // Velocity now explicitly falls downward
//                    Vector velocity = new Vector(
//                            0, // X velocity set to 0
//                            -1.2 * (1 + Math.random() * 0.2), // Negative Y for downward fall
//                            0  // Z velocity set to 0
//                    );
//
//                    // Cycle through color gradients for more dynamic effect
//                    DustOptions currentColor = colorGradients[tickCounter % colorGradients.length];
//
//                    assert world != null;
//                    world.spawnParticle(
//                            Particle.REDSTONE,
//                            particleLocation,
//                            2,
//                            0, velocity.getX(), velocity.getY(), velocity.getZ(),
//                            currentColor
//                    );
//
//                    // Optional: Add occasional additional particle types for depth
//                    if (Math.random() < 0.1) {
//                        world.spawnParticle(
//                                Particle.WATER_SPLASH,
//                                particleLocation,
//                                1,
//                                0.2, 0.2, 0.2, 0.1
//                        );
//                    }
//                }
//
//                // Slower height progression with sine wave
//                height = coneHeight * Math.sin(tickCounter * 0.05); // Reduced frequency for slower rise
//
//                tickCounter++;
//
//                // Reset after a complete cycle
//                if (tickCounter > 200) {
//                    tickCounter = 0;
//                }
//            }
//        }.runTaskTimer(PluginImpl.getInstance().plugin, 0L, 2L);
//    }
//}