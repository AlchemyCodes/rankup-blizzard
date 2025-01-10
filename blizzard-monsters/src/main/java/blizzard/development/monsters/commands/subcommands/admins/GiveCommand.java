package blizzard.development.monsters.commands.subcommands.admins;

import blizzard.development.monsters.database.cache.methods.MonstersCacheMethods;
import blizzard.development.monsters.database.cache.methods.PlayersCacheMethods;
import blizzard.development.monsters.monsters.handlers.eggs.MonstersEggHandler;
import blizzard.development.monsters.monsters.handlers.monsters.MonstersHandler;
import blizzard.development.monsters.utils.items.TextAPI;
import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.*;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.Set;

@CommandAlias("monsters|monster|monstros|monstro|bosses|boss")
@CommandPermission("blizzard.monsters.admin")
public class GiveCommand extends BaseCommand {

    @Subcommand("give|dar")
    @CommandCompletion("@players @monsters @amount @amount")
    @Syntax("<jogador> <monstro> <quantia> <stack>")
    public void onGiveMonster(CommandSender sender, String player, String monster, Integer stack) {
        MonstersHandler monstersHandler = MonstersHandler.getInstance();
        MonstersEggHandler eggHandler = MonstersEggHandler.getInstance();

        ConfigurationSection monstersSection = monstersHandler.getSection();
        Set<String> monsters = monstersHandler.getAll();

        if (monstersSection == null || monsters == null) {
            sender.sendActionBar(TextAPI.parse("§c§lEI! §cOcorreu um erro ao encontrar os monstros na configuração."));
            return;
        }

        Player target = Bukkit.getPlayer(player);
        if (target == null) {
            sender.sendActionBar(TextAPI.parse("§c§lEI! §cO jogador está offline ou é inválido."));
            return;
        }

        if (monsters.contains(monster)) {
            sender.sendActionBar(TextAPI.parse(
                    "§a§lYAY! §aVocê deu §7x" + stack
                            + "§a monstros do tipo §7" + monster
                            + "§a para o jogador §7"
                            + target.getName() + "§a."
            ));
            eggHandler.giveEgg(target, monster, stack);
        } else {
            Arrays.asList(
                    "",
                    " §c§lEI §cO monstro §7" + monster + "§c não existe.",
                    " §cDisponíveis: §7" + monsters + "§c.",
                    ""
            ).forEach(sender::sendMessage);
        }
    }

    @Subcommand("givelimit|darlimite")
    @CommandCompletion("@players @amount")
    @Syntax("<jogador> <quantia>")
    public void onGiveLimit(CommandSender sender, String player, Integer amount) {
        PlayersCacheMethods methods = PlayersCacheMethods.getInstance();

        Player target = Bukkit.getPlayer(player);
        if (target == null) {
            sender.sendActionBar(TextAPI.parse("§c§lEI! §cO jogador está offline ou é inválido."));
            return;
        }

        methods.addMonstersLimit(target, amount);

        sender.sendActionBar(TextAPI.parse(
                "§a§lYAY! §aVocê deu §7x" + amount
                        + "§a limites de monstros"
                        + "§a para o jogador §7"
                        + target.getName() + "§a."
        ));
    }

    @Subcommand("removelimit|removerlimite")
    @CommandCompletion("@players @amount")
    @Syntax("<jogador> <quantia>")
    public void onRemoveLimit(CommandSender sender, String player, Integer amount) {
        PlayersCacheMethods methods = PlayersCacheMethods.getInstance();

        Player target = Bukkit.getPlayer(player);
        if (target == null) {
            sender.sendActionBar(TextAPI.parse("§c§lEI! §cO jogador está offline ou é inválido."));
            return;
        }

        methods.removeMonstersLimit(target, amount);

        sender.sendActionBar(TextAPI.parse(
                "§a§lYAY! §aVocê removeu §7x" + amount
                        + "§a limites de monstros"
                        + "§a do jogador §7"
                        + target.getName() + "§a."
        ));
    }
}
