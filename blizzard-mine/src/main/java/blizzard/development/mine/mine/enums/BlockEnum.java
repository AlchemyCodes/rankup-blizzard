package blizzard.development.mine.mine.enums;

public enum BlockEnum {

    ICE("Gelo", 1),
    PACKED_ICE("Gelo Compacto", 2),
    BLUE_ICE("Gelo Azul", 3),
    COBBLED_DEEPSLATE("Rocha", 4),
    DEEPSLATE("Rocha Compacta", 5),
    AVALANCHE_SNOW("Avalanche", 6);

    private final String type;
    private final double value;

    BlockEnum(String type, double value) {
        this.type = type;
        this.value = value;
    }

    public String getType() {
        return type;
    }

    public double getValue() {
        return value;
    }
}
