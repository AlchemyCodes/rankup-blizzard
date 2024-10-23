/*    */ package blizzard.development.rankup.utils;
/*    */ 
/*    */ import java.text.DecimalFormat;
/*    */ import java.text.DecimalFormatSymbols;
/*    */ import java.util.Locale;
/*    */ 
/*    */ public class NumberFormat
/*    */ {
/*    */   public static final double LOG = 6.907755278982137D;
/* 10 */   public static final Object[][] VALUES = new Object[][] { { "", "K", "M", "B", "T", "Q", "QQ", "S", "SS", "O", "N", "D", "UN", "DD", "TR", "QT", "QN", "SD", "SPD", "OD", "ND", "VG", "UVG", "DVG", "TVG", "QTV"
/*    */       }, { 
/* 12 */         Double.valueOf(1.0D), Double.valueOf(1000.0D), Double.valueOf(1000000.0D), Double.valueOf(1.0E9D), Double.valueOf(1.0E12D), Double.valueOf(1.0E15D), Double.valueOf(1.0E18D), Double.valueOf(1.0E21D), Double.valueOf(1.0E24D), Double.valueOf(1.0E27D), Double.valueOf(1.0E30D), Double.valueOf(1.0E33D), Double.valueOf(1.0E36D), Double.valueOf(1.0E39D), Double.valueOf(1.0E42D), Double.valueOf(1.0E45D), Double.valueOf(1.0E48D), Double.valueOf(1.0E51D), Double.valueOf(1.0E54D), Double.valueOf(1.0E57D), Double.valueOf(1.0E60D), Double.valueOf(1.0E63D), Double.valueOf(1.0E66D), Double.valueOf(1.0E69D), Double.valueOf(1.0E72D) } };
/*    */ 
/*    */   
/* 15 */   public static final DecimalFormat FORMAT = new DecimalFormat("#,###.##", new DecimalFormatSymbols(new Locale("pt", "BR")));
/*    */   
/*    */   public static String formatNumber(double number) {
/* 18 */     if (number == 0.0D) return FORMAT.format(number); 
/* 19 */     int index = (int)(Math.log(number) / 6.907755278982137D);
/* 20 */     return FORMAT.format(number / ((Double)VALUES[1][index]).doubleValue()) + FORMAT.format(number / ((Double)VALUES[1][index]).doubleValue());
/*    */   }
/*    */   
/*    */   public static boolean isInvalid(double value) {
/* 24 */     return (value < 0.0D || Double.isNaN(value) || Double.isInfinite(value));
/*    */   }
/*    */ }


/* Location:              C:\Users\joaop\Desktop\blizzard-rankup-1.0-SNAPSHOT.jar!\blizzard\development\ranku\\utils\NumberFormat.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */