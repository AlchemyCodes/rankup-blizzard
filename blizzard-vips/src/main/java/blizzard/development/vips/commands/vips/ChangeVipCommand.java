package blizzard.development.vips.commands.vips;

import blizzard.development.vips.database.storage.VipData;
import blizzard.development.vips.plugin.PluginImpl;
import blizzard.development.vips.vips.adapters.VipsAdapter;
import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.Default;
import co.aikar.commands.annotation.Syntax;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

@CommandAlias("trocarvip")
public class ChangeVipCommand extends BaseCommand {

    @Default
    @Syntax("<vip>")
    public void onCommand(Player player, String vipName) {
        VipsAdapter vipsAdapter = VipsAdapter.getInstance();
        YamlConfiguration messagesConfig = PluginImpl.getInstance().Messages.getConfig();
        boolean hasVip = vipsAdapter.playerHasVip(String.valueOf(player.getUniqueId()), vipName);

        if (hasVip) {
            for (VipData vips : vipsAdapter.getPlayerVips(String.valueOf(player.getUniqueId()))) {
                if (!vips.getVipName().equals(vipName)) {
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "lp user " + player.getName() + " parent remove " + vips.getVipName());
                }
            }

            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "lp user " + player.getName() + " parent add " + vipName);
            player.sendMessage(messagesConfig.getString("commands.changeVip.changeVip").replace("{vip}", vipName));
        } else {
            player.sendMessage(messagesConfig.getString("commands.changeVip.noVip"));
        }
    }
}
