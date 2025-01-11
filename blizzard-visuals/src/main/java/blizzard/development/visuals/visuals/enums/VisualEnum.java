package blizzard.development.visuals.visuals.enums;

public enum VisualEnum {

    STONE("PEDRA"),
    IRON("FERRO"),
    GOLD("OURO"),
    DIAMOND("DIAMANTE");

    private final String name;

    VisualEnum(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
