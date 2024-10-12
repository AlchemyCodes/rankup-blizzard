package blizzard.development.clans.commands.subcommands.staff;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.*;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import blizzard.development.clans.database.storage.ClansData;
import blizzard.development.clans.methods.ClansMethods;
import blizzard.development.clans.utils.gradient.TextUtil;

@CommandAlias("clans|clan")
public class ForceDisbandCommand extends BaseCommand {

    @Subcommand("deletar")
    @CommandPermission("legacy.clans.admin")
    @CommandCompletion("@clans")
    @Syntax("<clan>")
    public void onCommand(CommandSender sender, String clan) {

        ClansData data = ClansMethods.getClan(clan);

        if (data == null) {
            sender.sendMessage("§cO clã informado não existe!");
            return;
        }

        String clanCase = data.getClan();

        if (!clanCase.equals(clan)) {
            sender.sendMessage("§cO clã informado não existe!");
            return;
        }

        String name = data.getName();
        String tag = data.getTag();

        for (Player broadcast : Bukkit.getOnlinePlayers()) {
            broadcast.sendActionBar(
                    TextUtil.parse("<bold><#ff0000> YAY! <#ff0000></bold><#ff0000>O clan " + name + " [" + tag + "] foi deletado. <#ff5555>"));
        }

        sender.sendMessage("§aO clan §7" + name + "§a foi deletado com sucesso!");
        ClansMethods.deleteClan(clan);

    }
}
