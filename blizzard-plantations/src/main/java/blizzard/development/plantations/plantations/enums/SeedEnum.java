package blizzard.development.plantations.plantations.enums;

public enum SeedEnum {

    LETTUCE("alface"),

    TOMATO("tomate"),

    STRAWBERRY("morango"),

    POTATO("batata");

    private final String name;

    SeedEnum(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

}
