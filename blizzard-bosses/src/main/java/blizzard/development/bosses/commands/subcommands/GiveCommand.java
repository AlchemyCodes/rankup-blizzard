package blizzard.development.bosses.commands.subcommands;

import blizzard.development.bosses.handlers.eggs.BigFootEgg;
import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.*;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandAlias("bosses|boss|monstros|monstro")
public class GiveCommand extends BaseCommand {

    @Subcommand("giveboss")
    @Syntax("<player> <boss> <amount>")
    @CommandCompletion("@players @bosses @amount")
    @CommandPermission("blizzard.bosses.admin")
    public void onCommand(CommandSender sender, String name, String boss, Integer amount) {

        Player target = Bukkit.getPlayer(name);
        if (target == null) {
            sender.sendMessage("§c§lEI! §cO jogador é inválido ou está offline.");
            return;
        }

        switch (boss) {
            case "bigfoot", "pegrande" -> BigFootEgg.give(target, amount);
            default -> sender.sendMessage("§cBosses disponíveis: §7bigfoot");
        }

        sender.sendMessage("§a§lYAY! §aVocê deu §fx" + amount + " boss(es) §f" + boss + "§a para o jogador §f" + target.getName() + "!");
    }
}
