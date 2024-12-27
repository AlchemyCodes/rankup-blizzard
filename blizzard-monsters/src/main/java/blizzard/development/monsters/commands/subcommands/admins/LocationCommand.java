package blizzard.development.monsters.commands.subcommands.admins;

import blizzard.development.monsters.monsters.enums.Locations;
import blizzard.development.monsters.utils.LocationUtils;
import blizzard.development.monsters.utils.items.TextAPI;
import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandCompletion;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Subcommand;
import org.bukkit.entity.Player;

import java.util.Arrays;

@CommandAlias("monsters|monster|monstros|monstro|bosses|boss")
@CommandPermission("blizzard.monsters.admin")
public class LocationCommand extends BaseCommand {

    @Subcommand("set")
    @CommandCompletion("@subcommand_actions")
    public void onCommand(Player player, String place) {
        switch (place) {
            case "entrada", "entry" -> {
                LocationUtils.setLocation(Locations.ENTRY.getName(), player.getLocation());
                handleMessage(player, place);
            }
            case "saida", "exit" -> {
                LocationUtils.setLocation(Locations.EXIT.getName(), player.getLocation());
                handleMessage(player, place);
            }
            default -> Arrays.asList(
                    "",
                    " §c§lEI §cO lugar §7" + place + "§c não existe.",
                    " §cDisponíveis: §7" + Arrays.toString(Locations.values()).toLowerCase(),
                    ""
            ).forEach(player::sendMessage);
        }
    }

    private void handleMessage(Player player, String place) {
        player.sendActionBar(TextAPI.parse("§a§lYAY! §aVocê marcou a localização §7'" + place + "' §acom sucesso."));
    }
}
