package blizzard.development.plantations.database.storage;

public class PlayerData {

    private String nickname, uuid;
    private Integer plantations;
    private Boolean isInPlantation;

    public PlayerData(String nickname, String uuid, Integer plantations, boolean isInPlantation) {
        this.nickname = nickname;
        this.uuid = uuid;
        this.plantations = plantations;
        this.isInPlantation = isInPlantation;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public Integer getPlantations() {
        return plantations;
    }

    public void setPlantations(Integer plantations) {
        this.plantations = plantations;
    }

    public Boolean getIsInPlantation() {
        return isInPlantation;
    }

    public void setInPlantation(Boolean inPlantation) {
        isInPlantation = inPlantation;
    }
}
