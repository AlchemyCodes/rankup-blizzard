package blizzard.development.spawners.commands.spawners.subcommands;

import blizzard.development.spawners.utils.PluginImpl;
import blizzard.development.spawners.utils.items.TextAPI;
import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.*;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.List;

@CommandAlias("spawners|spawner|geradores|gerador")
@CommandPermission("blizzard.spawners.admin")
public class UnlockCommand extends BaseCommand {

    @Subcommand("unlock|destravar|liberar|desbloquear")
    @CommandCompletion("@spawners")
    @Syntax("<spawner>")
    public void onCommand(Player player, String type) {
        FileConfiguration config = PluginImpl.getInstance().Spawners.getConfig();

        String spawnerType = getSpawnerType(type);

        String spawner = config.getString("spawners." + spawnerType + ".type", null);

        if (spawner == null) {
            player.sendActionBar(TextAPI.parse("§c§lEI! §cO gerador de §7" + type + "§c não existe."));
            player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 0.5f, 0.5f);
        } else {
            boolean released = config.getBoolean("spawners." + spawnerType + ".permitted-purchase", false);

            if (released) {
                player.sendActionBar(TextAPI.parse("§c§lEI! §cO gerador de §7" + type + "§c já está liberado."));
            } else {
                setSpawnerReleased(config, spawnerType);

                player.sendActionBar(TextAPI.parse("§a§lYAY! §aO gerador de §7" + type + "§a foi liberado."));

                List<String> messages = Arrays.asList(
                        "",
                        "§a§l Um novo spawner!",
                        "§f O gerador de " + getSpawnerName(spawnerType) + "§f foi liberado.",
                        "§8 [Corra e garanta seu domínio no servidor]",
                        ""
                );

                for (Player players : Bukkit.getOnlinePlayers()) {
                    messages.forEach(players::sendMessage);
                    players.sendTitle(
                            "§a§lGeradores!",
                            "§7Um novo gerador foi liberado.",
                            10,
                            70,
                            20
                    );
                    players.playSound(players.getLocation(), Sound.ENTITY_LIGHTNING_BOLT_THUNDER, 1.0f, 1.0f);
                }
            }

        }
    }

    public void setSpawnerReleased(FileConfiguration config, String spawner) {
        config.set("spawners." + spawner + ".permitted-purchase", true);
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

    public String getSpawnerName(String spawner) {
        return switch (spawner) {
            case "PIG", "pig", "PORCO", "porco" -> "§dPorco";
            case "COW", "cow", "VACA", "vaca" -> "§8Vaca";
            case "MOOSHROOM", "mooshroom", "Coguvaca", "coguvaca" -> "§cCoguvaca";
            case "SHEEP", "sheep", "OVELHA", "ovelha" -> "§fOvelha";
            case "ZOMBIE", "zombie", "ZUMBI", "zumbi" -> "§2Zumbi";
            default -> null;
        };
    }
}
