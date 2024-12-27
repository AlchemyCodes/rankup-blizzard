package blizzard.development.spawners.handlers.enums.spawners;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum Spawners {
    PIG("porco"),
    COW("vaca"),
    MOOSHROOM("coguvaca"),
    SHEEP("ovelha"),
    ZOMBIE("zumbi");
    //
//    SKELETON("esqueleto"),
//    CREEPER("creeper"),
//    PIGMAN("pigman"),
//    BLAZE("blaze"),
//    WITCH("bruxa");

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
