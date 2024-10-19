package blizzard.development.core.commands;

import blizzard.development.core.Main;
import blizzard.development.core.commands.clothings.ClothingCommand;
import blizzard.development.core.commands.temperature.TemperatureCommand;
import co.aikar.commands.PaperCommandManager;

import java.util.Arrays;

public class CommandRegistry {

    public void register() {
        PaperCommandManager paperCommandManager = new PaperCommandManager(Main.getInstance());

        Arrays.asList(
                new ClothingCommand(),
                new TemperatureCommand()
        ).forEach(paperCommandManager::registerCommand);
    }
}
