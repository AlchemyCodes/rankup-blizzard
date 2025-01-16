package blizzard.development.mine.commands.mine;

import blizzard.development.currencies.utils.CooldownUtils;
import blizzard.development.mine.database.cache.methods.PlayerCacheMethods;
import blizzard.development.mine.mine.adapters.MineAdapter;
import blizzard.development.mine.mine.enums.BlockEnum;
import blizzard.development.mine.utils.locations.LocationUtils;
import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Default;
import co.aikar.commands.annotation.Subcommand;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.concurrent.TimeUnit;

@CommandAlias("mina|mineracao|mine")
@CommandPermission("blizzard.mine.user")
public class MineCommand extends BaseCommand {

    private final CooldownUtils cooldown = CooldownUtils.getInstance();

    @Default
    public void onMineCommand(Player player) {
        player.sendActionBar("mudei isso aqui para /mina ir por enquanto");
    }

    @Subcommand("ir|go|join")
    public void onGoCommand(Player player) {
        MineAdapter
                .getInstance()
                .sendToMine(
                        player
                );
    }

    @Subcommand("sair|leave")
    public void onLeaveCommand(Player player) {
        player.sendActionBar("ainda nao");
    }

    @Subcommand("resetar|reset")
    public void onResetCommand(Player player) {
        String cooldownName = "blizzard.mine.reset-cooldown";

        if (cooldown.isInCountdown(player, cooldownName)) {
            player.sendActionBar("§c§lEI! §cAguarde um pouco antes de fazer isso novamentee.");
            return;
        }

        MineAdapter.getInstance().generateMine(player);

        player.sendActionBar("§a§lYAY! §aVocê restou sua mina com sucesso.");
        cooldown.createCountdown(player, cooldownName, 3, TimeUnit.MINUTES);
    }
}
