package monkeyboystein.Party;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Andrew on 11/30/2014.
 */
public class Party {
    private List<String> invites = new ArrayList<String>();
    private String owner;
    private List<String> members = new ArrayList<String>();
    public boolean isInvited(String pl)
    {
        if(invites.contains(pl))
        {
            return true;
        }
        else
        {
            return false;
        }
    }
    public List<String> getMembers() {
        return members;
    }

    public void setMembers(List<String> members) {
        this.members = members;
    }
    public void addMember(String s)
    {
        if(invites.contains(s))
        {
            invites.remove(s);
        }
        Bukkit.getPlayer(owner).sendMessage(ChatColor.GREEN + s + " has joined your party.");
        members.add(s);
    }
    public void removeMember(String s)
    {
        if(members.contains(s))
        {
            members.remove(s);
        }
    }

    public List<String> getInvites() {
        return invites;
    }

    public void setInvites(List<String> invites) {
        this.invites = invites;
    }
    public void addInvite(String s)
    {
        if(!invites.contains(s)) {
            invites.add(s);
        }
    }
    public void removeInvite(String s)
    {
        if(invites.contains(s))
        {
            invites.remove(s);
        }
    }


    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public Party(String owner, String invite)
    {
        setOwner(owner);
        addInvite(invite);
    }
}
