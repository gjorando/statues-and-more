/**
 * TileEntity class of the showcase
 */

package net.minecraft.src.statues;

import net.minecraft.client.Minecraft;
import net.minecraft.src.*;

public class TileEntityShowcase extends TileEntity implements IInventory
{
	private Minecraft mc = (ModLoader.getMinecraftInstance());
    private ItemStack items[] = new ItemStack[1];
    /* the current angle of the lid (between 0 and 1) */
    public float lidAngle;
    /* the angle of the lid last tick */
    public float prevLidAngle;
    /* server sync counter (once per 20 ticks) */
    private int ticksSinceSync;
    /* the number of players currently using this chest */
    public int numUsingPlayers = 0;
    
    /**
     * Returns the number of slots in the inventory.
     */
	public int getSizeInventory()
	{
	    return items.length;
	}
	
	/**
     * Returns the stack in slot i
     */
	public ItemStack getStackInSlot(int i)
	{
	    return items[i];
	}
	
	/**
     * Removes from an inventory slot (first arg) up to a specified number (second arg) of items and returns them in a
     * new stack.
     */
	public ItemStack decrStackSize(int i, int j)
	{
	    if (items[i] != null)
	    {
	        if (items[i].stackSize <= j)
	        {
	            ItemStack itemstack = items[i];
	            items[i] = null;
	            onInventoryChanged();
	            return itemstack;
	        }
	
	        ItemStack itemstack1 = items[i].splitStack(j);
	
	        if (items[i].stackSize == 0)
	        {
	            items[i] = null;
	        }
	
	        onInventoryChanged();
	        return itemstack1;
	    }
	    else
	    {
	        return null;
	    }
	}
	
	/**
     * When some containers are closed they call this on each slot, then drop whatever it returns as an EntityItem -
     * like when you close a workbench GUI.
     */
	public ItemStack getStackInSlotOnClosing(int i)
	{
	    if (items[i] != null)
	    {
	        ItemStack itemstack = items[i];
	        items[i] = null;
	        return itemstack;
	    }
	    else
	    {
	        return null;
	    }
	}
	
	/**
     * Sets the given item stack to the specified slot in the inventory (can be crafting or armor sections).
     */
	public void setInventorySlotContents(int i, ItemStack itemstack)
	{
	    items[i] = itemstack;
	
	    if (itemstack != null && itemstack.stackSize > getInventoryStackLimit())
	    {
	        itemstack.stackSize = getInventoryStackLimit();
	    }
	
	    onInventoryChanged();
	}
	
	/**
     * Returns the name of the inventory.
     */
	public String getInvName()
	{
	    return "Showcase";
	}
	
	/**
     * Reads a tile entity from NBT.
     */
	public void readFromNBT(NBTTagCompound nbttagcompound)
	{
		super.readFromNBT(nbttagcompound);
        NBTTagList nbttaglist = nbttagcompound.getTagList("Items");
        items = new ItemStack[getSizeInventory()];

        for (int i = 0; i < nbttaglist.tagCount(); i++)
        {
            NBTTagCompound nbttagcompound1 = (NBTTagCompound)nbttaglist.tagAt(i);
            int j = nbttagcompound1.getByte("Slot") & 0xff;

            if (j >= 0 && j < items.length)
            {
                items[j] = ItemStack.loadItemStackFromNBT(nbttagcompound1);
            }
        }
	}
	
	/**
	 * Writes a tile entity to NBT.
	 */
	public void writeToNBT(NBTTagCompound nbttagcompound)
	{
		super.writeToNBT(nbttagcompound);
        NBTTagList nbttaglist = new NBTTagList();

        for (byte byte0 = 0; byte0 < items.length; byte0++)
        {
            if (items[byte0] != null)
            {
                NBTTagCompound nbttagcompound1 = new NBTTagCompound();
                nbttagcompound1.setByte("Slot", byte0);
                items[byte0].writeToNBT(nbttagcompound1);
                nbttaglist.appendTag(nbttagcompound1);
            }
        }

        nbttagcompound.setTag("Items", nbttaglist);
	}
	
	/**
     * Returns the maximum stack size for a inventory slot. Seems to always be 64, possibly will be extended. *Isn't
     * this more of a set than a get?*
     */
	public int getInventoryStackLimit()
	{
	    return 1;
	}
	
	/**
     * Do not make give this method the name canInteractWith because it clashes with Container
     */
	public boolean isUseableByPlayer(EntityPlayer entityplayer)
	{
	    if (worldObj.getBlockTileEntity(xCoord, yCoord, zCoord) != this)
	    {
	        return false;
	    }
	
	    return entityplayer.getDistanceSq((double)xCoord + 0.5D, (double)yCoord + 0.5D, (double)zCoord + 0.5D) <= 64D;
	}
	
	/**
	 * Called when an the contents of an Inventory change, usually
	 */
	public void onInventoryChanged()
	{
	    worldObj.markBlockNeedsUpdate(xCoord, yCoord, zCoord);
	}
	
	/**
	 * Called when the container is opened
	 */
	public void openChest()
	{
		numUsingPlayers++;
        worldObj.addBlockEvent(this.xCoord, this.yCoord, this.zCoord, mod_statue.showcase.blockID, 1, this.numUsingPlayers);
	}
	
	/**
	 * Called when the container is closed
	 */
	public void closeChest()
	{
		numUsingPlayers--;
        worldObj.addBlockEvent(this.xCoord, this.yCoord, this.zCoord, mod_statue.showcase.blockID, 1, this.numUsingPlayers);
	}
	
	public void onTileEntityPowered(int par1, int par2)
    {
        if (par1 == 1)
        {
            numUsingPlayers = par2;
        }
    }
	
	/**
	 * get the Minecraft instance used by the game
	 */
	public Minecraft getMc()
	{
		return mc;
	}
	
	public void updateEntity()
    {
        super.updateEntity();

        if ((++ticksSinceSync % 20) * 4 == 0)
        {
            ;
        }

        prevLidAngle = lidAngle;
        float f = 0.1F;

        if(numUsingPlayers > 0 && lidAngle == 0F)
        {
        	double d = (double)xCoord + 0.5D;
            double d1 = (double)zCoord + 0.5D;

            worldObj.playSoundEffect(d, (double)yCoord + 0.5D, d1, "random.chestopen", 0.5F, worldObj.rand.nextFloat() * 0.1F + 0.9F);
        }
        
        if((numUsingPlayers == 0 && lidAngle > 0F) || (numUsingPlayers > 0 && lidAngle < 1F))
        {
        	float f1 = lidAngle;
        	
        	if (numUsingPlayers > 0)
        	{
        		lidAngle += f;
        	}
        	else
        	{
        		lidAngle -= f;
        	}
        	
        	if(lidAngle > 1F)
        	{
        		lidAngle = 1F;
        	}
        	if(lidAngle < 0F)
        	{
        		lidAngle = 0F;
        	}
        	
        	float f2 = 0.5F;

            if (lidAngle < f2 && f1 >= f2)
            {
                double d2 = (double)xCoord + 0.5D;
                double d3 = (double)zCoord + 0.5D;

                worldObj.playSoundEffect(d2, (double)yCoord + 0.5D, d3, "random.chestclosed", 0.5F, worldObj.rand.nextFloat() * 0.1F + 0.9F);
            }
        }
    }
}
