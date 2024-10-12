package blizzard.development.clans;

import org.bukkit.plugin.java.JavaPlugin;
import blizzard.development.clans.utils.PluginImpl;

public class Main extends JavaPlugin {
    public PluginImpl pluginImpl;

    @Override
    public void onEnable() {
        pluginImpl = new PluginImpl(this);
        pluginImpl.onLoad();
    }

    public void onDisable() {
        pluginImpl.onUnload();
    }
}