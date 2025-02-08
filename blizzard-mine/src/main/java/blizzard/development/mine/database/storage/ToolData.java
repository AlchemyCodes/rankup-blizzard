package blizzard.development.mine.database.storage;

public class ToolData {

    private String id, type, skin, owner;
    private Integer blocks;
    private Integer meteor;

    public ToolData(String id, String type, String skin, String owner, Integer blocks, Integer meteor) {
        this.id = id;
        this.type = type;
        this.skin = skin;
        this.owner = owner;
        this.blocks = blocks;
        this.meteor = meteor;
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

    public String getSkin() {
        return skin;
    }

    public void setSkin(String skin) {
        this.skin = skin;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
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
