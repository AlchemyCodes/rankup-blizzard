package blizzard.development.mail.listeners;

import blizzard.development.mail.database.methods.PlayerMethods;
import blizzard.development.mail.utils.MailUtils;
import blizzard.development.mail.utils.PluginImpl;
import blizzard.development.mail.utils.items.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class ItemsListener implements Listener {
    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        MailUtils mailUtils = MailUtils.getInstance();
        PlayerMethods playerMethods = PlayerMethods.getInstance();

        Player player = event.getPlayer();
        ItemStack item = player.getItemInHand();

        String itemNameByMaterial = mailUtils.getItemNameByMaterial(item.getType());

        Material material = mailUtils.getItemMaterial(itemNameByMaterial);
        String pdc = mailUtils.getItemPdc(itemNameByMaterial);

        if (item.getType() != material) {
            return;
        }

        if (!ItemBuilder.hasPersistentData(PluginImpl.getInstance().plugin, item, pdc)) {
            return;
        }

        playerMethods.addToList(player, mailUtils.getItemNameByMaterial(material));

        item.setAmount(item.getAmount() - 1);
    }
}
