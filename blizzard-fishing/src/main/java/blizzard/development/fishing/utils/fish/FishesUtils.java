package blizzard.development.fishing.utils.fish;

import blizzard.development.fishing.Main;
import blizzard.development.fishing.database.cache.methods.PlayersCacheMethod;
import blizzard.development.fishing.database.cache.methods.RodsCacheMethod;
import blizzard.development.fishing.enums.RodMaterials;
import blizzard.development.fishing.utils.NumberFormat;
import blizzard.development.fishing.utils.PluginImpl;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.PacketContainer;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;

public class FishesUtils {

    private static FishesUtils instance;
    Plugin plugin = PluginImpl.getInstance().plugin;
    private final Random random = new Random();
    private final YamlConfiguration config = PluginImpl.getInstance().Config.getConfig();
    private final YamlConfiguration enchantmentsConfig = PluginImpl.getInstance().Enchantments.getConfig();
    private final YamlConfiguration messagesConfig = PluginImpl.getInstance().Messages.getConfig();

    public HashMap<RodMaterials, Player> activeSkin = new HashMap<>();
    public HashMap<Player, Boolean> specialistActive = new HashMap<>();

    public void setActiveSkin(Player player, RodMaterials material) {
        activeSkin.entrySet().removeIf(entry -> entry.getValue().equals(player));

        activeSkin.put(material, player);
    }

    public boolean getActiveSkin(RodMaterials material) {
        return activeSkin.containsKey(material);
    }

    public RodMaterials getPlayerMaterial(Player player) {
        for (RodMaterials material : activeSkin.keySet()) {
            if (activeSkin.get(material).equals(player)) {
                return material;
            }
        }
        return null;
    }

    public int getStrengthNecessary(String rarity) {
        return switch (rarity.toLowerCase()) {
            case "common" -> config.getInt("strengthNeeded.common");
            case "rare" -> config.getInt("strengthNeeded.rare");
            case "legendary" -> config.getInt("strengthNeeded.legendary");
            case "mystic" -> config.getInt("strengthNeeded.mystic");
            default -> 0;
        };
    }

    public int getFishXp(String rarity) {
        return switch (rarity.toLowerCase()) {
            case "common" -> config.getInt("xpByRarity.common");
            case "rare" -> config.getInt("xpByRarity.rare");
            case "legendary" -> config.getInt("xpByRarity.legendary");
            case "mystic" -> config.getInt("xpByRarity.mystic");
            case "frozen" -> config.getInt("xpByRarity.frozen");
            default -> 0;
        };
    }

    public int getFishValue(String rarity) {
        return switch (rarity.toLowerCase()) {
            case "common" -> config.getInt("valueByRarity.common");
            case "rare" -> config.getInt("valueByRarity.rare");
            case "legendary" -> config.getInt("valueByRarity.legendary");
            case "mystic" -> config.getInt("valueByRarity.mystic");
            case "frozen" -> config.getInt("valueByRarity.frozen");
            default -> 0;
        };
    }

    public String getRarity(int playerStrength) {
        Random random = new Random();
        int chance = random.nextInt(101);

        if (playerStrength >= getStrengthNecessary("mystic")) {
            if (chance <= 50) return "common";
            else if (chance <= 80) return "rare";
            else if (chance <= 95) return "legendary";
            else return "mystic";
        }
        else if (playerStrength >= getStrengthNecessary("legendary")) {
            if (chance <= 60) return "common";
            else if (chance <= 85) return "rare";
            else return "legendary";
        }
        else if (playerStrength >= getStrengthNecessary("rare")) {
            if (chance <= 70) return "common";
            else return "rare";
        }
        else {
            return "common";
        }
    }

    public List<String> getFishes(String rarity) {
        List<String> fishes = new ArrayList<>();

        for (String fish : config.getConfigurationSection("fishes").getKeys(false)) {

            String fishRarity = config.getString("fishes." + fish + ".rarity");
            assert fishRarity != null;

            if (fishRarity.equalsIgnoreCase(rarity)) {
                fishes.add(fish);
            }
        }

        return fishes;
    }

    public String getFishName(String fish) {
        switch (fish) {
            case "bacalhau" -> fish = "Bacalhau";
            case "salmao" -> fish = "Salmão";
            case "caranguejo" -> fish = "Caranguejo";
            case "lagosta" -> fish = "Lagosta";
            case "lula" -> fish = "Lula";
            case "lula_brilhante" -> fish = "Lula Brilhante";
            case "tubarao" -> fish = "Tubarão";
            case "baleia" -> fish = "Baleia";
            case "congelado" -> fish = "Peixe Congelado";
        }
        return fish;
    }

    public void giveFish(Player player, String fish, PlayersCacheMethod cacheMethod, int fishQuantity) {
            switch (fish) {
                case "bacalhau" -> cacheMethod.setBacalhau(player, cacheMethod.getBacalhau(player) + fishQuantity);
                case "salmao" ->  cacheMethod.setSalmao(player, cacheMethod.getSalmao(player) + fishQuantity);
                case "caranguejo" -> cacheMethod.setCaranguejo(player, cacheMethod.getCaranguejo(player) + fishQuantity);
                case "lagosta" -> cacheMethod.setLagosta(player, cacheMethod.getLagosta(player) + fishQuantity);
                case "lula" -> cacheMethod.setLula(player, cacheMethod.getLula(player) + fishQuantity);
                case "lula_brilhante" -> cacheMethod.setLulaBrilhante(player, cacheMethod.getLulaBrilhante(player) + fishQuantity);
                case "tubarao" -> cacheMethod.setTubarao(player, cacheMethod.getTubarao(player) + fishQuantity);
                case "baleia" -> cacheMethod.setBaleia(player, cacheMethod.getBaleia(player) + fishQuantity);
                case "congelado" -> cacheMethod.setFrozenFish(player, cacheMethod.getFrozenFish(player) + fishQuantity);
            }
    }

    public String getRandomFish() {
        String[] fishes = {"bacalhau", "salmao", "caranguejo", "lagosta", "lula", "lula_brilhante", "tubarao", "baleia"};

        int index = random.nextInt(fishes.length);
        return fishes[index];
    }

    public void giveXp(Player player, String rarity, RodsCacheMethod rodsCacheMethod) {
        rodsCacheMethod.setXp(player, getXp(player, rarity, rodsCacheMethod));

        if (hasXpForLeveling(player)) {
            rodsCacheMethod.setXp(player, 0);
            rodsCacheMethod.setStrength(player, rodsCacheMethod.getStrength(player) + 1);
            player.sendMessage(messagesConfig.getString("pesca.upouForca"));
        }
    }

    public double getXp(Player player, String rarity, RodsCacheMethod rodsCacheMethod) {
        double xp = FishesUtils.getInstance().getFishXp(rarity) * fishQuantity(player, rodsCacheMethod) * valuesWithSpecialistActive(player);

        xp = (xp + rodsCacheMethod.getExperienced(player)) * getPlayerMaterial(player).getBonus();

        return xp;
    }

    public double xpNecessaryForLeveling(Player player) {
        double initialXp = config.getDouble("level.initial");
        double xpPerLevel = config.getDouble("level.perLevel");
        double xpMultiplier = config.getDouble("level.multiplier");


        return (initialXp + (RodsCacheMethod.getInstance().getStrength(player) * xpPerLevel)) * xpMultiplier;
    }

    public boolean hasXpForLeveling(Player player) {
        return RodsCacheMethod.getInstance().getXp(player) > xpNecessaryForLeveling(player);
    }

    public void giveFrozenFish(Player player, PlayersCacheMethod cacheMethod, RodsCacheMethod rodsCacheMethod, int quantity) {
        getFishMessage(player, messagesConfig, "§bPeixe Congelado§f", getXp(player, "frozen", rodsCacheMethod), quantity);
        cacheMethod.setFrozenFish(player, cacheMethod.getFrozenFish(player) + fishQuantity(player, rodsCacheMethod) * valuesWithSpecialistActive(player));
        giveXp(player, "frozen", rodsCacheMethod);
        player.sendMessage("§7[DEBUG] §fSeu xp: §a" + rodsCacheMethod.getXp(player));
    }

    public void getFishMessage(Player player, YamlConfiguration config, String caughtFish, double xp, int quantity) {
        String message = config.getString("pesca.action");
        if (message != null) {
            player.sendActionBar(message
                    .replace("{quantity}", quantity + "")
                    .replace("{fishname}", caughtFish)
                    .replace("{xp}", NumberFormat.getInstance().formatNumber(xp)));
        }
    }

    public int fishQuantity(Player player, RodsCacheMethod rodsCacheMethod) {
        int rand = new Random().nextInt(101);

        if (rand <= enchantmentsConfig.getInt("enchantments.rod.sortudo.chance") * rodsCacheMethod.getLucky(player)) {
            return 2;
        }
        return 1;
    }

    public void startDiamondRain(Player player, RodsCacheMethod rodsCacheMethod) {
        YamlConfiguration messagesConfig = PluginImpl.getInstance().Messages.getConfig();
        int rand = new Random().nextInt(101);

        if (isStorageFull(player, PlayersCacheMethod.getInstance())) return;

        if (rand <= enchantmentsConfig.getInt("enchantments.rod.especialista.chance") * rodsCacheMethod.getSpecialist(player)) {

            player.sendTitle(messagesConfig.getString("pesca.specialist.title"),
                    messagesConfig.getString("pesca.specialist.sub-title"));

            specialistActive.put(player, true);

            new BukkitRunnable() {
                public void run() {
                    specialistActive.remove(player);
                }
            }.runTaskLater(Main.getInstance(), 20L * 10);

            createDiamondRain(player);
        }
    }

    public int valuesWithSpecialistActive(Player player) {
        if (specialistActive.containsKey(player)) {
            return 2;
        }
        return 1;
    }

    public boolean isStorageFull(Player player, PlayersCacheMethod cacheMethod) {
        if (cacheMethod.getFishes(player) >= cacheMethod.getStorage(player)) {
            player.sendMessage("§cBalde cheio");
            return true;
        }
        return false;
    }

    public void createDiamondRain(Player player) {
        Location playerLoc = player.getLocation();
        World world = player.getWorld();

        new BukkitRunnable() {
            int timeElapsed = 0;

            @Override
            public void run() {
                if (timeElapsed >= 10 * 20) {
                    this.cancel();
                    return;
                }

                if (timeElapsed % (20 /2) == 0) {
                    int diamondsPerWave = 20;

                    for (int i = 0; i < diamondsPerWave; i++) {
                        double x = playerLoc.getX() + (Math.random() * 20) - 10;
                        double z = playerLoc.getZ() + (Math.random() * 20) - 10;
                        double y = playerLoc.getY() + 15;

                        Location diamondLoc = new Location(world, x, y, z);

                        Item fakeDiamond = world.dropItem(diamondLoc, new ItemStack(Material.DIAMOND));
                        fakeDiamond.setPickupDelay(Integer.MAX_VALUE);

                        for (Player p : Bukkit.getOnlinePlayers()) {
                            if (p != player) {
                                p.hideEntity(plugin, fakeDiamond);
                            }
                        }

                        Bukkit.getScheduler().runTaskLater(plugin, fakeDiamond::remove, 60L);
                    }
                }

                timeElapsed++;
            }
        }.runTaskTimer(plugin, 0L, 1L);
    }


    public static FishesUtils getInstance() {
        if (instance == null) {
            instance = new FishesUtils();
        }
        return instance;
    }
}