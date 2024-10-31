package blizzard.development.spawners.commands.spawners.subcommands;

import blizzard.development.spawners.handlers.spawners.mobs.PigMob;
import blizzard.development.spawners.utils.items.apis.TextAPI;
import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.*;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.List;

@CommandAlias("spawners|spawner|geradores|gerador")
public class GiveCommand extends BaseCommand {

    // formatar os double

    @Subcommand("give")
    @CommandPermission("blizzard.spawners.basic")
    @CommandCompletion("@players @spawners @amount @amount")
    @Syntax("<player> <spawner> <quantia> <stack>")
    public void onCommand(CommandSender sender, String target, String type, Double amount, Integer stack) {
        Player player = Bukkit.getPlayer(target);
        if (player == null) {
            sender.sendActionBar(TextAPI.parse("§c§lEI! §cO jogador fornecido é inválido ou está offline."));
            return;
        }

        if (!verifyAmount(amount) || !verifyAmount(Double.valueOf(stack))) {
            player.sendActionBar(TextAPI.parse("§c§lEI! §cO valor deve ser maior que 0."));
            return;
        }

        switch (type) {
            case "pigs", "pig", "porcos", "porco" -> {
                PigMob.getInstance().give(player, amount, stack);
                sender.sendActionBar(TextAPI.parse(successMessage(player, type, amount, stack)));
            }
            default -> {
                List<String> messages = Arrays.asList(
                        "",
                        " §c§lEI §cO spawner §7" + type + "§c não existe.",
                        " §cDisponíveis: §7[porco]",
                        ""
                );
                for (String message : messages) {
                    sender.sendMessage(message);
                }
            }
        }
    }

    public String successMessage(Player player, String type, Double amount, Integer stack) {
        return "§a§lYAY! §aVocê deu §fx" + amount + " spawner(s) §f" + type + " (" + stack + ")" + "§a para o jogador §f" + player.getName() + "§a!";
    }

    public Boolean verifyAmount(Double amount) {
        return amount > 0;
    }
}
