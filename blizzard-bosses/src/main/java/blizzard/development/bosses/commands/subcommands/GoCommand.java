package blizzard.development.bosses.commands.subcommands;

import blizzard.development.bosses.methods.GeneralMethods;
import blizzard.development.bosses.utils.PluginImpl;
import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Subcommand;
import co.aikar.commands.annotation.Syntax;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;


@CommandAlias("bosses|boss|monstros|monstro")
public class GoCommand extends BaseCommand {

    @Subcommand("go|join|ir|")
    @Syntax("<ir>")
    @CommandPermission("blizzard.bosses.basic")
    public void onJoin(CommandSender sender) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("§cEste comando só pode ser utilizado por jogadores!");
            return;
        }

        Player player = (Player) sender;
        sendPlayerToWorld(player, false);
    }

    @Subcommand("leave|sair")
    @Syntax("<sair>")
    @CommandPermission("blizzard.bosses.basic")
    public void onLeave(CommandSender sender) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("§cEste comando só pode ser utilizado por jogadores!");
            return;
        }

        Player player = (Player) sender;
        sendPlayerToWorld(player, true);
    }

    public static void sendPlayerToWorld(Player player, Boolean isInBossesWorld) {
        String worldSpawn = PluginImpl.getInstance().Locations.getConfig().getString("spawn.location.world");
        double x = PluginImpl.getInstance().Locations.getConfig().getDouble("spawn.location.x");
        double y = PluginImpl.getInstance().Locations.getConfig().getDouble("spawn.location.y");
        double z = PluginImpl.getInstance().Locations.getConfig().getDouble("spawn.location.z");
        float yaw = (float) PluginImpl.getInstance().Locations.getConfig().getDouble("spawn.location.yaw");
        float pitch = (float) PluginImpl.getInstance().Locations.getConfig().getDouble("spawn.location.pitch");

        if (worldSpawn == null) {
            player.sendActionBar("§c§lEI! §cO local de spawn do mundo de bosses não foi definido ou é inválido.");
            return;
        }

        World world = Bukkit.getWorld(worldSpawn);

        if (isInBossesWorld) {
            GeneralMethods.removePlayerFromWorld(player);

            player.performCommand("spawn");

            player.sendTitle(
                    "§c§lBOSSES!",
                    "§cVocê saiu da área de bosses.",
                    10,
                    70,
                    20
            );
        } else {
            GeneralMethods.setPlayerInWorld(player);

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
            player.sendTitle(
                    "§a§lBosses!",
                    "§aVocê entrou na área de bosses.",
                    10,
                    70,
                    20
            );
        }
    }
}
