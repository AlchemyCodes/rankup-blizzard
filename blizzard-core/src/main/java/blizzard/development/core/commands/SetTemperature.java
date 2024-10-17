package blizzard.development.core.commands;

import blizzard.development.core.database.cache.PlayersCacheManager;
import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Default;
import co.aikar.commands.annotation.Syntax;
import org.bukkit.entity.Player;

@CommandAlias("temperature")
public class SetTemperature extends BaseCommand {

    @Default
    public void onCommand(Player player, double temperature) {
        PlayersCacheManager.setTemperature(player, temperature);
    }
}
