package blizzard.development.essentials.database.storage;

public class HomeData {

    private String uuid, nickname, name, location;

    public HomeData(String uuid, String nickname, String name, String location) {
        this.uuid = uuid;
        this.nickname = nickname;
        this.name = name;
        this.location = location;
    }

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
