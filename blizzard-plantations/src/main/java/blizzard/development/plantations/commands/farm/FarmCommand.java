package blizzard.development.plantations.commands.farm;

import blizzard.development.plantations.api.WorldAPI;
import blizzard.development.plantations.inventories.FarmInventory;
import blizzard.development.plantations.plantations.adapters.SeedAdapter;
import blizzard.development.plantations.plantations.adapters.ToolAdapter;
import blizzard.development.plantations.plantations.enums.SeedEnum;
import blizzard.development.plantations.utils.CooldownUtils;
import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Default;
import co.aikar.commands.annotation.Subcommand;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.concurrent.TimeUnit;

@CommandAlias("estufa|plantar")
public class FarmCommand extends BaseCommand {

    @Default
    public void onCommand(CommandSender commandSender) {
        if (!(commandSender instanceof Player)) return;

        Player player = (Player) commandSender;

        FarmInventory farmInventory = new FarmInventory();
        farmInventory.open(player);
    }

    @Subcommand("giveseed")
    @CommandPermission("alchemy.plantations.giveseed")
    @CommandAlias("<jogador> <semente> <quantia>")
    public void onGiveSeedCommand(CommandSender commandSender, String playerTarget, String seed, int amount) {

        Player player = (Player) commandSender;
        Player target = Bukkit.getPlayer(playerTarget);

        if (target == null) {
            player.sendActionBar("§c§lEI! §cO jogador está offline ou não existe.");
            return;
        }

        SeedAdapter seedAdapter = new SeedAdapter();

        switch (seed) {
            case "batata":
                seedAdapter.giveSeed(SeedEnum.POTATO, target, amount);
                break;
            case "cenoura":
                seedAdapter.giveSeed(SeedEnum.CARROT, target, amount);
                break;
            case "tomate":
                seedAdapter.giveSeed(SeedEnum.TOMATO, target, amount);
                break;
            case "milho":
                seedAdapter.giveSeed(SeedEnum.CORN, target, amount);
                break;
            default:
                player.sendMessage("");
                player.sendMessage(" §c§lEI! §cA semente §7" + seed + "§c não existe.");
                player.sendMessage(" §cDisponíveis: §7[batata, cenoura, tomate e milho]");
                player.sendMessage("");
        }
    }

    @Subcommand("criar")
    public void onCreate(CommandSender commandSender) {
        Player player = (Player) commandSender;


        WorldAPI worldAPI = new WorldAPI();
        worldAPI.createWorld(
                player
        );
    }

    @Subcommand("ir")
    public void onGo(CommandSender commandSender) {
        Player player = (Player) commandSender;


        WorldAPI worldAPI = new WorldAPI();
        worldAPI.teleportToWorld(
                player
        );
    }

    @Subcommand("aradora")
    public void onClaimPlowingTool(CommandSender commandSender) {
        Player player = (Player) commandSender;

        if (CooldownUtils.getInstance().isInCountdown(player, "plowing-cooldown")) {
            player.sendTitle(
                    "§c§lEI!",
                    "§cAguarde um pouco para resgatar.",
                    10,
                    70,
                    20
            );
            return;
        }

        ToolAdapter toolAdapter = new ToolAdapter();
        toolAdapter.givePlowingTool(player);

        CooldownUtils.getInstance().createCountdown(player, "plowing-cooldown", 3, TimeUnit.SECONDS);

        player.sendTitle(
                "§a§lFerramenta resgatada!",
                "§aVocê recebeu uma ferramenta de arar.",
                10,
                70,
                20
        );
    }

    @Subcommand("cultivadora")
    public void onClaimTool(CommandSender commandSender) {
        Player player = (Player) commandSender;

        if (CooldownUtils.getInstance().isInCountdown(player, "tool-cooldown")) {
            player.sendTitle(
                    "§c§lEI!",
                    "§cAguarde um pouco para resgatar.",
                    10,
                    70,
                    20
            );
            return;
        }

        ToolAdapter toolAdapter = new ToolAdapter();
        toolAdapter.giveTool(player);

        CooldownUtils.getInstance().createCountdown(player, "tool-cooldown", 3, TimeUnit.SECONDS);

        player.sendTitle(
                "§a§lFerramenta resgatada!",
                "§aVocê recebeu uma ferramenta de cultivo.",
                10,
                70,
                20
        );
    }
}
