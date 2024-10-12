package blizzard.development.clans.inventories.primary;

import com.github.stefvanschie.inventoryframework.gui.GuiItem;
import com.github.stefvanschie.inventoryframework.gui.type.ChestGui;
import com.github.stefvanschie.inventoryframework.pane.StaticPane;
import com.github.stefvanschie.inventoryframework.pane.util.Slot;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import blizzard.development.clans.database.cache.PlayersCacheManager;
import blizzard.development.clans.inventories.secondary.ClansListInventory;
import blizzard.development.clans.inventories.secondary.InvitesInventory;
import blizzard.development.clans.listeners.clans.ClansCreationListener;
import blizzard.development.clans.methods.ClansMethods;
import blizzard.development.clans.utils.PluginImpl;
import blizzard.development.clans.utils.skulls.SkullAPI;
import java.util.Arrays;
import java.util.List;

public class DefaultInventory {

    public static void open(Player player) {
        ChestGui inventory = new ChestGui(3, "§8Clans");

        StaticPane pane = new StaticPane(0, 0, 9, 3);

        GuiItem profileItem = new GuiItem(profile(player), event -> {
            event.setCancelled(true);
        });

        GuiItem createItem = new GuiItem(create(), event -> {
            player.getOpenInventory().close();
            ClansCreationListener.addPendingCreate(player);
            int price = PluginImpl.getInstance().Config.getInt("clans.create-price");
            List<String> messages = Arrays.asList(
                    "",
                    "§aDigite no chat a tag e o nome do clan que deseja criar!",
                    "",
                    "§7Formato: <tag> <nome>. Exemplo: QDR QUADRADO",
                    "§7Preço: §2$§7" + price,
                    "",
                    "§8Digite 'cancelar' para cancelar a criação.",
                    ""
            );
            for (String message : messages) {
                player.sendMessage(message);
            }
            event.setCancelled(true);
        });

        GuiItem invitesItem = new GuiItem(invites(), event -> {
            InvitesInventory.open(player);
            event.setCancelled(true);
        });

        GuiItem clansItem = new GuiItem(clans(), event -> {
            ClansListInventory.open(player, 0);
            event.setCancelled(true);
        });

        pane.addItem(profileItem, Slot.fromIndex(10));
        pane.addItem(createItem, Slot.fromIndex(12));
        pane.addItem(invitesItem, Slot.fromIndex(14));
        pane.addItem(clansItem, Slot.fromIndex(16));

        inventory.addPane(pane);

        player.playSound(player.getLocation(), Sound.BLOCK_CHEST_OPEN, 0.5f, 0.5f);

        inventory.show(player);
    }

    public static ItemStack profile(Player player) {
        ItemStack skull = SkullAPI.withName(new ItemStack(Material.PLAYER_HEAD), player.getName());
        ItemMeta meta = skull.getItemMeta();
        meta.setDisplayName("§ePerfil");

        int invites = ClansMethods.getInvitesCount(player.getName());

        meta.setLore(Arrays.asList(
                "",
                "§7 Clan: §eNenhum",
                "§7 KDR: §e" + PlayersCacheManager.getKDR(player.getName()),
                "§7 Invites: §e" + invites,
                ""
        ));
        skull.setItemMeta(meta);

        return skull;
    }

    public static ItemStack create() {
        String value = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNWZmMzE0MzFkNjQ1ODdmZjZlZjk4YzA2NzU4MTA2ODFmOGMxM2JmOTZmNTFkOWNiMDdlZDc4NTJiMmZmZDEifX19";

        ItemStack skull = SkullAPI.fromBase64(SkullAPI.Type.BLOCK, value);
        ItemMeta meta = skull.getItemMeta();
        meta.setDisplayName("§aCriar");
        meta.setLore(Arrays.asList(
                "§7Crie seu próprio clan",
                "",
                "§aClique para criar."
        ));
        skull.setItemMeta(meta);

        return skull;
    }

    public static ItemStack invites() {
        ItemStack item = new ItemStack(Material.LEGACY_EMPTY_MAP);

        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName("§aConvites");
        meta.setLore(Arrays.asList(
                "§7Visualize convites enviados para você",
                "",
                "§aClique para visualizar."
        ));
        item.setItemMeta(meta);

        return item;
    }

    public static ItemStack clans() {
        ItemStack item = new ItemStack(Material.DIAMOND_HORSE_ARMOR);

        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName("§bClans");
        meta.setLore(Arrays.asList(
                "§7Visualize todos os clans do servidor",
                "",
                "§bClique para visualizar."
        ));
        item.setItemMeta(meta);

        return item;
    }

}
