package blizzard.development.vips.database.storage;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VipData {
    private String vipId;
    private String uuid;
    private String nickname;
    private String vipActivationDate;
    private String vipName;
    private long vipDuration;

    public VipData(String vipId, String uuid, String nickname, String vipActivationDate, String vipName, long vipDuration) {
        this.vipId = vipId;
        this.uuid = uuid;
        this.nickname = nickname;
        this.vipActivationDate = vipActivationDate;
        this.vipName = vipName;
        this.vipDuration = vipDuration;
    }
}
