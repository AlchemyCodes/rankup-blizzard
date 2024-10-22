/*    */ package blizzard.development.core.clothing.adapters;
/*    */ 
/*    */ import blizzard.development.core.clothing.factory.ClothingActivatorFactory;
/*    */ import blizzard.development.core.clothing.item.CommonClothingBuildItem;
/*    */ import org.bukkit.Material;
/*    */ import org.bukkit.entity.Player;
/*    */ 
/*    */ 
/*    */ public class CommonClothingAdapter
/*    */   implements ClothingActivatorFactory
/*    */ {
/*    */   public void active(Player player) {
/* 13 */     CommonClothingBuildItem commonClothingBuildItem = new CommonClothingBuildItem();
/*    */     
/* 15 */     player.getInventory().setHelmet(commonClothingBuildItem.buildCommonClothing(Material.LEATHER_HELMET));
/* 16 */     player.getInventory().setChestplate(commonClothingBuildItem.buildCommonClothing(Material.LEATHER_CHESTPLATE));
/* 17 */     player.getInventory().setLeggings(commonClothingBuildItem.buildCommonClothing(Material.LEATHER_LEGGINGS));
/* 18 */     player.getInventory().setBoots(commonClothingBuildItem.buildCommonClothing(Material.LEATHER_BOOTS));
/*    */   }
/*    */ }


/* Location:              C:\Users\joaop\Desktop\blizzard-core-1.0-SNAPSHOT.jar!\blizzard\development\core\clothing\adapters\CommonClothingAdapter.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */