package blizzard.development.vips.commands.vips;

import blizzard.development.vips.database.cache.methods.PlayersCacheMethod;
import blizzard.development.vips.database.dao.PlayersDAO;
import blizzard.development.vips.database.storage.PlayersData;
import blizzard.development.vips.utils.PluginImpl;
import blizzard.development.vips.utils.RandomIdGenerator;
import blizzard.development.vips.utils.TimeParser;
import blizzard.development.vips.utils.vips.VipUtils;
import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandCompletion;
import co.aikar.commands.annotation.Default;
import co.aikar.commands.annotation.Syntax;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.sql.SQLException;
import java.util.List;

@CommandAlias("darvip")
public class GiveVip extends BaseCommand {

    private final PlayersDAO playersDAO;
    YamlConfiguration messagesConfig = PluginImpl.getInstance().Messages.getConfig();

    public GiveVip(PlayersDAO playersDAO) {
        this.playersDAO = playersDAO;
    }

    @Default
    @Syntax("<player> <vip> <tempo>")
    @CommandCompletion("@playerName @vipName @vipDate")
    public void onCommand(Player sender, String playerName, String vipName, String durationInput) throws SQLException {
        VipUtils vipUtils = VipUtils.getInstance();
        PlayersCacheMethod instance = PlayersCacheMethod.getInstance();
        Player targetPlayer = Bukkit.getPlayerExact(playerName);

        if (targetPlayer == null) {
            messagesConfig.getString("messages.PlayerNotFound");
            return;
        }

        long duration = getDuration(durationInput);

        if (hasVip(sender, targetPlayer, playerName, vipName, vipUtils, duration)) {
            return;
        }

        vipUtils.giveVip(targetPlayer, instance.getDate(), RandomIdGenerator.generateVipId(), vipName, duration);

        sendVipMessage(playerName, vipName);
    }

    public void sendVipMessage(String playerName, String vipName) {
        String title = messagesConfig.getString("commands.giveVip.vip.title");
        String subtitle = messagesConfig.getString("commands.giveVip.vip.subtitle")
                .replace("{playerName}", playerName)
                .replace("{vipName}", vipName);

        for (Player player : Bukkit.getOnlinePlayers()) {
            player.sendTitle(title, subtitle);
            player.playSound(player.getLocation(), Sound.ENTITY_LIGHTNING_BOLT_THUNDER, 1, 1);
        }
    }

    public boolean hasVip(Player sender, Player targetPlayer, String playerName, String vipName, VipUtils vipUtils, long duration) throws SQLException {
        YamlConfiguration messagesConfig = PluginImpl.getInstance().Messages.getConfig();

        List<PlayersData> playerVips = playersDAO.getAllPlayerVips(targetPlayer.getName());

        boolean hasVip = vipUtils.hasVip(playerName, vipName);

        if (hasVip) {
            for (PlayersData vipData : playerVips) {
                if (vipData.getVipName().equalsIgnoreCase(vipName)) {
                    vipData.setVipDuration(vipData.getVipDuration() + duration);
                    playersDAO.updatePlayerData(vipData);
                }
            }

            sender.sendMessage(messagesConfig.getString("commands.giveVip.vipTimeExtended"));
            return true;
        }
        return false;
    }

    public long getDuration(String durationInput) {
        long duration = 0;

        try {
            duration = TimeParser.parseTime(durationInput);
            return duration;
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }

        return duration;
    }
}
