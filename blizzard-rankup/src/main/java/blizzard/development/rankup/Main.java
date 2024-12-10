package blizzard.development.rankup;

import blizzard.development.rankup.utils.PluginImpl;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
    public PluginImpl pluginImpl;
    public static Main instance;

    public void onEnable() {
        instance = this;
        this.pluginImpl = new PluginImpl(this);
        this.pluginImpl.onLoad();
    }

    public void onDisable() {
        this.pluginImpl.onUnload();
    }

    public static Main getInstance() {
        return instance;
    }
}
