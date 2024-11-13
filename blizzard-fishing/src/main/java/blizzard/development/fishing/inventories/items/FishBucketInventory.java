package blizzard.development.fishing.inventories.items;

import blizzard.development.fishing.database.cache.PlayersCacheManager;
import blizzard.development.fishing.database.cache.methods.PlayersCacheMethod;
import blizzard.development.fishing.database.cache.methods.RodsCacheMethod;
import blizzard.development.fishing.utils.PluginImpl;
import blizzard.development.fishing.utils.fish.FishesUtils;
import blizzard.development.fishing.utils.items.ItemBuilder;
import blizzard.development.fishing.utils.items.skulls.SkullAPI;
import com.github.stefvanschie.inventoryframework.gui.GuiItem;
import com.github.stefvanschie.inventoryframework.gui.type.ChestGui;
import com.github.stefvanschie.inventoryframework.pane.StaticPane;
import com.github.stefvanschie.inventoryframework.pane.util.Slot;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public class FishBucketInventory {
    public static void openBucket(Player player) {

        ChestGui inventory = new ChestGui(6, "Inventario Balde");

        StaticPane pane = new StaticPane(0, 0, 9, 6);

        GuiItem bacalhau = new GuiItem(bacalhau(player), event -> {
            event.setCancelled(true);
        });
        GuiItem salmao = new GuiItem(salmao(player), event -> {
            event.setCancelled(true);
        });
        GuiItem caranguejo = new GuiItem(caranguejo(player), event -> {
            event.setCancelled(true);
        });
        GuiItem lagosta = new GuiItem(lagosta(player), event -> {
            event.setCancelled(true);
        });
        GuiItem lula = new GuiItem(lula(player), event -> {
            event.setCancelled(true);
        });
        GuiItem lulaBrilhante = new GuiItem(lulaBrilhante(player), event -> {
            event.setCancelled(true);
        });
        GuiItem tubarao = new GuiItem(tubarao(player), event -> {
            event.setCancelled(true);
        });
        GuiItem baleia = new GuiItem(baleia(player), event -> {
            event.setCancelled(true);
        });
        GuiItem peixeCongelado = new GuiItem(peixeCongelado(player), event -> {
            event.setCancelled(true);
        });
        GuiItem back = new GuiItem(back(player), event -> {
            event.setCancelled(true);
        });
        GuiItem go = new GuiItem(go(player), event -> {
            event.setCancelled(true);
        });
        GuiItem soon = new GuiItem(soon(player), event -> {
            event.setCancelled(true);
        });

        pane.addItem(bacalhau, Slot.fromIndex(10));
        pane.addItem(salmao, Slot.fromIndex(11));
        pane.addItem(caranguejo, Slot.fromIndex(12));
        pane.addItem(lagosta, Slot.fromIndex(13));
        pane.addItem(lula, Slot.fromIndex(19));
        pane.addItem(lulaBrilhante, Slot.fromIndex(20));
        pane.addItem(tubarao, Slot.fromIndex(21));
        pane.addItem(baleia, Slot.fromIndex(22));
        pane.addItem(soon, Slot.fromIndex(28));
        pane.addItem(soon, Slot.fromIndex(29));
        pane.addItem(soon, Slot.fromIndex(30));
        pane.addItem(soon, Slot.fromIndex(31));
        pane.addItem(peixeCongelado, Slot.fromIndex(25));
        pane.addItem(back, Slot.fromIndex(46));
        pane.addItem(go, Slot.fromIndex(49));



        inventory.addPane(pane);
        inventory.show(player);
    }

    private static ItemStack bacalhau(Player player) {
        return createFishItem(player, "Bacalhau"
                , "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNzg5MmQ3ZGQ2YWFkZjM1Zjg2ZGEyN2ZiNjNkYTRlZGRhMjExZGY5NmQyODI5ZjY5MTQ2MmE0ZmIxY2FiMCJ9fX0="
                , "bacalhau");
    }

    private static ItemStack salmao(Player player) {
        return createFishItem(player, "Salmão"
                , "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMTMwZGU5MTQwOTY4NWZjZmU2OWM5Mzk3YmZkNzFmYWY0ZTBjY2ZlYTBiMTk1NDAyZjEzNGY1OTUyN2MwMjM4NCJ9fX0="
                , "salmao");
    }

    private static ItemStack caranguejo(Player player) {
        return createFishItem(player, "Caranguejo"
                , "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYjEyOWRmNDJiZjI3YWFmNWU4MTY0MzkxODUxYTc1YWVmOGNlMDAwNzYyY2EwMmZjN2MzZWJmN2ZmY2QyNTgzOCJ9fX0="
                , "caranguejo");
    }

    private static ItemStack lagosta(Player player) {
        return createFishItem(player, "Lagosta", "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZDBjNzVlNzQ3OGJmOGY4YTk3NTgzMGQ5NTZmYzQ5YmEwNDY5ZjE1ZTM0MzUyODFjMjM3ODkxYTIyN2ZmYjc2MyJ9fX0="
                , "lagosta");
    }

    private static ItemStack lula(Player player) {
        return createFishItem(player, "Lula", "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNDljMmM5Y2U2N2ViNTk3MWNjNTk1ODQ2M2U2YzlhYmFiOGU1OTlhZGMyOTVmNGQ0MjQ5OTM2YjAwOTU3NjlkZCJ9fX0="
                , "lula");
    }

    private static ItemStack lulaBrilhante(Player player) {
        return createFishItem(player, "Lula Brilhante", "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNDVjOTk5ZGQxMmRkMWM4NjZmZGQwZWU5NGEzOTczNTMzNDI4Y2Q3MmQ5Mjk2YzYyNzI0ZjQyOTM2NWRhOGVlYiJ9fX0="
                , "lula_brilhante");
    }

    private static ItemStack tubarao(Player player) {
        return createFishItem(player, "Tubarão", "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOGYwNjdmOGQ3NjJlNGFmOTNiZDNmMmY5ZTc5ODdhYjBhYTBiZGQzOWFkMWU2YTNkNmI0NjJjMWE2NDdlODAxIn19fQ=="
                , "tubarao");
    }

    private static ItemStack baleia(Player player) {
        return createFishItem(player, "Baleia", "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvODhmZWYwMjViOTQ0YTJhMTRiMDg0ZGM1MGI5MmQ2ZGU5OTllNDRkMGJiMDViYmYwOTIzNzgyOTJmN2QyZGJlZiJ9fX0="
                , "baleia");
    }

    private static ItemStack peixeCongelado(Player player) {
        return new ItemBuilder("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNzY0YjExYWU1ZDM4NzdjZjA4OGU0Yjg0MjQ4MzM3ODNiZmE5NzNhMDM0OTUwYTc0ZDc4Yjg5ZjMzMTVkMzZiZCJ9fX0=")
                .setDisplayName("§bPeixe Congelados §7[" + PlayersCacheMethod.getInstance().getFrozenFish(player) +"]")
                .setLore(Arrays.asList
                        ("§7Peixes congelados ao descongelar","§7viram outro peixe aleatório"
                        ,"§7dando dinheiro extra ao descongelar!"))
                .build();
    }

    private static ItemStack back(Player player) {
        return new ItemBuilder(Material.RED_DYE)
                .setDisplayName("§cVoltar")
                .setLore(Arrays.asList("§7Volte ao menu anterior."))
                .build();
    }

    private static ItemStack go(Player player) {
        return new ItemBuilder(Material.GREEN_DYE)
                .setDisplayName("§aMelhorias")
                .setLore(Arrays.asList("§7Clique para ir ao", "§7menu de melhorias do balde."))
                .build();
    }

    private static ItemStack soon(Player player) {
        return new ItemBuilder(Material.BARRIER)
                .setDisplayName("")
                .setLore(Arrays.asList(""))
                .build();
    }

    private static ItemStack createFishItem(Player player, String name, String textureValue, String fishName) {
        YamlConfiguration config = PluginImpl.getInstance().Config.getConfig();
        int fishAmount = PlayersCacheMethod.getInstance().getFishAmount(player, fishName);
        int strength = RodsCacheMethod.getInstance().getStrength(player);
        String rarity = config.getString("fishes." + fishName + ".rarity");
        assert rarity != null;
        int requiredStrength = FishesUtils.getStrengthNecessary(rarity);

        ItemBuilder itemBuilder = new ItemBuilder(textureValue)
                .setDisplayName("§b" + name + " [" + fishAmount + "]");

        if (strength >= requiredStrength) {
            double unitPrice = config.getDouble("fishes." + fishName + ".price");
            itemBuilder.setLore(Arrays.asList(
                    "",
                    "§8 ■ §7Valor Unitário: §b" + unitPrice,
                    "§8 ▶ §7Valor Total: §b" + unitPrice * fishAmount,
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
