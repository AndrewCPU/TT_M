package monkeyboystein.utils;

import monkeyboystein.Arena.ArenaAPI;
import monkeyboystein.Main.Main;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;

/**
 * Created by Andrew on 12/19/2014.
 */
public class Lobby {
    Location spawn;

    public Location getSpawn() {
        return spawn;
    }

    public void setSpawn(Location spawn) {
        this.spawn = spawn;
    }

    public Lobby(Location spawn) {

        this.spawn = spawn;
    }
    public static Location getLobbyLocation(ArenaAPI arenaAPI)
    {
        Storage storage = Main.storage;
        FileConfiguration config = storage.getMain().getConfig();
        String path = arenaAPI.getName() + ".Lobby.";
        int x = config.getInt(path + ".X");
        int y = config.getInt(path + ".Y");
        int z = config.getInt(path + ".Z");
        return  new Location(Bukkit.getWorld("world"),x,y,z);
    }
}
