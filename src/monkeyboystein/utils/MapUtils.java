package monkeyboystein.utils;

import monkeyboystein.Arena.ArenaAPI;
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
    public static List<Block> blocksFromTwoPoints(Location loc1, Location loc2) {
        List<Block> blocks = new ArrayList<Block>();

        int topBlockX = (loc1.getBlockX() < loc2.getBlockX() ? loc2.getBlockX() : loc1.getBlockX());
        int bottomBlockX = (loc1.getBlockX() > loc2.getBlockX() ? loc2.getBlockX() : loc1.getBlockX());

        int topBlockY = (loc1.getBlockY() < loc2.getBlockY() ? loc2.getBlockY() : loc1.getBlockY());
        int bottomBlockY = (loc1.getBlockY() > loc2.getBlockY() ? loc2.getBlockY() : loc1.getBlockY());

        int topBlockZ = (loc1.getBlockZ() < loc2.getBlockZ() ? loc2.getBlockZ() : loc1.getBlockZ());
        int bottomBlockZ = (loc1.getBlockZ() > loc2.getBlockZ() ? loc2.getBlockZ() : loc1.getBlockZ());

        for (int x = bottomBlockX; x <= topBlockX; x++) {
            for (int z = bottomBlockZ; z <= topBlockZ; z++) {
                for (int y = bottomBlockY; y <= topBlockY; y++) {
                    Block block = loc1.getWorld().getBlockAt(x, y, z);

                    blocks.add(block);
                }
            }
        }

        return blocks;
    }
    public static boolean isOre(Block block)
    {
        if(block.getType().toString().contains("ORE"))
        {
            return true;
        }
        return false;
    }
    public static void regenerateMap(ArenaAPI arenaAPI) {

        for(BlockState state : arenaAPI.getBrokenBlocks())
        {
            state.getLocation().getBlock().setType(Material.STONE);

        }
        arenaAPI.setBrokenBlocks(new ArrayList<BlockState>());

        List<Block> blocks = blocksFromTwoPoints(arenaAPI.getCorner1(), arenaAPI.getCurrent2());



        for (Block b : blocks)
        {
            Location loc = b.getLocation();
            if(isOre(b))
            {
                loc.getBlock().setType(Material.STONE);
            }
            if(loc.getBlock().getType()== Material.STONE)
            {
                int i = new Random().nextInt(20 - 1) + 1;
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
                else
                {
                    int chance = new Random().nextInt(21- 1) + 1;
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
