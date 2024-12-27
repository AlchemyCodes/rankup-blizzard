package blizzard.development.plantations.commands.seeds;

import blizzard.development.plantations.database.cache.methods.PlayerCacheMethod;
import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandCompletion;
import co.aikar.commands.annotation.Default;
import co.aikar.commands.annotation.Optional;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandAlias("sementes|semente")
public class SeedCommand extends BaseCommand {

    @Default
    @CommandCompletion("@players")
    public void onSeedCommand(CommandSender commandSender, @Optional String playerTarget) {
        Player player = (Player) commandSender;

        if (playerTarget == null) {
            player.sendActionBar("§a§lEI! §aVocê possui §l" + PlayerCacheMethod.getInstance().getPlantations(player) + "✿ §asementes.");
            return;
        }

        Player target = Bukkit.getPlayer(playerTarget);
        if (target == null) {
            player.sendActionBar("§c§lEI! §cO jogador está offline ou não existe.");
            return;
        }

        if (target == player) {
            player.sendActionBar("§a§lEI! §aVocê possui §l" + PlayerCacheMethod.getInstance().getPlantations(player) + "✿ §asementes.");
            return;
        }

        player.sendActionBar("§a§lEI! §aO jogador " + target.getName() + " possui §l" + PlayerCacheMethod.getInstance().getPlantations(target) + "✿ §asementes.");
    }
}
