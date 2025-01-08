package blizzard.development.plantations.inventories.enchantments.upgrade;

import blizzard.development.plantations.Main;
import blizzard.development.plantations.builder.ItemBuilder;
import blizzard.development.plantations.database.cache.methods.PlayerCacheMethod;
import blizzard.development.plantations.database.cache.methods.ToolCacheMethod;
import blizzard.development.plantations.inventories.enchantments.EnchantmentInventory;
import blizzard.development.plantations.utils.MessageUtils;
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
import static blizzard.development.plantations.utils.NumberFormat.formatNumber;

public class EnchantmentUpgradeInventory {

    private final PlayerCacheMethod playerCacheMethod = PlayerCacheMethod.getInstance();
    private final ToolCacheMethod toolCacheMethod = ToolCacheMethod.getInstance();

    private final int EXPLOSION_COST = 10;
    private final int AGILITY_COST = 10;
    private final int BOTANY_COST = 10;
    private final int THUNDERSTORM_COST = 10;
    private final int XRAY_COST = 10;
    private final int BLIZZARD_COST = 10;

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

        int seeds = playerCacheMethod.getPlantations(player);

        GuiItem level1 = new GuiItem(level1(enchantment, seeds), event -> {
            switch (enchantment) {
                case "Explosão":
                    if (EXPLOSION_COST > seeds) {
                        int subtraction = EXPLOSION_COST - seeds;
                        MessageUtils.insufficientSeeds(player, subtraction);
                        return;
                    }
                    toolCacheMethod.setExplosion(id, explosionEnchant + 1);
                    break;
                case "Botânico":
                    if (BOTANY_COST > seeds) {
                        int subtraction = BOTANY_COST - seeds;
                        MessageUtils.insufficientSeeds(player, subtraction);
                        return;
                    }
                    toolCacheMethod.setBotany(id, botanyEnchant + 1);
                    break;
                case "Agilidade":
                    if (AGILITY_COST > seeds) {
                        int subtraction = AGILITY_COST - seeds;
                        MessageUtils.insufficientSeeds(player, subtraction);
                        return;
                    }
                    toolCacheMethod.setAgility(id, agilityEnchant + 1);
                    break;
                case "Trovoada":
                    if (THUNDERSTORM_COST > seeds) {
                        int subtraction = THUNDERSTORM_COST - seeds;
                        MessageUtils.insufficientSeeds(player, subtraction);
                        return;
                    }
                    toolCacheMethod.setThunderstorm(id, thunderstormEnchant + 1);
                    break;
                case "Nevasca":
                    if (BLIZZARD_COST > seeds) {
                        int subtraction = BLIZZARD_COST - seeds;
                        MessageUtils.insufficientSeeds(player, subtraction);
                        return;
                    }
                    toolCacheMethod.setBlizzard(id, blizzardEnchant + 1);
                    break;
                case "Raio-X":
                    if (XRAY_COST > seeds) {
                        int subtraction = XRAY_COST - seeds;
                        MessageUtils.insufficientSeeds(player, subtraction);
                        return;
                    }
                    toolCacheMethod.setXray(id, xrayEnchant + 1);
                    break;
            }
            event.setCancelled(true);
        });

        GuiItem level5 = new GuiItem(level5(enchantment, seeds), event -> {
            switch (enchantment) {
                case "Explosão":
                    if (EXPLOSION_COST * 5 > seeds) {
                        int subtraction = (EXPLOSION_COST * 5) - seeds;
                        MessageUtils.insufficientSeeds(player, subtraction);
                        return;
                    }
                    toolCacheMethod.setExplosion(id, explosionEnchant + 5);
                    break;
                case "Botânico":
                    if (BOTANY_COST * 5 > seeds) {
                        int subtraction = (BOTANY_COST * 5) - seeds;
                        MessageUtils.insufficientSeeds(player, subtraction);
                        return;
                    }
                    toolCacheMethod.setBotany(id, botanyEnchant + 5);
                    break;
                case "Agilidade":
                    if (AGILITY_COST * 5 > seeds) {
                        int subtraction = (AGILITY_COST * 5) - seeds;
                        MessageUtils.insufficientSeeds(player, subtraction);
                        return;
                    }
                    toolCacheMethod.setAgility(id, agilityEnchant + 5);
                    break;
                case "Trovoada":
                    if (THUNDERSTORM_COST * 5 > seeds) {
                        int subtraction = (THUNDERSTORM_COST * 5) - seeds;
                        MessageUtils.insufficientSeeds(player, subtraction);
                        return;
                    }
                    toolCacheMethod.setThunderstorm(id, thunderstormEnchant + 5);
                    break;
                case "Nevasca":
                    if (BLIZZARD_COST * 5 > seeds) {
                        int subtraction = (BLIZZARD_COST * 5) - seeds;
                        MessageUtils.insufficientSeeds(player, subtraction);
                        return;
                    }
                    toolCacheMethod.setBlizzard(id, blizzardEnchant + 5);
                    break;
                case "Raio-X":
                    if (XRAY_COST * 5 > seeds) {
                        int subtraction = (XRAY_COST * 5) - seeds;
                        MessageUtils.insufficientSeeds(player, subtraction);
                        return;
                    }
                    toolCacheMethod.setXray(id, xrayEnchant + 5);
                    break;
            }
            event.setCancelled(true);
        });

        GuiItem level25 = new GuiItem(level25(enchantment, seeds), event -> {
            switch (enchantment) {
                case "Explosão":
                    if (EXPLOSION_COST * 25 > seeds) {
                        int subtraction = (EXPLOSION_COST * 25) - seeds;
                        MessageUtils.insufficientSeeds(player, subtraction);
                        return;
                    }
                    toolCacheMethod.setExplosion(id, explosionEnchant + 25);
                    break;
                case "Botânico":
                    if (BOTANY_COST * 25 > seeds) {
                        int subtraction = (BOTANY_COST * 25) - seeds;
                        MessageUtils.insufficientSeeds(player, subtraction);
                        return;
                    }
                    toolCacheMethod.setBotany(id, botanyEnchant + 25);
                    break;
                case "Agilidade":
                    if (AGILITY_COST * 25 > seeds) {
                        int subtraction = (AGILITY_COST * 25) - seeds;
                        MessageUtils.insufficientSeeds(player, subtraction);
                        return;
                    }
                    toolCacheMethod.setAgility(id, agilityEnchant + 25);
                    break;
                case "Trovoada":
                    if (THUNDERSTORM_COST * 25 > seeds) {
                        int subtraction = (THUNDERSTORM_COST * 25) - seeds;
                        MessageUtils.insufficientSeeds(player, subtraction);
                        return;
                    }
                    toolCacheMethod.setThunderstorm(id, thunderstormEnchant + 25);
                    break;
                case "Nevasca":
                    if (BLIZZARD_COST * 25 > seeds) {
                        int subtraction = (BLIZZARD_COST * 25) - seeds;
                        MessageUtils.insufficientSeeds(player, subtraction);
                        return;
                    }
                    toolCacheMethod.setBlizzard(id, blizzardEnchant + 25);
                    break;
                case "Raio-X":
                    if (XRAY_COST * 25 > seeds) {
                        int subtraction = (XRAY_COST * 25) - seeds;
                        MessageUtils.insufficientSeeds(player, subtraction);
                        return;
                    }
                    toolCacheMethod.setXray(id, xrayEnchant + 25);
                    break;
            }
            event.setCancelled(true);
        });

        GuiItem level50 = new GuiItem(level50(enchantment, seeds), event -> {
            switch (enchantment) {
                case "Explosão":
                    if (EXPLOSION_COST * 50 > seeds) {
                        int subtraction = (EXPLOSION_COST * 50) - seeds;
                        MessageUtils.insufficientSeeds(player, subtraction);
                        return;
                    }
                    toolCacheMethod.setExplosion(id, explosionEnchant + 50);
                    break;
                case "Botânico":
                    if (BOTANY_COST * 50 > seeds) {
                        int subtraction = (BOTANY_COST * 50) - seeds;
                        MessageUtils.insufficientSeeds(player, subtraction);
                        return;
                    }
                    toolCacheMethod.setBotany(id, botanyEnchant + 50);
                    break;
                case "Agilidade":
                    if (AGILITY_COST * 50 > seeds) {
                        int subtraction = (AGILITY_COST * 50) - seeds;
                        MessageUtils.insufficientSeeds(player, subtraction);
                        return;
                    }
                    toolCacheMethod.setAgility(id, agilityEnchant + 50);
                    break;
                case "Trovoada":
                    if (THUNDERSTORM_COST * 50 > seeds) {
                        int subtraction = (THUNDERSTORM_COST * 50) - seeds;
                        MessageUtils.insufficientSeeds(player, subtraction);
                        return;
                    }
                    toolCacheMethod.setThunderstorm(id, thunderstormEnchant + 50);
                    break;
                case "Nevasca":
                    if (BLIZZARD_COST * 50 > seeds) {
                        int subtraction = (BLIZZARD_COST * 50) - seeds;
                        MessageUtils.insufficientSeeds(player, subtraction);
                        return;
                    }
                    toolCacheMethod.setBlizzard(id, blizzardEnchant + 50);
                    break;
                case "Raio-X":
                    if (XRAY_COST * 50 > seeds) {
                        int subtraction = (XRAY_COST * 50) - seeds;
                        MessageUtils.insufficientSeeds(player, subtraction);
                        return;
                    }
                    toolCacheMethod.setXray(id, xrayEnchant + 50);
                    break;
            }
            event.setCancelled(true);
        });

        GuiItem level100 = new GuiItem(level100(enchantment, seeds), event -> {
            switch (enchantment) {
                case "Explosão":
                    if (EXPLOSION_COST * 100 > seeds) {
                        int subtraction = (EXPLOSION_COST * 100) - seeds;
                        MessageUtils.insufficientSeeds(player, subtraction);
                        return;
                    }
                    open(player, "Explosão");
                    player.sendMessage("antes " + explosionEnchant);
                    toolCacheMethod.setExplosion(id, explosionEnchant + 100);
                    player.sendMessage("dps " + explosionEnchant);
                    break;
                case "Botânico":
                    if (BOTANY_COST * 100 > seeds) {
                        int subtraction = (BOTANY_COST * 100) - seeds;
                        MessageUtils.insufficientSeeds(player, subtraction);
                        return;
                    }
                    toolCacheMethod.setBotany(id, botanyEnchant + 100);
                    break;
                case "Agilidade":
                    if (AGILITY_COST * 100 > seeds) {
                        int subtraction = (AGILITY_COST * 100) - seeds;
                        MessageUtils.insufficientSeeds(player, subtraction);
                        return;
                    }
                    toolCacheMethod.setAgility(id, agilityEnchant + 100);
                    break;
                case "Trovoada":
                    if (THUNDERSTORM_COST * 100 > seeds) {
                        int subtraction = (THUNDERSTORM_COST * 100) - seeds;
                        MessageUtils.insufficientSeeds(player, subtraction);
                        return;
                    }
                    toolCacheMethod.setThunderstorm(id, thunderstormEnchant + 100);
                    break;
                case "Nevasca":
                    if (BLIZZARD_COST * 100 > seeds) {
                        int subtraction = (BLIZZARD_COST * 100) - seeds;
                        MessageUtils.insufficientSeeds(player, subtraction);
                        return;
                    }
                    toolCacheMethod.setBlizzard(id, blizzardEnchant + 100);
                    break;
                case "Raio-X":
                    if (XRAY_COST * 100 > seeds) {
                        int subtraction = (XRAY_COST * 100) - seeds;
                        MessageUtils.insufficientSeeds(player, subtraction);
                        return;
                    }
                    toolCacheMethod.setXray(id, xrayEnchant + 100);
                    break;
            }
            event.setCancelled(true);
        });

        GuiItem level500 = new GuiItem(level500(enchantment, seeds), event -> {
            switch (enchantment) {
                case "Explosão":
                    if (EXPLOSION_COST * 500 > seeds) {
                        int subtraction = (EXPLOSION_COST * 500) - seeds;
                        MessageUtils.insufficientSeeds(player, subtraction);
                        return;
                    }
                    toolCacheMethod.setExplosion(id, explosionEnchant + 500);
                    break;
                case "Botânico":
                    if (BOTANY_COST * 500 > seeds) {
                        int subtraction = (BOTANY_COST * 500) - seeds;
                        MessageUtils.insufficientSeeds(player, subtraction);
                        return;
                    }
                    toolCacheMethod.setBotany(id, botanyEnchant + 500);
                    break;
                case "Agilidade":
                    if (AGILITY_COST * 500 > seeds) {
                        int subtraction = (AGILITY_COST * 500) - seeds;
                        MessageUtils.insufficientSeeds(player, subtraction);
                        return;
                    }
                    toolCacheMethod.setAgility(id, agilityEnchant + 500);
                    break;
                case "Trovoada":
                    if (THUNDERSTORM_COST * 500 > seeds) {
                        int subtraction = (THUNDERSTORM_COST * 500) - seeds;
                        MessageUtils.insufficientSeeds(player, subtraction);
                        return;
                    }
                    toolCacheMethod.setThunderstorm(id, thunderstormEnchant + 500);
                    break;
                case "Nevasca":
                    if (BLIZZARD_COST * 500 > seeds) {
                        int subtraction = (BLIZZARD_COST * 500) - seeds;
                        MessageUtils.insufficientSeeds(player, subtraction);
                        return;
                    }
                    toolCacheMethod.setBlizzard(id, blizzardEnchant + 500);
                    break;
                case "Raio-X":
                    if (XRAY_COST * 500 > seeds) {
                        int subtraction = (XRAY_COST * 500) - seeds;
                        MessageUtils.insufficientSeeds(player, subtraction);
                        return;
                    }
                    toolCacheMethod.setXray(id, xrayEnchant + 500);
                    break;
            }
            event.setCancelled(true);
        });

        GuiItem level1000 = new GuiItem(level1000(enchantment, seeds), event -> {
            switch (enchantment) {
                case "Explosão":
                    if (EXPLOSION_COST * 1000 > seeds) {
                        int subtraction = (EXPLOSION_COST * 1000) - seeds;
                        MessageUtils.insufficientSeeds(player, subtraction);
                        return;
                    }
                    toolCacheMethod.setExplosion(id, explosionEnchant + 1000);
                    open(player, "Explosão");
                    break;
                case "Botânico":
                    if (BOTANY_COST * 1000 > seeds) {
                        int subtraction = (BOTANY_COST * 1000) - seeds;
                        MessageUtils.insufficientSeeds(player, subtraction);
                        return;
                    }
                    toolCacheMethod.setBotany(id, botanyEnchant + 1000);
                    open(player, "Botânico");
                    break;
                case "Agilidade":
                    if (AGILITY_COST * 1000 > seeds) {
                        int subtraction = (AGILITY_COST * 1000) - seeds;
                        MessageUtils.insufficientSeeds(player, subtraction);
                        return;
                    }
                    toolCacheMethod.setAgility(id, agilityEnchant + 1000);
                    open(player, "Agilidade");
                    break;
                case "Trovoada":
                    if (THUNDERSTORM_COST * 1000 > seeds) {
                        int subtraction = (THUNDERSTORM_COST * 1000) - seeds;
                        MessageUtils.insufficientSeeds(player, subtraction);
                        return;
                    }
                    toolCacheMethod.setThunderstorm(id, thunderstormEnchant + 1000);
                    open(player, "Trovoada");
                    break;
                case "Nevasca":
                    if (BLIZZARD_COST * 1000 > seeds) {
                        int subtraction = (BLIZZARD_COST * 1000) - seeds;
                        MessageUtils.insufficientSeeds(player, subtraction);
                        return;
                    }
                    toolCacheMethod.setBlizzard(id, blizzardEnchant + 1000);
                    open(player, "Nevasca");
                    break;
                case "Raio-X":
                    if (XRAY_COST * 1000 > seeds) {
                        int subtraction = (XRAY_COST * 1000) - seeds;
                        MessageUtils.insufficientSeeds(player, subtraction);
                        return;
                    }
                    toolCacheMethod.setXray(id, xrayEnchant + 1000);
                    open(player, "Raio-X");
                    break;
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

    private final  Map<String, Integer> enchantmentCosts = Map.of(
        "Explosão", EXPLOSION_COST,
        "Botânico", BOTANY_COST,
        "Agilidade", AGILITY_COST,
        "Trovoada", THUNDERSTORM_COST,
        "Nevasca", BLIZZARD_COST,
        "Raio-X", XRAY_COST
    );

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

        if (enchantmentCosts.containsKey(enchantment)) {
            int cost = enchantmentCosts.get(enchantment);
            if (cost > seeds) {
                material = Material.RED_STAINED_GLASS_PANE;
                displayName = "§c§l1 §cNível";
                lore = Arrays.asList(
                    "§7Encante 1 nível",
                    "§7de " + enchantment + ".",
                    "",
                    " §fCusto: §c✿" + formatNumber(cost),
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

        if (enchantmentCosts.containsKey(enchantment)) {
            int cost = enchantmentCosts.get(enchantment);
            if (cost * 5 > seeds) {
                material = Material.RED_STAINED_GLASS_PANE;
                displayName = "§c§l5 §cNíveis";
                lore = Arrays.asList(
                    "§7Encante 5 níveis",
                    "§7de " + enchantment + ".",
                    "",
                    " §fCusto: §c✿" + formatNumber(cost),
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

        if (enchantmentCosts.containsKey(enchantment)) {
            int cost = enchantmentCosts.get(enchantment);
            if (cost * 25 > seeds) {
                material = Material.RED_STAINED_GLASS_PANE;
                displayName = "§c§l25 §cNíveis";
                lore = Arrays.asList(
                    "§7Encante 25 níveis",
                    "§7de " + enchantment + ".",
                    "",
                    " §fCusto: §c✿" + formatNumber(cost),
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

        if (enchantmentCosts.containsKey(enchantment)) {
            int cost = enchantmentCosts.get(enchantment);
            if (cost * 50 > seeds) {
                material = Material.RED_STAINED_GLASS_PANE;
                displayName = "§c§l50 §cNíveis";
                lore = Arrays.asList(
                    "§7Encante 50 níveis",
                    "§7de " + enchantment + ".",
                    "",
                    " §fCusto: §c✿" + formatNumber(cost),
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

        if (enchantmentCosts.containsKey(enchantment)) {
            int cost = enchantmentCosts.get(enchantment);
            if (cost * 100 > seeds) {
                material = Material.RED_STAINED_GLASS_PANE;
                displayName = "§c§l100 §cNíveis";
                lore = Arrays.asList(
                    "§7Encante 100 níveis",
                    "§7de " + enchantment + ".",
                    "",
                    " §fCusto: §c✿" + formatNumber(cost),
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

        if (enchantmentCosts.containsKey(enchantment)) {
            int cost = enchantmentCosts.get(enchantment);
            if (cost * 500 > seeds) {
                material = Material.RED_STAINED_GLASS_PANE;
                displayName = "§c§l500 §cNíveis";
                lore = Arrays.asList(
                    "§7Encante 500 níveis",
                    "§7de " + enchantment + ".",
                    "",
                    " §fCusto: §c✿" + formatNumber(cost),
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
        int cost = enchantmentCosts.get(enchantment);

        Material material = Material.GREEN_STAINED_GLASS_PANE;
        String displayName = "§a§l1,000 §aNíveis";
        List<String> lore = Arrays.asList(
            "§7Encante 1000 níveis",
            "§7de " + enchantment + ".",
            "",
            " §fCusto: §a✿" + NumberFormat.format(cost * 1000),
            "",
            "§aClique para encantar."
        );

        if (enchantmentCosts.containsKey(enchantment)) {
            if (cost * 1000 > seeds) {
                material = Material.RED_STAINED_GLASS_PANE;
                displayName = "§c§l1,000 §cNíveis";
                lore = Arrays.asList(
                    "§7Encante 1,000 níveis",
                    "§7de " + enchantment + ".",
                    "",
                    " §fCusto: §c✿" + formatNumber(cost),
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
