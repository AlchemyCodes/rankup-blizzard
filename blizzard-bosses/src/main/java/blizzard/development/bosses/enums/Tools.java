package blizzard.development.bosses.enums;

public enum Tools {
    SWORD("sword"),
    RADAR("radar");

    private final String type;

    Tools(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
