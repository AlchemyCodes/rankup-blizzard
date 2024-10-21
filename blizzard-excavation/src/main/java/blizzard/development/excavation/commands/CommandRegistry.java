package blizzard.development.excavation.commands;

import blizzard.development.excavation.Main;
import blizzard.development.excavation.commands.geral.ExcavateCommand;
import co.aikar.commands.PaperCommandManager;

import java.util.Arrays;

public class CommandRegistry {

    public void register() {
        PaperCommandManager paperCommandManager = new PaperCommandManager(Main.getInstance());

        Arrays.asList(
                new ExcavateCommand()
        ).forEach(paperCommandManager::registerCommand);
    }
}
