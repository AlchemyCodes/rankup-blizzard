package blizzard.development.plantations.utils.placeholder;

import blizzard.development.plantations.database.cache.methods.PlayerCacheMethod;
import blizzard.development.plantations.managers.AreaManager;
import blizzard.development.plantations.utils.NumberFormat;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class PlaceholderRegistry extends PlaceholderExpansion {
    @Override
    public @NotNull String getIdentifier() {
        return "estufa";
    }

    @Override
    public @NotNull String getAuthor() {
        return "swagviper";
    }

    @Override
    public @NotNull String getVersion() {
        return "1.0";
    }

    @Override
    public boolean persist() {
        return true;
    }

    @Override
    public boolean canRegister() {
        return true;
    }

    @Override
    public String onPlaceholderRequest(Player player, @NotNull String identifier) {
        if (player == null) {
            return "";
        }

        int area = switch (AreaManager.getInstance().getArea(player)) {
            case 10 -> 20;
            case 20 -> 40;
            case 30 -> 60;
            case 40 -> 80;
            case 50 -> 100;
            default ->
                throw new IllegalStateException("Unexpected value: " + AreaManager.getInstance().getArea(player));
        };

        String plantation = switch (AreaManager.getInstance().getAreaPlantation(player)) {
            case "POTATOES" -> "§eBatata";
            case "CARROTS" -> "§6Cenoura";
            case "BEETROOTS" -> "§cTomate";
            case "WHEAT" -> "§eMilho";
            default ->
                throw new IllegalStateException("Unexpected value: " + AreaManager.getInstance().getAreaPlantation(player));
        };

        int seeds = PlayerCacheMethod.getInstance().getPlantations(player);

        return switch (identifier) {
            case "area" -> String.valueOf(area);
            case "seeds" -> NumberFormat.formatNumber(seeds);
            case "plantacao" -> plantation;
              default -> throw new IllegalStateException("Unexpected value: " + identifier);
        };
    }
}
