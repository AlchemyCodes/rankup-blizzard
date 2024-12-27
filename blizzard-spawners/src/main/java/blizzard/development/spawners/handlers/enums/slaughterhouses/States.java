package blizzard.development.spawners.handlers.enums.slaughterhouses;

public enum States {
    OFF("off"),
    ON("on");

    private final String state;

    States(String state) {
        this.state = state;
    }

    public String getState() {
        return state;
    }
}
