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
public class JoinCommand extends BaseCommand {

    @Subcommand("entrar|ir|go")
    public void onCommand(Player player) {
        MonstersWorldManager worldManager = MonstersWorldManager.getInstance();

        if (!worldManager.containsPlayer(player)) {
            Location location = LocationUtils.getInstance().getLocation(Locations.ENTRY.getName());

            if (location == null) {
                player.sendActionBar(TextAPI.parse("§c§lEI! §cO local de entrada não está configurado."));
                return;
            }

            if (player.getInventory().firstEmpty() == -1) {
                player.sendActionBar(TextAPI.parse("§c§lEI! §cVocê precisa de pelo menos um slot vazio."));
                return;
            }

            worldManager.addPlayer(player);

            player.teleport(location);
            MonstersToolManager.getInstance().giveRadar(player);

            player.sendActionBar(TextAPI.parse("§a§lYAY! §aVocê entrou no mundo de monstros."));
        } else {
            player.sendActionBar(TextAPI.parse("§c§lEI! §cVocê já está no mundo de monstros."));
        }
    }
}
