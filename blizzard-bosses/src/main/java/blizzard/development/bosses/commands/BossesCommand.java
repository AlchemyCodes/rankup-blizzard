package blizzard.development.bosses.commands;

import blizzard.development.bosses.database.cache.ToolsCacheManager;
import blizzard.development.bosses.tools.SwordTool;
import blizzard.development.bosses.utils.PluginImpl;
import blizzard.development.bosses.utils.items.ItemBuilder;
import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.Default;
import co.aikar.commands.annotation.Subcommand;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

@CommandAlias("bosses|boss|monstros|monstro")
public class BossesCommand extends BaseCommand {

    @Default
    public void onCommand(CommandSender sender) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("§cEste comando só pode ser utilizado por jogadores!");
            return;
        }

        Player player = (Player) sender;
        giveTools(player);
    }

    @Subcommand("type")
    public void onType(CommandSender sender) {
        if (!(sender instanceof Player)) {
            return;
        }

        Player player = (Player) sender;
        ItemStack item = player.getInventory().getItemInMainHand();

        boolean hasSwordTool = ItemBuilder.hasPersistentData(PluginImpl.getInstance().plugin, item, "blizzard.bosses.sword-tool");

        if (hasSwordTool) {
            String toolId = ItemBuilder.getPersistentData(PluginImpl.getInstance().plugin, item, "blizzard.bosses.tool-id");
            ToolsCacheManager.setType(toolId, "sexo");
            player.sendMessage("§aTipo setado para: sexo");
        } else {
            player.sendMessage("§cEste item não é uma ferramenta válida!");
        }
    }

    private void giveTools(Player player) {
        SwordTool.giveSword(player);
    }
}
