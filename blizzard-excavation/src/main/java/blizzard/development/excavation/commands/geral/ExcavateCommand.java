package blizzard.development.excavation.commands.geral;

import blizzard.development.excavation.database.cache.methods.PlayerCacheMethod;
import blizzard.development.excavation.excavation.item.ExcavatorBuildItem;
import blizzard.development.excavation.inventories.ExcavationInventory;
import blizzard.development.excavation.utils.LocationUtils;
import blizzard.development.excavation.utils.PluginImpl;
import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Default;
import co.aikar.commands.annotation.Subcommand;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandAlias("escavar|escavacao")
public class ExcavateCommand extends BaseCommand {

    @Default
    public void onCommand(CommandSender commandSender) {
        if (!(commandSender instanceof Player player)) return;

        ExcavationInventory excavationInventory = new ExcavationInventory();
        excavationInventory.open(player);
    }

    @Subcommand("setspawn")
    @CommandPermission("alchemy.excavation.staff")
    public void onSetSpawn(CommandSender commandSender) {

        Player player = (Player) commandSender;

        double x = player.getX();
        double y = player.getY();
        double z = player.getZ();
        float yaw = player.getYaw();
        float pitch = player.getPitch();
        String world = player.getWorld().getName();

        PluginImpl.getInstance().Locations.getConfig().set("excavation.location.world", world);
        PluginImpl.getInstance().Locations.getConfig().set("excavation.location.x", x);
        PluginImpl.getInstance().Locations.getConfig().set("excavation.location.y", y);
        PluginImpl.getInstance().Locations.getConfig().set("excavation.location.z", z);
        PluginImpl.getInstance().Locations.getConfig().set("excavation.location.yaw", yaw);
        PluginImpl.getInstance().Locations.getConfig().set("excavation.location.pitch", pitch);
        PluginImpl.getInstance().Locations.saveConfig();

        player.sendMessage("");
        player.sendMessage(" §b§lYAY! §bVocê setou o spawn da escavação nas cordenadas;");
        player.sendMessage(" §7x" + x + " y" + y + " z" + z + " §fno mundo §8[" + world + "]");
        player.sendMessage("");
    }

    @Subcommand("escavadeira")
    public void onGive(CommandSender commandSender) {
        Player player = (Player) commandSender;

        ExcavatorBuildItem excavatorBuildItem = new ExcavatorBuildItem();
        player.getInventory().addItem(excavatorBuildItem.buildExcavator());
    }

    @Subcommand("ir")
    public void onGo(CommandSender commandSender) {
        Player player = (Player) commandSender;

        PlayerCacheMethod playerCacheMethod = new PlayerCacheMethod();
        LocationUtils locationUtils = new LocationUtils();

        if (!playerCacheMethod.isInExcavation(player)) {

            player.teleport(locationUtils.excavationLocation());
            player.sendTitle(
                    "§b§lEscavação!",
                    "§bVocê entrou na escavação.",
                    10,
                    70,
                    20
            );

            playerCacheMethod.setInExcavation(player);
        } else {
            player.sendActionBar("§c§lEI! §cVocê já está na escavação.");
        }
    }

    @Subcommand("sair")
    public void onLeave(CommandSender commandSender) {
        Player player = (Player) commandSender;

        PlayerCacheMethod playerCacheMethod = new PlayerCacheMethod();

        if (playerCacheMethod.isInExcavation(player)) {
            player.teleport(player.getWorld().getSpawnLocation());

            player.sendTitle(
                    "§c§lEscavação!",
                    "§cVocê saiu da escavação.",
                    10,
                    70,
                    20
            );

            playerCacheMethod.removeInExcavation(player);
        } else {
            player.sendActionBar("§c§lEI! §cVocê não está na escavação.");
        }
    }
}
