package blizzard.development.clans.commands.subcommands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Subcommand;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.List;

@CommandAlias("clans|clan")
public class HelpCommand extends BaseCommand {

    @Subcommand("help")
    @CommandPermission("legacy.clans.basic")
    public void onCommand(CommandSender sender) {

        if (sender.hasPermission("legacy.clans.admin")) {
            List<String> admin = Arrays.asList(
                    "",
                    " §a§lCLANS",
                    "",
                    " §7/clans §8- §7Abra o menu de clans",
                    " §7/clans info §8- §7Informações de um clan",
                    " §7/clans criar §8- §7Crie um clan",
                    " §7/clans convidar §8- §7Convide um jogador ao seu clan",
                    " §7/clans expulsar §8- §7Expulse um jogador do seu clan",
                    " §7/clans promover §8- §7Promova um jogador do seu clan",
                    " §7/clans rebaixar §8- §7Rebaixe um jogador do seu clan",
                    " §7/clans desfazer §8- §7Desfaça seu clan",
                    " §7/clans convites §8- §7Veja seus convites de clans",
                    " §7/clans destaques §8- §7Veja os destaques de clans",
                    " §7/clans cargos §8- §7Veja todos os cargos",
                    " §7/clans sair §8- §7Saia do seu clan",
                    " ",
                    " §7/c §8- §7Falar no chat do clan",
                    " §7/a §8- §7Falar no chat dos aliados",
                    "",
                    " §c/clans deletar §4- §cDeleta um clan",
                    " §c/clans entrar §4- §cEntra em um clan",
                    " §c/clans espiar §4- §cEspie o chat dos clans",
                    " §c/clans give §4- §cDê limite para um jogador",
                    " §c/clans reload §4- §cReinicia o plugin",
                    ""
            );
            for (String s : admin) {
                sender.sendMessage(s);
            }
        } else {
            List<String> message = Arrays.asList(
                    "",
                    " §a§lCLANS",
                    "",
                    " §7/clans §8- §7Abra o menu de clans",
                    " §7/clans info §8- §7Informações de um clan",
                    " §7/clans criar §8- §7Crie um clan",
                    " §7/clans convidar §8- §7Convide um jogador ao seu clan",
                    " §7/clans expulsar §8- §7Expulse um jogador do seu clan",
                    " §7/clans promover §8- §7Promova um jogador do seu clan",
                    " §7/clans rebaixar §8- §7Rebaixe um jogador do seu clan",
                    " §7/clans desfazer §8- §7Desfaça seu clan",
                    " §7/clans convites §8- §7Veja seus convites de clans",
                    " §7/clans destaques §8- §7Veja os destaques de clans",
                    " §7/clans cargos §8- §7Veja todos os cargos",
                    " §7/clans sair §8- §7Saia do seu clan",
                    " ",
                    " §7/c §8- §7Falar no chat do clan",
                    " §7/a §8- §7Falar no chat dos aliados",
                    ""
            );
            for (String s : message) {
                sender.sendMessage(s);
            }
        }
    }

}
