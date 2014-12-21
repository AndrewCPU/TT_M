package monkeyboystein.utils;

import monkeyboystein.Arena.ArenaAPI;
import monkeyboystein.Main.Main;
import org.bukkit.ChatColor;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;

import java.util.Random;

/**
 * Created by Andrew on 12/20/2014.
 */
public class SignManager {
    Storage storage = Main.storage;
    public void updateSign(ArenaAPI arenaAPI)
    {
        Block b = arenaAPI.getSignLoc().getBlock();
        if(b.getType().toString().contains("SIGN"))
        {
            Sign s = (Sign)b.getState();
            s.setLine(0, ChatColor.translateAlternateColorCodes('&', new Random().nextInt(10) + "[Snow]"));
            s.setLine(1, arenaAPI.getName());
            s.setLine(2, arenaAPI.getPlayers().size() + "/" + storage.getMaxPlayers());
            s.setLine(3, ChatColor.RED  +arenaAPI.getState().toString());
            s.update();
        }

    }
}
