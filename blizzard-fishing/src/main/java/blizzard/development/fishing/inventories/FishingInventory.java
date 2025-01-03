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
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.Objects;

public class FishingInventory {

    public static void openFishing(Player player) {
        ChestGui gui = new ChestGui(3, "Pesca");
        StaticPane pane = new StaticPane(0, 0, 9, 3);

        if (player.getWorld().getName().equals("pesca")) {
            pane.addItem(createReturnButton(player), Slot.fromIndex(13));
        } else {
            pane.addItem(createGoButton(player), Slot.fromIndex(13));
        }

        pane.addItem(createBucketItem(player), Slot.fromIndex(15));
//        pane.addItem(createBoosterButton(player), Slot.fromIndex(16));
        pane.addItem(createMaterialsButton(player), Slot.fromIndex(11));

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

    private static GuiItem createMaterialsButton(Player player) {
        return new GuiItem(materials(), event -> {
            event.setCancelled(true);
            MaterialsInventory.materialsMenu(player);
        });
    }

    public static ItemStack returnFishing() {
        return new ItemBuilder(Material.BARRIER)
                .setDisplayName("§cSair")
                .setLore(Arrays.asList("§7Clique para sair da pesca!"))
                .build();
    }

    public static ItemStack openBucket() {
        return new ItemBuilder(Material.PUFFERFISH_BUCKET)
                .setDisplayName("§6Balde")
                .setLore(Arrays.asList("§7Clique para ver seu balde!"))
                .build();
    }

    public static ItemStack goFishing() {
        return new ItemBuilder(Material.COMPASS)
                .setDisplayName("§aEntrar")
                .setLore(Arrays.asList("§7Clique para entrar na pesca!"))
                .build();
    }

    public static ItemStack boosters() {
        return new ItemBuilder(Material.EXPERIENCE_BOTTLE)
                .setDisplayName("§eBoosters")
                .setLore(Arrays.asList(
                        "§7Adquira boosters para",
                        "§7aumentar sua produtividade.",
                        "",
                        " §7▶ §fBoosters o fornecerá",
                        "    §fmais ganhos de xp§l!",
                        "",
                        "§eClique para acessar."
                ))
                .build();
    }

    public static ItemStack materials() {
        return new ItemBuilder(Material.MINECART)
                .setDisplayName("§6Skins de Vara")
                .setLore(Arrays.asList(
                        "§7Gerencie suas skins",
                        "",
                        " §7▶ §fSkins o fornecerá mais",
                        "    §fganhos de coins ao pescar§l!",
                        "",
                        "§6Clique para acessar."
                ))
                .build();
    }

    public static boolean isInventoryEmpty(Player player) {
        for (ItemStack item : player.getInventory().getContents()) {
            if (item != null) {
                return false;
            }
        }
        return true;
    }
}
