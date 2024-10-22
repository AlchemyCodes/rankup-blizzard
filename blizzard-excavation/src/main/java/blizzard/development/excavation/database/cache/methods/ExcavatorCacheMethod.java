package blizzard.development.excavation.database.cache.methods;

import blizzard.development.excavation.database.cache.ExcavatorCacheManager;
import blizzard.development.excavation.database.dao.ExcavatorDAO;
import blizzard.development.excavation.database.storage.ExcavatorData;
import blizzard.development.excavation.managers.BatchManager;
import org.bukkit.entity.Player;

public class ExcavatorCacheMethod {

    private static final ExcavatorDAO excavatorDAO = new ExcavatorDAO();
    private static final ExcavatorCacheManager excavatorCacheManager = new ExcavatorCacheManager();

    public static void createExcavator(Player player) {
        String nickname = player.getName();

        ExcavatorData excavatorData = new ExcavatorData(
                nickname, 5, 1, 1
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
            return excavatorData.getEfficiency();
        }

        return 0;
    }

    public void setEfficiencyEnchant(String nickname, int amount) {
        ExcavatorData excavatorData = excavatorCacheManager.getExcavatorData(nickname);

        if (excavatorData != null) {
            excavatorData.setEfficiency(excavatorData.getEfficiency() + amount);
        }

    }

    public int agilityEnchant(String nickname) {
        ExcavatorData excavatorData = excavatorCacheManager.getExcavatorData(nickname);

        if (excavatorData != null) {
            return excavatorData.getAgility();
        }

        return 0;
    }

    public void setAgilityEnchant(String nickname, int amount) {
        ExcavatorData excavatorData = excavatorCacheManager.getExcavatorData(nickname);

        if (excavatorData != null) {
            excavatorData.setAgility(excavatorData.getAgility() + amount);
        }

    }

    public int extractorEnchant(String nickname) {
        ExcavatorData excavatorData = excavatorCacheManager.getExcavatorData(nickname);

        if (excavatorData != null) {
            return excavatorData.getExtractor();
        }

        return 0;
    }

    public void setExtractorEnchant(String nickname, int amount) {
        ExcavatorData excavatorData = excavatorCacheManager.getExcavatorData(nickname);

        if (excavatorData != null) {
            excavatorData.setExtractor(excavatorData.getExtractor() + amount);
        }

    }
}
