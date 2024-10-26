package blizzard.development.spawners;

import blizzard.development.spawners.utils.PluginImpl;
import org.bukkit.plugin.java.JavaPlugin;

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