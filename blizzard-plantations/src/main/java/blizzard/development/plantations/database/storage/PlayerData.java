package blizzard.development.plantations.database.storage;

import blizzard.development.plantations.plantations.enums.PlantationEnum;

import java.util.ArrayList;
import java.util.List;

public class PlayerData {

    private String uuid, nickname, area_plantation;
    private Integer area, blocks, plantations;
    private Boolean isInPlantation;
    private List<String> friends;

    public PlayerData(String uuid, String nickname, Integer area, String area_plantation, Integer blocks, Integer plantations, boolean isInPlantation, List<String> friends) {
        this.uuid = uuid;
        this.nickname = nickname;
        this.area = area;
        this.area_plantation = area_plantation;
        this.blocks = blocks;
        this.plantations = plantations;
        this.isInPlantation = isInPlantation;
        this.friends = friends;
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

    public String getAreaPlantation() {
        return area_plantation;
    }

    public void setAreaPlantation(PlantationEnum plantationEnum) {
        this.area_plantation = plantationEnum.getName();
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

    public List<String> getFriends() {
        if (friends == null) {
            friends = new ArrayList<>();
        }
        return friends;
    }

    public void setFriends(List<String> friends) {
        this.friends = friends;
    }
}
