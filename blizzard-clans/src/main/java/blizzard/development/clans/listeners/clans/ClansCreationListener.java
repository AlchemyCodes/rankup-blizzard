package blizzard.development.clans.listeners.clans;

import blizzard.development.clans.utils.gradient.TextUtil;
import io.papermc.paper.event.player.AsyncChatEvent;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.scheduler.BukkitRunnable;
import blizzard.development.clans.methods.ClansMethods;
import blizzard.development.clans.managers.EconomyManager;
import blizzard.development.clans.utils.PluginImpl;

import java.util.HashMap;
import java.util.Map;

public class ClansCreationListener implements Listener {

    public static final Map<Player, String[]> pendingCreates = new HashMap<>();
    public static final Map<Player, BukkitRunnable> createTimeouts = new HashMap<>();

    public static void addPendingCreate(Player player) {
        pendingCreates.put(player, new String[2]);

        if (createTimeouts.containsKey(player)) {
            createTimeouts.get(player).cancel();
        }

        BukkitRunnable timeoutTask = new BukkitRunnable() {
            @Override
            public void run() {
                if (pendingCreates.containsKey(player)) {
                    player.sendMessage("§cTempo para a criação do clã expirou.");
                    removePendingCreate(player);
                }
            }
        };
        timeoutTask.runTaskLater(PluginImpl.getInstance().plugin, 20 * 30);
        createTimeouts.put(player, timeoutTask);
    }

    public static void removePendingCreate(Player player) {
        pendingCreates.remove(player);
        if (createTimeouts.containsKey(player)) {
            createTimeouts.get(player).cancel();
            createTimeouts.remove(player);
        }
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onPlayerChat(AsyncChatEvent event) {
        Player player = event.getPlayer();
        if (!pendingCreates.containsKey(player)) {
            return;
        }
        event.setCancelled(true);

        String message = PlainTextComponentSerializer.plainText().serialize(event.message());
        if (message.equalsIgnoreCase("cancelar")) {
            player.sendMessage(TextUtil.parse("§aVocê cancelou a criação do clã com sucesso!"));
            removePendingCreate(player);
            return;
        }

        String[] inputs = message.split(" ");
        if (inputs.length != 2) {
            player.sendMessage(TextUtil.parse("§cFormato inválido. Utilize: <tag> <nome>. Exemplo: QDR Quadrado"));
            return;
        }

        String tag = inputs[0];
        String name = inputs[1];

        String userClan = ClansMethods.getUserClan(player);

        if (userClan != null) {
            player.sendMessage("§cVocê já está em um clan!");
            return;
        }

        if (tag.length() != 3) {
            player.sendMessage("§cA tag do seu clan deve ter somente §73 §ccaracteres!");
        } else if (name.length() > 15) {
            player.sendMessage("§cO nome do seu clan deve ter no máximo §710 §ccaracteres!");
        } else if (tag.contains("&") || name.contains("&")) {
            player.sendMessage("§cA tag e o nome do seu clan não podem conter o caractere §7'&'§c!");
        } else if (ClansMethods.isClanTagBlacklisted(tag)) {
            player.sendMessage("§cA tag informada não pode ser utilizada!");
        } else if (ClansMethods.isClanNameBlacklisted(name)) {
            player.sendMessage("§cO nome informado não pode ser utilizado!");
        } else if (!ClansMethods.isClanTagAvailable(tag) || !ClansMethods.isClanTagUnique(tag)) {
            player.sendMessage("§cJá existe um clan com a tag §7" + tag);
        } else if (!ClansMethods.isClanNameAvailable(name) || !ClansMethods.isClanNameUnique(name)) {
            player.sendMessage("§cJá existe um clan com o nome §7" + name);
        } else {

            double price = PluginImpl.getInstance().Config.getDouble("clans.create-price");

            if (EconomyManager.hasEnough(player, price)) {
                EconomyManager.withdraw(player, price);
                ClansMethods.createClan(player, tag, name);

                for (Player broadcast : Bukkit.getOnlinePlayers()) {
                    broadcast.sendActionBar(
                            TextUtil.parse("<bold><#04ff00> YAY! <#04ff00></bold><#04ff00>O clan " + name + " [" + tag + "] foi criado. <#55ff55>"));
                }

                player.sendMessage("§aVocê criou o clan §7" + name + "§a e gastou §2$§7" + price);
            } else {
                player.sendMessage("§cVocê precisa de §2$§7" + price + "§c para criar um clan!");
            }
            removePendingCreate(player);
        }
    }
}
