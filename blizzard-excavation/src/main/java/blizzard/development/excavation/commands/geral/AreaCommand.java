package blizzard.development.excavation.commands.geral;

import blizzard.development.excavation.api.cuboid.Cuboid;
import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.Default;
import com.github.retrooper.packetevents.PacketEvents;
import com.github.retrooper.packetevents.event.PacketListener;
import com.github.retrooper.packetevents.event.PacketReceiveEvent;
import com.github.retrooper.packetevents.protocol.packettype.PacketType;
import com.github.retrooper.packetevents.protocol.player.DiggingAction;
import com.github.retrooper.packetevents.protocol.player.User;
import com.github.retrooper.packetevents.protocol.world.states.WrappedBlockState;
import com.github.retrooper.packetevents.util.Vector3i;
import com.github.retrooper.packetevents.wrapper.play.client.WrapperPlayClientPlayerDigging;
import com.github.retrooper.packetevents.wrapper.play.client.WrapperPlayClientUseItem;
import com.github.retrooper.packetevents.wrapper.play.server.WrapperPlayServerBlockChange;
import org.bukkit.block.Block;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Iterator;

@CommandAlias("area")
public class AreaCommand extends BaseCommand implements PacketListener {

    @Default
    public void onCommand(CommandSender commandSender) {
        Player player = (Player) commandSender;

        Cuboid cuboid = new Cuboid(
                player.getLocation().add(5, 0, 5),
                player.getLocation().add(-5, 10, -5)
        );


        User user = PacketEvents.getAPI().getPlayerManager().getUser(player);

        Iterator<Block> blockIterator = cuboid.blockList();

        WrappedBlockState coarseDirtState = WrappedBlockState.getByString("minecraft:coarse_dirt");

            while (blockIterator.hasNext()) {
                Block block = blockIterator.next();

                WrapperPlayServerBlockChange packet = new WrapperPlayServerBlockChange(
                        new Vector3i(block.getX(), block.getY(), block.getZ()),
                    coarseDirtState.getGlobalId()
            );

            user.sendPacket(packet);
        }

        player.sendMessage("§aÁrea alterada com sucesso!");
    }


//    @Override
//    public void onPacketReceive(PacketReceiveEvent event) {
//        User user = event.getUser();
//
//        if (event.getPacketType() == PacketType.Play.Client.PLAYER_DIGGING) {
//            WrapperPlayClientPlayerDigging digging = new WrapperPlayClientPlayerDigging(event);
//
//            if (digging.getAction() == DiggingAction.FINISHED_DIGGING) {
//                event.setCancelled(true);
//
//                user.sendTitle(
//                        "§4§lPACKETS",
//                        "§4Quebrou um bloco.",
//                        10,
//                        70,
//                        20
//                );
//            }
//            return;
//        }
//
//        if (event.getPacketType() == PacketType.Play.Client.USE_ITEM) {
//            WrapperPlayClientUseItem wrapper = new WrapperPlayClientUseItem(event);
//
//            Vector3i blockPos = wrapper.readBlockPosition();
//
//
//            event.setCancelled(true);
//
//            user.sendTitle(
//                    "§aBloco Protegido!",
//                    "§aVocê não pode interagir com este bloco.",
//                    10,
//                    70,
//                    20
//            );
//
//
//        }
//    }
}