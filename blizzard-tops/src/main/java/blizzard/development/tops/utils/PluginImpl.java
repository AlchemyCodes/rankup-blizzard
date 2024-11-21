package blizzard.development.tops.utils;

import blizzard.development.tops.commands.CommandRegistry;
import blizzard.development.tops.utils.config.ConfigUtils;
import co.aikar.commands.Locales;
import co.aikar.commands.PaperCommandManager;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public class PluginImpl {
    public final Plugin plugin;
    private static PluginImpl instance;

    public ConfigUtils Config;

    public PluginImpl(Plugin plugin) {
        this.plugin = plugin;
        instance = this;
        Config = new ConfigUtils((JavaPlugin) plugin, "config.yml");
    }

    public void onEnable() {
        Config.saveDefaultConfig();

        PaperCommandManager commandManager = new PaperCommandManager(plugin);
        commandManager.getLocales().setDefaultLocale(Locales.PORTUGUESE);

        registerCommands();
    }

    public void onDisable() {}

    private void registerCommands() {
        CommandRegistry.getInstance().register();
    }

    public static PluginImpl getInstance() {
        return instance;
    }
}