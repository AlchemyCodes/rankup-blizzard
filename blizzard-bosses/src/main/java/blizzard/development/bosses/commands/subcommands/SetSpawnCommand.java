package blizzard.development.bosses.commands.subcommands;

import blizzard.development.bosses.utils.PluginImpl;
import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.Subcommand;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandAlias("bosses|boss|monstros|monstro")
public class SetSpawnCommand extends BaseCommand {

    @Subcommand("setspawn")
    public void onCommand(CommandSender sender) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("§cEste comando só pode ser utilizado por jogadores!");
            return;
        }

        Player player = (Player) sender;

        double x = player.getX();
        double y = player.getY();
        double z = player.getZ();
        float yaw = player.getYaw();
        float pitch = player.getPitch();
        String world = player.getWorld().getName();

        PluginImpl.getInstance().Locations.getConfig().set("spawn.location.world", world);
        PluginImpl.getInstance().Locations.getConfig().set("spawn.location.x", x);
        PluginImpl.getInstance().Locations.getConfig().set("spawn.location.y", y);
        PluginImpl.getInstance().Locations.getConfig().set("spawn.location.z", z);
        PluginImpl.getInstance().Locations.getConfig().set("spawn.location.yaw", yaw);
        PluginImpl.getInstance().Locations.getConfig().set("spawn.location.pitch", pitch);
        PluginImpl.getInstance().Locations.saveConfig();

        player.sendActionBar("§a§lYAY! §aLocal de spawn da área de bosses definido.");
    }
}
