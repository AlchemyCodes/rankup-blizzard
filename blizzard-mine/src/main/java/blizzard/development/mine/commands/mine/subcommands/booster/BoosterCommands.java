package blizzard.development.mine.commands.mine.subcommands.booster;

import blizzard.development.mine.database.cache.methods.BoosterCacheMethods;
import blizzard.development.mine.database.dao.BoosterDAO;
import blizzard.development.mine.mine.adapters.BoosterAdapter;
import blizzard.development.mine.mine.enums.BoosterEnum;
import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.Default;
import co.aikar.commands.annotation.Subcommand;
import org.bukkit.entity.Player;

@CommandAlias("booster")
public class BoosterCommands extends BaseCommand {

    @Subcommand("confirmar")
    public void onConfirm(Player player) {
        BoosterAdapter boosterAdapter = BoosterAdapter.getInstance();
        BoosterEnum booster = boosterAdapter.getBooster(player.getInventory().getItemInMainHand());

        if (boosterAdapter.isAccumulatingBooster(player)) {
            boosterAdapter.addTime(player, booster);
            boosterAdapter.removeAccumulatingBooster(player);
            boosterAdapter.removeBooster(player, booster);
            player.sendMessage("addei time!");
        }

        if (boosterAdapter.isChangingBooster(player)) {
            BoosterCacheMethods.getInstance().setBoosterName(player.getUniqueId(), booster.getName());
            BoosterCacheMethods.getInstance().setBoosterDuration(player.getUniqueId(), booster.getDuration());
            boosterAdapter.removeChangingBooster(player);
            boosterAdapter.removeBooster(player, booster);
            player.sendMessage("mudou de booster!");
        }
    }

    @Subcommand("negar")
    public void onDeny(Player player) {
        BoosterAdapter boosterAdapter = BoosterAdapter.getInstance();

//        if (!boosterAdapter.isAccumulatingBooster(player) || !boosterAdapter.isChangingBooster(player)) return;

        boosterAdapter.removeAccumulatingBooster(player);
        boosterAdapter.removeChangingBooster(player);
        player.sendMessage("negou!");
    }

    @Subcommand("continuar")
    public void onContinue(Player player) {
        BoosterAdapter boosterAdapter = BoosterAdapter.getInstance();

        if (!boosterAdapter.isAccumulatingBooster(player)) return;

        boosterAdapter.setAlwaysAccumulate(player);
        boosterAdapter.removeAccumulatingBooster(player);
        player.sendMessage("está accumulating!!");
    }
}
