package blizzard.development.currencies.extensions;

import blizzard.development.currencies.database.dao.PlayersDAO;
import blizzard.development.currencies.database.storage.PlayersData;
import blizzard.development.currencies.enums.Currencies;
import com.nickuc.chat.api.translator.GlobalTag;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.List;

public class NChatExtension implements GlobalTag {
    private final Plugin plugin;

    PlayersDAO dao = new PlayersDAO();

    public NChatExtension(Plugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public String replaceTag(Player player, String tag) {
        List<PlayersData> topPlayers = dao.getTopPlayer(Currencies.FLAKES);
        if (!topPlayers.isEmpty() && topPlayers.get(0).getNickname().equals(player.getName())) {
            return "[❄]";
        }
        return "";
    }

    @Override
    public Plugin getOwner() {
        return this.plugin;
    }
}
