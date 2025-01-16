package blizzard.development.mysteryboxes.commands.mysterybox;

import blizzard.development.mysteryboxes.managers.MysteryBoxManager;
import blizzard.development.mysteryboxes.mysteryboxes.adapters.MysteryBoxAdapter;
import blizzard.development.mysteryboxes.mysteryboxes.enums.MysteryBoxEnum;
import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.*;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;


@CommandAlias("caixamisteriosa|cm|caixas|caixa|luckyblock|lb")
public class MysteryBoxCommand extends BaseCommand {

    @Default
    @CommandPermission("alchemy.mysteryboxes.command")
    public void onCommand(CommandSender commandSender) {
        commandSender.sendMessage("");
        commandSender.sendMessage(" §c§lEI! §cFaltam argumentos neste comando rsrs.");
        commandSender.sendMessage(" §cCaixas disponíveis: §7[comum, rara, mistica, lendaria e blizzard]");
        commandSender.sendMessage("");
    }

    @Subcommand("give")
    @CommandCompletion("@players @boxes @range:1-20")
    @CommandPermission("alchemy.mysteryboxes.give")
    public void onGiveCommand(CommandSender commandSender, String playerTarget, MysteryBoxEnum mysteryBoxEnum, int amount) {
        Player target = Bukkit.getPlayer(playerTarget);

        if (target == null) {
            commandSender.sendActionBar(Component.text("§c§lEI! §cO jogador não existe ou está offline."));
            return;
        }

        switch (mysteryBoxEnum) {
            case RARE -> MysteryBoxAdapter.getInstance().giveRareMysteryBox(target, amount);
            case LEGENDARY -> MysteryBoxAdapter.getInstance().giveLegendaryMysteryBox(target, amount);
            case BLIZZARD -> MysteryBoxAdapter.getInstance().giveBlizzardMysteryBox(target, amount);
        }

    }

    @Subcommand("ver")
    @CommandCompletion("@boxes")
    public void onLookCommand(CommandSender commandSender, MysteryBoxEnum mysteryBoxEnum) {

        switch (mysteryBoxEnum.getName()) {
            case "Rara":
                commandSender.sendMessage("abrir inventario rara");
            case "Lendária":
                commandSender.sendMessage("abrir inventario lendária");
            case "Blizzard":
                commandSender.sendMessage("abrir inventario blizzard");
        }

    }

    @Subcommand("dev")
    @CommandPermission("alchemy.mysteryboxes.dev")
    public void onDevCommand(CommandSender commandSender, int i) {
        Player player = (Player) commandSender;

        MysteryBoxManager mysteryBoxManager = new MysteryBoxManager();

        switch (i) {
            case 1:
                mysteryBoxManager.startAnimation(player, MysteryBoxEnum.RARE, player.getLocation());
                break;
            case 2:
                mysteryBoxManager.startAnimation(player, MysteryBoxEnum.LEGENDARY, player.getLocation());
                break;
            case 3:
                mysteryBoxManager.startAnimation(player, MysteryBoxEnum.BLIZZARD, player.getLocation());
        }
    }
}
