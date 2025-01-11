package blizzard.development.plantations.commands.seeds;

import blizzard.development.plantations.api.SeedAPI;
import blizzard.development.plantations.database.cache.methods.PlayerCacheMethod;
import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.*;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static blizzard.development.plantations.utils.NumberFormat.formatNumber;

@CommandAlias("sementes|semente")
public class SeedCommand extends BaseCommand {

    @Default
    @CommandCompletion("@players")
    public void onSeedCommand(CommandSender commandSender, @Optional String playerTarget) {
        Player player = (Player) commandSender;

        if (playerTarget == null) {
            player.sendActionBar("§a§lEI! §aVocê possui §l" + formatNumber(PlayerCacheMethod.getInstance().getPlantations(player)) + "✿ §asementes.");
            return;
        }

        Player target = Bukkit.getPlayer(playerTarget);
        if (target == null) {
            player.sendActionBar("§c§lEI! §cO jogador está offline ou não existe.");
            return;
        }

        if (target == player) {
            player.sendActionBar("§a§lEI! §aVocê possui §l" + formatNumber(PlayerCacheMethod.getInstance().getPlantations(player)) + "✿ §asementes.");
            return;
        }

        player.sendActionBar("§a§lEI! §aO jogador " + target.getName() + " possui §l" + formatNumber(PlayerCacheMethod.getInstance().getPlantations(target)) + "✿ §asementes.");
    }

    @Subcommand("giveseed")
    @Syntax("<player> <amount>")
    @CommandCompletion("@players @range:1-200000")
    @CommandPermission("alchemy.plantations.giveseed")
    public void onGiveSeed(CommandSender commandSender, String playerTarget, int amount) {
        Player player = (Player) commandSender;
        Player target = Bukkit.getPlayer(playerTarget);

        if (target == null) {
            player.sendActionBar("§c§lEI! §cO jogador está offline ou não existe.");
            return;
        }

        SeedAPI seedAPI = SeedAPI.getInstance();
        seedAPI.addSeed(target, amount);

        player.sendActionBar("§a§lYAY! §aVocê adicionou §l" + formatNumber(amount) + "§a sementes na conta do jogador " + target.getName());
    }

    @Subcommand("removeseed")
    @Syntax("<player> <amount>")
    @CommandCompletion("@players @range:1-200")
    @CommandPermission("alchemy.plantations.removeseed")
    public void onRemoveSeed(CommandSender commandSender, String playerTarget, int amount) {
        Player player = (Player) commandSender;
        Player target = Bukkit.getPlayer(playerTarget);

        if (target == null) {
            player.sendActionBar("§c§lEI! §cO jogador está offline ou não existe.");
            return;
        }

        SeedAPI seedAPI = SeedAPI.getInstance();

        if (seedAPI.getSeedBalance(target) < amount) {
            player.sendActionBar("§c§lEI! §cO jogador não tem essa quantia para ser removida.");
            return;
        }

        seedAPI.removeSeed(player, amount);
        player.sendActionBar("§a§lYAY! §aVocê removeu " + formatNumber(amount) + " sementes do jogador " + target.getName() + ".");
    }

    @Subcommand("resetseed")
    @Syntax("<player>")
    @CommandCompletion("@players")
    @CommandPermission("alchemy.plantations.resetseed")
    public void onRemoveSeed(CommandSender commandSender, String playerTarget) {
        Player player = (Player) commandSender;
        Player target = Bukkit.getPlayer(playerTarget);

        if (target == null) {
            player.sendActionBar("§c§lEI! §cO jogador está offline ou não existe.");
            return;
        }

        SeedAPI seedAPI = SeedAPI.getInstance();
        seedAPI.setSeed(player, 0);

        player.sendActionBar("§a§lYAY! §aVocê resetou as sementes do jogador " + target.getName() + ".");
    }
}
