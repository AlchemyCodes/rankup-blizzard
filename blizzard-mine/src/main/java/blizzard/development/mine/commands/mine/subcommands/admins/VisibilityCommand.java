package blizzard.development.mine.commands.mine.subcommands.admins;

import blizzard.development.core.Main;
import blizzard.development.currencies.utils.CooldownUtils;
import blizzard.development.mine.database.cache.methods.PlayerCacheMethods;
import blizzard.development.mine.mine.adapters.MineAdapter;
import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.Subcommand;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

@CommandAlias("mina|mineracao|mine")
public class VisibilityCommand extends BaseCommand {

    private final PlayerCacheMethods cacheMethods = PlayerCacheMethods.getInstance();

    @Subcommand("mostrar|show")
    public void onShow(Player player) {
        for (Player players : Bukkit.getOnlinePlayers()) {
            player.showPlayer(Main.getInstance(), players);
        }
        player.sendActionBar("§a§lYAY! §aVocê ativou a visibilidade dos jogadores na mina.");
    }

    @Subcommand("esconder|hide")
    public void onHide(Player player) {
        for (Player players : Bukkit.getOnlinePlayers()) {
            player.hidePlayer(Main.getInstance(), players);
        }
        player.sendActionBar("§a§lYAY! §aVocê desativou a visibilidade dos jogadores na mina.");
    }
}
