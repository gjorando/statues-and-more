package net.minecraft.src;

public class SlotStandardBlock extends Slot
{
    public SlotStandardBlock(IInventory iinventory, int i, int j, int k)
    {
        super(iinventory, i, j, k);
    }
    
    public SlotStandardBlock(TileEntity_statue te, int i, int j, int k)
    {
        super(te, i, j, k);
    }

    public boolean isItemValid(ItemStack stack)
    {
        if (stack.itemID < 256)
        {
        	if (stack.itemID == Item.cake.shiftedIndex)
        	{
        		return true;
        	}
        	else if(Block.blocksList[stack.itemID].getRenderType() == 0)
        	{
        		return true;
        	}
        	else
        	{
        		return false;
        	}
        }
        else if(stack.itemID == Item.cake.shiftedIndex)
        {
            return true;
        }
        else
        {
        	return false;
        	
        }
    }

	public int getBackgroundIconIndex()
	{
	    return mod_statue.slotStandardBlock;
	}
}