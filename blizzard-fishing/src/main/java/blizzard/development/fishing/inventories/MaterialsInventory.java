package blizzard.development.fishing.inventories;

import blizzard.development.fishing.database.cache.methods.PlayersCacheMethod;
import blizzard.development.fishing.database.cache.methods.RodsCacheMethod;
import blizzard.development.fishing.database.storage.PlayersData;
import blizzard.development.fishing.database.storage.RodsData;
import blizzard.development.fishing.enums.RodMaterials;
import blizzard.development.fishing.utils.PluginImpl;
import blizzard.development.fishing.utils.items.ItemBuilder;
import com.github.stefvanschie.inventoryframework.gui.GuiItem;
import com.github.stefvanschie.inventoryframework.gui.type.ChestGui;
import com.github.stefvanschie.inventoryframework.pane.StaticPane;
import com.github.stefvanschie.inventoryframework.pane.util.Slot;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.List;

public class MaterialsInventory {
    public static void materialsMenu(Player player) {
        ChestGui gui = new ChestGui(6, "Materiais da Vara");

        StaticPane pane = new StaticPane(0, 0, 9, 6);

        GuiItem woodMaterialActivated = new GuiItem(woodActivated(), event -> event.setCancelled(true));
        GuiItem ironMaterialActivated = new GuiItem(ironActivated(), event -> event.setCancelled(true));
        GuiItem carbonMaterialActivated = new GuiItem(carbonActivated(), event -> event.setCancelled(true));
        GuiItem noneMaterialActived = new GuiItem(noneActivated(), event -> event.setCancelled(true));

        RodsCacheMethod rodsData = RodsCacheMethod.getInstance();

        List<RodMaterials> materials = rodsData.getMaterials(player);

        GuiItem back = new GuiItem(back(), event -> { event.setCancelled(true);
            FishingInventory.openFishing(player);});

        GuiItem bambuMaterial = new GuiItem(bambooMaterial(), event -> { event.setCancelled(true);
            if (materials.equals(RodMaterials.BAMBOO)) return;

            rodsData.addMaterial(player, RodMaterials.BAMBOO);
            player.sendMessage("§b§lYAY! §fVocê ativou o material bambu!");
            player.getOpenInventory().setItem(32, noneMaterialActived.getItem());
            player.playSound(player.getLocation(), Sound.BLOCK_AMETHYST_BLOCK_CHIME, 1,1);});

        GuiItem woodMaterial = new GuiItem(woodMaterial(), event -> { event.setCancelled(true);
            if (materials.equals(RodMaterials.WOOD)) return;

            if (!(materials.contains(RodMaterials.WOOD))) {
                player.sendMessage("§cVocê não tem esse material.");
                player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 1 ,1);
                return;
            }
            rodsData.addMaterial(player, RodMaterials.WOOD);
            player.sendMessage("§b§lYAY! §fVocê ativou o material madeira!");
            player.getOpenInventory().setItem(32, woodMaterialActivated.getItem());
            player.playSound(player.getLocation(), Sound.BLOCK_AMETHYST_BLOCK_CHIME, 1,1);});

        GuiItem ironMaterial = new GuiItem(ironMaterial(), event -> { event.setCancelled(true);
            if (materials.equals(RodMaterials.IRON)) return;

            if (!(materials.contains(RodMaterials.IRON))) {
                player.sendMessage("§cVocê não tem esse material.");
                player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 1 ,1);
                return;
            }
            rodsData.addMaterial(player, RodMaterials.IRON);
            player.sendMessage("§b§lYAY! §fVocê ativou o material ferro!");
            player.getOpenInventory().setItem(32, ironMaterialActivated.getItem());
            player.playSound(player.getLocation(), Sound.BLOCK_AMETHYST_BLOCK_CHIME, 1,1);});

        GuiItem carbonMaterial = new GuiItem(carbonMaterial(), event -> { event.setCancelled(true);
            if (materials.equals(RodMaterials.CARBON)) return;

            if (!(materials.contains(RodMaterials.CARBON))) {
                player.sendMessage("§cVocê não tem esse material.");
                player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 1 ,1);
                return;
            }
            rodsData.addMaterial(player, RodMaterials.CARBON);
            player.sendMessage("§b§lYAY! §fVocê ativou o material carbono!");
            player.getOpenInventory().setItem(32, carbonMaterialActivated.getItem());
            player.playSound(player.getLocation(), Sound.BLOCK_AMETHYST_BLOCK_CHIME, 1,1);});

        GuiItem rod = new GuiItem(rod(player), event -> {
            event.setCancelled(true);
        });


        if (materials.contains(RodMaterials.CARBON)) {
            pane.addItem(carbonMaterialActivated, Slot.fromIndex(32));
        } else if (materials.contains(RodMaterials.IRON)) {
            pane.addItem(ironMaterialActivated, Slot.fromIndex(32));
        } else if (materials.contains(RodMaterials.WOOD)) {
            pane.addItem(woodMaterialActivated, Slot.fromIndex(32));
        } else {
            pane.addItem(noneMaterialActived, Slot.fromIndex(32));
        }

        pane.addItem(bambuMaterial, Slot.fromIndex(10));
        pane.addItem(woodMaterial, Slot.fromIndex(12));
        pane.addItem(ironMaterial, Slot.fromIndex(14));
        pane.addItem(carbonMaterial, Slot.fromIndex(16));
        pane.addItem(rod, Slot.fromIndex(30));
        pane.addItem(back, Slot.fromIndex(45));


        gui.addPane(pane);

        gui.show(player);
    }

    public static ItemStack back() {
        ItemStack back = new ItemStack(Material.RED_DYE);
        ItemMeta meta = back.getItemMeta();
        meta.setDisplayName("§cVoltar");
        meta.setLore(Arrays.asList(
                "§7Clique para voltar",
                "§7ao menu anterior."
        ));

        back.setItemMeta(meta);

        return back;
    }

    public static ItemStack bambooMaterial() {
        ItemStack wood = new ItemStack(Material.BAMBOO);
        ItemMeta meta = wood.getItemMeta();
        meta.setDisplayName("§6Bambu");
        meta.setLore(Arrays.asList(
                "§7Esse é o material",
                "§7da sua primeira vara",
                "",
                "§7▶  §fBônus: §6Nenhum",
                "",
                "§6Clique para ativar."
        ));

        wood.setItemMeta(meta);

        return wood;
    }

    public static ItemStack woodMaterial() {
        ItemStack wood = new ItemStack(Material.STICK);
        ItemMeta meta = wood.getItemMeta();
        meta.setDisplayName("§6Madeira");
        meta.setLore(Arrays.asList(
                "§7Ative essa skin para obter",
                "§7bônus no ganho de experiência.",
                "",
                "§7▶  §fBônus: §610%",
                "",
                "§6Clique para ativar."
        ));

        wood.setItemMeta(meta);

        return wood;
    }

    public static ItemStack ironMaterial() {
        ItemStack iron = new ItemStack(Material.IRON_INGOT);
        ItemMeta meta = iron.getItemMeta();
        meta.setDisplayName("§6Ferro");
        meta.setLore(Arrays.asList(
                "§7Ative essa skin para obter",
                "§7bônus no ganho de experiência.",
                "",
                "§7▶  §fBônus: §620%",
                "",
                "§6Clique para ativar."
        ));

        iron.setItemMeta(meta);

        return iron;
    }

    public static ItemStack carbonMaterial() {
        ItemStack carbon = new ItemStack(Material.COAL);
        ItemMeta meta = carbon.getItemMeta();
        meta.setDisplayName("§6Carbono");
        meta.setLore(Arrays.asList(
                "§7Ative essa skin para obter",
                "§7bônus no ganho de experiência.",
                "",
                "§7▶  §fBônus: §630%",
                "",
                "§6Clique para ativar."
        ));

        carbon.setItemMeta(meta);

        return carbon;
    }

    public static ItemStack woodActivated() {
        ItemStack wood = new ItemStack(Material.STICK);
        ItemMeta meta = wood.getItemMeta();
        meta.setDisplayName("§6Madeira");
        meta.setLore(Arrays.asList(
                "§fEsse material está ativado!",
                "",
                "§7▶  §fBônus: §610%"
        ));

        wood.setItemMeta(meta);

        return wood;
    }

    public static ItemStack ironActivated() {
        ItemStack iron = new ItemStack(Material.IRON_INGOT);
        ItemMeta meta = iron.getItemMeta();
        meta.setDisplayName("§6Ferro");
        meta.setLore(Arrays.asList(
                "§fEsse material está ativado!",
                "",
                "§7▶  §fBônus: §620%"
        ));

        iron.setItemMeta(meta);

        return iron;
    }
    public static ItemStack carbonActivated() {
        ItemStack carbon = new ItemStack(Material.COAL);
        ItemMeta meta = carbon.getItemMeta();
        meta.setDisplayName("§6Carbono");
        meta.setLore(Arrays.asList(
                "§fEsse material está ativado!",
                "",
                "§7▶  §fBônus: §630%"
        ));

        carbon.setItemMeta(meta);

        return carbon;
    }

    public static ItemStack noneActivated() {
        ItemStack carbon = new ItemStack(Material.BAMBOO);
        ItemMeta meta = carbon.getItemMeta();
        meta.setDisplayName("§6Bambu");
        meta.setLore(Arrays.asList(
                "§fVocê está com a vara",
                "principal ativa."
        ));

        carbon.setItemMeta(meta);

        return carbon;
    }

    public static ItemStack rod(Player player) {
        RodsCacheMethod instance = RodsCacheMethod.getInstance();

        return new ItemBuilder(Material.FISHING_ROD)
                .setDisplayName("§6Vara de Pesca")
                .setLore(Arrays.asList(
                        "§7Utilize esse vara para",
                        "§7pescar uma varidade de peixes.",
                        "",
                        " §6Encantamentos:",
                        "  §7XXXXXXXX §l{rod_enchant_xxxxxx}",
                        "  §7Experiente §l" + instance.getExperienced(player),
                        "  §7Sortudo §l" + instance.getLucky(player),
                        "",
                        "  §8▶ §fForça: §7" + instance.getStrength(player),
                        "  §8■ §fMaterial: §7" + instance.getBestMaterial(player).getFancyName(),
                        "")).build();
    }


}
