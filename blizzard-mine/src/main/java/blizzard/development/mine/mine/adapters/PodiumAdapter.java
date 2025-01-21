package blizzard.development.mine.mine.adapters;

import blizzard.development.mine.builders.hologram.HologramBuilder;
import blizzard.development.mine.database.cache.methods.PlayerCacheMethods;
import blizzard.development.mine.database.storage.PlayerData;
import blizzard.development.mine.mine.factory.PodiumFactory;
import blizzard.development.mine.utils.NumberFormat;
import com.destroystokyo.paper.profile.PlayerProfile;
import com.destroystokyo.paper.profile.ProfileProperty;
import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.NPC;
import net.citizensnpcs.api.npc.NPCRegistry;
import net.citizensnpcs.trait.SkinTrait;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import java.util.*;

public class PodiumAdapter implements PodiumFactory {

    private static final PodiumAdapter instance = new PodiumAdapter();

    public static PodiumAdapter getInstance() {
        return instance;
    }

    private final PlayerCacheMethods playerCacheMethods = PlayerCacheMethods.getInstance();
    private final List<PlayerData> top = playerCacheMethods.getTopBlocks(3);
    private final NPCRegistry registry = CitizensAPI.getNPCRegistry();
    private final Set<UUID> podiumNPCs = new HashSet<>();

    private void createPodiumNPC(Player viewer, PlayerData playerData, List<String> hologramLines, Location location) {
        NPC npc = registry.createNPC(EntityType.PLAYER, playerData.getNickname());
        NPC npc1 = registry.createNPC(EntityType.MINECART, "");

        SkinTrait skinTrait = npc.getOrAddTrait(SkinTrait.class);
        skinTrait.setSkinName(playerData.getNickname());

        viewer.sendMessage(skinTrait.getSkinName());

        npc.spawn(location);
        npc1.spawn(location);
        podiumNPCs.add(npc.getUniqueId());
        podiumNPCs.add(npc1.getUniqueId());

        HologramBuilder.getInstance().createHologram(
            viewer,
            npc.getUniqueId(),
            location.add(0.0, 3.6, 0.0),
            hologramLines,
            false
        );
    }

    @Override
    public void topOne(Player player) {
        if (top.isEmpty()) return;

        createPodiumNPC(
            player,
            top.get(0),
            Arrays.asList(
                "§e§lO MELHOR!",
                "§f",
                "§7Blocos quebrados:",
                "§b§l❒§b" + NumberFormat.formatNumber(top.get(0).getBlocks())
            ),
            player.getLocation()
        );
    }

    @Override
    public void topTwo(Player player) {
        if (top.size() < 2) return;

        createPodiumNPC(
            player,
            top.get(1),
            Arrays.asList(
                "§e§lSEGUNDO MELHOR!",
                "§f",
                "§7Blocos quebrados:",
                "§b§l❒§b" + NumberFormat.formatNumber(top.get(1).getBlocks())
            ),
            player.getLocation()
        );
    }

    @Override
    public void topTree(Player player) {
        if (top.size() < 3) return;

        createPodiumNPC(
            player,
            top.get(2),
            Arrays.asList(
                "§e§lTERCEIRO MELHOR!",
                "§f",
                "§7Blocos quebrados:",
                "§b§l❒§b" + NumberFormat.formatNumber(top.get(2).getBlocks())
            ),
            player.getLocation()
        );

    }

    public void removeAllNPCs() {
        for (UUID uuid : podiumNPCs) {
            NPC npc = registry.getByUniqueId(uuid);
            if (npc != null) {
                npc.destroy();
                HologramBuilder.getInstance().removeHologram(uuid);
            }
        }
        podiumNPCs.clear();
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