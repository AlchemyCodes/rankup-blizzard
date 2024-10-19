package blizzard.development.bosses.utils;

import co.aikar.commands.Locales;
import co.aikar.commands.PaperCommandManager;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;

public class PluginImpl {
    public final Plugin plugin;
    private static PluginImpl instance;
    private static PluginManager pluginManager;
    private static PaperCommandManager commandManager;

    public PluginImpl(Plugin plugin) {
        this.plugin = plugin;
        instance = this;
        pluginManager = Bukkit.getPluginManager();
        commandManager = new PaperCommandManager(plugin);
        commandManager.getLocales().setDefaultLocale(Locales.PORTUGUESE);

    }

    public void onLoad() {
        registerDatabase();
        registerListeners();
        registerCommands();
    }



    public void onUnload() {
    }

    public void registerDatabase() {
    }

    private void registerListeners() {
    }

    private void registerCommands() {
    }

    public static PluginImpl getInstance() {
        return instance;
    }
}
