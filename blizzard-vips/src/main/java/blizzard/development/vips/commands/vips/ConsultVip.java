package blizzard.development.vips.commands.vips;

import blizzard.development.vips.database.dao.PlayersDAO;
import blizzard.development.vips.database.storage.PlayersData;
import blizzard.development.vips.utils.PluginImpl;
import blizzard.development.vips.utils.TimeConverter;
import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.*;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.hover.content.Text;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.sql.SQLException;
import java.util.List;

@CommandAlias("vip|vips")
public class ConsultVip extends BaseCommand {

    @Default
    @Syntax("<id|playerName>")
    @CommandCompletion("@playerName")
    public void onCommand(Player player, @Optional String argument) throws SQLException {
        if (argument == null) {
            sendPlayerVips(player, player.getName());
            return;
        }

        sendPlayerVips(player, argument);
    }

    public void sendPlayerVips(Player sender, String playerName) throws SQLException {
        PlayersDAO playersDAO = new PlayersDAO();
        YamlConfiguration messagesConfig = PluginImpl.getInstance().Messages.getConfig();

        Player specificPlayer = Bukkit.getPlayer(playerName);

        if (specificPlayer == null) {
            sender.sendMessage(messagesConfig.getString("messages.playerNotFound"));
            return;
        }

        List<PlayersData> allPlayerVips = playersDAO.getAllPlayerVips(specificPlayer.getName());

        if (allPlayerVips.isEmpty()) {
            sender.sendMessage(messagesConfig.getString("commands.consultVip.noActiveVip"));
            return;
        }

        for (PlayersData playerVip : allPlayerVips) {
            String vipName = playerVip.getVipName();
            String activationDate = playerVip.getVipActivationDate();
            long vipDuration = playerVip.getVipDuration();
            String converted = TimeConverter.convertSecondsToTimeFormat(vipDuration);

            String hoverMessage = messagesConfig.getString("commands.consultVip.hoverMessage")
                    .replace("{activationDate}", activationDate)
                    .replace("{duration}", converted);

            TextComponent vipMessage = new TextComponent(messagesConfig.getString("commands.consultVip.vipFormat")
                    .replace("{vipName}", vipName));

            vipMessage.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text(hoverMessage)));
            vipMessage.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND
                    ,"/vip details " + vipName));

            specificPlayer.spigot().sendMessage(vipMessage);
        }
    }

    @Subcommand("details")
    @Syntax("<vipName>")
    public void onVipDetailsCommand(Player player, String vipName) throws SQLException {
        PlayersDAO playersDAO = new PlayersDAO();
        YamlConfiguration messagesConfig = PluginImpl.getInstance().Messages.getConfig();
        List<PlayersData> allPlayerVips = playersDAO.getAllPlayerVips(player.getName());

        for (PlayersData playerVip : allPlayerVips) {
            String vipName1 = playerVip.getVipName();
            String vipActivationDate = playerVip.getVipActivationDate();
            String converted = TimeConverter.convertSecondsToTimeFormat(playerVip.getVipDuration());

            if (vipName1.equalsIgnoreCase(vipName)) {
                String detailsMessage = messagesConfig.getString("commands.consultVip.detailsMessage")
                        .replace("{vipName}", vipName1)
                        .replace("{activationDate}", vipActivationDate)
                        .replace("{duration}", converted);

                player.sendMessage(detailsMessage);
                return;
            }
        }

        player.sendMessage(messagesConfig.getString("commands.consultVip.noVipDetails").replace("{vipName}", vipName));
    }
}
