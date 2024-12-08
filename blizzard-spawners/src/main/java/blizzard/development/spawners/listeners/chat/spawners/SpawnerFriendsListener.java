package blizzard.development.spawners.listeners.chat.spawners;

import blizzard.development.spawners.database.cache.setters.SpawnersCacheSetters;
import blizzard.development.spawners.database.cache.getters.SpawnersCacheGetters;
import blizzard.development.spawners.inventories.friends.FriendsInventory;
import blizzard.development.spawners.utils.PluginImpl;
import io.papermc.paper.event.player.AsyncChatEvent;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SpawnerFriendsListener implements Listener {

    public static final Map<Player, BukkitRunnable> inviteTimeouts = new HashMap<>();
    public static final Map<Player, String> pendingInvites = new HashMap<>();

    private final SpawnersCacheSetters setters = SpawnersCacheSetters.getInstance();
    private final SpawnersCacheGetters getters = SpawnersCacheGetters.getInstance();

    public static void addPendingInvite(Player player, String spawnerId) {
        if (inviteTimeouts.containsKey(player)) {
            inviteTimeouts.get(player).cancel();
        }

        BukkitRunnable timeoutTask = new BukkitRunnable() {
            @Override
            public void run() {
                player.sendActionBar("§c§lEI! §cO tempo para adicionar um amigo expirou.");
                removePendingInvite(player);
            }
        };
        timeoutTask.runTaskLater(PluginImpl.getInstance().plugin, 20 * 30);
        inviteTimeouts.put(player, timeoutTask);
        pendingInvites.put(player, spawnerId);
    }

    public static void removePendingInvite(Player player) {
        if (inviteTimeouts.containsKey(player)) {
            inviteTimeouts.get(player).cancel();
            inviteTimeouts.remove(player);
        }
        pendingInvites.remove(player);
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onPlayerChat(AsyncChatEvent event) {
        Player player = event.getPlayer();
        if (!inviteTimeouts.containsKey(player)) {
            return;
        }
        event.setCancelled(true);

        String message = PlainTextComponentSerializer.plainText().serialize(event.message());

        if (message.equalsIgnoreCase("cancelar")) {
            player.sendActionBar("§c§lEI! §cVocê cancelou a ação de adicionar amigo.");
            removePendingInvite(player);
            return;
        }

        String spawnerId = pendingInvites.get(player);

        Player friend = Bukkit.getPlayer(message);

        if (friend == null) {
            player.sendActionBar("§c§lEI! §cO jogador informado não está online ou é inválido.");
            return;
        }

        if (getters.getSpawnerFriends(spawnerId).contains(friend.getName())) {
            player.sendActionBar("§c§lEI! §cEste jogador já tem acesso ao gerador.");
            player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 0.5f, 0.5f);
            return;
        }

        if (friend.getName().equalsIgnoreCase(player.getName())) {
            player.sendActionBar("§c§lEI! §cVocê não pode se adicionar ao gerador.");
            player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 0.5f, 0.5f);
            return;
        }

        if (friend.getName().equalsIgnoreCase(getters.getSpawnerOwner(spawnerId))) {
            player.sendActionBar("§c§lEI! §cVocê não pode adicionar o dono do gerador como amigo.");
            player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 0.5f, 0.5f);
            return;
        }

        if (getters.getSpawnerFriends(spawnerId).size() >= getters.getSpawnerFriendsLimit(spawnerId)) {
            player.sendActionBar("§c§lEI! §cO gerador já atingiu o limite máximo de amigos.");
            player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 0.5f, 0.5f);
            return;
        }

        setters.addSpawnerFriend(spawnerId, List.of(friend.getName()));
        player.sendActionBar("§a§lYAY! §aO jogador §7" + message + "§a foi adicionado como amigo ao gerador.");
        player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_YES, 1.0f, 1.0f);

        removePendingInvite(player);
    }
}