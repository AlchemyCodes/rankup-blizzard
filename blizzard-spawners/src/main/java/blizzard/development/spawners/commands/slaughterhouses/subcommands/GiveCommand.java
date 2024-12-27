package blizzard.development.spawners.commands.slaughterhouses.subcommands;

import blizzard.development.spawners.handlers.slaughterhouse.SlaughterhouseHandler;
import blizzard.development.spawners.utils.NumberFormat;
import blizzard.development.spawners.utils.PluginImpl;
import blizzard.development.spawners.utils.items.TextAPI;
import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.*;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@CommandAlias("slaughterhouses|slaughterhouse|abatedouros|abatedouro|matadouros|matadouro")
@CommandPermission("blizzard.spawners.admin")
public class GiveCommand extends BaseCommand {

    @Subcommand("give")
    @CommandCompletion("@players @slaughterhouseslevels @amount")
    @Syntax("<player> <nivel> <stack>")
    public void onGiveSlaughterhouse(CommandSender sender, String target, Integer level, Integer stack) {
        Player player = Bukkit.getPlayer(target);
        if (player == null) {
            sender.sendActionBar(TextAPI.parse("§c§lEI! §cO jogador fornecido é inválido ou está offline."));
            return;
        }

        if (!verifyAmount(stack)) {
            player.sendActionBar(TextAPI.parse("§c§lEI! §cO valor deve ser maior que 0."));
            return;
        }

        final SlaughterhouseHandler slaughterhouse = SlaughterhouseHandler.getInstance();

        if (slaughterhouse.giveSlaughterhouse(player, level, stack)) {
            String formattedStack = NumberFormat.getInstance().formatNumber(stack);
            sender.sendActionBar(TextAPI.parse("§a§lYAY! §aVocê deu §fx" + formattedStack + " abatedouro(s) §ade nível §f" + level + "§a para o jogador §f" + player.getName() + "§a!"));
        } else {
            String formattedLevel = NumberFormat.getInstance().formatNumber(level);
            List<String> messages = Arrays.asList(
                    "",
                    " §c§lEI §cO abatedouro de nível §7" + formattedLevel + "§c não existe.",
                    " §cDisponíveis: §7" + Objects.requireNonNull(PluginImpl.getInstance().Slaughterhouses.getConfig().getConfigurationSection("slaughterhouses")).getKeys(false),
                    ""
            );
            messages.forEach(sender::sendMessage);
        }
    }

    public Boolean verifyAmount(Integer amount) {
        return amount > 0;
    }
}
