package blizzard.development.mine.mine.enums;

public enum BlockEnum {

    STONE("Pedra", 1),
    COBBLESTONE("Pedregulho", 2),
    GRANITE("Granito", 3),
    DIORITE("Diorito", 4),
    ANDESITE("Andesito", 5),
    DEEPSLATE("Ardosia", 6),
    PRISMARINE("Prismarinho", 7),
    ICE("Gelo", 8),
    AVALANCHE_SNOW("AvalancheManager", 6);

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
