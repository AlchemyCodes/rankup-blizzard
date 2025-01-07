package blizzard.development.core.commands.temperature;

import blizzard.development.core.database.cache.PlayersCacheManager;
import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.Default;
import org.bukkit.entity.Player;

@CommandAlias("temperature")
public class TemperatureCommand extends BaseCommand {

    @Default
    public void onCommand(Player player, double temperature) {
        PlayersCacheManager.getInstance().setTemperature(player, temperature);
    }
}
