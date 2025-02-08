package blizzard.development.mine.commands.mine.subcommands.users;

import blizzard.development.currencies.utils.CooldownUtils;
import blizzard.development.mine.database.cache.methods.PlayerCacheMethods;
import blizzard.development.mine.mine.adapters.MineAdapter;
import blizzard.development.mine.mine.enums.ToolEnum;
import blizzard.development.mine.mine.item.ToolBuildItem;
import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Subcommand;
import org.bukkit.entity.Player;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

@CommandAlias("mina|mineracao|mine")
@CommandPermission("blizzard.mine.user")
public class PickaxeCommand extends BaseCommand {

    @Subcommand("picareta|pickaxe")
    public void onPickaxeCommand(Player player) {
        CooldownUtils cooldown = CooldownUtils.getInstance();
        String cooldownName = "blizzard.mine.pickaxe-cooldown";

        if (cooldown.isInCountdown(player, cooldownName) && !player.hasPermission("blizzard.mine.cooldown-bypass")) {
            player.sendActionBar("§c§lEI! §cAguarde um pouco antes de fazer isso novamente.");
            return;
        }

        String toolId = UUID.randomUUID().toString().substring(0, 8);

        ToolBuildItem.tool(
                player,
                true,
                toolId,
                ToolEnum.WOODEN,
                0,
                0
        );

        cooldown.createCountdown(player, cooldownName, 5, TimeUnit.SECONDS);
    }
}
