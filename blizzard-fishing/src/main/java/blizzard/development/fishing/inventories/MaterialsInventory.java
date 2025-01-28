package blizzard.development.fishing.inventories;

import blizzard.development.fishing.database.cache.methods.RodsCacheMethod;
import blizzard.development.fishing.enums.RodMaterials;
import blizzard.development.fishing.handlers.FishBucketHandler;
import blizzard.development.fishing.handlers.FishingRodHandler;
import blizzard.development.fishing.utils.PluginImpl;
import blizzard.development.fishing.utils.fish.FishesUtils;
import blizzard.development.fishing.utils.items.ItemBuilder;
import com.github.stefvanschie.inventoryframework.gui.GuiItem;
import com.github.stefvanschie.inventoryframework.gui.type.ChestGui;
import com.github.stefvanschie.inventoryframework.pane.StaticPane;
import com.github.stefvanschie.inventoryframework.pane.util.Slot;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.List;

public class MaterialsInventory {
    public static void materialsMenu(Player player) {
        ChestGui gui = new ChestGui(6, "Materiais da Vara");
        StaticPane pane = new StaticPane(0, 0, 9, 6);

        RodsCacheMethod rodsData = RodsCacheMethod.getInstance();
        FishesUtils fishesUtils = FishesUtils.getInstance();
        List<RodMaterials> materials = rodsData.getMaterials(player);

        addMaterialButtons(player, pane, fishesUtils, materials);

        pane.addItem(new GuiItem(createBackItem(), event -> {
            event.setCancelled(true);

            FishingInventory.openFishing(player);
        }), Slot.fromIndex(45));

        pane.addItem(new GuiItem(FishingRodHandler.getRod(player, RodsCacheMethod.getInstance(), PluginImpl.getInstance().plugin),
                event -> event.setCancelled(true)), Slot.fromIndex(30));

        gui.addPane(pane);
        gui.show(player);
    }

    private static void addMaterialButtons(Player player, StaticPane pane, FishesUtils fishesUtils, List<RodMaterials> materials) {
        GuiItem activatedItem = createActivatedItem(player, fishesUtils);
        pane.addItem(activatedItem, Slot.fromIndex(32));

        for (RodMaterials material : RodMaterials.values()) {
            GuiItem materialItem = createMaterialItem(player, fishesUtils, materials, material);
            pane.addItem(materialItem, Slot.fromIndex(getSlotForMaterial(material)));
        }
    }

    private static GuiItem createMaterialItem(Player player, FishesUtils fishesUtils, List<RodMaterials> materials, RodMaterials material) {
        ItemStack item = createMaterialStack(material);
        return new GuiItem(item, event -> {
            event.setCancelled(true);

            if (fishesUtils.getActiveSkin(material)) {
                player.sendMessage("§cEsse material já está ativado.");
                return;
            }

            if (!materials.contains(material)) {
                player.sendMessage("§cVocê não possui esse material.");
                player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 1, 1);
                return;
            }

            fishesUtils.setActiveSkin(player, material);
            player.sendMessage("§b§lYAY! §fVocê ativou o material " + material.getFancyName() + "!");
            player.playSound(player.getLocation(), Sound.BLOCK_AMETHYST_BLOCK_CHIME, 1, 1);
            materialsMenu(player);
        });
    }

    private static GuiItem createActivatedItem(Player player, FishesUtils fishesUtils) {
        RodMaterials activeMaterial = fishesUtils.getPlayerMaterial(player);
        ItemStack activatedStack = createActivatedMaterial(activeMaterial);
        return new GuiItem(activatedStack, event ->  {
            event.setCancelled(true);
            player.sendMessage(String.valueOf(fishesUtils.getActiveSkin(activeMaterial)));
        });
    }

    private static int getSlotForMaterial(RodMaterials material) {
        return switch (material) {
            case BAMBOO -> 10;
            case WOOD -> 12;
            case IRON -> 14;
            case CARBON -> 16;
        };
    }

    private static ItemStack createBackItem() {
        return new ItemBuilder(Material.RED_DYE)
                .setDisplayName("§cVoltar")
                .setLore(List.of(
                        "§7Clique para voltar",
                        "§7ao menu anterior."))
                .build();
    }

    private static ItemStack createMaterialStack(RodMaterials material) {
        return switch (material) {
            case BAMBOO -> new ItemBuilder(material.getMaterial())
                    .setDisplayName("§6" + material.getFancyName())
                    .setLore(List.of(
                            "§7Material inicial",
                            "§7Sem bônus.")).build();

            case WOOD -> new ItemBuilder(material.getMaterial())
                    .setDisplayName("§6" + material.getFancyName())
                    .setLore(List.of("§7Bônus: §610%")).build();

            case IRON -> new ItemBuilder(material.getMaterial())
                    .setDisplayName("§6" + material.getFancyName())
                    .setLore(List.of("§7Bônus: §620%")).build();

            case CARBON -> new ItemBuilder(material.getMaterial())
                    .setDisplayName("§6" + material.getFancyName())
                    .setLore(List.of("§7Bônus: §630%")).build();
        };
    }


    private static ItemStack createActivatedMaterial(RodMaterials material) {
        return new ItemBuilder(material.getMaterial())
                .setDisplayName("§6" + material.getFancyName())
                .setLore(List.of("§fEsse material está ativado!"))
                .build();

        }
    }

