package blizzard.development.fishing.commands;

import blizzard.development.fishing.Main;
import co.aikar.commands.PaperCommandManager;

import java.util.Arrays;

public class CommandRegistry {

    public void register() {
        PaperCommandManager paperCommandManager = new PaperCommandManager(Main.getInstance());

        Arrays.asList(
                new FishingCommand()
        ).forEach(paperCommandManager::registerCommand);
    }
}
