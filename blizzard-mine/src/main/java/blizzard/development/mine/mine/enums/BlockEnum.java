package blizzard.development.mine.mine.enums;

public enum BlockEnum {

    STONE("Pedra"),
    COBBLESTONE("Pedregulho"),
    ANDESITE("Andesito");

    private final String type;

    BlockEnum(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
