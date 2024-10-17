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
            player.sendMessage("começou nevasca");

            Bukkit.getScheduler().runTaskLater(plugin, () -> {
                world.setStorm(false);
                isSnowing = false;
                player.sendMessage("acabou nevasca");
            }, 20L * duration);
        }



    }
}
