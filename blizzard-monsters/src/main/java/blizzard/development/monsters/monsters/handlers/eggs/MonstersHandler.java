package blizzard.development.monsters.monsters.handlers.eggs;

import blizzard.development.monsters.utils.PluginImpl;

import java.util.List;
import java.util.Objects;
import java.util.Set;

public class MonstersHandler {
    private static MonstersHandler instance;

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

    public Double getLife(String monsterName) {
        return plugin.Monsters.getConfig().getDouble("monsters." + monsterName + ".monster.life");
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

    public Set<String> getAll() {
        return Objects.requireNonNull(
                plugin.Monsters.getConfig().getConfigurationSection("monsters")
        ).getKeys(false);
    }

    public static MonstersHandler getInstance() {
        if (instance == null) instance = new MonstersHandler();
        return instance;
    }
}
