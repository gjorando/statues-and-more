package net.minecraft.src;

public class ContainerShowcase extends Container
{
    private IInventory inv;
    private TileEntity_showcase tile;

    public ContainerShowcase(InventoryPlayer inventory, TileEntity_showcase teshowcase)
    {
        inv = inventory;
        tile = teshowcase;
        addSlot(new SlotHand(teshowcase, 0, 120, 59));
        
        for(int j = 0; j < 3; j++)
		{
			for(int i1 = 0; i1 < 9; i1++)
			{
				addSlot(new Slot(inv, i1 + j * 9 + 9, 48 + i1 * 18, 84 + 18 + 18 + 24 + j * 18));
			}
		}
  
        for(int k = 0; k < 9; k++)
		{
        	addSlot(new Slot(inv, k, 48 + k * 18, 142 + 18 + 18 + 24));
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
