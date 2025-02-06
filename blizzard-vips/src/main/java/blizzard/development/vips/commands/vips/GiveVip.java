package blizzard.development.vips.commands.vips;

import blizzard.development.vips.utils.PluginImpl;
import blizzard.development.vips.utils.RandomIdGenerator;
import blizzard.development.vips.utils.vips.VipUtils;
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
import java.util.Arrays;

@CommandAlias("darvip")
public class GiveVip extends BaseCommand {

    private final YamlConfiguration messagesConfig = PluginImpl.getInstance().Messages.getConfig();

    @Default
    @Syntax("<player> <vip> <tempo>")
    @CommandCompletion("@playerName @vipName @vipDate")
    public void onCommand(CommandSender sender, String playerName, String vipName, String durationInput) throws SQLException {
        VipUtils vipUtils = VipUtils.getInstance();
        Player targetPlayer = Bukkit.getPlayerExact(playerName);

        if (targetPlayer == null) {
            messagesConfig.getString("messages.playerNotFound");
            return;
        }

        if (!vipUtils.vipExist(vipName)) {
            sender.sendMessage(messagesConfig.getString("messages.vipNotFound"));
            return;
        }

        long duration = vipUtils.getDuration(durationInput);

        if (vipUtils.hasVip(vipName)) {
            vipUtils.extendVip(targetPlayer, vipName, vipUtils, duration);
            return;
        }

        vipUtils.giveVip(targetPlayer, vipUtils.getDate(), RandomIdGenerator.generateVipId(), vipName, duration);

        vipUtils.sendVipMessage(playerName, vipName);
    }
}
