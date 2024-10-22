/*    */ package blizzard.development.core.clothing.item;
/*    */ 
/*    */ import blizzard.development.core.Main;
/*    */ import blizzard.development.core.builder.ItemBuilder;
/*    */ import java.util.Arrays;
/*    */ import org.bukkit.Material;
/*    */ import org.bukkit.inventory.ItemStack;
/*    */ import org.bukkit.plugin.Plugin;
/*    */ 
/*    */ public class MysticClothingBuildItem
/*    */ {
/*    */   public ItemStack buildMysticClothing(Material material) {
/* 13 */     return (new ItemBuilder(material))
/* 14 */       .setDisplayName("<#e6e3dc>Manto de Ferro<#e6e3dc>")
/* 15 */       .setLore(Arrays.asList(new String[] {
/*    */ 
/*    */             
/*    */             "§7Este manto de ferro lhe", "§7fornece 25% de proteção ao frio.", "", " <#e6e3dc>Este é um manto da<#e6e3dc>", " <#e6e3dc>categoria mística.<#e6e3dc>", ""
/*    */ 
/*    */ 
/*    */ 
/*    */           
/* 23 */           })).addPersistentData((Plugin)Main.getInstance(), "manto.ferro")
/* 24 */       .build();
/*    */   }
/*    */ }


/* Location:              C:\Users\joaop\Desktop\blizzard-core-1.0-SNAPSHOT.jar!\blizzard\development\core\clothing\item\MysticClothingBuildItem.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */