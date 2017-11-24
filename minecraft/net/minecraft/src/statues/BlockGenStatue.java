/**
 * Block class for the giant statue generator
 */

package net.minecraft.src.statues;

import net.minecraft.src.*;

public class BlockGenStatue extends Block
{

	public BlockGenStatue(int par1, int par2)
	{
		super(par1, par2, Material.rock);
		this.setCreativeTab(CreativeTabs.tabDeco);
	}
	
	/**
	 * From the specified side and block metadata retrieves the blocks texture. Args: side, metadata
	 */
	public int getBlockTextureFromSideAndMetadata(int i, int j)
	{
		switch(j)
		{
		case 0:
			switch(i)
			{
			case 0:
				return mod_statue.genstatueupdown;
			case 1:
				return mod_statue.genstatueupdown;
			case 2:
				return mod_statue.genstatuerear;
			case 3:
				return mod_statue.genstatuefront;
			case 4:
				return mod_statue.genstatuelateral;
			case 5:
				return mod_statue.genstatuelateral;
			}
		case 1:
			switch(i)
			{
			case 0:
				return mod_statue.genstatueupdown;
			case 1:
				return mod_statue.genstatueupdown;
			case 2:
				return mod_statue.genstatuelateral;
			case 3:
				return mod_statue.genstatuelateral;
			case 4:
				return mod_statue.genstatuefront;
			case 5:
				return mod_statue.genstatuerear;
			}
		case 2:
			switch(i)
			{
			case 0:
				return mod_statue.genstatueupdown;
			case 1:
				return mod_statue.genstatueupdown;
			case 2:
				return mod_statue.genstatuefront;
			case 3:
				return mod_statue.genstatuerear;
			case 4:
				return mod_statue.genstatuelateral;
			case 5:
				return mod_statue.genstatuelateral;
			}
		case 3:
			switch(i)
			{
			case 0:
				return mod_statue.genstatueupdown;
			case 1:
				return mod_statue.genstatueupdown;
			case 2:
				return mod_statue.genstatuelateral;
			case 3:
				return mod_statue.genstatuelateral;
			case 4:
				return mod_statue.genstatuerear;
			case 5:
				return mod_statue.genstatuefront;
			}
		}
		return 1; //error code
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
		ModLoader.openGUI((EntityPlayer) entityliving, new GuiGiantStatue(world, i, j, k));
	}
	
	/**
	 * Called upon block activation (right click on the block.)
	 */
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer entityplayer, int par6, float par7, float par8, float par9)
	{
		ModLoader.openGUI(entityplayer, new GuiGiantStatue(world, x, y, z));
		return true;
	}
}
