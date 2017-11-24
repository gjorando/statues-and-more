/**
 * Block class for the statue
 */

package net.minecraft.src.statues;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import net.minecraft.src.*;

public class BlockStatue extends BlockContainer
{
	private Class Statue_EntityClass;
	
	public BlockStatue(int i, Class tClass, Material material) 
	{
		super(i, material);
		Statue_EntityClass = tClass;
		setBlockBounds(0F, 0F, 0F, 1F, 0.5F, 1F);
	}
	
	/**
	 * return the TileEntity of a block
	 */
	public TileEntity getBlockEntity()
	{   
		try
		{
			return (TileEntity)Statue_EntityClass.newInstance();
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
		return mod_statue.itemStatue.shiftedIndex;
	}
	
	/**
	 * return the quantity of block(s)/item(s) dropped
	 */
	public int quantityDropped(Random random)
	{
		return 1;
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
     * Retrieves the block texture to use based on the display side. Args: iBlockAccess, x, y, z, side
     */
	public int getBlockTexture(IBlockAccess iblockaccess, int i, int j, int k, int w)
	{
		IInventory iinventory = (IInventory)iblockaccess.getBlockTileEntity(i, j, k);
		TileEntityStatue tile = (TileEntityStatue)iblockaccess.getBlockTileEntity(i, j, k);
		
		if (iinventory != null && iinventory.getStackInSlot(4) != null)
		{
			ItemStack itemstack = iinventory.getStackInSlot(4);
			
			if (itemstack.itemID < 256 && Block.blocksList[itemstack.itemID].getRenderType() == 0)
			{
				setBlockBounds(0F, 0F, 0F, 1F, 0.5F, 1F);
				Block block = Block.blocksList[itemstack.itemID];
				return block.getBlockTextureFromSideAndMetadata(w, itemstack.getItemDamage());
			}
			else if(itemstack.itemID == Item.cake.shiftedIndex)
			{
				setBlockBounds(0.0625F, 0F, 0.0625F, 0.9375F, 0.5F, 0.9375F);
				return Block.cake.getBlockTextureFromSide(w);
			}
		}
		else if(tile.getStackInSlot(4) == null && tile.getButtonValue() != 0)
		{
			return mod_statue.textNull;
		}
		return Block.stoneSingleSlab.getBlockTextureFromSide(w);
	}
	
	/**
     * if the specified block is in the given AABB, add its collision bounding box to the given list
     */
    public void addCollidingBlockToList(World var1, int var2, int var3, int var4, AxisAlignedBB var5, List var6, Entity var7)
    {
        this.setBlockBounds(0F, 0F, 0F, 1F, 2F, 1F);
        super.addCollidingBlockToList(var1, var2, var3, var4, var5, var6, var7);
        this.setBlockBounds(0F, 0F, 0F, 1F, 0.5F, 1F);
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
		TileEntityStatue testatue = (TileEntityStatue)world.getBlockTileEntity(x, y, z);
		int slot = 0;
		ItemStack itemstack = entityplayer.inventory.getCurrentItem();
		boolean hammerHeld = isAHammer(itemstack);
		
		if (testatue != null && entityplayer instanceof EntityPlayerMP)
        {
            if (world.isRemote)
            {
                return true;
            }
            else
            {
            	ContainerStatue var11 = new ContainerStatue(entityplayer.inventory, testatue);
	            ModLoader.serverOpenWindow((EntityPlayerMP) entityplayer, var11, mod_statue.statueContainerID, testatue.xCoord, testatue.yCoord, testatue.zCoord);
	            
                return true;
            }
        }
        else if (hammerHeld && (entityplayer.inventory.hasItem(mod_statue.burin.shiftedIndex) || entityplayer.capabilities.isCreativeMode))
        {
        	if(entityplayer.inventory.hasItem(mod_statue.burin.shiftedIndex) == true)
			{
				for (int i = 0; i < 36; i++)
				{
					if (entityplayer.inventory.getStackInSlot(i) != null && entityplayer.inventory.getStackInSlot(i).itemID == mod_statue.burin.shiftedIndex)
					{
						slot = i;
					}
				}
				
				ItemStack burin = entityplayer.inventory.getStackInSlot(slot);
				burin.damageItem(1, entityplayer);
			}
        	
			ModLoader.openGUI(entityplayer, new GuiSculpt(testatue, world, x, y, z));
			
            return true;
        }
        else
        {
        	return true;
        }
	}
	
	/**
	 * return true if the itemstack is a hammer, and false if the itemstack is empty or isn't a hammer
	 */
	private boolean isAHammer(ItemStack itemstack)
	{
		if (itemstack != null)
		{
			if (itemstack.itemID == mod_statue.marteau.shiftedIndex)
			{
				return true;
			}
			else
			{
				return false;
			}
		}
		else
		{
			return false;
		}
	}
	
	/**
     * Checks to see if its valid to put this block at the specified coordinates. Args: world, x, y, z
     */
	public boolean canPlaceBlockAt(World world, int i, int j, int k)
	{
		if (world.getBlockId(i, j+1, k) == 0 && world.getBlockId(i, j-1, k) != 0)
		{
			return true;
		}
		else
		{
			return false;
		}
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
     * return a new TileEntityStatue()
     */
	public TileEntity createNewTileEntity(World var1)
	{
		return new TileEntityStatue();
	}
}
