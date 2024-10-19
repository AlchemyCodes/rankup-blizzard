package blizzard.development.bosses.commands;

import blizzard.development.bosses.utils.PluginImpl;
import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.Default;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;


@CommandAlias("bosses|boss|monstros|monstro")
public class BossesCommand extends BaseCommand {

    @Default
    private void onCommand(CommandSender sender) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("§cEste comando só pode ser utilizado por jogadores!");
            return;
        }
        Player player = (Player) sender;
        sendPlayerToBossWorld(player);
        player.sendMessage("§aVocê foi teleportado para o mundo de bosses!");

    }

    private void sendPlayerToBossWorld(Player player) {
        String worldSpawn = PluginImpl.getInstance().Locations.getConfig().getString("spawn.location.world");
        double x = PluginImpl.getInstance().Locations.getConfig().getDouble("spawn.location.x");
        double y = PluginImpl.getInstance().Locations.getConfig().getDouble("spawn.location.y");
        double z = PluginImpl.getInstance().Locations.getConfig().getDouble("spawn.location.z");
        float yaw = (float) PluginImpl.getInstance().Locations.getConfig().getDouble("spawn.location.yaw");
        float pitch = (float) PluginImpl.getInstance().Locations.getConfig().getDouble("spawn.location.pitch");

        if (worldSpawn == null) {
            player.sendMessage("§cO local de spawn do mundo de bosses não foi definido ou é inválido!");
            return;
        }

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
