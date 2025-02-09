package blizzard.development.vips.utils;

import blizzard.development.vips.database.cache.VipCacheManager;
import blizzard.development.vips.database.cache.methods.VipCacheMethod;
import blizzard.development.vips.database.dao.VipDAO;
import blizzard.development.vips.database.storage.VipData;
import blizzard.development.vips.vips.enums.VipEnum;
import blizzard.development.vips.plugin.PluginImpl;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public class VipUtils {

    private static VipUtils instance;
    private final VipDAO vipDAO;

    public VipUtils(VipDAO vipDAO) {
        this.vipDAO = vipDAO;
    }




    public boolean hasVip(String uuid, String vipName) {
        return VipCacheManager.getInstance().vipCache.values().stream()
                .anyMatch(vip -> vip.getUuid().equals(uuid) && vip.getVipName().equalsIgnoreCase(vipName));
    }

    public List<VipData> getPlayerVips(String uuid) {
        return VipCacheManager.getInstance().vipCache.values().stream()
                .filter(vip -> vip.getUuid().equals(uuid))
                .collect(Collectors.toList());
    }

    public void extendVip(Player player, String vipName, long duration) {
        YamlConfiguration messagesConfig = PluginImpl.getInstance().Messages.getConfig();

        List<VipData> playerVips = getPlayerVips(String.valueOf(player.getUniqueId()));

        for (VipData vipData : playerVips) {
            if (vipData.getVipName().equalsIgnoreCase(vipName)) {
                VipCacheMethod.getInstance().setVipDuration(vipData.getVipId(), vipData.getVipDuration() + duration);
                VipCacheManager.getInstance().cacheVipData(vipData.getVipId(), vipData);
            }
        }

        player.sendMessage(messagesConfig.getString("commands.giveVip.vipTimeExtended"));
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


    public String getFormatedDate() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy 'às' HH:mm");

        return now.format(formatter);
    }


    public boolean isVipValid(String vipName) {
        return Arrays.stream(VipEnum.values())
                .anyMatch(vipEnum -> vipEnum.getName().equalsIgnoreCase(vipName));
    }

    public static VipUtils getInstance() {
        if (instance == null) {
            instance = new VipUtils(new VipDAO());
        }
        return instance;
    }
}