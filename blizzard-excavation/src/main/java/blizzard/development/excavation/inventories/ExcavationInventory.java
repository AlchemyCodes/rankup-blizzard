package blizzard.development.excavation.inventories;

import blizzard.development.excavation.builder.ItemBuilder;
import blizzard.development.excavation.database.cache.methods.ExcavatorCacheMethod;
import blizzard.development.excavation.database.cache.methods.PlayerCacheMethod;
import blizzard.development.excavation.inventories.shop.ShopInventory;
import blizzard.development.excavation.utils.LocationUtils;
import com.github.stefvanschie.inventoryframework.gui.GuiItem;
import com.github.stefvanschie.inventoryframework.gui.type.ChestGui;
import com.github.stefvanschie.inventoryframework.pane.StaticPane;
import com.github.stefvanschie.inventoryframework.pane.util.Slot;
import eu.decentsoftware.holograms.api.utils.scheduler.S;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;

public class ExcavationInventory {

    public void open(Player player) {
        ChestGui inventory = new ChestGui(3, "§8Escavação");
        StaticPane pane = new StaticPane(0, 0, 9, 3);

        PlayerCacheMethod playerCacheMethod = new PlayerCacheMethod();
        ExcavatorCacheMethod excavatorCacheMethod = new ExcavatorCacheMethod();


        GuiItem enchantments = new GuiItem(enchantments(), event -> {
            EnchantmentInventory enchantmentInventory = new EnchantmentInventory();
            enchantmentInventory.open(player, excavatorCacheMethod);

            event.setCancelled(true);
        });

        GuiItem shop = new GuiItem(shop(), event -> {
            ShopInventory shopInventory = new ShopInventory();
            shopInventory.open(player);

            event.setCancelled(true);
        });


        GuiItem go = new GuiItem(go(playerCacheMethod.isInExcavation(player)), event -> {

            if (!playerCacheMethod.isInExcavation(player)) {
                LocationUtils locationUtils = new LocationUtils();
                player.teleport(locationUtils.excavationLocation());

                player.sendTitle(
                        "§b§lEscavação!",
                        "§bVocê entrou na escavação.",
                        10,
                        70,
                        20
                );
                playerCacheMethod.setInExcavation(player);
            } else {

                player.teleport(player.getWorld().getSpawnLocation());
                player.sendTitle(
                        "§c§lEscavação!",
                        "§cVocê saiu da escavação.",
                        10,
                        70,
                        20
                );

                playerCacheMethod.removeInExcavation(player);
            }

            event.setCancelled(true);
        });

        GuiItem information = new GuiItem(information(), event -> {
            event.setCancelled(true);
        });

        GuiItem classification = new GuiItem(classification(), event -> {
            event.setCancelled(true);
        });

        pane.addItem(enchantments, Slot.fromIndex(10));
        pane.addItem(shop, Slot.fromIndex(11));
        pane.addItem(go, Slot.fromIndex(13));
        pane.addItem(information, Slot.fromIndex(15));
        pane.addItem(classification, Slot.fromIndex(16));

        inventory.addPane(pane);
        inventory.show(player);
    }

    private ItemStack enchantments() {
        return new ItemBuilder(Material.END_CRYSTAL)
                .setDisplayName("§dEncantamentos.")
                .setLore(Arrays.asList(
                        "§7Visualize os encantamentos",
                        "§7que sua escavadora possui.",
                        "",
                        " §fOs encantamentos são",
                        " §fupados automaticamente.",
                        "",
                        "§dClique para visualizar."
                ))
                .build();
    }

    private ItemStack shop() {
        return new ItemBuilder(Material.FIRE_CHARGE)
                .setDisplayName("§6Loja de Itens.")
                .setLore(Arrays.asList(
                        "§7Troque seus fósseis",
                        "§7por itens especiais.",
                        "",
                        "§6Clique para visualizar."
                ))
                .build();
    }
    private ItemStack go(boolean isInExcavation) {
        return new ItemBuilder(isInExcavation ? Material.REDSTONE : Material.COMPASS)
                .setDisplayName(isInExcavation ? "§cSair da Escavação." : "§bIr à Escavação.")
                .setLore(Arrays.asList(
                        isInExcavation ? "§7Volte para a segurança" : "§7Escave grandes terrenos",
                        isInExcavation ? "§7fora da escavação." : "§7à busca de fósseis antigos.",
                        "",
                        isInExcavation ? "§cClique para sair." : "§bClique para ir."
                ))
                .addEnchant(Enchantment.DURABILITY, 1, true)
                .build();
    }


    private ItemStack information() {
        return new ItemBuilder(Material.BONE)
                .setDisplayName("§fInformações.")
                .setLore(Arrays.asList(
                        "§7Confira agora todas as",
                        "§7informações sobre a escavação.",
                        "",
                        " §f§lFÓSSEIS",
                        " <#e6e3dc>Ao escavar, ganhará fósseis<#e6e3dc>",
                        " <#e6e3dc>de antigos animais e bosses.<#e6e3dc>",
                        " §7(Use-os para trocar na loja)",
                        "",
                        " §f§lAMBIENTAÇÂO",
                        " <#e6e3dc>Durante a escavação, poderá<#e6e3dc>",
                        " <#e6e3dc>acontecer tempestades de neve<#e6e3dc>",
                        ""
                ))
                .build();
    }

    private ItemStack classification() {
        return new ItemBuilder(Material.GOLD_INGOT)
                .setDisplayName("§eClassificação.")
                .setLore(Arrays.asList(
                        "§7Confira agora os jogadores",
                        "§7que mais se destacam.",
                        "",
                        "§eClique para visualizar."
                ))
                .build();
    }
}
