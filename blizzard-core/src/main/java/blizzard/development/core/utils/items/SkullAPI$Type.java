/*    */ package blizzard.development.core.utils.items;
/*    */ 
/*    */ import org.bukkit.Material;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public enum Type
/*    */ {
/* 48 */   BLOCK(Material.PLAYER_HEAD),
/* 49 */   ITEM(Material.PLAYER_HEAD);
/*    */   
/*    */   private Material mat;
/*    */ 
/*    */   
/*    */   Type(Material mat) {
/* 55 */     this.mat = mat;
/*    */   }
/*    */ }


/* Location:              C:\Users\joaop\Desktop\blizzard-core-1.0-SNAPSHOT.jar!\blizzard\development\cor\\utils\items\SkullAPI$Type.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */