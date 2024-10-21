package blizzard.development.currencies.enums;

public enum Currencies {
    SOULS("souls");

    private final String name;

    Currencies(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
