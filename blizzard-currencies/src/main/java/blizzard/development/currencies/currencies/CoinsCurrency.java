package blizzard.development.currencies.currencies;

import blizzard.development.currencies.database.cache.PlayersCacheManager;
import blizzard.development.currencies.database.storage.PlayersData;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;

import java.util.List;
import java.util.stream.Collectors;

public class CoinsCurrency {
    private static CoinsCurrency instance;
    private static Economy economy;

    public boolean setupEconomy() {
        if (Bukkit.getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
        RegisteredServiceProvider<Economy> rsp = Bukkit.getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        economy = rsp.getProvider();
        return economy != null;
    }

    public Double getBalance(Player player) {
        if (economy == null) return 0.0;
        return economy.getBalance(player);
    }

    public void setBalance(Player player, Double balance) {
        if (economy == null) return;
        double bal = economy.getBalance(player);
        economy.withdrawPlayer(player, bal);
        economy.depositPlayer(player, balance);
    }

    public void addBalance(Player player, Double balance) {
        if (economy == null) return;
        economy.depositPlayer(player, balance);
    }

    public void removeBalance(Player player, Double balance) {
        if (economy == null) return;
        economy.withdrawPlayer(player, balance);
    }

    public static CoinsCurrency getInstance() {
        if (instance == null) instance = new CoinsCurrency();
        return instance;
    }
}
