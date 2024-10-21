package blizzard.development.currencies.currencies;

import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;

public class CoinsCurrency {
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

    public boolean hasEnough(Player player, double amount) {
        return economy != null && economy.getBalance(player) > amount;
    }

    public boolean withdraw(Player player, double amount) {
        if (economy == null) return false;
        economy.withdrawPlayer(player, amount);
        return true;
    }

    public boolean deposit(Player player, double amount) {
        if (economy == null) return false;
        economy.depositPlayer(player, amount);
        return true;
    }

    public double getBalance(Player player) {
        if (economy == null) return 0.0;
        return economy.getBalance(player);
    }
}
