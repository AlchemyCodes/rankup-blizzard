package blizzard.development.crates;

import blizzard.development.crates.utils.PluginImpl;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

    public static Main instance;

    @Override
    public void onEnable() {
        instance = this;

        PluginImpl pluginImpl = new PluginImpl(this);
        pluginImpl.onLoad();
    }

    @Override
    public void onDisable() {

    }

    public static Main getInstance() {
        return instance;
    }
}