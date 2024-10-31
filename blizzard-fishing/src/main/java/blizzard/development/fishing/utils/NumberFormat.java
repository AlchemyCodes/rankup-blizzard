package blizzard.development.fishing.utils;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

public class NumberFormat {
    public static final double LOG = 6.907755278982137D;
    public static final Object[][] VALUES = new Object[][] {
            { "", "K", "M", "B", "T", "Q", "QQ", "S", "SS", "O", "N", "D", "UN", "DD", "TR", "QT", "QN", "SD", "SPD", "OD", "ND", "VG", "UVG", "DVG", "TVG", "QTV" },
            { Double.valueOf(1.0D), Double.valueOf(1000.0D), Double.valueOf(1000000.0D), Double.valueOf(1.0E9D), Double.valueOf(1.0E12D), Double.valueOf(1.0E15D), Double.valueOf(1.0E18D), Double.valueOf(1.0E21D), Double.valueOf(1.0E24D), Double.valueOf(1.0E27D), Double.valueOf(1.0E30D), Double.valueOf(1.0E33D), Double.valueOf(1.0E36D), Double.valueOf(1.0E39D), Double.valueOf(1.0E42D), Double.valueOf(1.0E45D), Double.valueOf(1.0E48D), Double.valueOf(1.0E51D), Double.valueOf(1.0E54D), Double.valueOf(1.0E57D), Double.valueOf(1.0E60D), Double.valueOf(1.0E63D), Double.valueOf(1.0E66D), Double.valueOf(1.0E69D), Double.valueOf(1.0E72D) }
    };

    public static final DecimalFormat FORMAT = new DecimalFormat("#,###.##", new DecimalFormatSymbols(new Locale("pt", "BR")));

    public static String formatNumber(double number) {
        if (isInvalid(number)) {
            return "Invalid number";
        }
        if (number == 0.0D) {
            return FORMAT.format(number);
        }
        int index = (int)(Math.log(number) / LOG);
        if (index >= VALUES[0].length || index < 0) {
            return FORMAT.format(number);
        }
        double baseValue = (Double) VALUES[1][index];
        return FORMAT.format(number / baseValue) + " " + VALUES[0][index];
    }

    public static boolean isInvalid(double value) {
        return (value < 0.0D || Double.isNaN(value) || Double.isInfinite(value));
    }
}
