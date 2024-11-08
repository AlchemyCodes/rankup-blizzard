package blizzard.development.spawners.handlers.enums;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

    public static List<String> getAllTypes() {
        return Stream.of(Spawners.values())
                .map(Spawners::getType)
                .collect(Collectors.toList());
    }
}
