/*    */ package blizzard.development.core.clothing.adapters;
/*    */ 
/*    */ import blizzard.development.core.clothing.factory.ClothingActivatorFactory;
/*    */ import blizzard.development.core.clothing.item.LegendaryClothingBuildItem;
/*    */ import org.bukkit.Material;
/*    */ import org.bukkit.entity.Player;
/*    */ 
/*    */ public class LegendaryClothingAdapter
/*    */   implements ClothingActivatorFactory
/*    */ {
/*    */   public void active(Player player) {
/* 12 */     LegendaryClothingBuildItem legendaryClothingBuildItem = new LegendaryClothingBuildItem();
/*    */     
/* 14 */     player.getInventory().setHelmet(legendaryClothingBuildItem.buildLegendaryClothing(Material.DIAMOND_HELMET));
/* 15 */     player.getInventory().setChestplate(legendaryClothingBuildItem.buildLegendaryClothing(Material.DIAMOND_CHESTPLATE));
/* 16 */     player.getInventory().setLeggings(legendaryClothingBuildItem.buildLegendaryClothing(Material.DIAMOND_LEGGINGS));
/* 17 */     player.getInventory().setBoots(legendaryClothingBuildItem.buildLegendaryClothing(Material.DIAMOND_BOOTS));
/*    */   }
/*    */ }


/* Location:              C:\Users\joaop\Desktop\blizzard-core-1.0-SNAPSHOT.jar!\blizzard\development\core\clothing\adapters\LegendaryClothingAdapter.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */