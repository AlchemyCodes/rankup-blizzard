package blizzard.development.mysterybox.commands.mysterybox;

import blizzard.development.mysterybox.managers.MysteryBoxManager;
import blizzard.development.mysterybox.managers.RewardManager;
import blizzard.development.mysterybox.mysterybox.adapters.MysteryBoxAdapter;
import blizzard.development.mysterybox.mysterybox.enums.MysteryBoxEnum;
import blizzard.development.mysterybox.utils.PluginImpl;
import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.*;
import net.kyori.adventure.text.Component;
import org.bukkit.*;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.ItemDisplay;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Transformation;
import org.joml.Quaternionf;
import org.joml.Vector3f;


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

    @Subcommand("teste")
    @CommandPermission("alchemy.mysteryboxes.dev")
    public void onTestCommand(CommandSender commandSender) {
        Player player = (Player) commandSender;

        ItemDisplay display = (ItemDisplay) player.getWorld().spawn(player.getLocation().add(0, 2, 0), ItemDisplay.class);
        display.setItemStack(new ItemStack(Material.DIAMOND_PICKAXE));
        Quaternionf initialRotation = new Quaternionf().rotateXYZ(0, 0, 0);
        display.setTransformation(new Transformation(
            new Vector3f(0f, 0f, 0f),
            initialRotation,
            new Vector3f(10f, 10f, 10f),
            new Quaternionf()
        ));

        new BukkitRunnable() {
            int ticks = 0;

            final double speed = 0.09;
            final int duration = 30;
            final double radius = 2.5;

            @Override
            public void run() {
                float angle = ticks * 0.3f;

                Quaternionf rotation = new Quaternionf().rotateY(angle);
                display.setTransformation(new Transformation(
                    new Vector3f(0f, 0f, 0f),
                    rotation,
                    new Vector3f(10f, 10f, 10f),
                    new Quaternionf()
                ));

                ticks++;
            }
        }.runTaskTimer(PluginImpl.getInstance().plugin, 0L, 1L);
    }
}
