package blizzard.development.mine.commands.mine.subcommands.admins;

import blizzard.development.mine.managers.mine.DisplayManager;
import blizzard.development.mine.mine.enums.LocationEnum;
import blizzard.development.mine.utils.locations.LocationUtils;
import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Subcommand;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandAlias("mina|mineracao|mine")
@CommandPermission("blizzard.mine.admin")
public class LocationsCommand extends BaseCommand {

    @Subcommand("setspawn")
    @CommandPermission("blizzard.mine.admin")
    public void onSetSpawnCommand(CommandSender commandSender) {
        Player player = (Player) commandSender;

        LocationUtils.setLocation(
                player,
                LocationEnum.SPAWN.getName(),
                player.getLocation()
        );
    }

    @Subcommand("setsaida")
    @CommandPermission("blizzard.mine.admin")
    public void onSetExitCommand(CommandSender commandSender) {
        Player player = (Player) commandSender;

        LocationUtils.setLocation(
                player,
                LocationEnum.EXIT.getName(),
                player.getLocation()
        );
    }

    @Subcommand("setcenter")
    @CommandPermission("blizzard.mine.admin")
    public void onSetCenterCommand(CommandSender commandSender) {
        Player player = (Player) commandSender;

        LocationUtils.setLocation(
                player,
                LocationEnum.CENTER.getName(),
                player.getLocation()
        );
    }

    @Subcommand("setnpc")
    @CommandPermission("blizzard.mine.admin")
    public void onSetNPCCommand(CommandSender commandSender) {
        Player player = (Player) commandSender;

        LocationUtils.setLocation(
                player,
                LocationEnum.NPC.getName(),
                player.getLocation()
        );
    }

    @Subcommand("setdisplay")
    @CommandPermission("blizzard.mine.admin")
    public void onSetDisplayCommand(CommandSender commandSender) {
        Player player = (Player) commandSender;

        LocationUtils.setLocation(
                player,
                LocationEnum.DISPLAY.getName(),
                player.getLocation().add(0, 2, 0)
        );

        DisplayManager.getInstance().createPickaxeDisplay(player.getLocation().add(0, 2, 0));
    }
}
