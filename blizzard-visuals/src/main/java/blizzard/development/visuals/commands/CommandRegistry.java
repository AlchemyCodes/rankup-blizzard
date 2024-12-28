package blizzard.development.visuals.commands;

import blizzard.development.visuals.Main;
import blizzard.development.visuals.commands.visual.VisualCommand;
import blizzard.development.visuals.visuals.enums.VisualEnum;
import co.aikar.commands.PaperCommandManager;
import com.google.common.collect.ImmutableList;

import java.util.Arrays;

public class CommandRegistry {

    public void register() {
        PaperCommandManager paperCommandManager = new PaperCommandManager(Main.getInstance());

        paperCommandManager.getCommandCompletions().registerCompletion("skins", c ->
            ImmutableList.of(VisualEnum.STONE.toString(), VisualEnum.IRON.toString(), VisualEnum.GOLD.toString(), VisualEnum.DIAMOND.toString()));

        Arrays.asList(
            new VisualCommand()
        ).forEach(paperCommandManager::registerCommand);
    }
}
