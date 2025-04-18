package blizzard.development.mine.mine.events.npc;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class NPCInteractEvent extends Event implements Cancellable {
    private static final HandlerList HANDLERS = new HandlerList();

    private final Player player;
    private final int entityId;
    private boolean cancelled;

    public NPCInteractEvent(@NotNull Player player, int entityId) {
        super(true);
        this.player = player;
        this.entityId = entityId;
    }

    public @NotNull Player getPlayer() {
        return player;
    }

    public int getEntityId() {
        return entityId;
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }

    @Override
    public @NotNull HandlerList getHandlers() {
        return HANDLERS;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }
}