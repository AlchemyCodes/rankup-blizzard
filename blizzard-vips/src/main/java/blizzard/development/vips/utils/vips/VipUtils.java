package blizzard.development.vips.utils.vips;

import blizzard.development.vips.database.cache.PlayersCacheManager;
import blizzard.development.vips.database.dao.PlayersDAO;
import blizzard.development.vips.database.storage.PlayersData;
import blizzard.development.vips.utils.PluginImpl;
import blizzard.development.vips.utils.TimeParser;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;

public class VipUtils {

    private static VipUtils instance;
    private final PlayersDAO playersDAO;

    public HashMap<String, String> activeVip = new HashMap<>();
    public boolean isVipTimeFrozen = false;

    public VipUtils(PlayersDAO playersDAO) {
        this.playersDAO = playersDAO;
    }

    public void setActiveVip(Player player, String vipName) {
        activeVip.put(player.getName(), vipName);
    }

    public String getActiveVip(Player player) {
        return activeVip.get(player.getName());
    }

    public void removeVip(Player player, String playerName, String vipName) throws SQLException {
        YamlConfiguration messagesConfig = PluginImpl.getInstance().Messages.getConfig();
        List<PlayersData> allPlayerVips = playersDAO.getAllPlayerVips(playerName);

        boolean vipFound = false;

        for (PlayersData playersData : allPlayerVips) {
            if (playersData.getVipName().equalsIgnoreCase(vipName)) {
                playersDAO.deletePlayerVip(playersData);

                player.sendMessage(messagesConfig.getString("commands.removeVip.vipRemoved")
                        .replace("{vipName}", vipName));

                vipFound = true;
                break;
            }
        }

        if (!vipFound) {
            player.sendMessage(messagesConfig.getString("commands.removeVip.vipNotFound")
                    .replace("{vipName}", vipName));
        }
    }

    public boolean hasVip(String playerName, String vipName) throws SQLException {
        List<PlayersData> allPlayerVips = playersDAO.getAllPlayerVips(playerName);

        for (PlayersData playersData : allPlayerVips) {
            if (playersData.getVipName().equalsIgnoreCase(vipName)) {
                return true;
            }
        }
        return false;
    }

    public void extendVip(Player sender, String targetPlayer, String vipName, VipUtils vipUtils, long duration) throws SQLException {
        YamlConfiguration messagesConfig = PluginImpl.getInstance().Messages.getConfig();

        List<PlayersData> playerVips = playersDAO.getAllPlayerVips(targetPlayer);

        boolean hasVip = vipUtils.hasVip(targetPlayer, vipName);

        if (hasVip) {
            for (PlayersData vipData : playerVips) {
                if (vipData.getVipName().equalsIgnoreCase(vipName)) {
                    vipData.setVipDuration(vipData.getVipDuration() + duration);
                    playersDAO.updatePlayerData(vipData);
                }
            }

            sender.sendMessage(messagesConfig.getString("commands.giveVip.vipTimeExtended"));
        }
    }

    public void giveVip(Player targetPlayer, String date, String vipId, String vipName, long duration) throws SQLException {
        PlayersData newPlayerData = new PlayersData(
                targetPlayer.getUniqueId().toString(),
                playersDAO.getNextVipIndex(targetPlayer.getName()),
                targetPlayer.getName(),
                date,
                vipId,
                vipName.toUpperCase(),
                duration
        );

        playersDAO.createPlayerData(newPlayerData);

        PlayersCacheManager.getInstance().cachePlayerData(targetPlayer.getName(), newPlayerData);

        Bukkit.dispatchCommand(
                Bukkit.getConsoleSender(), "lp user " + targetPlayer.getName() + " parent add " + vipName);
    }

    public void sendVipMessage(String playerName, String vipName) {
        YamlConfiguration messagesConfig = PluginImpl.getInstance().Messages.getConfig();

        String title = messagesConfig.getString("commands.giveVip.vip.title");
        String subtitle = messagesConfig.getString("commands.giveVip.vip.subtitle")
                .replace("{playerName}", playerName)
                .replace("{vipName}", vipName);

        for (Player player : Bukkit.getOnlinePlayers()) {
            player.sendTitle(title, subtitle);
            player.playSound(player.getLocation(), Sound.ENTITY_LIGHTNING_BOLT_THUNDER, 1, 1);
        }
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

    public String getDate() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy 'às' HH:mm");

        return now.format(formatter);
    }


    public static VipUtils getInstance() {
        if (instance == null) {
            instance = new VipUtils(new PlayersDAO());
        }
        return instance;
    }
}
