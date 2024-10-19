package blizzard.development.core.managers;

import blizzard.development.core.database.cache.PlayersCacheManager;
import blizzard.development.core.utils.PluginImpl;
import org.bukkit.Bukkit;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.UUID;

public class BossBarManager {

    private static final HashMap<UUID, BossBar> playerBossBars = new HashMap<>();

    public static void createBossBar(Player player) {
        BossBar bossBar = Bukkit.createBossBar("Temperatura: " + PlayersCacheManager.getTemperature(player)
                + " °C", BarColor.BLUE, BarStyle.SEGMENTED_6);

        playerBossBars.put(player.getUniqueId(), bossBar);
        bossBar.addPlayer(player);
        startBossBarUpdate(player);
    }

    public static void removeBossBar(Player player) {
        BossBar bossBar = playerBossBars.remove(player.getUniqueId());
        if (bossBar != null) {
            bossBar.removePlayer(player);
        }
    }

    private static void startBossBarUpdate(Player player) {
        new BukkitRunnable() {
            @Override
            public void run() {
                if (!player.isOnline()) {
                    this.cancel();
                    removeBossBar(player);
                    return;
                }

                double temperature = PlayersCacheManager.getTemperature(player);
                updateBossBar(player, temperature);
            }
        }.runTaskTimer(PluginImpl.getInstance().plugin, 0L, 20L * 3);
    }

    private static void updateBossBar(Player player, double temperature) {
        BossBar bossBar = playerBossBars.get(player.getUniqueId());

        if (bossBar == null) return;

        double progress = Math.min(Math.max(temperature / 20.0, 0.0), 1.0);
        bossBar.setProgress(progress);

        if (temperature <= 0) {
            bossBar.setTitle("§b❄❄❄❄❄❄❄❄ Você está congelando! ❄❄❄❄❄❄❄❄");
        } else {
            bossBar.setTitle("§bTemperatura: §b§l" + temperature + " §b°C");
        }
    }
}
