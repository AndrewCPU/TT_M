package monkeyboystein.utils;

import monkeyboystein.Arena.ArenaAPI;
import monkeyboystein.Main.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;

/**
 * Created by Andrew on 12/20/2014.
 */
public class MainScoreboardManager {
    Storage storage = Main.storage;
    public void updateScoreboard()
    {
        for(Player p : Bukkit.getOnlinePlayers())
        {
            if(storage.getArenaManager().isInArena(p))
            {
                updateInGame(p);
            }
            else
            {
                updateMain( p);
            }
        }
    }
    public void updateInGame(Player p)
    {
        ArenaAPI arenaAPI = storage.getArenaManager().getPlayerArena(p);
        if(arenaAPI!=null)
        {
            Scoreboard scoreboard = storage.getMain().getServer().getScoreboardManager().getNewScoreboard();
            Objective objective = scoreboard.registerNewObjective(p.getName(), "dummy");
            for(ArenaScore score : arenaAPI.getScores())
            {
                Score pScore = objective.getScore(score.playerName);
                pScore.setScore(score.score);
            }
            objective.setDisplaySlot(DisplaySlot.SIDEBAR);
            objective.setDisplayName(ChatColor.BLUE + "Miners Run");
            p.setScoreboard(scoreboard);
        }
    }
    public void updateMain(Player p)
    {

            Scoreboard scoreboard = storage.getMain().getServer().getScoreboardManager().getNewScoreboard();
            Objective objective = scoreboard.registerNewObjective(p.getName(),"dummy");
            Score gems = objective.getScore(ChatColor.GREEN + "Gems");
            //gems set to database connection
            gems.setScore(15);
            int gemAmount = storage.getSql().getGems(p.getName());
            Score gemValue = objective.getScore(ChatColor.BOLD + "" + gemAmount);
            gemValue.setScore(14);
            Score blankSpot1 = objective.getScore(ChatColor.translateAlternateColorCodes('&', "&a"));
            blankSpot1.setScore(13);
            Score rank = objective.getScore(ChatColor.BLUE + "Rank");
            rank.setScore(12);
            String rankString = storage.getSql().getRank(p.getName());
            Score rankValue = objective.getScore(ChatColor.BOLD  +rankString);
            rankValue.setScore(11);
            Score blank2 = objective.getScore(ChatColor.translateAlternateColorCodes('&', "&b"));
            blank2.setScore(10);
            Score website = objective.getScore(ChatColor.GOLD + "Website");
            website.setScore(9);
            String websiteString = storage.getMain().getConfig().getString("Website");

            Score websiteValue = objective.getScore(websiteString);
            websiteValue.setScore(8);
            Score closingLine = objective.getScore("------------");
            closingLine.setScore(7);
            objective.setDisplayName(ChatColor.translateAlternateColorCodes('&', storage.getMain().getConfig().getString("ScoreboardHeader")));
            objective.setDisplaySlot(DisplaySlot.SIDEBAR);
            p.setScoreboard(scoreboard);



    }
}
