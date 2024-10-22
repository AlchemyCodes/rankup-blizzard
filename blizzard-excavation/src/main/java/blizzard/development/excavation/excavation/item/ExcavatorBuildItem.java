package blizzard.development.excavation.excavation.item;

import blizzard.development.excavation.Main;
import blizzard.development.excavation.builder.ItemBuilder;
import blizzard.development.excavation.database.cache.methods.ExcavatorCacheMethod;
import blizzard.development.excavation.database.cache.methods.PlayerCacheMethod;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;


public class ExcavatorBuildItem {

    private final ExcavatorCacheMethod excavatorCacheMethod = new ExcavatorCacheMethod();
    private final PlayerCacheMethod playerCacheMethod = new PlayerCacheMethod();

    public ItemStack buildExcavator(Player player) {
        ExcavatorCacheMethod.createExcavator(player);

        int blocksBroken = playerCacheMethod.getBlocks(player);
        int efficiencyLevel = excavatorCacheMethod.effiencyEnchant(player.getName());
        int agilityLevel = excavatorCacheMethod.agilityEnchant(player.getName());
        int extractorLevel = excavatorCacheMethod.extractorEnchant(player.getName());

        return new ItemBuilder(Material.DIAMOND_SHOVEL)
                .setDisplayName("<#34f2f5>Escavadora<#34f2f5> §7[§l" + blocksBroken + "§7]")
                .setLore(Arrays.asList(
                        "§7Utilize esta escavadora",
                        "§7para coletar recursos.",
                        "",
                        " <#1bf4f7>Encantamentos:<#0ec9cc>",
                        "  §7Eficiência §l" + efficiencyLevel,
                        "  §7Durabilidade §l∞",
                        "  §7Agilidade §l" + agilityLevel,
                        "  §7Extrator §l" + extractorLevel,
                        "",
                        " §bOs encantamentos são",
                        " §bupados automaticamente.",
                        ""
                ))
                .addEnchant(Enchantment.DIG_SPEED, excavatorCacheMethod.effiencyEnchant(player.getName()), true)
                .addPersistentData(Main.getInstance(), "excavator.tool", "excavator")
                .addPersistentData(Main.getInstance(), "excavator.nickname", "nickname")
                .build();

    }
}
