package blizzard.development.plantations.inventories.enchantments.upgrade;

import blizzard.development.plantations.Main;
import blizzard.development.plantations.builder.ItemBuilder;
import blizzard.development.plantations.database.cache.methods.PlayerCacheMethod;
import blizzard.development.plantations.database.cache.methods.ToolCacheMethod;
import blizzard.development.plantations.inventories.enchantments.EnchantmentInventory;
import blizzard.development.plantations.utils.NumberFormat;
import com.github.stefvanschie.inventoryframework.gui.GuiItem;
import com.github.stefvanschie.inventoryframework.gui.type.ChestGui;
import com.github.stefvanschie.inventoryframework.pane.StaticPane;
import com.github.stefvanschie.inventoryframework.pane.util.Slot;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static blizzard.development.plantations.builder.ItemBuilder.getPersistentData;

public class EnchantmentUpgradeInventory {

    private final PlayerCacheMethod playerCacheMethod = PlayerCacheMethod.getInstance();
    private final ToolCacheMethod toolCacheMethod = ToolCacheMethod.getInstance();

    private int EXPLOSION_COST = 0;
    private int AGILITY_COST = 0;
    private int BOTANY_COST = 0;
    private int THUNDERSTORM_COST = 0;
    private int XRAY_COST = 0;
    private int BLIZZARD_COST = 0;

    public void open(Player player, String enchantment) {
        ChestGui inventory = new ChestGui(4, "Encantamento " + enchantment.toLowerCase());
        StaticPane pane = new StaticPane(0, 0, 9, 4);

        ItemStack item = player.getInventory().getItemInMainHand();
        String id = getPersistentData(Main.getInstance(), item, "ferramenta-id");

        int explosionEnchant = toolCacheMethod.getExplosion(id);
        int agilityEnchant = toolCacheMethod.getAgility(id);
        int botanyEnchant = toolCacheMethod.getBotany(id);
        int thunderstormEnchant = toolCacheMethod.getThunderstorm(id);
        int xrayEnchant = toolCacheMethod.getXray(id);
        int blizzardEnchant = toolCacheMethod.getBlizzard(id);

        EXPLOSION_COST = 1001;
        AGILITY_COST = 10041;
        BOTANY_COST = 100081;
        THUNDERSTORM_COST = 1000121;
        XRAY_COST = 10000161;
        BLIZZARD_COST = 1000000201;

        int seeds = playerCacheMethod.getPlantations(player);

        GuiItem level1 = new GuiItem(level1(enchantment, seeds), event -> {
            switch (enchantment) {
                case "Explosão" -> player.sendMessage("Explosão");
                case "Botânico" -> player.sendMessage("Botânico");
                case "Agilidade" -> player.sendMessage("Agilidade");
                case "Trovoada" -> player.sendMessage("Trovoada");
                case "Nevasca" -> player.sendMessage("Nevasca");
                case "Raio-X" -> player.sendMessage("Raio-X");
            }
           event.setCancelled(true);
        });

        GuiItem level5 = new GuiItem(level5(enchantment, seeds), event -> {
            switch (enchantment) {
                case "Explosão" -> player.sendMessage("Explosão");
                case "Botânico" -> player.sendMessage("Botânico");
                case "Agilidade" -> player.sendMessage("Agilidade");
                case "Trovoada" -> player.sendMessage("Trovoada");
                case "Nevasca" -> player.sendMessage("Nevasca");
                case "Raio-X" -> player.sendMessage("Raio-X");
            }
            event.setCancelled(true);
        });

        GuiItem level25 = new GuiItem(level25(enchantment, seeds), event -> {
            switch (enchantment) {
                case "Explosão" -> player.sendMessage("Explosão");
                case "Botânico" -> player.sendMessage("Botânico");
                case "Agilidade" -> player.sendMessage("Agilidade");
                case "Trovoada" -> player.sendMessage("Trovoada");
                case "Nevasca" -> player.sendMessage("Nevasca");
                case "Raio-X" -> player.sendMessage("Raio-X");
            }
            event.setCancelled(true);
        });

        GuiItem level50 = new GuiItem(level50(enchantment, seeds), event -> {
            switch (enchantment) {
                case "Explosão" -> player.sendMessage("Explosão");
                case "Botânico" -> player.sendMessage("Botânico");
                case "Agilidade" -> player.sendMessage("Agilidade");
                case "Trovoada" -> player.sendMessage("Trovoada");
                case "Nevasca" -> player.sendMessage("Nevasca");
                case "Raio-X" -> player.sendMessage("Raio-X");
            }
            event.setCancelled(true);
        });

        GuiItem level100 = new GuiItem(level100(enchantment, seeds), event -> {
            switch (enchantment) {
                case "Explosão" -> player.sendMessage("Explosão");
                case "Botânico" -> player.sendMessage("Botânico");
                case "Agilidade" -> player.sendMessage("Agilidade");
                case "Trovoada" -> player.sendMessage("Trovoada");
                case "Nevasca" -> player.sendMessage("Nevasca");
                case "Raio-X" -> player.sendMessage("Raio-X");
            }
            event.setCancelled(true);
        });

        GuiItem level500 = new GuiItem(level500(enchantment, seeds), event -> {
            switch (enchantment) {
                case "Explosão" -> player.sendMessage("Explosão");
                case "Botânico" -> player.sendMessage("Botânico");
                case "Agilidade" -> player.sendMessage("Agilidade");
                case "Trovoada" -> player.sendMessage("Trovoada");
                case "Nevasca" -> player.sendMessage("Nevasca");
                case "Raio-X" -> player.sendMessage("Raio-X");
            }
            event.setCancelled(true);
        });

        GuiItem level1000 = new GuiItem(level1000(enchantment, seeds), event -> {
            switch (enchantment) {
                case "Explosão" -> player.sendMessage("Explosão");
                case "Botânico" -> player.sendMessage("Botânico");
                case "Agilidade" -> player.sendMessage("Agilidade");
                case "Trovoada" -> player.sendMessage("Trovoada");
                case "Nevasca" -> player.sendMessage("Nevasca");
                case "Raio-X" -> player.sendMessage("Raio-X");
            }
            event.setCancelled(true);
        });

        GuiItem tool = new GuiItem(item, event -> {
            event.setCancelled(true);
        });

        GuiItem back = new GuiItem(back(), event -> {

            EnchantmentInventory enchantmentInventory = new EnchantmentInventory();
            enchantmentInventory.open(player);
            event.setCancelled(true);
        });

        pane.addItem(level1, Slot.fromIndex(10));
        pane.addItem(level5, Slot.fromIndex(11));
        pane.addItem(level25, Slot.fromIndex(12));
        pane.addItem(level50, Slot.fromIndex(13));
        pane.addItem(level100, Slot.fromIndex(14));
        pane.addItem(level500, Slot.fromIndex(15));
        pane.addItem(level1000, Slot.fromIndex(16));
        pane.addItem(tool, Slot.fromIndex(31));
        pane.addItem(back, Slot.fromIndex(27));

        inventory.addPane(pane);
        inventory.show(player);
    }

    private ItemStack level1(String enchantment, int seeds) {

        Material material = Material.GREEN_STAINED_GLASS_PANE;
        String displayName = "§a§l1 §aNível";
        List<String> lore = Arrays.asList(
            "§7Encante 1 nível",
            "§7de " + enchantment + ".",
            "",
            " §fCusto: §a✿1,000",
            "",
            "§aClique para encantar."
        );

        Map<String, Integer> enchantmentCosts = Map.of(
            "Explosão", EXPLOSION_COST,
            "Botânico", BOTANY_COST,
            "Agilidade", AGILITY_COST,
            "Trovoada", THUNDERSTORM_COST,
            "Nevasca", BLIZZARD_COST,
            "Raio-X", XRAY_COST
        );

        if (enchantmentCosts.containsKey(enchantment)) {
            int cost = enchantmentCosts.get(enchantment);
            if (cost > seeds) {
                material = Material.RED_STAINED_GLASS_PANE;
                displayName = "§c§l1 §cNível";
                lore = Arrays.asList(
                    "§7Encante 1 nível",
                    "§7de " + enchantment + ".",
                    "",
                    " §fCusto: §c✿" + NumberFormat.formatNumber(cost),
                    "",
                    "§cSementes insuficientes."
                );
            }
        }


        return new ItemBuilder(material)
            .setDisplayName(displayName)
            .setLore(lore)
            .build();
    }

    private ItemStack level5(String enchantment, int seeds) {

        Material material = Material.GREEN_STAINED_GLASS_PANE;
        String displayName = "§a§l5 §aNíveis";
        List<String> lore = Arrays.asList(
            "§7Encante 5 níveis",
            "§7de " + enchantment + ".",
            "",
            " §fCusto: §a✿5,000",
            "",
            "§aClique para encantar."
        );

        Map<String, Integer> enchantmentCosts = Map.of(
            "Explosão", EXPLOSION_COST,
            "Botânico", BOTANY_COST,
            "Agilidade", AGILITY_COST,
            "Trovoada", THUNDERSTORM_COST,
            "Nevasca", BLIZZARD_COST,
            "Raio-X", XRAY_COST
        );

        if (enchantmentCosts.containsKey(enchantment)) {
            int cost = enchantmentCosts.get(enchantment);
            if (cost > seeds) {
                material = Material.RED_STAINED_GLASS_PANE;
                displayName = "§c§l5 §cNíveis";
                lore = Arrays.asList(
                    "§7Encante 5 níveis",
                    "§7de " + enchantment + ".",
                    "",
                    " §fCusto: §c✿" + NumberFormat.formatNumber(cost),
                    "",
                    "§cSementes insuficientes."
                );
            }
        }

        return new ItemBuilder(material)
            .setDisplayName(displayName)
            .setLore(lore)
            .build();
    }

    private ItemStack level25(String enchantment, int seeds) {

        Material material = Material.GREEN_STAINED_GLASS_PANE;
        String displayName = "§a§l25 §aNíveis";
        List<String> lore = Arrays.asList(
            "§7Encante 25 níveis",
            "§7de " + enchantment + ".",
            "",
            " §fCusto: §a✿25,000",
            "",
            "§aClique para encantar."
        );

        Map<String, Integer> enchantmentCosts = Map.of(
            "Explosão", EXPLOSION_COST,
            "Botânico", BOTANY_COST,
            "Agilidade", AGILITY_COST,
            "Trovoada", THUNDERSTORM_COST,
            "Nevasca", BLIZZARD_COST,
            "Raio-X", XRAY_COST
        );

        if (enchantmentCosts.containsKey(enchantment)) {
            int cost = enchantmentCosts.get(enchantment);
            if (cost > seeds) {
                material = Material.RED_STAINED_GLASS_PANE;
                displayName = "§c§l25 §cNíveis";
                lore = Arrays.asList(
                    "§7Encante 25 níveis",
                    "§7de " + enchantment + ".",
                    "",
                    " §fCusto: §c✿" + NumberFormat.formatNumber(cost),
                    "",
                    "§cSementes insuficientes."
                );
            }
        }

        return new ItemBuilder(material)
            .setDisplayName(displayName)
            .setLore(lore)
            .build();
    }

    private ItemStack level50(String enchantment, int seeds) {

        Material material = Material.GREEN_STAINED_GLASS_PANE;
        String displayName = "§a§l50 §aNíveis";
        List<String> lore = Arrays.asList(
            "§7Encante 50 níveis",
            "§7de " + enchantment + ".",
            "",
            " §fCusto: §a✿50,000",
            "",
            "§aClique para encantar."
        );

        Map<String, Integer> enchantmentCosts = Map.of(
            "Explosão", EXPLOSION_COST,
            "Botânico", BOTANY_COST,
            "Agilidade", AGILITY_COST,
            "Trovoada", THUNDERSTORM_COST,
            "Nevasca", BLIZZARD_COST,
            "Raio-X", XRAY_COST
        );

        if (enchantmentCosts.containsKey(enchantment)) {
            int cost = enchantmentCosts.get(enchantment);
            if (cost > seeds) {
                material = Material.RED_STAINED_GLASS_PANE;
                displayName = "§c§l50 §cNíveis";
                lore = Arrays.asList(
                    "§7Encante 50 níveis",
                    "§7de " + enchantment + ".",
                    "",
                    " §fCusto: §c✿" + NumberFormat.formatNumber(cost),
                    "",
                    "§cSementes insuficientes."
                );
            }
        }

        return new ItemBuilder(material)
            .setDisplayName(displayName)
            .setLore(lore)
            .build();
    }

    private ItemStack level100(String enchantment, int seeds) {

        Material material = Material.GREEN_STAINED_GLASS_PANE;
        String displayName = "§a§l100 §aNíveis";
        List<String> lore = Arrays.asList(
            "§7Encante 100 níveis",
            "§7de " + enchantment + ".",
            "",
            " §fCusto: §a✿100,000",
            "",
            "§aClique para encantar."
        );

        Map<String, Integer> enchantmentCosts = Map.of(
            "Explosão", EXPLOSION_COST,
            "Botânico", BOTANY_COST,
            "Agilidade", AGILITY_COST,
            "Trovoada", THUNDERSTORM_COST,
            "Nevasca", BLIZZARD_COST,
            "Raio-X", XRAY_COST
        );

        if (enchantmentCosts.containsKey(enchantment)) {
            int cost = enchantmentCosts.get(enchantment);
            if (cost > seeds) {
                material = Material.RED_STAINED_GLASS_PANE;
                displayName = "§c§l100 §cNíveis";
                lore = Arrays.asList(
                    "§7Encante 100 níveis",
                    "§7de " + enchantment + ".",
                    "",
                    " §fCusto: §c✿" + NumberFormat.formatNumber(cost),
                    "",
                    "§cSementes insuficientes."
                );
            }
        }

        return new ItemBuilder(material)
            .setDisplayName(displayName)
            .setLore(lore)
            .build();
    }

    private ItemStack level500(String enchantment, int seeds) {

        Material material = Material.GREEN_STAINED_GLASS_PANE;
        String displayName = "§a§l500 §aNíveis";
        List<String> lore = Arrays.asList(
            "§7Encante 500 níveis",
            "§7de " + enchantment + ".",
            "",
            " §fCusto: §a✿500,000",
            "",
            "§aClique para encantar."
        );

        Map<String, Integer> enchantmentCosts = Map.of(
            "Explosão", EXPLOSION_COST,
            "Botânico", BOTANY_COST,
            "Agilidade", AGILITY_COST,
            "Trovoada", THUNDERSTORM_COST,
            "Nevasca", BLIZZARD_COST,
            "Raio-X", XRAY_COST
        );

        if (enchantmentCosts.containsKey(enchantment)) {
            int cost = enchantmentCosts.get(enchantment);
            if (cost > seeds) {
                material = Material.RED_STAINED_GLASS_PANE;
                displayName = "§c§l500 §cNíveis";
                lore = Arrays.asList(
                    "§7Encante 500 níveis",
                    "§7de " + enchantment + ".",
                    "",
                    " §fCusto: §c✿" + NumberFormat.formatNumber(cost),
                    "",
                    "§cSementes insuficientes."
                );
            }
        }

        return new ItemBuilder(material)
            .setDisplayName(displayName)
            .setLore(lore)
            .build();
    }

    private ItemStack level1000(String enchantment, int seeds) {

        Material material = Material.GREEN_STAINED_GLASS_PANE;
        String displayName = "§a§l1,000 §aNíveis";
        List<String> lore = Arrays.asList(
            "§7Encante 1000 níveis",
            "§7de " + enchantment + ".",
            "",
            " §fCusto: §a✿500,000",
            "",
            "§aClique para encantar."
        );

        Map<String, Integer> enchantmentCosts = Map.of(
            "Explosão", EXPLOSION_COST,
            "Botânico", BOTANY_COST,
            "Agilidade", AGILITY_COST,
            "Trovoada", THUNDERSTORM_COST,
            "Nevasca", BLIZZARD_COST,
            "Raio-X", XRAY_COST
        );

        if (enchantmentCosts.containsKey(enchantment)) {
            int cost = enchantmentCosts.get(enchantment);
            if (cost > seeds) {
                material = Material.RED_STAINED_GLASS_PANE;
                displayName = "§c§l1,000 §cNíveis";
                lore = Arrays.asList(
                    "§7Encante 1,000 níveis",
                    "§7de " + enchantment + ".",
                    "",
                    " §fCusto: §c✿" + NumberFormat.formatNumber(cost),
                    "",
                    "§cSementes insuficientes."
                );
            }
        }

        return new ItemBuilder(material)
            .setDisplayName(displayName)
            .setLore(lore)
            .build();
    }

    public static ItemStack back() {
        return new ItemBuilder(Material.REDSTONE)
            .setDisplayName("§cVoltar")
            .setLore(Arrays.asList(
                "§7Clique para voltar",
                "§7ao menu anterior."
            ))
            .build();
    }

}
