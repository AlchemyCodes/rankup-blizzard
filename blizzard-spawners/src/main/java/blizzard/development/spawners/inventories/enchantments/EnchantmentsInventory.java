package blizzard.development.spawners.inventories.enchantments;

import blizzard.development.spawners.database.cache.getters.SpawnersCacheGetters;
import blizzard.development.spawners.database.cache.managers.SpawnersCacheManager;
import blizzard.development.spawners.database.cache.setters.SpawnersCacheSetters;
import blizzard.development.spawners.handlers.enchantments.EnchantmentsHandler;
import blizzard.development.spawners.inventories.enchantments.items.EnchantmentItems;
import blizzard.development.spawners.inventories.spawners.SpawnersInventory;
import blizzard.development.spawners.tasks.spawners.mobs.SpawnersMobsTaskManager;
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

        GuiItem speedItem = new GuiItem(items.speed(player, id, "speed"), event -> {
            upgradeEnchantment(player, id, "speed", 1);
            event.setCancelled(true);
        });

        GuiItem luckyItem = new GuiItem(items.lucky(player, id, "lucky"), event -> {
            upgradeEnchantment(player, id, "lucky", 1);
            event.setCancelled(true);
        });

        GuiItem experienceItem = new GuiItem(items.experience(player, id, "experience"), event -> {
            upgradeEnchantment(player, id, "experience", 1);
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

    public void upgradeEnchantment(Player player, String id, String enchantment, int level) {
        final SpawnersCacheManager manager = SpawnersCacheManager.getInstance();
        final SpawnersCacheGetters getters = SpawnersCacheGetters.getInstance();
        final SpawnersCacheSetters setters = SpawnersCacheSetters.getInstance();
        final EnchantmentsHandler handler = EnchantmentsHandler.getInstance();

        switch (enchantment) {
            case "speed" -> {
                if ((getters.getSpawnerSpeedLevel(id) - level) < handler.getMaxLevel(enchantment)) {
                    upgradeUnsuccessfully(player);
                } else {
                    setters.addSpawnerSpeedLevel(id, (handler.getPerLevel(enchantment) * level));
                    upgradeSuccessfully(player, id, "Velocidade");
                    SpawnersMobsTaskManager.getInstance().startTask(manager.getSpawnerData(id));
                }
            }
            case "lucky" -> {
                if ((getters.getSpawnerLuckyLevel(id) + level) > handler.getMaxLevel(enchantment)) {
                    upgradeUnsuccessfully(player);
                } else {
                    setters.addSpawnerLuckyLevel(id, (handler.getPerLevel(enchantment) * level));
                    upgradeSuccessfully(player, id, "Sortudo");
                }
            }
            case "experience" -> {
                if ((getters.getSpawnerExperienceLevel(id) + level) > handler.getMaxLevel(enchantment)) {
                    upgradeUnsuccessfully(player);
                } else {
                    setters.addSpawnerExperienceLevel(id, (handler.getPerLevel(enchantment) * level));
                    upgradeSuccessfully(player, id, "Experiente");
                }
            }
        }
    }

    private void upgradeSuccessfully(Player player, String id, String enchantment) {
        player.sendActionBar(TextAPI.parse("§a§lYAY! §aVocê melhorou o encantamento §7" + enchantment +"§a do seu spawner."));
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
