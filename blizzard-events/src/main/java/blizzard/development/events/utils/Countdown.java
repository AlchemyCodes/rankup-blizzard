package blizzard.development.events.utils;

import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class Countdown {
    private static Countdown instance;

    private final Map<String, Long> rawMap = new HashMap<>();

    public void createCountdown(Player player, String name, long time, TimeUnit unit) {
        createCountdown(player.getUniqueId() + ";" + name, time, unit);
    }

    public void createCountdown(String name, long time, TimeUnit unit) {
        rawMap.put(name, System.currentTimeMillis() + unit.toMillis(time));
    }

    public boolean isInCountdown(Player player, String name) {
        return isInCountdown(player.getUniqueId() + ";" + name);
    }

    public boolean isInCountdown(String name) {
        return System.currentTimeMillis() < rawMap.getOrDefault(name, 0L);
    }

    public long getCountdownTime(Player player, String name) {
        return rawMap.get(player.getUniqueId() + ";" + name) - System.currentTimeMillis();
    }

    public long getCountdownTime(Player player, String name, TimeUnit unit) {
        return unit.convert(getCountdownTime(player, name), TimeUnit.MILLISECONDS);
    }

    public void clear() {
        rawMap.clear();
    }

    public static Countdown getInstance() {
        if (instance == null) instance = new Countdown();

        return instance;
    }
}