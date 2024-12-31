package blizzard.development.monsters.monsters.handlers.eggs;

import blizzard.development.monsters.builders.ItemBuilder;
import blizzard.development.monsters.monsters.handlers.monsters.MonstersHandler;
import blizzard.development.monsters.utils.NumberFormatter;
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

    public void giveEgg(Player player, String monster, Integer stack) {
        MonstersHandler monstersHandler = MonstersHandler.getInstance();

        String eggType = "blizzard.monsters.monster";

        Set<String> monsters = monstersHandler.getAll();

        ItemStack item;

        if (monsters.contains(monster)) {

            List<String> lore = getLore(monster);

            lore.replaceAll(line -> line
                    .replace("{life}", NumberFormatter.getInstance().formatNumber(monstersHandler.getLife(monster)))
                    .replace("{damage}", NumberFormatter.getInstance().formatNumber(monstersHandler.getAttackDamage(monster)))
            );

            String displayName = getDisplayName(monster);

            Material material = Material.getMaterial(getMaterial(monster));
            if (material != null) {

                item = new ItemBuilder(material)
                        .setDisplayName(displayName)
                        .setLore(lore)
                        .addItemFlags(ItemFlag.HIDE_ITEM_SPECIFICS)
                        .addPersistentData(plugin.plugin, eggType, monster)
                        .setAmount(stack)
                        .build();

                player.getInventory().addItem(item);
            } else {
                item = new ItemBuilder(getMaterial(monster))
                        .setDisplayName(displayName)
                        .setLore(lore)
                        .addItemFlags(ItemFlag.HIDE_ITEM_SPECIFICS)
                        .addPersistentData(plugin.plugin, eggType, monster)
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
