package blizzard.development.spawners.handlers.slaughterhouse;

import blizzard.development.spawners.utils.PluginImpl;

import java.util.List;

public class SlaughterhouseHandler {
    private static SlaughterhouseHandler instance;

    public int getLevel(int slaughterhouseId) {
        return PluginImpl.getInstance().Slaughterhouses.getConfig().getInt("slaughterhouses." + slaughterhouseId + ".level", 0);
    }

    public String getItem(int slaughterhouseId) {
        return PluginImpl.getInstance().Slaughterhouses.getConfig().getString("slaughterhouses." + slaughterhouseId + ".item", "HAY_BLOCK");
    }

    public String getDisplayName(int slaughterhouseId) {
        return PluginImpl.getInstance().Slaughterhouses.getConfig().getString("slaughterhouses." + slaughterhouseId + ".display-name", "§cUnknown Slaughterhouse");
    }

    public List<String> getLore(int slaughterhouseId) {
        return PluginImpl.getInstance().Slaughterhouses.getConfig().getStringList("slaughterhouses." + slaughterhouseId + ".lore");
    }

    public int getKillCooldown(int slaughterhouseId) {
        return PluginImpl.getInstance().Slaughterhouses.getConfig().getInt("slaughterhouses." + slaughterhouseId + ".kill-cooldown", 60);
    }

    public int getKillArea(int slaughterhouseId) {
        return PluginImpl.getInstance().Slaughterhouses.getConfig().getInt("slaughterhouses." + slaughterhouseId + ".kill-area", 5);
    }

    public boolean isSlaughterhouseReleased(int slaughterhouseId) {
        return PluginImpl.getInstance().Slaughterhouses.getConfig().contains("slaughterhouses." + slaughterhouseId + ".released");
    }

    public boolean isSlaughterhouseValid(int slaughterhouseId) {
        return PluginImpl.getInstance().Slaughterhouses.getConfig().contains("slaughterhouses." + slaughterhouseId);
    }

    public static SlaughterhouseHandler getInstance() {
        if (instance == null) instance = new SlaughterhouseHandler();
        return instance;
    }
}
