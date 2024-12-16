package blizzard.development.spawners.utils;

import blizzard.development.spawners.handlers.enums.slaughterhouses.States;

public class SlaughterhousesUtils {
    private static SlaughterhousesUtils instance;

    public String getSlaughterhouseState(States state) {
        if (state.getState().equalsIgnoreCase(States.OFF.getState())) {
            return "§cDesligado";
        } else if (state.getState().equalsIgnoreCase(States.ON.getState())) {
            return "§aLigado";
        }
        return null;
    }

    public static SlaughterhousesUtils getInstance() {
        if (instance == null) instance = new SlaughterhousesUtils();
        return instance;
    }
}
