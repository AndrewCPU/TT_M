package monkeyboystein.utils;

import monkeyboystein.Arena.ArenaAPI;
import monkeyboystein.Main.Main;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Andrew on 12/19/2014.
 */
public class MapDecay {
    HashMap<ArenaAPI, List<BlockState>> states = new HashMap<ArenaAPI, List<BlockState>>();

    public void decay(ArenaAPI arenaAPI) {
        List<Block> blocks = Main.storage.getMain().blocksFromTwoPoints(arenaAPI.getCurrent1(), arenaAPI.getCurrent2());
      //  System.out.print(blocks);
        for (Block b : blocks) {
            List<BlockState> states = this.states.get(arenaAPI);
            if(states==null)
            {
                this.states.put(arenaAPI, new ArrayList<BlockState>());
                states = this.states.get(arenaAPI);
            }
            if(b.getState()==null)
            {

            }
            else
            {
                System.out.print(b.getState());
                states.add(b.getState());
                this.states.remove(arenaAPI);
                this.states.put(arenaAPI,states);
            }

        }
        for (Block b : blocks) {
         //   b.getLocation().getWorld().spawnFallingBlock(b.getLocation(), b.getType(), b.getData());
            b.setType(Material.AIR);
        }
        arenaAPI.setCurrent1(arenaAPI.getCurrent1().add(0,1,0));
        arenaAPI.setCurrent2(arenaAPI.getCurrent2().add(0,1,0));
    }

    public void undo(ArenaAPI arenaAPI)
    {
        if(states.containsKey(arenaAPI))
        {
            for(BlockState state : states.get(arenaAPI))
            {
                System.out.print(state);
                state.update(true);
            }
            states.remove(arenaAPI);
        }



    }


}
