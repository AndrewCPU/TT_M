package monkeyboystein.utils;

import monkeyboystein.Arena.ArenaAPI;
import monkeyboystein.Arena.ArenaState;
import monkeyboystein.Main.Main;

/**
 * Created by Andrew on 12/20/2014.
 */
public class TickManager {
    Storage storage = Main.storage;
    public void tickAll()
    {
        for(ArenaAPI arenaAPI : storage.getMain().arenas)
        {
            arenaAPI.tick();
            storage.getSignManager().updateSign(arenaAPI);
        }
    }
    public void tick(ArenaState state)
    {
        for(ArenaAPI arenaAPI : storage.getMain().arenas)
        {
            if(arenaAPI.getState()==state)
            {
                arenaAPI.tick();
            }
        }
    }
}
