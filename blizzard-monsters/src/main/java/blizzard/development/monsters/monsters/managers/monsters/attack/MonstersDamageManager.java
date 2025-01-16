package blizzard.development.monsters.monsters.managers.monsters.attack;

import blizzard.development.monsters.database.cache.methods.MonstersCacheMethods;
import blizzard.development.monsters.monsters.holograms.MonsterNameHologram;
import blizzard.development.monsters.monsters.managers.monsters.MonstersGeneralManager;
import blizzard.development.monsters.utils.CooldownUtils;
import blizzard.development.monsters.utils.PluginImpl;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class MonstersDamageManager {
    private static MonstersDamageManager instance;

    private final MonstersGeneralManager manager = MonstersGeneralManager.getInstance();

    private final Map<String, Long> lastAttackTime = new HashMap<>();

    public void dispatchDamage(Player player, String monster, String displayName) {
        long currentTime = System.currentTimeMillis();
        if (lastAttackTime.containsKey(monster)) {
            long timeSinceLastAttack = currentTime - lastAttackTime.get(monster);
            if (timeSinceLastAttack < 500) {
                return;
            }
        }

        double attackChance = manager.getAttackChance(monster);
        if (attackChance != 0) {
            double random = (Math.random() * 100);
            if (random < attackChance) {
                MonstersAttackManager.getInstance().attack(player, monster, displayName);
            }
            lastAttackTime.put(monster, currentTime);
        }
    }

    public void receiveDamage(Player player, Location location, String monster, String uuid, String displayName, Integer life, Integer damage) {
        CooldownUtils cooldown = CooldownUtils.getInstance();
        String cooldownName = "blizzard.monsters.hit-cooldown";

        if (cooldown.isInCountdown(player, cooldownName)) return;

        MonstersCacheMethods methods = MonstersCacheMethods.getInstance();

        methods.setLife(uuid, life);

        UUID monsterUUID = UUID.fromString(uuid);

        MonsterNameHologram.getInstance().update(player, monsterUUID, displayName, life);

        handleParticles(player, location);

        player.sendActionBar("§3§lMonstros! §f✧ §7Você removeu §c§l-§c" + damage + "§7 da vida do monstro.");

        handleSound(player, monster);

        cooldown.createCountdown(
                player,
                cooldownName,
                PluginImpl.getInstance().Config.getInt("monsters.hit-delay"),
                TimeUnit.MILLISECONDS
        );
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

    private void handleSound(Player player, String monster) {
        String soundName = manager.getDamageSound(monster);

        Sound sound;
        if (soundName != null) {
            sound = Sound.valueOf(soundName);
        } else {
            sound = Sound.ENTITY_ARROW_HIT;
        }

        player.playSound(player.getLocation(), sound, 1.0f, 1.0f);
    }

    public static MonstersDamageManager getInstance() {
        if (instance == null) instance = new MonstersDamageManager();
        return instance;
    }
}
