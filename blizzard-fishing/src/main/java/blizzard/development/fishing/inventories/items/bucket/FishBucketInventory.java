package blizzard.development.fishing.inventories.items.bucket;

import blizzard.development.currencies.api.CurrenciesAPI;
import blizzard.development.currencies.enums.Currencies;
import blizzard.development.fishing.database.cache.methods.PlayersCacheMethod;
import blizzard.development.fishing.database.cache.methods.RodsCacheMethod;
import blizzard.development.fishing.utils.PluginImpl;
import blizzard.development.fishing.utils.fish.FishData;
import blizzard.development.fishing.utils.fish.FishesUtils;
import blizzard.development.fishing.utils.items.ItemBuilder;
import com.github.stefvanschie.inventoryframework.gui.GuiItem;
import com.github.stefvanschie.inventoryframework.gui.type.ChestGui;
import com.github.stefvanschie.inventoryframework.pane.StaticPane;
import com.github.stefvanschie.inventoryframework.pane.util.Slot;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FishBucketInventory {

    private static final Map<String, FishData> fishMap = new HashMap<>();
    static {
        fishMap.put("bacalhau", new FishData("Bacalhau", "common", "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNzg5MmQ3ZGQ2YWFkZjM1Zjg2ZGEyN2ZiNjNkYTRlZGRhMjExZGY5NmQyODI5ZjY5MTQ2MmE0ZmIxY2FiMCJ9fX0="
                , 10));
        fishMap.put("salmao", new FishData("Salmão", "common", "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMTMwZGU5MTQwOTY4NWZjZmU2OWM5Mzk3YmZkNzFmYWY0ZTBjY2ZlYTBiMTk1NDAyZjEzNGY1OTUyN2MwMjM4NCJ9fX0="
                , 11));
        fishMap.put("caranguejo", new FishData("Caranguejo", "rare", "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYjEyOWRmNDJiZjI3YWFmNWU4MTY0MzkxODUxYTc1YWVmOGNlMDAwNzYyY2EwMmZjN2MzZWJmN2ZmY2QyNTgzOCJ9fX0="
                , 12));
        fishMap.put("lagosta", new FishData("Lagosta", "rare", "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZDBjNzVlNzQ3OGJmOGY4YTk3NTgzMGQ5NTZmYzQ5YmEwNDY5ZjE1ZTM0MzUyODFjMjM3ODkxYTIyN2ZmYjc2MyJ9fX0="
                , 13));
        fishMap.put("lula", new FishData("Lula", "legendary", "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNDljMmM5Y2U2N2ViNTk3MWNjNTk1ODQ2M2U2YzlhYmFiOGU1OTlhZGMyOTVmNGQ0MjQ5OTM2YjAwOTU3NjlkZCJ9fX0="
                , 19));
        fishMap.put("lula_brilhante", new FishData("Lula Brilhante", "legendary", "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNDVjOTk5ZGQxMmRkMWM4NjZmZGQwZWU5NGEzOTczNTMzNDI4Y2Q3MmQ5Mjk2YzYyNzI0ZjQyOTM2NWRhOGVlYiJ9fX0="
                , 20));
        fishMap.put("tubarao", new FishData("Tubarão", "mystic", "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOGYwNjdmOGQ3NjJlNGFmOTNiZDNmMmY5ZTc5ODdhYjBhYTBiZGQzOWFkMWU2YTNkNmI0NjJjMWE2NDdlODAxIn19fQ=="
                , 21));
        fishMap.put("baleia", new FishData("Baleia", "mystic", "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvODhmZWYwMjViOTQ0YTJhMTRiMDg0ZGM1MGI5MmQ2ZGU5OTllNDRkMGJiMDViYmYwOTIzNzgyOTJmN2QyZGJlZiJ9fX0="
                , 22));
    }

    public static void openBucket(Player player) {
        ChestGui inventory = new ChestGui(6, "Inventário Balde");
        StaticPane pane = new StaticPane(0, 0, 9, 6);

        YamlConfiguration config = PluginImpl.getInstance().Config.getConfig();

        FishesUtils fishesUtils = FishesUtils.getInstance();
        PlayersCacheMethod cacheMethod = PlayersCacheMethod.getInstance();
        RodsCacheMethod rodsCacheMethod = RodsCacheMethod.getInstance();
        CurrenciesAPI currenciesAPI = CurrenciesAPI.getInstance();

        Currencies coins = Currencies.COINS;

        fishMap.forEach((fishName, fishData) -> {
            double fishPrice = config.getDouble("fishes." + fishName + ".price");
            int fishAmount = cacheMethod.getFishAmount(player, fishName);
            int strength = rodsCacheMethod.getStrength(player);
            String rarity = config.getString("fishes." + fishName + ".rarity");
            int requiredStrength = fishesUtils.getStrengthNecessary(rarity);

            GuiItem item = new GuiItem(createFishItem(fishData, fishPrice, fishAmount, strength, requiredStrength), event -> {
                int quantity = cacheMethod.getFishAmount(player, fishName);
                if (quantity == 0) {
                    player.sendMessage("vc nao tem " + fishName + " para vender");
                    event.setCancelled(true);
                    return;
                }
                int fishValue = fishesUtils.getFishValue(fishData.getRarity());
                int totalValue = fishValue * quantity;

                currenciesAPI.addBalance(player, coins, totalValue);
                player.sendMessage("vc vendeu " + quantity + " " + fishName + " por " + totalValue + " moedas.");
                cacheMethod.setFishAmount(player, fishName, 0);

                event.setCancelled(true);
            });

            pane.addItem(item, Slot.fromIndex(fishData.getSlot()));
        });

        GuiItem backButton = new GuiItem(new ItemBuilder(Material.RED_DYE)
                .setDisplayName("§cVoltar")
                .setLore(List.of("§7Volte ao menu anterior."))
                .build(),

                event -> {
                    event.setCancelled(true);
                });

        GuiItem upgradesButton = new GuiItem(new ItemBuilder(Material.LIME_DYE)
                .setDisplayName("§aMelhorias")
                .setLore(List.of("§7Clique para ir ao, §7menu de melhorias do balde."))
                .build(),

                event -> {
                    event.setCancelled(true);
                    FishBucketUpgrades.upgradesMenu(player);
                });

        GuiItem frozenFish = new GuiItem(new ItemBuilder("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNzY0YjExYWU1ZDM4NzdjZjA4OGU0Yjg0MjQ4MzM3ODNiZmE5NzNhMDM0OTUwYTc0ZDc4Yjg5ZjMzMTVkMzZiZCJ9fX0=")
                .setDisplayName("§bPeixe Congelados §7[" + PlayersCacheMethod.getInstance().getFrozenFish(player) +"]")
                .setLore(List.of("§7Peixes congelados ao descongelar"
                        ,"§7viram outro peixe aleatório"
                        ,"§7dando dinheiro extra ao descongelar!"))
                .build(),

                event -> {
                    event.setCancelled(true);
                });

        GuiItem none = new GuiItem(new ItemBuilder(Material.BARRIER)
                .setDisplayName("")
                .setLore(List.of(""))
                .build(),

                event -> {
                    event.setCancelled(true);
                });

        for (int i = 28; i < 32; i++) {
            pane.addItem(none, Slot.fromIndex(i));
        }

        pane.addItem(frozenFish, Slot.fromIndex(25));
        pane.addItem(backButton, Slot.fromIndex(46));
        pane.addItem(upgradesButton, Slot.fromIndex(49));

        inventory.addPane(pane);
        inventory.show(player);
    }

    private static ItemStack createFishItem(FishData data, double fishPrice, int fishAmount, int strength, int requiredStrength) {
        ItemBuilder itemBuilder = new ItemBuilder(data.getHeadValue())
                .setDisplayName("§b" + data.getName() + " [" + fishAmount + "]");

        if (strength >= requiredStrength) {
            itemBuilder.setLore(Arrays.asList(
                    "",
                    "§8 ■ §7Valor Unitário: §b" + fishPrice,
                    "§8 ▶ §7Valor Total: §b" + fishPrice * fishAmount,
                    "",
                    "§bClique aqui para vender."
            ));
        } else {
            itemBuilder.setLore(Arrays.asList(
                    "§cForça " + requiredStrength + " necessário!"
            ));
        }

        return itemBuilder.build();
    }
}
