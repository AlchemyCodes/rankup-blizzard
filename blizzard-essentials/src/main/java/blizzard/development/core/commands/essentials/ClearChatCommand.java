package blizzard.development.core.commands.essentials;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Default;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;

@CommandAlias("clearchat")
public class ClearChatCommand extends BaseCommand {
    @Default
    @CommandPermission("alchemy.core.admin")
    public void onCommand(CommandSender sender) {
        for (int i = 0; i < 51; i++) {
            Bukkit.broadcast(Component.text("§7"));
        }
        sender.sendMessage("§7O chat foi limpo com sucesso!");
    }
}
