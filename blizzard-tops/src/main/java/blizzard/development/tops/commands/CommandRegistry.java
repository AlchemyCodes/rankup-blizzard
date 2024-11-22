package blizzard.development.tops.commands;

import blizzard.development.tops.commands.tops.TopsCommand;
import blizzard.development.tops.utils.PluginImpl;
import co.aikar.commands.PaperCommandManager;

import java.util.ArrayList;
import java.util.Arrays;

public class CommandRegistry {
    private static CommandRegistry instance;

    public void register() {
        PaperCommandManager paperCommandManager = new PaperCommandManager(PluginImpl.getInstance().plugin);

        Arrays.asList(
                new TopsCommand()
        ).forEach(paperCommandManager::registerCommand);

        paperCommandManager.getCommandCompletions().registerCompletion("currencies", c -> {
            ArrayList<String> array = new ArrayList<>();
            array.add("Flocos");
            array.add("Fosseis");
            array.add("Almas");
            return array;
        });
    }

    public static CommandRegistry getInstance() {
        if (instance == null) instance = new CommandRegistry();
        return instance;
    }
}
