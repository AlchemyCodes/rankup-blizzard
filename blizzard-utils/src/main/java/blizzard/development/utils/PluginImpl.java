package blizzard.development.utils;

import blizzard.development.listeners.GroundDropListener;
import blizzard.development.utils.config.ConfigUtils;
import co.aikar.commands.Locales;
import co.aikar.commands.PaperCommandManager;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;


public class PluginImpl {
    public final Plugin plugin;

    private static @Getter PluginImpl instance;

    public ConfigUtils Config;

    public PluginImpl(Plugin plugin) {
        this.plugin = plugin;
        instance = this;
        Config = new ConfigUtils((JavaPlugin) plugin, "config.yml");
    }

    public void onEnable() {
        Config.saveDefaultConfig();

        registerListeners();;
    }

    public void onDisable() {}

    private void registerListeners() {
        Bukkit.getPluginManager().registerEvents(new GroundDropListener(plugin), plugin);
    }
}