package blizzard.development.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import org.bukkit.entity.Player;

public class Countdown {
    private static Countdown instance;
    private final Map<String, Long> rawMap = new HashMap<>();

    public Countdown() {}

    public void createCountdown(Player player, String name, long time, TimeUnit unit) {
        this.createCountdown(player.getUniqueId() + ";" + name, time, unit);
    }

    public void createCountdown(String name, long time, TimeUnit unit) {
        this.rawMap.put(name, System.currentTimeMillis() + unit.toMillis(time));
    }

    public boolean isInCountdown(Player player, String name) {
        return this.isInCountdown(player.getUniqueId() + ";" + name);
    }

    public boolean isInCountdown(String name) {
        return System.currentTimeMillis() < this.rawMap.getOrDefault(name, 0L);
    }

    public long getCountdownTime(Player player, String name) {
        return this.rawMap.get(player.getUniqueId() + ";" + name) - System.currentTimeMillis();
    }

    public long getCountdownTime(Player player, String name, TimeUnit unit) {
        return unit.convert(this.getCountdownTime(player, name), TimeUnit.MILLISECONDS);
    }

    public void clear() {
        this.rawMap.clear();
    }

    public static Countdown getInstance() {
        if (instance == null) {
            instance = new Countdown();
        }

        return instance;
    }
}
