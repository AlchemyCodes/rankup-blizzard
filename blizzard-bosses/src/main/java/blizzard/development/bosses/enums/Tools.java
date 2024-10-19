package blizzard.development.bosses.enums;

public enum Tools {
    SWORD("sword");

    private final String type;

    Tools(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
