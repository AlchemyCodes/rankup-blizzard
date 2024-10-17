package blizzard.development.core.database.storage;

public class PlayersData {
    private String uuid, nickname;
    private double temperature;

    public PlayersData(String uuid, String nickname, double temperature) {
        this.uuid = uuid;
        this.nickname = nickname;
    }

    public String getUuid() {
        return uuid;
    }

    public String getNickname() {
        return nickname;
    }

    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }
}
