/*    */ package blizzard.development.rankup.database.storage;
/*    */ 
/*    */ public class PlayersData {
/*    */   private String uuid;
/*    */   private String nickname;
/*    */   private String rank;
/*    */   private int prestige;
/*    */   
/*    */   public PlayersData(String uuid, String nickname, String rank, int prestige) {
/* 10 */     this.uuid = uuid;
/* 11 */     this.nickname = nickname;
/* 12 */     this.rank = rank;
/* 13 */     this.prestige = prestige;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getUuid() {
/* 20 */     return this.uuid;
/*    */   }
/*    */   
/*    */   public void setUuid(String uuid) {
/* 24 */     this.uuid = uuid;
/*    */   }
/*    */   
/*    */   public String getNickname() {
/* 28 */     return this.nickname;
/*    */   }
/*    */   
/*    */   public void setNickname(String nickname) {
/* 32 */     this.nickname = nickname;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getRank() {
/* 37 */     return this.rank;
/*    */   }
/*    */   
/*    */   public void setRank(String rank) {
/* 41 */     this.rank = rank;
/*    */   }
/*    */   
/*    */   public int getPrestige() {
/* 45 */     return this.prestige;
/*    */   }
/*    */   
/*    */   public void setPrestige(int prestige) {
/* 49 */     this.prestige = prestige;
/*    */   }
/*    */ }


/* Location:              C:\Users\joaop\Desktop\blizzard-rankup-1.0-SNAPSHOT.jar!\blizzard\development\rankup\database\storage\PlayersData.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */