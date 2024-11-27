package blizzard.development.spawners.handlers.enums;

public enum States {
    PUBLIC("public"),
    PRIVATE("private");

    private final String state;

    States(String state) {
        this.state = state;
    }

    public String getState() {
        return state;
    }
}
