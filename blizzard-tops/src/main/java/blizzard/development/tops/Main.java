package blizzard.development.tops;

import blizzard.development.tops.utils.PluginImpl;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
    public PluginImpl pluginImpl;

    @Override
    public void onEnable() {
        pluginImpl = new PluginImpl(this);
        pluginImpl.onEnable();
    }

    @Override
    public void onDisable() {
        pluginImpl = new PluginImpl(this);
        pluginImpl.onDisable();
    }
}