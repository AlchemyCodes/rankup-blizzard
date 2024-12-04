package blizzard.development.core.commands.schematic;

import blizzard.development.core.managers.SchematicManager;
import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.Default;
import org.bukkit.entity.Player;

@CommandAlias("coreschem")
public class SchemCommand extends BaseCommand {

    @Default
    public void onCommand(Player player) {
        SchematicManager schematicManager = new SchematicManager();
        schematicManager.pasteSchematicForPlayer(player, player.getLocation());
    }
}
