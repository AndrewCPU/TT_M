package monkeyboystein.utils;

import monkeyboystein.Arena.ArenaAPI;
import monkeyboystein.Arena.ArenaState;
import monkeyboystein.Main.Main;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

/**
 * Created by Andrew on 12/19/2014.
 */
public class UserManagement {
    Storage storage = Main.storage;
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

    }

}
