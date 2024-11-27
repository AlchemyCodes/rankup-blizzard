package blizzard.development.spawners.inventories.enchantments;

import blizzard.development.spawners.database.cache.getters.SpawnersCacheGetters;
import blizzard.development.spawners.database.cache.managers.SpawnersCacheManager;
import blizzard.development.spawners.database.cache.setters.SpawnersCacheSetters;
import blizzard.development.spawners.handlers.enchantments.EnchantmentsHandler;
import blizzard.development.spawners.handlers.enums.Enchantments;
import blizzard.development.spawners.inventories.enchantments.items.EnchantmentItems;
import blizzard.development.spawners.inventories.spawners.SpawnersInventory;
import blizzard.development.spawners.tasks.spawners.mobs.SpawnersMobsTaskManager;
import blizzard.development.spawners.utils.SpawnersUtils;
import blizzard.development.spawners.utils.items.TextAPI;
import com.github.stefvanschie.inventoryframework.gui.GuiItem;
import com.github.stefvanschie.inventoryframework.gui.type.ChestGui;
import com.github.stefvanschie.inventoryframework.pane.StaticPane;
import com.github.stefvanschie.inventoryframework.pane.util.Slot;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

public class EnchantmentsInventory {
    private static EnchantmentsInventory instance;

    private final EnchantmentItems items = EnchantmentItems.getInstance();

    public void open(Player player, String id) {
        ChestGui inventory = new ChestGui(3, "§8Encantamentos");
        StaticPane pane = new StaticPane(0, 0, 9, 3);

        GuiItem speedItem = new GuiItem(items.speed(player, id, Enchantments.SPEED), event -> {
            upgradeEnchantment(player, id, Enchantments.SPEED, 1);
            event.setCancelled(true);
        });

        GuiItem luckyItem = new GuiItem(items.lucky(player, id, Enchantments.LUCKY), event -> {
            upgradeEnchantment(player, id, Enchantments.LUCKY, 1);
            event.setCancelled(true);
        });

        GuiItem experienceItem = new GuiItem(items.experience(player, id, Enchantments.EXPERIENCE), event -> {
            upgradeEnchantment(player, id, Enchantments.EXPERIENCE, 1);
            event.setCancelled(true);
        });

        GuiItem backItem = new GuiItem(items.back(), event -> {
            SpawnersInventory.getInstance().open(player, id);
            event.setCancelled(true);
        });

        pane.addItem(speedItem, Slot.fromIndex(11));
        pane.addItem(luckyItem, Slot.fromIndex(13));
        pane.addItem(experienceItem, Slot.fromIndex(15));
        pane.addItem(backItem, Slot.fromIndex(18));

        inventory.addPane(pane);
        inventory.show(player);
    }

    public void upgradeEnchantment(Player player, String id, Enchantments enchantment, int level) {
        final SpawnersCacheManager manager = SpawnersCacheManager.getInstance();
        final SpawnersCacheGetters getters = SpawnersCacheGetters.getInstance();
        final SpawnersCacheSetters setters = SpawnersCacheSetters.getInstance();
        final EnchantmentsHandler handler = EnchantmentsHandler.getInstance();

        switch (enchantment) {
            case SPEED -> {
                if ((getters.getSpawnerSpeedLevel(id) - level) < handler.getMaxLevel(enchantment.getName())) {
                    upgradeUnsuccessfully(player);
                } else {
                    setters.addSpawnerSpeedLevel(id, (handler.getPerLevel(enchantment.getName()) * level));
                    upgradeSuccessfully(player, id, Enchantments.SPEED);
                    SpawnersMobsTaskManager.getInstance().startTask(manager.getSpawnerData(id));
                }
            }
            case LUCKY -> {
                if ((getters.getSpawnerLuckyLevel(id) + level) > handler.getMaxLevel(enchantment.getName())) {
                    upgradeUnsuccessfully(player);
                } else {
                    setters.addSpawnerLuckyLevel(id, (handler.getPerLevel(enchantment.getName()) * level));
                    upgradeSuccessfully(player, id, Enchantments.LUCKY);
                }
            }
            case EXPERIENCE -> {
                if ((getters.getSpawnerExperienceLevel(id) + level) > handler.getMaxLevel(enchantment.getName())) {
                    upgradeUnsuccessfully(player);
                } else {
                    setters.addSpawnerExperienceLevel(id, (handler.getPerLevel(enchantment.getName()) * level));
                    upgradeSuccessfully(player, id, Enchantments.EXPERIENCE);
                }
            }
        }
    }

    private void upgradeSuccessfully(Player player, String id, Enchantments enchantment) {
        player.sendActionBar(TextAPI.parse(
                "§a§lYAY! §aVocê melhorou o encantamento §7" + SpawnersUtils.getInstance().getEnchantmentName(enchantment) +"§a do seu spawner."
        ));
        player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_YES, 1.0f, 1.0f);
        open(player, id);
    }

    private void upgradeUnsuccessfully(Player player) {
        player.sendActionBar(TextAPI.parse("§c§lEI! §cEste encantamento já está em seu nível máximo."));
        player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 0.5f, 0.5f);
    }

    public static EnchantmentsInventory getInstance() {
        if (instance == null) instance = new EnchantmentsInventory();
        return instance;
    }
}
