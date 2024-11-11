package blizzard.development.plantations.inventories;

import blizzard.development.plantations.api.WorldAPI;
import blizzard.development.plantations.builder.ItemBuilder;
import blizzard.development.plantations.database.cache.methods.PlayerCacheMethod;
import blizzard.development.plantations.inventories.ranking.RankingInventory;
import com.github.stefvanschie.inventoryframework.gui.GuiItem;
import com.github.stefvanschie.inventoryframework.gui.type.ChestGui;
import com.github.stefvanschie.inventoryframework.pane.StaticPane;
import com.github.stefvanschie.inventoryframework.pane.util.Slot;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;

public class FarmInventory {

    private final PlayerCacheMethod playerCacheMethod = new PlayerCacheMethod();

    public void open(Player player) {
        ChestGui inventory = new ChestGui(3, "Estufa");
        StaticPane pane = new StaticPane(0, 0, 9, 3);

        boolean isInPlantation = playerCacheMethod.isInPlantation(player);

        GuiItem go = new GuiItem(createGoItem(isInPlantation), event -> {

            WorldAPI worldAPI = new WorldAPI();
            World world = Bukkit.getWorld("mundo_" + player.getUniqueId());

            if (world == null) {
                player.closeInventory();
                worldAPI.createWorld(player);
                playerCacheMethod.setInPlantation(player);
            } else if (isInPlantation) {
                player.closeInventory();
                World spawn = Bukkit.getWorld("spawn2");
                player.teleport(spawn.getSpawnLocation());
                player.sendTitle("§c§lEstufa!", "§cVocê saiu da estufa.", 10, 70, 20);
                playerCacheMethod.removeInPlantation(player);
            } else {
                player.closeInventory();
                player.sendTitle("§a§lEstufa!", "§aVocê entrou na estufa.", 10, 70, 20);
                worldAPI.teleportToWorld(player);
                playerCacheMethod.setInPlantation(player);
            }

            event.setCancelled(true);

        });

        pane.addItem(new GuiItem(createShopItem(), event -> event.setCancelled(true)), Slot.fromIndex(11));
        pane.addItem(go, Slot.fromIndex(13));
        pane.addItem(new GuiItem(createPlowingItem(), event -> event.setCancelled(true)), Slot.fromIndex(15));
        pane.addItem(new GuiItem(createToolItem(), event -> event.setCancelled(true)), Slot.fromIndex(16));
        pane.addItem(new GuiItem(createClassificationItem(), event -> {
            new RankingInventory().open(player);
            event.setCancelled(true);
        }), Slot.fromIndex(10));

        inventory.addPane(pane);
        inventory.show(player);
    }

    private ItemStack createGoItem(boolean isInPlantation) {
        return new ItemBuilder(isInPlantation ? Material.REDSTONE : Material.SLIME_BALL)
                .setDisplayName(isInPlantation ? "§cSair da Estufa." : "§aIr à Estufa.")
                .setLore(Arrays.asList(
                        isInPlantation ? "§7Volte para a segurança" : "§7Plante e colha frutos",
                        isInPlantation ? "§7fora da estufa." : "§7na sua área de estufa.",
                        "",
                        isInPlantation ? "§cClique para sair." : "§aClique para ir."
                ))
                .addEnchant(org.bukkit.enchantments.Enchantment.DURABILITY, 1, true)
                .build();
    }

    private ItemStack createPlowingItem() {
        return new ItemBuilder(Material.WOODEN_HOE)
                .setDisplayName("<#a88459>Ferramenta de Arar<#a88459>")
                .setLore(Arrays.asList(
                        "§7Use esta ferramenta para",
                        "§7arar o solo na sua estufa.",
                        "",
                        " <#a88459>Encantamentos:<#a88459>",
                        "  §7Durabilidade §l∞",
                        "  §7Acelerador §l0",
                        "  §7Arador §l0",
                        "",
                        "<#a88459>Clique para resgatar.<#a88459>"
                ))
                .build();
    }

    private ItemStack createToolItem() {
        return new ItemBuilder(Material.GOLDEN_HOE)
                .setDisplayName("§6Ferramenta de Cultivo")
                .setLore(Arrays.asList(
                        "§7Use esta ferramenta para",
                        "§7cultivar plantações.",
                        "",
                        " §6Encantamentos:",
                        "  §7Durabilidade §l∞",
                        "  §7Agilidade §l0",
                        "  §7Botânico §l0",
                        "  §7Explosão §l0",
                        "",
                        "§6Clique para resgatar."
                ))
                .build();
    }

    private ItemStack createShopItem() {
        return new ItemBuilder(Material.TNT_MINECART)
                .setDisplayName("§4Loja de Bosses.")
                .setLore(Arrays.asList(
                        "§7Troque suas sementes",
                        "§7por ovos de bosses.",
                        "",
                        "§4Clique para visualizar."
                ))
                .build();
    }

    private ItemStack createClassificationItem() {
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
