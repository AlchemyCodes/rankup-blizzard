package blizzard.development.fishing;

import blizzard.development.fishing.utils.PluginImpl;
import lombok.Getter;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
    public PluginImpl pluginImpl;
    @Getter
    public static Main instance;

    public void onEnable() {
        instance = this;
        this.pluginImpl = new PluginImpl((Plugin)this);
        this.pluginImpl.onLoad();
    }

    public void onDisable() {
        this.pluginImpl.onUnload();
    }
}
