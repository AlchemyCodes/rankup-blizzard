package blizzard.development.clans.tasks;

import org.bukkit.scheduler.BukkitRunnable;
import blizzard.development.clans.database.cache.ClansCacheManager;
import blizzard.development.clans.database.cache.PlayersCacheManager;
import blizzard.development.clans.database.storage.ClansData;

import java.text.DecimalFormat;
import java.util.List;

public class KillDeathRatioTask extends BukkitRunnable {

    @Override
    public void run() {
        for (ClansData clanData : ClansCacheManager.getAllClans()) {
            String clanName = clanData.getClan();
            List<String> members = ClansCacheManager.getMembers(clanName);

            double totalKDR = 0.0;
            int memberCount = 0;

            for (String memberName : members) {
                int kills = PlayersCacheManager.getKills(memberName);
                int deaths = PlayersCacheManager.getDeaths(memberName);

                double kdr = deaths > 0 ? (double) kills / deaths : kills;

                totalKDR += kdr;
                memberCount++;
            }

            double averageKDR = memberCount > 0 ? totalKDR / memberCount : 0.0;

            DecimalFormat decimalFormat = new DecimalFormat("#.##");

            ClansCacheManager.setKdr(clanName, Double.parseDouble(decimalFormat.format(averageKDR)));
        }
    }
}
