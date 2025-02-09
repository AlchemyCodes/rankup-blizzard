package blizzard.development.vips.vips.adapters;

import blizzard.development.vips.database.cache.VipCacheManager;
import blizzard.development.vips.database.cache.methods.VipCacheMethod;
import blizzard.development.vips.database.dao.VipDAO;
import blizzard.development.vips.database.storage.VipData;
import blizzard.development.vips.plugin.PluginImpl;
import blizzard.development.vips.utils.time.TimeParser;
import blizzard.development.vips.vips.enums.VipEnum;
import blizzard.development.vips.vips.factory.VipsFactory;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class VipsAdapter implements VipsFactory {
    @Getter
    private static final VipsAdapter instance = new VipsAdapter();

    private final VipDAO vipDAO = new VipDAO();

    @Override
    public void giveVip(Player player, String date, String vipId, String vipName, long duration) {
        VipData vipData = new VipData(vipId, player.getUniqueId().toString(), player.getName(), date, vipName, duration);

        try {
            vipDAO.createVipData(vipData);
        } catch(Exception err) {
            err.printStackTrace();
        }

        VipCacheManager.getInstance().cacheVipData(vipId, vipData);

        Bukkit.dispatchCommand(
                Bukkit.getConsoleSender(), "lp user " + player.getName() + " parent add " + vipName);
    }

    @Override
    public void removeVip(Player player, String vipName) {
        YamlConfiguration messagesConfig = PluginImpl.getInstance().Messages.getConfig();

        Optional<VipData> vipToRemove = VipCacheManager.getInstance().vipCache.values().stream()
                .filter(vip -> vip.getUuid().equals(player.getUniqueId().toString())
                        && vip.getVipName().equalsIgnoreCase(vipName))
                .findFirst();

        if (vipToRemove.isPresent()) {
            VipCacheManager.getInstance().removeVipData(vipToRemove.get().getVipId());
            player.sendMessage(messagesConfig.getString("commands.removeVip.vipRemoved")
                    .replace("{vipName}", vipName));
        } else {
            player.sendMessage(messagesConfig.getString("commands.removeVip.vipNotFound")
                    .replace("{vipName}", vipName));
        }
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

    public boolean playerHasVip(String uuid, String vipName) {
        return VipCacheManager.getInstance().vipCache.values().stream()
                .anyMatch(vip -> vip.getUuid().equals(uuid) && vip.getVipName().equalsIgnoreCase(vipName));
    }

    public List<VipData> getPlayerVips(String uuid) {
        return VipCacheManager.getInstance().vipCache.values().stream()
                .filter(vip -> vip.getUuid().equals(uuid))
                .collect(Collectors.toList());
    }

    public boolean isVipValid(String vipName) {
        return Arrays.stream(VipEnum.values())
                .anyMatch(vipEnum -> vipEnum.getName().equalsIgnoreCase(vipName));
    }

    public String getFormatedDate() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy 'às' HH:mm");

        return now.format(formatter);
    }

    public long getTimeParsed(String durationInput) {
        long duration = 0;

        try {
            duration = TimeParser.parseTime(durationInput);
            return duration;
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }

        return duration;
    }

    public void sendVipMessage(String playerName, String vipName) {
        YamlConfiguration messagesConfig = PluginImpl.getInstance().Messages.getConfig();

        String title = messagesConfig.getString("commands.giveVip.vip.title");
        String subtitle = messagesConfig.getString("commands.giveVip.vip.subtitle")
                .replace("{playerName}", playerName)
                .replace("{vipName}", vipName.toUpperCase());

        for (Player player : Bukkit.getOnlinePlayers()) {
            player.sendTitle(title, subtitle);
            player.playSound(player.getLocation(), Sound.ENTITY_LIGHTNING_BOLT_THUNDER, 1, 1);
        }
    }
}
