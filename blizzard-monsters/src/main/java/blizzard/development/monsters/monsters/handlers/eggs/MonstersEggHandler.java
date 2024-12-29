package blizzard.development.monsters.monsters.handlers.eggs;

import blizzard.development.monsters.builders.ItemBuilder;
import blizzard.development.monsters.utils.PluginImpl;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.Set;

public class MonstersEggHandler {
    private static MonstersEggHandler instance;

    private final PluginImpl plugin = PluginImpl.getInstance();

    public void giveEgg(Player player, String monster, Integer amount, Integer stack) {
        MonstersHandler monstersHandler = MonstersHandler.getInstance();

        String eggType = "blizzard.monsters.monster";
        String eggAmount = "blizzard.monsters.monster-amount";

        Set<String> monsters = monstersHandler.getAll();

        ItemStack item;

        if (monsters.contains(monster)) {

            List<String> lore = getLore(monster);
            lore.forEach(line -> line.replace("{amount}", amount.toString()));

            String displayName = getDisplayName(monster).replace("{amount}", amount.toString());

            Material material = Material.getMaterial(getMaterial(monster));
            if (material != null) {

                item = new ItemBuilder(material)
                        .setDisplayName(displayName)
                        .setLore(lore)
                        .addItemFlags(ItemFlag.HIDE_ITEM_SPECIFICS)
                        .addPersistentData(plugin.plugin, eggType, monster)
                        .addPersistentData(plugin.plugin, eggAmount, amount)
                        .setAmount(stack)
                        .build();

                player.getInventory().addItem(item);
            } else {
                item = new ItemBuilder(getMaterial(monster))
                        .setDisplayName(displayName)
                        .setLore(lore)
                        .addItemFlags(ItemFlag.HIDE_ITEM_SPECIFICS)
                        .addPersistentData(plugin.plugin, eggType, monster)
                        .addPersistentData(plugin.plugin, eggAmount, amount)
                        .setAmount(stack)
                        .build();

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

    public static MonstersEggHandler getInstance() {
        if (instance == null) instance = new MonstersEggHandler();
        return instance;
    }
}
