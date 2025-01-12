package blizzard.development.mail.utils;

import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;

import java.util.Collections;
import java.util.List;
import java.util.Set;

public class MailUtils {
    private static MailUtils instance;

    private final YamlConfiguration config = PluginImpl.getInstance().Config.getConfig();
    private final Set<String> items = config.getConfigurationSection("items").getKeys(false);

    public boolean itemExists(String itemName) {
        for (String item : items) {
            if (item.equals(itemName)) {
                return true;
            }
        }
        return false;
    }

    public String getItemDisplayName(String itemName) {
        String itemPath = "items." + itemName;
        return config.getString(itemPath + ".displayName");
    }

    public List<String> getItemLore(String itemName) {
        String itemPath = "items." + itemName;
        return config.getStringList(itemPath + ".lore");

    }

    public Material getItemMaterial(String itemName) {
        String itemPath = "items." + itemName;
        return Material.getMaterial(config.getString(itemPath + ".material"));
    }

    public String getItemPdc(String itemName) {
        String itemPath = "items." + itemName;
        return itemPath + ".pdc";
    }

    public String getItemNameByMaterial(Material material) {
        for (String item : items) {
            if (getItemMaterial(item) == material) {
                return item;
            }
        }
        return null;
    }

    public static MailUtils getInstance() {
        if (instance == null) {
            instance = new MailUtils();
        }
        return instance;
    }
}
