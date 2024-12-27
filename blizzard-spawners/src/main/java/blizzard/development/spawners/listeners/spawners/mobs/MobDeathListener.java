package blizzard.development.spawners.listeners.spawners.mobs;

import blizzard.development.spawners.database.cache.managers.SpawnersCacheManager;
import blizzard.development.spawners.database.cache.setters.PlayersCacheSetters;
import blizzard.development.spawners.database.cache.setters.SpawnersCacheSetters;
import blizzard.development.spawners.database.storage.SpawnersData;
import blizzard.development.spawners.handlers.enchantments.EnchantmentsHandler;
import blizzard.development.spawners.handlers.enums.spawners.Enchantments;
import blizzard.development.spawners.handlers.rewards.RewardsHandler;
import blizzard.development.spawners.tasks.others.HologramsTask;
import blizzard.development.spawners.tasks.spawners.mobs.SpawnersMobsTaskManager;
import blizzard.development.spawners.utils.LocationUtil;
import blizzard.development.spawners.utils.SpawnersUtils;
import blizzard.development.spawners.utils.items.TextAPI;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Objects;
import java.util.Random;

public class MobDeathListener implements Listener {
    private final SpawnersCacheManager manager = SpawnersCacheManager.getInstance();
    private final SpawnersCacheSetters setters = SpawnersCacheSetters.getInstance();

    @EventHandler
    public void onMobDeath(EntityDeathEvent event) {
        Entity mob = event.getEntity();

        if (!mob.hasMetadata("blizzard_spawners-mob") || !mob.hasMetadata("blizzard_spawners-id")) {
            return;
        }

        String spawnerId = mob.getMetadata("blizzard_spawners-id").get(0).asString();
        SpawnersData data = manager.getSpawnerData(spawnerId);

        if (data == null) {
            return;
        }

        if (event.getEntity().getKiller() == null) {
            event.getDrops().clear();
            event.setDroppedExp(0);
            return;
        }

        Player player = event.getEntity().getKiller();
        final EnchantmentsHandler handler = EnchantmentsHandler.getInstance();

        event.getDrops().clear();
        event.setDroppedExp(
                SpawnersUtils.getInstance().getSpawnerDroppedXP(data)
                        * data.getExperienceLevel()
                        * handler.getPerLevel(Enchantments.EXPERIENCE.getName())
        );

        addDrops(player, data, data.getMobAmount());
        resetMobsAmount(data, spawnerId);
        checkReward(player, data);
    }

    public void resetMobsAmount(SpawnersData data, String id) {
        setters.setSpawnerMobAmout(data.getId(), 0.0);
        SpawnersMobsTaskManager.getInstance().syncMobAmount(id, 0.0);
    }

    public void addDrops(Player player, SpawnersData data, double amount) {
        ItemStack tool = player.getInventory().getItemInMainHand();
        int lootingLevel = tool.getEnchantments().getOrDefault(Enchantment.LOOT_BONUS_MOBS, 0);
        setters.addSpawnerDrops(data.getId(), (amount * (1 + lootingLevel)));
        PlayersCacheSetters.getInstance().addKilledMobs(player, amount);
    }

    private void checkReward(Player player, SpawnersData data) {
        Random random = new Random();
        int chance = random.nextInt(100) + 1;

        final EnchantmentsHandler enchantmentsHandler = EnchantmentsHandler.getInstance();
        final RewardsHandler rewardsHandler = RewardsHandler.getInstance();

        int luckyLevel = data.getLuckyLevel();
        int baseChance = enchantmentsHandler.getPerLevel(Enchantments.LUCKY.getName()) * luckyLevel;

        if (chance <= baseChance) {
            RewardsHandler.Reward reward = rewardsHandler.giveRandomReward(player);

            if (reward != null) {
                HologramsTask.initializeHologramTask(
                        player,
                        Objects.requireNonNull(LocationUtil.deserializeLocation(data.getMobLocation())),
                        Material.getMaterial(reward.material()),
                        reward.display()
                );
                player.sendActionBar(TextAPI.parse("§a§lYAY! §aVocê encontrou uma recompensa matando mobs."));
            }
        }
    }
}