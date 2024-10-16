package blizzard.development.crates.managers;

import blizzard.development.crates.Main;
import blizzard.development.crates.builder.HologramBuilder;
import blizzard.development.crates.utils.item.skull.SkullUtils;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.EulerAngle;

import java.util.ArrayList;
import java.util.List;

import static blizzard.development.crates.builder.HologramBuilder.removeHologram;
import static org.bukkit.Bukkit.getServer;

public class CrateManager {

    private static final List<ArmorStand> armorStandList = new ArrayList<>();
    private static int taskId;

    public static void createCrate(Player p, Location loc, String hologramName, Particle particle, int count, String base64, String text, String metaData) {
        HologramBuilder hologramBuilder = new HologramBuilder();

        ArmorStand stand = (ArmorStand) p.getWorld().spawnEntity(loc, EntityType.ARMOR_STAND);

        double x = stand.getLocation().getX();
        double y = stand.getLocation().getY() + 2.2;
        double z = stand.getLocation().getZ();

        hologramBuilder.createHologram(hologramName, new Location(stand.getWorld(), x, y, z), text);
        stand.setCustomName(hologramName);
        stand.setCustomNameVisible(false);
        stand.setMetadata(metaData, new FixedMetadataValue(Main.getInstance(), true));
        stand.setInvisible(true);
        stand.setGravity(false);
        stand.setHelmet(new ItemStack(SkullUtils.fromBase64(SkullUtils.Type.ITEM, base64)));

        armorStandList.add(stand);

        taskId = new BukkitRunnable() {
            double rate = 1;
            double yStand = -1;
            double radius = 1.1;
            @Override
            public void run() {
                double cos = Math.cos(rate) * radius;
                double sin = Math.sin(rate) * radius;

                float x = (float) (loc.getX() + cos);
                float y = (float) (loc.getY() + 0.7);
                float z = (float) (loc.getZ() + sin);

                stand.setHeadPose(new EulerAngle(0, yStand, 0));


                p.getWorld().spawnParticle(particle, new Location(stand.getWorld(), x, y, z), count);

                rate += 0.3;
                yStand -= 0.2;

            }
        }.runTaskTimerAsynchronously(Main.instance, 0L, 0L).getTaskId();
    }

    public static void removeCrate() {

        for (ArmorStand stand : armorStandList) {
            if (stand == null) return;

            stand.remove();
            removeHologram();
            getServer().getScheduler().cancelTask(taskId);
        }

        armorStandList.clear();
    }

    public void loadCrates() {
        for (ArmorStand stand : Main.instance.getServer().getWorlds().stream()
                .flatMap(world -> world.getEntitiesByClass(ArmorStand.class).stream())
                .filter(stand -> stand.getCustomName() != null && stand.hasMetadata("lendaria") && stand.hasMetadata("mitica"))
                .toList()) {

            Location loc = stand.getLocation();
            double locX = loc.getX();
            double locY = loc.getY();
            double locZ = loc.getZ();

            new BukkitRunnable() {
                double graus = 1;
                double yStand = -1;
                double raio = 1.1;

                @Override
                public void run() {
                    double cos = Math.cos(graus) * raio;
                    double sin = Math.sin(graus) * raio;

                    float x = (float) (loc.getX() + cos);
                    float y = (float) (loc.getY() - 1);
                    float z = (float) (loc.getZ() + sin);

                    stand.setHeadPose(new EulerAngle(0, yStand, 0));

                    if (stand.hasMetadata("lendaria")) {
                        stand.getWorld().spawnParticle(Particle.LAVA, new Location(stand.getWorld(), x, y, z), 0);
                    } else if (stand.hasMetadata("mitica")) {
                        stand.getWorld().spawnParticle(Particle.SNOWBALL, new Location(stand.getWorld(), x, y, z), 1);
                    }


                    graus += 0.3;
                    yStand -= 0.2;
                }
            }.runTaskTimerAsynchronously(Main.instance, 0L, 0L);
        }
    }
}
