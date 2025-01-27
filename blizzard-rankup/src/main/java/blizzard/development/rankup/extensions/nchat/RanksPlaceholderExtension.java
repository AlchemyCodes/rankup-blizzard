package blizzard.development.rankup.extensions.nchat;

import blizzard.development.rankup.database.cache.method.PlayersCacheMethod;
import blizzard.development.rankup.utils.PluginImpl;
import blizzard.development.rankup.utils.RanksUtils;
import com.nickuc.chat.api.translator.GlobalTag;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;


public class RanksPlaceholderExtension implements GlobalTag {
    private final Plugin plugin;

    public RanksPlaceholderExtension(Plugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public String replaceTag(Player player, String tag) {
        YamlConfiguration ranksConfig = PluginImpl.getInstance().Ranks.getConfig();
        String rank = PlayersCacheMethod.getInstance().getRank(player);

        return RanksUtils.getCurrentRankTag(ranksConfig, rank);
    }

    @Override
    public Plugin getOwner() {
        return this.plugin;
    }
}
