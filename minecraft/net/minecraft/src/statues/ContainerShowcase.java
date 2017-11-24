/**
 * Container class for the statue
 */

package net.minecraft.src.statues;

import net.minecraft.src.*;

public class ContainerShowcase extends Container
{
    private IInventory inv;
    private TileEntityShowcase tile;

    public ContainerShowcase(InventoryPlayer inventory, TileEntityShowcase teshowcase)
    {
    	inv = inventory;
        tile = teshowcase;
        addSlotToContainer(new SlotHand(teshowcase, 0, 120, 59));
        
        for(int j = 0; j < 3; j++)
		{
			for(int i1 = 0; i1 < 9; i1++)
			{
				addSlotToContainer(new Slot(inv, i1 + j * 9 + 9, 48 + i1 * 18, 84 + 18 + 18 + 24 + j * 18));
			}
		}
  
        for(int k = 0; k < 9; k++)
		{
        	addSlotToContainer(new Slot(inv, k, 48 + k * 18, 142 + 18 + 18 + 24));
		}
    }

    /**
     * Return true if the Player can interact with the container, else return false
     */
    public boolean canInteractWith(EntityPlayer entityplayer)
    {
        return true;
    }

    /**
     * Called to transfer a stack from one inventory to the other eg. when shift clicking.
     */
    public ItemStack transferStackInSlot(int par1)
    {
    	return null;
    }
}
