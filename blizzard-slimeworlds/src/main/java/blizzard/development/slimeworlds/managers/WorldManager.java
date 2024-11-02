package blizzard.development.slimeworlds.managers;

import blizzard.development.slimeworlds.Main;
import blizzard.development.slimeworlds.api.SlimeWorldAPI;
import blizzard.development.slimeworlds.tasks.SchematicTask;
import com.grinderwolf.swm.api.SlimePlugin;
import com.infernalsuite.aswm.api.AdvancedSlimePaperAPI;
import com.infernalsuite.aswm.api.loaders.SlimeLoader;
import com.infernalsuite.aswm.api.world.SlimeWorld;
import com.infernalsuite.aswm.api.world.properties.SlimeProperties;
import com.infernalsuite.aswm.api.world.properties.SlimePropertyMap;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class WorldManager {

    private static WorldManager instance;
    private final Map<UUID, String> worlds = new HashMap<>();

    public void createWorld(Player player) throws Exception {
        String name = "mundo_".concat(player.getName()).concat("_").concat(player.getUniqueId().toString().substring(0, 8));

        AdvancedSlimePaperAPI asp = AdvancedSlimePaperAPI.instance();

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


        Plugin plugin = Bukkit.getPluginManager().getPlugin("SlimeWorldManager");
        SlimePlugin slimePlugin = (SlimePlugin) plugin;

        SlimeLoader loader = (SlimeLoader) slimePlugin.getLoader("slime-worlds");

        SlimeWorld world = asp.createEmptyWorld(
                name,
                false,
                properties,
                loader
        );

        SlimeWorld mirror = asp.loadWorld(world, true);
        asp.saveWorld(world);


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
                            "" + seconds,
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
