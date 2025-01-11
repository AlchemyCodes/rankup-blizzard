package blizzard.development.mail.database.storage;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PlayerData {
    private String uuid, nickname;
    private List<String> items;

    public PlayerData(String uuid, String nickname, List<String> items) {
        this.uuid = uuid;
        this.nickname = nickname;
        this.items = items;
    }
}
