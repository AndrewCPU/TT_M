package monkeyboystein.Main.Listener;

import monkeyboystein.Arena.ArenaAPI;
import monkeyboystein.Main.Main;
import monkeyboystein.utils.ArenaScore;
import monkeyboystein.utils.Storage;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
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
    public void join(final PlayerJoinEvent event)
    {
        storage.getManagement().eventTrigger(event);
        Bukkit.getScheduler().scheduleSyncDelayedTask(storage.getMain(), new Runnable() {
            @Override
            public void run() {
                event.getPlayer().teleport(event.getPlayer().getWorld().getSpawnLocation());
            }
        }, 10);
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
                    if(event.getTo().getBlockY()>=arenaAPI.getTopY() && score.getScore()>=350 && arenaAPI.getTime()<=150)
                    {
                        arenaAPI.endGame();
                    }
                }
            }
        }

        for(ArenaAPI arenaAPI : storage.getArenas())
        {
            int x = storage.getMain().getConfig().getInt(arenaAPI.getName() + ".Portal.X");
            int y = storage.getMain().getConfig().getInt(arenaAPI.getName() + ".Portal.Y");
            int z = storage.getMain().getConfig().getInt(arenaAPI.getName() + ".Portal.Z");
            Location loc = new Location(Bukkit.getWorld("world"),x,y,z);
            if(event.getPlayer().getLocation().getBlockX()==loc.getBlockX() && event.getPlayer().getLocation().getBlockY()==loc.getBlockY() && event.getPlayer().getLocation().getBlockZ()==loc.getBlockZ())
            {

                if(arenaAPI!=null)
                {
                    if(arenaAPI.getPlayers().size()==storage.getMaxPlayers())
                    {
                        event.getPlayer().sendMessage(storage.getHeader() + "That game is full");
                    }
                    else
                    {
                        storage.getManagement().joinGame(arenaAPI,event.getPlayer());
                    }
                }
            }
        }

    }
    @EventHandler
    public void onBlowUP(EntityExplodeEvent event)
    {
        event.setCancelled(true);
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
            ArenaAPI api = storage.getArenaManager().getPlayerArena(event.getPlayer());
            api.removePlayer(event.getPlayer().getName());
            ArenaScore score = null;
            for(ArenaScore s : api.getScores())
            {
                if(s.getPlayerName().equals(event.getPlayer().getName()))
                {
                    score = s;

                }
            }
            api.removeScore(score);

        }
    }
}
