package blizzard.development.visuals.utils;

import blizzard.development.visuals.commands.CommandRegistry;
import blizzard.development.visuals.utils.config.ConfigUtils;
import co.aikar.commands.Locales;
import co.aikar.commands.PaperCommandManager;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public class PluginImpl {

    public final Plugin plugin;
    private static PluginImpl instance;
    private static PaperCommandManager commandManager;
    public ConfigUtils Config;

    public PluginImpl(Plugin plugin) {
        this.plugin = plugin;
        instance = this;
        commandManager = new PaperCommandManager(plugin);
        commandManager.getLocales().setDefaultLocale(Locales.PORTUGUESE);
        Config = new ConfigUtils((JavaPlugin) plugin, "config.yml");
    }

    public void onEnable() {
        try {
            commandManager = new PaperCommandManager(plugin);
            commandManager.getLocales().setDefaultLocale(Locales.PORTUGUESE);
        } catch (IllegalStateException e) {
            throw new RuntimeException("Erro ao inicializar PaperCommandManager", e);
        }

        registerCommands();

        Config.saveDefaultConfig();
    }

    private void registerListeners() {

    }

    private void registerCommands() {
        CommandRegistry commandRegistry = new CommandRegistry();
        commandRegistry.register();
    }

    public static PluginImpl getInstance() {
        return instance;
    }
}
