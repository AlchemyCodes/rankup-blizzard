package blizzard.development.essentials.commands.staff;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Default;
import co.aikar.commands.annotation.Syntax;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandAlias("sudo")
public class SudoCommand extends BaseCommand {

    @Default
    @CommandPermission("alchemy.essentials.staff")
    @Syntax("<jogador> <mensagem>")
    public void onCommand(CommandSender commandSender, String playerTarget, String[] messages) {

        Player target = Bukkit.getPlayer(playerTarget);

        StringBuilder announceBuilder = new StringBuilder();

        for (String arg : messages) {
            announceBuilder.append(arg).append(" ");
        }

        String message = announceBuilder.toString().trim();

        if (target == null) return;

        Bukkit.dispatchCommand(target, message);

        commandSender.sendActionBar(Component.text("§b§lYAY! §bComando sudo realizado com sucesso."));
    }
}
