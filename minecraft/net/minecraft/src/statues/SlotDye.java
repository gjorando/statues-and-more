/**
 * Slot class for the slot dye
 */

package net.minecraft.src.statues;

import net.minecraft.src.*;

public class SlotDye extends Slot
{
    public SlotDye(IInventory iinventory, int i, int j, int k)
    {
        super(iinventory, i, j, k);
    }
    
    public SlotDye(TileEntityStatue te, int i, int j, int k)
    {
        super(te, i, j, k);
    }

    /**
     * Check if the stack is a valid item for this slot. Always true beside for the armor slots.
     */
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

    /**
     * Returns the icon index on items.png that is used as background image of the slot.
     */
	public int getBackgroundIconIndex()
	{
	    return mod_statue.slotDye;
	}
}