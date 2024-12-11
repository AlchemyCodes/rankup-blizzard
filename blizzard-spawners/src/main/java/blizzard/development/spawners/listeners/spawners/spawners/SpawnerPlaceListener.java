package blizzard.development.spawners.listeners.spawners.spawners;

import blizzard.development.spawners.builders.DisplayBuilder;
import blizzard.development.spawners.builders.EffectsBuilder;
import blizzard.development.spawners.database.cache.getters.PlayersCacheGetters;
import blizzard.development.spawners.database.cache.getters.SpawnersCacheGetters;
import blizzard.development.spawners.database.cache.managers.SpawnersCacheManager;
import blizzard.development.spawners.database.cache.setters.PlayersCacheSetters;
import blizzard.development.spawners.database.cache.setters.SpawnersCacheSetters;
import blizzard.development.spawners.database.storage.SpawnersData;
import blizzard.development.spawners.handlers.enums.Spawners;
import blizzard.development.spawners.handlers.enums.States;
import blizzard.development.spawners.handlers.spawners.SpawnersHandler;
import blizzard.development.spawners.methods.SpawnersMethods;
import blizzard.development.spawners.tasks.spawners.mobs.SpawnersMobsTaskManager;
import blizzard.development.spawners.utils.*;
import blizzard.development.spawners.builders.ItemBuilder;
import blizzard.development.spawners.utils.items.TextAPI;
import com.plotsquared.core.PlotSquared;
import com.plotsquared.core.plot.Plot;
import com.plotsquared.core.plot.PlotArea;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class SpawnerPlaceListener implements Listener {

    private final CooldownUtils cooldown = CooldownUtils.getInstance();
    private final SpawnersHandler spawnersHandler = SpawnersHandler.getInstance();

    @EventHandler
    private void onSpawnerPlace(BlockPlaceEvent event) {
        Player player = event.getPlayer();
        Block spawnerBlock = event.getBlockPlaced();
        String cooldownName = "blizzard.spawners.place-cooldown";

        if (spawnerBlock.getType().equals(Material.SPAWNER)) {
            if (!LocationUtil.terrainVerify(player, spawnerBlock)) {
                event.setCancelled(true);
                return;
            }

            if (cooldown.isInCountdown(player, cooldownName) && !player.hasPermission("blizzard.spawners.admin")) {
                player.sendActionBar(TextAPI.parse("§c§lEI! §cAguarde um pouco antes de colocar outro spawner."));
                event.setCancelled(true);
                return;
            }

            if (stackSpawners(player, spawnerBlock.getLocation(), player.isSneaking(), 4)) {
                player.sendActionBar(TextAPI.parse("§a§lYAY! §aVocê agrupou esses spawners com sucesso."));
                cooldown.createCountdown(player, cooldownName, 1000, TimeUnit.MILLISECONDS);
                event.setCancelled(true);
                return;
            }

            if (LocationUtil.hasNearbySpawners(spawnerBlock.getLocation(), 5)) {
                player.sendActionBar(TextAPI.parse("§c§lEI! §cJá existe um spawner no raio de 5 blocos."));
                event.setCancelled(true);
                return;
            }

            ItemStack spawnerItem = player.getInventory().getItemInMainHand();
            final String id = UUID.randomUUID().toString().substring(0, 10);
            boolean found = false;

            for (Spawners spawnerType : Spawners.values()) {
                final String key = "blizzard.spawners-" + spawnerType.getType();

                if (ItemBuilder.hasPersistentData(PluginImpl.getInstance().plugin, spawnerItem, key)) {
                    String value = ItemBuilder.getPersistentData(PluginImpl.getInstance().plugin, spawnerItem, key);
                    String type = ItemBuilder.getPersistentData(PluginImpl.getInstance().plugin, spawnerItem, "type");
                    String speed = ItemBuilder.getPersistentData(PluginImpl.getInstance().plugin, spawnerItem, "speed");
                    String lucky = ItemBuilder.getPersistentData(PluginImpl.getInstance().plugin, spawnerItem, "lucky");
                    String experience = ItemBuilder.getPersistentData(PluginImpl.getInstance().plugin, spawnerItem, "experience");

                    if (value == null || speed == null || type == null || lucky == null || experience == null) {
                        event.setCancelled(true);
                        return;
                    }

                    FileConfiguration config = PluginImpl.getInstance().Spawners.getConfig();
                    boolean released = config.getBoolean("spawners." + getSpawnerType(type) + ".permitted-purchase", false);

                    if (!released && !player.hasPermission("blizzard.spawners.admin")) {
                        player.sendActionBar(TextAPI.parse(
                                "§c§lEI! §cEste spawner não está liberado.")
                        );
                        event.setCancelled(true);
                        return;
                    }

                    if (!setupSpawner(player, id, spawnerBlock.getLocation(), spawnerType, Double.parseDouble(value), Integer.parseInt(speed), Integer.parseInt(lucky), Integer.parseInt(experience))) {
                        event.setCancelled(true);
                        return;
                    }

                    found = true;
                    break;
                }
            }

            if (!found) {
                player.sendActionBar(TextAPI.parse("§c§lEI! §cEste é um spawner sem dados."));
                event.setCancelled(true);
                return;
            }

            cooldown.createCountdown(player, cooldownName, 1000, TimeUnit.MILLISECONDS);
        }
    }

    private Boolean setupSpawner(Player player, String id, Location spawnerLocation, Spawners spawner, Double amount, Integer speed, Integer lucky, Integer experience) {
        final PlayersCacheSetters setters = PlayersCacheSetters.getInstance();

        spawnersHandler.createStaticSpawner(spawnerLocation, spawner);

        PlotArea plotArea = PlotSquared.get().getPlotAreaManager().getPlotArea(LocationUtil.getPlotLocation(spawnerLocation.getBlock()));
        if (plotArea == null) return false;

        Plot plot = plotArea.getPlot(LocationUtil.getPlotLocation(spawnerLocation.getBlock()));
        Location mobLocation = spawnerLocation.clone();
        mobLocation.setX(mobLocation.getBlockX() + 0.5);
        mobLocation.setZ(mobLocation.getBlockZ() + 0.5);
        Vector direction = spawnerLocation.getDirection().normalize();
        direction.multiply(-1);
        mobLocation.add(direction.multiply(1.0));
        mobLocation.setDirection(direction);

        if (!SpawnersMethods.createSpawner(
                player,
                spawner,
                id,
                LocationUtil.getSerializedLocation(spawnerLocation),
                LocationUtil.getSerializedLocation(mobLocation),
                States.PRIVATE.getState(),
                String.valueOf(plot.getId()),
                amount,
                0.0,
                0.0,
                speed,
                lucky,
                experience,
                PluginImpl.getInstance().Config.getInt("spawners.initial-friends-limit")
        )) {
            return false;
        }

        SpawnersData spawnerData = SpawnersCacheManager.getInstance().getSpawnerData(id);
        SpawnersMobsTaskManager.getInstance().startTask(spawnerData);

        DisplayBuilder.createSpawnerDisplay(spawnerLocation,
                spawner.getType(),
                amount,
                SpawnersUtils.getInstance().getSpawnerState(States.PRIVATE),
                player.getName()
        );
        EffectsBuilder.createSpawnerEffect(player, spawnerLocation, spawner.getType());

        String formattedAmount = NumberFormat.getInstance().formatNumber(amount);
        player.sendActionBar(TextAPI.parse("§a§lYAY! §aVocê colocou §fx" + formattedAmount + " §aspawner(s) de " + spawner.getType() + "§a!"));
        return true;
    }

    public Boolean stackSpawners(Player player, Location location, boolean sneaking, int radius) {
        final SpawnersCacheSetters setters = SpawnersCacheSetters.getInstance();
        final SpawnersUtils utils = SpawnersUtils.getInstance();

        ItemStack spawnerItem = player.getInventory().getItemInMainHand();

        for (Spawners spawnerType : Spawners.values()) {
            final String key = "blizzard.spawners-" + spawnerType.getType();

            if (ItemBuilder.hasPersistentData(PluginImpl.getInstance().plugin, spawnerItem, key)) {
                String spawnersType = ItemBuilder.getPersistentData(PluginImpl.getInstance().plugin, spawnerItem, "type");
                String spawnersAmount = ItemBuilder.getPersistentData(PluginImpl.getInstance().plugin, spawnerItem, key);
                String spawnersSpeed = ItemBuilder.getPersistentData(PluginImpl.getInstance().plugin, spawnerItem, "speed");
                String spawnersLucky = ItemBuilder.getPersistentData(PluginImpl.getInstance().plugin, spawnerItem, "lucky");
                String spawnersExperience = ItemBuilder.getPersistentData(PluginImpl.getInstance().plugin, spawnerItem, "experience");

                if (spawnersType == null || spawnersAmount == null || spawnersSpeed == null || spawnersLucky == null || spawnersExperience == null) {
                    return false;
                }

                List<SpawnersData> spawnersData = LocationUtil.getNearbySpawners(location, radius);
                if (spawnersData.isEmpty()) return false;

                SpawnersData closestSpawner = spawnersData.get(0);

                if (!spawnersType.equalsIgnoreCase(utils.getSpawnerFromName(closestSpawner.getType()).getType())
                        || Integer.parseInt(spawnersSpeed) != closestSpawner.getSpeedLevel()
                        || Integer.parseInt(spawnersLucky) != closestSpawner.getLuckyLevel()
                        || Integer.parseInt(spawnersExperience) != closestSpawner.getExperienceLevel()) {
                    return false;
                }

                int spawnerItemAmount = player.getInventory().getItemInMainHand().getAmount();
                double amount;

                FileConfiguration config = PluginImpl.getInstance().Spawners.getConfig();
                boolean released = config.getBoolean("spawners." + getSpawnerType(spawnersType) + ".permitted-purchase", false);

                if (!released && !player.hasPermission("blizzard.spawners.admin")) {
                    player.sendActionBar(TextAPI.parse(
                            "§c§lEI! §cEste spawner não está liberado.")
                    );
                    return false;
                }

                if (closestSpawner.getState().equals(States.PRIVATE.getState())
                        && !player.getName().equals(closestSpawner.getNickname())
                        && !player.hasPermission("blizzard.spawners.admin")
                        && !SpawnersCacheGetters.getInstance().getSpawnerFriends(closestSpawner.getId()).contains(player.getName())
                ) {
                    return false;
                }

                if (sneaking) {
                    player.getInventory().setItemInMainHand(null);
                    amount = Double.parseDouble(spawnersAmount) * spawnerItemAmount;
                } else {
                    if (spawnerItemAmount == 1) {
                        player.getInventory().setItemInMainHand(null);
                    } else {
                        player.getInventory().getItemInMainHand().setAmount(spawnerItemAmount - 1);
                    }
                    amount = Double.parseDouble(spawnersAmount);
                }

                setters.setSpawnerAmout(closestSpawner.getId(), closestSpawner.getAmount() + amount);
                SpawnersMobsTaskManager.getInstance().syncMobAmount(closestSpawner.getId(), closestSpawner.getMobAmount());

                DisplayBuilder.removeSpawnerDisplay(Objects.requireNonNull(LocationUtil.deserializeLocation(closestSpawner.getLocation())));
                DisplayBuilder.createSpawnerDisplay(
                        Objects.requireNonNull(LocationUtil.deserializeLocation(closestSpawner.getLocation())),
                        closestSpawner.getType(),
                        closestSpawner.getAmount(),
                        SpawnersUtils.getInstance().getSpawnerState(States.valueOf(closestSpawner.getState().toUpperCase())),
                        closestSpawner.getNickname());
                return true;
            }
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
