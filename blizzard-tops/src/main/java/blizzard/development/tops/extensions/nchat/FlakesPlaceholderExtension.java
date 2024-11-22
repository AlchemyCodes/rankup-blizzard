package blizzard.development.tops.extensions.nchat;

import blizzard.development.currencies.api.CurrenciesAPI;
import blizzard.development.currencies.enums.Currencies;
import com.nickuc.chat.api.translator.GlobalTag;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.UUID;

public class FlakesPlaceholderExtension implements GlobalTag {
    private final Plugin plugin;

    public FlakesPlaceholderExtension(Plugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public String replaceTag(Player player, String tag) {
        final CurrenciesAPI api = CurrenciesAPI.getInstance();

        Currencies currency = Currencies.FLAKES;

        UUID topPlayerUUID = UUID.fromString(api.getTopPlayers(currency).get(0).getUuid());
        if (player.getUniqueId().equals(topPlayerUUID)) {
            return currency.getEmoji();
        }

        return "";
    }

    @Override
    public Plugin getOwner() {
        return this.plugin;
    }
}
