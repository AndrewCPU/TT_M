package monkeyboystein.Main;

import monkeyboystein.Arena.ArenaManager;
import monkeyboystein.Main.Listener.PluginListener;
import monkeyboystein.utils.*;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Created by Andrew on 12/19/2014.
 */
public class Main extends JavaPlugin {
    PluginListener listener = new PluginListener();
    public void onEnable()
    {
        getServer().getPluginManager().registerEvents(listener, this);
        setup();
    }
    MapDecay decay = new MapDecay();
    SQLAPI sqlapi = new SQLAPI();
    public static Storage storage = new Storage();
    UserManagement management = new UserManagement();
    InteractManager interactManager = new InteractManager();
    ArenaManager arenaManager = new ArenaManager();
    SignManager signManager = new SignManager();
    public void setup()
    {
        storage.setDecay(decay);
        storage.setListener(listener);
        storage.setSql(sqlapi);
        storage.setManagement(management);
        storage.setInteractManager(interactManager);
        storage.setArenaManager(arenaManager);
        storage.setSignManager(signManager);
    }
}
