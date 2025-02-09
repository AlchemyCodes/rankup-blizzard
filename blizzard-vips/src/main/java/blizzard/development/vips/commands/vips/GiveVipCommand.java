package blizzard.development.vips.commands.vips;

import blizzard.development.vips.plugin.PluginImpl;
import blizzard.development.vips.utils.RandomIdGenerator;
import blizzard.development.vips.vips.adapters.VipsAdapter;
import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandCompletion;
import co.aikar.commands.annotation.Default;
import co.aikar.commands.annotation.Syntax;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.sql.SQLException;

@CommandAlias("darvip")
public class GiveVipCommand extends BaseCommand {

    private final YamlConfiguration messagesConfig = PluginImpl.getInstance().Messages.getConfig();

    @Default
    @Syntax("<player> <vip> <tempo>")
    @CommandCompletion("@playerName @vipName @vipDate")
    public void onCommand(CommandSender sender, String playerName, String vipName, String durationInput) {
        VipsAdapter vipsAdapter = VipsAdapter.getInstance();
        Player targetPlayer = Bukkit.getPlayerExact(playerName);

        if (targetPlayer == null) {
            messagesConfig.getString("messages.playerNotFound");
            return;
        }

        if (!vipsAdapter.isVipValid(vipName)) {
            sender.sendMessage(messagesConfig.getString("messages.vipNotFound"));
            return;
        }

        long duration = vipsAdapter.getTimeParsed(durationInput);
        if (duration <= 0) {
            sender.sendMessage("§cFormato de tempo inválido!");
            return;
        }

        if (vipsAdapter.playerHasVip(String.valueOf(targetPlayer.getUniqueId()), vipName)) {
            vipsAdapter.extendVip(targetPlayer, vipName, duration);
            return;
        }

        vipsAdapter.giveVip(targetPlayer, vipsAdapter.getFormatedDate(), RandomIdGenerator.generateId(), vipName, duration);
        vipsAdapter.sendVipMessage(playerName, vipName);
    }
}