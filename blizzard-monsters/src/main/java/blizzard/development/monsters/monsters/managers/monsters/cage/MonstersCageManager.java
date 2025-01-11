package blizzard.development.monsters.monsters.managers.monsters.cage;

import blizzard.development.monsters.database.cache.managers.MonstersCacheManager;
import blizzard.development.monsters.database.dao.MonstersDAO;
import blizzard.development.monsters.database.storage.MonstersData;
import blizzard.development.monsters.monsters.managers.eggs.MonstersEggManager;
import blizzard.development.monsters.monsters.managers.monsters.MonstersGeneralManager;
import blizzard.development.monsters.utils.NumberFormatter;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.sql.SQLException;
import java.util.*;

public class MonstersCageManager {
    private static MonstersCageManager instance;

    public void catchMonster(Player player, String monsterType, boolean all) {
        List<MonstersData> monstersToRemove = new ArrayList<>();
        MonstersCacheManager cacheManager = MonstersCacheManager.getInstance();

        synchronized (cacheManager.monstersCache) {
            for (MonstersData monster : cacheManager.monstersCache.values()) {
                if (monster.getOwner().equals(player.getName()) && monster.getType().equals(monsterType)) {
                    monstersToRemove.add(monster);
                    if (!all) break;
                }
            }
        }

        String monsterDisplay = MonstersGeneralManager.getInstance().getDisplayName(monsterType);

        if (monstersToRemove.isEmpty()) {
            player.sendActionBar("§c§lEI! §cVocê não possui monstros do tipo §7" + monsterDisplay + "§c para capturar.");
            return;
        }

        int totalToCatch = all ? monstersToRemove.size() : 1;

        if (!hasEmptySlots(player, totalToCatch)) {
            player.sendActionBar("§c§lEI! §cVocê não tem espaço suficiente no inventário para isso.");
            return;
        }

        try {
            MonstersDAO dao = new MonstersDAO();

            for (MonstersData monster : monstersToRemove) {
                synchronized (cacheManager.monstersCache) {
                    cacheManager.removeMonsterData(monster.getUuid());
                    dao.deleteMonsterData(monster);
                }

                MonstersEggManager.getInstance().giveEgg(player, monster.getType(), 1);
            }

            Arrays.asList(
                    "",
                    " §b§lMonstros! §7Você capturou os monstros.",
                    " §7O total de §f" + NumberFormatter.getInstance().formatNumber(totalToCatch) + "§7 monstro(s) do tipo",
                    " §f" + monsterDisplay + "§7 foram capturados com sucesso.",
                    ""
            ).forEach(player::sendMessage);

        } catch (SQLException e) {
            e.printStackTrace();
            player.sendActionBar("§c§lErro! §cOcorreu um erro ao capturar os monstros no banco de dados.");
        }
    }

    public void catchAllMonsters(Player player) {
        Map<String, List<MonstersData>> monstersByType = new HashMap<>();
        MonstersCacheManager cacheManager = MonstersCacheManager.getInstance();

        synchronized (cacheManager.monstersCache) {
            for (MonstersData monster : cacheManager.monstersCache.values()) {
                if (monster.getOwner().equals(player.getName())) {
                    monstersByType.computeIfAbsent(monster.getType(), k -> new ArrayList<>()).add(monster);
                }
            }
        }

        int totalMonsters = monstersByType.values().stream().mapToInt(List::size).sum();

        if (totalMonsters <= 0) {
            player.sendActionBar("§c§lEI! §cVocê não possui monstros para capturar.");
            return;
        }

        if (!hasEmptySlots(player, totalMonsters)) {
            player.sendActionBar("§c§lEI! §cVocê não tem espaço suficiente no inventário para isso.");
            return;
        }

        try {
            MonstersDAO dao = new MonstersDAO();

            for (Map.Entry<String, List<MonstersData>> entry : monstersByType.entrySet()) {
                String monsterType = entry.getKey();
                List<MonstersData> monsters = entry.getValue();

                for (MonstersData monster : monsters) {
                    synchronized (cacheManager.monstersCache) {
                        cacheManager.removeMonsterData(monster.getUuid());
                        dao.deleteMonsterData(monster);
                    }
                }

                MonstersEggManager.getInstance().giveEgg(player, monsterType, monsters.size());
            }

            Arrays.asList(
                    "",
                    " §b§lMonstros! §7Você capturou todos os monstros.",
                    " §7O total de §f" + NumberFormatter.getInstance().formatNumber(totalMonsters) + "§7 monstros foram",
                    " §7capturados com sucesso.",
                    ""
            ).forEach(player::sendMessage);

        } catch (SQLException e) {
            e.printStackTrace();
            player.sendMessage("§c§lErro! §cOcorreu um erro ao remover os monstros do banco de dados.");
        }
    }

    public boolean hasEmptySlots(Player player, int monsters) {
        int requiredSlots = (int) Math.ceil((double) monsters / 64);
        int emptySlots = 0;

        for (ItemStack item : player.getInventory().getStorageContents()) {
            if (item == null || item.getType() == Material.AIR) {
                emptySlots++;
            }

            if (emptySlots >= requiredSlots) {
                return true;
            }
        }

        return false;
    }

    public static MonstersCageManager getInstance() {
        if (instance == null) instance = new MonstersCageManager();
        return instance;
    }
}