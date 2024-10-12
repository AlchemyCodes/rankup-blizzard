package blizzard.development.clans.inventories.secondary;

import com.github.stefvanschie.inventoryframework.gui.GuiItem;
import com.github.stefvanschie.inventoryframework.gui.type.ChestGui;
import com.github.stefvanschie.inventoryframework.pane.StaticPane;
import com.github.stefvanschie.inventoryframework.pane.util.Slot;
import com.plotsquared.core.PlotSquared;
import com.plotsquared.core.location.Location;
import com.plotsquared.core.plot.Plot;
import com.plotsquared.core.plot.PlotArea;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import blizzard.development.clans.database.cache.ClansCacheManager;
import blizzard.development.clans.database.storage.ClansData;
import blizzard.development.clans.enums.Roles;
import blizzard.development.clans.inventories.primary.ClansInventory;
import blizzard.development.clans.methods.ClansMethods;

import java.util.Arrays;
import java.util.Objects;
import java.util.UUID;

public class BaseInventory {

    public static void open(Player player) {
        ChestGui inventory = new ChestGui(4, "§8Clans - Base");

        StaticPane pane = new StaticPane(0, 0, 9, 4);

        String clan = ClansMethods.getUserClan(player);
        org.bukkit.Location location = player.getLocation();

        GuiItem setLocationItem = new GuiItem(setLocation(), event -> {
            ClansData data = ClansMethods.getClan(clan);

            if (data == null) {
                player.sendMessage("§cVocê não está em um clan!");
                player.getOpenInventory().close();
                event.setCancelled(true);
                return;
            }

            String playerRole = ClansMethods.getUser(player).getRole();
            Boolean isOwner = ClansMethods.isOwner(clan, player);
            Boolean leader = playerRole.equals(Roles.LEADER.getName());

            if (!isOwner && !leader) {
                player.sendMessage("§cVocê não tem permissão para marcar a base do clan!");
                event.setCancelled(true);
                return;
            }

            Location playerLocation = Location.at(
                    location.getWorld().getName(),
                    (int) location.getX(),
                    (int) location.getY(),
                    (int) location.getZ()
            );

            UUID playerUUID = player.getUniqueId();
            PlotArea plotArea = PlotSquared.get().getPlotAreaManager().getPlotArea(playerLocation);

            if (plotArea == null) {
                player.sendMessage("§cVocê não pode marcar a base do clan neste local.");
                player.getOpenInventory().close();
                event.setCancelled(true);
                return;
            }

            Plot plot = plotArea.getPlot(playerLocation);

            if (plot == null || playerLocation == null) {
                player.sendMessage("§cVocê não pode marcar a base do clan neste local.");
                player.getOpenInventory().close();
                event.setCancelled(true);
                return;
            }

            if (!(Objects.equals(plot.getOwner(), playerUUID) || plot.isAdded(playerUUID))) {
                player.sendMessage("§cVocê não tem permissão para marcar a base do clan nesse terreno.");
                player.getOpenInventory().close();
                event.setCancelled(true);
                return;
            }

            org.bukkit.Location baseLocation = ClansCacheManager.getClanBase(clan);

            if (baseLocation != null) {
                player.sendMessage("§cSeu clan já tem uma base marcada!");
                player.getOpenInventory().close();
                event.setCancelled(true);
                return;
            }

            ClansCacheManager.setClanBase(clan, location);
            player.sendMessage("§aVocê marcou a localização da base do clan com sucesso!");
            player.getOpenInventory().close();
            event.setCancelled(true);
        });

        GuiItem teleportItem = new GuiItem(teleport(), event -> {
            ClansData data = ClansMethods.getClan(clan);

            if (data == null) {
                player.sendMessage("§cVocê não está em um clan!");
                player.getOpenInventory().close();
                event.setCancelled(true);
                return;
            }

            String playerRole = ClansMethods.getUser(player).getRole();
            Boolean member = playerRole.equals(Roles.MEMBER.getName());

            if (member) {
                player.sendMessage("§cVocê não tem permissão para marcar a base do clan!");
                return;
            }

            org.bukkit.Location baseLocation = ClansCacheManager.getClanBase(clan);

            if (baseLocation == null) {
                player.sendMessage("§cSeu clan ainda não tem nenhuma base!");
                player.getOpenInventory().close();
                event.setCancelled(true);
                return;
            }

            player.teleport(baseLocation);
            player.sendMessage("§aVocê foi teleportado para a base do seu clan!");
            player.getOpenInventory().close();
            event.setCancelled(true);
        });

        GuiItem unSetLocationItem = new GuiItem(unSetLocation(), event -> {
            ClansData data = ClansMethods.getClan(clan);

            if (data == null) {
                player.sendMessage("§cVocê não está em um clan!");
                player.getOpenInventory().close();
                event.setCancelled(true);
                return;
            }

            String playerRole = ClansMethods.getUser(player).getRole();
            Boolean isOwner = ClansMethods.isOwner(clan, player);
            Boolean leader = playerRole.equals(Roles.LEADER.getName());

            if (!isOwner && !leader) {
                player.sendMessage("§cVocê não tem permissão para desmarcar a base do clan!");
                return;
            }

            org.bukkit.Location baseLocation = ClansCacheManager.getClanBase(clan);

            if (baseLocation == null) {
                player.sendMessage("§cSeu clan ainda não tem nenhuma base para desmarcar!");
                player.getOpenInventory().close();
                event.setCancelled(true);
                return;
            }

            ClansCacheManager.unSetClanBase(clan);
            player.sendMessage("§aVocê desmarcou a localização da base do clan com sucesso!");
            player.getOpenInventory().close();
            event.setCancelled(true);
        });

        GuiItem backItem = new GuiItem(back(), event -> {
            ClansInventory.open(player);
            event.setCancelled(true);
        });

        pane.addItem(setLocationItem, Slot.fromIndex(11));
        pane.addItem(teleportItem, Slot.fromIndex(13));
        pane.addItem(unSetLocationItem, Slot.fromIndex(15));
        pane.addItem(backItem, Slot.fromIndex(31));

        inventory.addPane(pane);

        player.playSound(player.getLocation(), Sound.BLOCK_CHEST_OPEN, 0.5f, 0.5f);

        inventory.show(player);
    }

    public static ItemStack setLocation() {
        ItemStack item = new ItemStack(Material.LIME_DYE);

        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName("§aMarcar");
        meta.setLore(Arrays.asList(
                "§7Marque a localização da base do seu clan",
                "",
                "§aClique para marcar."
        ));
        item.setItemMeta(meta);

        return item;
    }

    public static ItemStack teleport() {
        ItemStack item = new ItemStack(Material.COMPASS);

        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName("§8Teleportar");
        meta.setLore(Arrays.asList(
                "§7Vá para a localização da base do seu clan",
                "",
                "§8Clique para ir."
        ));
        item.setItemMeta(meta);

        return item;
    }

    public static ItemStack unSetLocation() {
        ItemStack item = new ItemStack(Material.REDSTONE);

        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName("§cDesmarcar");
        meta.setLore(Arrays.asList(
                "§7Desmarque a localização da base do seu clan",
                "",
                "§cClique para desmarcar."
        ));
        item.setItemMeta(meta);

        return item;
    }

    public static ItemStack back() {
        ItemStack item = new ItemStack(Material.RED_DYE);

        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName("§cVoltar");
        meta.setLore(Arrays.asList(
                "§7Volte para o menu principal!"
        ));
        item.setItemMeta(meta);

        return item;
    }
}
