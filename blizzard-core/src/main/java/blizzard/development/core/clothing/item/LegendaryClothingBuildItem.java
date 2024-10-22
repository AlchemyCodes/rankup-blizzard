/*    */ package blizzard.development.core.clothing.item;
/*    */ 
/*    */ import blizzard.development.core.Main;
/*    */ import blizzard.development.core.builder.ItemBuilder;
/*    */ import java.util.Arrays;
/*    */ import org.bukkit.Material;
/*    */ import org.bukkit.inventory.ItemStack;
/*    */ import org.bukkit.plugin.Plugin;
/*    */ 
/*    */ public class LegendaryClothingBuildItem
/*    */ {
/*    */   public ItemStack buildLegendaryClothing(Material material) {
/* 13 */     return (new ItemBuilder(material))
/* 14 */       .setDisplayName("§bManto de Diamante")
/* 15 */       .setLore(Arrays.asList(new String[] {
/*    */ 
/*    */             
/*    */             "§7Este manto de diamante lhe", "§7fornece 35% de proteção ao frio.", "", " §bEste é um manto da", " §bcategoria lendária.", ""
/*    */ 
/*    */ 
/*    */ 
/*    */           
/* 23 */           })).addPersistentData((Plugin)Main.getInstance(), "manto.diamante")
/* 24 */       .build();
/*    */   }
/*    */ }


/* Location:              C:\Users\joaop\Desktop\blizzard-core-1.0-SNAPSHOT.jar!\blizzard\development\core\clothing\item\LegendaryClothingBuildItem.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */