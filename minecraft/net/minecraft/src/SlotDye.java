package net.minecraft.src;

public class SlotDye extends Slot
{
    public SlotDye(IInventory iinventory, int i, int j, int k)
    {
        super(iinventory, i, j, k);
    }
    
    public SlotDye(TileEntity_statue te, int i, int j, int k)
    {
        super(te, i, j, k);
    }

    public boolean isItemValid(ItemStack stack)
    {
        if (stack.itemID == 351)
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
	    return mod_statue.slotDye;
	}
}