package blizzard.development.tops.tasks;

import blizzard.development.currencies.api.CurrenciesAPI;
import blizzard.development.currencies.enums.Currencies;
import blizzard.development.tops.extensions.LPermsExtension;
import blizzard.development.tops.utils.NumberFormat;
import blizzard.development.tops.utils.items.TextAPI;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class TopsMessageTask extends BukkitRunnable {

    @Override
    public void run() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            final CurrenciesAPI api = CurrenciesAPI.getInstance();

            // Coins

            // Flakes

            String flakesPlayer = api.getTopPlayers(Currencies.FLAKES).get(0).getNickname();
            String flakesPlayerPrefix = LPermsExtension.getPlayerPrefix(UUID.fromString(api.getTopPlayers(Currencies.FLAKES).get(0).getUuid()));
            String flakesAmount = NumberFormat.getInstance().formatNumber(api.getTopPlayers(Currencies.FLAKES).get(0).getFlakes());

            // Fossils

            String fossilsPlayer = api.getTopPlayers(Currencies.FOSSILS).get(0).getNickname();
            String fossilsPlayerPrefix = LPermsExtension.getPlayerPrefix(UUID.fromString(api.getTopPlayers(Currencies.FOSSILS).get(0).getUuid()));
            String fossilsAmount = NumberFormat.getInstance().formatNumber(api.getTopPlayers(Currencies.FOSSILS).get(0).getFossils());

            // Souls

            String soulsPlayer = api.getTopPlayers(Currencies.SOULS).get(0).getNickname();
            String soulsPlayerPrefix = LPermsExtension.getPlayerPrefix(UUID.fromString(api.getTopPlayers(Currencies.SOULS).get(0).getUuid()));
            String soulsAmount = NumberFormat.getInstance().formatNumber(api.getTopPlayers(Currencies.SOULS).get(0).getSouls());

            // Fish

            List<String> messages = Arrays.asList(
                    "",
                    " §d§lWOW! §dConfira os jogadores que mais se destacam.",
                    "",
                    //"   §2❒ {prefix} {nickname}: §2§l$§a{currencies-money}",
                    "   §b❒ " + flakesPlayerPrefix + flakesPlayer + "§b: §b§l❆§b" + flakesAmount,
                    "   §f❒ " + fossilsPlayerPrefix + fossilsPlayer + "§f: §f§l🦴§f" + fossilsAmount,
                    "   §d❒ " + soulsPlayerPrefix + soulsPlayer + "§d: §d§l👻§d" + soulsAmount,
                    "   §b❒ " + soulsPlayerPrefix + soulsPlayer + "§b: §b§l🐟§b" + soulsAmount,
                    "",
                    "  §fNão desista, continue jogando",
                    "  §fpara conseguir se §ldestacar§f.",
                    ""
            );

            player.sendActionBar(TextAPI.parse("§d§lEI! §dUm novo anúncio, confira agora o chat."));
            messages.forEach(player::sendMessage);
        }
    }
}
