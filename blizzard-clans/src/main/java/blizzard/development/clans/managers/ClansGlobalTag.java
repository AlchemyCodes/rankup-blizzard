package blizzard.development.clans.managers;

import blizzard.development.clans.database.storage.ClansData;
import blizzard.development.clans.methods.ClansMethods;
import com.nickuc.chat.api.translator.GlobalTag;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class ClansGlobalTag implements GlobalTag {

    private final Plugin plugin;

    public ClansGlobalTag(Plugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public String replaceTag(Player player, String tag) {
        String clanName = ClansMethods.getUserClan(player);
        if (clanName != null) {
            ClansData clanData = ClansMethods.getClan(clanName);
            if (clanData != null && clanData.getTag() != null) {
                return "[" + clanData.getTag() + "]";
            }
        }
        return "";
    }


    @Override
    public Plugin getOwner() {
        return this.plugin;
    }
}
