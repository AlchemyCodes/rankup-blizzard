package blizzard.development.currencies.api;

import blizzard.development.currencies.currencies.*;
import blizzard.development.currencies.currencies.limits.SpawnersLimitCurrency;
import blizzard.development.currencies.database.storage.PlayersData;
import blizzard.development.currencies.enums.Currencies;
import blizzard.development.currencies.utils.NumberFormat;
import org.bukkit.entity.Player;

import java.util.List;

public class CurrenciesAPI {
    private static CurrenciesAPI instance;

    NumberFormat format = NumberFormat.getInstance();

    // API Variables
    CoinsCurrency coinsCurrency = CoinsCurrency.getInstance();
    String coins = Currencies.COINS.getName();
    SoulsCurrency soulsCurrency = SoulsCurrency.getInstance();
    String souls = Currencies.SOULS.getName();
    FlakesCurrency flakesCurrency = FlakesCurrency.getInstance();
    String flakes = Currencies.FLAKES.getName();
    FossilsCurrency fossilsCurrency = FossilsCurrency.getInstance();
    String fossils = Currencies.FOSSILS.getName();
    SpawnersLimitCurrency spawnersLimitCurrency = SpawnersLimitCurrency.getInstance();
    String spawnersLimit = Currencies.SPAWNERSLIMIT.getName();
    BlocksCurrency blocksCurrency = BlocksCurrency.getInstance();
    String blocks = Currencies.BLOCKS.getName();

    // API Methods
    public Double getBalance(Player player, Currencies currency) {
        if (currency.getName().equals(coins)) {
            return coinsCurrency.getBalance(player);
        } else if (currency.getName().equals(souls)) {
            return soulsCurrency.getBalance(player);
        } else if (currency.getName().equals(flakes)) {
            return flakesCurrency.getBalance(player);
        } else if (currency.getName().equals(fossils)) {
            return fossilsCurrency.getBalance(player);
        } else if (currency.getName().equals(spawnersLimit)) {
            return spawnersLimitCurrency.getBalance(player);
        } else if (currency.getName().equals(blocks)) {
            return blocksCurrency.getBalance(player);
        }
        return 0.0;
    }

    public String getFormattedBalance(Player player, Currencies currency) {
        if (currency.getName().equals(coins)) {
            return format.formatNumber(coinsCurrency.getBalance(player));
        } else if (currency.getName().equals(souls)) {
            return format.formatNumber(soulsCurrency.getBalance(player));
        } else if (currency.getName().equals(flakes)) {
            return format.formatNumber(flakesCurrency.getBalance(player));
        } else if (currency.getName().equals(fossils)) {
            return format.formatNumber(fossilsCurrency.getBalance(player));
        } else if (currency.getName().equals(spawnersLimit)) {
            return format.formatNumber(spawnersLimitCurrency.getBalance(player));
        } else if (currency.getName().equals(blocks)) {
            return format.formatNumber(blocksCurrency.getBalance(player));
        }
        return "0";
    }

    public boolean setBalance(Player player, Currencies currency, double balance) {
        if (balance <= 0) {
            return false;
        }
        if (currency.getName().equals(coins)) {
            coinsCurrency.setBalance(player, balance);
        } else if (currency.getName().equals(souls)) {
            soulsCurrency.setBalance(player, balance);
        } else if (currency.getName().equals(flakes)) {
            flakesCurrency.setBalance(player, balance);
        } else if (currency.getName().equals(fossils)) {
            fossilsCurrency.setBalance(player, balance);
        } else if (currency.getName().equals(spawnersLimit)) {
            spawnersLimitCurrency.setBalance(player, balance);
        } else if (currency.getName().equals(blocks)) {
            blocksCurrency.setBalance(player, balance);
        }
        return true;
    }

    public boolean addBalance(Player player, Currencies currency, double balance) {
        if (balance <= 0) {
            return false;
        }
        if (currency.getName().equals(coins)) {
            coinsCurrency.addBalance(player, balance);
        } else if (currency.getName().equals(souls)) {
            soulsCurrency.addBalance(player, balance);
        } else if (currency.getName().equals(flakes)) {
            flakesCurrency.addBalance(player, balance);
        } else if (currency.getName().equals(fossils)) {
            fossilsCurrency.addBalance(player, balance);
        } else if (currency.getName().equals(spawnersLimit)) {
            spawnersLimitCurrency.addBalance(player, balance);
        } else if (currency.getName().equals(blocks)) {
            blocksCurrency.addBalance(player, balance);
        }
        return true;
    }

    public boolean removeBalance(Player player, Currencies currency, double balance) {
        if (balance <= 0) {
            return false;
        }
        if (currency.getName().equals(coins)) {
            coinsCurrency.removeBalance(player, balance);
        } else if (currency.getName().equals(souls)) {
            soulsCurrency.removeBalance(player, balance);
        } else if (currency.getName().equals(flakes)) {
            flakesCurrency.removeBalance(player, balance);
        } else if (currency.getName().equals(fossils)) {
            fossilsCurrency.removeBalance(player, balance);
        } else if (currency.getName().equals(spawnersLimit)) {
            spawnersLimitCurrency.removeBalance(player, balance);
        } else if (currency.getName().equals(blocks)) {
            blocksCurrency.removeBalance(player, balance);
        }
        return true;
    }

    public boolean transferBalance(Player player, Player target, Currencies currency, double balance) {
        if (balance <= 0) {
            return false;
        }
        if (currency.getName().equals(souls)) {
            soulsCurrency.removeBalance(player, balance);
            soulsCurrency.addBalance(target, balance);
        } else if (currency.getName().equals(flakes)) {
            flakesCurrency.removeBalance(player, balance);
            flakesCurrency.addBalance(target, balance);
        } else if (currency.getName().equals(fossils)) {
            fossilsCurrency.removeBalance(player, balance);
            fossilsCurrency.addBalance(target, balance);
        } else if (currency.getName().equals(spawnersLimit)) {
            spawnersLimitCurrency.removeBalance(player, balance);
            spawnersLimitCurrency.addBalance(target, balance);
        } else if (currency.getName().equals(blocks)) {
            blocksCurrency.removeBalance(player, balance);
            blocksCurrency.addBalance(target, balance);
        }
        return true;
    }

    public List<PlayersData> getTopPlayers(Currencies currency) {
        if (currency.getName().equals(souls)) {
            return soulsCurrency.getTopPlayers();
        } else if (currency.getName().equals(flakes)) {
            return flakesCurrency.getTopPlayers();
        } else if (currency.getName().equals(fossils)) {
            return fossilsCurrency.getTopPlayers();
        } else if (currency.getName().equals(spawnersLimit)) {
            return spawnersLimitCurrency.getTopPlayers();
        } else if (currency.getName().equals(blocks)) {
            return blocksCurrency.getTopPlayers();
        }
        return null;
    }

    public static CurrenciesAPI getInstance() {
        if (instance == null) instance = new CurrenciesAPI();
        return instance;
    }
}
