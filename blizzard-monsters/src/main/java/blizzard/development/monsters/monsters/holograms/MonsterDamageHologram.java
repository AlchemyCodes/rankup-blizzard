package blizzard.development.monsters.monsters.holograms;

import java.util.List;

public class MonsterDamageHologram {
    public static List<String> getLines(Integer damage) {
        return List.of(
                "§c❤" + damage
        );
    }
}
