package blizzard.development.essentials.commands.staff;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Default;
import co.aikar.commands.annotation.Syntax;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

@CommandAlias("fly|voar|superman")
public class FlyCommand extends BaseCommand {

    private final List<Player> flyingPlayers = new ArrayList<>();
    @Default
    @CommandPermission("alchemy.essentials.staff")
    public void onCommand(CommandSender commandSender) {
        if (!(commandSender instanceof Player)) {
            return;
        }

        Player player = (Player) commandSender;

        if (flyingPlayers.contains(player)) {
            player.setAllowFlight(false);
            player.setFlying(false);

            player.sendActionBar("§c§lOK! §cVocê desativou o seu modo fly.");
            flyingPlayers.remove(player);
        } else {
            player.setAllowFlight(true);
            player.setFlying(true);

            player.sendActionBar("§b§lOK! §bVocê ativou o seu modo fly.");
            flyingPlayers.add(player);
        }

    }
}
