package blizzard.development.bosses.commands.subcommands;

import blizzard.development.bosses.tools.AreaTool;
import blizzard.development.bosses.tools.RadarTool;
import blizzard.development.bosses.tools.SwordTool;
import blizzard.development.bosses.utils.CooldownUtils;
import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Subcommand;
import co.aikar.commands.annotation.Syntax;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.concurrent.TimeUnit;

@CommandAlias("bosses|boss|monstros|monstro")
public class ToolsCommand extends BaseCommand {

    @Subcommand("sword|espada")
    @Syntax("<espada>")
    @CommandPermission("blizzard.bosses.basic")
    public void onSword(CommandSender sender) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("§cEste comando só pode ser utilizado por jogadores!");
            return;
        }

        Player player = (Player) sender;

        String cooldown = "blizzard.bosses.tools-sword.cooldown";
        if (CooldownUtils.getInstance().isInCountdown(player, cooldown)) {
            sender.sendMessage("§c§lEI! §cAguarde um pouco para executar essa ação.");
            return;
        }

        SwordTool.give(player);
        CooldownUtils.getInstance().createCountdown(player, cooldown, 3, TimeUnit.SECONDS);
        sender.sendMessage("§b§lYAY! §bVocê resgatou uma espada para derrotar bosses.");
    }

    @Subcommand("radar")
    @Syntax("<radar>")
    @CommandPermission("blizzard.bosses.basic")
    public void onRadar(CommandSender sender) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("§cEste comando só pode ser utilizado por jogadores!");
            return;
        }

        Player player = (Player) sender;

        String cooldown = "blizzard.bosses.tools-radar.cooldown";
        if (CooldownUtils.getInstance().isInCountdown(player, cooldown)) {
            sender.sendMessage("§c§lEI! §cAguarde um pouco para executar essa ação.");
            return;
        }

        RadarTool.give(player);
        CooldownUtils.getInstance().createCountdown(player, cooldown, 3, TimeUnit.SECONDS);
        sender.sendMessage("§b§lYAY! §bVocê resgatou um radar para encontrar bosses.");
    }

    @Subcommand("area")
    @Syntax("<area>")
    @CommandPermission("blizzard.bosses.admin")
    public void onArea(CommandSender sender) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("§cEste comando só pode ser utilizado por jogadores!");
            return;
        }

        Player player = (Player) sender;

        AreaTool.give(player);
        sender.sendMessage("§b§lYAY! §bVocê resgatou um item de area.");
    }
}
