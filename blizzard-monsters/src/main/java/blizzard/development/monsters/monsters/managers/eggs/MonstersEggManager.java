package blizzard.development.monsters.monsters.managers.eggs;

import blizzard.development.monsters.builders.ItemBuilder;
import blizzard.development.monsters.monsters.managers.monsters.MonstersGeneralManager;
import blizzard.development.monsters.monsters.managers.monsters.rewards.MonstersRewardManager;
import blizzard.development.monsters.utils.NumberFormatter;
import blizzard.development.monsters.utils.PluginImpl;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class MonstersEggManager {
    private static MonstersEggManager instance;

    private final PluginImpl plugin = PluginImpl.getInstance();

    public void giveEgg(Player player, String monster, Integer stack) {
        MonstersGeneralManager monstersManager = MonstersGeneralManager.getInstance();

        String eggType = "blizzard.monsters.monster";
        Set<String> monsters = monstersManager.getAll();

        if (monsters.contains(monster)) {
            List<String> lore = new ArrayList<>(getLore(monster));

            lore.replaceAll(line -> line
                    .replace("{life}", NumberFormatter.getInstance().formatNumber(monstersManager.getLife(monster)))
                    .replace("{damage}", NumberFormatter.getInstance().formatNumber(monstersManager.getAttackDamage(monster)))
            );

            List<String> rewards = monstersManager.getRewards(monster);
            List<String> formattedRewards = rewards.stream()
                    .map(reward -> "  " + MonstersRewardManager.getInstance().getDisplayName(reward))
                    .toList();

            List<String> updatedLore = new ArrayList<>();
            for (String line : lore) {
                if (line.contains("{rewards}")) {
                    updatedLore.addAll(formattedRewards);
                } else {
                    updatedLore.add(line);
                }
            }

            String displayName = getDisplayName(monster);
            Material material = Material.getMaterial(getMaterial(monster));
            if (material != null) {
                ItemStack item = new ItemBuilder(material)
                        .setDisplayName(displayName)
                        .setLore(updatedLore)
                        .addItemFlags(ItemFlag.HIDE_ITEM_SPECIFICS)
                        .addPersistentData(plugin.plugin, eggType, monster)
                        .setAmount(stack)
                        .build(false);

                player.getInventory().addItem(item);
            }
        }
    }


    public String getMaterial(String monsterName) {
        return plugin.Monsters.getConfig().getString("monsters." + monsterName + ".egg.material");
    }

    public String getDisplayName(String monsterName) {
        return plugin.Monsters.getConfig().getString("monsters." + monsterName + ".egg.display-name");
    }

    public List<String> getLore(String monsterName) {
        return plugin.Monsters.getConfig().getStringList("monsters." + monsterName + ".egg.lore");
    }

    public static MonstersEggManager getInstance() {
        if (instance == null) instance = new MonstersEggManager();
        return instance;
    }
}
