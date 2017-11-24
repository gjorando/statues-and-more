package net.minecraft.src;

public class BlockGenStatue extends Block
{

	protected BlockGenStatue(int par1, int par2)
	{
		super(par1, par2, Material.rock);
	}
	
    public int getBlockTextureFromSideAndMetadata(int par1, int par2)
    {
        if (par1 == 1)
        {
            return 0;
        }

        return par1 != 0 ? 3 : 2;
    }
	
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

}
