package blizzard.development.mine.commands.mine;

import blizzard.development.mine.mine.adapters.EnchantmentAdapter;
import blizzard.development.mine.mine.adapters.MineAdapter;
import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Default;
import co.aikar.commands.annotation.Subcommand;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class MineCommand extends BaseCommand {

    @Default
    public void onMineCommand(CommandSender commandSender) {
        Player player = (Player) commandSender;

        MineAdapter
            .getInstance()
            .sendToMine(
                player
            );
    }

    @Subcommand("dev")
    @CommandPermission("alchemy.mine.dev")
    public void onDevCommand(CommandSender commandSender) {
        Player player = (Player) commandSender;

        EnchantmentAdapter
            .getInstance()
            .digger(player);

        EnchantmentAdapter
            .getInstance()
            .meteor(player);
    }
}
