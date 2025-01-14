package blizzard.development.monsters.monsters.managers.monsters.attack;

import blizzard.development.monsters.builders.hologram.HologramBuilder;
import blizzard.development.monsters.database.cache.managers.MonstersCacheManager;
import blizzard.development.monsters.database.dao.MonstersDAO;
import blizzard.development.monsters.database.storage.MonstersData;
import blizzard.development.monsters.monsters.managers.monsters.MonstersGeneralManager;
import blizzard.development.monsters.monsters.managers.packets.MonstersPacketsManager;
import blizzard.development.monsters.utils.CooldownUtils;
import blizzard.development.monsters.utils.PluginImpl;
import lombok.SneakyThrows;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class MonstersDeathManager {
    private static MonstersDeathManager instance;

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

        player.sendMessage("vose mato un monstro " + displayName);
        player.playSound(player.getLocation(), Sound.ENTITY_DRAGON_FIREBALL_EXPLODE, 1.0f, 1.0f);

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
        MonstersGeneralManager.getInstance().getMonsters(
                player
        ).remove(monstersData.getUuid());
    }

    private void handleReward() {
    }

    public static MonstersDeathManager getInstance() {
        if (instance == null) instance = new MonstersDeathManager();
        return instance;
    }
}
