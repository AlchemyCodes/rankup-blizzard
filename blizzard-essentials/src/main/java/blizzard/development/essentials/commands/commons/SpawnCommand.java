package blizzard.development.essentials.commands.commons;

import blizzard.development.essentials.utils.PluginImpl;
import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.Default;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandAlias("spawn|inicio")
public class SpawnCommand extends BaseCommand {

    @Default
    public void onCommand(CommandSender commandSender) {
        if (!(commandSender instanceof Player)) {
            return;
        }

        Player player = (Player) commandSender;

        String worldSpawn = PluginImpl.getInstance().Locations.getConfig().getString("spawn.location.world");
        double x = PluginImpl.getInstance().Locations.getConfig().getDouble("spawn.location.x");
        double y = PluginImpl.getInstance().Locations.getConfig().getDouble("spawn.location.y");
        double z = PluginImpl.getInstance().Locations.getConfig().getDouble("spawn.location.z");
        float yaw = (float) PluginImpl.getInstance().Locations.getConfig().getDouble("spawn.location.yaw");
        float pitch = (float) PluginImpl.getInstance().Locations.getConfig().getDouble("spawn.location.pitch");

        if (worldSpawn == null) return;

        World world = Bukkit.getWorld(worldSpawn);

        player.teleport(
                new Location(
                        world,
                        x,
                        y,
                        z,
                        yaw,
                        pitch
                )
        );
    }
}
