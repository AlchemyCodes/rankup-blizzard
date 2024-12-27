package blizzard.development.events.utils;

import blizzard.development.events.commands.CommandRegistry;
import blizzard.development.events.listeners.ListenerRegistry;
import co.aikar.commands.Locales;
import co.aikar.commands.PaperCommandManager;
import lombok.Getter;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public class PluginImpl {
    public final Plugin plugin;

    @Getter
    private static PluginImpl instance;

    public ConfigUtils Config;
    public ConfigUtils Messages;
    public ConfigUtils Locations;

    public PluginImpl(Plugin plugin) {
        this.plugin = plugin;
        instance = this;

        PaperCommandManager commandManager = new PaperCommandManager(plugin);
        commandManager.getLocales().setDefaultLocale(Locales.PORTUGUESE);

        this.Config = new ConfigUtils((JavaPlugin)plugin, "config.yml");
        this.Messages = new ConfigUtils((JavaPlugin)plugin, "messages.yml");
        this.Locations = new ConfigUtils((JavaPlugin)plugin, "locations.yml");
    }

    public void onLoad() {
        loadConfigs();
        registerCommands();
        registerListeners();
    }

    public void loadConfigs() {
        if (!this.Config.existsConfig()) {
            this.Config.saveDefaultConfig();
        }
        if (!this.Messages.existsConfig()) {
            this.Messages.saveDefaultConfig();
        }
        if (!this.Locations.existsConfig()) {
            this.Locations.saveDefaultConfig();
        }

        this.Config.reloadConfig();
    }

    private void registerListeners() {
        ListenerRegistry listenerRegistry = new ListenerRegistry();
        listenerRegistry.register();
    }

    private void registerCommands() {
        CommandRegistry commandRegistry = new CommandRegistry();
        commandRegistry.register();
    }

    public File getDataFolder() {
        return this.plugin.getDataFolder();
    }
}
