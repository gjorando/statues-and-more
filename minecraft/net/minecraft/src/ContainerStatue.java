package net.minecraft.src;

public class ContainerStatue extends Container
{
    private IInventory inv;
    private TileEntity_statue tile;

    public ContainerStatue(InventoryPlayer inventory, TileEntity_statue testatue)
    {
        inv = inventory;
        tile = testatue;
        addSlot(new SlotArmor(null, testatue, 0, 80, 8, 0));
        addSlot(new SlotArmor(null, testatue, 1, 80, 26, 1));
        addSlot(new SlotArmor(null, testatue, 2, 80, 44, 2));
        addSlot(new SlotArmor(null, testatue, 3, 80, 62, 3));
        addSlot(new SlotStandardBlock(testatue, 4, 80, 80));
        addSlot(new SlotDye(testatue, 5, 111, 105));
        addSlot(new SlotHand(testatue, 6, 111, 26));
        
        for(int j = 0; j < 3; j++)
		{
			for(int i1 = 0; i1 < 9; i1++)
			{
				addSlot(new Slot(inv, i1 + j * 9 + 9, 8 + i1 * 18, 84 + 18 + 18 + 24 + j * 18));
			}
		}
  
        for(int k = 0; k < 9; k++)
		{
        	addSlot(new Slot(inv, k, 8 + k * 18, 142 + 18 + 18 + 24));
		}
    }

    public boolean canInteractWith(EntityPlayer entityplayer)
    {
        return true;
    }

    /**
     * Called to transfer a stack from one inventory to the other eg. when shift clicking.
     */
    public ItemStack transferStackInSlot(int i)
    {
        return null;
    }
}
