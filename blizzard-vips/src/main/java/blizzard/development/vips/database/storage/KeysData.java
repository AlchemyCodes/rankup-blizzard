package blizzard.development.vips.database.storage;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class KeysData {
    public String key;
    public String vipName;
    public long duration;

    public KeysData(String key, String vipName, long duration) {
        this.key = key;
        this.vipName = vipName;
        this.duration = duration;
    }

}
