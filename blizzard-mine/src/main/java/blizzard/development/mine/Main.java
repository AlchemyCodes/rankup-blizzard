package blizzard.development.mine;

import blizzard.development.mine.utils.PluginImpl;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin {
    private final PluginImpl pluginImpl = new PluginImpl(this);

    @Override
    public void onEnable() {
        pluginImpl.onEnable();
    }

    @Override
    public void onDisable() {
        pluginImpl.onDisable();
    }
}
