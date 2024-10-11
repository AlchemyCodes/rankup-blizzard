package blizzard.development.core.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Default;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.List;

@CommandAlias("essentials")
public class EssentialsCommand extends BaseCommand {

    @Default
    @CommandPermission("alchemy.core.admin")
    public void onDefault(Player player) {
        List<String> message = Arrays.asList(
                "",
                "§b§lALCHEMY ESSENTIALS",
                "",
                "§7/ping §8- §7Visualize sua latência",
                "§7/gamemode §8- §7Mude seu modo de jogo",
                "§7/teleport §8- §7Se teleporta para alguma localização/jogador",
                "§7/heal §8- §7Regenere toda sua vida/fome",
                ""
        );

        for (String line : message) {
            player.sendMessage(line);
        }
    }

}
