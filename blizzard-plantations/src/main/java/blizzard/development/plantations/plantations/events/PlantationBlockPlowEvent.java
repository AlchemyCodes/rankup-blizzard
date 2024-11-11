package blizzard.development.plantations.plantations.events;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.inventory.EquipmentSlot;

public class PlantationBlockPlowEvent extends Event implements Cancellable {

    private static final HandlerList handlers = new HandlerList();
    private boolean cancelled;
    private final Player player;
    private final Block block;
    private final Material originalType;
    private final EquipmentSlot equipmentSlot;

    public PlantationBlockPlowEvent(Player player, Block block, Material originalType, EquipmentSlot equipmentSlot) {
        this.player = player;
        this.block = block;
        this.originalType = originalType;
        this.equipmentSlot = equipmentSlot;
    }

    public Player getPlayer() {
        return player;
    }

    public Block getBlock() {
        return block;
    }

    public Material getOriginalType() {
        return originalType;
    }

    public EquipmentSlot getEquipmentSlot() {
        return equipmentSlot;
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean cancel) {
        this.cancelled = cancel;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}
