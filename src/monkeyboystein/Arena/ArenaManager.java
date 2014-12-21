package monkeyboystein.Arena;

import monkeyboystein.Main.Main;
import monkeyboystein.utils.Storage;
import org.bukkit.entity.Player;

/**
 * Created by Andrew on 12/19/2014.
 */
public class ArenaManager {
    Storage storage = Main.storage;
    public boolean isInArena(Player p)
    {
       for(ArenaAPI arena : storage.getArenas())
       {
           if(arena.getPlayers().contains(p.getName()))
           {
               return true;
           }
       }
        return false;
    }
    public ArenaAPI getArena(String name)
    {
        for(ArenaAPI arena : storage.getArenas())
        {
            if(arena.getName().equalsIgnoreCase(name))
            {
                return arena;
            }
        }
        return null;
    }
    public ArenaAPI getPlayerArena(Player p)
    {
        for(ArenaAPI arena : storage.getArenas())
        {
            if(arena.getPlayers().contains(p.getName()))
            {
                return arena;
            }
        }
        return null;
    }
}
