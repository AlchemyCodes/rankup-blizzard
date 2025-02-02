package blizzard.development.farm.farm.adapters;

import blizzard.development.farm.database.cache.methods.StorageCacheMethod;
import blizzard.development.farm.database.cache.methods.ToolCacheMethod;
import blizzard.development.farm.farm.factory.PlantationFactory;
import blizzard.development.farm.managers.PlantationManager;
import blizzard.development.farm.utils.PluginImpl;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.Ageable;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import static blizzard.development.farm.builders.item.ItemBuilder.getPersistentData;

public class PlantationAdapter implements PlantationFactory {

    private static final PlantationAdapter instance = new PlantationAdapter();

    public static PlantationAdapter getInstance() {
        return instance;
    }

    @Override
    public void handleBreakCarrots(Player player, Block block) {
        ItemStack itemStack = player.getInventory().getItemInMainHand();
        String id = getPersistentData(PluginImpl.getInstance().plugin, itemStack, "ferramenta.farm.id");

        int plantations = ToolCacheMethod.getInstance().getPlantations(id);

        StorageCacheMethod.getInstance()
            .addCarrotsAmount(
                player,
                1
            );

        ToolAdapter.getInstance()
            .updateTool(
                player,
                itemStack
            );

        ToolCacheMethod.getInstance()
            .setPlantations(
                id,
                plantations + 1
            );

        PlantationManager.getInstance().queueReplant(block, Material.CARROTS);
    }

    @Override
    public void handleBreakPotatoes(Player player, Block block) {
        ItemStack itemStack = player.getInventory().getItemInMainHand();
        String id = getPersistentData(PluginImpl.getInstance().plugin, itemStack, "ferramenta.farm.id");

        int plantations = ToolCacheMethod.getInstance().getPlantations(id);

        StorageCacheMethod.getInstance()
            .addPotatoesAmount(
                player,
                1
            );

        ToolAdapter.getInstance()
            .updateTool(
                player,
                itemStack
            );

        ToolCacheMethod.getInstance()
            .setPlantations(
                id,
                plantations + 1
            );

        PlantationManager.getInstance().queueReplant(block, Material.POTATOES);
    }

    @Override
    public void handleBreakWheat(Player player, Block block) {
        ItemStack itemStack = player.getInventory().getItemInMainHand();
        String id = getPersistentData(PluginImpl.getInstance().plugin, itemStack, "ferramenta.farm.id");

        int plantations = ToolCacheMethod.getInstance().getPlantations(id);

        StorageCacheMethod.getInstance()
            .addWheatAmount(
                player,
                1
            );

        ToolAdapter.getInstance()
            .updateTool(
                player,
                itemStack
            );

        ToolCacheMethod.getInstance()
            .setPlantations(
                id,
                plantations + 1
            );

        PlantationManager.getInstance().queueReplant(block, Material.WHEAT);
    }

    @Override
    public void handleBreakMelon(Player player, Block block) {
        ItemStack itemStack = player.getInventory().getItemInMainHand();
        String id = getPersistentData(PluginImpl.getInstance().plugin, itemStack, "ferramenta.farm.id");

        int plantations = ToolCacheMethod.getInstance().getPlantations(id);

        StorageCacheMethod.getInstance()
            .addMelonAmount(
                player,
                1
            );

        ToolAdapter.getInstance()
            .updateTool(
                player,
                itemStack
            );

        ToolCacheMethod.getInstance()
            .setPlantations(
                id,
                plantations + 1
            );

        block.setType(Material.AIR);
    }

    @Override
    public void handleBreakCactus(Player player, Block block) {
        ItemStack itemStack = player.getInventory().getItemInMainHand();
        String id = getPersistentData(PluginImpl.getInstance().plugin, itemStack, "ferramenta.farm.id");

        int plantations = ToolCacheMethod.getInstance().getPlantations(id);

        StorageCacheMethod.getInstance()
            .addCactusAmount(
                player,
                1
            );

        ToolAdapter.getInstance()
            .updateTool(
                player,
                itemStack
            );

        ToolCacheMethod.getInstance()
            .setPlantations(
                id,
                plantations + 1
            );

        block.setType(Material.AIR);
    }
}
