package blizzard.development.clans.listeners.clans;

import blizzard.development.clans.utils.gradient.TextUtil;
import io.papermc.paper.event.player.AsyncChatEvent;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.scheduler.BukkitRunnable;
import blizzard.development.clans.methods.ClansMethods;
import blizzard.development.clans.utils.PluginImpl;
import blizzard.development.clans.database.cache.ClansCacheManager;

import java.util.HashMap;
import java.util.Map;

public class ClansChangesListener implements Listener {

    public static final Map<Player, String> pendingChanges = new HashMap<>();
    public static final Map<Player, BukkitRunnable> changeTimeouts = new HashMap<>();

    public static void addPendingChange(Player player, String changeType) {
        String clan = ClansMethods.getUserClan(player);
        if (clan == null) {
            player.sendMessage("§cVocê não está em um clan!");
            return;
        }

        pendingChanges.put(player, changeType);

        if (changeTimeouts.containsKey(player)) {
            changeTimeouts.get(player).cancel();
        }

        BukkitRunnable timeoutTask = new BukkitRunnable() {
            @Override
            public void run() {
                if (pendingChanges.containsKey(player)) {
                    player.sendMessage("§cTempo para a mudança expirou.");
                    removePendingChange(player);
                }
            }
        };
        timeoutTask.runTaskLater(PluginImpl.getInstance().plugin, 20 * 30);
        changeTimeouts.put(player, timeoutTask);
    }

    public static void removePendingChange(Player player) {
        pendingChanges.remove(player);
        if (changeTimeouts.containsKey(player)) {
            changeTimeouts.get(player).cancel();
            changeTimeouts.remove(player);
        }
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onPlayerChat(AsyncChatEvent event) {
        Player player = event.getPlayer();
        if (!pendingChanges.containsKey(player)) {
            return;
        }
        event.setCancelled(true);

        String message = PlainTextComponentSerializer.plainText().serialize(event.message());
        if (message.equalsIgnoreCase("cancelar")) {
            player.sendMessage(TextUtil.parse("§aVocê cancelou a mudança com sucesso!"));
            removePendingChange(player);
            return;
        }

        String changeType = pendingChanges.get(player);
        String clan = ClansMethods.getUserClan(player);

        if (clan == null) {
            player.sendMessage("§cVocê não está em um clan!");
            removePendingChange(player);
            return;
        }

        if (changeType.equals("changeTag")) {
            changeClanTag(player, clan, message);
        } else if (changeType.equals("changeName")) {
            changeClanName(player, clan, message);
        }
    }

    private void changeClanTag(Player player, String clan, String newTag) {
        if (newTag.length() != 3) {
            player.sendMessage("§cA tag do seu clan deve ter somente 3 caracteres!");
        } else if (newTag.contains("&")) {
            player.sendMessage("§cA tag do seu clan não pode conter o caractere §7'&'§c!");
        } else if (ClansMethods.isClanTagBlacklisted(newTag)) {
            player.sendMessage("§cA tag informada não pode ser utilizada!");
        } else if (ClansCacheManager.getClansData(clan).getTag().equals(newTag)) {
            player.sendMessage("§cA tag informada já é a do seu clan!");
        } else if (!ClansMethods.isClanTagAvailable(newTag) || !ClansMethods.isClanTagUnique(newTag)) {
            player.sendMessage("§cJá existe um clan com a tag §7" + newTag);
        } else {
            ClansCacheManager.setClanTag(clan, newTag);
            player.sendMessage("§aTag do clan alterada com sucesso para: §7" + newTag);
            removePendingChange(player);
        }
    }

    private void changeClanName(Player player, String clan, String newName) {
        if (newName.length() > 10) {
            player.sendMessage("§cO nome do seu clan deve ter no máximo 10 caracteres!");
        } else if (newName.contains("&")) {
            player.sendMessage("§cO nome do seu clan não pode conter o caractere §7'&'§c!");
        } else if (ClansMethods.isClanNameBlacklisted(newName)) {
            player.sendMessage("§cO nome informado não pode ser utilizado!");
        } else if (ClansCacheManager.getClansData(clan).getName().equals(newName)) {
            player.sendMessage("§cO nome informado já é o do seu clan!");
        } else if (!ClansMethods.isClanNameAvailable(newName) || !ClansMethods.isClanNameUnique(newName)) {
            player.sendMessage("§cJá existe um clan com o nome §7" + newName);
        } else {
            ClansCacheManager.setClanName(clan, newName);
            player.sendMessage("§aNome do clan alterado com sucesso para: §7" + newName);
            removePendingChange(player);
        }
    }
}
