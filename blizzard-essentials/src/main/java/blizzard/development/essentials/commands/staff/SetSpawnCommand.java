package blizzard.development.essentials.commands.staff;

import blizzard.development.essentials.utils.PluginImpl;
import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Default;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandAlias("setspawn|setarspawn|setspaw")
public class SetSpawnCommand extends BaseCommand {

    @Default
    @CommandPermission("alchemy.essentials.staff")
    public void onCommand(CommandSender commandSender) {
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

        PluginImpl.getInstance().Locations.getConfig().set("spawn.location.world", world);
        PluginImpl.getInstance().Locations.getConfig().set("spawn.location.x", x);
        PluginImpl.getInstance().Locations.getConfig().set("spawn.location.y", y);
        PluginImpl.getInstance().Locations.getConfig().set("spawn.location.z", z);
        PluginImpl.getInstance().Locations.getConfig().set("spawn.location.yaw", yaw);
        PluginImpl.getInstance().Locations.getConfig().set("spawn.location.pitch", pitch);
        PluginImpl.getInstance().Locations.saveConfig();

        player.sendMessage("");
        player.sendMessage(" §b§lYAY! §bVocê setou o spawn nas cordenadas;");
        player.sendMessage(" §7x" + x + " y" + y + " z" + z + " §fno mundo §8[" + world + "]");
        player.sendMessage("");
    }
}
