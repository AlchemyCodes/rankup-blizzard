package blizzard.development.spawners.handlers.slaughterhouse;

import blizzard.development.spawners.builders.ItemBuilder;
import blizzard.development.spawners.utils.PluginImpl;
import blizzard.development.spawners.utils.TimeConverter;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import java.util.List;
import java.util.Objects;
import java.util.Set;

public class SlaughterhouseHandler {
    private static SlaughterhouseHandler instance;

    final Plugin plugin = PluginImpl.getInstance().plugin;

    public boolean giveSlaughterhouse(Player player, Integer level, Integer amount) {
        final Integer tier = getLevel(level);

        final String key = "blizzard.spawners.slaughterhouse";
        final Set<String> slaughterhousesKeys = Objects.requireNonNull(PluginImpl.getInstance().Slaughterhouses.getConfig().getConfigurationSection("slaughterhouses")).getKeys(false);

        ItemStack item;

        if (slaughterhousesKeys.contains(tier.toString())) {

            List<String> lore = getLore(tier);
            lore.replaceAll(line -> line
                    .replace("{cooldown}", TimeConverter.convertSecondsToTimeFormat(getKillCooldown(tier)))
                    .replace("{area}", String.valueOf(getKillArea(tier)))
            );

            item = new ItemBuilder(getItem(tier))
                    .setDisplayName(getDisplayName(tier))
                    .setLore(lore)
                    .addItemFlags(ItemFlag.HIDE_ITEM_SPECIFICS)
                    .addPersistentData(plugin, key, String.valueOf(tier))
                    .setAmount(amount)
                    .build();

            player.getInventory().addItem(item);
            return true;
        }
        return false;
    }

    public int getLevel(int slaughterhouseId) {
        return PluginImpl.getInstance().Slaughterhouses.getConfig().getInt("slaughterhouses." + slaughterhouseId + ".level", 0);
    }

    public String getItem(int slaughterhouseId) {
        return PluginImpl.getInstance().Slaughterhouses.getConfig().getString("slaughterhouses." + slaughterhouseId + ".item");
    }

    public String getDisplayName(int slaughterhouseId) {
        return PluginImpl.getInstance().Slaughterhouses.getConfig().getString("slaughterhouses." + slaughterhouseId + ".display-name");
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
