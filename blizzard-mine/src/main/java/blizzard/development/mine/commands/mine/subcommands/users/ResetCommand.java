package blizzard.development.mine.commands.mine.subcommands.users;

import blizzard.development.core.Main;
import blizzard.development.currencies.utils.CooldownUtils;
import blizzard.development.mine.database.cache.methods.PlayerCacheMethods;
import blizzard.development.mine.mine.adapters.MineAdapter;
import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Subcommand;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.concurrent.TimeUnit;

@CommandAlias("mina|mineracao|mine")
@CommandPermission("blizzard.mine.user")
public class ResetCommand extends BaseCommand {

    @Subcommand("resetar|reset")
    public void onResetCommand(Player player) {
        PlayerCacheMethods cacheMethods = PlayerCacheMethods.getInstance();

        CooldownUtils cooldown = CooldownUtils.getInstance();
        String cooldownName = "blizzard.mine.reset-cooldown";

        if (cacheMethods.isInMine(player)) {
            if (cooldown.isInCountdown(player, cooldownName) && !player.hasPermission("blizzard.mine.cooldown-bypass")) {
                player.sendActionBar("§c§lEI! §cAguarde um pouco antes de fazer isso novamente.");
                return;
            }

            MineAdapter.getInstance().resetMine(player);

            cooldown.createCountdown(player, cooldownName, 5, TimeUnit.MINUTES);
        } else {
            player.sendActionBar("§c§lEI! §cVocê não está na mina.");
        }
    }
}
