package blizzard.development.spawners.builders;

import blizzard.development.spawners.database.cache.managers.SpawnersCacheManager;
import blizzard.development.spawners.database.storage.SpawnersData;
import blizzard.development.spawners.handlers.enums.Spawners;
import blizzard.development.spawners.utils.LocationUtil;
import blizzard.development.spawners.utils.PluginImpl;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.Map;

public class EffectsBuilder {
    private static final Map<String, BukkitRunnable> activeEffects = new HashMap<>();
    private static final SpawnersCacheManager cache = SpawnersCacheManager.getInstance();

    private static final Particle.DustTransition PIG_PARTICLE = new Particle.DustTransition(
            Color.fromRGB(255, 192, 203),
            Color.fromRGB(255, 182, 193),
            1.5f
    );

    private static final Particle.DustTransition COW_PARTICLE = new Particle.DustTransition(
            Color.fromRGB(255, 255, 255),
            Color.fromRGB(240, 240, 240),
            1.5f
    );

    private static final Particle.DustTransition MOOSHROOM_PARTICLE = new Particle.DustTransition(
            Color.fromRGB(255, 0, 0),
            Color.fromRGB(139, 0, 0),
            1.5f
    );

    private static final Particle.DustTransition SHEEP_PARTICLE = new Particle.DustTransition(
            Color.fromRGB(255, 255, 255),
            Color.fromRGB(255, 255, 255),
            1.5f
    );

    private static final Particle.DustTransition ZOMBIE_PARTICLE = new Particle.DustTransition(
            Color.fromRGB(0, 100, 0),
            Color.fromRGB(0, 50, 0),
            1.5f
    );


    private static String serializeLocation(Location location) {
        return location.getWorld().getName() + ":" + location.getBlockX() + ":" + location.getBlockY() + ":" + location.getBlockZ();
    }

    public static void createSpawnerEffect(Player player, Location location, String spawnerType) {
        if (location.getWorld() == null) {
            return;
        }

        String locationKey = serializeLocation(location);

        BukkitRunnable effectTask = new BukkitRunnable() {
            double angle = 0;

            @Override
            public void run() {
                if (!location.getChunk().isLoaded()) {
                    cancel();
                    activeEffects.remove(locationKey);
                    return;
                }

                World world = location.getWorld();
                Location center = location.clone().add(0.5, 0.5, 0.5);

                if (angle % 6 == 0) {
                    for (int i = 0; i < 2; i++) {
                        double radians = Math.toRadians(angle + (i * 180));
                        double x = center.getX() + (Math.cos(radians) * 0.5);
                        double z = center.getZ() + (Math.sin(radians) * 0.5);

                        Location particleLoc = new Location(world, x, center.getY() - 0.3, z);

                        if (spawnerType.equalsIgnoreCase(Spawners.PIG.getType())) {
                            player.spawnParticle(Particle.DUST_COLOR_TRANSITION,
                                    particleLoc,
                                    3,
                                    0, 0, 0, 0,
                                    PIG_PARTICLE
                            );
                        } else if (spawnerType.equalsIgnoreCase(Spawners.COW.getType())) {
                            player.spawnParticle(Particle.DUST_COLOR_TRANSITION,
                                    particleLoc,
                                    3,
                                    0, 0, 0, 0,
                                    COW_PARTICLE
                            );
                        } else if (spawnerType.equalsIgnoreCase(Spawners.MOOSHROOM.getType())) {
                            player.spawnParticle(Particle.DUST_COLOR_TRANSITION,
                                    particleLoc,
                                    3,
                                    0, 0, 0, 0,
                                    MOOSHROOM_PARTICLE
                            );
                        } else if (spawnerType.equalsIgnoreCase(Spawners.SHEEP.getType())) {
                            player.spawnParticle(Particle.DUST_COLOR_TRANSITION,
                                    particleLoc,
                                    3,
                                    0, 0, 0, 0,
                                    SHEEP_PARTICLE
                            );
                        } else if (spawnerType.equalsIgnoreCase(Spawners.ZOMBIE.getType())) {
                            player.spawnParticle(Particle.DUST_COLOR_TRANSITION,
                                    particleLoc,
                                    3,
                                    0, 0, 0, 0,
                                    ZOMBIE_PARTICLE
                            );
                        }
                    }

                    if (Math.random() < 0.5) {
                        double yOffset = Math.sin(angle / 20) * 0.2;
                        Location topLoc = center.clone().add(0, 0.9 + yOffset, 0);
                        player.spawnParticle(Particle.SPELL_INSTANT,
                                topLoc,
                                2,
                                0.15, 0, 0.15, 0);
                    }
                }

                angle += 3;
                if (angle >= 360) {
                    angle = 0;
                }
            }
        };

        effectTask.runTaskTimer(PluginImpl.getInstance().plugin, 0L, 4L);
        activeEffects.put(locationKey, effectTask);
    }

    public static void restorePlayerEffects(Player player) {
        String playerName = player.getName();
        for (SpawnersData spawnerData : cache.spawnersCache.values()) {
            if (spawnerData.getNickname().equals(playerName)) {
                Location location = LocationUtil.deserializeLocation(spawnerData.getLocation());
                if (location.getWorld() != null) {
                    String spawnerType = spawnerData.getType();
                    createSpawnerEffect(player, location, spawnerType);
                }
            }
        }
    }

    public static void removeSpawnerEffect(Location location) {
        String locationKey = serializeLocation(location);
        BukkitRunnable task = activeEffects.remove(locationKey);
        if (task != null) {
            task.cancel();
        }
    }
}
