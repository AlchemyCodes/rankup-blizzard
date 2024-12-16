package blizzard.development.spawners.listeners.limits;

import blizzard.development.spawners.builders.ItemBuilder;
import blizzard.development.spawners.database.cache.getters.SpawnersCacheGetters;
import blizzard.development.spawners.database.cache.managers.SpawnersCacheManager;
import blizzard.development.spawners.database.cache.setters.SpawnersCacheSetters;
import blizzard.development.spawners.database.storage.SpawnersData;
import blizzard.development.spawners.handlers.enums.spawners.States;
import blizzard.development.spawners.handlers.limits.LimitsHandler;
import blizzard.development.spawners.utils.CooldownUtils;
import blizzard.development.spawners.utils.LocationUtil;
import blizzard.development.spawners.utils.PluginImpl;
import blizzard.development.spawners.utils.items.TextAPI;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.concurrent.TimeUnit;

public class FriendsLimitInteractListener implements Listener {

    private final SpawnersCacheManager cache = SpawnersCacheManager.getInstance();
    private final CooldownUtils cooldown = CooldownUtils.getInstance();
    private final String key = "blizzard.spawners-friendslimit";

    @EventHandler
    private void onFriendsLimitPlace(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        Block limitBlock = event.getClickedBlock();
        ItemStack limitItem = player.getInventory().getItemInMainHand();
        String cooldownName = "blizzard.spawners.limits.place-cooldown";

        if (limitItem.getType().equals(Material.NETHER_STAR) && ItemBuilder.hasPersistentData(PluginImpl.getInstance().plugin, limitItem, key)) {

            if (event.getAction().equals(Action.RIGHT_CLICK_AIR) || event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
                event.setCancelled(true);

                if (cooldown.isInCountdown(player, cooldownName) && !player.hasPermission("blizzard.spawners.admin")) {
                    player.sendActionBar(TextAPI.parse("§c§lEI! §cAguarde um pouco antes de usar outro limite."));
                    event.setCancelled(true);
                    return;
                }

                if (limitBlock == null  || limitBlock.getType().equals(Material.AIR)) return;

                if (addFriendsLimit(player, limitBlock.getLocation(), player.isSneaking())) {
                    player.sendActionBar(TextAPI.parse("§a§lYAY! §aVocê aumentou o limite de amigos desse spawner com sucesso."));
                    cooldown.createCountdown(player, cooldownName, 1000, TimeUnit.MILLISECONDS);
                    event.setCancelled(true);
                }

                cooldown.createCountdown(player, cooldownName, 1000, TimeUnit.MILLISECONDS);
            }
        }
    }

    public Boolean addFriendsLimit(Player player, Location location, boolean sneaking) {
        final SpawnersCacheSetters setters = SpawnersCacheSetters.getInstance();
        final SpawnersCacheGetters getters = SpawnersCacheGetters.getInstance();
        ItemStack limitItem = player.getInventory().getItemInMainHand();

        if (limitItem.getType().equals(Material.NETHER_STAR) && ItemBuilder.hasPersistentData(PluginImpl.getInstance().plugin, limitItem, key)) {
            String limitAmount = ItemBuilder.getPersistentData(PluginImpl.getInstance().plugin, limitItem, key);
            if (limitAmount == null) {
                return false;
            }

            String serializedLocation = LocationUtil.getSerializedLocation(location);

            SpawnersData data = null;
            for (SpawnersData spawner : cache.spawnersCache.values()) {
                if (spawner.getLocation().equals(serializedLocation)) {
                    data = spawner;
                    break;
                }
            }

            if (data == null) {
                player.sendActionBar(TextAPI.parse("§c§lEI! §cVocê precisa utilizar este limite em um spawner."));
                return false;
            }

            SpawnersData closestSpawner = data;
            int limitsItemAmount = player.getInventory().getItemInMainHand().getAmount();

            FileConfiguration config = PluginImpl.getInstance().Spawners.getConfig();
            boolean released = config.getBoolean("spawners." + getSpawnerType(closestSpawner.getType()) + ".permitted-purchase", false);

            if (!released && !player.hasPermission("blizzard.spawners.admin")) {
                player.sendActionBar(TextAPI.parse("§c§lEI! §cEste spawner não está liberado."));
                return false;
            }

            if (closestSpawner.getState().equals(States.PRIVATE.getState())
                    && !player.getName().equals(closestSpawner.getNickname())
                    && !player.hasPermission("blizzard.spawners.admin")
                    && !SpawnersCacheGetters.getInstance().getSpawnerFriends(closestSpawner.getId()).contains(player.getName())) {
                player.sendActionBar(TextAPI.parse("§c§lEI! §cVocê não pode interagir com este spawner."));
                return false;
            }

            int maxLimit = 10;
            int currentLimit = getters.getSpawnerFriendsLimit(closestSpawner.getId());
            int remainingLimit = maxLimit - currentLimit;

            if (remainingLimit <= 0) {
                player.sendActionBar(TextAPI.parse("§c§lEI! §cEste spawner está com o limite de amigos no máximo."));
                return false;
            }

            double amount;
            if (sneaking) {
                amount = Double.parseDouble(limitAmount) * limitsItemAmount;
                player.getInventory().setItemInMainHand(null);
            } else {
                amount = Double.parseDouble(limitAmount);
                ItemStack currentItem = player.getInventory().getItemInMainHand();
                if (currentItem.getAmount() > 1) {
                    currentItem.setAmount(currentItem.getAmount() - 1);
                } else {
                    player.getInventory().setItemInMainHand(null);
                }
            }

            if (amount <= remainingLimit) {
                setters.addSpawnerFriendsLimit(closestSpawner.getId(), (int) amount);

                player.sendActionBar(TextAPI.parse(
                        "§a§lYAY! §aVocê adicionou §f+" + amount + " §alimite(s) de amigo no seu gerador.")
                );
            } else {
                setters.addSpawnerFriendsLimit(closestSpawner.getId(), remainingLimit);

                double remainingAmount = amount - remainingLimit;

                LimitsHandler.giveFriendsLimit(player, remainingAmount, 1);

                player.sendActionBar(TextAPI.parse(
                        "§a§lYAY! §aVocê adicionou §f+" + remainingLimit + " §alimite(s) de amigo no seu gerador.")
                );
            }

            return true;
        }
        return false;
    }


    public String getSpawnerType(String spawner) {
        return switch (spawner) {
            case "PIG", "pig", "PORCO", "porco" -> "pig";
            case "COW", "cow", "VACA", "vaca" -> "cow";
            case "MOOSHROOM", "mooshroom", "Coguvaca", "coguvaca" -> "mooshroom";
            case "SHEEP", "sheep", "OVELHA", "ovelha" -> "sheep";
            case "ZOMBIE", "zombie", "ZUMBI", "zumbi" -> "zombie";
            default -> null;
        };
    }
}
