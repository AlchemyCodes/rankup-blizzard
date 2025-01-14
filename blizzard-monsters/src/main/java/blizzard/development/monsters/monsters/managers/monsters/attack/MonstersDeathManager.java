package blizzard.development.monsters.monsters.managers.monsters.attack;

import blizzard.development.monsters.builders.hologram.HologramBuilder;
import blizzard.development.monsters.database.dao.MonstersDAO;
import blizzard.development.monsters.database.storage.MonstersData;
import blizzard.development.monsters.monsters.managers.packets.MonstersPacketsManager;
import blizzard.development.monsters.utils.CooldownUtils;
import blizzard.development.monsters.utils.PluginImpl;
import lombok.SneakyThrows;
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

        handleData(monstersData);

        player.sendMessage("vose mato un monstro " + displayName);

        cooldown.createCountdown(
                player,
                cooldownName,
                500,
                TimeUnit.MILLISECONDS
        );
    }

    @SneakyThrows
    private void handleData(MonstersData monstersData) {
        MonstersDAO monstersDAO = new MonstersDAO();
        monstersDAO.deleteMonsterData(monstersData);
    }

    public static MonstersDeathManager getInstance() {
        if (instance == null) instance = new MonstersDeathManager();
        return instance;
    }
}
