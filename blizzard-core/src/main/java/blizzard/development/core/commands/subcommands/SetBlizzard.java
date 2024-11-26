package blizzard.development.core.commands.subcommands;

import blizzard.development.core.api.CoreAPI;
import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Subcommand;
import org.bukkit.World;
import org.bukkit.entity.Player;

@CommandAlias("blizzard")
public class SetBlizzard extends BaseCommand {

    @Subcommand("iniciar")
    @CommandPermission("core.admin")
    public void startBlizzard() {
        CoreAPI.getInstance().startBlizzard();
    }

    @Subcommand("parar")
    @CommandPermission("core.admin")
    public void stopBlizzard() {
        CoreAPI.getInstance().stopBlizzard();
    }
}
