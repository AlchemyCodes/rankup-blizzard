package blizzard.development.currencies.commands.currencies.rankup.subcommands;

import blizzard.development.currencies.api.CurrenciesAPI;
import blizzard.development.currencies.database.cache.PlayersCacheManager;
import blizzard.development.currencies.enums.Currencies;
import blizzard.development.currencies.utils.CooldownUtils;
import blizzard.development.currencies.utils.NumberFormat;
import blizzard.development.currencies.utils.PluginImpl;
import blizzard.development.currencies.utils.TextAPI;
import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.*;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.*;
import java.util.concurrent.TimeUnit;

@CommandAlias("flakes|flocos|floco")
public class FlakesExchangeCommand extends BaseCommand {
    private final Map<UUID, UUID> transactionPairs = new HashMap<>();

    NumberFormat format = NumberFormat.getInstance();
    CooldownUtils cooldown = CooldownUtils.getInstance();
    PlayersCacheManager cache = PlayersCacheManager.getInstance();
    PluginImpl impl = PluginImpl.getInstance();
    CurrenciesAPI api = CurrenciesAPI.getInstance();
    Currencies currency = Currencies.FLAKES;

    @Subcommand("pay|pagar|enviar|transferir")
    @CommandPermission("blizzard.currencies.basic")
    @CommandCompletion("@players")
    @Syntax("<jogador> <quantia>")
    public void onPayCommand(CommandSender sender, String nickname, String amount) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage("§cEste comando só pode ser utilizado por jogadores!");
            return;
        }

        if (!impl.Config.getBoolean("currencies.can-pay.flakes")) {
            sender.sendMessage("§c§lEI §cVocê não pode utilizar este comando.");
            return;
        }

        String cooldownName = currency.getName() + "_pay";

        if (cooldown.isInCountdown(player, cooldownName)) {
            sender.sendActionBar(TextAPI.parse("§c§lEI! §cAguarde alguns segundos executar esse comando."));
            return;
        }

        cooldown.createCountdown(player, cooldownName, 5, TimeUnit.SECONDS);

        Player target = Bukkit.getPlayer(nickname);
        if (target == null) {
            sender.sendActionBar(TextAPI.parse("§c§lEI! §cO jogador informado é inválido ou está offline."));
            return;
        }

        if (player.equals(target)) {
            sender.sendActionBar(TextAPI.parse("§c§lEI! §cVocê não pode enviar flocos para si mesmo."));
            return;
        }

        if (cache.getPlayerData(player) == null) {
            sender.sendActionBar(TextAPI.parse("§c§lEI! §cVocê não foi encontrado no banco de dados."));
            return;
        }

        if (cache.getPlayerData(target) == null) {
            sender.sendActionBar(TextAPI.parse("§c§lEI! §cO jogador informado não foi encontrado no banco de dados."));
            return;
        }

        if (transactionPairs.containsKey(player.getUniqueId()) || transactionPairs.containsKey(target.getUniqueId()) ||
                transactionPairs.containsValue(player.getUniqueId()) || transactionPairs.containsValue(target.getUniqueId())) {
            sender.sendActionBar(TextAPI.parse("§c§lEI! §cVocês já estão envolvidos em uma transação!"));
            return;
        }

        try {
            double doubleAmount = Double.parseDouble(amount);
            double bal = api.getBalance(player, currency);
            if (bal < doubleAmount) {
                sender.sendActionBar(TextAPI.parse("§c§lEI! §cVocê não possui a quantia necessária de flocos para realizar essa transação."));
                return;
            }
            if (doubleAmount <= 0) {
                sender.sendActionBar(TextAPI.parse("§c§lEI! §cO valor inserido deve ser maior que zero."));
                return;
            }
            if (api.transferBalance(player, target, currency, doubleAmount)) {
                List<String> playerMessages = Arrays.asList(
                        "",
                        "§b§lTransferência realizada!",
                        "§bVocê pagou ❄" + "§l" + format.formatNumber(doubleAmount) + "§b flocos para o jogador " + target.getName() + ".",
                        ""
                );

                List<String> targetMessages = Arrays.asList(
                        "",
                        "§b§lTransferência recebida",
                        "§bVocê recebeu ❄" + "§l" + format.formatNumber(doubleAmount) + "§b flocos do jogador " + player.getName() + ".",
                        ""
                );

                for (String playerMessage : playerMessages) {
                    player.sendMessage(TextAPI.parse(playerMessage));
                }
                for (String targetMessage : targetMessages) {
                    target.sendMessage(TextAPI.parse(targetMessage));
                }
                transactionPairs.remove(player.getUniqueId());
                transactionPairs.remove(target.getUniqueId());
            } else {
                sender.sendActionBar(TextAPI.parse("§c§lEI! §cOcorreu um erro ao concluir a transação."));
            }
        } catch (NumberFormatException ex) {
            if (format.parseNumber(amount) == null) {
                sender.sendActionBar(TextAPI.parse("§c§lEI! §cO valor inserido é inválido."));
                return;
            }
            double doubleFormatted = format.parseNumber(amount);
            double bal = api.getBalance(player, currency);
            if (bal < doubleFormatted) {
                sender.sendActionBar(TextAPI.parse("§c§lEI! §cVocê não possui a quantia necessária de flocos para realizar essa transação."));
                return;
            }
            if (api.transferBalance(player, target, currency, doubleFormatted)) {
                List<String> playerMessages = Arrays.asList(
                        "",
                        "§b§lTransferência realizada!",
                        "§bVocê pagou ❄" + "§l" + format.formatNumber(doubleFormatted) + "§b flocos para o jogador " + target.getName() + ".",
                        ""
                );

                List<String> targetMessages = Arrays.asList(
                        "",
                        "§b§lTransferência recebida!",
                        "§bVocê recebeu ❄" + "§l" + format.formatNumber(doubleFormatted) + "§b flocos do jogador " + player.getName() + ".",
                        ""
                );

                for (String playerMessage : playerMessages) {
                    player.sendMessage(TextAPI.parse(playerMessage));
                }
                for (String targetMessage : targetMessages) {
                    target.sendMessage(TextAPI.parse(targetMessage));
                }
                transactionPairs.remove(player.getUniqueId());
                transactionPairs.remove(target.getUniqueId());
            } else {
                sender.sendActionBar(TextAPI.parse("§c§lEI! §cOcorreu um erro ao concluir a transação."));
            }
        }

    }

    @Subcommand("set|setar")
    @CommandPermission("blizzard.currencies.admin")
    @CommandCompletion("@players")
    @Syntax("<jogador> <quantia>")
    public void onSetCommand(CommandSender sender, String nickname, String amount) {
        Player target = Bukkit.getPlayer(nickname);
        if (target == null) {
            sender.sendActionBar(TextAPI.parse("§c§lEI! §cO jogador informado é inválido ou está offline."));
            return;
        }

        if (cache.getPlayerData(target) == null) {
            sender.sendActionBar(TextAPI.parse("§c§lEI! §cO jogador informado não foi encontrado no banco de dados."));
            return;
        }

        try {
            double doubleAmount = Double.parseDouble(amount);
            if (doubleAmount <= 0) {
                sender.sendActionBar(TextAPI.parse("§c§lEI! §cO valor inserido é inválido."));
                return;
            }
            if (api.setBalance(target, currency, doubleAmount)) {
                sender.sendActionBar(TextAPI.parse("§a§lYAY! §aVocê setou " + format.formatNumber(doubleAmount) + " flocos para o jogador " + target.getName()));
            } else {
                sender.sendActionBar(TextAPI.parse("§c§lEI! §cOcorreu um erro ao concluir a transação."));
            }
        } catch (NumberFormatException ex) {
            if (format.parseNumber(amount) == null) {
                sender.sendActionBar(TextAPI.parse("§c§lEI! §cO valor inserido é inválido."));
                return;
            }
            double doubleFormatted = format.parseNumber(amount);
            if (api.setBalance(target, currency, doubleFormatted)) {
                sender.sendActionBar(TextAPI.parse("§a§lYAY! §aVocê setou " + format.formatNumber(doubleFormatted) + " flocos para o jogador " + target.getName()));
            } else {
                sender.sendActionBar(TextAPI.parse("§c§lEI! §cOcorreu um erro ao concluir a transação."));
            }
        }
    }

    @Subcommand("add|adicionar")
    @CommandPermission("blizzard.currencies.admin")
    @CommandCompletion("@players")
    @Syntax("<jogador> <quantia>")
    public void onAddCommand(CommandSender sender, String nickname, String amount) {
        Player target = Bukkit.getPlayer(nickname);
        if (target == null) {
            sender.sendActionBar(TextAPI.parse("§c§lEI! §cO jogador informado é inválido ou está offline."));
            return;
        }

        if (cache.getPlayerData(target) == null) {
            sender.sendActionBar(TextAPI.parse("§c§lEI! §cO jogador informado não foi encontrado no banco de dados."));
            return;
        }

        try {
            double doubleAmount = Double.parseDouble(amount);
            if (doubleAmount <= 0) {
                sender.sendActionBar(TextAPI.parse("§c§lEI! §cO valor inserido deve ser maior que zero."));
                return;
            }
            if (api.addBalance(target, currency, doubleAmount)) {
                sender.sendActionBar(TextAPI.parse("§a§lYAY! §aVocê adicionou " + format.formatNumber(doubleAmount) + " flocos para o jogador " + target.getName()));
            } else {
                sender.sendActionBar(TextAPI.parse("§c§lEI! §cOcorreu um erro ao concluir a transação."));
            }
        } catch (NumberFormatException ex) {
            if (format.parseNumber(amount) == null) {
                sender.sendActionBar(TextAPI.parse("§c§lEI! §cO valor inserido é inválido."));
                return;
            }
            double doubleFormatted = format.parseNumber(amount);
            if (api.addBalance(target, currency, doubleFormatted)) {
                sender.sendActionBar(TextAPI.parse("§a§lYAY! §aVocê adicionou " + format.formatNumber(doubleFormatted) + " flocos para o jogador " + target.getName()));
            } else {
                sender.sendActionBar(TextAPI.parse("§c§lEI! §cOcorreu um erro ao concluir a transação."));
            }
        }
    }

    @Subcommand("remove|remover")
    @CommandPermission("blizzard.currencies.admin")
    @CommandCompletion("@players")
    @Syntax("<jogador> <quantia>")
    public void onRemoveCommand(CommandSender sender, String nickname, String amount) {
        Player target = Bukkit.getPlayer(nickname);
        if (target == null) {
            sender.sendActionBar(TextAPI.parse("§c§lEI! §cO jogador informado é inválido ou está offline."));
            return;
        }

        if (cache.getPlayerData(target) == null) {
            sender.sendActionBar(TextAPI.parse("§c§lEI! §cO jogador informado não foi encontrado no banco de dados."));
            return;
        }

        try {
            double doubleAmount = Double.parseDouble(amount);
            if (doubleAmount <= 0) {
                sender.sendActionBar(TextAPI.parse("§c§lEI! §cO valor inserido deve ser maior que zero."));
                return;
            }
            double bal = api.getBalance(target, currency);
            if (bal < doubleAmount) {
                sender.sendActionBar(TextAPI.parse("§c§lEI! §cO jogador possui uma quantia menor que essa de saldo."));
                return;
            }
            if (api.removeBalance(target, currency, doubleAmount)) {
                sender.sendActionBar(TextAPI.parse("§a§lYAY! §aVocê removeu " + format.formatNumber(doubleAmount) + " flocos do jogador " + target.getName()));
            } else {
                sender.sendActionBar(TextAPI.parse("§c§lEI! §cOcorreu um erro ao concluir a transação."));
            }
        } catch (NumberFormatException ex) {
            if (format.parseNumber(amount) == null) {
                sender.sendActionBar(TextAPI.parse("§c§lEI! §cO valor inserido é inválido."));
                return;
            }
            double doubleFormatted = format.parseNumber(amount);
            double bal = api.getBalance(target, currency);
            if (bal < doubleFormatted) {
                sender.sendActionBar(TextAPI.parse("§c§lEI! §cO jogador possui uma quantia menor que essa de saldo."));
                return;
            }
            if (api.removeBalance(target, currency, doubleFormatted)) {
                sender.sendActionBar(TextAPI.parse("§a§lYAY! §aVocê removeu " + format.formatNumber(doubleFormatted) + " flocos do jogador " + target.getName()));
            } else {
                sender.sendActionBar(TextAPI.parse("§c§lEI! §cOcorreu um erro ao concluir a transação."));
            }
        }
    }
}
