package blizzard.development.spawners.listeners.drops;

import blizzard.development.spawners.builders.ItemBuilder;
import blizzard.development.spawners.database.cache.getters.SpawnersCacheGetters;
import blizzard.development.spawners.database.cache.managers.SpawnersCacheManager;
import blizzard.development.spawners.database.cache.setters.SpawnersCacheSetters;
import blizzard.development.spawners.database.storage.SpawnersData;
import blizzard.development.spawners.handlers.enums.spawners.States;
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

public class DropsAutoSellInteractListener implements Listener {
    private final SpawnersCacheManager cache = SpawnersCacheManager.getInstance();
    private final CooldownUtils cooldown = CooldownUtils.getInstance();
    private final String key = "blizzard.spawners-autosell";

    @EventHandler
    private void onDropsAutoSellPlace(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        Block limitBlock = event.getClickedBlock();
        ItemStack limitItem = player.getInventory().getItemInMainHand();
        String cooldownName = "blizzard.spawners.autosell.place-cooldown";

        if (limitItem.getType().equals(Material.ENDER_EYE) && ItemBuilder.hasPersistentData(PluginImpl.getInstance().plugin, limitItem, key)) {


            if (event.getAction().equals(Action.RIGHT_CLICK_AIR) || event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
                event.setCancelled(true);

                if (cooldown.isInCountdown(player, cooldownName) && !player.hasPermission("blizzard.spawners.admin")) {
                    player.sendActionBar(TextAPI.parse("§c§lEI! §cAguarde um pouco antes de usar outro ativador."));
                    event.setCancelled(true);
                    return;
                }

                if (limitBlock == null  || limitBlock.getType().equals(Material.AIR)) return;

                if (activateAutoSell(player, limitBlock.getLocation())) {
                    player.sendActionBar(TextAPI.parse("§a§lYAY! §aVocê ativou o auto-vender neste spawner."));
                    cooldown.createCountdown(player, cooldownName, 1000, TimeUnit.MILLISECONDS);
                    event.setCancelled(true);
                }

                cooldown.createCountdown(player, cooldownName, 1000, TimeUnit.MILLISECONDS);
            }
        }
    }

    public Boolean activateAutoSell(Player player, Location location) {
        final SpawnersCacheSetters setters = SpawnersCacheSetters.getInstance();
        ItemStack autoSellItem = player.getInventory().getItemInMainHand();

        if (autoSellItem.getType().equals(Material.ENDER_EYE) && ItemBuilder.hasPersistentData(PluginImpl.getInstance().plugin, autoSellItem, key)) {
            String limitAmount = ItemBuilder.getPersistentData(PluginImpl.getInstance().plugin, autoSellItem, key);
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
                player.sendActionBar(TextAPI.parse("§c§lEI! §cVocê precisa utilizar este ativador em um spawner."));
                return false;
            }

            SpawnersData closestSpawner = data;

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

            boolean autoSell = closestSpawner.getAutoSell();

            if (autoSell) {
                player.sendActionBar(TextAPI.parse("§c§lEI! §cEste spawner já está com auto-vender ativo."));
                return false;
            }

            ItemStack currentItem = player.getInventory().getItemInMainHand();
            if (currentItem.getAmount() > 1) {
                currentItem.setAmount(currentItem.getAmount() - 1);
                setters.setDropsAutoSell(closestSpawner.getId(), true);
            } else {
                player.getInventory().setItemInMainHand(null);
                setters.setDropsAutoSell(closestSpawner.getId(), true);
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
