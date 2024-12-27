package blizzard.development.monsters.monsters.enums;

import lombok.Getter;

public @Getter enum Tools {
    SWORD("sword");

    private final String type;

    Tools(String type) {
        this.type = type;
    }
}
