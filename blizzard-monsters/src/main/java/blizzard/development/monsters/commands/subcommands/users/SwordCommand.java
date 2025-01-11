package blizzard.development.monsters.commands.subcommands.users;

import blizzard.development.monsters.monsters.managers.tools.MonstersToolManager;
import blizzard.development.monsters.utils.CooldownUtils;
import blizzard.development.monsters.utils.items.TextAPI;
import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.*;
import org.bukkit.entity.Player;

import java.util.concurrent.TimeUnit;

@CommandAlias("monsters|monster|monstros|monstro|bosses|boss")
@CommandPermission("blizzard.monsters.basic")
public class SwordCommand extends BaseCommand {

    @Subcommand("aniquiladora|espada|matadora")
    public void onCommand(Player player) {
        CooldownUtils cooldown = CooldownUtils.getInstance();

        String cooldownName = "blizzard.monsters.sword-cooldown";

        if (cooldown.isInCountdown(player, cooldownName) && !player.hasPermission("blizzard.monsters.admin")) {
            player.sendActionBar(TextAPI.parse("§c§lEI! §cVocê precisa esperar um pouco antes de executar esse comando."));
            return;
        }

        if (player.getInventory().firstEmpty() == -1) {
            player.sendActionBar(TextAPI.parse("§c§lEI! §cVocê precisa de pelo menos um slot vazio."));
            return;
        }

        player.sendActionBar(TextAPI.parse("§a§lYAY! §aVocê recebeu uma aniquiladora."));
        MonstersToolManager.getInstance().giveSword(player, 2, 1);

        cooldown.createCountdown(player, cooldownName, 2, TimeUnit.SECONDS);
    }
}
