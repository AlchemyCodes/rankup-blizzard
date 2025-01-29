package blizzard.development.farm.database.storage;

public class ToolData {

    private String uuid, nickname;
    private Integer plantations, fortune;

    public ToolData(String uuid, String nickname, Integer plantations, Integer fortune) {
        this.uuid = uuid;
        this.nickname = nickname;
        this.plantations = plantations;
        this.fortune = fortune;
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

    public Integer getPlantations() {
        return plantations;
    }

    public void setPlantations(Integer plantations) {
        this.plantations = plantations;
    }

    public Integer getFortune() {
        return fortune;
    }

    public void setFortune(Integer fortune) {
        this.fortune = fortune;
    }
}
