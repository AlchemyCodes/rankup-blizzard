package blizzard.development.excavation.database.cache.methods;

import blizzard.development.excavation.database.cache.ExcavatorCacheManager;
import blizzard.development.excavation.database.dao.ExcavatorDAO;
import blizzard.development.excavation.database.storage.ExcavatorData;
import blizzard.development.excavation.managers.BatchManager;
import org.bukkit.entity.Player;

import static blizzard.development.excavation.database.cache.ExcavatorCacheManager.excavatorCache;

public class ExcavatorCacheMethod {

    private static final ExcavatorDAO excavatorDAO = new ExcavatorDAO();
    private static final ExcavatorCacheManager excavatorCacheManager = new ExcavatorCacheManager();

    public static void createExcavator(Player player) {
        String nickname = player.getName();

        ExcavatorData excavatorData = new ExcavatorData(
                nickname, 1, 1, 1
        );

        try {
            BatchManager.addToPendingQueue(excavatorData);
            ExcavatorCacheManager.cacheExcavatorData(nickname, excavatorData);
        } catch (Exception exception) {
            player.sendMessage("§cOcorreu um erro ao salvar essa ferramenta no banco de dados!");
            exception.printStackTrace();
        }
    }

    public int effiencyEnchant(String nickname) {
        ExcavatorCacheManager excavatorCacheManager = new ExcavatorCacheManager();
        ExcavatorData excavatorData = excavatorCacheManager.getExcavatorData(nickname);

        if (excavatorData != null) {
            excavatorCache.put(nickname, excavatorData);
            return excavatorData.getEfficiency();
        }

        return 0;
    }

    public void setEfficiencyEnchant(String nickname, int amount) {
        ExcavatorData excavatorData = excavatorCacheManager.getExcavatorData(nickname);

        if (excavatorData != null) {
            excavatorData.setEfficiency(amount);
            excavatorCache.put(nickname, excavatorData);
        }
    }

    public int agilityEnchant(String nickname) {
        ExcavatorData excavatorData = excavatorCacheManager.getExcavatorData(nickname);

        if (excavatorData != null) {
            excavatorCache.put(nickname, excavatorData);
            return excavatorData.getAgility();
        }

        return 0;
    }

    public void setAgilityEnchant(String nickname, int amount) {
        ExcavatorData excavatorData = excavatorCacheManager.getExcavatorData(nickname);

        if (excavatorData != null) {
            excavatorData.setAgility(amount);
            excavatorCache.put(nickname, excavatorData);
        }
    }

    public int extractorEnchant(String nickname) {
        ExcavatorData excavatorData = excavatorCacheManager.getExcavatorData(nickname);

        if (excavatorData != null) {
            excavatorCache.put(nickname, excavatorData);
            return excavatorData.getExtractor();
        }

        return 0;
    }

    public void setExtractorEnchant(String nickname, int amount) {
        ExcavatorData excavatorData = excavatorCacheManager.getExcavatorData(nickname);

        if (excavatorData != null) {
            excavatorData.setExtractor(amount);
            excavatorCache.put(nickname, excavatorData);
        }
    }
}