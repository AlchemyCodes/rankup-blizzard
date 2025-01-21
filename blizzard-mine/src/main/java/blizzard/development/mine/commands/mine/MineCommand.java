package blizzard.development.mine.commands.mine;

import blizzard.development.currencies.utils.CooldownUtils;
import blizzard.development.mine.database.cache.methods.PlayerCacheMethods;
import blizzard.development.mine.inventories.MineInventory;
import blizzard.development.mine.mine.adapters.MineAdapter;
import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Default;
import co.aikar.commands.annotation.Subcommand;
import org.bukkit.entity.Player;

import java.util.concurrent.TimeUnit;

@CommandAlias("mina|mineracao|mine")
@CommandPermission("blizzard.mine.user")
public class MineCommand extends BaseCommand {

    private final CooldownUtils cooldown = CooldownUtils.getInstance();

    private final MineAdapter mineAdapter = MineAdapter.getInstance();
    private final PlayerCacheMethods cacheMethods = PlayerCacheMethods.getInstance();

    @Default
    public void onMineCommand(Player player) {
        String cooldownName = "blizzard.mine.go-cooldown";

        if (!cacheMethods.isInMine(player)) {
            if (cooldown.isInCountdown(player, cooldownName) && !player.hasPermission("blizzard.mine.cooldown-bypass")) {
                player.sendActionBar("§c§lEI! §cAguarde um pouco antes de fazer isso novamente.");
                return;
            }

            mineAdapter
                .sendToMine(
                    player
                );

            cooldown.createCountdown(player, cooldownName, 15, TimeUnit.SECONDS);
        } else {
            player.sendActionBar("§c§lEI! §cVocê já está na mina.");
        }
    }

//    @Subcommand("ir|go|join")
//    public void onGoCommand(Player player) {
//        String cooldownName = "blizzard.mine.go-cooldown";
//
//        if (!cacheMethods.isInMine(player)) {
//            if (cooldown.isInCountdown(player, cooldownName) && !player.hasPermission("blizzard.mine.cooldown-bypass")) {
//                player.sendActionBar("§c§lEI! §cAguarde um pouco antes de fazer isso novamente.");
//                return;
//            }
//
//            mineAdapter
//                    .sendToMine(
//                            player
//                    );
//
//            cooldown.createCountdown(player, cooldownName, 15, TimeUnit.SECONDS);
//        } else {
//            player.sendActionBar("§c§lEI! §cVocê já está na mina.");
//        }
//    }

    @Subcommand("sair|leave")
    public void onLeaveCommand(Player player) {
        String cooldownName = "blizzard.mine.go-cooldown";

        if (cacheMethods.isInMine(player)) {
            if (cooldown.isInCountdown(player, cooldownName) && !player.hasPermission("blizzard.mine.cooldown-bypass")) {
                player.sendActionBar("§c§lEI! §cAguarde um pouco antes de fazer isso novamente.");
                return;
            }

            mineAdapter
                    .sendToExit(
                            player
                    );

            cooldown.createCountdown(player, cooldownName, 15, TimeUnit.SECONDS);
        } else {
            player.sendActionBar("§c§lEI! §cVocê não está na mina.");
        }
    }
}
