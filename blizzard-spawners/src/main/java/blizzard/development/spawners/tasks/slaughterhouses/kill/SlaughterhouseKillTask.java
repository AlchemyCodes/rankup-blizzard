package blizzard.development.spawners.tasks.slaughterhouses.kill;

import blizzard.development.spawners.database.cache.getters.SlaughterhouseCacheGetters;
import blizzard.development.spawners.database.cache.getters.SpawnersCacheGetters;
import blizzard.development.spawners.database.cache.setters.SpawnersCacheSetters;
import blizzard.development.spawners.database.storage.SlaughterhouseData;
import blizzard.development.spawners.database.storage.SpawnersData;
import blizzard.development.spawners.handlers.enums.slaughterhouses.States;
import blizzard.development.spawners.handlers.slaughterhouse.SlaughterhouseHandler;
import blizzard.development.spawners.handlers.spawners.SpawnersHandler;
import blizzard.development.spawners.tasks.spawners.mobs.SpawnersMobsTaskManager;
import blizzard.development.spawners.utils.LocationUtil;
import blizzard.development.spawners.utils.SpawnersUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.List;
import java.util.Objects;

public class SlaughterhouseKillTask extends BukkitRunnable {
    private final SlaughterhouseData slaughterhouseData;
    private BukkitTask bukkitTask;

    public SlaughterhouseKillTask(SlaughterhouseData slaughterhouseData) {
        this.slaughterhouseData = slaughterhouseData;
    }

    public void setBukkitTask(BukkitTask bukkitTask) {
        this.bukkitTask = bukkitTask;
    }

    @Override
    public void run() {
        final SlaughterhouseHandler handler = SlaughterhouseHandler.getInstance();
        final SpawnersCacheSetters setters = SpawnersCacheSetters.getInstance();
        final SpawnersUtils utils = SpawnersUtils.getInstance();

        if (!slaughterhouseData.getState().equals(States.ON.getState())) {
            return;
        }

        String nickname = slaughterhouseData.getNickname();
        Player player = Bukkit.getPlayer(nickname);

        List<String> friends = slaughterhouseData.getFriends();
        boolean anyFriendOnline = friends != null && friends.stream()
                .map(Bukkit::getPlayer)
                .filter(Objects::nonNull)
                .anyMatch(Player::isOnline);

        if ((player == null || !player.isOnline()) && !anyFriendOnline) {
            return;
        }

        Location slaughterhouseLocation = LocationUtil.deserializeLocation(slaughterhouseData.getLocation());
        int radius = handler.getKillArea(Integer.parseInt(slaughterhouseData.getTier()));
        int looting = handler.getKillLooting(Integer.parseInt(slaughterhouseData.getTier()));

        if (slaughterhouseLocation == null) {
            return;
        }

        List<SpawnersData> nearbySpawners = LocationUtil.getNearbySpawners(slaughterhouseLocation, radius);

        for (SpawnersData spawnerData : nearbySpawners) {
            boolean isOwner = Objects.equals(spawnerData.getNickname(), slaughterhouseData.getNickname());
            boolean isFriend = spawnerData.getFriends() != null && spawnerData.getFriends().contains(slaughterhouseData.getNickname());

            if (!isOwner && !isFriend) {
                continue;
            }

            if (spawnerData.getMobAmount() <= 0) {
                continue;
            }

            EntityType entityType = utils.getEntityTypeFromSpawner(utils.getSpawnerFromName(spawnerData.getType()));
            Location spawnerLoc = LocationUtil.deserializeLocation(spawnerData.getLocation());

            List<Entity> nearbyEntities = Objects.requireNonNull(spawnerLoc).getWorld().getNearbyEntities(spawnerLoc, radius, radius, radius)
                    .stream()
                    .filter(entity -> entity instanceof LivingEntity)
                    .filter(entity -> entity.getType() == entityType)
                    .filter(entity -> hasSpawnerMeta(entity, spawnerData.getId()))
                    .toList();

            nearbyEntities.forEach(entity -> ((LivingEntity) entity).setHealth(0.0));

            double mobAmount = spawnerData.getMobAmount();
            double drops = mobAmount * (1 + looting);

            setters.addSpawnerDrops(spawnerData.getId(), drops);
            resetMobsAmount(spawnerData, spawnerData.getId());
        }
    }

    public void resetMobsAmount(SpawnersData data, String id) {
        final SpawnersCacheSetters setters = SpawnersCacheSetters.getInstance();
        setters.setSpawnerMobAmout(data.getId(), 0.0);
        SpawnersMobsTaskManager.getInstance().syncMobAmount(id, 0.0);
    }

    private boolean hasSpawnerMeta(Entity entity, String spawnerId) {
        if (entity.hasMetadata("blizzard_spawners-id")) {
            return entity.getMetadata("blizzard_spawners-id").stream()
                    .anyMatch(meta -> meta.asString().equals(spawnerId));
        }
        return false;
    }

    @Override
    public void cancel() {
        super.cancel();
        if (bukkitTask != null) {
            bukkitTask.cancel();
        }
    }
}
