package blizzard.development.spawners.commands.spawners.subcommands;

import blizzard.development.currencies.api.CurrenciesAPI;
import blizzard.development.currencies.enums.Currencies;
import blizzard.development.spawners.handlers.drops.DropsHandler;
import blizzard.development.spawners.handlers.limits.LimitsHandler;
import blizzard.development.spawners.utils.NumberFormat;
import blizzard.development.spawners.utils.items.TextAPI;
import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.*;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.List;

@CommandAlias("spawners|spawner|geradores|gerador")
@CommandPermission("blizzard.spawners.admin")
public class AutoSellCommand extends BaseCommand {

    @Subcommand("autosell|autovender|autovenda")
    @CommandCompletion("@actionssell @players @amount")
    @Syntax("<ação> <player> <quantia>")
    public void onGiveLimit(CommandSender sender, String action, String target, Integer amount) {
        Player player = Bukkit.getPlayer(target);
        if (player == null) {
            sender.sendActionBar(TextAPI.parse("§c§lEI! §cO jogador fornecido é inválido ou está offline."));
            return;
        }

        if (!verifyAmount(amount)) {
            player.sendActionBar(TextAPI.parse("§c§lEI! §cO valor deve ser maior que 0."));
            return;
        }

        switch (action) {
            case "give", "givar", "dar" -> {
                DropsHandler.giveDropsAutoSell(player, amount);
                dropsSuccessMessage(player, action, amount);
            }
            default -> {
                List<String> messages = Arrays.asList(
                        "",
                        " §c§lEI §cA ação §7" + action + "§c não existe.",
                        " §cDisponíveis: §7[dar]",
                        ""
                );
                for (String message : messages) {
                    sender.sendMessage(message);
                }
            }
        }
    }

    public String dropsSuccessMessage(Player player, String action, Integer amount) {
        String formattedAmount = NumberFormat.getInstance().formatNumber(amount);
        return "§a§lYAY! §aVocê " + action + " §fx" + formattedAmount + " ativador(es)" + " §a para o jogador §f" + player.getName() + "§a!";
    }

    public Boolean verifyAmount(Integer amount) {
        return amount > 0;
    }
}
