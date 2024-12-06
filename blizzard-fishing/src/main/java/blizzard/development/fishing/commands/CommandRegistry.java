package blizzard.development.fishing.commands;

import blizzard.development.fishing.Main;
import blizzard.development.fishing.commands.subcommands.SetCommands;
import co.aikar.commands.PaperCommandManager;

import java.util.Arrays;

public class CommandRegistry {

    public void register() {
        PaperCommandManager paperCommandManager = new PaperCommandManager(Main.getInstance());

        Arrays.asList(
                new FishingCommand(),
                new SetCommands(),
                new GiveItemsCommand(),
                new Geyser()
        ).forEach(paperCommandManager::registerCommand);
    }
}
