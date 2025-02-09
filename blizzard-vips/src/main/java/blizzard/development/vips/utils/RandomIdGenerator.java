package blizzard.development.vips.utils;

import java.security.SecureRandom;

public class RandomIdGenerator {
    private static final String LETTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final int NUMERIC_LENGTH = 6;
    private static final SecureRandom RANDOM = new SecureRandom();

    public static String generateId() {
        StringBuilder vipId = new StringBuilder();
        for (int i = 0; i < 2; i++) {
            char randomLetter = LETTERS.charAt(RANDOM.nextInt(LETTERS.length()));
            vipId.append(randomLetter);
        }

        for (int i = 0; i < NUMERIC_LENGTH; i++) {
            int randomDigit = RANDOM.nextInt(10);
            vipId.append(randomDigit);
        }

        return vipId.toString();
    }
}