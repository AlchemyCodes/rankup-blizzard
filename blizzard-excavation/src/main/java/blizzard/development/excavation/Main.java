package blizzard.development.excavation;

import blizzard.development.excavation.utils.PluginImpl;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

    public static Main instance;

    @Override
    public void onEnable() {
        instance = this;

        PluginImpl pluginImpl = new PluginImpl(this);
        pluginImpl.onLoad();
    }

    public static Main getInstance() {
        return instance;
    }
}