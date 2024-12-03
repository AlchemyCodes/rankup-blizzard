package blizzard.development.essentials.utils.luckperms;

import net.luckperms.api.LuckPerms;
import net.luckperms.api.LuckPermsProvider;
import net.luckperms.api.model.user.User;
import net.luckperms.api.query.QueryOptions;

import java.util.UUID;

public class LuckPermsUtils {

    public static String getPlayerPrefix(UUID playerUUID) {
        LuckPerms luckPerms = LuckPermsProvider.get();
        User user = luckPerms.getUserManager().loadUser(playerUUID).join();
        QueryOptions queryOptions = luckPerms.getContextManager().getStaticQueryOptions();
        String prefix = user.getCachedData().getMetaData(queryOptions).getPrefix();
        return prefix.replace('&', '§');
    }
}
