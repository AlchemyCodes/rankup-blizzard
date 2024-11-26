package blizzard.development.core.listener;

import blizzard.development.core.Main;
import blizzard.development.core.database.dao.PlayersDAO;
import blizzard.development.core.listener.campfire.CampfirePlace;
import blizzard.development.core.listener.clothing.ClothingActivatorInteractEvent;
import blizzard.development.core.listener.clothing.ClothingInventoryEvent;
import blizzard.development.core.listener.geral.TrafficListener;
import java.util.Arrays;

import blizzard.development.core.listener.packets.PacketListener;
import com.github.retrooper.packetevents.PacketEvents;
import com.github.retrooper.packetevents.event.EventManager;
import com.github.retrooper.packetevents.event.PacketListenerPriority;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;

public class ListenerRegistry {
    private final PlayersDAO playersDAO;

    public ListenerRegistry(PlayersDAO playersDAO) {
        this.playersDAO = playersDAO;
    }

    public void register() {
        PluginManager pluginManager = Bukkit.getPluginManager();

        Arrays.asList(
                new TrafficListener(this.playersDAO),
                new ClothingActivatorInteractEvent(),
                new ClothingInventoryEvent(),
                new CampfirePlace()
        ).forEach(listener -> pluginManager.registerEvents(listener, Main.getInstance()));
    }

    public void registerPacket() {

        EventManager eventManager = PacketEvents.getAPI().getEventManager();

        Arrays.asList(
                new PacketListener(PacketListenerPriority.HIGHEST)
        ).forEach(eventManager::registerListener);

    }

    // igual o outro, só instanciar uma classe que registrar! entendi muito obrigaod dwag agora eu vou fazer aquilo queo  snow manddou fazer viu muito obrigadio pela ajuda
}
