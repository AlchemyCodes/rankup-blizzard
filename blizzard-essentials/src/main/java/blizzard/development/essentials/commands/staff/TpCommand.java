package blizzard.development.essentials.commands.staff;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.*;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Interaction;
import org.bukkit.entity.Player;

@CommandAlias("tp|go")
public class TpCommand extends BaseCommand {

    @Default
    @CommandPermission("alchemy.essentials.staff")
    @CommandCompletion("@players")
    @Syntax("<jogador> <x> <y> <z>")
    public void onCommand(CommandSender commandSender, String playerTarget) {
        if (!(commandSender instanceof Player)) {
            return;
        }

        Player player = (Player) commandSender;
        Player target = Bukkit.getPlayer(playerTarget);

        if (target == null) {
            player.sendActionBar("§c§lEI! §cO jogador é inexistente ou está offline.");
            return;
        }

        if (target.getName().equalsIgnoreCase(player.getName())) {
            player.sendActionBar("§c§lEI! §cVocê não pode teletransportar para si mesmo.");
            return;
        }

        player.teleport(target.getLocation());
        player.sendActionBar("§b§lYAY! §bVocê foi teleportado para o jogador " + target.getName() + ".");


    }
}

