package blizzard.development.bosses.inventories;

import blizzard.development.bosses.utils.PluginImpl;
import blizzard.development.bosses.utils.items.ItemBuilder;
import com.github.stefvanschie.inventoryframework.gui.GuiItem;
import com.github.stefvanschie.inventoryframework.gui.type.ChestGui;
import com.github.stefvanschie.inventoryframework.pane.StaticPane;
import com.github.stefvanschie.inventoryframework.pane.util.Slot;
import org.bukkit.*;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.Objects;

public class BossesInventory {
    public static void open(Player player) {
        ChestGui inventory = new ChestGui(3, "§8Bosses");

        StaticPane pane = new StaticPane(0, 0, 9, 3);

        GuiItem stockItem = new GuiItem(stock(), event -> {
            event.setCancelled(true);
        });

        GuiItem friendsItem = new GuiItem(friends(), event -> {
            event.setCancelled(true);
        });

        GuiItem enterWorldItem = new GuiItem(enterWorld(), event -> {
            player.getOpenInventory().close();
            sendPlayerToWorld(player);
            event.setCancelled(true);
        });

        GuiItem leaveWorldItem = new GuiItem(leaveWorld(), event -> {
            player.getOpenInventory().close();
            sendPlayerToWorld(player);
            event.setCancelled(true);
        });

        GuiItem rankingItem = new GuiItem(ranking(), event -> {
            event.setCancelled(true);
        });

        boolean isInBossWorld = player.getWorld().equals(Bukkit.getWorld(Objects.requireNonNull(PluginImpl.getInstance().Locations.getConfig().getString("spawn.location.world"))));

        pane.addItem(stockItem, Slot.fromIndex(10));
        pane.addItem(friendsItem, Slot.fromIndex(12));
        pane.addItem(isInBossWorld ? leaveWorldItem : enterWorldItem, Slot.fromIndex(14));
        pane.addItem(rankingItem, Slot.fromIndex(16));

        inventory.addPane(pane);

        player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 0.5f, 0.5f);

        inventory.show(player);
    }

    public static ItemStack stock() {
        return new ItemBuilder(Material.TURTLE_EGG)
                .setDisplayName("&bArmazém")
                .setLore(Arrays.asList(
                        "&7Visualize e gerencie seu",
                        "&7armazém de recompensas.",
                        "",
                        "&bClique para visualizar."
                ))
                .build();
    }

    public static ItemStack friends() {
        return new ItemBuilder(Material.OAK_SIGN)
                .setDisplayName("&cAmigos")
                .setLore(Arrays.asList(
                        "&7Gerencie seus amigos",
                        "",
                        "&cClique para gerenciar."
                ))
                .build();
    }

    public static ItemStack enterWorld() {
        return new ItemBuilder(Material.COMPASS)
                .setDisplayName("&bIr ao mundo")
                .setLore(Arrays.asList(
                        "&7Vá para o mundo e",
                        "&7derrote seus bosses.",
                        "",
                        "&bClique para ir ao mundo."
                ))
                .addItemFlags(ItemFlag.HIDE_ENCHANTS)
                .addEnchant(Enchantment.DURABILITY, 1, true)
                .build();
    }

    public static ItemStack leaveWorld() {
        return new ItemBuilder(Material.REDSTONE)
                .setDisplayName("&cSair do mundo")
                .setLore(Arrays.asList(
                        "&7Volte ao spawn com segurança.",
                        "",
                        "&cClique para sair do mundo."
                ))
                .addItemFlags(ItemFlag.HIDE_ENCHANTS)
                .addEnchant(Enchantment.DURABILITY, 1, true)
                .build();
    }

    public static ItemStack ranking() {
        return new ItemBuilder(Material.GOLD_INGOT)
                .setDisplayName("&eClassificação")
                .setLore(Arrays.asList(
                        "&7Visualize os jogadores",
                        "&7que mais se destacam.",
                        "",
                        "&eClique para visualizar."
                ))
                .build();
    }


    private static void sendPlayerToWorld(Player player) {

        String worldSpawn = PluginImpl.getInstance().Locations.getConfig().getString("spawn.location.world");
        double x = PluginImpl.getInstance().Locations.getConfig().getDouble("spawn.location.x");
        double y = PluginImpl.getInstance().Locations.getConfig().getDouble("spawn.location.y");
        double z = PluginImpl.getInstance().Locations.getConfig().getDouble("spawn.location.z");
        float yaw = (float) PluginImpl.getInstance().Locations.getConfig().getDouble("spawn.location.yaw");
        float pitch = (float) PluginImpl.getInstance().Locations.getConfig().getDouble("spawn.location.pitch");

        if (worldSpawn == null) {
            player.sendActionBar("§c§lEI! §cO local de spawn do mundo de bosses não foi definido ou é inválido.");
            return;
        }

        World world = Bukkit.getWorld(worldSpawn);


        if (!player.getWorld().equals(world)) {
            player.teleport(
                    new Location(
                            world,
                            x,
                            y,
                            z,
                            yaw,
                            pitch
                    )
            );
            player.sendTitle(
                    "§a§lBosses!",
                    "§aVocê entrou na área de bosses.",
                    10,
                    70,
                    20
            );
            return;
        }

        player.performCommand("spawn");

        player.sendTitle(
                "§c§lBOSSES!",
                "§cVocê saiu da área de bosses.",
                10,
                70,
                20
        );
    }
}
