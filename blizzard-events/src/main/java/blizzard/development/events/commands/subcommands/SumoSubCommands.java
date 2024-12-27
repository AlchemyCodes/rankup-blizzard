package blizzard.development.events.commands.subcommands;

import blizzard.development.events.utils.PluginImpl;
import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Subcommand;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

@CommandAlias("sumo")
public class SumoSubCommands extends BaseCommand {

    @Subcommand("setlocation")
    @CommandPermission("events.admin")
    public void setLocations(Player player, String location) {
        YamlConfiguration locationsConfig = PluginImpl.getInstance().Locations.getConfig();

        switch (location) {
            case "arena" -> {
                locationsConfig.set("events.sumo.arena.world", player.getWorld().getName());
                locationsConfig.set("events.sumo.arena.x", player.getLocation().getX());
                locationsConfig.set("events.sumo.arena.y", player.getLocation().getY());
                locationsConfig.set("events.sumo.arena.z", player.getLocation().getZ());
                locationsConfig.set("events.sumo.arena.yaw", player.getLocation().getYaw());
                locationsConfig.set("events.sumo.arena.pitch", player.getLocation().getPitch());
                PluginImpl.getInstance().Locations.saveConfig();
            }

            case "player1" -> {
                locationsConfig.set("events.sumo.player1.world", player.getWorld().getName());
                locationsConfig.set("events.sumo.player1.x", player.getLocation().getX());
                locationsConfig.set("events.sumo.player1.y", player.getLocation().getY());
                locationsConfig.set("events.sumo.player1.z", player.getLocation().getZ());
                locationsConfig.set("events.sumo.player1.yaw", player.getLocation().getYaw());
                locationsConfig.set("events.sumo.player1.pitch", player.getLocation().getPitch());
                PluginImpl.getInstance().Locations.saveConfig();
            }

            case "player2" -> {
                locationsConfig.set("events.sumo.player2.world", player.getWorld().getName());
                locationsConfig.set("events.sumo.player2.x", player.getLocation().getX());
                locationsConfig.set("events.sumo.player2.y", player.getLocation().getY());
                locationsConfig.set("events.sumo.player2.z", player.getLocation().getZ());
                locationsConfig.set("events.sumo.player2.yaw", player.getLocation().getYaw());
                locationsConfig.set("events.sumo.player2.pitch", player.getLocation().getPitch());
                PluginImpl.getInstance().Locations.saveConfig();
            }

        }
    }
}
