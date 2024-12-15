package blizzard.development.events;

import blizzard.development.events.utils.PluginImpl;
import lombok.Getter;
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
//        this.pluginImpl.onUnload();
    }
}
