package blizzard.development.plantations.listeners.packets.plantation;

import blizzard.development.plantations.Main;
import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketAdapter;

public class PlantationBreakListener extends PacketAdapter {

    public PlantationBreakListener() {
        super(PacketAdapter.params(
            Main.getInstance(),
            PacketType.Play.Client.BLOCK_DIG
        ).optionAsync());
    }


}
