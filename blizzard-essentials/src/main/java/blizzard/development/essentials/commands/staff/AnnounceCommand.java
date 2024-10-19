package blizzard.development.essentials.commands.staff;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Default;
import co.aikar.commands.annotation.Syntax;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Server;
import org.bukkit.Sound;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandAlias("anuncio|anunciar")
public class AnnounceCommand extends BaseCommand {

    @Default
    @CommandPermission("alchemy.essentials.staff")
    @Syntax("<anuncio>")
    public void onCommand(CommandSender commandSender, String[] announce) {

        Server server = Bukkit.getServer();
        int onlinePlayers = server.getOnlinePlayers().size();

        StringBuilder announceBuilder = new StringBuilder();

        for (String arg : announce) {
            announceBuilder.append(arg).append(" ");
        }

        String announcement = announceBuilder.toString().trim();

        for (Player player : Bukkit.getOnlinePlayers()) {

            announcement = ChatColor.translateAlternateColorCodes('&', announcement);

            player.sendMessage("");
            player.sendMessage(" §b§lEI! §bAnúncio novo na área.");
            player.sendMessage(" §b❆§f " + announcement);
            player.sendMessage("");

            player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 5f, 5f);

            player.sendTitle(
                    "§b§lANÚNCIO!",
                    "§fConfira já o chat.",
                    10,
                    20,
                    70
            );

            commandSender.sendActionBar(Component.text("§b§lYAY! §bVocê enviou um anúncio para todos os §l" + onlinePlayers + "§b jogadores online."));
            commandSender.sendMessage(Component.text("§b§lYAY! §bVocê enviou um anúncio para todos os §l" + onlinePlayers + "§b jogadores online."));
        }
    }
}
