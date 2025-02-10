package blizzard.development.mine.inventories.management;

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
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

public class ManagementInventory {

    public void open(Player player) {
        ChestGui inventory = new ChestGui(3, "Mina - Gerenciamento");
        StaticPane pane = new StaticPane(0, 0, 9, 3);

        GuiItem profile = new GuiItem(createProfileItem(player), event -> event.setCancelled(true));

        GuiItem reset = new GuiItem(createResetItem(), event -> {
            event.setCancelled(true);
            player.getInventory().close();

            PlayerCacheMethods cacheMethods = PlayerCacheMethods.getInstance();

            CooldownUtils cooldown = CooldownUtils.getInstance();
            String cooldownName = "blizzard.mine.reset-cooldown";

            if (cacheMethods.isInMine(player)) {
                if (cooldown.isInCountdown(player, cooldownName) && !player.hasPermission("blizzard.mine.cooldown-bypass")) {
                    player.sendActionBar("§c§lEI! §cAguarde um pouco antes de fazer isso novamente.");
                    return;
                }

                MineAdapter.getInstance().resetMine(player);

                cooldown.createCountdown(player, cooldownName, 5, TimeUnit.MINUTES);
            } else {
                player.sendActionBar("§c§lEI! §cVocê não está na mina.");
            }
        });

        GuiItem missions = new GuiItem(createMissionItem(), event -> {
            event.setCancelled(true);
        });

        GuiItem preferences = new GuiItem(createPrefsItem(), event -> {
            event.setCancelled(true);
        });

        pane.addItem(profile, Slot.fromIndex(10));
        pane.addItem(reset, Slot.fromIndex(12));
        pane.addItem(missions, Slot.fromIndex(14));
        pane.addItem(preferences, Slot.fromIndex(16));

        inventory.addPane(pane);
        inventory.show(player);
    }

    private ItemStack createProfileItem(Player player) {
        return new ItemBuilder(player)
                .setDisplayName("§ePerfil")
                .setLore(Arrays.asList(
                        "§7Aqui tem q ficar tudo, tipo, nivel, tema e etc"
                ))
                .addItemFlags(ItemFlag.HIDE_UNBREAKABLE, ItemFlag.HIDE_ITEM_SPECIFICS, ItemFlag.HIDE_ATTRIBUTES)
                .build();
    }

    private ItemStack createResetItem() {
        return new ItemBuilder(Material.STONE)
                .setDisplayName("§8Resetar mina")
                .setLore(Arrays.asList(
                        "§7Resete os blocos de sua",
                        "§7área de mineração.",
                        "",
                        "§8Clique para resetar."
                ))
                .build();
    }

    private ItemStack createMissionItem() {
        return new ItemBuilder(Material.WRITABLE_BOOK)
                .setDisplayName("§aMissões")
                .setLore(Arrays.asList(
                        "§7Complete missões e",
                        "§7receba recompensas.",
                        "",
                        "§aClique para visualizar."
                ))
                .build();
    }

    private ItemStack createPrefsItem() {
        return new ItemBuilder(Material.REPEATER)
                .setDisplayName("§cPreferências")
                .setLore(Arrays.asList(
                        "§7Gerencia suas preferências",
                        "§7na área de mineração.",
                        "",
                        "§cClique para gerenciar."
                ))
                .build();
    }
}
