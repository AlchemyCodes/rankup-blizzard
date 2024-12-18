package blizzard.development.plantations.database.storage;

public class PlayerData {

    private String uuid, nickname;
    private Integer area, blocks, plantations;
    private Boolean isInPlantation;

    public PlayerData(String uuid, String nickname, Integer area, Integer blocks, Integer plantations, boolean isInPlantation) {
        this.uuid = uuid;
        this.nickname = nickname;
        this.area = area;
        this.blocks = blocks;
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

    public Integer getArea() {
        return area;
    }

    public void setArea(Integer area) {
        this.area = area;
    }

    public Integer getBlocks() {
        return blocks;
    }

    public void setBlocks(Integer blocks) {
        this.blocks = blocks;
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
