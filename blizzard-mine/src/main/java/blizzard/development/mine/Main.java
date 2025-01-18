package blizzard.development.mine;

import blizzard.development.mine.utils.PluginImpl;
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
