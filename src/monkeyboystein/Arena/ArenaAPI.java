package monkeyboystein.Arena;

import monkeyboystein.Main.Main;
import monkeyboystein.utils.*;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.entity.Player;

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
    int time = 600;
    List<ArenaScore> scores = new ArrayList<ArenaScore>();
    ArenaPlayerSpawns spawns = new ArenaPlayerSpawns();
    Lobby lobby = new Lobby(null);
    ArenaState state = ArenaState.OFF;
    Location signLoc;

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
    public void startCountdown()
    {

    }
    public void update()
    {
        if(players.size()==storage.getMaxPlayers())
        {
            startCountdown();
        }
        storage.getSignManager().updateSign(this);

    }

    public void removePlayer(String s)
    {
        if(players.contains(s))
        {
            players.add(s);
        }
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
        storage.getDecay().undo(this);
        MapUtils.regenerateMap(this);
        scores = new ArrayList<ArenaScore>();
        time = 600;
        setCurrent1(getLower());
        Location higher = getHigher();
        higher.setY(getLower().getY());
        setCurrent2(higher);

    }


    public void tick()
    {
        time-=1;
        if(time<=0)
        {
            endGame();
        }

        for(String s : players)
        {
            Bukkit.getPlayer(s).setExp(time);
        }

    }
}
