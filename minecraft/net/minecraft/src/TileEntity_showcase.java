package net.minecraft.src;

import net.minecraft.client.Minecraft;

public class TileEntity_showcase extends TileEntity implements IInventory
{
    private Minecraft mc;
    private ItemStack items[];
	private int buttonValue;
	private String textField1, textField2;
	public String skinURL;
	private String textField3;
	
    /** the current angle of the lid (between 0 and 1) */
    public float lidAngle;

    /** the angle of the lid last tick */
    public float prevLidAngle;

    /** server sync counter (once per 20 ticks) */
    private int ticksSinceSync;
    
    /** the number of players currently using this chest */
    public int numUsingPlayers;
    
    public TileEntity_showcase()
    {
    	textField1 = "<player name>";
    	textField2 = "<skin url>";
    	textField3 = "";
    	buttonValue = 0;
    	numUsingPlayers = 0;
        mc = (ModLoader.getMinecraftInstance());
        items = new ItemStack[1];
    }

    public int getSizeInventory()
    {
        return items.length;
    }

    public ItemStack getStackInSlot(int i)
    {
        return items[i];
    }

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

    public void setInventorySlotContents(int i, ItemStack itemstack)
    {
        items[i] = itemstack;

        if (itemstack != null && itemstack.stackSize > getInventoryStackLimit())
        {
            itemstack.stackSize = getInventoryStackLimit();
        }

        onInventoryChanged();
    }

    public String getInvName()
    {
        return "Showcase";
    }

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

    public int getInventoryStackLimit()
    {
        return 1;
    }

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

    public void openChest()
    {
        numUsingPlayers++;
        worldObj.playNoteAt(xCoord, yCoord, zCoord, 1, numUsingPlayers);
    }

    public void closeChest()
    {
        numUsingPlayers--;
        worldObj.playNoteAt(xCoord, yCoord, zCoord, 1, numUsingPlayers);
    }
    
    public void onTileEntityPowered(int par1, int par2)
    {
        if (par1 == 1)
        {
            numUsingPlayers = par2;
        }
    }
    
	public Minecraft getMc()
	{
		return mc;
	}
	
    public void updateEntity()
    {
        super.updateEntity();

        if ((++ticksSinceSync % 20) * 4 == 0)
        {
            worldObj.playNoteAt(xCoord, yCoord, zCoord, 1, numUsingPlayers);
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