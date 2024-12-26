package blizzard.development.events.commands.events;

import blizzard.development.events.managers.SumoManager;
import blizzard.development.events.utils.PluginImpl;
import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.Default;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.util.Objects;

@CommandAlias("sumo")
public class SumoCommand extends BaseCommand {

    @Default
    public void onCommand(Player player) {
        SumoManager instance = SumoManager.getInstance();
        YamlConfiguration messagesConfig = PluginImpl.getInstance().Messages.getConfig();

        if (!instance.isSumoActive) {
            player.sendMessage(Objects.requireNonNull(messagesConfig.getString("events.sumo.sumoNotActive")));
            return;
        }

        if (instance.isInGame.contains(player)) {
            player.sendMessage(Objects.requireNonNull(messagesConfig.getString("events.sumo.sumoInGame")));
        }

        if (instance.players.contains(player)) {
            instance.players.remove(player);
            instance.teleportToSpawn(player);
            player.sendMessage(Objects.requireNonNull(messagesConfig.getString("events.sumo.sumoLeave")));
            return;
        }

        instance.players.add(player);
        instance.teleportToSumoArena(player);
        player.sendMessage(Objects.requireNonNull(messagesConfig.getString("events.sumo.sumoEnter")));

    }
}
