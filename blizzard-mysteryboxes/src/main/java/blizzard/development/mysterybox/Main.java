package blizzard.development.mysterybox;

import blizzard.development.mysterybox.utils.PluginImpl;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin {

    private PluginImpl pluginImpl;

    @Override
    public void onEnable() {
        pluginImpl = new PluginImpl(this);
        pluginImpl.onEnable();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
