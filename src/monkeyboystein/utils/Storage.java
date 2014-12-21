package monkeyboystein.utils;

import monkeyboystein.Arena.ArenaAPI;
import monkeyboystein.Arena.ArenaManager;
import monkeyboystein.Main.Listener.PluginListener;
import monkeyboystein.Main.Main;
import org.bukkit.ChatColor;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Andrew on 12/19/2014.
 */
public class Storage {
    String header = ChatColor.GRAY + "[" + ChatColor.AQUA + "Diamond Miners" + ChatColor.GRAY + "] " + ChatColor.YELLOW;
    PluginListener listener;
    Main main;
    MapDecay decay;
    SQLAPI sql;
    UserManagement management;
    List<ArenaAPI> arenas = new ArrayList<ArenaAPI>();
    ArenaManager arenaManager;
    int maxPlayers = 5;
    SignManager signManager;

    public SignManager getSignManager() {
        return signManager;
    }

    public void setSignManager(SignManager signManager) {
        this.signManager = signManager;
    }

    public int getMaxPlayers() {
        return maxPlayers;
    }

    public void setMaxPlayers(int maxPlayers) {
        this.maxPlayers = maxPlayers;
    }

    public void setArenas(List<ArenaAPI> arenas) {
        this.arenas = arenas;
    }

    public ArenaManager getArenaManager() {
        return arenaManager;
    }

    public void setArenaManager(ArenaManager arenaManager) {
        this.arenaManager = arenaManager;
    }

    public void addArena(ArenaAPI arenaAPI)
    {
        if(arenas.contains(arenaAPI))
        {

        }
        else
        {
            arenas.add(arenaAPI);
        }
    }
    public void removeArena(ArenaAPI api)
    {
        if(arenas.contains(api))
        {
            arenas.remove(api);
        }
    }
    public List<ArenaAPI>  getArenas()
    {
        return arenas;
    }
    public InteractManager getInteractManager() {
        return interactManager;
    }

    public void setInteractManager(InteractManager interactManager) {
        this.interactManager = interactManager;
    }

    InteractManager interactManager;

    public PluginListener getListener() {
        return listener;
    }

    public void setListener(PluginListener listener) {
        this.listener = listener;
    }

    public Main getMain() {
        return main;
    }

    public void setMain(Main main) {
        this.main = main;
    }

    public MapDecay getDecay() {
        return decay;
    }

    public void setDecay(MapDecay decay) {
        this.decay = decay;
    }

    public SQLAPI getSql() {
        return sql;
    }

    public void setSql(SQLAPI sql) {
        this.sql = sql;
    }

    public UserManagement getManagement() {
        return management;
    }

    public void setManagement(UserManagement management) {
        this.management = management;
    }
}
