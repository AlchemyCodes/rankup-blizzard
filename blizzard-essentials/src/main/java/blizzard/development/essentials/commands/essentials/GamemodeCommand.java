package blizzard.development.essentials.commands.essentials;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.*;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandAlias("gamemode|gm")
public class GamemodeCommand extends BaseCommand {

    @Default
    @CommandPermission("alchemy.core.admin")
    @Syntax("<gamemode> <player>")
    public void onCommand(CommandSender sender, String gameMode, @Optional String target) {
        if (target == null) {
            if (!(sender instanceof Player)) {
                sender.sendMessage("§cApenas jogadores podem utilizar este comando!");
                return;
            }

            Player player  = (Player) sender;

            switch (gameMode) {
                case "0", "survival", "sobrevivencia" -> {
                    player.setGameMode(GameMode.SURVIVAL);
                    player.sendMessage("§7Seu modo de jogo foi alterado para §fSobrevivência§7!");
                }
                case "1", "creative", "criativo" -> {
                    player.setGameMode(GameMode.CREATIVE);
                    player.sendMessage("§7Seu modo de jogo foi alterado para §fCriativo§7!");
                }
                case "2", "adventure", "aventura" -> {
                    player.setGameMode(GameMode.ADVENTURE);
                    player.sendMessage("§7Seu modo de jogo foi alterado para §fAventura§7!");
                }
                case "3", "spectator", "espectador" -> {
                    player.setGameMode(GameMode.SPECTATOR);
                    player.sendMessage("§7Seu modo de jogo foi alterado para §fEspectador§7!");
                }
                default -> player.sendMessage("§7Opções disponíveis: §f0, 1, 2 e 3!");
            }
            return;
        }

        Player targetPlayer = Bukkit.getPlayer(target);
        if (targetPlayer != null) {
            switch (gameMode) {
                case "0", "survival" -> {
                    targetPlayer.setGameMode(GameMode.SURVIVAL);
                    sender.sendMessage("§7Você alterou o modo de jogo do jogador §f" + targetPlayer.getName() + "§7 para §fSobrevivência§7!");
                }
                case "1", "creative" -> {
                    targetPlayer.setGameMode(GameMode.CREATIVE);
                    sender.sendMessage("§7Você alterou o modo de jogo do jogador §f" + targetPlayer.getName() + "§7 para §fSCriativo§7!");
                }
                case "2", "adventure" -> {
                    targetPlayer.setGameMode(GameMode.ADVENTURE);
                    sender.sendMessage("§7Você alterou o modo de jogo do jogador §f" + targetPlayer.getName() + "§7 para §fAventura§7!");
                }
                case "3", "spectator" -> {
                    targetPlayer.setGameMode(GameMode.SPECTATOR);
                    sender.sendMessage("§7Você alterou o modo de jogo do jogador §f" + targetPlayer.getName() + "§7 para §fEspectador§7!");
                }
                default -> sender.sendMessage("§7Opções disponíveis: §f0, 1, 2 e 3!");
            }
        } else {
            sender.sendMessage("§7O jogador fornecido está offline ou é inválido!");
        }
    }
}
