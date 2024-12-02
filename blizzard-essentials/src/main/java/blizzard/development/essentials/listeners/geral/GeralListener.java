package blizzard.development.essentials.listeners.geral;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.weather.ThunderChangeEvent;
import org.bukkit.event.weather.WeatherChangeEvent;
import org.bukkit.event.world.TimeSkipEvent;


public class GeralListener implements Listener {

    @EventHandler
    public void onWeatherChange(WeatherChangeEvent event) {
        if (event.toWeatherState()) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onThunderChange(ThunderChangeEvent event) {
        if (event.toThunderState()) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onTimeSkip(TimeSkipEvent event) {
        if (event.getSkipReason() == TimeSkipEvent.SkipReason.NIGHT_SKIP) {
            event.setCancelled(true);
        }
    }

}
