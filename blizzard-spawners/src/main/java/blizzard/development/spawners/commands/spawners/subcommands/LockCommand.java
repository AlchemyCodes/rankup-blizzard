package blizzard.development.spawners.commands.spawners.subcommands;

import blizzard.development.spawners.utils.PluginImpl;
import blizzard.development.spawners.utils.items.TextAPI;
import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.*;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;

@CommandAlias("spawners|spawner|geradores|gerador")
@CommandPermission("blizzard.spawners.admin")
public class LockCommand extends BaseCommand {

    @Subcommand("lock|travar|bloquear")
    @CommandCompletion("@spawners")
    @Syntax("<spawner>")
    public void onCommand(CommandSender sender, String type) {
        FileConfiguration config = PluginImpl.getInstance().Spawners.getConfig();

        String spawnerType = getSpawnerType(type);

        String spawner = config.getString("spawners." + spawnerType + ".type", null);

        if (spawner == null) {
            sender.sendActionBar(TextAPI.parse("§c§lEI! §cO gerador de §7" + type + "§c não existe."));
        } else {
            boolean released = config.getBoolean("spawners." + spawnerType + ".permitted-purchase", false);

            if (released) {
                setSpawnerReleased(config, spawnerType);
                sender.sendActionBar(TextAPI.parse("§a§lYAY! §aO gerador de §7" + type + "§a foi bloqueado."));
            } else {
                sender.sendActionBar(TextAPI.parse("§c§lEI! §cO gerador de §7" + type + "§c já está bloqueado."));
            }
        }
    }

    public void setSpawnerReleased(FileConfiguration config, String spawner) {
        config.set("spawners." + spawner + ".permitted-purchase", false);
        PluginImpl.getInstance().Spawners.saveConfig();
        PluginImpl.getInstance().Spawners.reloadConfig();
    }

    public String getSpawnerType(String spawner) {
        return switch (spawner) {
            case "PIG", "pig", "PORCO", "porco" -> "pig";
            case "COW", "cow", "VACA", "vaca" -> "cow";
            case "MOOSHROOM", "mooshroom", "Coguvaca", "coguvaca" -> "mooshroom";
            case "SHEEP", "sheep", "OVELHA", "ovelha" -> "sheep";
            case "ZOMBIE", "zombie", "ZUMBI", "zumbi" -> "zombie";
            default -> null;
        };
    }
}
