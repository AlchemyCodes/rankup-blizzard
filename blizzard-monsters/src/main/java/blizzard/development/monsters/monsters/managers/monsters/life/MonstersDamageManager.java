package blizzard.development.monsters.monsters.managers.monsters.life;

import blizzard.development.monsters.database.cache.methods.MonstersCacheMethods;
import blizzard.development.monsters.monsters.holograms.MonsterNameHologram;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import java.util.UUID;

public class MonstersDamageManager {
    private static MonstersDamageManager instance;

    public void dispatchDamage(Player player, String uuid, String displayName, Integer life) {
    }

    public void receiveDamage(Player player, Location location, String uuid, String displayName, Integer life, Integer damage) {
        MonstersCacheMethods methods = MonstersCacheMethods.getInstance();

        methods.setLife(uuid, life);

        UUID monsterUUID = UUID.fromString(uuid);

        MonsterNameHologram.getInstance().update(player, monsterUUID, displayName, life);

        handleParticles(player, location);

        player.sendActionBar("§3§lMonstros! §7✧ §fVocê removeu §c§l-§c" + damage + "§f da vida do monstro.");
        player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_ATTACK_STRONG, 1.0f, 1.0f);
    }

    private void handleParticles(Player player, Location location) {
        double radius = 0.8;
        for (int degree = 0; degree < 360; degree += 45) {
            double radians = Math.toRadians(degree);
            double x = Math.cos(radians) * radius + 0.5;
            double z = Math.sin(radians) * radius + 0.5;

            Location particleLocation = location.clone().add(x, -2.2, z);

            player.spawnParticle(
                    Particle.HEART,
                    particleLocation,
                    1,
                    0, 0, 0,
                    0
            );
        }
    }

    public static MonstersDamageManager getInstance() {
        if (instance == null) instance = new MonstersDamageManager();
        return instance;
    }
}
