package blizzard.development.core.tasks;

import blizzard.development.core.api.CoreAPI;
import blizzard.development.core.commands.ActiveCoreDebug;
import blizzard.development.core.database.cache.PlayersCacheManager;
import blizzard.development.core.utils.PluginImpl;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class CoreDebugTask implements Runnable {

    public CoreDebugTask() {
        Bukkit.getScheduler().runTaskTimerAsynchronously(PluginImpl.getInstance().plugin,
                this, 0L, 20L);
    }

    @Override
    public void run() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            if (!ActiveCoreDebug.debugHashMap.getOrDefault(player, true)) return;

            player.sendMessage(" ",
                    "§bCORE DEBUG",
                    "§7Temperatura: §b" + PlayersCacheManager.getInstance().getTemperature(player),
                    "§7Roupa: §b" + PlayersCacheManager.getInstance().getPlayerClothing(player),
                    "§7Bonus: §b" + CoreAPI.getInstance().getPlayerBonus(player),
                    "§7TempoTemperaturaDiminuir: " + TemperatureDecayTask.getTime(player, PluginImpl.getInstance().Config.getConfig()),
                    " ");

        }
    }
}
