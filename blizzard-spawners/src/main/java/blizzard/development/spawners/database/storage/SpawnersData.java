package blizzard.development.spawners.database.storage;

public class SpawnersData {
    private String id, type, location, nickname;

    public SpawnersData(String id, String type, String location, String nickname) {
        this.id = id;
        this.type = type;
        this.location = location;
        this.nickname = nickname;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
}
