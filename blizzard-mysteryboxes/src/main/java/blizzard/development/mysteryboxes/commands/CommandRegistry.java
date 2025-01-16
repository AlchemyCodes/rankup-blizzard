package blizzard.development.mysteryboxes.commands;

import blizzard.development.mysteryboxes.commands.mysterybox.MysteryBoxCommand;
import blizzard.development.mysteryboxes.mysteryboxes.enums.MysteryBoxEnum;
import blizzard.development.mysteryboxes.utils.PluginImpl;
import co.aikar.commands.PaperCommandManager;
import com.google.common.collect.ImmutableList;

import java.util.Arrays;

public class CommandRegistry {

    public void register() {
        PaperCommandManager paperCommandManager = new PaperCommandManager(PluginImpl.getInstance().plugin);

        paperCommandManager.getCommandCompletions().registerCompletion("boxes", c ->
            ImmutableList.of(MysteryBoxEnum.RARE.toString(), MysteryBoxEnum.LEGENDARY.toString(), MysteryBoxEnum.BLIZZARD.toString()));

        Arrays.asList(
            new MysteryBoxCommand()
        ).forEach(paperCommandManager::registerCommand);
    }
}
