package blizzard.development.providers;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

import blizzard.development.builders.ItemBuilder;
import blizzard.development.utils.NumberFormatter;
import blizzard.development.utils.PluginImpl;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.material.MaterialData;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;

import java.util.*;

public class GroundDropProvider {
    private static final String DROP_TAG = "ground-drop";
    private final Plugin plugin;
    private final List<String> blacklistedMetadata = new ArrayList();
    private final List<ItemStack> itemStackList = new CopyOnWriteArrayList();

    public GroundDropProvider(Plugin plugin) {
        this.plugin = plugin;
        FileConfiguration config = PluginImpl.getInstance().plugin.getConfig();
        ConfigurationSection configSection = config.getConfigurationSection("ground-drop-blacklist");
        if (configSection != null) {
            this.blacklistedMetadata.addAll(configSection.getStringList("metadata"));
            ConfigurationSection itemSection = configSection.getConfigurationSection("items");
            if (itemSection != null) {
                Iterator var5 = itemSection.getKeys(false).iterator();

                while(var5.hasNext()) {
                    String name = (String)var5.next();
                    ConfigurationSection section = itemSection.getConfigurationSection(name);
                    if (section != null) {
                        String id = section.getString("id");
                        String[] strippedMaterial = id.split(":");
                        if (strippedMaterial.length == 2) {
                            MaterialData data = new MaterialData(Material.matchMaterial(strippedMaterial[0]), Byte.parseByte(strippedMaterial[1]));
                            ItemBuilder item = new ItemBuilder(data.getItemType(), 1, data.getData());
                            this.itemStackList.add(item.build());
                        }
                    }
                }

            }
        }
    }

    public static void wipeAllGroundDrop() {
        Iterator var0 = Bukkit.getWorlds().iterator();

        while(var0.hasNext()) {
            World world = (World)var0.next();
            Iterator var2 = world.getEntities().iterator();

            while(var2.hasNext()) {
                Entity entity = (Entity)var2.next();
                if (entity.hasMetadata("ground-drop")) {
                    entity.remove();
                }
            }
        }

    }

    public boolean provideGroundDrop(Item item) {
        if (this.isBlacklisted(item)) {
            return true;
        } else {
            ItemStack itemStack = item.getItemStack();
            Location location = item.getLocation();
            Item nearbyItem = this.nearbyDrop(location, itemStack);
            if (nearbyItem != null) {
                int amount = this.getDropAmount(nearbyItem) + itemStack.getAmount();
                this.applyMetadata(nearbyItem, (double)amount);
                return false;
            } else {
                this.applyMetadata(item, (double)itemStack.getAmount());
                return true;
            }
        }
    }

    public void merge(Item item, Item target) {
        this.applyMetadata(item, (double)(this.getDropAmount(item) + this.getDropAmount(target)));
        target.remove();
    }

    private void applyMetadata(Item item, double amount) {
        if (amount == 0.0) {
            item.remove();
        }

        item.setMetadata("ground-drop", new FixedMetadataValue(this.plugin, amount));
        this.updateItem(item, amount);
    }

    private void updateItem(Item item, double amount) {
        ItemStack itemStack = item.getItemStack();
        itemStack.setAmount(1);
        item.setCustomName("§b§lx§b" + NumberFormatter.getInstance().formatNumber(amount));
        item.setCustomNameVisible(true);
    }

    private Item nearbyDrop(Location location, ItemStack currentItem) {
        List<Entity> nearbyEntities = (List)location.getWorld().getNearbyEntities(location, 10.0, 10.0, 10.0).stream().filter(($) -> {
            return $.getType() == EntityType.DROPPED_ITEM;
        }).collect(Collectors.toList());
        if (nearbyEntities.isEmpty()) {
            return null;
        } else {
            Iterator var4 = nearbyEntities.iterator();

            Item item;
            ItemStack itemStack;
            do {
                if (!var4.hasNext()) {
                    return null;
                }

                Entity entity = (Entity)var4.next();
                item = (Item)entity;
                itemStack = item.getItemStack();
            } while(!item.hasMetadata("ground-drop") || !itemStack.isSimilar(currentItem));

            return item;
        }
    }

    public boolean isDrop(Item item) {
        return this.isBlacklisted(item) ? false : item.hasMetadata("ground-drop");
    }

    public int getDropAmount(Item item) {
        return !this.isDrop(item) ? 0 : ((MetadataValue)item.getMetadata("ground-drop").get(0)).asInt();
    }

    public void provideDrop(Player player, Item item) {
        PlayerInventory inventory = player.getInventory();
        if (inventory.firstEmpty() != -1) {
            int dropAmount = this.getDropAmount(item);
            if (dropAmount != 0) {
                player.playSound(player.getLocation(), Sound.ENTITY_ITEM_PICKUP, 1.0F, 1.0F);
                ItemStack itemStack = item.getItemStack();
                int maxStackSize = itemStack.getMaxStackSize();
                if (dropAmount <= 64 && maxStackSize == 64) {
                    this.give(inventory, itemStack, dropAmount);
                    item.remove();
                } else {
                    ItemStack[] var7 = inventory.getContents();
                    int var8 = var7.length;

                    for(int var9 = 0; var9 < var8; ++var9) {
                        ItemStack content = var7[var9];
                        if (content == null || content.getType() == Material.AIR) {
                            int min = Math.min(maxStackSize, dropAmount);
                            this.give(inventory, itemStack, min);
                            dropAmount -= min;
                            if (dropAmount <= 0) {
                                item.remove();
                                return;
                            }
                        }
                    }

                    this.applyMetadata(item, (double)dropAmount);
                }
            }
        }
    }

    private void give(Inventory inventory, ItemStack item, int amount) {
        item.setAmount(amount);
        inventory.addItem(new ItemStack[]{item});
    }

    private boolean isBlacklisted(Item item) {
        Iterator var2 = this.blacklistedMetadata.iterator();

        String metadata;
        do {
            if (!var2.hasNext()) {
                return this.isBlacklisted(item.getItemStack());
            }

            metadata = (String)var2.next();
        } while(!item.hasMetadata(metadata));

        return true;
    }

    private boolean isBlacklisted(ItemStack item) {
        if (item.hasItemMeta() && item.getItemMeta().hasDisplayName()) {
            byte itemData = item.getData().getData();
            Material itemType = item.getType();
            String itemName = item.getItemMeta().getDisplayName();
            Iterator var5 = this.itemStackList.iterator();

            Material type;
            byte data;
            String displayName;
            do {
                if (!var5.hasNext()) {
                    return false;
                }

                ItemStack itemStack = (ItemStack)var5.next();
                type = itemStack.getType();
                data = itemStack.getData().getData();
                displayName = itemStack.getItemMeta().getDisplayName();
            } while(type != itemType || data != itemData || !displayName.contains(itemName));

            return true;
        } else {
            return false;
        }
    }
}