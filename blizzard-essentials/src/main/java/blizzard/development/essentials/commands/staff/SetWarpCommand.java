package blizzard.development.essentials.commands.staff;

import blizzard.development.essentials.utils.PluginImpl;
import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Default;
import co.aikar.commands.annotation.Syntax;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandAlias("setwarp|setarwarp")
public class SetWarpCommand extends BaseCommand {

    @Default
    @CommandPermission("alchemy.essentials.staff")
    @Syntax("<nome>")
    public void onCommand(CommandSender commandSender, String warp) {
        if (!(commandSender instanceof Player)) {
            return;
        }

        Player player = (Player) commandSender;

        double x = player.getX();
        double y = player.getY();
        double z = player.getZ();
        float yaw = player.getYaw();
        float pitch = player.getPitch();
        String world = player.getWorld().getName();

        PluginImpl.getInstance().Locations.getConfig().set("warps." + warp + ".location.world", world);
        PluginImpl.getInstance().Locations.getConfig().set("warps." + warp + ".location.x", x);
        PluginImpl.getInstance().Locations.getConfig().set("warps." + warp + ".location.y", y);
        PluginImpl.getInstance().Locations.getConfig().set("warps." + warp + ".location.z", z);
        PluginImpl.getInstance().Locations.getConfig().set("warps." + warp + ".location.yaw", yaw);
        PluginImpl.getInstance().Locations.getConfig().set("warps." + warp + ".location.pitch", pitch);
        PluginImpl.getInstance().Locations.getConfig().set("warps." + warp + ".messages.title", "§b§lYAY!");
        PluginImpl.getInstance().Locations.getConfig().set("warps." + warp + ".messages.sub-title", "§bVocê foi teleportado com sucesso.");
        PluginImpl.getInstance().Locations.getConfig().set("warps." + warp + ".messages.action-bar", "§b§lYAY! §bVocê foi teleportado para a warp §7%warp%§b.");


        PluginImpl.getInstance().Locations.saveConfig();

        player.sendActionBar("§b§lYAY! §bVocê setou a localização da warp §7" + warp + "§b com sucesso!");
    }
}
