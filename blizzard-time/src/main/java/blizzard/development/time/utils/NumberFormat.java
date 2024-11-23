package blizzard.development.time.utils;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NumberFormat {
    private static NumberFormat instance;
    public final double LOG = 6.907755278982137D;
    public final Object[][] VALUES = {
            {"", "K", "M", "B", "T", "Q", "QQ", "S", "SS", "O", "N", "D", "UN", "DD", "TR", "QT", "QN", "SD", "SPD", "OD", "ND", "VG", "UVG", "DVG", "TVG", "QTV"},
            {1D, 1000.0D, 1000000.0D, 1.0E9D, 1.0E12D, 1.0E15D, 1.0E18D, 1.0E21D, 1.0E24D, 1.0E27D, 1.0E30D, 1.0E33D, 1.0E36D, 1.0E39D, 1.0E42D, 1.0E45D, 1.0E48D, 1.0E51D, 1.0E54D, 1.0E57D, 1.0E60D, 1.0E63D, 1.0E66D, 1.0E69D, 1.0E72D}
    };
    public final DecimalFormat FORMAT = new DecimalFormat("#,###.##", new DecimalFormatSymbols(new Locale("pt", "BR")));

    public String formatNumber(double number) {
        if (number == 0) return FORMAT.format(number);
        int index = (int) (Math.log(number) / LOG);
        return FORMAT.format(number / (double) VALUES[1][index]) + VALUES[0][index];
    }

    public Double parseNumber(String input) {
        if (input == null || input.isEmpty()) return null;

        input = input.trim().toUpperCase();

        Pattern pattern = Pattern.compile("^(\\d*\\.?\\d+)\\s*([A-Z]+)?$");
        Matcher matcher = pattern.matcher(input);

        if (!matcher.matches()) return null;

        try {
            double number = Double.parseDouble(matcher.group(1));

            if (number == 0) return null;

            if (matcher.group(2) == null) return number;

            String suffix = matcher.group(2);

            for (int i = 0; i < VALUES[0].length; i++) {
                if (suffix.equals(VALUES[0][i].toString())) {
                    return number * (double) VALUES[1][i];
                }
            }

            return null;

        } catch (NumberFormatException e) {
            return null;
        }
    }


    public boolean isInvalid(double value) {
        return value < 0 || Double.isNaN(value) || Double.isInfinite(value);
    }

    public static NumberFormat getInstance() {
        if (instance == null) instance = new NumberFormat();
        return instance;
    }
}