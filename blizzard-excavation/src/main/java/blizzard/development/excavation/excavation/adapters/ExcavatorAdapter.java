package blizzard.development.excavation.excavation.adapters;

import blizzard.development.excavation.database.dao.ExcavatorDAO;
import blizzard.development.excavation.database.storage.ExcavatorData;
import blizzard.development.excavation.excavation.factory.ExcavatorFactory;
import blizzard.development.excavation.excavation.item.ExcavatorBuildItem;
import org.bukkit.entity.Player;

import java.sql.SQLException;

public class ExcavatorAdapter implements ExcavatorFactory {

    private final ExcavatorDAO excavatorDAO;

    public ExcavatorAdapter(ExcavatorDAO excavatorDAO) {
        this.excavatorDAO = excavatorDAO;
    }

    @Override
    public void give(Player player) {

        ExcavatorData excavatorData = excavatorDAO.findExcavatorData(player.getName());
        ExcavatorBuildItem excavatorBuildItem = new ExcavatorBuildItem();

        if (excavatorData == null) {
            excavatorData = new ExcavatorData(player.getName(), 5, 1, 1);

            try {
                excavatorDAO.createExcavatorData(excavatorData);
            } catch (SQLException exception) {
                exception.printStackTrace();
            }

            player.getInventory().addItem(excavatorBuildItem.buildExcavator());
        } else player.getInventory().addItem(excavatorBuildItem.buildExcavator());
    }

    @Override
    public void giveWithData(Player player) {
        ExcavatorBuildItem excavatorBuildItem = new ExcavatorBuildItem();

        player.getInventory().addItem(excavatorBuildItem.buildExcavator());
    }
}
