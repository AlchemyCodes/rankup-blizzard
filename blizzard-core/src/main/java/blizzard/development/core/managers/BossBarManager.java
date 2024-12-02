package blizzard.development.core.managers;

import blizzard.development.core.database.cache.PlayersCacheManager;
import blizzard.development.core.utils.NumberFormat;
import blizzard.development.core.utils.PluginImpl;
import java.util.HashMap;
import java.util.UUID;
import org.bukkit.Bukkit;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class BossBarManager {

    private static final HashMap<UUID, BossBar> playerBossBars = new HashMap<>();

    public static void createBossBar(Player player) {
        BossBar bossBar = Bukkit.createBossBar("Temperatura: " + NumberFormat.getInstance().formatNumber(PlayersCacheManager.getTemperature(player))  + " °C",
                BarColor.BLUE, BarStyle.SEGMENTED_6);
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

    private static void startBossBarUpdate(final Player player) {
        new BukkitRunnable() {
            public void run() {
                if (!player.isOnline()) {
                    cancel();
                    BossBarManager.removeBossBar(player);
                    return;
                }
                double temperature = PlayersCacheManager.getTemperature(player);
                BossBarManager.updateBossBar(player, temperature);
            }
        }.runTaskTimer(PluginImpl.getInstance().plugin, 0L, 60L);
    }

    private static void updateBossBar(Player player, double temperature) {
        BossBar bossBar = playerBossBars.get(player.getUniqueId());
        if (bossBar == null) return;

        double progress = Math.min(Math.max(temperature / 20.0D, 0.0D), 1.0D);
        bossBar.setProgress(progress);

        if (temperature <= 0.0D) {
            bossBar.setTitle("§b§lEI1 §bVocê está congelando!");
        } else {
            bossBar.setTitle("§bTemperatura: §b§l" + NumberFormat.getInstance().formatNumber(temperature) + " §b°C");
        }
    }
}
