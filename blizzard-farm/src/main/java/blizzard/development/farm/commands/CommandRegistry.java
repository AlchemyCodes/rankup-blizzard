package blizzard.development.farm.commands;

import blizzard.development.farm.commands.storage.StorageCommand;
import blizzard.development.farm.utils.PluginImpl;
import co.aikar.commands.PaperCommandManager;

import java.util.Arrays;

public class CommandRegistry {

    public void register() {
        PaperCommandManager paperCommandManager = new PaperCommandManager(PluginImpl.getInstance().plugin);

        Arrays.asList(
            new StorageCommand()
        ).forEach(paperCommandManager::registerCommand);
    }
}
