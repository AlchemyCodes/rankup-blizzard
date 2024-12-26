package blizzard.development.vips;

import blizzard.development.vips.utils.PluginImpl;
import lombok.Getter;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
    public PluginImpl pluginImpl;
    @Getter
    public static Main instance;

    public void onEnable() {
        instance = this;
        this.pluginImpl = new PluginImpl(this);
        this.pluginImpl.onLoad();
    }

    public void onDisable() {
        this.pluginImpl.onUnload();
    }
}
