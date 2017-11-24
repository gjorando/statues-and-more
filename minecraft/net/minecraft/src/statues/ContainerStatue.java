/**
 * Container class for the statue
 */

package net.minecraft.src.statues;

import net.minecraft.src.*;

public class ContainerStatue extends Container
{
    private IInventory inv;
    private TileEntityStatue tile;

    public ContainerStatue(InventoryPlayer inventory, TileEntityStatue testatue)
    {
        inv = inventory;
        tile = testatue;
        
        addSlotToContainer(new SlotArmorStatue(testatue, 0, 80, 8, 0));
        addSlotToContainer(new SlotArmorStatue(testatue, 1, 80, 26, 1));
        addSlotToContainer(new SlotArmorStatue(testatue, 2, 80, 44, 2));
        addSlotToContainer(new SlotArmorStatue(testatue, 3, 80, 62, 3));
        addSlotToContainer(new SlotStandardBlock(testatue, 4, 80, 80));
        addSlotToContainer(new SlotDye(testatue, 5, 111, 105));
        addSlotToContainer(new SlotHand(testatue, 6, 111, 26));
        
        for(int j = 0; j < 3; j++)
		{
			for(int i1 = 0; i1 < 9; i1++)
			{
				addSlotToContainer(new Slot(inv, i1 + j * 9 + 9, 8 + i1 * 18, 84 + 18 + 18 + 24 + j * 18));
			}
		}
  
        for(int k = 0; k < 9; k++)
		{
        	addSlotToContainer(new Slot(inv, k, 8 + k * 18, 142 + 18 + 18 + 24));
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
