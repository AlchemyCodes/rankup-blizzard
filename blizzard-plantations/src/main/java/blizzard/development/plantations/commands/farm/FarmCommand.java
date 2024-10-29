package blizzard.development.plantations.commands.farm;

import blizzard.development.plantations.database.cache.methods.PlayerCacheMethod;
import blizzard.development.plantations.plantations.adapters.SeedAdapter;
import blizzard.development.plantations.plantations.enums.SeedEnum;
import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Default;
import co.aikar.commands.annotation.Subcommand;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandAlias("estufa|plantar")
public class FarmCommand extends BaseCommand {

    @Default
    public void onCommand(CommandSender commandSender) {
        if (!(commandSender instanceof Player)) return;

        Player player = (Player) commandSender;

        PlayerCacheMethod playerCacheMethod = new PlayerCacheMethod();
        playerCacheMethod.setInPlantation(player);

        // TODO: criar o inventário da estufa para o player.
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
            case "tomate":
                seedAdapter.giveSeed(SeedEnum.TOMATO, player, amount);
                break;
            case "alface":
                seedAdapter.giveSeed(SeedEnum.LETTUCE, player, amount);
                break;
            default:
                player.sendMessage("");
                player.sendMessage(" §c§lEI! §cA semente §7" + seed + "§c não existe.");
                player.sendMessage(" §cDisponíveis: §7[tomate, alface]");
                player.sendMessage("");
        }
    }

}
