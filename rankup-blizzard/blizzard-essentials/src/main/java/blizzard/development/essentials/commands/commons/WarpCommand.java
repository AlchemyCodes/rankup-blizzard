package blizzard.development.essentials.commands.commons;

import blizzard.development.essentials.utils.PluginImpl;
import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.Default;
import co.aikar.commands.annotation.Syntax;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandAlias("warp|warps")
public class WarpCommand extends BaseCommand {

    @Default
    @Syntax("<warp>")
    public void onCommand(CommandSender commandSender, String warp) {
        if (!(commandSender instanceof Player)) {
            return;
        }

        Player player = (Player) commandSender;

        if (PluginImpl.getInstance().Locations.getConfig().contains("warps." + warp)) {

            String worldSpawn = PluginImpl.getInstance().Locations.getConfig().getString("warps." + warp + ".location.world");
            double x = PluginImpl.getInstance().Locations.getConfig().getDouble("warps." + warp + ".location.x");
            double y = PluginImpl.getInstance().Locations.getConfig().getDouble("warps." + warp + ".location.y");
            double z = PluginImpl.getInstance().Locations.getConfig().getDouble("warps." + warp + ".location.z");
            float yaw = (float) PluginImpl.getInstance().Locations.getConfig().getDouble("warps." + warp + ".location.yaw");
            float pitch = (float) PluginImpl.getInstance().Locations.getConfig().getDouble("warps." + warp + ".location.pitch");

            String title = PluginImpl.getInstance().Locations.getString("warps." + warp + ".messages.title");
            String subtitle = PluginImpl.getInstance().Locations.getString("warps." + warp + ".messages.sub-title");
            String actionbar = PluginImpl.getInstance().Locations.getString("warps." + warp + ".messages.action-bar");

            player.sendTitle(
                    title,
                    subtitle,
                    10,
                    60,
                    20
            );

            if (actionbar != null) {
                actionbar = actionbar.replace("%warp%", warp);
                player.sendActionBar(actionbar);
            }

            Location location = new Location(
                    Bukkit.getWorld(worldSpawn),
                    x,
                    y,
                    z,
                    yaw,
                    pitch
            );

            player.teleport(location);
        } else {
            player.sendTitle(
                    "§c§lCalma ai!",
                    "§cA warp §7" + warp + "§c não existe.",
                    10,
                    60,
                    20
            );
        }
    }
}
