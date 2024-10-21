package blizzard.development.core.tasks;

import blizzard.development.core.utils.PluginImpl;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class BlizzardTask implements Runnable {

    private final Plugin plugin;
    private final YamlConfiguration config;
    public static boolean isSnowing;

    public BlizzardTask() {
        plugin = PluginImpl.getInstance().plugin;
        config = PluginImpl.getInstance().Config.getConfig();

        int time = config.getInt("blizzard.time");

        plugin.getServer().getScheduler().runTaskTimer(plugin, this, 20L * time, 20L * time);
    }

    @Override
    public void run() {
        int duration = config.getInt("blizzard.duration");

        for (Player player : Bukkit.getOnlinePlayers()) {
            World world = player.getWorld();

            world.setStorm(true);
            isSnowing = true;
            player.sendTitle(
                    "§b§lO FRIO CHEGOU.",
                    "§fProteja-se, a tempestade de neve chegou.",
                    10,
                    70,
                    20
            );

            Bukkit.getScheduler().runTaskLater(plugin, () -> {
                world.setStorm(false);
                isSnowing = false;
                player.sendTitle(
                        "§b§lA TEMPESTADE PASSOU.",
                        "§fAproveite a calmaria antes que ela volte.",
                        10,
                        70,
                        20
                );
            }, 20L * duration);
        }



    }
}
