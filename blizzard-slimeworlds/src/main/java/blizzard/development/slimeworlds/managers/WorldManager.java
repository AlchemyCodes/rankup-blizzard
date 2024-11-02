package blizzard.development.slimeworlds.managers;

import blizzard.development.slimeworlds.Main;
import blizzard.development.slimeworlds.tasks.SchematicTask;
import com.infernalsuite.aswm.api.SlimePlugin;
import com.infernalsuite.aswm.api.loaders.SlimeLoader;
import com.infernalsuite.aswm.api.world.SlimeWorld;
import com.infernalsuite.aswm.api.world.properties.SlimeProperties;
import com.infernalsuite.aswm.api.world.properties.SlimePropertyMap;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class WorldManager {

    private static WorldManager instance;
    private final Map<UUID, String> worlds = new HashMap<>();

    public void createWorld(Player player) throws Exception {
        String name = "mundo_" + player.getName();


        SlimePropertyMap properties = new SlimePropertyMap();
        properties.setValue(SlimeProperties.DIFFICULTY, "normal");
        properties.setValue(SlimeProperties.SPAWN_X, 0);
        properties.setValue(SlimeProperties.SPAWN_Y, 100);
        properties.setValue(SlimeProperties.SPAWN_Z, 0);
        properties.setValue(SlimeProperties.ALLOW_ANIMALS, false);
        properties.setValue(SlimeProperties.ALLOW_MONSTERS, false);
        properties.setValue(SlimeProperties.DRAGON_BATTLE, false);
        properties.setValue(SlimeProperties.PVP, false);
        properties.setValue(SlimeProperties.ENVIRONMENT, "normal");
        properties.setValue(SlimeProperties.WORLD_TYPE, "DEFAULT");
        properties.setValue(SlimeProperties.DEFAULT_BIOME, "minecraft:plains");


        SlimePlugin slimePlugin = (SlimePlugin) Bukkit.getServer().getPluginManager().getPlugin("SlimeWorldManager");
        SlimeLoader loader = slimePlugin.getLoader("file");

        SlimeWorld world = slimePlugin.createEmptyWorld(
                loader,
                name,
                false,
                properties
        );

        slimePlugin.loadWorld(world, true);

        worlds.put(player.getUniqueId(), name);

        Location location = new Location(Bukkit.getWorld(name), 0, 100, 0);

        SchematicTask schematicTask = new SchematicTask();
        schematicTask.schematic(
                name,
                location
        );

        start(player, location);
    }

    private void start(Player player, Location location) {
        new BukkitRunnable() {
            private int seconds = 5;

            @Override
            public void run() {
                if (seconds > 0) {
                    player.sendTitle(
                            "§a§l" + seconds,
                            "",
                            10,
                            70,
                            20
                    );
                    seconds--;
                } else {
                    player.sendTitle(
                            "§a§lMundo criado!",
                            "",
                            10,
                            70,
                            20
                    );

                    player.teleport(location);
                    cancel();
                }
            }
        }.runTaskTimer(Main.getInstance(), 0L, 20L);
    }

    public static WorldManager getInstance() {
        if (instance == null) instance = new WorldManager();
        return instance;
    }
}
