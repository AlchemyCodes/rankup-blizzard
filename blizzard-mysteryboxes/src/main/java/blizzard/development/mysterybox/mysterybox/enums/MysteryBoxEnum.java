package blizzard.development.mysterybox.mysterybox.enums;

public enum MysteryBoxEnum {

    RARE("Rara"),
    LEGENDARY("Lendária"),
    BLIZZARD("Blizzard");


    private final String name;

    MysteryBoxEnum(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
