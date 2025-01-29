package blizzard.development.mine.commands.mine.subcommands.admins;

import blizzard.development.mine.database.cache.methods.ToolCacheMethods;
import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.*;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandAlias("mina|mineracao|mine")
@CommandPermission("blizzard.mine.admin")
public class SettersCommand extends BaseCommand {

    @Subcommand("setmeteor")
    @CommandPermission("blizzard.mine.admin")
    @CommandCompletion("@players")
    @Syntax("<jogador> <nivel>")
    public void onSetMeteor(CommandSender commandSender, String target, int amount) {
        Player targetPlayer = Bukkit.getPlayer(target);
        if (targetPlayer == null) {
            commandSender.sendActionBar(Component.text("§c§lEI! §cO jogador inserido é inválido ou não está online"));
            return;
        }

        ToolCacheMethods.getInstance().setMeteor(targetPlayer, amount);

        commandSender.sendActionBar(Component.text(
                "§a§lYAY! §aVocê setou o encantamento do jogador §7"
                        + targetPlayer.getName()
                        + "§a para o nível §7"
                        + amount + "§a."
            ));

        }
    }

