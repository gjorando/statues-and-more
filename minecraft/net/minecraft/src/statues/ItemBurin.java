/**
 * Item class for the chisel
 */

package net.minecraft.src.statues;

import net.minecraft.src.*;

public class ItemBurin extends Item
{
	public ItemBurin(int par1)
	{
		super(par1);
		setMaxDamage(19); //sets max item uses - 1
		maxStackSize = 1;
		this.setTabToDisplayOn(CreativeTabs.tabTools);
	}
	
	/**
	 * Returns True is the item is rendered in full 3D when hold.
	 */
	public boolean isFull3D()
	{
		return true;
	}
}