package monkeyboystein.utils;

import monkeyboystein.Arena.ArenaAPI;
import monkeyboystein.Main.Main;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;

/**
* Created by Andrew on 12/19/2014.
*/
public class InteractManager {
    Storage storage = Main.storage;
    public void interact(PlayerInteractEvent event)
    {
        if(event.getClickedBlock()!=null)
        {
            if(event.getClickedBlock().getType()== Material.WALL_SIGN || event.getClickedBlock().getType()==Material.SIGN_POST)
            {
                Sign s = (Sign)event.getClickedBlock().getState();
                signInteract(s,event.getPlayer());
            }
        }

    }
    public void signInteract(Sign sign, Player player)
    {
        if(sign.getLine(0).contains("Miners"))
        {
            String arenaName = ChatColor.stripColor(sign.getLine(1));
            ArenaAPI arena = storage.getArenaManager().getArena(arenaName);
            if(arena!=null)
            {
                if(arena.getPlayers().size()==storage.getMaxPlayers())
                {
                    player.sendMessage(storage.header + "That game is full");
                }
                else
                {
                   storage.getManagement().joinGame(arena,player);
                }
            }
        }
    }

}
