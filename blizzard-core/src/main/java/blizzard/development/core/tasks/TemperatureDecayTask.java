package blizzard.development.core.tasks;

import blizzard.development.core.api.CoreAPI;
import blizzard.development.core.clothing.ClothingType;
import blizzard.development.core.database.cache.PlayersCacheManager;
import blizzard.development.core.utils.PluginImpl;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

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
        double playerTemperature = PlayersCacheManager.getInstance().getTemperature(player);
        double freezing = config.getDouble("temperature.freezingTemperature");
        double maxWithBlizzard = config.getDouble("temperature.maxWithBlizzard");
        double max = config.getDouble("temperature.max");

        CoreAPI instance = CoreAPI.getInstance();

        if (playerTemperature <= freezing && instance.isIsBlizzard()) return;

        if (!instance.isIsBlizzard()) {
            if (playerTemperature >= max) return;
            PlayersCacheManager.getInstance().setTemperature(player, playerTemperature + temperatureDecay);
            return;
        }

        if (instance.isIsBlizzard() && playerTemperature >= maxWithBlizzard) return;

        if (instance.hasGeneratorOn(player) && playerTemperature < maxWithBlizzard) {
            PlayersCacheManager.getInstance().setTemperature(player, playerTemperature + temperatureDecay);
            return;
        }

        PlayersCacheManager.getInstance().setTemperature(player, playerTemperature - temperatureDecay);
    }

    public static long getTime(Player player, YamlConfiguration config) {
        double timeToDecay = config.getDouble("temperature.timeToDecay");
        String playerClothing = PlayersCacheManager.getInstance().getPlayerClothing(player);

        long time = (long) timeToDecay;
        boolean isBlizzard = CoreAPI.getInstance().isIsBlizzard();

        if (isBlizzard) {
            time /= 2L;
        }

        switch (ClothingType.valueOf(playerClothing)) {
            case COMMON -> time = isBlizzard
                    ? time + (long) (time * (config.getInt("clothes.common.percentage") / 100.0))
                    : time - (long) (time * (config.getInt("clothes.common.percentage") / 100.0));
            case RARE -> time = isBlizzard
                    ? time + (long) (time * (config.getInt("clothes.rare.percentage") / 100.0))
                    : time - (long) (time * (config.getInt("clothes.rare.percentage") / 100.0));
            case MYSTIC -> time = isBlizzard
                    ? time + (long) (time * (config.getInt("clothes.mystic.percentage") / 100.0))
                    : time - (long) (time * (config.getInt("clothes.mystic.percentage") / 100.0));
            case LEGENDARY -> time = isBlizzard
                    ? time + (long) (time * (config.getInt("clothes.legendary.percentage") / 100.0))
                    : time - (long) (time * (config.getInt("clothes.legendary.percentage") / 100.0));
        }
        return time;
    }

}
