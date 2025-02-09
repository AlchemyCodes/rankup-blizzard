package blizzard.development.vips.vips.factory;

import org.bukkit.entity.Player;

public interface VipsFactory {
    void giveVip(Player player, String date, String vipId, String vipName, long duration);
    void removeVip(Player player, String vipName);
}
