package blizzard.development.core.tasks;

import blizzard.development.core.clothing.ClothingType;
import blizzard.development.core.database.cache.PlayersCacheManager;
import blizzard.development.core.managers.CampfireManager;
import blizzard.development.core.utils.PluginImpl;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class TemperatureDecayTask {

    public static Map<UUID, Integer> playerTasks = new HashMap<>();

    public static void startPlayerRunnable(Player player) {
        UUID playerUUID = player.getUniqueId();
        YamlConfiguration config = PluginImpl.getInstance().Config.getConfig();

        int taskId = Bukkit.getScheduler().runTaskTimer(PluginImpl.getInstance().plugin,
                () -> decayTemperature(player, config), 0L, 20L * getTime(player, config)).getTaskId();

        playerTasks.put(playerUUID, taskId);
    }

    public static void stopPlayerRunnable(Player player) {
        UUID playerUUID = player.getUniqueId();
        if (playerTasks.containsKey(playerUUID)) {
            Bukkit.getScheduler().cancelTask(playerTasks.get(playerUUID));
            playerTasks.remove(playerUUID);
        }

    }

    public static void decayTemperature(Player player, YamlConfiguration config) {
        double temperatureDecay = config.getDouble("temperature.temperatureDecay");
        double playerTemperature = PlayersCacheManager.getTemperature(player);
        int freezing = config.getInt("temperature.freezingTemperature");

        if (!BlizzardTask.isSnowing && playerTemperature <= 5) return;

        if (CampfireManager.hasCampfire(player)) {
            if (!BlizzardTask.isSnowing && playerTemperature >= 5) return;

            PlayersCacheManager.setTemperature(player, playerTemperature + temperatureDecay);

            return;
        }

        if (BlizzardTask.isSnowing && playerTemperature <= freezing) {
            PlayersCacheManager.setTemperature(player, freezing);

            player.damage(config.getDouble("temperature.damage"));

            return;
        }

        PlayersCacheManager.setTemperature(player, playerTemperature - temperatureDecay);
    }

    public static long getTime(Player player, YamlConfiguration config) {
        double timeToDecay = config.getDouble("temperature.timeToDecay");
        String playerClothing = PlayersCacheManager.getPlayerClothing(player);

        long time = switch (ClothingType.valueOf(playerClothing)) {
            case COMMON -> (long) (timeToDecay + timeToDecay * config.getDouble("clothes.common.percentage") / 100);
            case RARE -> (long) (timeToDecay + timeToDecay * config.getDouble("clothes.rare.percentage") / 100);
            case MYSTIC -> (long) (timeToDecay + timeToDecay * config.getDouble("clothes.mystic.percentage") / 100);
            case LEGENDARY -> (long) (timeToDecay + timeToDecay * config.getDouble("clothes.legendary.percentage") / 100);
            default -> (long) timeToDecay;
        };

        if (BlizzardTask.isSnowing) {
            time = time / 2;
            return time;
        }

        return time;
    }
}
