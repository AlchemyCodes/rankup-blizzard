/*    */ package blizzard.development.core.clothing.adapters;
/*    */ 
/*    */ import blizzard.development.core.clothing.factory.ClothingActivatorFactory;
/*    */ import blizzard.development.core.clothing.item.RareClothingBuildItem;
/*    */ import org.bukkit.Material;
/*    */ import org.bukkit.entity.Player;
/*    */ 
/*    */ public class RareClothingAdapter
/*    */   implements ClothingActivatorFactory
/*    */ {
/*    */   public void active(Player player) {
/* 12 */     RareClothingBuildItem rareClothingBuildItem = new RareClothingBuildItem();
/*    */     
/* 14 */     player.getInventory().setHelmet(rareClothingBuildItem.buildRareClothing(Material.CHAINMAIL_HELMET));
/* 15 */     player.getInventory().setChestplate(rareClothingBuildItem.buildRareClothing(Material.CHAINMAIL_CHESTPLATE));
/* 16 */     player.getInventory().setLeggings(rareClothingBuildItem.buildRareClothing(Material.CHAINMAIL_LEGGINGS));
/* 17 */     player.getInventory().setBoots(rareClothingBuildItem.buildRareClothing(Material.CHAINMAIL_BOOTS));
/*    */   }
/*    */ }


/* Location:              C:\Users\joaop\Desktop\blizzard-core-1.0-SNAPSHOT.jar!\blizzard\development\core\clothing\adapters\RareClothingAdapter.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */