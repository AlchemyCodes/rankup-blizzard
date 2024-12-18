package blizzard.development.plantations.managers;

import blizzard.development.plantations.database.cache.methods.PlayerCacheMethod;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.title.Title;
import org.bukkit.entity.Player;

import java.time.Duration;

public class AreaManager {

    private static final AreaManager instance = new AreaManager();
    private final PlayerCacheMethod playerCacheMethod = PlayerCacheMethod.getInstance();

    public void applyAreaUpgrade(Player player) {
        PlantationManager plantationManager = new PlantationManager();

        int radius = playerCacheMethod.getArea(player);

        player.showTitle(
            Title.title(
                Component.text("§a§lAplicando melhoria!"),
                Component.text("§7A sua área foi melhorada."),
                Title.Times.times(Duration.ZERO, Duration.ofSeconds(2), Duration.ofSeconds(3))
            )
        );

        plantationManager.transform(
            player,
            radius
        );
    }

    public void resetArea(Player player) {
        PlantationManager plantationManager = new PlantationManager();

        int radius = playerCacheMethod.getArea(player);

        player.showTitle(
            Title.title(
                Component.text("§a§lÁrea resetada!"),
                Component.text("§7A sua área foi resetada."),
                Title.Times.times(Duration.ZERO, Duration.ofSeconds(2), Duration.ofSeconds(3))
            )
        );

        plantationManager.reset(
            player,
            radius
        );
    }

    public void setArea(Player player, int radius) {
        playerCacheMethod.setArea(player, radius);
    }

    public Integer getArea(Player player) {
        return playerCacheMethod.getArea(player);
    }

    public static AreaManager getInstance() {
        return instance;
    }

}
