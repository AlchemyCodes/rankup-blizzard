package blizzard.development.vips.database.storage;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PlayersData {
    private String uuid, nickname;
    private int id;
    private String vipActivationDate;
    private String vipId;
    private String vipName;
    private long vipDuration;

    public PlayersData(String uuid, int id, String nickname, String vipActivationDate, String vipId, String vipName, long vipDuration) {
        this.uuid = uuid;
        this.id = id;
        this.nickname = nickname;
        this.vipId = vipId;
        this.vipName = vipName;
        this.vipDuration = vipDuration;
        this.vipActivationDate = vipActivationDate;
    }
}
