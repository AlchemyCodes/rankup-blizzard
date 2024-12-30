package blizzard.development.monsters.commands.subcommands.users;

import blizzard.development.monsters.monsters.enums.Locations;
import blizzard.development.monsters.monsters.handlers.tools.MonstersToolHandler;
import blizzard.development.monsters.monsters.handlers.world.MonstersWorldHandler;
import blizzard.development.monsters.utils.LocationUtils;
import blizzard.development.monsters.utils.items.TextAPI;
import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Subcommand;
import org.bukkit.Location;
import org.bukkit.entity.Player;

@CommandAlias("monsters|monster|monstros|monstro|bosses|boss")
@CommandPermission("blizzard.monsters.basic")
public class LeaveCommand extends BaseCommand {

    @Subcommand("sair|leave|exit")
    public void onCommand(Player player) {
        MonstersWorldHandler handler = MonstersWorldHandler.getInstance();

        if (handler.containsPlayer(player)) {
            Location location = LocationUtils.getInstance().getLocation(Locations.EXIT.getName());

            if (location == null) {
                player.sendActionBar(TextAPI.parse("§c§lEI! §cO local de saída não está configurado."));
                return;
            }

            handler.removePlayer(player);
            MonstersToolHandler.getInstance().removeRadar(player);

            player.teleport(location);
            player.sendActionBar(TextAPI.parse("§a§lYAY! §aVocê saiu do mundo de monstros."));
        } else {
            player.sendActionBar(TextAPI.parse("§c§lEI! §cVocê não está no mundo de monstros."));
        }
    }
}
