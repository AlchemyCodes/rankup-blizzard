package blizzard.development.fishing.inventories;

import blizzard.development.fishing.database.cache.methods.RodsCacheMethod;
import blizzard.development.fishing.handlers.FishBucketHandler;
import blizzard.development.fishing.handlers.FishingNetHandler;
import blizzard.development.fishing.handlers.FishingRodHandler;
import blizzard.development.fishing.handlers.FurnaceItemHandler;
import blizzard.development.fishing.inventories.items.bucket.FishBucketInventory;
import blizzard.development.fishing.utils.PluginImpl;
import blizzard.development.fishing.utils.ProxyManager;
import blizzard.development.fishing.utils.items.ItemBuilder;
import com.github.stefvanschie.inventoryframework.gui.GuiItem;
import com.github.stefvanschie.inventoryframework.gui.type.ChestGui;
import com.github.stefvanschie.inventoryframework.pane.StaticPane;
import com.github.stefvanschie.inventoryframework.pane.util.Slot;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.Objects;

public class FishingInventory {

    public static void openFishing(Player player) {
        ChestGui gui = new ChestGui(3, "Pescaria");
        StaticPane pane = new StaticPane(0, 0, 9, 3);

        if (player.getWorld().getName().equals("pesca")) {
            pane.addItem(createReturnButton(player), Slot.fromIndex(13));
        } else {
            pane.addItem(createGoButton(player), Slot.fromIndex(13));
        }

        pane.addItem(createBucketItem(player), Slot.fromIndex(16));
        pane.addItem(createTopItem(player), Slot.fromIndex(10));
//        pane.addItem(createBoosterButton(player), Slot.fromIndex(16));
//        pane.addItem(createMaterialsButton(player), Slot.fromIndex(11));

        gui.addPane(pane);
        gui.show(player);
    }

    private static GuiItem createReturnButton(Player player) {
        return new GuiItem(returnFishing(), event -> {
            event.setCancelled(true);
            player.getInventory().clear();

            player.teleport(Objects.requireNonNull(Bukkit.getWorld("spawn2")).getSpawnLocation());
        });
    }

    private static GuiItem createBucketItem(Player player) {
        return new GuiItem(openBucket(), event -> {
            event.setCancelled(true);
            FishBucketInventory.openBucket(player);
        });
    }

    private static GuiItem createTopItem(Player player) {
        return new GuiItem(top(), event -> {
            event.setCancelled(true);
        });
    }

    private static GuiItem createGoButton(Player player) {
        YamlConfiguration locationsConfig = PluginImpl.getInstance().Locations.getConfig();

        Location location = new Location(
                Bukkit.getWorld(locationsConfig.getString("spawn.world")),
                locationsConfig.getDouble("spawn.x"),
                locationsConfig.getDouble("spawn.y"),
                locationsConfig.getDouble("spawn.z"),
                (float) locationsConfig.getDouble("spawn.yaw"),
                (float) locationsConfig.getDouble("spawn.pitch"));

        return new GuiItem(goFishing(), event -> {
            event.setCancelled(true);

            if (!isInventoryEmpty(player)) {
                player.sendMessage("§cSeu inventário precisa estar vazio!");
                return;
            }

            player.teleport(location);

            FishingRodHandler.setRod(player, 0);
            FishingNetHandler.setNet(player, 3);
            FurnaceItemHandler.setFurnace(player, 5);
            FishBucketHandler.setBucket(player, 8);
        });
    }

//    private static GuiItem createBoosterButton(Player player) {
//        return new GuiItem(boosters(), event -> {
//            event.setCancelled(true);
//            BoosterInventory.openBooster(player);
//        });
//    }

//    private static GuiItem createMaterialsButton(Player player) {
//        return new GuiItem(materials(), event -> {
//            event.setCancelled(true);
//            MaterialsInventory.materialsMenu(player);
//        });
//    }

    public static ItemStack returnFishing() {
        return new ItemBuilder(Material.REDSTONE)
            .setDisplayName("§cSair da Pesca.")
            .setLore(Arrays.asList(
                "§7Volta para a segurança",
                "§7fora da pescaria.",
                "",
                "§cClique para sair."
            ))
            .addEnchant(Enchantment.DURABILITY, 10000, true)
            .build();
    }

    public static ItemStack openBucket() {
        return new ItemBuilder(Material.WATER_BUCKET)
                .setDisplayName("§bBalde de Peixes")
                .setLore(Arrays.asList(
                   "§7Venda peixes ou aprimore",
                   "§7seu balde com melhorias.",
                   "",
                   "§bClique para visualizar."
                ))
                .build();
    }

    public static ItemStack goFishing() {
        return new ItemBuilder(Material.COMPASS)
                .setDisplayName("§aEntrar na pesca")
                .setLore(Arrays.asList(
                    "§7Pesque grandes peixes para",
                    "§7conseguir recompensas.",
                    "",
                    "§aClique para ir."
                ))
                .addEnchant(Enchantment.DURABILITY, 10000, false)
                .build();
    }

    public static ItemStack top() {
        return new ItemBuilder(Material.GOLD_INGOT)
                .setDisplayName("§eClassificação")
                .setLore(Arrays.asList(
                        "§7Confira agora os jogadores",
                        "§7que mais se destacam.",
                        "",
                        "§eClique para visualizar."
                ))
                .build();
    }

//    public static ItemStack materials() {
//        return new ItemBuilder(Material.MINECART)
//                .setDisplayName("§6Skins de Vara")
//                .setLore(Arrays.asList(
//                        "§7Gerencie suas skins",
//                        "",
//                        " §7▶ §fSkins o fornecerá mais",
//                        "    §fganhos de coins ao pescar§l!",
//                        "",
//                        "§6Clique para acessar."
//                ))
//                .build();
//    }

    public static boolean isInventoryEmpty(Player player) {
        for (ItemStack item : player.getInventory().getContents()) {
            if (item != null) {
                return false;
            }
        }
        return true;
    }
}
