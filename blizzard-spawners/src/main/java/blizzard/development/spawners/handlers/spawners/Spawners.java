package blizzard.development.spawners.handlers.spawners;

public enum Spawners {
    PIG("porco"),
    COW("vaca");

    private final String type;

    Spawners(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
