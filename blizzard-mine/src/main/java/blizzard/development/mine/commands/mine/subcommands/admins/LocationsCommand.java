package blizzard.development.mine.commands.mine.subcommands.admins;

import blizzard.development.mine.builders.display.ExtractorBuilder;
import blizzard.development.mine.builders.hologram.HologramBuilder;
import blizzard.development.mine.managers.mine.DisplayManager;
import blizzard.development.mine.mine.adapters.PodiumAdapter;
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
                player.getLocation().add(0.5, 0, 0.5)
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

    @Subcommand("setextractor")
    @CommandPermission("blizzard.mine.admin")
    public void onSetExtractorNPCCommand(CommandSender commandSender) {
        Player player = (Player) commandSender;

        LocationUtils.setLocation(
                player,
                LocationEnum.EXTRACTOR_NPC.getName(),
                player.getLocation()
        );

        ExtractorBuilder.getInstance()
                .createExtractor(
                        player.getLocation()
                );
    }


    @Subcommand("removeextractornpc")
    public void onRemoveExtractorNPC(CommandSender commandSender) {
        Player player = (Player) commandSender;
        ExtractorBuilder.getInstance().removeExtractor();
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

    @Subcommand("setpodiumtop1")
    @CommandPermission("blizzard.mine.admin")
    public void onSetPodiumTop1Command(CommandSender commandSender) {
        Player player = (Player) commandSender;

        PodiumAdapter.getInstance()
            .topOne(
                player.getLocation()
            );

        LocationUtils.setLocation(
                player,
                LocationEnum.TOP_ONE_NPC.getName(),
                player.getLocation()
        );
    }

    @Subcommand("setpodiumtop2")
    @CommandPermission("blizzard.mine.admin")
    public void onSetPodiumTop2Command(CommandSender commandSender) {
        Player player = (Player) commandSender;

        PodiumAdapter.getInstance()
            .topTwo(
                player.getLocation()
            );

        LocationUtils.setLocation(
                player,
                LocationEnum.TOP_TWO_NPC.getName(),
                player.getLocation()
        );
    }

    @Subcommand("setpodiumtop3")
    @CommandPermission("blizzard.mine.admin")
    public void onSetPodiumTop3Command(CommandSender commandSender) {
        Player player = (Player) commandSender;

        PodiumAdapter.getInstance()
            .topTree(
                 player.getLocation()
            );

        LocationUtils.setLocation(
                player,
                LocationEnum.TOP_TREE_NPC.getName(),
                player.getLocation()
        );
    }

    @Subcommand("removepodiumtops")
    @CommandPermission("blizzard.mine.admin")
    public void onRemovePodiumTopsCommand(CommandSender commandSender) {
        Player player = (Player) commandSender;

        PodiumAdapter.getInstance()
            .removeAllNPCs();
    }
}
