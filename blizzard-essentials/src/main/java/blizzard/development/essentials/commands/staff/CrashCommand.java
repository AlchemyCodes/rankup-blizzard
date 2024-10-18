package blizzard.development.essentials.commands.staff;

import blizzard.development.essentials.utils.PacketSender;
import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Default;
import co.aikar.commands.annotation.Syntax;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandAlias("crashar|crash")
public class CrashCommand extends BaseCommand {

    @Default
    @CommandPermission("alchemy.essentials.staff.crash")
    @Syntax("<jogador>")
    public void onCommand(CommandSender commandSender, String playerTarget) {


        Player player = Bukkit.getPlayer(playerTarget);
        PacketSender.sendPackets(player, 99999);

        commandSender.sendActionBar(Component.text("§b§lYAY! §bComando crash realizado com sucesso."));
    }
}
