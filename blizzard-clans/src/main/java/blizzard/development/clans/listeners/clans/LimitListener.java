package blizzard.development.clans.listeners.clans;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import blizzard.development.clans.builders.LimitBuilder;
import blizzard.development.clans.database.cache.ClansCacheManager;
import blizzard.development.clans.database.cache.PlayersCacheManager;
import blizzard.development.clans.database.storage.ClansData;
import blizzard.development.clans.database.storage.PlayersData;
import blizzard.development.clans.enums.Roles;
import blizzard.development.clans.methods.ClansMethods;
import blizzard.development.clans.utils.NBTUtils;
import blizzard.development.clans.utils.PluginImpl;

public class LimitListener implements Listener {

    @EventHandler
    public void onInteractEvent(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        Action action = event.getAction();

        ItemStack item = player.getInventory().getItemInMainHand();

        if (NBTUtils.startsWith(item, LimitBuilder.data)) {

            if (action.isLeftClick()) {
                return;
            }

            if (action.isRightClick()) {

                String clan = ClansMethods.getUserClan(player);

                if (clan == null) {
                    player.sendMessage("§cVocê não está em nenhum clan!");
                    event.setCancelled(true);
                    return;
                }

                PlayersData playersData = PlayersCacheManager.getPlayerData(player);

                boolean isOwner = ClansMethods.isOwner(clan, player);
                boolean isLeader = playersData.getRole().equals(Roles.LEADER.getName());

                if (!isOwner && !isLeader) {
                    player.sendMessage("§cVocê não tem permissão para aumentar o limite de membros do seu clan!");
                    event.setCancelled(true);
                    return;
                }

                Inventory inventory = player.getInventory();

                String[] parts = NBTUtils.getTag(item).split(LimitBuilder.data);
                int amount = Integer.parseInt(parts[1]);

                ClansData data = ClansMethods.getClan(clan);

                int clanMax = data.getMax();
                int max = PluginImpl.getInstance().Config.getInt("clans.max-members");
                int remaining = max - clanMax;

                if ((clanMax + amount) > max) {
                    if (remaining != 0) {
                        if (item.getAmount() == 0) {
                            inventory.removeItem(item);
                            player.getInventory().addItem(LimitBuilder.createLimit(player, amount - remaining));
                            ClansCacheManager.addMaxClanMembers(clan, remaining);
                            player.sendMessage("§aLimite de membros do seu clan aumentado para " + (clanMax + remaining));
                        } else {
                            item.setAmount(item.getAmount() - 1);
                            player.getInventory().addItem(LimitBuilder.createLimit(player, amount - remaining));
                            ClansCacheManager.addMaxClanMembers(clan, remaining);
                            player.sendMessage("§aLimite de membros do seu clan aumentado para §7" + (clanMax + remaining));
                        }
                    } else {
                        player.sendMessage("§cExcedeu limite de membros do clan §7[" + max + "]§c!");
                    }
                    event.setCancelled(true);
                    return;
                }

                if (item.getAmount() == 0) {
                    inventory.removeItem(item);
                    ClansCacheManager.addMaxClanMembers(clan, amount);
                    player.sendMessage("§aLimite de membros do seu clan aumentado para " + (clanMax + amount));
                } else {
                    item.setAmount(item.getAmount() - 1);
                    ClansCacheManager.addMaxClanMembers(clan, amount);
                    player.sendMessage("§aLimite de membros do seu clan aumentado para §7" + (clanMax + amount));
                }

            }
            event.setCancelled(true);
        }
    }
}
