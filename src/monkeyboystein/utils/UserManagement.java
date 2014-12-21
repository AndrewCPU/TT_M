package monkeyboystein.utils;

import monkeyboystein.Arena.ArenaAPI;
import monkeyboystein.Arena.ArenaState;
import monkeyboystein.Main.Main;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

/**
 * Created by Andrew on 12/19/2014.
 */
public class UserManagement {
    Storage storage = Main.storage;
    public void breakBlock(BlockBreakEvent event)
    {

        if(event.getBlock().getType().toString().contains("ORE"))
        {
            int points = 0;
            if(event.getBlock().getType().toString().contains("REDSTONE"))
            {
                points = 3;
            }
            if(event.getBlock().getType().toString().contains("DIAMOND"))
            {
                points = 10;
            }
            if(event.getBlock().getType().toString().contains("COAL"))
            {
                points = 1;
            }
            if(event.getBlock().getType().toString().contains("EMERALD"))
            {
                points = 7;
            }
            if(event.getBlock().getType().toString().contains("LAPIS"))
            {
                points = 5;
            }
            if(event.getBlock().getType().toString().contains("IRON"))
            {
                points = 6;
            }
            if(event.getBlock().getType().toString().contains("GOLD"))
            {
                points = 5;
            }
            if(storage.getArenaManager().isInArena(event.getPlayer()))
            {
                ArenaAPI arenaAPI = storage.getArenaManager().getPlayerArena(event.getPlayer());
                if(arenaAPI!=null)
                {

                    event.setExpToDrop(0);

                    ArenaScore score = null;
                    for(ArenaScore s : arenaAPI.getScores())
                    {
                        if(s.playerName.equals(event.getPlayer().getName()))
                        {
                            score = s;

                        }
                    }

                    if(score!=null)
                    {
                        arenaAPI.removeScore(score);
                        score.score(points);
                        arenaAPI.addScore(score);
                        event.getPlayer().sendMessage(storage.header + "You scored " + points + " points");


                    }
                    if(event.isCancelled()==false)
                    {
                        arenaAPI.addBlock(event.getBlock().getState());
                    }

                }
            }

        }
        else
        {
            if(storage.getArenaManager().isInArena(event.getPlayer()))
            {
                event.setCancelled(true);
            }
            else
            {
                if(!event.getPlayer().isOp())
                {
                    event.setCancelled(true);
                }
            }

        }

    }
    public void eventTrigger(Event event) {
        if (event instanceof PlayerJoinEvent) {
            PlayerJoinEvent playerJoinEvent = (PlayerJoinEvent) event;


        }
        if (event instanceof PlayerQuitEvent) {
            PlayerQuitEvent playerQuitEvent = (PlayerQuitEvent) event;
            if(storage.getArenaManager().isInArena(playerQuitEvent.getPlayer()))
            {
                leaveGame(storage.getArenaManager().getPlayerArena(playerQuitEvent.getPlayer()), playerQuitEvent.getPlayer());
            }

        }
    }

    public void joinGame(ArenaAPI arena, Player p) {
        if(arena.getState()== ArenaState.OFF)
        {
            p.teleport(arena.getLobby().getSpawn());
            arena.addPlayer(p.getName());
            p.sendMessage(storage.header + "Joining game...");
        }
        else
        {
            p.sendMessage(storage.header + "Sorry you cant join this game!");
        }
    }

    public void leaveGame(ArenaAPI arena, Player p)
    {
        arena.removePlayer(p.getName());
        for(String s : arena.getPlayers())
        {
            Bukkit.getPlayer(s).sendMessage(storage.header + p.getName() + " is a quitter!");
        }
        ArenaScore score = null;
        for(ArenaScore s : arena.getScores())
        {
            if(s.playerName.equals(p.getName()))
            {
                score = s;

            }
        }
        arena.removeScore(score);

    }

}
