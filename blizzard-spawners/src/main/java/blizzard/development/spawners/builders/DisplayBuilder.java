package blizzard.development.spawners.builders;

import blizzard.development.spawners.handlers.enums.Spawners;
import blizzard.development.spawners.utils.NumberFormat;
import blizzard.development.spawners.utils.items.TextAPI;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.entity.Display;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.TextDisplay;

public class DisplayBuilder {
    public static void createSpawnerDisplay(Location location, String spawnerType, Double amount, Player player) {
        Location displayLoc = location.clone().add(0.5, 1, 0.5);
        TextDisplay display = (TextDisplay) location.getWorld().spawnEntity(displayLoc, EntityType.TEXT_DISPLAY);

        display.setInvulnerable(true);
        display.setPersistent(true);

        display.setAlignment(TextDisplay.TextAlignment.CENTER);
        display.setBillboard(Display.Billboard.CENTER);
        display.setBackgroundColor(Color.fromARGB(0,0,0,0));

        String formattedAmount = NumberFormat.getInstance().formatNumber(amount);

        String spawner;
        if (spawnerType.equals(Spawners.PIG.getType())) {
            spawner = "§dGerador de §lPorco";
        } else if (spawnerType.equals(Spawners.COW.getType())) {
            spawner = "§8Gerador de §lVaca";
        } else {
            spawner = spawnerType;
        }

        String displayText = String.join("\n",
                "",
                spawner,
                "§7Quantidade: §f§l" + formattedAmount + "§fx",
                "§7Dono: §f" + player.getName(),
                ""
        );

        display.text(TextAPI.parse(displayText));

        display.setCustomName(null);
        display.setCustomNameVisible(false);

        display.setPersistent(true);
        display.setDefaultBackground(false);

    }

    public static void removeSpawnerDisplay(Location location) {
        location.getWorld().getNearbyEntities(location.clone().add(0.5, 1, 0.5), 1, 1, 1).forEach(entity -> {
            if (entity instanceof TextDisplay) {
                entity.remove();
            }
        });
    }
}
