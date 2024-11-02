package blizzard.development.slimeworlds.managers;


import blizzard.development.slimeworlds.Main;
import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.WorldEdit;
import com.sk89q.worldedit.WorldEditException;
import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.extent.clipboard.Clipboard;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardFormats;
import com.sk89q.worldedit.function.operation.Operation;
import com.sk89q.worldedit.function.operation.Operations;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldedit.session.ClipboardHolder;
import org.bukkit.Location;
import org.bukkit.World;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class SchematicManager {

    public void pasteSchematic(World world, Location location) {
        File file = new File(Main.getInstance().getDataFolder(), "floatingisland.schem");

        if (!file.exists()) {
            return;
        }

        try (InputStream inputStream = new FileInputStream(file)) {
            Clipboard clipboard = ClipboardFormats.findByFile(file)
                    .getReader(inputStream)
                    .read();


            EditSession editSession = WorldEdit.getInstance().newEditSession(BukkitAdapter.adapt(world));
            Operation operation = new ClipboardHolder(clipboard)
                    .createPaste(editSession)
                    .to(BlockVector3.at(location.getX(), location.getY(), location.getZ()))
                    .ignoreAirBlocks(true)
                    .build();

            Operations.complete(operation);
            editSession.flushSession();


        } catch (WorldEditException | IOException e) {
            throw new RuntimeException(e);
        }
    }

}