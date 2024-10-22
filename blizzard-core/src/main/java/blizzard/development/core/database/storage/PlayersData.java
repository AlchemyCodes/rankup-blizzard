/*    */ package blizzard.development.core.database.storage;
/*    */ 
/*    */ import blizzard.development.core.clothing.ClothingType;
/*    */ 
/*    */ public class PlayersData
/*    */ {
/*    */   private String uuid;
/*    */   private String nickname;
/*    */   
/*    */   public PlayersData(String uuid, String nickname, double temperature, ClothingType clothingType) {
/* 11 */     this.uuid = uuid;
/* 12 */     this.nickname = nickname;
/* 13 */     this.temperature = temperature;
/* 14 */     this.clothingType = clothingType;
/*    */   }
/*    */   private double temperature; private ClothingType clothingType;
/*    */   public String getUuid() {
/* 18 */     return this.uuid;
/*    */   }
/*    */   
/*    */   public String getNickname() {
/* 22 */     return this.nickname;
/*    */   }
/*    */   
/*    */   public double getTemperature() {
/* 26 */     return this.temperature;
/*    */   }
/*    */   
/*    */   public void setTemperature(double temperature) {
/* 30 */     this.temperature = temperature;
/*    */   }
/*    */   
/*    */   public ClothingType getClothingType() {
/* 34 */     return this.clothingType;
/*    */   }
/*    */   
/*    */   public void setClothingType(ClothingType clothingType) {
/* 38 */     this.clothingType = clothingType;
/*    */   }
/*    */ }


/* Location:              C:\Users\joaop\Desktop\blizzard-core-1.0-SNAPSHOT.jar!\blizzard\development\core\database\storage\PlayersData.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */