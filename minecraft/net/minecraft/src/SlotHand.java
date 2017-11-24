package net.minecraft.src;

public class SlotHand extends Slot
{
    public SlotHand(IInventory iinventory, int i, int j, int k)
    {
        super(iinventory, i, j, k);
    }
    
    public SlotHand(TileEntity_statue te, int i, int j, int k)
    {
        super(te, i, j, k);
    }

	public int getBackgroundIconIndex()
	{
	    return mod_statue.slotHand;
	}
}