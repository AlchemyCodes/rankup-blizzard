/*    */ package blizzard.development.core.clothing.item;
/*    */ 
/*    */ import blizzard.development.core.Main;
/*    */ import blizzard.development.core.builder.ItemBuilder;
/*    */ import java.util.Arrays;
/*    */ import org.bukkit.Material;
/*    */ import org.bukkit.inventory.ItemStack;
/*    */ import org.bukkit.plugin.Plugin;
/*    */ 
/*    */ public class CommonClothingBuildItem
/*    */ {
/*    */   public ItemStack buildCommonClothing(Material material) {
/* 13 */     return (new ItemBuilder(material))
/* 14 */       .setDisplayName("§cManto de Couro")
/* 15 */       .setLore(Arrays.asList(new String[] {
/*    */ 
/*    */             
/*    */             "§7Este manto de couro lhe", "§7fornece 5% de proteção ao frio.", "", " §cEste é um manto da", " §ccategoria comum.", ""
/*    */ 
/*    */ 
/*    */ 
/*    */           
/* 23 */           })).addPersistentData((Plugin)Main.getInstance(), "manto.couro")
/* 24 */       .build();
/*    */   }
/*    */ }


/* Location:              C:\Users\joaop\Desktop\blizzard-core-1.0-SNAPSHOT.jar!\blizzard\development\core\clothing\item\CommonClothingBuildItem.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */