/**
 * Slot class for the slot standard block
 */

package net.minecraft.src.statues;

import net.minecraft.src.*;

public class SlotStandardBlock extends Slot
{
    public SlotStandardBlock(IInventory iinventory, int i, int j, int k)
    {
        super(iinventory, i, j, k);
    }
    
    public SlotStandardBlock(TileEntityStatue te, int i, int j, int k)
    {
        super(te, i, j, k);
    }

    /**
     * Check if the stack is a valid item for this slot. Always true beside for the armor slots.
     */
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

    /**
     * Returns the icon index on items.png that is used as background image of the slot.
     */
	public int getBackgroundIconIndex()
	{
	    return mod_statue.slotStandardBlock;
	}
}