package blizzard.development.mine.commands.mine.subcommands.admins;

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

        LocationUtils.setMineSpawnLocation(
                player,
                player.getWorld(),
                (int) player.getX(),
                (int) player.getY(),
                (int) player.getZ(),
                player.getYaw(),
                player.getPitch()
        );
    }

    @Subcommand("setcenter")
    @CommandPermission("blizzard.mine.admin")
    public void onSetCenterSpawnCommand(CommandSender commandSender) {
        Player player = (Player) commandSender;

        LocationUtils.setMineCenterLocation(
                player,
                player.getWorld(),
                (int) player.getX(),
                (int) player.getY(),
                (int) player.getZ(),
                player.getYaw(),
                player.getPitch()
        );
    }

    @Subcommand("setnpc")
    @CommandPermission("blizzard.mine.admin")
    public void onSetNPCCommand(CommandSender commandSender) {
        Player player = (Player) commandSender;
        player.sendActionBar("ainda nao tlg");
    }
}
