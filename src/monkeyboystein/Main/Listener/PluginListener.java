package monkeyboystein.Main.Listener;

import monkeyboystein.Main.Main;
import monkeyboystein.utils.Storage;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

/**
 * Created by Andrew on 12/19/2014.
 */
public class PluginListener implements Listener{
    Storage storage = Main.storage;
    @EventHandler
    public void quit(PlayerQuitEvent event)
    {
        storage.getManagement().eventTrigger(event);
    }
    @EventHandler
    public void join(PlayerJoinEvent event)
    {
        storage.getManagement().eventTrigger(event);
    }
    @EventHandler
    public void interact(PlayerInteractEvent event)
    {
        storage.getInteractManager().interact(event);
    }
    @EventHandler
    public void breakBlock(BlockBreakEvent event)
    {
        storage.getManagement().breakBlock(event);
    }
    @EventHandler
    public void place(BlockPlaceEvent event)
    {
        if(storage.getArenaManager().isInArena(event.getPlayer()))
        {
            event.setBuild(false);
        }
        else
        {
            if(!(event.getPlayer().isOp()))
            {
                event.setBuild(false);
            }
        }
    }
    @EventHandler
    public void onDeath(PlayerDeathEvent event)
    {
        //teleport back to spawn point in map clear points

    }
}
