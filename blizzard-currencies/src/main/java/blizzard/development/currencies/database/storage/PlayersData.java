package blizzard.development.currencies.database.storage;

public class PlayersData {
    private String uuid, nickname;
    private Double souls, flakes, fossils, spawnersLimit;

    public PlayersData(String uuid, String nickname, Double souls, Double flakes, Double fossils, Double spawnersLimit) {
        this.uuid = uuid;
        this.nickname = nickname;
        this.souls = souls;
        this.flakes = flakes;
        this.fossils = fossils;
        this.spawnersLimit = spawnersLimit;
    }

    // Commons

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    // Currencies

    public Double getSouls() {
        return souls;
    }

    public void setSouls(Double souls) {
        this.souls = souls;
    }

    public Double getFlakes() {
        return flakes;
    }

    public void setFlakes(Double flakes) {
        this.flakes = flakes;
    }

    public Double getFossils() {
        return fossils;
    }

    public void setFossils(Double fossils) {
        this.fossils = fossils;
    }

    public Double getSpawnersLimit() {
        return spawnersLimit;
    }

    public void setSpawnersLimit(Double spawnersLimit) {
        this.spawnersLimit = spawnersLimit;
    }
}
