package blizzard.development.mine.mine.adapters;

import blizzard.development.mine.builders.item.ItemBuilder;
import blizzard.development.mine.database.cache.BoosterCacheManager;
import blizzard.development.mine.database.cache.methods.BoosterCacheMethods;
import blizzard.development.mine.database.dao.BoosterDAO;
import blizzard.development.mine.database.storage.BoosterData;
import blizzard.development.mine.mine.enums.BoosterEnum;
import blizzard.development.mine.mine.item.BoosterBuildItem;
import blizzard.development.mine.utils.PluginImpl;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.hover.content.Text;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.sql.SQLException;
import java.util.HashMap;

public class BoosterAdapter {
    private final BoosterDAO boosterDAO;

    private static final BoosterAdapter instance = new BoosterAdapter(new BoosterDAO());
    public HashMap<Player, Boolean> accumulatingBooster = new HashMap<>();
    public HashMap<Player, Boolean> changingBooster = new HashMap<>();
    public HashMap<Player, Boolean> alwaysAccumulate = new HashMap<>();

    public BoosterAdapter(BoosterDAO boosterDAO) {
        this.boosterDAO = boosterDAO;
    }

    public static BoosterAdapter getInstance() {
        return instance;
    }

    public void giveBooster(Player player, BoosterEnum booster) {
        player.getInventory().addItem(BoosterBuildItem.booster(player, booster));
    }

    public void removeBooster(Player player, BoosterEnum booster) {
        for (ItemStack item : player.getInventory().getContents()) {
            if (item == null) continue;

            if (ItemBuilder.hasPersistentData(PluginImpl.getInstance().plugin, item, booster.name().toLowerCase())) {
                item.setAmount(item.getAmount() - 1);
                break;
            }
        }
    }

    public void applyBooster(Player player, BoosterEnum booster) {
        BoosterData boosterData = boosterDAO.findBoosterData(player.getUniqueId().toString());

        if (boosterData == null) {
            boosterData = new BoosterData(
                    player.getUniqueId().toString(),
                    player.getName(),
                    booster.getName(),
                    booster.getDuration()
            );
            removeBooster(player, booster);
            try {
                boosterDAO.createBoosterData(boosterData);
            } catch (SQLException exception) {
                exception.printStackTrace();
            }
        }

        if (isChangingBooster(player) || isAccumulatingBooster(player)) return;

        if (boosterData.getBoosterName().equalsIgnoreCase("")) {
            BoosterCacheMethods.getInstance().setBoosterName(player.getUniqueId(), booster.getName());
            BoosterCacheMethods.getInstance().setBoosterDuration(player.getUniqueId(), booster.getDuration());
            removeBooster(player, booster);
            return;
        }

        if (!boosterData.getBoosterName().equalsIgnoreCase(booster.getName())) {
            setChangingBooster(player);
            sendChangeBoosterMessage(player);
            return;
        }

        if (boosterData.getBoosterName().equalsIgnoreCase(booster.getName())) {
            if (isAlwaysAccumulate(player)) {
                addTime(player, booster);
                removeBooster(player, booster);
                return;
            }
            setAccumulatingBooster(player);
            sendHasBoosterMessage(player);
            return;
        }

        BoosterCacheMethods.getInstance().setBoosterName(player.getUniqueId(), booster.getName());
        BoosterCacheMethods.getInstance().setBoosterDuration(player.getUniqueId(), booster.getDuration());

        BoosterCacheManager.getInstance().cacheBoosterData(
                player.getUniqueId(),
                boosterData
        );

        removeBooster(player, booster);
        player.sendMessage("ativou booster");
    }

    public void addTime(Player player, BoosterEnum booster) {
        BoosterCacheMethods.getInstance().setBoosterDuration(player.getUniqueId(),
                BoosterCacheMethods.getInstance().getBoosterDuration(player.getUniqueId()) + booster.getDuration());
    }

    public void sendChangeBoosterMessage(Player player) {
        player.sendMessage("");
        player.sendMessage("  §b§lOPS! §bParece que você tem um booster diferente ativo!");
        player.sendMessage("");
        player.sendMessage("    §7Deseja trocar o booster ativo");
        player.sendMessage("    §7pelo booster da sua mão?");
        player.sendMessage("");

        TextComponent sim = new TextComponent("    §a§l[SIM] ");
        sim.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/booster confirmar"));
        sim.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text("§a§lVocê DEVE estar segurando o booster!")));

        TextComponent nao = new TextComponent("§c§l[NÃO]");
        nao.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/booster negar"));

        player.spigot().sendMessage(sim, nao);

        player.sendMessage("");
    }

    public void sendHasBoosterMessage(Player player) {
        player.sendMessage("");
        player.sendMessage("  §b§lOPS! §bVocê já tem esse booster ativo!");
        player.sendMessage("");
        player.sendMessage("    §7Deseja acumular o tempo");
        player.sendMessage("    §7com o do booster atual?");
        player.sendMessage("");

        TextComponent sim = new TextComponent("    §a§l[SIM] ");
        sim.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/booster confirmar"));
        sim.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text("§a§lVocê DEVE estar segurando o booster!")));

        TextComponent nao = new TextComponent("§c§l[NÃO]");
        nao.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/booster negar"));

        player.spigot().sendMessage(sim, nao);

        TextComponent continuar = new TextComponent("\n  §bClique §lAQUI! §bpara sempre acumular.");
        continuar.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/booster continuar"));
        continuar.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text("§b§lIsso valerá até você reconectar!")));

        player.spigot().sendMessage(continuar);
        player.sendMessage("");
    }

    public BoosterEnum getBooster(ItemStack item) {
        for (BoosterEnum booster : BoosterEnum.values()) {
            if (ItemBuilder.hasPersistentData(PluginImpl.getInstance().plugin, item, booster.name().toLowerCase())) {
                return booster;
            }
        }
        return null;
    }

    private void setAccumulatingBooster(Player player) {
        accumulatingBooster.put(player, true);
    }

    public void removeAccumulatingBooster(Player player) {
        accumulatingBooster.remove(player);
    }

    public boolean isAccumulatingBooster(Player player) {
       return accumulatingBooster.containsKey(player);
    }

    private void setChangingBooster(Player player) {
        changingBooster.put(player, true);
    }

    public void removeChangingBooster(Player player) {
        changingBooster.remove(player);
    }

    public boolean isChangingBooster(Player player) {
        return changingBooster.containsKey(player);
    }

    public void setAlwaysAccumulate(Player player) {
        alwaysAccumulate.put(player, true);
    }

    public void removeAlwaysAccumulate(Player player) {
        alwaysAccumulate.remove(player);
    }

    public boolean isAlwaysAccumulate(Player player) {
        return alwaysAccumulate.getOrDefault(player, false);
    }


}
