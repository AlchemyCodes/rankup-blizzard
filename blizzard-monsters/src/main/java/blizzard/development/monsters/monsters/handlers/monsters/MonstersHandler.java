package blizzard.development.monsters.monsters.handlers.monsters;

import blizzard.development.monsters.database.cache.managers.MonstersCacheManager;
import blizzard.development.monsters.database.dao.MonstersDAO;
import blizzard.development.monsters.database.storage.MonstersData;
import blizzard.development.monsters.utils.PluginImpl;
import blizzard.development.monsters.utils.items.TextAPI;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class MonstersHandler {
    private static MonstersHandler instance;

    public HashMap<Player, List<String>> monsters = new HashMap<>();

    public void addMonster(Player player, List<String> monsterNames) {
        List<String> monstersList = monsters.getOrDefault(player, new ArrayList<>());

        monstersList.addAll(monsterNames);
        monsters.put(player, monstersList);
    }

    public List<String> getMonsters(Player player) {
        return monsters.getOrDefault(player, new ArrayList<>());
    }

    private final PluginImpl plugin = PluginImpl.getInstance();

    public String getType(String monsterName) {
        return plugin.Monsters.getConfig().getString("monsters." + monsterName + ".type");
    }

    public String getDisplayName(String monsterName) {
        return plugin.Monsters.getConfig().getString("monsters." + monsterName + ".monster.display-name");
    }

    public String getSkinValue(String monsterName) {
        return plugin.Monsters.getConfig().getString("monsters." + monsterName + ".monster.skin-value");
    }

    public String getSkinSignature(String monsterName) {
        return plugin.Monsters.getConfig().getString("monsters." + monsterName + ".monster.skin-signature");
    }

    public Integer getLife(String monsterName) {
        return plugin.Monsters.getConfig().getInt("monsters." + monsterName + ".monster.life");
    }

    public Integer getAttackChance(String monsterName) {
        return plugin.Monsters.getConfig().getInt("monsters." + monsterName + ".monster.attack-chance");
    }

    public Integer getAttackDamage(String monsterName) {
        return plugin.Monsters.getConfig().getInt("monsters." + monsterName + ".monster.attack-damage");
    }

    public List<String> getRewards(String monsterName) {
        return plugin.Monsters.getConfig().getStringList("monsters." + monsterName + ".monster.rewards");
    }

    public ConfigurationSection getSection() {
        return plugin.Monsters.getConfig().getConfigurationSection("monsters");
    }

    public Set<String> getAll() {
        if (getSection() == null) return null;

        Set<String> keys = getSection().getKeys(false);
        if (keys.isEmpty()) return null;

        return keys;
    }

    public void createData(Player player, String uuid, String id, String type, String location, Integer life) {
        String owner = player.getName();

        MonstersData monstersData = new MonstersData(
                uuid,
                id,
                type,
                location,
                life,
                owner
        );

        try {
            new MonstersDAO().createMonsterData(monstersData);
            MonstersCacheManager.getInstance().cacheMonsterData(uuid, monstersData);
        } catch (Exception ex) {
            player.sendActionBar(TextAPI.parse("§c§lEI! §cOcorreu um erro ao salvar esse monstro no banco de dados!"));
            ex.printStackTrace();
        }
    }

    public static MonstersHandler getInstance() {
        if (instance == null) instance = new MonstersHandler();
        return instance;
    }
}
