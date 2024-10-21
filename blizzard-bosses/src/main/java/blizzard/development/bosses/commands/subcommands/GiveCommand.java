package blizzard.development.bosses.commands.subcommands;

import blizzard.development.bosses.handlers.eggs.BigFootEgg;
import blizzard.development.bosses.utils.items.apis.TextAPI;
import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.*;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.List;

@CommandAlias("bosses|boss|monstros|monstro")
public class GiveCommand extends BaseCommand {

    @Subcommand("giveboss")
    @Syntax("<player> <boss> <amount> <stack>")
    @CommandCompletion("@players @bosses @amount @amount")
    @CommandPermission("blizzard.bosses.admin")
    public void onCommand(CommandSender sender, String name, String boss, Integer amount, Integer stack) {

        Player target = Bukkit.getPlayer(name);
        if (target == null) {
            sender.sendActionBar(TextAPI.parse("§c§lEI! §cO jogador é inválido ou está offline."));
            return;
        }

        if (amount <=0) {
            sender.sendActionBar(TextAPI.parse("§c§lEI! §cA quantia tem que ser maior que 0."));
            return;
        }

        switch (boss) {
            case "bigfoot", "pegrande" -> BigFootEgg.give(target, amount, stack);
            default -> {
                List<String> messages = Arrays.asList(
                        "",
                        " §c§lEI §cO boss §7" + boss + "§c não existe.",
                        " §cDisponíveis: §7[pegrande]",
                        ""
                );
                for (String message : messages) {
                    sender.sendMessage(message);
                }
            }
        }

        sender.sendActionBar(TextAPI.parse("§a§lYAY! §aVocê deu §fx" + amount + " boss(es) §f" + boss + " (" + stack + ")" + "§a para o jogador §f" + target.getName() + "§a!"));
    }
}
