package blizzard.development.core.tasks;

import blizzard.development.core.api.CoreAPI;
import blizzard.development.core.utils.PluginImpl;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class BlizzardTask implements Runnable {
    private final Plugin plugin = PluginImpl.getInstance().plugin;
    private final YamlConfiguration config = PluginImpl.getInstance().Config.getConfig();

    public BlizzardTask() {
        int time = this.config.getInt("blizzard.time");
        this.plugin.getServer().getScheduler().runTaskTimer(this.plugin, this, 20L * time, 20L * time);
    }

    @Override
    public void run() {
        int duration = this.config.getInt("blizzard.duration");

        CoreAPI instance = CoreAPI.getInstance();
            instance.startBlizzard();
            Bukkit.getScheduler().runTaskLater(this.plugin, instance::stopBlizzard, 20L * duration);

    }
}
