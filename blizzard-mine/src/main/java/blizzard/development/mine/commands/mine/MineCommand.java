package blizzard.development.mine.commands.mine;

import blizzard.development.mine.database.cache.methods.PlayerCacheMethod;
import blizzard.development.mine.mine.adapters.EnchantmentAdapter;
import blizzard.development.mine.mine.adapters.MineAdapter;
import blizzard.development.mine.mine.enums.BlockEnum;
import blizzard.development.mine.utils.locations.LocationUtils;
import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Default;
import co.aikar.commands.annotation.Subcommand;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandAlias("mina|mineracao|mine")
public class MineCommand extends BaseCommand {

    @Default
    public void onMineCommand(CommandSender commandSender) {
        Player player = (Player) commandSender;

        MineAdapter
            .getInstance()
            .sendToMine(
                player
            );
    }

    @Subcommand("setspawn")
    @CommandPermission("alchemy.mine.setspawn")
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

    @Subcommand("setcenterspawn")
    @CommandPermission("alchemy.mine.setspawn")
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

    @Subcommand("dev")
    @CommandPermission("alchemy.mine.dev")
    public void onDevCommand(CommandSender commandSender) {
        Player player = (Player) commandSender;

        PlayerCacheMethod.getInstance().setAreaBlock(player, BlockEnum.COBBLESTONE);

    }

    @Subcommand("dev2")
    @CommandPermission("alchemy.mine.dev")
    public void onDev2Command(CommandSender commandSender) {
        Player player = (Player) commandSender;


        PlayerCacheMethod.getInstance().setAreaBlock(player, BlockEnum.ANDESITE);
    }
}
