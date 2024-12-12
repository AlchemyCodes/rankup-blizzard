package blizzard.development.spawners.commands.spawners.subcommands;

import blizzard.development.currencies.api.CurrenciesAPI;
import blizzard.development.currencies.enums.Currencies;
import blizzard.development.spawners.handlers.enchantments.EnchantmentsHandler;
import blizzard.development.spawners.handlers.enums.Enchantments;
import blizzard.development.spawners.handlers.enums.Spawners;
import blizzard.development.spawners.handlers.limits.LimitsHandler;
import blizzard.development.spawners.handlers.mobs.MobsHandler;
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
public class LimitsCommand extends BaseCommand {

    @Subcommand("limit|limits|limite|limites")
    @CommandCompletion("@actions @limits @players @amount")
    @Syntax("<ação> <tipo> <player> <quantia>")
    public void onGiveLimit(CommandSender sender, String action, String type, String target, Double amount) {
        Player player = Bukkit.getPlayer(target);
        if (player == null) {
            sender.sendActionBar(TextAPI.parse("§c§lEI! §cO jogador fornecido é inválido ou está offline."));
            return;
        }

        if (!verifyAmount(amount)) {
            player.sendActionBar(TextAPI.parse("§c§lEI! §cO valor deve ser maior que 0."));
            return;
        }

        final CurrenciesAPI api = CurrenciesAPI.getInstance();

        switch (action) {
            case "give", "givar", "dar" -> {
                switch (type) {
                    case "purchase", "purchases", "compra", "compras" -> {
                        LimitsHandler.givePurchaseLimit(
                                player,
                                amount,
                                1
                        );
                        sender.sendActionBar(TextAPI.parse(limitsSuccessMessage(player, "deu", amount, 1)));
                    }
                    case "friend", "friends", "amigo", "amigos" -> {
                        LimitsHandler.giveFriendsLimit(
                                player,
                                amount,
                                1
                        );
                        sender.sendActionBar(TextAPI.parse(limitsSuccessMessage(player, "deu", amount, 1)));
                    }
                    default -> {
                        List<String> messages = Arrays.asList(
                                "",
                                " §c§lEI §cO limite §7" + type + "§c não existe.",
                                " §cDisponíveis: §7[compras, amigos]",
                                ""
                        );
                        for (String message : messages) {
                            sender.sendMessage(message);
                        }
                    }
                }
            }
            case "add", "adicionar" -> {
                switch (type) {
                    case "purchase", "purchases", "compra", "compras" -> {
                        api.addBalance(player, Currencies.SPAWNERSLIMIT, amount);
                        sender.sendActionBar(TextAPI.parse(limitsSuccessMessage(player, "adicionou", amount, 1)));
                    }
                    default -> {
                        List<String> messages = Arrays.asList(
                                "",
                                " §c§lEI §cO limite §7" + type + "§c não existe.",
                                " §cDisponíveis: §7[compras]",
                                ""
                        );
                        for (String message : messages) {
                            sender.sendMessage(message);
                        }
                    }
                }
            }
            case "remove", "remover" -> {
                switch (type) {
                    case "purchase", "purchases", "compra", "compras" -> {
                        api.removeBalance(player, Currencies.SPAWNERSLIMIT, amount);
                        sender.sendActionBar(TextAPI.parse(limitsSuccessMessage(player, "removeu", amount, 1)));
                    }
                    default -> {
                        List<String> messages = Arrays.asList(
                                "",
                                " §c§lEI §cO limite §7" + type + "§c não existe.",
                                " §cDisponíveis: §7[compras, amigos]",
                                ""
                        );
                        for (String message : messages) {
                            sender.sendMessage(message);
                        }
                    }
                }
            }
            default -> {
                List<String> messages = Arrays.asList(
                        "",
                        " §c§lEI §cA ação §7" + action + "§c não existe.",
                        " §cDisponíveis: §7[dar, adicionar, remover]",
                        ""
                );
                for (String message : messages) {
                    sender.sendMessage(message);
                }
            }
        }
    }

    public String limitsSuccessMessage(Player player, String action, Double amount, Integer stack) {
        String formattedAmount = NumberFormat.getInstance().formatNumber(amount);
        return "§a§lYAY! §aVocê " + action + " §fx" + formattedAmount + " limite(s)§f" + " (" + stack + ")" + "§a para o jogador §f" + player.getName() + "§a!";
    }

    public Boolean verifyAmount(Double amount) {
        return amount > 0;
    }
}
