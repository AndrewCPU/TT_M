package monkeyboystein.utils;

import monkeyboystein.Arena.ArenaAPI;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Andrew on 12/19/2014.
 */
public class MapDecay {
    HashMap<ArenaAPI, List<BlockState>> states = new HashMap<ArenaAPI, List<BlockState>>();

    public void decay(ArenaAPI arenaAPI) {
        List<Block> blocks = MapUtils.blocksFromTwoPoints(arenaAPI.getCurrent1(), arenaAPI.getCurrent2());
        for (Block b : blocks) {
            List<BlockState> states = this.states.get(arenaAPI);
            states.add(b.getState());
            this.states.remove(arenaAPI);
            this.states.put(arenaAPI,states);
        }
        for (Block b : blocks) {
            b.getLocation().getWorld().spawnFallingBlock(b.getLocation(), b.getType(), b.getData());
            b.setType(Material.AIR);
        }
        arenaAPI.setCurrent1(arenaAPI.getCurrent1().add(0,1,0));
        arenaAPI.setCurrent2(arenaAPI.getCurrent2().add(0,1,0));
    }

    public void undo(ArenaAPI arenaAPI)
    {
        for(BlockState state : states.get(arenaAPI))
        {

            state.getLocation().getBlock().setType(state.getType());
            state.getLocation().getBlock().setData(state.getBlock().getData());


        }

    }


}
