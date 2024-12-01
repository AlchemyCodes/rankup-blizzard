package blizzard.development.spawners.commands.spawners.subcommands;

import blizzard.development.spawners.handlers.enchantments.EnchantmentsHandler;
import blizzard.development.spawners.handlers.enums.Enchantments;
import blizzard.development.spawners.handlers.enums.Spawners;
import blizzard.development.spawners.handlers.mobs.MobsHandler;
import blizzard.development.spawners.utils.NumberFormat;
import blizzard.development.spawners.utils.items.TextAPI;
import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.*;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.List;

@CommandAlias("spawners|spawner|geradores|gerador")
@CommandPermission("blizzard.spawners.admin")
public class GiveCommand extends BaseCommand {

    @Subcommand("give")
    @CommandCompletion("@players @spawners @amount @amount")
    @Syntax("<player> <spawner> <quantia> <stack>")
    public void onCommand(CommandSender sender, String target, String type, Double amount, Integer stack) {
        Player player = Bukkit.getPlayer(target);
        if (player == null) {
            sender.sendActionBar(TextAPI.parse("§c§lEI! §cO jogador fornecido é inválido ou está offline."));
            return;
        }

        if (!verifyAmount(amount) || !verifyAmount(Double.valueOf(stack))) {
            player.sendActionBar(TextAPI.parse("§c§lEI! §cO valor deve ser maior que 0."));
            return;
        }

        final EnchantmentsHandler handler = EnchantmentsHandler.getInstance();

        switch (type) {
            case "pigs", "pig", "porcos", "porco" -> {
                MobsHandler.giveMobSpawner(
                        player,
                        Spawners.PIG,
                        amount,
                        stack,
                        handler.getInitialLevel(Enchantments.SPEED.getName()),
                        handler.getInitialLevel(Enchantments.LUCKY.getName()),
                        handler.getInitialLevel(Enchantments.EXPERIENCE.getName())
                );
                sender.sendActionBar(TextAPI.parse(successMessage(player, type, amount, stack)));
            }
            case "cows", "cow", "vacas", "vaca" -> {
                MobsHandler.giveMobSpawner(
                        player,
                        Spawners.COW,
                        amount,
                        stack,
                        handler.getInitialLevel(Enchantments.SPEED.getName()),
                        handler.getInitialLevel(Enchantments.LUCKY.getName()),
                        handler.getInitialLevel(Enchantments.EXPERIENCE.getName())
                );
                sender.sendActionBar(TextAPI.parse(successMessage(player, type, amount, stack)));
            }
            case "mooshrooms", "mooshroom", "coguvacas", "coguvaca" -> {
                MobsHandler.giveMobSpawner(
                        player,
                        Spawners.MOOSHROOM,
                        amount,
                        stack,
                        handler.getInitialLevel(Enchantments.SPEED.getName()),
                        handler.getInitialLevel(Enchantments.LUCKY.getName()),
                        handler.getInitialLevel(Enchantments.EXPERIENCE.getName())
                );
                sender.sendActionBar(TextAPI.parse(successMessage(player, type, amount, stack)));
            }
            case "sheeps", "sheep", "ovelhas", "ovelha" -> {
                MobsHandler.giveMobSpawner(
                        player,
                        Spawners.SHEEP,
                        amount,
                        stack,
                        handler.getInitialLevel(Enchantments.SPEED.getName()),
                        handler.getInitialLevel(Enchantments.LUCKY.getName()),
                        handler.getInitialLevel(Enchantments.EXPERIENCE.getName())
                );
                sender.sendActionBar(TextAPI.parse(successMessage(player, type, amount, stack)));
            }
            case "zombies", "zombie", "zumbis", "zumbi" -> {
                MobsHandler.giveMobSpawner(
                        player,
                        Spawners.ZOMBIE,
                        amount,
                        stack,
                        handler.getInitialLevel(Enchantments.SPEED.getName()),
                        handler.getInitialLevel(Enchantments.LUCKY.getName()),
                        handler.getInitialLevel(Enchantments.EXPERIENCE.getName())
                );
                sender.sendActionBar(TextAPI.parse(successMessage(player, type, amount, stack)));
            }
            default -> {
                List<String> messages = Arrays.asList(
                        "",
                        " §c§lEI §cO spawner §7" + type + "§c não existe.",
                        " §cDisponíveis: §7[porco, vaca, covugaca, ovelha e zumbi]",
                        ""
                );
                for (String message : messages) {
                    sender.sendMessage(message);
                }
            }
        }
    }

    public String successMessage(Player player, String type, Double amount, Integer stack) {
        String formattedAmount = NumberFormat.getInstance().formatNumber(amount);
        return "§a§lYAY! §aVocê deu §fx" + formattedAmount + " spawner(s) §ade §f" + type + " (" + stack + ")" + "§a para o jogador §f" + player.getName() + "§a!";
    }

    public Boolean verifyAmount(Double amount) {
        return amount > 0;
    }
}
