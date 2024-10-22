/*    */ package blizzard.development.core.clothing.item;
/*    */ 
/*    */ import blizzard.development.core.Main;
/*    */ import blizzard.development.core.builder.ItemBuilder;
/*    */ import java.util.Arrays;
/*    */ import org.bukkit.Material;
/*    */ import org.bukkit.inventory.ItemStack;
/*    */ import org.bukkit.plugin.Plugin;
/*    */ 
/*    */ public class RareClothingBuildItem
/*    */ {
/*    */   public ItemStack buildRareClothing(Material material) {
/* 13 */     return (new ItemBuilder(material))
/* 14 */       .setDisplayName("<#e6e3dc>Manto de Malha<#e6e3dc>")
/* 15 */       .setLore(Arrays.asList(new String[] {
/*    */ 
/*    */             
/*    */             "§7Este manto de malha lhe", "§7fornece 15% de proteção ao frio.", "", " <#c4c4c4>Este é um manto da<#c4c4c4>", " <#c4c4c4>categoria rara.<#c4c4c4>", ""
/*    */ 
/*    */ 
/*    */ 
/*    */           
/* 23 */           })).addPersistentData((Plugin)Main.getInstance(), "manto.malha")
/* 24 */       .build();
/*    */   }
/*    */ }


/* Location:              C:\Users\joaop\Desktop\blizzard-core-1.0-SNAPSHOT.jar!\blizzard\development\core\clothing\item\RareClothingBuildItem.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */