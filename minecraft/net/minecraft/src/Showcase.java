package net.minecraft.src;

import java.util.ArrayList;
import java.util.Random;

import net.minecraft.client.Minecraft;

public class Showcase extends BlockContainer
{
	private Class Showcase_EntityClass;
	   
   protected Showcase(int i, Class tClass, Material material) 
   {
	   super(i, material);
	   Showcase_EntityClass = tClass;
	   //setBlockBounds(0F, 0F, 0F, 0F, 0F, 0F);
   }
   
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
   
   public int idDropped(int i, Random random, int j)
   {
      return mod_statue.itemShowcase.shiftedIndex;
   }
      
   public int quantityDropped(Random random)
   {
      return 1;
   }

   public int getRenderType()
   {
      return -1;
   }
   
   public boolean isOpaqueCube()
   {
      return false;
   }
   
   public boolean renderAsNormalBlock()
   {
      return false;
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
   }
   
   public boolean blockActivated(World world, int x, int y, int z, EntityPlayer entityplayer)
   {
	TileEntity_showcase teshowcase = (TileEntity_showcase)world.getBlockTileEntity(x, y, z);
	teshowcase.numUsingPlayers++;
    ModLoader.openGUI(entityplayer, new GuiShowcase(entityplayer.inventory, teshowcase, world, x, y, z));
    return true;
   }

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
   
   public void onBlockRemoval(World world, int i, int j, int k)
   {
       ModLoader.genericContainerRemoval(world, i, j, k);
       super.onBlockRemoval(world, i, j, k);
   }
   
   public void onNeighborBlockChange(World world, int i, int j, int k, int l)
   {
       if (!world.isBlockNormalCube(i, j - 1, k))
       {
           dropBlockAsItem(world, i, j, k, world.getBlockMetadata(i, j, k), 0);
           world.setBlockWithNotify(i, j, k, 0);
       }
   }
}
