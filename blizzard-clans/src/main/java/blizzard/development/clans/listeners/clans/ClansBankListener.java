package blizzard.development.clans.listeners.clans;

import blizzard.development.clans.managers.EconomyManager;
import blizzard.development.clans.utils.gradient.TextUtil;
import io.papermc.paper.event.player.AsyncChatEvent;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.scheduler.BukkitRunnable;
import blizzard.development.clans.methods.ClansMethods;
import blizzard.development.clans.utils.NumberFormat;
import blizzard.development.clans.utils.PluginImpl;
import blizzard.development.clans.database.cache.ClansCacheManager;

import java.util.HashMap;
import java.util.Map;

public class ClansBankListener implements Listener {

    public static final Map<Player, String> pendingTransactions = new HashMap<>();
    public static final Map<Player, BukkitRunnable> transactionTimeouts = new HashMap<>();
    public static final Map<String, Long> clanTransactionCooldowns = new HashMap<>();
    private static final long COOLDOWN_TIME = 5000;

    public static void addPendingTransaction(Player player, String transactionType) {
        String clan = ClansMethods.getUserClan(player);
        if (clan == null) {
            player.sendMessage("§cVocê não está em um clan!");
            return;
        }

        pendingTransactions.put(player, transactionType);

        if (transactionTimeouts.containsKey(player)) {
            transactionTimeouts.get(player).cancel();
        }

        BukkitRunnable timeoutTask = new BukkitRunnable() {
            @Override
            public void run() {
                if (pendingTransactions.containsKey(player)) {
                    player.sendMessage("§cTempo para a transação expirou.");
                    removePendingTransaction(player);
                }
            }
        };
        timeoutTask.runTaskLater(PluginImpl.getInstance().plugin, 20 * 30);
        transactionTimeouts.put(player, timeoutTask);
    }

    public static void removePendingTransaction(Player player) {
        pendingTransactions.remove(player);
        if (transactionTimeouts.containsKey(player)) {
            transactionTimeouts.get(player).cancel();
            transactionTimeouts.remove(player);
        }
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onPlayerChat(AsyncChatEvent event) {
        Player player = event.getPlayer();
        if (!pendingTransactions.containsKey(player)) {
            return;
        }
        event.setCancelled(true);

        String message = PlainTextComponentSerializer.plainText().serialize(event.message());
        if (message.equalsIgnoreCase("cancelar")) {
            player.sendMessage(TextUtil.parse("§aVocê cancelou a transação com sucesso!"));
            removePendingTransaction(player);
            return;
        }

        String transactionType = pendingTransactions.get(player);
        try {
            String clan = ClansMethods.getUserClan(player);
            long currentTime = System.currentTimeMillis();
            if (clanTransactionCooldowns.containsKey(clan)) {
                long lastTransactionTime = clanTransactionCooldowns.get(clan);
                if ((currentTime - lastTransactionTime) < COOLDOWN_TIME) {
                    player.sendMessage("§cVocê deve esperar antes de realizar outra transação!");
                    return;
                }
            }

            double parsedAmount = NumberFormat.parseNumber(message);

            if (NumberFormat.isInvalid(parsedAmount) || parsedAmount <= 0) {
                player.sendMessage("§cPor favor, insira uma quantia válida.");
                return;
            }

            long amount = (long) parsedAmount;

            if (clan == null) {
                player.sendMessage("§cVocê não está em um clan!");
                removePendingTransaction(player);
                return;
            }

            if (transactionType.equals("deposit")) {
                if (EconomyManager.hasEnough(player, amount)) {
                    EconomyManager.withdraw(player, amount);
                    ClansMethods.depositMoney(clan, amount);
                    player.sendMessage("§aVocê depositou §2$§7" + NumberFormat.formatNumber(amount) + "§a coins ao clan!");
                } else {
                    player.sendMessage("§cVocê não possui §2$§7" + NumberFormat.formatNumber(amount) + "§c coins!");
                }

            } else if (transactionType.equals("withdraw")) {
                long clanMoney = ClansCacheManager.getClansData(clan).getMoney();

                if (clanMoney >= amount) {
                    ClansMethods.withdrawMoney(clan, amount);
                    EconomyManager.deposit(player, amount);
                    player.sendMessage("§aVocê sacou §2$§7" + NumberFormat.formatNumber(amount) + "§a coins do clan!");
                } else {
                    player.sendMessage("§cO clan não possui §2$§7" + NumberFormat.formatNumber(amount) + "§c coins!");
                }
            }

            clanTransactionCooldowns.put(clan, System.currentTimeMillis());
            removePendingTransaction(player);
        } catch (IllegalArgumentException e) {
            player.sendMessage("§cPor favor, insira uma quantia válida!");
        }
    }
}
