package monkeyboystein.Main.Listener;

import monkeyboystein.Arena.ArenaAPI;
import monkeyboystein.Main.Main;
import monkeyboystein.utils.ArenaScore;
import monkeyboystein.utils.Storage;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.*;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

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
    public void onMove(PlayerMoveEvent event)
    {
        if(storage.getArenaManager().isInArena(event.getPlayer()))
        {
            ArenaAPI arenaAPI = storage.getArenaManager().getPlayerArena(event.getPlayer());
            if(arenaAPI!=null)
            {
                if(arenaAPI.getDead().contains(event.getPlayer().getName()))
                {
                    if(event.getTo().distance(arenaAPI.getCenter())>=256)
                    {
                        event.setCancelled(true);
                    }

                }
                else
                {
                    ArenaScore score = null;
                    for(ArenaScore s : arenaAPI.getScores())
                    {
                        if(s.getPlayerName().equalsIgnoreCase(event.getPlayer().getName()))
                        {
                            score = s;
                        }
                    }
                    if(event.getTo().getBlockY()>=arenaAPI.getTopY() && score.getScore()>=100 && arenaAPI.getTime()<=350)
                    {
                        arenaAPI.endGame();
                    }
                }
            }
        }
    }
    @EventHandler
    public void onDeath(PlayerDeathEvent event)
    {
        //teleport back to spawn point in map clear points
        if(storage.getArenaManager().isInArena(event.getEntity()))
        {
            ArenaAPI arenaAPI = storage.getArenaManager().getPlayerArena(event.getEntity());
            if(arenaAPI!=null)
            {
                for(String s : arenaAPI.getPlayers())
                {
                    Bukkit.getPlayer(s).sendMessage(storage.getHeader() + event.getEntity().getName() + " has been killed!");
                }
            }
        }
    }
    @EventHandler
    public void onRespawn(PlayerRespawnEvent event)
    {
        if(storage.getArenaManager().isInArena(event.getPlayer())) {
            ArenaAPI arenaAPI = storage.getArenaManager().getPlayerArena(event.getPlayer());
            if (arenaAPI != null) {
                event.setRespawnLocation(arenaAPI.getSpawns().getPlayerSpawn(1));
            }
            arenaAPI.addDead(event.getPlayer().getName());
            event.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 10000, 10, true));
        }
    }
}
