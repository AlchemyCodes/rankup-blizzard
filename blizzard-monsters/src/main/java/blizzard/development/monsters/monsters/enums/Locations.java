package blizzard.development.monsters.monsters.enums;

import lombok.Getter;

public @Getter enum Locations {
    ENTRY("entry"),
    EXIT("exit");

    private final String name;

    Locations(String name) {
        this.name = name;
    }
}
