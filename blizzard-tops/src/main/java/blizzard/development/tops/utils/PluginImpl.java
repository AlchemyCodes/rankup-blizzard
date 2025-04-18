package blizzard.development.tops.utils;

import blizzard.development.tops.commands.CommandRegistry;
import blizzard.development.tops.extensions.nchat.FlakesPlaceholderExtension;
import blizzard.development.tops.extensions.nchat.FossilsPlaceholderExtension;
import blizzard.development.tops.extensions.nchat.SoulsPlaceholderExtension;
import blizzard.development.tops.tasks.TopsMessageTask;
import blizzard.development.tops.utils.config.ConfigUtils;
import co.aikar.commands.Locales;
import co.aikar.commands.PaperCommandManager;
import com.nickuc.chat.api.nChatAPI;
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
        registerTasks();
        registerExtensions();
    }

    public void onDisable() {}

    private void registerCommands() {
        CommandRegistry.getInstance().register();
    }

    private void registerTasks() {
        new TopsMessageTask().runTaskTimerAsynchronously(plugin, 0, 20L * 600);
    }

    private void registerExtensions() {
        nChatAPI api = nChatAPI.getApi();
        if (api != null) {
            api.setGlobalTag("tops_flakes", new FlakesPlaceholderExtension(plugin));
            api.setGlobalTag("tops_fossils", new FossilsPlaceholderExtension(plugin));
            api.setGlobalTag("tops_souls", new SoulsPlaceholderExtension(plugin));
        }
    }

    public static PluginImpl getInstance() {
        return instance;
    }
}