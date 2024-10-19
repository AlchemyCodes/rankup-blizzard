package blizzard.development.essentials.commands.staff;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Default;
import co.aikar.commands.annotation.Syntax;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandAlias("tpto|ir|go")
public class TpToCommand extends BaseCommand {

    @Default
    @CommandPermission("alchemy.essentials.staff")
    @Syntax("<x> <y> <z>")
    public void onCommand(CommandSender commandSender, String x, String y, String z) {
        if (!(commandSender instanceof Player)) {
            return;
        }

        Player player = (Player) commandSender;

        if (x != null && y != null && z != null) {

            int locationX = Integer.parseInt(x);
            int locationY = Integer.parseInt(y);
            int locationZ = Integer.parseInt(z);

            player.teleport(new Location(player.getWorld(), locationX, locationY, locationZ));
            player.sendActionBar("§b§lYAY! §bVocê foi teleportado para as coordenadas §8[" + locationX + ", " + locationY + ", " + locationZ + "]§b.");
        }

    }
}
