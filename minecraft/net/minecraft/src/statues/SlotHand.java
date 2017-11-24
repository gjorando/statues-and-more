/**
 * Slot class for the slot hand
 */

package net.minecraft.src.statues;

import net.minecraft.src.*;

public class SlotHand extends Slot
{
    public SlotHand(IInventory iinventory, int i, int j, int k)
    {
        super(iinventory, i, j, k);
    }
    
    public SlotHand(TileEntityStatue te, int i, int j, int k)
    {
        super(te, i, j, k);
    }

    /**
     * Returns the icon index on items.png that is used as background image of the slot.
     */
	public int getBackgroundIconIndex()
	{
	    return mod_statue.slotHand;
	}
}