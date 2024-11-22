package blizzard.development.tops.extensions;

import blizzard.development.currencies.api.CurrenciesAPI;
import blizzard.development.currencies.enums.Currencies;
import com.nickuc.chat.api.translator.GlobalTag;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.UUID;

public class NChatExtension implements GlobalTag {

    private final Plugin plugin;

    public NChatExtension(Plugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public String replaceTag(Player player, String tag) {
        final CurrenciesAPI api = CurrenciesAPI.getInstance();

        for (Currencies currency : Currencies.values()) {
            if (tag.equalsIgnoreCase("{tops_" + currency.getName() + "}")) {
                UUID topPlayerUUID = UUID.fromString(api.getTopPlayers(currency).get(0).getUuid());
                if (player.getUniqueId().equals(topPlayerUUID)) {
                    return currency.getEmoji();
                }
            }
        }
        return "";
    }

    @Override
    public Plugin getOwner() {
        return this.plugin;
    }
}
