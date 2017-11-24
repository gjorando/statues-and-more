/**
 * Block class for the statue
 */

package net.minecraft.src.statues;

import net.minecraft.src.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import net.minecraft.client.Minecraft;

public class BlockShowcase extends BlockContainer
{
	private Class Showcase_EntityClass;
	
	public BlockShowcase(int i, Class tClass, Material material) 
	{
		super(i, material);
		Showcase_EntityClass = tClass;
	}
	
	/**
	 * return the TileEntity of a block
	 */
	public TileEntity getBlockEntity()
	{   
		try
    	{
    		return (TileEntity)Showcase_EntityClass.newInstance();
    	}

    	catch(Exception exception)
    	{
    		throw new RuntimeException(exception);
		}     
	}
   
	/**
	 * return the id of the associated block/item dropped
	 */
	public int idDropped(int i, Random random, int j)
	{
		return mod_statue.itemShowcase.shiftedIndex;
	}
	
	/**
	 * return the quantity of block(s)/item(s) dropped
	 */
	public int quantityDropped(Random random)
	{
		return 1;
	}
	
	/**
	 * The type of render function that is called for this block
	 */
	public int getRenderType()
	{
		return -1;
	}
	
	/**
	 * return false if the block isn't a full 1*1 cube
	 */
	public boolean isOpaqueCube()
	{
		return false;
	}
	
	/**
	 * return false if the block mustn't be rendered as a normal block
	 */
	public boolean renderAsNormalBlock()
	{
		return false;
	}
	
	/**
     * if the specified block is in the given AABB, add its collision bounding box to the given list
     */
    public void addCollidingBlockToList(World var1, int var2, int var3, int var4, AxisAlignedBB var5, List var6, Entity var7)
    {
        this.setBlockBounds(0F, 0F, 0F, 1F, 2F, 1F);
        super.addCollidingBlockToList(var1, var2, var3, var4, var5, var6, var7);
    }

	/**
     * Called when the block is placed in the world.
     */
	public void onBlockPlacedBy(World world, int i, int j, int k, EntityLiving entityliving)
	{
		int i1 = MathHelper.floor_double((double)((entityliving.rotationYaw * 4F) / 360F) + 0.5D) & 3;
		int j1 = world.getBlockMetadata(i, j, k) & 4;
		int k1 = 0;
		
		if (i1 == 0)
		{
			k1 = 0;
		}
		if (i1 == 1)
		{
			k1 = 1;
		}
		if (i1 == 2)
		{
			k1 = 2;
		}
		if (i1 == 3)
		{
			k1 = 3;
		}
		
		world.setBlockMetadataWithNotify(i, j, k, k1 | j1);
	}
	
	/**
     * Called upon block activation (right click on the block.)
     */
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer entityplayer, int par6, float par7, float par8, float par9)
	{
		TileEntityShowcase teshowcase = (TileEntityShowcase)world.getBlockTileEntity(x, y, z);
		
		if (teshowcase != null && entityplayer instanceof EntityPlayerMP)
        {
			System.out.println("non nul!");
            if (world.isRemote)
            {
                return true;
            }
            else
            {
            	teshowcase.numUsingPlayers++;
            	ContainerShowcase var11 = new ContainerShowcase(entityplayer.inventory, teshowcase);
	            ModLoader.serverOpenWindow((EntityPlayerMP) entityplayer, var11, mod_statue.showcaseContainerID, teshowcase.xCoord, teshowcase.yCoord, teshowcase.zCoord);
	            
                return true;
            }
        }
		else
		{
			return true;
		}
	}
	
	/**
     * Checks to see if its valid to put this block at the specified coordinates. Args: world, x, y, z
     */
	public boolean canPlaceBlockAt(World world, int i, int j, int k)
	{
		Minecraft mc = (ModLoader.getMinecraftInstance());
		int meta = MathHelper.floor_double((double)((mc.thePlayer.rotationYaw * 4F) / 360F) + 0.5D) & 3;
		if (world.getBlockId(i, j-1, k) != 0)
		{
			switch(meta)
			{
			case 0:
				if (world.getBlockId(i-1, j, k) == 0 && world.getBlockId(i, j, k) == 0 && world.getBlockId(i+1, j, k) == 0 && world.getBlockId(i-1, j+1, k) == 0 && world.getBlockId(i, j+1, k) == 0 && world.getBlockId(i+1, j+1, k) == 0)
				{
					return true;
				}
				else
				{
					return false;
				}
			case 1:
				if (world.getBlockId(i, j, k-1) == 0 && world.getBlockId(i, j, k) == 0 && world.getBlockId(i, j, k+1) == 0 && world.getBlockId(i, j+1, k-1) == 0 && world.getBlockId(i, j+1, k) == 0 && world.getBlockId(i, j+1, k+1) == 0)
				{
					return true;
				}
				else
				{
					return false;
				} 
			case 2:
				if (world.getBlockId(i-1, j, k) == 0 && world.getBlockId(i, j, k) == 0 && world.getBlockId(i+1, j, k) == 0 && world.getBlockId(i-1, j+1, k) == 0 && world.getBlockId(i, j+1, k) == 0 && world.getBlockId(i+1, j+1, k) == 0)
				{
					return true;
				}
				else
				{
					return false;
				}
			case 3:
				if (world.getBlockId(i, j, k-1) == 0 && world.getBlockId(i, j, k) == 0 && world.getBlockId(i, j, k+1) == 0 && world.getBlockId(i, j+1, k-1) == 0 && world.getBlockId(i, j+1, k) == 0 && world.getBlockId(i, j+1, k+1) == 0)
				{
					return true;
				}
				else
				{
					return false;
				}
			}
		}
		else
		{
			return false;
		}
		return false;
	}
		
	/**
     * ejects contained items into the world, and notifies neighbours of an update, as appropriate
     */
	public void breakBlock(World world, int i, int j, int k, int par4, int par5)
	{
		ModLoader.genericContainerRemoval(world, i, j, k);
		super.breakBlock(world, i, j, k, par4, par5);
	}
	
	/**
	 * Lets the block know when one of its neighbor changes. Doesn't know which neighbor changed (coordinates passed are
	 * their own) Args: x, y, z, neighbor blockID
	 */
	public void onNeighborBlockChange(World world, int i, int j, int k, int l)
	{
		if (!world.isBlockNormalCube(i, j - 1, k))
		{
			dropBlockAsItem(world, i, j, k, world.getBlockMetadata(i, j, k), 0);
			world.setBlockWithNotify(i, j, k, 0);
		}
	}

	/**
     * return a new TileEntityShowcase()
     */
	public TileEntity createNewTileEntity(World var1)
	{
		return new TileEntityShowcase();
	}
}
