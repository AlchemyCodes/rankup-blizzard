package blizzard.development.excavation.database.storage;

public class PlayerData {

    private String uuid, nickname;
    private Boolean isInExcavation;
    private Integer blocks;

    public PlayerData(String uuid, String nickname, boolean isInExcavation, Integer blocks) {
        this.uuid = uuid;
        this.nickname = nickname;
        this.isInExcavation = isInExcavation;
        this.blocks = blocks;
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

    public Boolean getInExcavation() {
        return isInExcavation;
    }

    public void setInExcavation(Boolean inExcavation) {
        isInExcavation = inExcavation;
    }

    public Integer getBlocks() {
        return blocks;
    }

    public void setBlocks(Integer blocks) {
        this.blocks = blocks;
    }
}
