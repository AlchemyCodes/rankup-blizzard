package blizzard.development.core.tasks;

import blizzard.development.core.managers.CampfireManager;
import blizzard.development.core.utils.PluginImpl;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class BlizzardTask implements Runnable {
    private final Plugin plugin = PluginImpl.getInstance().plugin;
    private final YamlConfiguration config = PluginImpl.getInstance().Config.getConfig();
    public static boolean isSnowing;

    public BlizzardTask() {
        int time = this.config.getInt("blizzard.time");
        this.plugin.getServer().getScheduler().runTaskTimer(this.plugin, this, 20L * time, 20L * time);
    }

    @Override
    public void run() {
        int duration = this.config.getInt("blizzard.duration");

        for (Player player : Bukkit.getOnlinePlayers()) {
            World world = player.getWorld();

            world.setStorm(true);
            isSnowing = true;
            player.sendTitle("§b§lO FRIO CHEGOU.", "§fProteja-se, a tempestade de neve chegou.", 10, 70, 20);

            Bukkit.getScheduler().runTaskLater(this.plugin, () -> {
                world.setStorm(false);
                isSnowing = false;
                CampfireManager.removeCampfire(player);
                player.sendTitle("§b§lA TEMPESTADE PASSOU.", "§fAproveite a calmaria antes que ela volte.", 10, 70, 20);
            }, 20L * duration);
        }
    }
}
