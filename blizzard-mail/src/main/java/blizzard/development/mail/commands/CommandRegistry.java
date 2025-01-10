package blizzard.development.mail.commands;

import blizzard.development.mail.Main;
import co.aikar.commands.PaperCommandManager;

import java.util.Arrays;

public class CommandRegistry {

    public void register() {
        PaperCommandManager paperCommandManager = new PaperCommandManager(Main.getInstance());

        Arrays.asList(
            new GiveItem(),
                new OpenMail()
        ).forEach(paperCommandManager::registerCommand);
    }
}
