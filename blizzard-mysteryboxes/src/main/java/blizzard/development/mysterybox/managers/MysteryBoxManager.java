package blizzard.development.mysterybox.managers;

import blizzard.development.mysterybox.mysterybox.enums.MysteryBoxEnum;
import blizzard.development.mysterybox.utils.PluginImpl;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Transformation;
import org.joml.Quaternionf;
import org.joml.Vector3f;

public class MysteryBoxManager {

    private static final MysteryBoxManager instance = new MysteryBoxManager();
    public static MysteryBoxManager getInstance() {
        return instance;
    }

    public void startAnimation(Player player, MysteryBoxEnum mysteryBoxEnum, Location location) {
        ItemDisplay display = (ItemDisplay) location.getWorld().spawn(location.add(0, 1, 0), ItemDisplay.class);

        ItemStack itemStack = null;
        String displayName = null;

        switch (mysteryBoxEnum) {
            case RARE:
                itemStack = new ItemStack(Material.CHEST);
                displayName = "§eCaixa Rara";
                break;
            case LEGENDARY:
                itemStack = new ItemStack(Material.ENDER_CHEST);
                displayName = "§dCaixa Lendária";
                break;
            case BLIZZARD:
                itemStack = new ItemStack(Material.SNOWBALL);
                displayName = "§bCaixa Blizzard";
                break;
            default:
                player.sendMessage("Contate um administrador.");
        }

        display.setItemStack(itemStack);
        display.setCustomName(displayName);
        display.setCustomNameVisible(true);


        Quaternionf initialRotation = new Quaternionf().rotateXYZ(0, 0, 0);
        display.setTransformation(new Transformation(
            new Vector3f(0f, 0f, 0f),
            initialRotation,
            new Vector3f(0.6f, 0.6f, 0.6f),
            new Quaternionf()
        ));

        new BukkitRunnable() {
            int ticks = 0;

            final double speed = 0.09;
            final int duration = 30;
            final double radius = 2.5;

            @Override
            public void run() {
                if (ticks >= duration) {
                    display.remove();
                    player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1.0f, 1.0f);
                    player.spawnParticle(Particle.EXPLOSION_LARGE, display.getLocation(), 1);

                    RewardManager.getInstance()
                        .reward(
                            player,
                            mysteryBoxEnum
                        );
                    this.cancel();
                    return;
                }

                float angle = ticks * 0.3f;

                Quaternionf rotation = new Quaternionf().rotateY(angle);
                display.setTransformation(new Transformation(
                    new Vector3f(0f, 0f, 0f),
                    rotation,
                    new Vector3f(0.5f, 0.5f, 0.5f),
                    new Quaternionf()
                ));

                double deltaY = speed * ticks;
                Location currentLoc = location.clone().add(0.5, deltaY, 0.5);
                display.teleport(currentLoc);

                if (ticks % 2 == 0) {
                    double spiralAngle = ticks * 0.5;
                    double spiralX = Math.cos(spiralAngle) * (radius * 0.5);
                    double spiralZ = Math.sin(spiralAngle) * (radius * 0.5);
                    Location spiralLoc = currentLoc.clone().add(spiralX, 0, spiralZ);
                    player.getWorld().spawnParticle(Particle.SPELL_WITCH, spiralLoc, 1, 0, 0, 0, 0);
                }

                if (ticks % 10 == 0) {
                    player.playSound(currentLoc, Sound.BLOCK_NOTE_BLOCK_PLING, 0.5f, 2.0f);
                }

                ticks++;
            }
        }.runTaskTimer(PluginImpl.getInstance().plugin, 0L, 1L);
    }
}