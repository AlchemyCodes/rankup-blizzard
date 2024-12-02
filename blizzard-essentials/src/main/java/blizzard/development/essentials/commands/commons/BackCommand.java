package blizzard.development.essentials.commands.commons;

import blizzard.development.essentials.managers.BackManager;
import blizzard.development.essentials.utils.CooldownUtils;
import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.Default;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandAlias("back|voltar")
public class BackCommand extends BaseCommand {


    @Default
    public void onCommand(CommandSender commandSender) {
        Player player = (Player) commandSender;

        if (!BackManager.contains(player)) {
            player.sendActionBar("§c§lEI! §cVocê não tem nenhuma localização para voltar.");
            return;
        }

        if (CooldownUtils.getInstance().isInCountdown(player, "back")) {
            player.sendActionBar("§c§lEI! §cAguarde um pouco para voltar novamente.");
            player.playSound(player.getLocation(), "block.note_block_pling", 1, 1);
            return;
        }

        Location location = BackManager.get(player);
        player.teleport(location);
        player.sendActionBar("§d§lYAY! §dVocê foi teleportado até a sua última localização.");
    }
}
