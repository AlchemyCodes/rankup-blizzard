package blizzard.development.mine.commands.mine.subcommands.admins;

import blizzard.development.mine.database.cache.methods.PlayerCacheMethods;
import blizzard.development.mine.mine.enums.BlockEnum;
import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.*;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.List;

@CommandAlias("mina|mineracao|mine")
@CommandPermission("blizzard.mine.admin")
public class SettersCommand extends BaseCommand {

    @Subcommand("setarea")
    @CommandPermission("blizzard.mine.admin")
    @CommandCompletion("@players")
    @Syntax("<jogador> <bloco>")
    public void onSetCenterCommand(CommandSender commandSender, String target, BlockEnum block) {
        Player targetPlayer = Bukkit.getPlayer(target);
        if (targetPlayer == null) {
            commandSender.sendActionBar(Component.text("§c§lEI! §cO jogador inserido é inválido ou não está online"));
            return;
        }

        List<BlockEnum> blocks = Arrays.stream(BlockEnum.values()).toList();

        if (blocks.contains(block)) {
            commandSender.sendActionBar(Component.text(
                    "§a§lYAY! §aVocê setou a area do jogador §7"
                            + targetPlayer.getName()
                            + "§a para o bloco de §7"
                            + block + "§a."
            ));
            PlayerCacheMethods.getInstance().setAreaBlock(targetPlayer, block);
        } else {
            Arrays.asList(
                    "",
                    " §c§lEI §cO bloco §7" + block + "§c não existe.",
                    " §cDisponíveis: §7" + blocks + "§c.",
                    ""
            ).forEach(commandSender::sendMessage);
        }
    }
}
