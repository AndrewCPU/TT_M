package monkeyboystein.utils;

import monkeyboystein.Arena.ArenaAPI;
import monkeyboystein.Main.Main;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.Configuration;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.HashMap;

/**
 * Created by Andrew on 12/19/2014.
 */
public class ArenaPlayerSpawns {
    HashMap<Integer,Location> spawns = new HashMap<Integer, Location>();
    public Location getPlayerSpawn(int num)
    {
        return spawns.get(num);
    }
    public void setPlayerSpawn(int num, Location loc)
    {
        if(spawns.containsKey(num))
        {
            spawns.remove(num);
        }
        spawns.put(num,loc);
    }
    public static Location getSpawnLocation(int i, ArenaAPI arena)
    {
        Storage storage = Main.storage;
        String path = arena.getName() + ".PlayerSpawn" + i ;
        FileConfiguration config = storage.getMain().getConfig();
        int x = config.getInt(path + ".X");
        int y = config.getInt(path + ".Y");
        int z = config.getInt(path + ".Z");
        return  new Location(Bukkit.getWorld("world"),x,y,z);
    }
}
