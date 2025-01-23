package blizzard.development.mine.database.storage;

import java.util.List;

public class ToolData {

    private String uuid, nickname;
    private Integer blocks;
    private Integer meteor;

    public ToolData(String uuid, String nickname, Integer blocks, Integer meteor) {
        this.uuid = uuid;
        this.nickname = nickname;
        this.blocks = blocks;
        this.meteor = meteor;
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

    public Integer getBlocks() {
        return blocks;
    }

    public void setBlocks(Integer blocks) {
        this.blocks = blocks;
    }

    public Integer getMeteor() {
        return meteor;
    }

    public void setMeteor(Integer meteor) {
        this.meteor = meteor;
    }
}
