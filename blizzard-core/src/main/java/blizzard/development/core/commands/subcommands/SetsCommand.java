package blizzard.development.core.commands.subcommands;

import blizzard.development.core.api.CoreAPI;
import blizzard.development.core.utils.PluginImpl;
import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Subcommand;
import co.aikar.commands.annotation.Syntax;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

@CommandAlias("core")
public class SetsCommand extends BaseCommand{

    @Subcommand("iniciarBlizzard")
    @CommandPermission("core.admin")
    public void startBlizzard() {
        CoreAPI.getInstance().startBlizzard();
    }

    @Subcommand("pararBlizzard")
    @CommandPermission("core.admin")
    public void stopBlizzard() {
        CoreAPI.getInstance().stopBlizzard();
    }

    @Subcommand("createlocation")
    @CommandPermission("core.admin")
    @Syntax("<locationName>")
    public void addCampfireLocation(Player player, String locationName) {
        YamlConfiguration coordinatesConfig = PluginImpl.getInstance().Coordinates.getConfig();

        String path = "generator." + locationName;

        coordinatesConfig.set(path + ".world", player.getWorld().getName());
        coordinatesConfig.set(path + ".x", player.getLocation().getX());
        coordinatesConfig.set(path + ".y", player.getLocation().add(0, -1, 0).getY());
        coordinatesConfig.set(path + ".z", player.getLocation().getZ());

        PluginImpl.getInstance().Coordinates.saveConfig();
    }

}
