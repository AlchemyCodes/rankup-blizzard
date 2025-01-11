package blizzard.development.visuals;

import blizzard.development.visuals.utils.PluginImpl;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin {

    public static Main instance;
    private PluginImpl pluginImpl;

    @Override
    public void onEnable() {
        instance = this;

        pluginImpl = new PluginImpl(this);
        pluginImpl.onEnable();
    }

    @Override
    public void onDisable() {

    }

    public static Main getInstance() {
        return instance;
    }
}
