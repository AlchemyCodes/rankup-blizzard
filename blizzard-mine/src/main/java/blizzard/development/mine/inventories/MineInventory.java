package blizzard.development.mine.inventories;

import blizzard.development.currencies.utils.CooldownUtils;
import blizzard.development.mine.builders.item.ItemBuilder;
import blizzard.development.mine.database.cache.methods.PlayerCacheMethods;
import blizzard.development.mine.mine.adapters.MineAdapter;
import com.github.stefvanschie.inventoryframework.gui.GuiItem;
import com.github.stefvanschie.inventoryframework.gui.type.ChestGui;
import com.github.stefvanschie.inventoryframework.pane.StaticPane;
import com.github.stefvanschie.inventoryframework.pane.util.Slot;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

public class MineInventory {

    public void open(Player player) {
        ChestGui inventory = new ChestGui(3, "Mina");
        StaticPane pane = new StaticPane(0, 0, 9, 3);

        PlayerCacheMethods cacheMethods = PlayerCacheMethods.getInstance();

        GuiItem go = new GuiItem(go(cacheMethods.isInMine(player)), event -> {
            event.setCancelled(true);

            CooldownUtils cooldown = CooldownUtils.getInstance();
            String cooldownName = "blizzard.mine.go-cooldown";

            if (cooldown.isInCountdown(player, cooldownName) && !player.hasPermission("blizzard.mine.cooldown-bypass")) {
                player.sendActionBar("§c§lEI! §cAguarde um pouco antes de fazer isso novamente.");
                player.closeInventory();
                return;
            }

            MineAdapter mineAdapter = MineAdapter.getInstance();

            if (cacheMethods.isInMine(player)) {
                player.closeInventory();
                mineAdapter
                        .sendToExit(
                                player
                        );
            } else {
                mineAdapter
                        .sendToMine(
                                player
                        );

                player.closeInventory();
            }

            cooldown.createCountdown(player, cooldownName, 15, TimeUnit.SECONDS);
        });

        pane.addItem(go, Slot.fromIndex(13));

        inventory.addPane(pane);
        inventory.show(player);
    }

    private ItemStack go(boolean isInMine) {
        return new ItemBuilder(isInMine ? Material.REDSTONE : Material.COMPASS)
                .setDisplayName(isInMine ? "§cSair da Mina." : "§aIr à Mina.")
                .setLore(Arrays.asList(
                        isInMine ? "§7Volte para a segurança" : "§7Minere blocos na",
                        isInMine ? "§7fora da mina." : "§7sua área de mineração.",
                        "",
                        isInMine ? "§cClique para sair." : "§aClique para ir."
                ))
                .addEnchant(org.bukkit.enchantments.Enchantment.DURABILITY, 1, true)
                .build();
    }

    private ItemStack ranking() {
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
}
