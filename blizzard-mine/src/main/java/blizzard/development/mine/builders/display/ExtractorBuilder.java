package blizzard.development.mine.builders.display;

import blizzard.development.mine.utils.PluginImpl;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.EnderCrystal;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.ItemDisplay;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;

public class ExtractorBuilder {

    private static final ExtractorBuilder instance = new ExtractorBuilder();
    public static ExtractorBuilder getInstance() {
        return instance;
    }

    private EnderCrystal extractor;

    public void removeExtractor() {
        if (extractor != null) {
            try {
                extractor.remove();
            } catch (Exception e) {
                e.printStackTrace();
            }
            extractor = null;
        }
    }

    public void createExtractor(Location location) {
        removeExtractor();

        String extractor_metadata = "blizzard_mine_extractor-metadata";

        extractor = (EnderCrystal) location.getWorld().spawnEntity(location.add(0, 0.9, 0), EntityType.ENDER_CRYSTAL);
        extractor.setMetadata(
            extractor_metadata,
            new FixedMetadataValue(
                PluginImpl.getInstance().plugin,
                extractor
            )
        );
        extractor.setInvulnerable(true);
        extractor.setPersistent(true);
    }

    public void initializeExtractor(Location location) {
        if (extractor == null) {
            createExtractor(location);
        }
    }
}
