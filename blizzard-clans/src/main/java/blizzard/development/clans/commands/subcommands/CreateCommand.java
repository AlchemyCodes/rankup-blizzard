package blizzard.development.clans.commands.subcommands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.*;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import blizzard.development.clans.methods.ClansMethods;
import blizzard.development.clans.utils.PluginImpl;
import blizzard.development.clans.utils.gradient.TextUtil;
import blizzard.development.clans.managers.EconomyManager;

@CommandAlias("clans|clan")
public class CreateCommand extends BaseCommand {

    @Subcommand("criar")
    @CommandPermission("legacy.clans.basic")
    @Syntax("<tag> <nome>")
    public void onCommand(Player player, String tag, String name) {

        String userClan = ClansMethods.getUserClan(player);

        if (userClan != null) {
            player.sendMessage("§cVocê já está em um clan!");
            return;
        }

        if (tag.length() != 3) {
            player.sendMessage("§cA tag do seu clan deve ter somente 3 caracteres!");
        } else if (name.length() > 10) {
            player.sendMessage("§cO nome do seu clan deve ter no máximo 10 caracteres!");
        } else if (ClansMethods.isClanTagBlacklisted(tag)) {
            player.sendMessage("§cA tag informada não pode ser utilizada!");
        } else if (tag.contains("&") || name.contains("&")) {
            player.sendMessage("§cA tag e o nome do seu clan não podem conter o caractere §7'&'§c!");
        } else if (ClansMethods.isClanNameBlacklisted(name)) {
            player.sendMessage("§cO nome informado não pode ser utilizado!");
        } else if (!ClansMethods.isClanTagAvailable(tag) || !ClansMethods.isClanTagUnique(tag)) {
            player.sendMessage("§cJá existe um clan com a tag " + tag);
        } else if (!ClansMethods.isClanNameAvailable(name) || !ClansMethods.isClanNameUnique(name)) {
            player.sendMessage("§cJá existe um clan com o nome " + name);
        } else {

            double price = PluginImpl.getInstance().Config.getDouble("clans.create-price");

            if (EconomyManager.hasEnough(player, price)) {
                EconomyManager.withdraw(player, price);
                ClansMethods.createClan(player, tag, name);

                for (Player broadcast : Bukkit.getOnlinePlayers()) {
                    broadcast.sendActionBar(
                            TextUtil.parse("<bold><#04ff00> YAY! <#04ff00></bold><#04ff00>O clan " + name + " [" + tag + "] foi criado. <#55ff55>"));
                }

                player.sendMessage("§aVocê criou o clan §7" + name + "§a e gastou §2$§7" + price);
            } else {
                player.sendMessage("§cVocê precisa de §2$§7" + price + "§c para criar um clan!");
            }
        }
    }
}
