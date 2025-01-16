package blizzard.development.monsters.monsters.managers.monsters.attack;

import blizzard.development.monsters.builders.hologram.HologramBuilder;
import blizzard.development.monsters.database.cache.managers.MonstersCacheManager;
import blizzard.development.monsters.database.cache.methods.PlayersCacheMethods;
import blizzard.development.monsters.database.dao.MonstersDAO;
import blizzard.development.monsters.database.storage.MonstersData;
import blizzard.development.monsters.monsters.managers.monsters.MonstersGeneralManager;
import blizzard.development.monsters.monsters.managers.monsters.rewards.MonstersRewardManager;
import blizzard.development.monsters.monsters.managers.packets.MonstersPacketsManager;
import blizzard.development.monsters.utils.CooldownUtils;
import lombok.SneakyThrows;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class MonstersDeathManager {
    private static MonstersDeathManager instance;

    private final MonstersGeneralManager generalManager = MonstersGeneralManager.getInstance();

    public void killMonster(Player player, MonstersData monstersData, String displayName) {
        CooldownUtils cooldown = CooldownUtils.getInstance();
        String cooldownName = "blizzard.monsters.kill-cooldown";

        if (cooldown.isInCountdown(player, cooldownName)) return;

        HologramBuilder.getInstance().removeHologram(UUID.fromString(monstersData.getUuid()));
        MonstersPacketsManager.getInstance().removeMonster(
                player,
                Integer.parseInt(monstersData.getId())
        );

        handleData(player, monstersData);

        Arrays.asList(
                "",
                " §3§lMonstros! §f✧ §7Você derrotou um " + displayName + "§7.",
                " §7Verifique suas recompensas em §f´/monstros´§7.",
                ""
        ).forEach(player::sendMessage);

        player.playSound(player.getLocation(), Sound.ENTITY_DRAGON_FIREBALL_EXPLODE, 1.0f, 1.0f);

        handleRewards(player, monstersData.getType());

        cooldown.createCountdown(
                player,
                cooldownName,
                500,
                TimeUnit.MILLISECONDS
        );
    }

    @SneakyThrows
    private void handleData(Player player, MonstersData monstersData) {
        MonstersCacheManager.getInstance().removeMonsterData(
                monstersData.getUuid()
        );
        new MonstersDAO().deleteMonsterData(monstersData);
        generalManager.getMonsters(
                player
        ).remove(monstersData.getUuid());
    }

    private void handleRewards(Player player, String monster) {
        List<String> rewards = generalManager.getRewards(monster);

        if (rewards == null || rewards.isEmpty()) return;

        MonstersRewardManager rewardManager = MonstersRewardManager.getInstance();

        Set<String> validRewards = rewardManager.getAll();

        if (validRewards == null || validRewards.isEmpty()) return;

        List<String> validRewardsList = rewards.stream()
                .filter(validRewards::contains)
                .toList();

        if (validRewardsList.isEmpty()) return;

        PlayersCacheMethods.getInstance().addRewards(player, validRewardsList);
    }


    public static MonstersDeathManager getInstance() {
        if (instance == null) instance = new MonstersDeathManager();
        return instance;
    }
}
