package blizzard.development.slimeworlds;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

    public static Main instance;

    @Override
    public void onEnable() {
        instance = this;
        saveDefaultConfig();
        Bukkit.getConsoleSender().sendMessage("§a§l[SlimeWorlds] §aAPI em funcionamento.");
    }

    public static Main getInstance() {
        return instance;
    }
}
