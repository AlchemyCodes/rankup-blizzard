package blizzard.development.time.commands.time;

import blizzard.development.time.database.cache.getters.PlayersCacheGetters;
import blizzard.development.time.inventories.TimeInventory;
import blizzard.development.time.utils.TimeConverter;
import blizzard.development.time.utils.items.TextAPI;
import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.*;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.Arrays;
import java.util.List;

@CommandAlias("times|time|tempo|tempos")
@CommandPermission("alchemy.time.basic")
public class TimeCommand extends BaseCommand {

    @Default
    @CommandCompletion("@players")
    private void onCommand(CommandSender sender, @Optional String name) {
        if (name == null) {
            if (!(sender instanceof Player player)) {
                sender.sendMessage("§c§lEI! §cApenas jogadores podem utilizar esse comando.");
                return;
            }

            TimeInventory.getInstance().open(player);
        } else {
            Player target = Bukkit.getPlayer(name);
            if (target == null) {
                sender.sendActionBar(TextAPI.parse("§c§lEI! §cO jogador fornecido é inválido ou está offline."));
                return;
            }

            String time = TimeConverter.convertSecondsToTimeFormat(PlayersCacheGetters.getInstance().getPlayTime(target));

            List<String> messages = Arrays.asList(
                    "",
                    " §eConfira agora o tempo de jogo do jogador §l" + target.getName() + "§e.",
                    " §eTempo: ⌚ " + "§l" + time,
                    ""
            );

            messages.forEach(sender::sendMessage);
        }
    }
}
