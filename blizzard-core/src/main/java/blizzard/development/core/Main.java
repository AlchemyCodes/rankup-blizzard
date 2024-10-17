package blizzard.development.core;


import blizzard.development.core.utils.PluginImpl;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
    public PluginImpl pluginImpl;
    public static Main instance;

    @Override
    public void onEnable() {
        instance = this;

        pluginImpl = new PluginImpl(this);
        pluginImpl.onLoad();
    }

    public void onDisable() {
        pluginImpl.onUnload();
    }

    public static Main getInstance() {
        return instance;
    }
}