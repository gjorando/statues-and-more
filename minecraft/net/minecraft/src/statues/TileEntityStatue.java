/**
 * TileEntity class of the statue
 */

package net.minecraft.src.statues;

import net.minecraft.src.*;
import net.minecraft.client.Minecraft;

public class TileEntityStatue extends TileEntity implements IInventory
{
	private Minecraft mc = (ModLoader.getMinecraftInstance());; //the minecraft object
	private ItemStack items[] = new ItemStack[7];
	private int buttonValue = 0;
	private String textField1 = "<player name>", textField2 = "<skin url>";
	private String skinURL;
	private String textField3 = "";
	
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
	    return "Statue";
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
	    
	    buttonValue = nbttagcompound.getInteger("buttonValue");
	    System.out.println("buttonValue: " + buttonValue);
	    textField1 = nbttagcompound.getString("textField1");
	    System.out.println("textField1: " + textField1);
	    textField2 = nbttagcompound.getString("textField2");
	    System.out.println("textField2: " + textField2);
	    textField3 = nbttagcompound.getString("textField3");
	    System.out.println("textField3: " + textField3);
	    System.out.println("read");
	}
	
	/**
	 * Writes a tile entity to NBT.
	 */
	public void writeToNBT(NBTTagCompound nbttagcompound)
	{
	    super.writeToNBT(nbttagcompound);
	    
	    nbttagcompound.setInteger("buttonValue", this.buttonValue);
	    System.out.println("buttonValue: " + buttonValue);
	    nbttagcompound.setString("textField1", this.textField1);
	    System.out.println("textField1: " + textField1);
	    nbttagcompound.setString("textField2", this.textField2);
	    System.out.println("textField2: " + textField2);
	    nbttagcompound.setString("textField3", this.textField3);
	    System.out.println("textField3: " + textField3);
	    
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
	    System.out.println("written");
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
		
	}
	
	/**
	 * Called when the container is closed
	 */
	public void closeChest()
	{
		
	}
	
	/**
	 * return the result of a getTexture depending of the buttonValue for the GUISculpt
	 */
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
			return mc.renderEngine.getTexture("/mob/char.png");
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
		case 8:
			return mc.renderEngine.getTexture("/mob/skeleton.png");
		case 9:
			return mc.renderEngine.getTexture("/dolfinsbizou/statue.png");
		}
	}
	
	/**
	 * Set the actual value of the button in GUISculpt
	 */
	public void setButtonValue(int value)
	{
		buttonValue = value;
	}
	
	/**
	 * Get the actual value of the button in GUISculpt
	 */
	public int getButtonValue()
	{
		return buttonValue;
	}
	
	/**
	 * get the Minecraft instance used by the game
	 */
	public Minecraft getMc()
	{
		return mc;
	}
	
	/**
	 * get the text field 1
	 */
	public String getTextField1()
	{
		return textField1;
	}
	
	/**
	 * set the text field 1
	 */
	public void setTextField1(String textField1)
	{
		this.textField1 = textField1;
	}
	
	/**
	 * get the skin url if needed
	 */
	public String getSkinURL()
	{
		return skinURL;
	}
	
	/**
	 * get the text field 2
	 */
	public String getTextField2()
	{
		return textField2;
	}
	
	/**
	 * set the text field 2
	 */
	public void setTextField2(String textField2)
	{
		this.textField2 = textField2;
	}
	
	/**
	 * get the text field 3
	 */
	public String getTextField3()
	{
		return textField3;
	}
	
	/**
	 * set the text field 3
	 */
	public void setTextField3(String textField3)
	{
		this.textField3 = textField3;
	}
	
	/**
	 * get the text color for the statue's sign based on the dye in the slot
	 */
	public int getTextColor()
	{
		ItemStack stack = this.getStackInSlot(5);
		if(stack != null)
		{
			int i = MathHelper.clamp_int(stack.getItemDamage(), 0, 15);
			switch(i)
			{
			case 0: //inksac
			default:
				return 0x151010;
			case 1: //rose red
				return 0x902D2F;
			case 2: //catus green
				return 0x30411E;
			case 3: //cocoa beans
				return 0x4B2E1F;
			case 4: //lapis lazuli
				return 0x2E3882;
			case 5: //purple dye
				return 0x7838A3;
			case 6: //cyan dye
				return 0x2E758D;
			case 7: //light gray dye
				return 0x929292;
			case 8: //gray dye
				return 0x3B3B3B;
			case 9: //pink dye
				return 0xCE778F;
			case 10: //lime green dye
				return 0x38B94E;
			case 11: //dandelion yellow
				return 0xCEC14C;
			case 12: //light blue dye
				return 0x7291C8;
			case 13: //magenta dye
				return 0xAE41AE;
			case 14: //orange dye
				return 0xDB733E;
			case 15: //bone meal
				return 0xFBFBFB;
			}
		}
		else
		{
			return 0x000000;
		}
	}
}