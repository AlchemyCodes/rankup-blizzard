package blizzard.development.clans.managers;

import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;

public class EconomyManager {
    private static Economy economy;

    public static boolean setupEconomy() {
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

    public static boolean hasEnough(Player player, double amount) {
        return economy != null && economy.getBalance(player) > amount;
    }

    public static boolean withdraw(Player player, double amount) {
        if (economy == null) return false;
        economy.withdrawPlayer(player, amount);
        return true;
    }

    public static boolean deposit(Player player, double amount) {
        if (economy == null) return false;
        economy.depositPlayer(player, amount);
        return true;
    }

    public static double getBalance(Player player) {
        if (economy == null) return 0.0;
        return economy.getBalance(player);
    }
}
