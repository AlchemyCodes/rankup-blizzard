package blizzard.development.time.commands.time.subcommands;

import blizzard.development.time.database.cache.getters.PlayersCacheGetters;
import blizzard.development.time.database.cache.setters.PlayersCacheSetters;
import blizzard.development.time.utils.TimeConverter;
import blizzard.development.time.utils.items.TextAPI;
import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandCompletion;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Subcommand;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandAlias("time|tempo")
@CommandPermission("alchemy.time.admin")
public class TimeExchangeCommand extends BaseCommand {

    private final PlayersCacheSetters setters = PlayersCacheSetters.getInstance();
    private final PlayersCacheGetters getters = PlayersCacheGetters.getInstance();

    @Subcommand("set|setar")
    @CommandCompletion("@players")
    public void onSet(CommandSender sender, String name, long time) {
        Player target = Bukkit.getPlayer(name);
        if (target == null) {
            sender.sendActionBar(TextAPI.parse("§c§lEI! §cO jogador fornecido é inválido ou está offline."));
            return;
        }

        if (time <= 0) {
            sender.sendActionBar(TextAPI.parse("§c§lEI! §cInsira um número maior do que 0."));
            return;
        }

        setters.setPlayTime(target, time);
        String timeToString = TimeConverter.convertSecondsToTimeFormat(time);
        sender.sendActionBar(TextAPI.parse(
                "§a§lYAY! §aVocê alterou o tempo do jogador §7" + target.getName() + "§a para §7" + timeToString)
        );
    }

    @Subcommand("add|adicionar")
    @CommandCompletion("@players")
    public void onAdd(CommandSender sender, String name, long time) {
        Player target = Bukkit.getPlayer(name);
        if (target == null) {
            sender.sendActionBar(TextAPI.parse("§c§lEI! §cO jogador fornecido é inválido ou está offline."));
            return;
        }

        if (time <= 0) {
            sender.sendActionBar(TextAPI.parse("§c§lEI! §cInsira um número maior do que 0."));
            return;
        }

        setters.addPlayTime(target, time);
        String timeToString = TimeConverter.convertSecondsToTimeFormat(time);
        sender.sendActionBar(TextAPI.parse(
                "§a§lYAY! §aVocê adicionou ao jogador §7" + target.getName() + "§a o tempo de §7" + timeToString)
        );
    }

    @Subcommand("remove|remover")
    @CommandCompletion("@players")
    public void onRemove(CommandSender sender, String name, long time) {
        Player target = Bukkit.getPlayer(name);
        if (target == null) {
            sender.sendActionBar(TextAPI.parse("§c§lEI! §cO jogador fornecido é inválido ou está offline."));
            return;
        }

        if (time <= 0) {
            sender.sendActionBar(TextAPI.parse("§c§lEI! §cInsira um número maior do que 0."));
            return;
        }

        if (getters.getPlayTime(target.getPlayer()) < time) {
            sender.sendActionBar(TextAPI.parse("§c§lEI! §cO jogador não tem essa quantia de tempo."));
            return;
        }

        setters.removePlayTime(target, time);
        String timeToString = TimeConverter.convertSecondsToTimeFormat(time);
        sender.sendActionBar(TextAPI.parse(
                "§a§lYAY! §aVocê removeu do jogador §7" + target.getName() + "§a o tempo de §7" + timeToString)
        );
    }
}
