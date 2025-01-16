package blizzard.development.mysterybox.mysterybox.item;

import blizzard.development.mysterybox.builder.ItemBuilder;
import blizzard.development.mysterybox.utils.PluginImpl;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;

public class MysteryBoxBuildItem {

    private static final MysteryBoxBuildItem instance = new MysteryBoxBuildItem();
    public static MysteryBoxBuildItem getInstance() {
        return instance;
    }

    public ItemStack rareMysteryBox(int amount) {
        return new ItemBuilder(Material.CHEST)
            .setDisplayName("§eCaixa Misteriosa §lRARA!")
            .setLore(Arrays.asList(
                "§7Abra esta caixa para",
                "§7conseguir itens raros.",
                "",
                " §eRecompensas",
                "  §f1x §aBooster (Estufa)",
                "  §f1x §7Skin de Pedra",
                "  §7entre outras...",
                " ",
                "§eClique para abrir."
            ))
            .addPersistentData(PluginImpl.getInstance().plugin, "caixamisteriosa-rara", "caixamisteriosa.rara")
            .build(amount);
    }

    public ItemStack legendaryMysteryBox(int amount) {
        return new ItemBuilder(Material.ENDER_CHEST)
            .setDisplayName("§dCaixa Misteriosa §lLENDÁRIA!")
            .setLore(Arrays.asList(
                "§7Abra esta caixa para",
                "§7conseguir itens lendários.",
                "",
                " §dRecompensas",
                "  §f1x §aBooster (Bosses)",
                "  §f1x §fSkin de Ferro",
                "  §7entre outras...",
                " ",
                "§dClique para abrir."
            ))
            .addPersistentData(PluginImpl.getInstance().plugin, "caixamisteriosa-lendaria", "caixamisteriosa.lendaria")
            .build(amount);
    }

    public ItemStack blizzardMysteryBox(int amount) {
        return new ItemBuilder(Material.SNOWBALL)
            .setDisplayName("§bCaixa Misteriosa §lBLIZZARD!")
            .setLore(Arrays.asList(
                "§7Abra esta caixa para",
                "§7conseguir itens lendários.",
                "",
                " §bRecompensas",
                "  §f1x §aBooster (Mina)",
                "  §f1x §6Skin de Ouro",
                "  §7entre outras...",
                " ",
                "§bClique para abrir."
            ))
            .addPersistentData(PluginImpl.getInstance().plugin, "caixamisteriosa-blizzard", "caixamisteriosa.blizzard")
            .build(amount);
    }
}
