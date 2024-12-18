package blizzard.development.plantations.commands.farm;

import blizzard.development.plantations.Main;
import blizzard.development.plantations.database.cache.methods.PlayerCacheMethod;
import blizzard.development.plantations.inventories.FarmInventory;
import blizzard.development.plantations.managers.AreaManager;
import blizzard.development.plantations.managers.PlantationManager;
import blizzard.development.plantations.plantations.adapters.ToolAdapter;
import blizzard.development.plantations.utils.CooldownUtils;
import blizzard.development.plantations.utils.LocationUtils;
import blizzard.development.plantations.utils.PluginImpl;
import blizzard.development.plantations.utils.displayentity.DisplayEntityUtils;
import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.*;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.title.Title;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

import static blizzard.development.plantations.utils.NumberFormat.formatNumber;

@CommandAlias("estufa|plantar")
public class FarmCommand extends BaseCommand {

    private final PlayerCacheMethod playerCacheMethod = new PlayerCacheMethod();

    @Default
    public void onCommand(CommandSender commandSender) {
        if (!(commandSender instanceof Player)) return;

        Player player = (Player) commandSender;

        FarmInventory farmInventory = new FarmInventory();
        farmInventory.open(player);
    }

//    @Subcommand("giveseed")
//    @CommandPermission("alchemy.plantations.giveseed")
//    @CommandAlias("<jogador> <semente> <quantia>")
//    public void onGiveSeedCommand(CommandSender commandSender, String playerTarget, String seed, int amount) {
//
//        Player player = (Player) commandSender;
//        Player target = Bukkit.getPlayer(playerTarget);
//
//        if (target == null) {
//            player.sendActionBar("§c§lEI! §cO jogador está offline ou não existe.");
//            return;
//        }
//
//        SeedAdapter seedAdapter = new SeedAdapter();
//
//        switch (seed) {
//            case "batata":
//                seedAdapter.giveSeed(SeedEnum.POTATO, target, amount);
//                break;
//            case "cenoura":
//                seedAdapter.giveSeed(SeedEnum.CARROT, target, amount);
//                break;
//            case "tomate":
//                seedAdapter.giveSeed(SeedEnum.TOMATO, target, amount);
//                break;
//            case "milho":
//                seedAdapter.giveSeed(SeedEnum.CORN, target, amount);
//                break;
//            default:
//                player.sendMessage("");
//                player.sendMessage(" §c§lEI! §cA semente §7" + seed + "§c não existe.");
//                player.sendMessage(" §cDisponíveis: §7[batata, cenoura, tomate e milho]");
//                player.sendMessage("");
//        }
//    }

    @Subcommand("setspawn")
    @CommandPermission("alchemy.plantations.setspawn")
    public void onSetSpawn(CommandSender commandSender) {
        Player player = (Player) commandSender;

        LocationUtils.setSpawnLocation(
            player,
            player.getWorld(),
            (int) player.getX(),
            (int) player.getY(),
            (int) player.getZ(),
            player.getYaw(),
            player.getPitch()
        );
    }

    @Subcommand("setnpcspawn")
    @CommandPermission("alchemy.plantations.setspawn")
    public void onSetNpcSpawn(CommandSender commandSender) {
        Player player = (Player) commandSender;

        DisplayEntityUtils.setDisplayLocation(player.getLocation());
    }

    @Subcommand("removenpcspawn")
    @CommandPermission("alchemy.plantations.setspawn")
    public void onRemoveNpcSpawn(CommandSender commandSender) {
        Player player = (Player) commandSender;

        DisplayEntityUtils.removeDisplay();
    }

    @Subcommand("ir")
    public void onGo(CommandSender commandSender) {
        Player player = (Player) commandSender;

        Location location = LocationUtils.getSpawnLocation();

        if (location == null) {
            player.sendActionBar("§c§lEI! §cO spawn da estufa ainda não foi setado.");
            return;
        }

        player.teleport(location);
    }

    @Subcommand("cultivadora")
    public void onClaimTool(CommandSender commandSender) {
        Player player = (Player) commandSender;

        if (CooldownUtils.getInstance().isInCountdown(player, "tool-cooldown")) {
            player.showTitle(
                Title.title(
                    Component.text("§c§lEI!"),
                    Component.text("§cAguarde um pouco para resgatar."),
                    Title.Times.times(Duration.ZERO, Duration.ofSeconds(2), Duration.ofSeconds(1))
                )
            );
            return;
        }

        ToolAdapter toolAdapter = new ToolAdapter();
        toolAdapter.giveTool(player);

        CooldownUtils.getInstance().createCountdown(player, "tool-cooldown", 3, TimeUnit.SECONDS);

        player.showTitle(
            Title.title(
                Component.text("§a§lFerramenta resgatada!"),
                Component.text("§7Você recebeu uma cultivadora."),
                Title.Times.times(Duration.ZERO, Duration.ofSeconds(2), Duration.ofSeconds(1))
            )
        );
    }

    @Subcommand("mostrar")
    public void onShow(CommandSender commandSender) {
        Player player = (Player) commandSender;


        boolean isInPlantation = playerCacheMethod.isInPlantation(player);

        if (isInPlantation) {
            for (Player players : Bukkit.getOnlinePlayers()) {
                player.showPlayer(Main.getInstance(), players);
            }

            player.sendActionBar("§a§lEI! §aVocê agora vê outros jogadores na estufa!");
        } else player.sendActionBar("§c§lEI! §cVocê não está na estufa!");

    }

    @Subcommand("esconder")
    public void onHide(CommandSender commandSender) {
        Player player = (Player) commandSender;


        boolean isInPlantation = playerCacheMethod.isInPlantation(player);

        if (isInPlantation) {
            for (Player players : Bukkit.getOnlinePlayers()) {
                player.hidePlayer(Main.getInstance(), players);
            }

            player.sendActionBar("§c§lEI! §cVocê desabilitou a visibilidade de outros jogadores na estufa!");
        } else player.sendActionBar("§c§lEI! §cVocê não está na estufa!");

    }

    @Subcommand("transformar")
    public void onTransform(CommandSender commandSender) {
        Player player = (Player) commandSender;

        AreaManager areaManager = AreaManager.getInstance();

        PlantationManager plantationManager = new PlantationManager();
        plantationManager.transform(player, areaManager.getArea(player));
    }

    @Subcommand("reloadconfigs")
    @CommandPermission("alchemy.plantations.reloadconfigs")
    public void onReload(CommandSender commandSender) {
        Player player = (Player) commandSender;

        PluginImpl.getInstance().Shop.reloadConfig();
        PluginImpl.getInstance().Config.reloadConfig();
        PluginImpl.getInstance().Database.reloadConfig();
        PluginImpl.getInstance().Ranking.reloadConfig();
        PluginImpl.getInstance().Locations.reloadConfig();
        player.sendActionBar("§4§lYAY! §4Reload de todas as config's foram realizadas com sucesso.");
    }

    @Subcommand("resetar")
    @CommandPermission("alchemy.plantations.resetar")
    public void onReset(CommandSender commandSender) {
        Player player = (Player) commandSender;

        AreaManager areaManager = AreaManager.getInstance();
        areaManager.setArea(player, 20);
        areaManager.resetArea(player);
    }


    @Subcommand("giveseed")
    @Syntax("<player> <key>")
    @CommandPermission("alchemy.plantations.giveseed")
    public void onGiveSeed(CommandSender commandSender, String playerTarget, int amount) {
        Player player = (Player) commandSender;
        Player target = Bukkit.getPlayer(playerTarget);

        if (target == null) {
            player.sendActionBar("§c§lEI! §cO jogador está offline ou não existe.");
            return;
        }

        PlayerCacheMethod playerCacheMethod = PlayerCacheMethod.getInstance();
        playerCacheMethod.setPlantations(target, playerCacheMethod.getPlantations(target) + amount);

        player.sendActionBar("§a§lYAY! §aVocê adicionou " + formatNumber(amount) + " sementes na conta do jogador " + target.getName());

        player.sendMessage("suas sementes: " + playerCacheMethod.getPlantations(player));
        player.sendMessage("sementes do jogador " + target.getName() + ": " + playerCacheMethod.getPlantations(target));
    }

    @Subcommand("devs")
    @CommandPermission("alchemy.plantations.giveseed")
    public void onDev(CommandSender commandSender) {
        Player player = (Player) commandSender;


//        HarvestEffect harvestEffect = new HarvestEffect();
//        harvestEffect.executeHarvestWave(player, 20);

//        TornadoEffect tornadoEffect = new TornadoEffect();
//        tornadoEffect.startTornadoEffect(player);


//        PacketUtils.getInstance()
//            .sendEntityPacket(
//                player.getLocation(),
//                player
//            );

//        player.getInventory().addItem(
//            new ItemBuilder("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMWU0NjVkZTI2Y2FmYjk0YTM0Y2U5Mjc5ZGY2NzlhYzI4OWQxY2M4NzQxMmZlYWNkYTkwZGI5MjYyMzA3ODJlZSJ9fX0=")
//                .setDisplayName("§6Abatedouro §l✂ §7Nível §l1")
//                .setLore(Arrays.asList(
//                    "§7Mate os mobs do seu",
//                    "§7gerador mais facilmente.",
//                    "",
//                    " §fÁrea: §35x5",
//                    " §fCooldown: §c1 minuto",
//                    "",
//                    "§6Clique no chão para colocar."
//                ))
//                .build()
//        );
    }

}
