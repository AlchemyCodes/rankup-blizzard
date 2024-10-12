package blizzard.development.clans.builders;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import blizzard.development.clans.database.cache.ClansCacheManager;
import blizzard.development.clans.database.storage.ClansData;
import blizzard.development.clans.methods.ClansMethods;
import blizzard.development.clans.utils.NBTUtils;
import blizzard.development.clans.utils.gradient.TextUtil;

import java.util.Arrays;

public class LimitBuilder {

    public static String data = "clans_limit_amount_";

    public static ItemStack createLimit(Player player, int amount) {

        ItemStack limit = new ItemStack(Material.CAULDRON);
        ItemMeta meta = limit.getItemMeta();

        String clan = ClansMethods.getUserClan(player);

        meta.displayName(TextUtil.parse("§bLimite de Membros §7[" + amount + "]"));
        if (clan == null) {
            meta.setLore(Arrays.asList(
                    "§7Ative este item para aumentar",
                    "§7o limite de membros do seu clan."
            ));
        } else {
            ClansData clansData = ClansCacheManager.getClansData(clan);
            meta.setLore(Arrays.asList(
                    "§7Ative este item para aumentar",
                    "§7o limite de membros do seu clan.",
                    "",
                    " §f§l▶ §fA partir do momento em",
                    " §f   que você ativar, seu clan",
                    " §f   irá possuir §l" + (clansData.getMax() + amount) + "§f membros." ,
                    "",
                    "§bClique para ativar."
            ));
        }

        meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);

        limit.setItemMeta(meta);

        NBTUtils.setTag(limit, data + amount);
        return limit;
    }
}