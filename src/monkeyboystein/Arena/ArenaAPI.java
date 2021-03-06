package monkeyboystein.Arena;

import monkeyboystein.Main.Main;
import monkeyboystein.utils.*;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Andrew on 12/19/2014.
 */
public class ArenaAPI {
    private Storage storage = Main.storage;
    String name;
    List<String> players;
    Location corner1;
    Location corner2;
    Location current1;
    Location current2;
    List<Block> arenaBlocks;
    int time = 300;
    List<ArenaScore> scores = new ArrayList<ArenaScore>();
    ArenaPlayerSpawns spawns = new ArenaPlayerSpawns();
    Lobby lobby = new Lobby(null);
    ArenaState state = ArenaState.OFF;
    Location signLoc;
    Location center;

    public Location getCenter() {
        return center;
    }

    public void setCenter(Location center) {
        this.center = center;
    }

    public void removeScore(ArenaScore a)
    {
        scores.remove(a);
    }
    public void addScore(ArenaScore a)
    {
        scores.add(a);
    }
    public Location getSignLoc() {
        return signLoc;
    }

    public void setSignLoc(Location signLoc) {
        this.signLoc = signLoc;
    }

    public ArenaState getState() {
        return state;
    }

    public void setState(ArenaState state) {
        this.state = state;
    }

    public List<BlockState> getBrokenBlocks() {
        return brokenBlocks;
    }

    public void setBrokenBlocks(List<BlockState> brokenBlocks) {
        this.brokenBlocks = brokenBlocks;
    }

    int topY;
    List<BlockState> brokenBlocks = new ArrayList<BlockState>();

    public void addBlock(BlockState state1)
    {
        brokenBlocks.add(state1);
    }

    public int getTopY() {
        return topY;
    }

    public void setTopY(int topY) {
        this.topY = topY;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getPlayers() {
        return players;
    }

    public void setPlayers(List<String> players) {
        this.players = players;
    }
    public void addPlayer(String s)
    {
        if(players.contains(s))
        {

        }
        else
        {
            players.add(s);
        }
        update();
    }
    List<BukkitTask> tasks = new ArrayList<BukkitTask>();
    public void startCountdown()
    {
        setState(ArenaState.STARTING);
        for(int i = 10; i>=0; i--)
        {
            final int time = i;
            int num = 0;
            if(i==10)
            {
                num = 0;
            }
            else if(i==9)
            {
                num = 1;
            }
            else if(i==8)
            {
                num = 2;
            }
            else if(i==7)
            {
                num = 3;
            }
            else if(i==6)
            {
                num = 4;
            }
            else if(i==5)
            {
                num = 5;
            }
            else if(i==4)
            {
                num = 6;
            }
            else if(i==3)
            {
                num = 7;
            }
            else if(i==2)
            {
                num = 8;
            }
            else if(i==1)
            {
                num = 9;
            }
            else if(i==0)
            {
                num = 10;
            }
            BukkitTask task = new BukkitRunnable()
            {
                @Override
                public void run()
                {
                    if(storage.getMaxPlayers()==players.size())
                    {
                        if(time==0)
                        {
                            startGame();
                            tasks.clear();
                            cancel();
                        }
                        else
                        {
                            for(String s : getPlayers())
                            {
                                Bukkit.getPlayer(s).sendMessage(storage.getHeader() + "Game starting in " + time);
                            }
                        }

                    }
                    else
                    {
                        for(BukkitTask bukkitTask : tasks)
                        {
                            bukkitTask.cancel();
                        }
                    }
                }
            }.runTaskLater(storage.getMain(), num * 20);
            tasks.add(task);

        }
    }
    public void update()
    {
        if(players.size()==storage.getMaxPlayers() && getState()==ArenaState.OFF)
        {
            startCountdown();
        }
        storage.getSignManager().updateSign(this);

    }

    public void removePlayer(String s)
    {
        if(players.contains(s))
        {
            players.remove(s);

        }
        if(dead.contains(s))
        {
            dead.remove(s);
        }

        Bukkit.getPlayer(s).teleport(Bukkit.getPlayer(s).getWorld().getSpawnLocation());
        Bukkit.getPlayer(s).getInventory().setContents(Bukkit.createInventory(null,Bukkit.getPlayer(s).getInventory().getSize()).getContents());
        Bukkit.getPlayer(s).getEquipment().setArmorContents(null);
        update();
    }

    public Location getCorner1() {
        return corner1;
    }

    public void setCorner1(Location corner1) {
        this.corner1 = corner1;
    }

    public Location getCorner2() {
        return corner2;
    }

    public void setCorner2(Location corner2) {
        this.corner2 = corner2;
    }

    public Location getCurrent1() {
        return current1;
    }

    public void setCurrent1(Location current1) {
        this.current1 = current1;
    }

    public Location getCurrent2() {
        return current2;
    }

    public void setCurrent2(Location current2) {
        this.current2 = current2;
    }

    public List<Block> getArenaBlocks() {
        return arenaBlocks;
    }

    public void setArenaBlocks(List<Block> arenaBlocks) {
        this.arenaBlocks = arenaBlocks;
    }
    public void setup()
    {
        lobby = new Lobby(Lobby.getLobbyLocation(this));
        lobby.setSpawn(Lobby.getLobbyLocation(this));
        for(int i = 1; i<=storage.getMaxPlayers(); i++)
        {
            spawns.setPlayerSpawn(i,ArenaPlayerSpawns.getSpawnLocation(i,this));
        }
    }
    public ArenaAPI(String name) {
        this.name = name;
       setup();
    }
    public ArenaAPI(String name, Location c1, Location c2) {
        this.name = name;
        setCorner1(c1);
        setCorner2(c2);
        setup();
    }
    public ArenaAPI(String name, int top) {
        this.name = name;
        topY = top;
        setup();
    }
    public ArenaAPI(String name, Location c1, Location c2,  int top) {
        this.name = name;
        setCorner1(c1);
        setCorner2(c2);
        topY = top;
        setup();
    }

    public void endGame()
    {
//todo this
        ArenaScore topPlayer = null;
        for(ArenaScore arenaScore : scores)
        {
            if(topPlayer==null)
            {
                topPlayer = arenaScore;
            }
            else
            {
                if(topPlayer.getScore()<arenaScore.getScore())
                {
                    topPlayer = arenaScore;
                }
            }
        }
        if(topPlayer!=null)
        {
            Bukkit.broadcastMessage(storage.getHeader() + topPlayer.getPlayerName() + " has won with " + topPlayer.getScore() + " on " + ChatColor.RED +  getName());
            for(String s : players)
            {
                ArenaScore score = null;
                for(ArenaScore arenaScore : scores)
                {
                    if(arenaScore.getPlayerName().equalsIgnoreCase(s))
                    {
                        score = arenaScore;
                    }

                }
                if(score!=null)
                {
                    int gems = score.getScore() / 2;
                    gems+=15;
                    storage.getSql().setGems(s, storage.getSql().getGems(s) + gems);
                    Player p = Bukkit.getPlayer(s);
                    p.sendMessage(ChatColor.GREEN + "------------------------------");
                    p.sendMessage(ChatColor.GREEN + "+" + gems + ChatColor.WHITE +" in total");
                    p.sendMessage(ChatColor.GRAY + "+" + 10 + ChatColor.WHITE + " for participation");
                    p.sendMessage(ChatColor.BLUE + "+" + 5 + ChatColor.WHITE + " because I'm nice");
                    p.sendMessage(ChatColor.GREEN + "------------------------------");
                    p.getInventory().setContents(Bukkit.createInventory(null,p.getInventory().getSize()).getContents());
                    p.getEquipment().setArmorContents(null);
                    p.setLevel(0);
                    storage.getMainScoreboardManager().updateScoreboard();
                    p.teleport(p.getWorld().getSpawnLocation());



                }
            }

        }
        reset();
    }
    public Location getLower()
    {
        if(corner1.getBlockY() < corner2.getBlockY())
        {
            return corner1;
        }
        else
        {
            return corner2;
        }
    }
    public Location getHigher()
    {
        if(corner1.getBlockY() > corner2.getBlockY())
        {
            return corner1;
        }
        else
        {
            return corner2;
        }
    }
    public void startGame()
    {
        setState(ArenaState.ON);
        int i = 1;
        for(String s : getPlayers())
        {
            Player p = Bukkit.getPlayer(s);
            p.teleport(getSpawns().getPlayerSpawn(i));
            i++;
            p.getInventory().addItem(new ItemStack(Material.DIAMOND_PICKAXE));
            p.updateInventory();
            scores.add(new ArenaScore(0,s,this.getName()));
            p.sendMessage(storage.getHeader() + "Starting game...");
        }
    }

    public Lobby getLobby() {
        return lobby;
    }

    public void setLobby(Lobby lobby) {
        this.lobby = lobby;
    }

    public ArenaPlayerSpawns getSpawns() {
        return spawns;
    }

    public void setSpawns(ArenaPlayerSpawns spawns) {
        this.spawns = spawns;
    }

    public List<ArenaScore> getScores() {
        return scores;
    }

    public void setScores(List<ArenaScore> scores) {
        this.scores = scores;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public void reset()
    {

        storage.getMain().updateArena(this);
    }
    public void addDead(String s)
    {
        dead.add(s);
    }
    public List<String> getDead()
    {
        return dead;
    }

    List<String> dead = new ArrayList<String>();
    public void tick()
    {
        time-=1;
        if(time<=0 || dead.size()==players.size() || dead.size() == (players.size() - 1) && storage.getMaxPlayers()!=1 || players.size()==1  && storage.getMaxPlayers()!=1|| players.size()==0)
        {
            endGame();
        }

        for(String s : players)
        {
            Bukkit.getPlayer(s).setLevel(time);
        }
        if(storage.getMain().isFactorOf(time,100))
        {
            storage.getDecay().decay(this);
        }


    }
}
