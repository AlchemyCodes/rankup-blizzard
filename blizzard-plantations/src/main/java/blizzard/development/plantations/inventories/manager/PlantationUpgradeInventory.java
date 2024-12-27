package blizzard.development.plantations.inventories.manager;

import blizzard.development.plantations.builder.ItemBuilder;
import blizzard.development.plantations.managers.AreaManager;
import blizzard.development.plantations.managers.PlantationManager;
import blizzard.development.plantations.plantations.enums.PlantationEnum;
import com.github.stefvanschie.inventoryframework.gui.GuiItem;
import com.github.stefvanschie.inventoryframework.gui.type.ChestGui;
import com.github.stefvanschie.inventoryframework.pane.StaticPane;
import com.github.stefvanschie.inventoryframework.pane.util.Slot;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.title.Title;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.time.Duration;
import java.util.Arrays;

public class PlantationUpgradeInventory {

    public void open(Player player) {
        ChestGui inventory = new ChestGui(5, "§8Plantações");
        StaticPane pane = new StaticPane(0, 0, 9, 5);


        GuiItem potato = new GuiItem(potato(), event -> {
            event.setCancelled(true);
        });

        GuiItem carrot = new GuiItem(carrot(), event -> {

            AreaManager.getInstance()
                .setAreaPlantation(
                    player,
                    PlantationEnum.CARROT
                );

            PlantationManager.getInstance()
                    .transform(
                        player,
                        AreaManager.getInstance().getArea(player)
                    );

            player.closeInventory();
            player.showTitle(
                Title.title(
                    Component.text("§d§lYeah! §dnovo upgrade."),
                    Component.text("§fVocê adquiriu a plantação §d§lcenoura§d."),
                    Title.Times.times(Duration.ZERO, Duration.ofSeconds(2), Duration.ofSeconds(1))
                )
            );

            event.setCancelled(true);
        });

        GuiItem tomato = new GuiItem(tomato(), event -> {

            AreaManager.getInstance()
                .setAreaPlantation(
                    player,
                    PlantationEnum.TOMATO
                );

            PlantationManager.getInstance()
                .transform(
                    player,
                    AreaManager.getInstance().getArea(player)
                );

            player.closeInventory();
            player.showTitle(
                Title.title(
                    Component.text("§d§lYeah! §dnovo upgrade."),
                    Component.text("§fVocê adquiriu a plantação §d§ltomate§d."),
                    Title.Times.times(Duration.ZERO, Duration.ofSeconds(2), Duration.ofSeconds(1))
                )
            );

            event.setCancelled(true);
        });

        GuiItem corn = new GuiItem(corn(), event -> {

            AreaManager.getInstance()
                .setAreaPlantation(
                    player,
                    PlantationEnum.CORN
                );

            PlantationManager.getInstance()
                .transform(
                    player,
                    AreaManager.getInstance().getArea(player)
                );

            player.closeInventory();
            player.showTitle(
                Title.title(
                    Component.text("§d§lYeah! §dnovo upgrade."),
                    Component.text("§fVocê adquiriu a plantação §d§lmilho§d."),
                    Title.Times.times(Duration.ZERO, Duration.ofSeconds(2), Duration.ofSeconds(1))
                )
            );

            event.setCancelled(true);
        });


        GuiItem area = new GuiItem(area(), event -> {
            AreaUpgradeInventory areaUpgradeInventory = new AreaUpgradeInventory();
            areaUpgradeInventory.open(player);
            event.setCancelled(true);
        });

        GuiItem plantations = new GuiItem(plantations(), event -> {
            event.setCancelled(true);
        });

        GuiItem information = new GuiItem(information(), event -> {
            event.setCancelled(true);
        });


        pane.addItem(potato, Slot.fromIndex(11));
        pane.addItem(carrot, Slot.fromIndex(12));
        pane.addItem(tomato, Slot.fromIndex(14));
        pane.addItem(corn, Slot.fromIndex(15));
        pane.addItem(plantations, Slot.fromIndex(39));
        pane.addItem(information, Slot.fromIndex(40));
        pane.addItem(area, Slot.fromIndex(41));

        inventory.addPane(pane);
        inventory.show(player);
        player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 0.5f, 0.5f);
    }

    public static ItemStack potato() {
        return new ItemBuilder("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvY2Y0NjI0ZWJmN2Q0MTlhMTFlNDNlZDBjMjAzOGQzMmNkMDlhZDFkN2E2YzZlMjBmNjMzOWNiY2ZlMzg2ZmQxYyJ9fX0")
            .setDisplayName("§ePlantação de §lBatata")
            .setLore(Arrays.asList(
                "§7Transforme as suas",
                "§7plantações em batata.",
                "",
                " §fCusto: §a✿15K",
                " §fPreço de venda: §a3",
                "",
                "§eClique para upar."
            ))
            .build();
    }

    public static ItemStack carrot() {
        return new ItemBuilder("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNGQzYTZiZDk4YWMxODMzYzY2NGM0OTA5ZmY4ZDJkYzYyY2U4ODdiZGNmM2NjNWIzODQ4NjUxYWU1YWY2YiJ9fX0")
            .setDisplayName("§6Plantação de §lCenoura")
            .setLore(Arrays.asList(
                "§7Transforme as suas",
                "§7plantações em cenoura.",
                "",
                " §fCusto: §a✿25K",
                " §fPreço de venda: §a8",
                "",
                "§6Clique para upar."
            ))
            .build();
    }

    public static ItemStack tomato() {
        return new ItemBuilder("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNGYxNGI1OGYzZGY2NWEwYzZiOTBlZTE5NDY0YjI1NTdjODNhZTJjOWZhMWI1NzM4YmIxMTM2NGNkOWY1YjNlMSJ9fX0")
            .setDisplayName("§cPlantação de §lTomate")
            .setLore(Arrays.asList(
                "§7Transforme as suas",
                "§7plantações em cenoura.",
                "",
                " §fCusto: §a✿45K",
                " §fPreço de venda: §a12",
                "",
                "§cClique para upar."
            ))
            .build();
    }

    public static ItemStack corn() {
        return new ItemBuilder("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZDNhNmIwOTljZDQwMWUzYTBkNjRkOWExNmY0NmNkMGM1Y2E1ZjdlNDVlNmE2OWMyN2QyZTQ3Mzc3NWIyNWZlIn19fQ")
            .setDisplayName("§ePlantação de §lMilho")
            .setLore(Arrays.asList(
                "§7Transforme as suas",
                "§7plantações em cenoura.",
                "",
                " §fCusto: §a✿65K",
                " §fPreço de venda: §a16",
                "",
                "§eClique para upar."
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
        return new ItemBuilder(Material.FARMLAND)
            .setDisplayName("§eÁrea de Plantações")
            .setLore(Arrays.asList(
                "§7Gerencia o tamanho",
                "§7da área da sua estufa.",
                "",
                "§eClique para acessar."
            ))
            .build();
    }

    public static ItemStack plantations() {
        return new ItemBuilder(Material.BARRIER)
            .setDisplayName("§cPlantações")
            .setLore(Arrays.asList(
                "§7Gerencia as plantações",
                "§7da área da sua estufa.",
                "",
                "§cVocê está aqui."
            ))
            .build();
    }
}
