package blizzard.development.core.commands.clothings;

import blizzard.development.core.Main;
import blizzard.development.core.builder.ItemBuilder;

import java.util.Arrays;

import blizzard.development.core.utils.PluginImpl;
import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Default;
import co.aikar.commands.annotation.Syntax;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

@CommandAlias("manto|mantos|traje|trajes|roupa|roupas")
public class ClothingCommand extends BaseCommand {

    private final YamlConfiguration config = PluginImpl.getInstance().Config.getConfig();

    @Default
    @CommandPermission("alchemy.core.staff")
    @Syntax("<jogador> <manto> <quantia>")
    public void onCommand(CommandSender commandSender, String target, String clothing, int amount) {
        Player player = Bukkit.getPlayer(target);

        if (player == null) {
            return;
        }

        switch (clothing) {
            case "comum":
                (new ItemBuilder(Material.LEATHER_CHESTPLATE))
                        .setDisplayName("§cManto de Couro §7[Ativador]")
                        .setLore(Arrays.asList(
                                "§7Ative o manto para se",
                                "§7proteger contra o fio",
                                "",
                                " <#e8e9eb>Este manto irá fornecer<#e4e7ed>",
                                " <#e8e9eb>" + config.getInt("clothes.common.percentage") + "% de proteção ao frio.<#e4e7ed>",
                                "",
                                "§cClique para ativar."
                        ))
                        .addPersistentData(Main.getInstance(), "ativador.comum")
                        .build(player, amount);
                return;
            case "rara":
                (new ItemBuilder(Material.CHAINMAIL_CHESTPLATE))
                        .setDisplayName("<#bec4c2>Manto de Malha<#bec4c2> §7[Ativador]")
                        .setLore(Arrays.asList(
                                "§7Ative o manto para se",
                                "§7proteger contra o fio",
                                "",
                                " <#c4c4c4>Este manto irá fornecer<#bec4c2>",
                                " <#c4c4c4>" + config.getInt("clothes.rare.percentage") + "% de proteção ao frio.<#bec4c2>",
                                "",
                                "<#c4c4c4>Clique para ativar.<#c4c4c4>"
                        ))
                        .addPersistentData(Main.getInstance(), "ativador.raro")
                        .build(player, amount);
                return;
            case "mistica":
                (new ItemBuilder(Material.IRON_CHESTPLATE))
                        .setDisplayName("<#e6e3dc>Manto de Ferro<#e6e3dc> §7[Ativador]")
                        .setLore(Arrays.asList(
                                "§7Ative o manto para se",
                                "§7proteger contra o fio",
                                "",
                                " <#e6e3dc>Este manto irá fornecer<#bec4c2>",
                                " <#e6e3dc>" + config.getInt("clothes.mystic.percentage") + "% de proteção ao frio.<#bec4c2>",
                                "",
                                "<#e6e3dc>Clique para ativar.<#e6e3dc>"
                        ))
                        .addPersistentData((Plugin)Main.getInstance(), "ativador.mistico")
                        .build(player, amount);
                return;
            case "lendaria":
                (new ItemBuilder(Material.DIAMOND_CHESTPLATE))
                        .setDisplayName("§bManto de Diamante §7[Ativador]")
                        .setLore(Arrays.asList(
                                "§7Ative o manto para se",
                                "§7proteger contra o fio",
                                "",
                                " <#e6e3dc>Este manto irá fornecer<#bec4c2>",
                                " <#e6e3dc>" + config.getInt("clothes.legendary.percentage") + "% de proteção ao frio.<#bec4c2>",
                                "",
                                "§bClique para ativar."
                        ))
                        .addPersistentData(Main.getInstance(), "ativador.lendario")
                        .build(player, amount);
                return;
        }
        commandSender.sendMessage("");
        commandSender.sendMessage(" §c§lEI §cO manto §7" + clothing + "§c não existe.");
        commandSender.sendMessage(" §cDisponíveis: §7[comum, rara, mistica e lendaria]");
        commandSender.sendMessage("");
    }
}
