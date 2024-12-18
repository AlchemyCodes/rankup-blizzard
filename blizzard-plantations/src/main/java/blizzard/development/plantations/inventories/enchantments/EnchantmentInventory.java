package blizzard.development.plantations.inventories.enchantments;

import blizzard.development.plantations.Main;
import blizzard.development.plantations.builder.ItemBuilder;
import blizzard.development.plantations.database.cache.methods.PlayerCacheMethod;
import blizzard.development.plantations.database.cache.methods.ToolCacheMethod;
import blizzard.development.plantations.utils.TextUtils;
import com.comphenix.protocol.PacketType;
import com.github.stefvanschie.inventoryframework.gui.GuiItem;
import com.github.stefvanschie.inventoryframework.gui.type.ChestGui;
import com.github.stefvanschie.inventoryframework.pane.StaticPane;
import com.github.stefvanschie.inventoryframework.pane.util.Slot;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.List;

import static blizzard.development.plantations.builder.ItemBuilder.getPersistentData;
import static blizzard.development.plantations.utils.NumberFormat.formatNumber;

public class EnchantmentInventory {

    private final PlayerCacheMethod playerCacheMethod = PlayerCacheMethod.getInstance();
    private final ToolCacheMethod toolCacheMethod = ToolCacheMethod.getInstance();

    public void open(Player player) {
        ChestGui inventory = new ChestGui(5, "Encantamentos");
        StaticPane pane = new StaticPane(0, 0, 9, 5);

        ItemStack item = player.getInventory().getItemInMainHand();
        String id = getPersistentData(Main.getInstance(), item, "ferramenta-id");

        int explosionEnchant = toolCacheMethod.getExplosion(id);
        int agilityEnchant = toolCacheMethod.getAgility(id);
        int botanyEnchant = toolCacheMethod.getBotany(id);
        int thunderstormEnchant = toolCacheMethod.getThunderstorm(id);
        int xrayEnchant = toolCacheMethod.getXray(id);

        int explosionCost = 1000;
        int agilityCost = 1000;
        int botanyCost = 1000;
        int thunderstormCost = 1000;
        int xrayCost = 1000;

        int seeds = playerCacheMethod.getPlantations(player);

        GuiItem explosion = new GuiItem(explosion(id, player), event -> {
            if (explosionCost > seeds) {
                int subtraction = explosionCost - seeds;

                player.closeInventory();
                player.sendMessage("");
                player.sendMessage(" §c§lEI! §cVocê não possui sementes para evoluir.");
                player.sendMessage(" §cFaltam §l" + formatNumber(subtraction) + "§c sementes para executar a ação.");
                player.sendMessage("");
                return;
            }

            toolCacheMethod.setExplosion(id, explosionEnchant + 1);

            open(player);
            playerCacheMethod.setPlantations(player, seeds - explosionCost);
            player.sendActionBar("§a§lYAY! §aVocê evoluiu o encantamento ´§fExplosão§a´ com sucesso!");
           event.setCancelled(true);
        });

        GuiItem agility = new GuiItem(agility(id, player), event -> {
            if (toolCacheMethod.getAgility(id) == 2) {
                player.sendActionBar("§c§lEI! §cA ferramenta já atingiu o nível máximo desse encantamento.");
                player.closeInventory();
                return;
            }

            if (agilityCost > seeds) {
                int subtraction = agilityCost - seeds;

                player.closeInventory();
                player.sendMessage("");
                player.sendMessage(" §c§lEI! §cVocê não possui sementes para evoluir.");
                player.sendMessage(" §cFaltam §l" + formatNumber(subtraction) + "§c sementes para executar a ação.");
                player.sendMessage("");
                return;
            }

            toolCacheMethod.setAgility(id, agilityEnchant + 1);

            open(player);
            player.sendActionBar("§a§lYAY! §aVocê evoluiu o encantamento ´§fAgilidade§a´ com sucesso!");
            event.setCancelled(true);
        });

        GuiItem botany = new GuiItem(botany(id, player), event -> {
            if (botanyCost > seeds) {
                int subtraction = botanyCost - seeds;

                player.closeInventory();
                player.sendMessage("");
                player.sendMessage(" §c§lEI! §cVocê não possui sementes para evoluir.");
                player.sendMessage(" §cFaltam §l" + formatNumber(subtraction) + "§c sementes para executar a ação.");
                player.sendMessage("");
                return;
            }

            toolCacheMethod.setBotany(id, botanyEnchant + 1);

            open(player);
            player.sendActionBar("§a§lYAY! §aVocê evoluiu o encantamento ´§fBotânico§a´ com sucesso!");
            event.setCancelled(true);
        });

        GuiItem thunderstorm = new GuiItem(thunderstorm(id, player), event -> {
            if (thunderstormCost > seeds) {
                int subtraction = thunderstormCost - seeds;

                player.closeInventory();
                player.sendMessage("");
                player.sendMessage(" §c§lEI! §cVocê não possui sementes para evoluir.");
                player.sendMessage(" §cFaltam §l" + formatNumber(subtraction) + "§c sementes para executar a ação.");
                player.sendMessage("");
                return;
            }

            toolCacheMethod.setThunderstorm(id, thunderstormEnchant + 1);

            open(player);
            player.sendActionBar("§a§lYAY! §aVocê evoluiu o encantamento ´§fTrovoada§a´ com sucesso!");
            event.setCancelled(true);
        });

        GuiItem xray = new GuiItem(xray(id, player), event -> {
            if (xrayCost > seeds) {
                int subtraction = xrayCost - seeds;

                player.closeInventory();
                player.sendMessage("");
                player.sendMessage(" §c§lEI! §cVocê não possui sementes para evoluir.");
                player.sendMessage(" §cFaltam §l" + formatNumber(subtraction) + "§c sementes para executar a ação.");
                player.sendMessage("");
                return;
            }

            toolCacheMethod.setThunderstorm(id, xrayEnchant + 1);

            open(player);
            player.sendActionBar("§a§lYAY! §aVocê evoluiu o encantamento ´§fRaio-X§a´ com sucesso!");
            event.setCancelled(true);
        });

        GuiItem comingSoon = new GuiItem(comingSoon(), event -> {
           event.setCancelled(true);
        });

        GuiItem tool = new GuiItem(item, event -> {
           event.setCancelled(true);
        });

        pane.addItem(explosion, Slot.fromIndex(10));
        pane.addItem(agility, Slot.fromIndex(16));
        pane.addItem(botany, Slot.fromIndex(13));
        pane.addItem(thunderstorm, Slot.fromIndex(20));
        pane.addItem(xray, Slot.fromIndex(24));
        pane.addItem(comingSoon, Slot.fromIndex(39));
        pane.addItem(tool, Slot.fromIndex(41));

        inventory.addPane(pane);
        inventory.show(player);
    }

    private ItemStack explosion(String id, Player player) {

        Material material = Material.TNT;
        String displayName = "§cExplosão §l" + toolCacheMethod.getExplosion(id);
        List<String> lore = Arrays.asList(
            "§7Invoque uma dinamite para",
            "§7explodir sua plantação.",
            "",
            " §fChance: §c▼2%",
            " §fCusto: §a✿50K",
            "",
            "§cClique para evoluir."
        );

        if (playerCacheMethod.getPlantations(player) < 1000) {
            material = Material.RED_STAINED_GLASS_PANE;
            displayName = "§cExplosão §l" + toolCacheMethod.getExplosion(id);
            lore = Arrays.asList(
                "§7Receba efeitos de",
                "§7velocidade na estufa.",
                "",
                " §fCusto: §a✿50K",
                "",
                "§cSementes insuficientes."
            );
        }

        return new ItemBuilder(material)
            .setDisplayName(displayName)
            .setLore(lore)
            .build();
    }

    private ItemStack agility(String id, Player player) {

        Material material = Material.FEATHER;
        String displayName = "§fAgilidade §l" + toolCacheMethod.getAgility(id);
        List<String> lore = Arrays.asList(
            "§7Receba efeitos de",
            "§7velocidade na estufa.",
            "",
            " §7Custo: §a✿10K",
            "",
            "§fClique para evoluir."
        );

        if (toolCacheMethod.getAgility(id) == 2) {
            material = Material.BARRIER;
            displayName = "§cAgilidade §l2";
            lore = Arrays.asList(
                "§7Receba efeitos de",
                "§7velocidade na estufa.",
                "",
                "§cNível máximo."
            );
        }

        if (playerCacheMethod.getPlantations(player) < 1000) {
            material = Material.RED_STAINED_GLASS_PANE;
            displayName = "§cAgilidade §l" + toolCacheMethod.getAgility(id);
            lore = Arrays.asList(
                "§7Receba efeitos de",
                "§7velocidade na estufa.",
                "",
                " §7Custo: §a✿10K",
                "",
                "§cSementes insuficientes."
            );
        }

        return new ItemBuilder(material)
            .setDisplayName(displayName)
            .setLore(lore)
            .build();
    }

    private ItemStack botany(String id, Player player) {

        Material material = Material.EMERALD;
        String displayName = "§aBotânico §l" + toolCacheMethod.getBotany(id);
        List<String> lore = Arrays.asList(
            "§7Ganhe mais sementes ao",
            "§7reaproveitar as plantações.",
            "",
            " §fCusto: §a✿50K",
            " §fAproveitamento: §3❒2.2%",
            "",
            "§aClique para evoluir."
        );

        if (playerCacheMethod.getPlantations(player) < 1000) {
            material = Material.RED_STAINED_GLASS_PANE;
            displayName = "§cBotânico §l" + toolCacheMethod.getBotany(id);
            lore = Arrays.asList(
                "§7Ganhe mais sementes ao",
                "§7reaproveitar as plantações.",
                "",
                " §fCusto: §a✿50K",
                "",
                "§cSementes insuficientes."
            );
        }

        return new ItemBuilder(material)
            .setDisplayName(displayName)
            .setLore(lore)
            .build();
    }

    private ItemStack thunderstorm(String id, Player player) {

        Material material = Material.TRIDENT;
        String displayName = "<#00aaaa>Tr<#02c9c9><#02c9c9>ovo<#02c9c9><#02c9c9>ada<#00aaaa> <bold><#00aaaa>" + toolCacheMethod.getThunderstorm(id) + "<#00aaaa></bold>";
        List<String> lore = Arrays.asList(
            "§7Invoque uma trovoada para",
            "§7quebrar suas plantações.",
            "",
            " §fChance: §c▼10%",
            " §fCusto: §a✿50K",
            "",
            "<#00aaaa>Clique para evoluir.<#00aaaa>"
        );

        if (playerCacheMethod.getPlantations(player) < 1000) {
            material = Material.RED_STAINED_GLASS_PANE;
            displayName = "§cTrovoada §l" + toolCacheMethod.getThunderstorm(id);
            lore = Arrays.asList(
                "§7Invoque uma trovoada para",
                "§7quebrar suas plantações.",
                "",
                " §fCusto: §a✿50K",
                "",
                "§cSementes insuficientes."
            );
        }

        return new ItemBuilder(material)
            .setDisplayName(displayName)
            .setLore(lore)
            .build();
    }

    private ItemStack xray(String id, Player player) {

        Material material = Material.FLINT;
        String displayName = "<#555555>Ra<#737373><#737373>io<#737373><#737373>-X<#555555> <bold><#737373>" + toolCacheMethod.getXray(id) + "<#737373></bold>";
        List<String> lore = Arrays.asList(
            "§7Quebre em formato de X",
            "§7as suas plantações.",
            "",
            " §fChance: §c▼10%",
            " §fCusto: §a✿50K",
            "",
            "<#737373>Clique para evoluir.<#737373>"
        );

        if (playerCacheMethod.getPlantations(player) < 1000) {
            material = Material.RED_STAINED_GLASS_PANE;
            displayName = "§cRaio-X §l" + toolCacheMethod.getXray(id);
            lore = Arrays.asList(
                "§7Quebre em formato de X",
                "§7as suas plantações.",
                "",
                " §fCusto: §a✿50K",
                "",
                "§cSementes insuficientes."
            );
        }

        return new ItemBuilder(material)
            .setDisplayName(displayName)
            .setLore(lore)
            .build();
    }

    private ItemStack comingSoon() {
        return new ItemBuilder(Material.END_CRYSTAL)
            .setDisplayName("§dInformação!")
            .setLore(Arrays.asList(
                "§7Novos encantamentos irão",
                "§7ser revelados em breve.",
                "",
                " §fNão perca nenhuma novidade.",
                " §ddiscord.alchemynetwork.net",
                ""
            ))
            .build();
    }
}
