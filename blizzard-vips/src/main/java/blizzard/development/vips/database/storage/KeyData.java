package blizzard.development.vips.database.storage;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class KeyData {
    public String key;
    public String vipName;
    public long duration;

    public KeyData(String key, String vipName, long duration) {
        this.key = key;
        this.vipName = vipName;
        this.duration = duration;
    }

}