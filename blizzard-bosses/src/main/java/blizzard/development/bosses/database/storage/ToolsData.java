package blizzard.development.bosses.database.storage;

public class ToolsData {
    private String id, type, nickname;

    public ToolsData(String id, String type, String nickname) {
        this.id = id;
        this.type = type;
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

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
}
