package monkeyboystein.utils;

/**
 * Created by Andrew on 12/19/2014.
 */
public class ArenaScore {
    int score = 0;
    String playerName;
    String arenaName;

    public ArenaScore(int score, String playerName, String arenaName) {
        this.score = score;
        this.playerName = playerName;
        this.arenaName = arenaName;
    }
    public int getScore()
    {
        return score;
    }
    public String getPlayerName()
    {
        return playerName;
    }

    public void score()
    {
        score+=1;
    }
    public void score(int i)
    {
        score+=i;
    }
    public void setScore(int i)
    {
        score = i;
    }

}
