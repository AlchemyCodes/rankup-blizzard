/*    */ package blizzard.development.core.clothing.adapters;
/*    */ 
/*    */ import blizzard.development.core.clothing.factory.ClothingActivatorFactory;
/*    */ import blizzard.development.core.clothing.item.MysticClothingBuildItem;
/*    */ import org.bukkit.Material;
/*    */ import org.bukkit.entity.Player;
/*    */ 
/*    */ public class MysticClothingAdapter
/*    */   implements ClothingActivatorFactory
/*    */ {
/*    */   public void active(Player player) {
/* 12 */     MysticClothingBuildItem mysticClothingBuildItem = new MysticClothingBuildItem();
/*    */     
/* 14 */     player.getInventory().setHelmet(mysticClothingBuildItem.buildMysticClothing(Material.IRON_HELMET));
/* 15 */     player.getInventory().setChestplate(mysticClothingBuildItem.buildMysticClothing(Material.IRON_CHESTPLATE));
/* 16 */     player.getInventory().setLeggings(mysticClothingBuildItem.buildMysticClothing(Material.IRON_LEGGINGS));
/* 17 */     player.getInventory().setBoots(mysticClothingBuildItem.buildMysticClothing(Material.IRON_BOOTS));
/*    */   }
/*    */ }


/* Location:              C:\Users\joaop\Desktop\blizzard-core-1.0-SNAPSHOT.jar!\blizzard\development\core\clothing\adapters\MysticClothingAdapter.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */