package blizzard.development.farm;

import blizzard.development.farm.utils.PluginImpl;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin {

    @Override
    public void onEnable() {
        PluginImpl pluginImpl = new PluginImpl(this);
        pluginImpl.onEnable();
    }

    @Override
    public void onDisable() {
        PluginImpl pluginImpl = new PluginImpl(this);
        pluginImpl.onDisable();
    }
}
