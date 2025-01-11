package blizzard.development.plantations.commands.farm;

import blizzard.development.plantations.Main;
import blizzard.development.plantations.api.CoreAPI;
import blizzard.development.plantations.database.cache.methods.PlayerCacheMethod;
import blizzard.development.plantations.database.cache.methods.ToolCacheMethod;
import blizzard.development.plantations.inventories.FarmInventory;
import blizzard.development.plantations.managers.AreaManager;
import blizzard.development.plantations.managers.PlantationManager;
import blizzard.development.plantations.managers.upgrades.agility.AgilityManager;
import blizzard.development.plantations.plantations.adapters.AreaAdapter;
import blizzard.development.plantations.plantations.adapters.ToolAdapter;
import blizzard.development.plantations.plantations.enums.PlantationEnum;
import blizzard.development.plantations.plantations.enums.ToolsEnum;
import blizzard.development.plantations.plantations.item.ToolBuildItem;
import blizzard.development.plantations.utils.CooldownUtils;
import blizzard.development.plantations.utils.LocationUtils;
import blizzard.development.plantations.utils.PluginImpl;
import blizzard.development.plantations.utils.displayentity.DisplayEntityUtils;
import blizzard.development.plantations.utils.packets.PacketUtils;
import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.*;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.title.Title;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.WeatherType;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Horse;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static blizzard.development.plantations.builder.ItemBuilder.getPersistentData;
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

        LocationUtils.setPlantationSpawnLocation(
            player,
            player.getWorld(),
            (int) player.getX(),
            (int) player.getY(),
            (int) player.getZ(),
            player.getYaw(),
            player.getPitch()
        );
    }

    @Subcommand("setnormalspawn")
    @CommandPermission("alchemy.plantations.setspawn")
    public void onSetNormalSpawn(CommandSender commandSender) {
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

    @Subcommand("setcenterspawn")
    @CommandPermission("alchemy.plantations.setspawn")
    public void onSetCenterSpawn(CommandSender commandSender) {
        Player player = (Player) commandSender;

        LocationUtils.setCenterLocation(
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

        Location location = LocationUtils.getPlantationSpawnLocation();

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
        areaManager.setArea(player, 10);
        areaManager.resetArea(player);
        areaManager.setAreaPlantation(player, PlantationEnum.POTATO);
    }

    @Subcommand("ir")
    @Syntax("<player>")
    @CommandCompletion("@players")
    public void onGoCommand(CommandSender commandSender, @Optional String playerTarget) {
        if (playerTarget == null) {
            Player player = (Player) commandSender;

            AreaAdapter
                .getInstance()
                .teleportToArea(player);
            return;
        }

        Player player = (Player) commandSender;
        Player target = Bukkit.getPlayer(playerTarget);

        Location location = LocationUtils.getPlantationSpawnLocation();

        if (location == null) {
            player.sendActionBar("§c§lEI! §cO spawn da estufa ainda não foi setado.");
            return;
        }

        if (target == null) {
            player.sendActionBar("§c§lEI! §cO jogador está offline ou não existe.");
            return;
        }

        AreaAdapter
            .getInstance()
            .teleportToFriendArea(player, target);
    }

    @Subcommand("testes")
    @CommandPermission("alchemy.plantations.giveseed")
    public void onTest(CommandSender commandSender, ToolsEnum toolsEnum) {
        Player player = (Player) commandSender;

        ItemStack item = player.getInventory().getItemInMainHand();
        String id = getPersistentData(Main.getInstance(), item, "ferramenta-id");

        ToolCacheMethod toolCacheMethod = ToolCacheMethod.getInstance();

        switch (toolsEnum) {
            case WOODEN -> toolCacheMethod.setSkin(id, ToolsEnum.WOODEN);
            case STONE -> toolCacheMethod.setSkin(id, ToolsEnum.STONE);
            case IRON -> toolCacheMethod.setSkin(id, ToolsEnum.IRON);
            case GOLD -> toolCacheMethod.setSkin(id, ToolsEnum.GOLD);
            case DIAMOND -> toolCacheMethod.setSkin(id, ToolsEnum.DIAMOND);
            default -> player.sendMessage("nao tem");
        }

        player.getInventory().setItemInMainHand(ToolBuildItem.tool(
            id,
            1,
            1,
            1,
            1,
            1,
            1,
            1,
            1
        ));
    }

    @Subcommand("devs")
    @CommandPermission("alchemy.plantations.giveseed")
    public void onDev(CommandSender commandSender, String playerTarget) {
        Player player = (Player) commandSender;
        Player target = Bukkit.getPlayer(playerTarget);


        if (target == null) {
            player.sendActionBar("§c§lEI! §cO jogador está offline ou não existe.");


            List<String>  message = Arrays.asList(
                "",
                " §3§lMonstros! §3Capturamos todos os seus monstros.",
                " §7Você pode pegá-los novamente no §7´§o§f/gaiola§7´.",
                ""
            );

            for (String messages : message) {
                player.sendMessage(messages);
            }

            return;
        }

        PlayerCacheMethod
            .getInstance()
            .addFriend(player, target.getName());
    }

}
