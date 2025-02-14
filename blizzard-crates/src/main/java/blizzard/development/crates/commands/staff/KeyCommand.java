package blizzard.development.crates.commands.staff;

import blizzard.development.crates.Main;
import blizzard.development.crates.builder.ItemBuilder;
import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Subcommand;
import co.aikar.commands.annotation.Syntax;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;

import java.util.Arrays;

@CommandAlias("key|chave")
public class KeyCommand extends BaseCommand {

    @Subcommand("givekey")
    @CommandPermission("alchemy.crates.crate.key")
    @Syntax("<player> <key>")
    public void onGive(CommandSender sender, String playerTarget, String key) {


        Player target = Bukkit.getPlayer(playerTarget);
        if (target == null) {
            sender.sendActionBar(Component.text("§c§lEI! §cO jogador não existe ou está offline."));
            return;
        }

        switch (key) {
            case "comum":

                target.getInventory().addItem(new ItemBuilder(Material.TRIPWIRE_HOOK)
                        .setDisplayName("§8Chave §lComum!")
                        .setLore(Arrays.asList(
                                "§7Use esta chave para",
                                "§7abrir a caixa comum.",
                                "",
                                "§8Clique para abrir"
                        ))
                        .addUnsafeEnchantment(Enchantment.DURABILITY, 1)
                        .addItemFlags(ItemFlag.HIDE_ATTRIBUTES)
                        .addItemFlags(ItemFlag.HIDE_ENCHANTS)
                        .addPersistentData(Main.getInstance(), "chave.comum", "chave.comum")
                        .build());

                break;
            case "rara":

                target.getInventory().addItem(new ItemBuilder(Material.TRIPWIRE_HOOK)
                        .setDisplayName("§aChave §lRara!")
                        .setLore(Arrays.asList(
                                "§7Use esta chave para",
                                "§7abrir a caixa rara.",
                                "",
                                "§aClique para abrir."
                        ))
                        .addUnsafeEnchantment(Enchantment.DURABILITY, 1)
                        .addItemFlags(ItemFlag.HIDE_ATTRIBUTES)
                        .addItemFlags(ItemFlag.HIDE_ENCHANTS)
                        .addPersistentData(Main.getInstance(), "chave.rara", "chave.rara")
                        .build());

                break;
            case "mistica":

                target.getInventory().addItem(new ItemBuilder(Material.TRIPWIRE_HOOK)
                        .setDisplayName("§5Chave §lMística!")
                        .setLore(Arrays.asList(
                                "§7Use esta chave para",
                                "§7abrir a caixa mística.",
                                "",
                                "§5Clique para abrir"
                        ))
                        .addUnsafeEnchantment(Enchantment.DURABILITY, 1)
                        .addItemFlags(ItemFlag.HIDE_ATTRIBUTES)
                        .addItemFlags(ItemFlag.HIDE_ENCHANTS)
                        .addPersistentData(Main.getInstance(), "chave.mistica", "chave.mistica")
                        .build());

                break;
            case "lendaria":

                target.getInventory().addItem(new ItemBuilder(Material.TRIPWIRE_HOOK)
                        .setDisplayName("§6Chave §lLendária!")
                        .setLore(Arrays.asList(
                                "§7Use esta chave para",
                                "§7abrir a caixa lendária.",
                                "",
                                "§6Clique para abrir"
                        ))
                        .addUnsafeEnchantment(Enchantment.DURABILITY, 1)
                        .addItemFlags(ItemFlag.HIDE_ATTRIBUTES)
                        .addItemFlags(ItemFlag.HIDE_ENCHANTS)
                        .addPersistentData(Main.getInstance(), "chave.lendaria", "chave.lendaria")
                        .build());

                break;
            case "blizzard":

                target.getInventory().addItem(new ItemBuilder(Material.TRIPWIRE_HOOK)
                        .setDisplayName("§bChave §lBlizzard!")
                        .setLore(Arrays.asList(
                                "§7Use esta chave para",
                                "§7abrir a caixa blizzard.",
                                "",
                                "§bClique para abrir"
                        ))
                        .addUnsafeEnchantment(Enchantment.DURABILITY, 1)
                        .addItemFlags(ItemFlag.HIDE_ATTRIBUTES)
                        .addItemFlags(ItemFlag.HIDE_ENCHANTS)
                        .addPersistentData(Main.getInstance(), "chave.blizzard", "chave.blizzard")
                        .build());

                break;
            default:
                sender.sendMessage("");
                sender.sendMessage(" §c§lEI! §cA chave §7" + key + "§c não existe.");
                sender.sendMessage(" §cDisponíveis: §7[comum, rara, mistica, lendaria e blizzard]");
                sender.sendMessage("");
        }
    }
}
