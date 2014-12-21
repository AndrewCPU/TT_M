package monkeyboystein.Party;

import monkeyboystein.utils.Storage;
import monkeyboystein.Main.Main;

/**
 * Created by Andrew on 11/30/2014.
 */
public class PartyManager {
    static Storage variables = Main.storage;
    public static boolean isInParty(String player)
    {
        for(Party p : variables.getMain().parties)
        {
            if(p.getMembers().contains(player) || p.getOwner().equalsIgnoreCase(player))
            {
                return true;
            }

        }
        return false;
    }
    public static Party getParty(String player)
    {
        for(Party p : variables.getMain().parties)
        {
            if(p.getMembers().contains(player) || p.getOwner().equalsIgnoreCase(player))
            {
                return p;
            }
        }
        return null;
    }
}
