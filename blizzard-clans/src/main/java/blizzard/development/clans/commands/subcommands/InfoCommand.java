package blizzard.development.clans.commands.subcommands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.*;
import org.bukkit.entity.Player;
import blizzard.development.clans.database.cache.ClansCacheManager;
import blizzard.development.clans.database.storage.ClansData;
import blizzard.development.clans.methods.ClansMethods;
import blizzard.development.clans.utils.NumberFormat;

import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.List;

@CommandAlias("clans|clan")
public class InfoCommand extends BaseCommand {

    @Subcommand("info")
    @CommandPermission("legacy.clans.basic")
    @CommandCompletion("@clans")
    @Syntax("<clan>")
    public void onCommand(Player player, String clan) {
        ClansData data = ClansMethods.getClan(clan);

        if (data == null) {
            player.sendMessage("§cO clã informado não existe!");
            return;
        }

        String clanCase = data.getClan();

        if (!clanCase.equals(clan)) {
            player.sendMessage("§cO clã informado não existe!");
            return;
        }

        DecimalFormat decimalFormat = new DecimalFormat("#.##");

        String date = data.getCreationDate();
        String tag = data.getTag();
        String name = data.getName();
        String owner = data.getOwner();
        int members = ClansMethods.getMembersCount(clan);
        int maxmembers = ClansCacheManager.getMaxClanMembers(clan);
        long money = data.getMoney();
        double kdr = Double.parseDouble(decimalFormat.format(data.getKdr()));

        List<String> messages = Arrays.asList(
                "",
                "§a Informações do clan §7" + name + " [" + tag + "]",
                "",
                "§7 Criação: §a" + date,
                "§7 Nome: §a" + name,
                "§7 Tag: §a" + tag,
                "§7 Dono: §a" + owner,
                "§7 Membros: §a" + members + "§7/§a" + maxmembers,
                "§7 Saldo: §2$§7" + NumberFormat.formatNumber(money),
                "§7 KDR: §a" + kdr,
                ""
        );

        for (String message : messages) {
            player.sendMessage(message);
        }

    }

}
