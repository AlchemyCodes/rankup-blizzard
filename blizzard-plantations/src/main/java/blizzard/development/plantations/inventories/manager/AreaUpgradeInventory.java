package blizzard.development.plantations.inventories.manager;

import blizzard.development.plantations.builder.ItemBuilder;
import blizzard.development.plantations.database.cache.methods.PlayerCacheMethod;
import blizzard.development.plantations.managers.AreaManager;
import com.github.stefvanschie.inventoryframework.gui.GuiItem;
import com.github.stefvanschie.inventoryframework.gui.type.ChestGui;
import com.github.stefvanschie.inventoryframework.pane.StaticPane;
import com.github.stefvanschie.inventoryframework.pane.util.Slot;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;

import static blizzard.development.plantations.utils.NumberFormat.formatNumber;

public class AreaUpgradeInventory {

    public void open(Player player) {
        ChestGui inventory = new ChestGui(5, "§8Área");
        StaticPane pane = new StaticPane(0, 0, 9, 5);

        AreaManager areaManager = AreaManager.getInstance();
        PlayerCacheMethod playerCacheMethod = PlayerCacheMethod.getInstance();

        int radius40Cost = 1000;
        int radius60Cost = 1000;
        int radius80Cost = 1000;
        int radius100Cost = 1000;

        int seeds = playerCacheMethod.getPlantations(player);

        GuiItem radius20 = new GuiItem(radius(player, Material.SMALL_AMETHYST_BUD, 10), event -> {;
            event.setCancelled(true);
        });

        GuiItem radius40 = new GuiItem(radius(player, Material.MEDIUM_AMETHYST_BUD, 20), event -> {
            int radius = areaManager.getArea(player);
            if (radius == 50) {
                player.closeInventory();
                player.sendActionBar("§c§lEI! §cA sua área já está no level máximo.");
                return;
            }

            if (radius40Cost > seeds) {
                int subtraction = radius40Cost - seeds;

                player.closeInventory();
                player.sendMessage("");
                player.sendMessage(" §c§lEI! §cVocê não possui sementes para evoluir.");
                player.sendMessage(" §cFaltam §l" + formatNumber(subtraction) + "§c sementes para executar a ação.");
                player.sendMessage("");
            }
            areaManager.setArea(player, 20);
            areaManager.applyAreaUpgrade(player);

            open(player);
            event.setCancelled(true);
        });

        GuiItem radius60 = new GuiItem(radius(player, Material.LARGE_AMETHYST_BUD, 30), event -> {
            int radius = areaManager.getArea(player);

            if (radius == 50) {
                player.closeInventory();
                player.sendActionBar("§c§lEI! §cA sua área já está no level máximo.");
                return;
            }

            if (radius != 20) {
                player.closeInventory();
                player.sendActionBar("§c§lEI! §cA sua área não está no nível anterior.");
                return;
            }

            areaManager.setArea(player, 30);
            areaManager.applyAreaUpgrade(player);

            open(player);
            event.setCancelled(true);
        });

        GuiItem radius80 = new GuiItem(radius(player, Material.AMETHYST_CLUSTER, 40), event -> {
            int radius = areaManager.getArea(player);

            if (radius == 50) {
                player.closeInventory();
                player.sendActionBar("§c§lEI! §cA sua área já está no level máximo.");
                return;
            }

            if (radius != 30) {
                player.closeInventory();
                player.sendActionBar("§c§lEI! §cA sua área não está no nível anterior.");
                return;
            }

            areaManager.setArea(player, 40);
            areaManager.applyAreaUpgrade(player);

            open(player);
            event.setCancelled(true);
        });

        GuiItem radius100 = new GuiItem(radius(player, Material.AMETHYST_SHARD, 50), event -> {
            int radius = areaManager.getArea(player);

            if (radius == 50) {
                player.closeInventory();
                player.sendActionBar("§c§lEI! §cA sua área já está no level máximo.");
                return;
            }

            areaManager.setArea(player, 50);
            areaManager.applyAreaUpgrade(player);

            open(player);
            event.setCancelled(true);
        });

        GuiItem area = new GuiItem(area(), event -> {
            event.setCancelled(true);
        });

        GuiItem plantations = new GuiItem(plantations(), event -> {
            PlantationUpgradeInventory plantationUpgradeInventory = new PlantationUpgradeInventory();
            plantationUpgradeInventory.open(player);
           event.setCancelled(true);
        });

        GuiItem information = new GuiItem(information(), event -> {
            event.setCancelled(true);
        });


        pane.addItem(radius20, Slot.fromIndex(11));
        pane.addItem(radius40, Slot.fromIndex(12));
        pane.addItem(radius60, Slot.fromIndex(13));
        pane.addItem(radius80, Slot.fromIndex(14));
        pane.addItem(radius100, Slot.fromIndex(15));
        pane.addItem(plantations, Slot.fromIndex(39));
        pane.addItem(information, Slot.fromIndex(40));
        pane.addItem(area, Slot.fromIndex(41));

        inventory.addPane(pane);
        inventory.show(player);
        player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 0.5f, 0.5f);
    }

    public static ItemStack radius(Player player, Material material, int radius) {
        PlayerCacheMethod playerCacheMethod = PlayerCacheMethod.getInstance();
        int currentRadius = playerCacheMethod.getArea(player);

        int parseCurrentRadius = switch (currentRadius) {
            case 10 -> 20;
            case 20 -> 40;
            case 30 -> 60;
            case 40 -> 80;
            case 50 -> 100;
            default -> throw new IllegalStateException("Unexpected value: " + currentRadius);
        };

        int parse = switch (radius) {
            case 10 -> 20;
            case 20 -> 40;
            case 30 -> 60;
            case 40 -> 80;
            case 50 -> 100;
            default -> throw new IllegalStateException("Unexpected value: " + radius);
        };

        if (parseCurrentRadius >= parse) {
            return new ItemBuilder(Material.BARRIER)
                .setDisplayName("§cNível desbloqueado!")
                .setLore(Arrays.asList(
                    "§7Você já possui este",
                    "§7nível ou um superior.",
                    "",
                    " §fRaio Atual: §c" + parseCurrentRadius + "x",
                    "",
                    "§cMelhoria não disponível."
                ))
                .build();
        }

        return new ItemBuilder(material)
            .setDisplayName("§dAumentar raio §l" + parse + "§dx")
            .setLore(Arrays.asList(
                "§7Aumente o raio de",
                "§7plantações da estufa.",
                "",
                " §fRaio: §d" + parse + "x",
                " §fCusto: §a✿15K",
                "",
                "§dClique para upar."
            ))
            .build();
    }

    public static ItemStack information() {
        return new ItemBuilder(Material.NETHER_STAR)
            .setDisplayName("§fInformação!")
            .setLore(Arrays.asList(
                "§7Altere sobre os menus",
                "§7clicando nos itens ao lado."
            ))
            .build();
    }

    public static ItemStack area() {
        return new ItemBuilder(Material.BARRIER)
            .setDisplayName("§cÀrea de Plantações")
            .setLore(Arrays.asList(
                "§7Gerencia o tamanho",
                "§7da área da sua estufa.",
                "",
                "§cVocê está aqui."
            ))
            .build();
    }

    public static ItemStack plantations() {
        return new ItemBuilder("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNGYxNGI1OGYzZGY2NWEwYzZiOTBlZTE5NDY0YjI1NTdjODNhZTJjOWZhMWI1NzM4YmIxMTM2NGNkOWY1YjNlMSJ9fX0")
            .setDisplayName("§cPlantações")
            .setLore(Arrays.asList(
                "§7Gerencia as plantações",
                "§7da área da sua estufa.",
                "",
                "§cClique para acessar."
            ))
            .build();
    }
}
