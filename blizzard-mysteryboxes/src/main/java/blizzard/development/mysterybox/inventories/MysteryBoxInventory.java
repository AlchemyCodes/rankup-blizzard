package blizzard.development.mysterybox.inventories;

import blizzard.development.mysterybox.builder.ItemBuilder;
import blizzard.development.mysterybox.managers.MysteryBoxManager;
import blizzard.development.mysterybox.mysterybox.enums.MysteryBoxEnum;
import blizzard.development.mysterybox.utils.CooldownUtils;
import blizzard.development.mysterybox.utils.PluginImpl;
import com.github.stefvanschie.inventoryframework.gui.GuiItem;
import com.github.stefvanschie.inventoryframework.gui.type.ChestGui;
import com.github.stefvanschie.inventoryframework.pane.StaticPane;
import com.github.stefvanschie.inventoryframework.pane.util.Slot;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import static blizzard.development.mysterybox.builder.ItemBuilder.hasPersistentData;

public class MysteryBoxInventory {

    public void open(Player player, Location location, ItemStack item) {
        ChestGui inventory = new ChestGui(3, "Confirma a ação?");
        StaticPane pane = new StaticPane(0, 0, 9, 3);

        GuiItem accept = new GuiItem(accept(), event -> {
            event.setCancelled(true);

            if (hasPersistentData(PluginImpl.getInstance().plugin, item, "caixamisteriosa-rara")) {
                if (CooldownUtils.getInstance().isInCountdown(player, "caixamisteriosa-rara")) {
                    player.sendActionBar("§c§lEI! §cAguarde um pouco para abrir esta caixa.");
                    return;
                }

                removeItem(
                    player
                );

                player.closeInventory();
                player.sendActionBar("§e§l§oYAY! §e§oVocê abriu uma caixa misteriosa rara.");

                MysteryBoxManager
                    .getInstance()
                    .startAnimation(
                        player,
                        MysteryBoxEnum.RARE,
                        location
                    );

                CooldownUtils.getInstance()
                    .createCountdown(
                        player,
                        "caixamisteriosa-rara",
                        1,
                        TimeUnit.SECONDS
                    );
                event.setCancelled(true);
            }

            if (hasPersistentData(PluginImpl.getInstance().plugin, item, "caixamisteriosa-lendaria")) {
                if (CooldownUtils.getInstance().isInCountdown(player, "caixamisteriosa-lendaria")) {
                    player.sendActionBar("§c§lEI! §cAguarde um pouco para abrir esta caixa.");
                    return;
                }

                removeItem(
                    player
                );

                player.closeInventory();
                player.sendActionBar("§e§l§oYAY! §e§oVocê abriu uma caixa misteriosa lendaria.");

                MysteryBoxManager
                    .getInstance()
                    .startAnimation(
                        player,
                        MysteryBoxEnum.LEGENDARY,
                        location
                    );

                CooldownUtils.getInstance()
                        .createCountdown(
                            player,
                            "caixamisteriosa-lendaria",
                            1,
                            TimeUnit.SECONDS
                        );
                event.setCancelled(true);
            }

            if (hasPersistentData(PluginImpl.getInstance().plugin, item, "caixamisteriosa-blizzard")) {
                if (CooldownUtils.getInstance().isInCountdown(player, "caixamisteriosa-blizzard")) {
                    player.sendActionBar("§c§lEI! §cAguarde um pouco para abrir esta caixa.");
                    return;
                }

                removeItem(
                    player
                );

                player.closeInventory();
                player.sendActionBar("§e§l§oYAY! §e§oVocê abriu uma caixa misteriosa blizzard.");

                MysteryBoxManager
                    .getInstance()
                    .startAnimation(
                        player,
                        MysteryBoxEnum.BLIZZARD,
                        location
                    );

                CooldownUtils.getInstance()
                    .createCountdown(
                        player,
                        "caixamisteriosa-blizzard",
                        1,
                        TimeUnit.SECONDS
                    );
                event.setCancelled(true);
            }
        });

        GuiItem instruction = new GuiItem(instruction(player), event -> event.setCancelled(true));

        GuiItem cancel = new GuiItem(cancel(), event -> {
            event.setCancelled(true);
            player.closeInventory();
        });


        pane.addItem(accept, Slot.fromIndex(10));
        pane.addItem(instruction, Slot.fromIndex(13));
        pane.addItem(cancel, Slot.fromIndex(16));

        inventory.addPane(pane);
        inventory.show(player);
    }

    private void removeItem (Player player) {
        player.getInventory().getItemInMainHand().setAmount(
            player.getInventory().getItemInMainHand().getAmount() - 1
        );
    }

    public ItemStack accept() {
        return (new ItemBuilder(Material.LIME_DYE))
            .setDisplayName("§aConfirmar ação.")
            .setLore(Arrays.asList("§7Você abrirá a sua", "§7caixa misteriosa", "", "§aClique para confirmar."))
            .build();
    }

    public ItemStack instruction(Player player) {
        return (new ItemBuilder(Material.MINECART))
            .setDisplayName("§d§lATENÇÃO! §d" + player.getName() + ".")
            .setLore(Arrays.asList("§7Confirme com cautela,", "§7a ação é irreversível."))
            .build();
    }

    public ItemStack cancel() {
        return (new ItemBuilder(Material.RED_DYE))
            .setDisplayName("§cCancelar ação.")
            .setLore(Arrays.asList("§7Você cancelará a", "§7abertura da caixa.", "", "§cClique para cancelar."))
            .build();
    }

}
