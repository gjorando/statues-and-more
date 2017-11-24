package net.minecraft.src;

import net.minecraft.client.Minecraft;

public class TileEntity_statue extends TileEntity implements IInventory
{
    private Minecraft mc;
    private ItemStack items[];
	private int buttonValue;
	private String textField1, textField2;
	public String skinURL;

    public TileEntity_statue()
    {
    	textField1 = "";
    	textField2 = "";
    	buttonValue = 0;
        mc = (ModLoader.getMinecraftInstance());
        items = new ItemStack[4];
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
        return "Statue";
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
        //*
        buttonValue = nbttagcompound.getInteger("buttonValue");
        textField1 = nbttagcompound.getString("textField1");
        textField2 = nbttagcompound.getString("textField2");//*/

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
        //*
        nbttagcompound.setInteger("buttonValue", buttonValue);
        nbttagcompound.setString("textField1", textField1);
        nbttagcompound.setString("textField2", textField2);//*/
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
    }

    public void closeChest()
    {
    }
    
    public int getSkin()
    {
		switch(buttonValue)
		{
		case 0:
		default:
			return mc.renderEngine.getTexture("/dolfinsbizou/massif.png");
		case 1:
			return 1;
		case 2:
			return mc.renderEngine.getTexture("/dolfinsbizou/statue.png");
		case 3:
			return mc.renderEngine.getTexture("/mob/zombie.png");
		case 4:
			if (getTextField1() != null && getTextField1().length() > 0)
	        {
	            skinURL = (new StringBuilder()).append("http://s3.amazonaws.com/MinecraftSkins/").append(getTextField1()).append(".png").toString();
	        }
			else
			{
				skinURL = "http://www.perdu.com";
			}
			return mc.renderEngine.getTextureForDownloadableImage(skinURL, "/dolfinsbizou/noweb.png");
		case 5:
			return mc.renderEngine.getTextureForDownloadableImage(getTextField2(), "/dolfinsbizou/noweb.png");
		case 6:
			return mc.renderEngine.getTexture("/mob/creeper.png");
		case 7:
			return mc.renderEngine.getTexture("/dolfinsbizou/croix.png");
		}
    }
    
    public void setButtonValue(int value)
    {
    	buttonValue = value;
    }

	public int getButtonValue()
	{
		return buttonValue;
	}

	public Minecraft getMc()
	{
		return mc;
	}

	public String getTextField1()
	{
		return textField1;
	}

	public void setTextField1(String textField1)
	{
		this.textField1 = textField1;
	}

	public String getSkinURL()
	{
		return skinURL;
	}
	
	public String getTextField2()
	{
		return textField2;
	}

	public void setTextField2(String textField2)
	{
		this.textField2 = textField2;
	}
    
}