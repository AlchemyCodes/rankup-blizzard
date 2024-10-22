package blizzard.development.currencies.enums;

public enum Currencies {
    COINS("coins"),
    SOULS("souls"),
    FLAKES("flakes"),
    FOSSILS("fossils");

    private final String name;

    Currencies(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
