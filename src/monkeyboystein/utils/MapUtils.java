package monkeyboystein.utils;

import monkeyboystein.Arena.ArenaAPI;
import monkeyboystein.Main.Main;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.BlockState;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Andrew on 12/19/2014.
 */
public class MapUtils {

    public boolean isOre(Block block)
    {
        if(block.getType().toString().contains("ORE"))
        {
            return true;
        }
        return false;
    }
    public void regenerateMap(ArenaAPI arenaAPI, Location c1, Location c2) {

        for(BlockState state : arenaAPI.getBrokenBlocks())
        {
            state.getLocation().getBlock().setType(Material.STONE);

        }
        arenaAPI.setBrokenBlocks(new ArrayList<BlockState>());

        List<Block> blocks = Main.storage.getMain().blocksFromTwoPoints(c1, c2);



        for (Block b : blocks)
        {
            Location loc = b.getLocation();
            if(isOre(b) || b.getType()==Material.GLOWSTONE)
            {
                loc.getBlock().setType(Material.STONE);
            }
            if(loc.getBlock().getType()== Material.STONE)
            {
                int i = new Random().nextInt(21 - 1) + 1;
                if(i==1)
                {
                    loc.getBlock().setType(Material.COAL_ORE);
                }
                else if(i==2)
                {
                    loc.getBlock().setType(Material.IRON_ORE);
                }
                else if(i==3)
                {
                    loc.getBlock().setType(Material.GOLD_ORE);
                }
                else if(i==4)
                {
                    loc.getBlock().setType(Material.DIAMOND_ORE);
                }
                else if(i==5)
                {
                    loc.getBlock().setType(Material.LAPIS_ORE);
                }
                else if(i==6)
                {
                    loc.getBlock().setType(Material.REDSTONE_ORE);
                }
                else if(i==7)
                {
                    loc.getBlock().setType(Material.EMERALD_ORE);
                }
                else if(i==8)
                {
                    loc.getBlock().setType(Material.GLOWSTONE);
                }
                else
                {
                    int chance = new Random().nextInt(501- 1) + 1;
                    if(chance==4)
                    {
                        Block block = loc.getBlock();
                        if(isOre(block.getRelative(BlockFace.DOWN)))
                        {
                            block.setType(block.getRelative(BlockFace.DOWN).getType());
                        }

                        if(isOre(block.getRelative(BlockFace.UP)))
                        {
                            block.setType(block.getRelative(BlockFace.UP).getType());
                        }

                        if(isOre(block.getRelative(BlockFace.NORTH)))
                        {
                            block.setType(block.getRelative(BlockFace.NORTH).getType());
                        }

                        if(isOre(block.getRelative(BlockFace.SOUTH)))
                        {
                            block.setType(block.getRelative(BlockFace.SOUTH).getType());
                        }

                        if(isOre(block.getRelative(BlockFace.EAST)))
                        {
                            block.setType(block.getRelative(BlockFace.EAST).getType());
                        }

                        if(isOre(block.getRelative(BlockFace.WEST)))
                        {
                            block.setType(block.getRelative(BlockFace.WEST).getType());
                        }
                    }
                }

            }
        }
    }
}
