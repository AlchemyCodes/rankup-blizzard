package blizzard.development.monsters.commands.subcommands.users;

import blizzard.development.monsters.monsters.enums.Locations;
import blizzard.development.monsters.monsters.managers.tools.MonstersToolManager;
import blizzard.development.monsters.monsters.managers.world.MonstersWorldManager;
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
        MonstersWorldManager worldManager = MonstersWorldManager.getInstance();

        if (worldManager.containsPlayer(player)) {
            Location location = LocationUtils.getInstance().getLocation(Locations.EXIT.getName());

            if (location == null) {
                player.sendActionBar(TextAPI.parse("§c§lEI! §cO local de saída não está configurado."));
                return;
            }

            worldManager.removePlayer(player);
            MonstersToolManager.getInstance().removeRadar(player);

            player.teleport(location);
            player.sendActionBar(TextAPI.parse("§a§lYAY! §aVocê saiu do mundo de monstros."));
        } else {
            player.sendActionBar(TextAPI.parse("§c§lEI! §cVocê não está no mundo de monstros."));
        }
    }
}
