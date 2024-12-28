package blizzard.development.visuals.commands.visual;

import blizzard.development.visuals.visuals.adapters.VisualAdapter;
import blizzard.development.visuals.visuals.enums.VisualEnum;
import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.*;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandAlias("skin|visual")
public class VisualCommand extends BaseCommand {

    @Default
    @CommandPermission("")
    public void onVisualCommand(CommandSender commandSender) {
        Player player = (Player) commandSender;

        player.sendMessage("comando errado ja ja faço a msg blz");
    }

    @Subcommand("give")
    @CommandPermission("")
    @Syntax("<player> <skin> <quantia>")
    @CommandCompletion("@players @skins @range:1-20")
    public void onVisualGiveCommand(CommandSender commandSender, String playerTarget, VisualEnum visualEnum, int amount) {
        Player player = (Player) commandSender;
        Player target = Bukkit.getPlayer(playerTarget);

        if (target == null) {
            player.sendActionBar("§c§lEI! §cO jogador está offline ou não existe.");
            return;
        }

        VisualAdapter
            .getInstance()
            .giveVisual(
                target,
                visualEnum,
                amount
            );
    }
}
