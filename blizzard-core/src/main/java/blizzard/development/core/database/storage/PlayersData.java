package blizzard.development.core.database.storage;

import blizzard.development.core.clothing.ClothingType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PlayersData {
    private String uuid;
    private String nickname;
    private double temperature;
    private ClothingType clothingType;

    public PlayersData(String uuid, String nickname, double temperature, ClothingType clothingType) {
        this.uuid = uuid;
        this.nickname = nickname;
        this.temperature = temperature;
        this.clothingType = clothingType;
    }
}
