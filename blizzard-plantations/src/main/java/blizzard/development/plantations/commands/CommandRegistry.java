package blizzard.development.plantations.commands;

import blizzard.development.plantations.Main;
import blizzard.development.plantations.commands.farm.FarmCommand;
import blizzard.development.plantations.commands.seeds.SeedCommand;
import co.aikar.commands.PaperCommandManager;

import java.util.Arrays;

public class CommandRegistry {

    public void register() {
        PaperCommandManager paperCommandManager = new PaperCommandManager(Main.getInstance());

        Arrays.asList(
            new FarmCommand(),
            new SeedCommand(),
            new DebugCommand()
        ).forEach(paperCommandManager::registerCommand);
    }
}
