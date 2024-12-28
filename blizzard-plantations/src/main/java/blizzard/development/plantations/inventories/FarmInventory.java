package blizzard.development.plantations.inventories;

import blizzard.development.plantations.Main;
import blizzard.development.plantations.api.CoreAPI;
import blizzard.development.plantations.builder.ItemBuilder;
import blizzard.development.plantations.database.cache.methods.PlayerCacheMethod;
import blizzard.development.plantations.inventories.manager.AreaUpgradeInventory;
import blizzard.development.plantations.inventories.ranking.RankingSeedsInventory;
import blizzard.development.plantations.inventories.shop.ShopInventory;
import blizzard.development.plantations.managers.AreaManager;
import blizzard.development.plantations.managers.PlantationManager;
import blizzard.development.plantations.managers.upgrades.agility.AgilityManager;
import blizzard.development.plantations.plantations.adapters.AreaAdapter;
import blizzard.development.plantations.plantations.adapters.ToolAdapter;
import blizzard.development.plantations.utils.CooldownUtils;
import blizzard.development.plantations.utils.LocationUtils;
import com.github.stefvanschie.inventoryframework.gui.GuiItem;
import com.github.stefvanschie.inventoryframework.gui.type.ChestGui;
import com.github.stefvanschie.inventoryframework.pane.StaticPane;
import com.github.stefvanschie.inventoryframework.pane.util.Slot;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.title.Title;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import java.time.Duration;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import static blizzard.development.plantations.builder.ItemBuilder.getPersistentData;

public class FarmInventory {


    public void open(Player player) {
        ChestGui inventory = new ChestGui(3, "Estufa");
        StaticPane pane = new StaticPane(0, 0, 9, 3);

        ItemStack item = player.getInventory().getItemInMainHand();

        Location location = LocationUtils.getPlantationSpawnLocation();
        Location spawn = LocationUtils.getSpawnLocation();

        if (location == null) {
            player.sendActionBar("§c§lEI! §cO spawn da estufa ainda não foi setado.");
            return;
        }

        if (spawn == null) {
            return;
        }

        GuiItem go = new GuiItem(go(PlayerCacheMethod.getInstance().isInPlantation(player)), event -> {
            event.setCancelled(true);

            if (PlayerCacheMethod.getInstance().isInPlantation(player)) {
                player.closeInventory();

                player.teleport(spawn);
                player.sendTitle("§c§lEstufa!", "§cVocê saiu da estufa.", 10, 70, 20);

                for (Player players : Bukkit.getOnlinePlayers()) {
                    players.showPlayer(Main.getInstance(), player);
                    player.showPlayer(Main.getInstance(), players);
                }

                PlayerCacheMethod.
                    getInstance()
                    .removeInPlantation(player);

                AgilityManager.clearEffect(player);
            } else {
                player.closeInventory();

                AreaAdapter.getInstance().teleportToArea(player);
            }
        });

        GuiItem shop = new GuiItem(shop(), event ->  {
            ShopInventory shopInventory = new ShopInventory();
            shopInventory.open(player);

            event.setCancelled(true);
        });

        GuiItem manager = new GuiItem(manager(), event -> {
            AreaUpgradeInventory areaUpgradeInventory = new AreaUpgradeInventory();
            areaUpgradeInventory.open(player);
            event.setCancelled(true);
        });

        GuiItem tool = new GuiItem(tool(), event -> {
            if (CooldownUtils.getInstance().isInCountdown(player, "tool-cooldown")) {
                player.showTitle(
                    Title.title(
                        Component.text("§c§lEI!"),
                        Component.text("§cAguarde um pouco para resgatar."),
                        Title.Times.times(Duration.ZERO, Duration.ofSeconds(2), Duration.ofSeconds(1))
                    )
                );
                event.setCancelled(true);
                return;
            }

            ToolAdapter toolAdapter = new ToolAdapter();
            toolAdapter.giveTool(player);

            CooldownUtils.getInstance().createCountdown(player, "tool-cooldown", 3, TimeUnit.SECONDS);

            player.closeInventory();
            player.showTitle(
                Title.title(
                    Component.text("§a§lFerramenta resgatada!"),
                    Component.text("§7Você recebeu uma cultivadora."),
                    Title.Times.times(Duration.ZERO, Duration.ofSeconds(2), Duration.ofSeconds(1))
                )
            );
           event.setCancelled(true);
        });

        GuiItem ranking = new GuiItem(ranking(), event -> {
            new RankingSeedsInventory().open(player);
            event.setCancelled(true);
        });

        pane.addItem(shop, Slot.fromIndex(11));
        pane.addItem(go, Slot.fromIndex(13));
        pane.addItem(manager, Slot.fromIndex(15));
        pane.addItem(tool, Slot.fromIndex(16));
        pane.addItem(ranking, Slot.fromIndex(10));

        inventory.addPane(pane);
        inventory.show(player);
    }

    private ItemStack go(boolean isInPlantation) {
        return new ItemBuilder(isInPlantation ? Material.REDSTONE : Material.COMPASS)
            .setDisplayName(isInPlantation ? "§cSair da Estufa." : "§aIr à Estufa.")
            .setLore(Arrays.asList(
                isInPlantation ? "§7Volte para a segurança" : "§7Colha plantações",
                isInPlantation ? "§7fora da estufa." : "§7na sua área de estufa.",
                "",
                isInPlantation ? "§cClique para sair." : "§aClique para ir."
            ))
            .addEnchant(org.bukkit.enchantments.Enchantment.DURABILITY, 1, true)
            .build();
    }

    private ItemStack manager() {
        return new ItemBuilder(Material.END_CRYSTAL)
            .setDisplayName("<#FF55FF>Geren<#fa7dfa><#fa7dfa>ciador da<#fa7dfa> <#fa7dfa>Estufa<#FF55FF>")
            .setLore(Arrays.asList(
                "§7Torne sua área mais",
                "§7eficiente com upgrades.",
                "",
                "§dClique para visualizar."
            ))
            .build();
    }

    private ItemStack tool() {
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

    private ItemStack shop() {
        return new ItemBuilder(Material.TNT_MINECART)
            .setDisplayName("§4Loja de Bosses")
            .setLore(Arrays.asList(
                "§7Troque suas sementes",
                "§7por ovos de bosses.",
                "",
                "§4Clique para visualizar."
            ))
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
