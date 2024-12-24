package blizzard.development.vips.utils.vips;

import blizzard.development.vips.database.cache.PlayersCacheManager;
import blizzard.development.vips.database.dao.PlayersDAO;
import blizzard.development.vips.database.storage.PlayersData;
import blizzard.development.vips.utils.PluginImpl;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

public class VipUtils {

    private static VipUtils instance;
    private final PlayersDAO playersDAO;

    public HashMap<String, String> activeVip = new HashMap<>();

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
    }


    public static VipUtils getInstance() {
        if (instance == null) {
            instance = new VipUtils(new PlayersDAO());
        }
        return instance;
    }
}
