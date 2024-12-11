package blizzard.development.currencies.commands.currencies.limits;

import blizzard.development.currencies.api.CurrenciesAPI;
import blizzard.development.currencies.database.cache.PlayersCacheManager;
import blizzard.development.currencies.database.storage.PlayersData;
import blizzard.development.currencies.enums.Currencies;
import blizzard.development.currencies.utils.CooldownUtils;
import blizzard.development.currencies.utils.TextAPI;
import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.*;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

@CommandAlias("limits|limit|limites|limite")
public class LimitsCommand extends BaseCommand {
    CooldownUtils cooldown = CooldownUtils.getInstance();
    PlayersCacheManager cache = PlayersCacheManager.getInstance();
    CurrenciesAPI api = CurrenciesAPI.getInstance();
    Currencies currency = Currencies.SPAWNERSLIMIT;

    @Default
    @CommandPermission("blizzard.currencies.basic")
    @CommandCompletion("@players")
    @Syntax("<jogador>")
    public void onLimitsCommand(CommandSender sender, @Optional String nickname) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage("§cEste comando só pode ser utilizado por jogadores!");
            return;
        }

        String cooldownName = currency.getName() + "_view";

        if (cooldown.isInCountdown(player, cooldownName)) {
            sender.sendActionBar(TextAPI.parse("§c§lEI! §cAguarde alguns segundos executar esse comando."));
            return;
        }

        cooldown.createCountdown(player, cooldownName, 3, TimeUnit.SECONDS);

        if (nickname == null) {
            PlayersData playerData = cache.getPlayerData(player);
            if (playerData == null) {
                sender.sendActionBar(TextAPI.parse("§c§lEI! §cVocê não foi encontrado no banco de dados."));
                return;
            }

            String balance = api.getFormattedBalance(player, currency);
            List<String> messages = Arrays.asList(
                    "",
                    " §3Confira agora o seu saldo disponível de Limites.",
                    " §3Limites: ∞" + "§l" + balance,
                    ""
            );

            for (String message : messages) {
                player.sendMessage(TextAPI.parse(message));
            }
        } else {
            Player target = Bukkit.getPlayer(nickname);

            if (target == null) {
                sender.sendActionBar(TextAPI.parse("§c§lEI! §cO jogador informado é inválido ou está offline."));
                return;
            }

            PlayersData targetData = cache.getPlayerData(target);
            if (targetData == null) {
                sender.sendActionBar(TextAPI.parse("§c§lEI! §cO jogador informado não foi encontrado no banco de dados."));
                return;
            }

            if (target.equals(player)) {
                sender.sendActionBar(TextAPI.parse("§c§lEI! §cUtilize '/limites'!"));
                return;
            }

            String balance = api.getFormattedBalance(target, currency);
            List<String> messages = Arrays.asList(
                    "",
                    " §3Confira agora o saldo disponível de limites do jogador §l" + target.getName() + "§b.",
                    " §3Limites: ∞" + "§l" + balance,
                    ""
            );

            for (String message : messages) {
                player.sendMessage(TextAPI.parse(message));
            }
        }
    }
}
