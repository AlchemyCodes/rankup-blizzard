package blizzard.development.plantations.inventories;

import blizzard.development.plantations.builder.ItemBuilder;
import blizzard.development.plantations.database.cache.methods.PlayerCacheMethod;
import blizzard.development.plantations.inventories.ranking.RankingInventory;
import com.github.stefvanschie.inventoryframework.gui.GuiItem;
import com.github.stefvanschie.inventoryframework.gui.type.ChestGui;
import com.github.stefvanschie.inventoryframework.pane.StaticPane;
import com.github.stefvanschie.inventoryframework.pane.util.Slot;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;

public class FarmInventory {

    private final PlayerCacheMethod playerCacheMethod = new PlayerCacheMethod();

    public void open(Player player) {
        ChestGui inventory = new ChestGui(3, "Estufa");
        StaticPane pane = new StaticPane(0, 0, 9, 3);

        boolean isInPlantation = playerCacheMethod.isInPlantation(player);

        GuiItem go = new GuiItem(go(isInPlantation), event -> {
            if (!isInPlantation) {
                player.closeInventory();

                player.sendTitle(
                        "§a§lEstufa!",
                        "§aVocê entrou na estufa.",
                        10,
                        70,
                        20
                );
                playerCacheMethod.setInPlantation(player);
            } else {

                player.teleport(Bukkit.getWorld("spawn2").getSpawnLocation());
                player.sendTitle(
                        "§c§lEstufa!",
                        "§cVocê saiu da estufa.",
                        10,
                        70,
                        20
                );

                playerCacheMethod.removeInPlantation(player);
            }
            event.setCancelled(true);
        });

        GuiItem classification = new GuiItem(classification(), event -> {
            RankingInventory rankingInventory = new RankingInventory();
            rankingInventory.open(player);

            event.setCancelled(true);
        });


        pane.addItem(go, Slot.fromIndex(13));
        pane.addItem(classification, Slot.fromIndex(16));

        inventory.addPane(pane);
        inventory.show(player);
    }


    private ItemStack go(boolean isInPlantation) {
        return new ItemBuilder(isInPlantation ? Material.REDSTONE : Material.SLIME_BALL)
                .setDisplayName(isInPlantation ? "§cSair da Estufa." : "§aIr à Estufa.")
                .setLore(Arrays.asList(
                        isInPlantation ? "§7Volte para a segurança" : "§7Plante e colha frutos",
                        isInPlantation ? "§7fora da estufa." : "§7na sua área de estufa.",
                        "",
                        isInPlantation ? "§cClique para sair." : "§aClique para ir."
                ))
                .addEnchant(Enchantment.DURABILITY, 1, true)
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
