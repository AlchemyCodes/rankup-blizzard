package blizzard.development.spawners.inventories.slaughterhouses.main.items;

import blizzard.development.spawners.database.cache.managers.SlaughterhouseCacheManager;
import blizzard.development.spawners.database.cache.managers.SpawnersCacheManager;
import blizzard.development.spawners.database.storage.SlaughterhouseData;
import blizzard.development.spawners.database.storage.SpawnersData;
import blizzard.development.spawners.handlers.enums.slaughterhouses.States;
import blizzard.development.spawners.handlers.slaughterhouse.SlaughterhouseHandler;
import blizzard.development.spawners.utils.NumberFormat;
import blizzard.development.spawners.utils.SpawnersUtils;
import blizzard.development.spawners.utils.TimeConverter;
import blizzard.development.spawners.utils.items.SkullAPI;
import blizzard.development.spawners.utils.items.TextAPI;
import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class MainItems {
    private static MainItems instance;

    private final SlaughterhouseCacheManager manager = SlaughterhouseCacheManager.getInstance();
    private final SlaughterhouseHandler handler = SlaughterhouseHandler.getInstance();

    public ItemStack info(String id) {
        final SlaughterhouseData data = manager.getSlaughterhouseData(id);

        int level = Integer.parseInt(data.getTier());

        ItemStack item = SkullAPI.withBase64(new ItemStack(Material.PLAYER_HEAD), handler.getItem(level));
        ItemMeta meta = item.getItemMeta();

        meta.displayName(TextAPI.parse("§aInformações"));
        meta.setLore(Stream.of(
                        "§7Confira de antemão algumas",
                        "§7informações sobre o seu abatedouro",
                        "",
                        "§f Nível: §a{tier}",
                        "§f Área: §3{area}x{area}",
                        "§f Cooldown: §c{cooldown}",
                        "§f Pilhagem: §6{looting}"
                )
                .map(line -> line
                        .replace("{tier}", String.valueOf(handler.getLevel(level)))
                        .replace("{cooldown}", TimeConverter.convertSecondsToTimeFormat(handler.getKillCooldown(level)))
                        .replace("{area}", String.valueOf(handler.getKillArea(level)))
                        .replace("{looting}", String.valueOf(handler.getKillLooting(level))))
                .toList());
        meta.addItemFlags(ItemFlag.HIDE_ITEM_SPECIFICS, ItemFlag.HIDE_ATTRIBUTES);

        item.setItemMeta(meta);
        return item;
    }

    public ItemStack state(String id) {
        final SlaughterhouseData data = manager.getSlaughterhouseData(id);

        boolean isOnline = data.getState().equals(States.ON.getState());

        ItemStack item = isOnline ? new ItemStack(Material.LIME_DYE) : new ItemStack(Material.REDSTONE);

        ItemMeta meta = item.getItemMeta();

        String display = isOnline ? "§cDesligar" : "§aLigar";

        String time = TimeConverter.convertSecondsToTimeFormat(handler.getKillCooldown(Integer.parseInt(data.getTier())));

        List<String> lore = isOnline ? Arrays.asList(
                "§7Gerencie o estado",
                "§7do seu abatedor.",
                "",
                "§f  Estado: §aLigado",
                "§f  Cooldown: §7" + time,
                "",
                "§cClique para desligar."
        ) : Arrays.asList(
                "§7Gerencie o estado",
                "§7do seu abatedor.",
                "",
                "§f  Estado: §cDesligado",
                "§f  Cooldown: §7" + time,
                "",
                "§aClique para ligar."
        );

        meta.displayName(TextAPI.parse(display));
        meta.setLore(lore);

        meta.addItemFlags(ItemFlag.HIDE_ITEM_SPECIFICS, ItemFlag.HIDE_ATTRIBUTES);
        item.setItemMeta(meta);
        return item;
    }

    public ItemStack friends() {
        ItemStack item = new ItemStack(Material.OAK_SIGN);
        ItemMeta meta = item.getItemMeta();
        meta.displayName(TextAPI.parse("§eAmigos"));
        meta.setLore(Arrays.asList(
                "§7Gerencie suas amizades e",
                "§7suas respectivas permissões",
                "",
                "§eClique para gerenciar."
        ));

        meta.addItemFlags(ItemFlag.HIDE_ITEM_SPECIFICS, ItemFlag.HIDE_ATTRIBUTES);
        item.setItemMeta(meta);
        return item;
    }

    public static MainItems getInstance() {
        if (instance == null) instance = new MainItems();
        return instance;
    }
}
