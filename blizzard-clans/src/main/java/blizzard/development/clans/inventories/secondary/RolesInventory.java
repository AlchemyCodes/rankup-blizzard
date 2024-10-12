package blizzard.development.clans.inventories.secondary;

import com.github.stefvanschie.inventoryframework.gui.GuiItem;
import com.github.stefvanschie.inventoryframework.gui.type.ChestGui;
import com.github.stefvanschie.inventoryframework.pane.StaticPane;
import com.github.stefvanschie.inventoryframework.pane.util.Slot;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import blizzard.development.clans.database.storage.PlayersData;
import blizzard.development.clans.inventories.primary.ClansInventory;
import blizzard.development.clans.methods.ClansMethods;
import blizzard.development.clans.utils.skulls.SkullAPI;
import java.util.Arrays;

public class RolesInventory {

    public static void open(Player player) {
        ChestGui inventory = new ChestGui(3, "§8Clans - Cargos");

        StaticPane pane = new StaticPane(0, 0, 9, 3);

        GuiItem leaderItem = new GuiItem(leader(), event -> {
            event.setCancelled(true);
        });

        GuiItem captainItem = new GuiItem(captain(), event -> {
            event.setCancelled(true);
        });

        GuiItem reliableItem = new GuiItem(reliable(), event -> {
            event.setCancelled(true);
        });

        GuiItem memberItem = new GuiItem(member(), event -> {
            event.setCancelled(true);
        });

        GuiItem backItem = new GuiItem(back(), event -> {
            PlayersData data = ClansMethods.getUser(player);

            if (data.getClan() == null) {
                player.getOpenInventory().close();
                event.setCancelled(true);
                return;
            }

            ClansInventory.open(player);
            event.setCancelled(true);
        });

        pane.addItem(leaderItem, Slot.fromIndex(11));
        pane.addItem(captainItem, Slot.fromIndex(12));
        pane.addItem(reliableItem, Slot.fromIndex(14));
        pane.addItem(memberItem, Slot.fromIndex(15));
        pane.addItem(backItem, Slot.fromIndex(18));

        inventory.addPane(pane);

        player.playSound(player.getLocation(), Sound.BLOCK_CHEST_OPEN, 0.5f, 0.5f);

        inventory.show(player);
    }

    private static ItemStack leader() {

        String value = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYmQyM2RiZWVmNjY2MTVhMWNhMzhmZmUyYjc0NTY3ZDk2YTcwNmEzNWE3MTFmMzU1N2M4ZTE3ZWM1ZWRlODA4MSJ9fX0=";
        ItemStack skull = SkullAPI.fromBase64(SkullAPI.Type.BLOCK, value);

        String suffix = "⭐";

        ItemMeta meta = skull.getItemMeta();
        meta.setDisplayName("§6Líder " + suffix);
        meta.setLore(Arrays.asList(
                "§7Veja as permissões do cargo:",
                "",
                "§8■ §7Desfazer o clan",
                "§8■ §7Mudar nome do clan",
                "§8■ §7Mudar tag do clan",
                "§8■ §7Mudar cargo de membros",
                "§8■ §7Gerenciar fogo amigo",
                "§8■ §7Remover saldo do banco",
                "§8■ §7Gerenciar aliados do clan",
                "§8■ §7Gerenciar rivais do clan",
                "§8■ §7Convidar membros para o clan",
                ""
        ));
        skull.setItemMeta(meta);

        return skull;
    }

    private static ItemStack captain() {

        String value = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMmI4OWIwNTY0YWQwMGM1ZjRkM2M2OTBjNGQxMzFjZmE5NDk2ZGRiNGU3YmY4MDFkOTU3N2JiM2JmNmU4NjNlNSJ9fX0=";
        ItemStack skull = SkullAPI.fromBase64(SkullAPI.Type.BLOCK, value);

        String suffix = "⚓";

        ItemMeta meta = skull.getItemMeta();
        meta.setDisplayName("§2Capitão " + suffix);
        meta.setLore(Arrays.asList(
                "§7Veja as permissões do cargo:",
                "",
                "§8■ §7Mudar cargo de membros",
                "§8■ §7Gerenciar fogo amigo",
                "§8■ §7Remover saldo do banco",
                "§8■ §7Gerenciar aliados do clan",
                "§8■ §7Gerenciar rivais do clan",
                "§8■ §7Convidar membros para o clan",
                ""
        ));
        skull.setItemMeta(meta);

        return skull;
    }

    private static ItemStack reliable() {

        String value = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMmJmMzU4ZGZkN2QwMjc1YWEyMmFlMzQwYjM2NjUzMmMzMTc0MzZjMjM3YzY3ZDhjNzk0ZWUxNmZkZmFhZDExMiJ9fX0=";
        ItemStack skull = SkullAPI.fromBase64(SkullAPI.Type.BLOCK, value);

        String suffix = "🛡";

        ItemMeta meta = skull.getItemMeta();
        meta.setDisplayName("§bConfiável " + suffix);
        meta.setLore(Arrays.asList(
                "§7Veja as permissões do cargo:",
                "",
                "§8■ §7Remover saldo do banco",
                "§8■ §7Convidar membros para o clan",
                ""
        ));
        skull.setItemMeta(meta);

        return skull;
    }

    private static ItemStack member() {

        String value = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZDQ5ZmNlMzQ2YjU3NjYzMWExZmQ0MTlhNmU0MDNlMGFmNjlhY2U0MzNmMjE0YjUzODRiNzM0ODM1N2VlNzQwMiJ9fX0=";
        ItemStack skull = SkullAPI.fromBase64(SkullAPI.Type.BLOCK, value);

        String suffix = "⚔";

        ItemMeta meta = skull.getItemMeta();
        meta.setDisplayName("§7Membro " + suffix);
        meta.setLore(Arrays.asList(
                "§7Este é o cargo inicial!"
        ));
        skull.setItemMeta(meta);

        return skull;
    }

    public static ItemStack back() {
        ItemStack item = new ItemStack(Material.RED_DYE);

        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName("§cVoltar");
        meta.setLore(Arrays.asList(
                "§7Clique para voltar."
        ));
        item.setItemMeta(meta);

        return item;
    }

}
