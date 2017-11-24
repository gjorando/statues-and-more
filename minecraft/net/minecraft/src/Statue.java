package net.minecraft.src;

import java.util.ArrayList;
import java.util.Random;

public class Statue extends BlockContainer
{
	private Class Statue_EntityClass;
	   
   protected Statue(int i, Class tClass)
   
   {
	   super(i, Material.wood);
	   Statue_EntityClass = tClass;
   }
   
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
   
   public int idDropped(int i, Random random, int j)
   {
      return mod_statue.itemStatue.shiftedIndex;
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
	   int slot = 0;
	   ItemStack itemstack = entityplayer.inventory.getCurrentItem();
       TileEntity_statue testatue = (TileEntity_statue)world.getBlockTileEntity(x, y, z);
       boolean hammerHeld = isAHammer(itemstack);
       if (hammerHeld && (entityplayer.inventory.hasItem(mod_statue.burin.shiftedIndex) || entityplayer.capabilities.isCreativeMode))
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
    	   ModLoader.openGUI(entityplayer, new GuiSculpt(testatue, world, x, y, z));
       }
       else if (testatue != null  && testatue.getButtonValue() != 0)
       {
           ModLoader.openGUI(entityplayer, new GuiStatue(entityplayer.inventory, testatue));
       }

       return true;
   }
   
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

public boolean canPlaceBlockAt(World world, int i, int j, int k)
   {
	   if (world.getBlockId(i, j+1, k) == 0 && world.getBlockId(i, j+2, k) == 0)
	   {
		   return true;
	   }
	   else
	   {
		   return false;
	   }
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
