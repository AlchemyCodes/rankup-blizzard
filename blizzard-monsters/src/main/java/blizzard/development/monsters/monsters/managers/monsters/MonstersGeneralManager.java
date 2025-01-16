package blizzard.development.monsters.monsters.managers.monsters;

import blizzard.development.monsters.database.cache.managers.MonstersCacheManager;
import blizzard.development.monsters.database.dao.MonstersDAO;
import blizzard.development.monsters.database.storage.MonstersData;
import blizzard.development.monsters.utils.PluginImpl;
import blizzard.development.monsters.utils.items.TextAPI;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class MonstersGeneralManager {
    private static MonstersGeneralManager instance;

    public HashMap<Player, List<String>> monsters = new HashMap<>();

    public void addMonster(Player player, List<String> monsterNames) {
        List<String> monstersList = monsters.getOrDefault(player, new ArrayList<>());

        monstersList.addAll(monsterNames);
        monsters.put(player, monstersList);
    }

    public List<String> getMonsters(Player player) {
        return monsters.getOrDefault(player, new ArrayList<>());
    }

    public HashMap<Player, Location> monstersLocation = new HashMap<>();
    public HashMap<Player, String> monstersDisplay = new HashMap<>();

    public void addMonsterInfo(Player player, Location location, String displayName) {
        monstersLocation.clear();
        monstersDisplay.clear();

        monstersLocation.put(player, location);
        monstersDisplay.put(player, displayName);
    }

    public Location getMonstersLocation(Player player) {
        return monstersLocation.getOrDefault(player, null);
    }

    public String getMonstersDisplay(Player player) {
        return monstersDisplay.getOrDefault(player, null);
    }

    public boolean isMonster(Player player, String id) {
        for (MonstersData monstersData : MonstersCacheManager.getInstance().monstersCache.values()) {
            if (monstersData.getOwner().equals(player.getName())) {
                if (monstersData.getId().equals(id)) {
                    return true;
                }
            }
        }
        return false;
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

    public String getDamageSound(String monsterName) {
        return plugin.Monsters.getConfig().getString("monsters." + monsterName + ".monster.damage-sound");
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

    public static MonstersGeneralManager getInstance() {
        if (instance == null) instance = new MonstersGeneralManager();
        return instance;
    }
}
