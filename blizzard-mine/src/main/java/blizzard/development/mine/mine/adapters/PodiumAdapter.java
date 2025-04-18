package blizzard.development.mine.mine.adapters;

import blizzard.development.core.Main;
import blizzard.development.currencies.database.storage.PlayersData;
import blizzard.development.mine.builders.hologram.HologramBuilder;
import blizzard.development.mine.database.cache.methods.PlayerCacheMethods;
import blizzard.development.mine.database.cache.methods.ToolCacheMethods;
import blizzard.development.mine.database.storage.PlayerData;
import blizzard.development.mine.database.storage.ToolData;
import blizzard.development.mine.mine.enums.LocationEnum;
import blizzard.development.mine.mine.factory.PodiumFactory;
import blizzard.development.mine.tasks.mine.PodiumUpdateTask;
import blizzard.development.mine.utils.locations.LocationUtils;
import blizzard.development.mine.utils.text.NumberUtils;
import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.NPC;
import net.citizensnpcs.api.npc.NPCRegistry;
import net.citizensnpcs.trait.SkinTrait;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.scheduler.BukkitTask;

import java.util.*;

public class PodiumAdapter implements PodiumFactory {

    private static final PodiumAdapter instance = new PodiumAdapter();

    public static PodiumAdapter getInstance() {
        return instance;
    }

    private final PlayerCacheMethods playerCacheMethods = PlayerCacheMethods.getInstance();
    private final List<PlayersData> top = playerCacheMethods.getTopBlocks();
    private final NPCRegistry registry = CitizensAPI.getNPCRegistry();
    private final Set<UUID> podiumNPCs = new HashSet<>();

    private final NumberUtils numberUtils = NumberUtils.getInstance();

    private void createPodiumNPC(PlayersData playersData, List<String> hologramLines, Location location) {
        NPC npc = registry.createNPC(EntityType.PLAYER, playersData.getNickname());
        NPC npc1 = registry.createNPC(EntityType.MINECART, "");

        SkinTrait skinTrait = npc.getOrAddTrait(SkinTrait.class);
        skinTrait.setSkinName(playersData.getNickname());

        npc.spawn(location);
        npc1.spawn(location);
        podiumNPCs.add(npc.getUniqueId());
        podiumNPCs.add(npc1.getUniqueId());

        HologramBuilder.getInstance().createGlobalHologram(
            npc.getUniqueId(),
            location.add(0.0, 3.6, 0.0),
            hologramLines
        );
    }

    @Override
    public void topOne(Location location) {
        if (top.isEmpty()) return;

        createPodiumNPC(
            top.getFirst(),
            Arrays.asList(
                "§e§lO MELHOR!",
                "§f",
                "§7Saldo de Blocos:",
                "§b§l❒§b" + numberUtils.formatNumber(top.getFirst().getBlocks())
            ), location
        );
    }

    @Override
    public void topTwo(Location location) {
        if (top.size() < 2) return;

        createPodiumNPC(
            top.get(1),
            Arrays.asList(
                "§e§lSEGUNDO MELHOR!",
                "§f",
                "§7Saldo de Blocos:",
                "§b§l❒§b" + numberUtils.formatNumber(top.get(1).getBlocks())
            ), location
        );
    }

    @Override
    public void topTree(Location location) {
        if (top.size() < 3) return;

        createPodiumNPC(
            top.get(2),
            Arrays.asList(
                "§e§lTERCEIRO MELHOR!",
                "§f",
                "§7Saldo de Blocos:",
                "§b§l❒§b" + numberUtils.formatNumber(top.get(2).getBlocks())
            ), location
        );
    }

    public void createAllNPCs() {
        Location topOneLocation = LocationUtils.getLocation(LocationEnum.TOP_ONE_NPC.getName());
        Location topTwoLocation = LocationUtils.getLocation(LocationEnum.TOP_TWO_NPC.getName());
        Location topTreeLocation = LocationUtils.getLocation(LocationEnum.TOP_TREE_NPC.getName());

        if (topOneLocation != null && topTwoLocation != null && topTreeLocation != null
        && podiumNPCs.isEmpty()
        ) {
            topOne(topOneLocation);
            topTwo(topTwoLocation);
            topTree(topTreeLocation);

            PodiumUpdateTask podiumUpdateTask = new PodiumUpdateTask();
            BukkitTask task = podiumUpdateTask.runTaskTimer(Main.getInstance(), 20L * 60 * 15, 20L * 60 * 15);
            podiumUpdateTask.setTask(task);
        }
    }

    public Set<UUID> getPodiumNPCs() {
        return Collections.unmodifiableSet(podiumNPCs);
    }

    public void removeAllNPCs() {
        Set<UUID> npcsToRemove = new HashSet<>(podiumNPCs);
        for (UUID uuid : npcsToRemove) {
            removeNPC(uuid);
        }
    }

    public void removeNPC(UUID uuid) {
        NPC npc = registry.getByUniqueId(uuid);
        if (npc != null) {
            npc.destroy();
            HologramBuilder.getInstance().removeHologram(uuid);
            podiumNPCs.remove(uuid);
        }
    }
}