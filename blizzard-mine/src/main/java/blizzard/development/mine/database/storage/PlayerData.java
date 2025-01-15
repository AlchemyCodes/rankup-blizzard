package blizzard.development.mine.database.storage;

import blizzard.development.mine.mine.enums.BlockEnum;

import java.util.ArrayList;
import java.util.List;

public class PlayerData {

    private String uuid, nickname, area_block;
    private Integer area, blocks;
    private Boolean isInMine;
    private List<String> friends;

    public PlayerData(String uuid, String nickname, Integer area, String area_block, Integer blocks, boolean isInMine, List<String> friends) {
        this.uuid = uuid;
        this.nickname = nickname;
        this.area = area;
        this.area_block = area_block;
        this.blocks = blocks;
        this.isInMine = isInMine;
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

    public String getAreaBlock() {
        return area_block;
    }

    public void setAreaBlock(String area_block) {
        this.area_block = area_block;
    }

    public void setAreaBlock(BlockEnum blockEnum) {
        this.area_block = blockEnum.getType();
    }

    public Integer getBlocks() {
        return blocks;
    }

    public void setBlocks(Integer blocks) {
        this.blocks = blocks;
    }

    public Boolean getIsInMine() {
        return isInMine;
    }

    public void setInPlantation(Boolean inPlantation) {
        isInMine = inPlantation;
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
