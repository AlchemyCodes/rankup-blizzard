package blizzard.development.core.database.storage;

import blizzard.development.core.clothing.ClothingType;

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

    public String getUuid() {
        return this.uuid;
    }

    public String getNickname() {
        return this.nickname;
    }

    public double getTemperature() {
        return this.temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    public ClothingType getClothingType() {
        return this.clothingType;
    }

    public void setClothingType(ClothingType clothingType) {
        this.clothingType = clothingType;
    }
}
