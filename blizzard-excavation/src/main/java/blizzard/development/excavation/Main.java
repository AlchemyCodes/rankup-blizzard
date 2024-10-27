package blizzard.development.excavation;

import blizzard.development.excavation.utils.PluginImpl;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

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
        if (pluginImpl != null) {
            pluginImpl.onDisable();
        }
    }

    public static Main getInstance() {
        return instance;
    }
}