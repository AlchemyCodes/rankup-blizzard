package blizzard.development.monsters.monsters.enums;

import lombok.Getter;

public @Getter enum Upgrades {

    DAMAGE("damage");

    private final String type;

    Upgrades(String type) {
        this.type = type;
    }
}
