package blizzard.development.core.commands.essentials;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.*;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.HashMap;

@CommandAlias("fly|voar")
public class FlyCommand extends BaseCommand {

    public static final HashMap<Player, Boolean> isFlying = new HashMap<>();

    @Default
    @CommandPermission("alchemy.core.admin")
    @Syntax("<jogador>")
    public void onCommand(CommandSender sender, @Optional String target) {
        if (target == null) {
            if (!(sender instanceof Player)) {
                sender.sendMessage("§cApenas jogadores podem utilizar este comando!");
                return;
            }

            Player player = (Player) sender;

            if (isFlying.containsKey(player)) {
                player.setAllowFlight(false);
                player.setFlying(false);
                sender.sendMessage("§7Seu modo de voo foi desabilitado!");
                isFlying.remove(player);
            } else {
                player.setAllowFlight(true);
                player.setFlying(true);
                sender.sendMessage("§7Seu modo de voo foi habilitado!");
                isFlying.put(player, true);
            }
            return;
        }

        Player targetPlayer = Bukkit.getPlayer(target);
        Player player = (Player) sender;

        if (targetPlayer != null) {
            if (isFlying.containsKey(targetPlayer)) {
                targetPlayer.setAllowFlight(false);
                targetPlayer.setFlying(false);
                sender.sendMessage("§7Você alterou o modo de voo do jogador §f" + targetPlayer.getName() + "§7 para desabilitado!");
                if (targetPlayer != player) {
                    sender.sendMessage("§7Seu modo de voo foi desabilitado!");
                }
                isFlying.remove(targetPlayer);
            } else {
                targetPlayer.setAllowFlight(true);
                targetPlayer.setFlying(true);
                sender.sendMessage("§7Você alterou o modo de voo do jogador §f" + targetPlayer.getName() + "§7 para habilitado!");
                if (targetPlayer != player) {
                    sender.sendMessage("§7Seu modo de voo foi habilitado!");
                }
                isFlying.put(targetPlayer, true);
            }
        } else {
            sender.sendMessage("§7O jogador fornecido está offline ou é inválido!");
        }
    }
}
