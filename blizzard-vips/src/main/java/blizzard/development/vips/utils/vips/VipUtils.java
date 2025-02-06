package blizzard.development.vips.utils.vips;

import blizzard.development.vips.database.cache.PlayersCacheManager;
import blizzard.development.vips.database.cache.methods.PlayersCacheMethod;
import blizzard.development.vips.database.dao.PlayersDAO;
import blizzard.development.vips.database.storage.PlayersData;
import blizzard.development.vips.enums.VipEnum;
import blizzard.development.vips.utils.PluginImpl;
import blizzard.development.vips.utils.TimeParser;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

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

    public void removeVip(Player player, String vipName) {
        YamlConfiguration messagesConfig = PluginImpl.getInstance().Messages.getConfig();
        Collection<PlayersData> allPlayersData = PlayersCacheManager.getInstance().getAllPlayersData();

        boolean vipFound = false;

        for (PlayersData playersData : allPlayersData) {
            if (playersData.getVipName().equalsIgnoreCase(vipName)) {
                PlayersCacheMethod.getInstance().removeVip(playersData.getVipId());
                PlayersCacheManager.getInstance().cachePlayerData(playersData.getVipId(), playersData);

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

    public boolean hasVip(String vipName) {
        Collection<PlayersData> allPlayersData = PlayersCacheManager.getInstance().getAllPlayersData();

        for (PlayersData playersData : allPlayersData) {
            if (playersData.getVipName().equalsIgnoreCase(vipName)) {
                return true;
            }
        }
        return false;
    }

    public void extendVip(Player sender, String vipName, VipUtils vipUtils, long duration) {
        YamlConfiguration messagesConfig = PluginImpl.getInstance().Messages.getConfig();

        Collection<PlayersData> allPlayersData = PlayersCacheManager.getInstance().getAllPlayersData();

        boolean hasVip = vipUtils.hasVip(vipName);

        if (hasVip) {
            for (PlayersData vipData : allPlayersData) {
                if (vipData.getVipName().equalsIgnoreCase(vipName)) {
                    PlayersCacheMethod.getInstance().setVipDuration(vipData.getVipId(), vipData.getVipDuration() + duration);
                    PlayersCacheManager.getInstance().cachePlayerData(vipData.getVipId(), vipData);
                }
            }

            sender.sendMessage(messagesConfig.getString("commands.giveVip.vipTimeExtended"));
        }
    }

    public void giveVip(Player targetPlayer, String date, String vipId, String vipName, long duration) {
        PlayersData playerData = playersDAO.findPlayerData(targetPlayer.getUniqueId().toString());

        if (playerData == null) {
            try {
                playerData = new PlayersData(
                    targetPlayer.getUniqueId().toString(),
                    playersDAO.getNextVipIndex(targetPlayer.getName()),
                    targetPlayer.getName(),
                    date,
                    vipId,
                    vipName.toUpperCase(),
                    duration

            );

                playersDAO.createPlayerData(playerData);
            } catch (SQLException exception) {
                exception.printStackTrace();
            }
        }

        PlayersCacheManager.getInstance().cachePlayerData(
                vipId,
                playerData
        );

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

    public boolean vipExist(String vipName) {
        return Arrays.stream(VipEnum.values())
                .anyMatch(vipEnum -> vipEnum.getName().equalsIgnoreCase(vipName));
    }

    public static VipUtils getInstance() {
        if (instance == null) {
            instance = new VipUtils(new PlayersDAO());
        }
        return instance;
    }
}
