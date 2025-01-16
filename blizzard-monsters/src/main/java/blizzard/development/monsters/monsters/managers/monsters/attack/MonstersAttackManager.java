package blizzard.development.monsters.monsters.managers.monsters.attack;

import blizzard.development.monsters.monsters.managers.monsters.MonstersGeneralManager;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import java.util.Random;

public class MonstersAttackManager {
    private static MonstersAttackManager instance;
    private final Random random = new Random();

    public void attack(Player player, String monster, String displayName) {
        int attackType = random.nextInt(3);

        switch (attackType) {
            case 0:
                handleBlindness(player, monster, displayName);
                break;
            case 1:
                handlePoison(player, monster, displayName);
                break;
            case 2:
                handleSlowness(player, monster, displayName);
                break;
        }
    }

    private void handleBlindness(Player player, String monster, String displayName) {
        player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 100, 0));
        handleDamage(player, monster);

        player.playSound(player.getLocation(), Sound.ENTITY_ELDER_GUARDIAN_CURSE, 1.0f, 1.0f);
        player.sendActionBar("§3§lMonstros! §f✧ §7Você foi cegado por §l" + displayName + "§7.");

        Location location = player.getLocation();
        player.spawnParticle(Particle.SMOKE_NORMAL, location, 20, 0.5, 0.5, 0.5, 0.1);
    }

    private void handlePoison(Player player, String monster, String displayName) {
        player.addPotionEffect(new PotionEffect(PotionEffectType.POISON, 60, 1));
        handleDamage(player, monster);

        player.playSound(player.getLocation(), Sound.ENTITY_SPIDER_AMBIENT, 1.0f, 0.5f);
        player.sendActionBar("§3§lMonstros! §f✧ §7Você foi envenenado por §l" + displayName + "§7!");

        Location location = player.getLocation();
        player.spawnParticle(Particle.SLIME, location, 20, 0.5, 1.0, 0.5, 0);
    }

    private void handleSlowness(Player player, String monster, String displayName) {
        player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 80, 1));
        handleDamage(player, monster);

        player.playSound(player.getLocation(), Sound.ENTITY_WITHER_AMBIENT, 1.0f, 1.5f);
        player.sendActionBar("§3§lMonstros! §f✧ §7Você foi enfraquecido por §l" + displayName + "§7.");

        Location location = player.getLocation();
        player.spawnParticle(Particle.SPELL_WITCH, location, 30, 0.5, 0.5, 0.5, 0.1);
    }

    private void handleDamage(Player player, String monster) {
        double damage = MonstersGeneralManager.getInstance().getAttackDamage(monster);

        double newHealth = Math.max(0, player.getHealth() - damage);
        player.setHealth(newHealth);
    }

    public static MonstersAttackManager getInstance() {
        if (instance == null) instance = new MonstersAttackManager();
        return instance;
    }
}