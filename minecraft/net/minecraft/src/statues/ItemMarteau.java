/**
 * Item Class for the hammer
 */

package net.minecraft.src.statues;

import net.minecraft.src.*;

public class ItemMarteau extends Item
{
	public ItemMarteau(int par1)
	{
		super(par1);
		maxStackSize = 1;
		this.setCreativeTab(CreativeTabs.tabTools);
	}
	
	/**
	 * Returns True is the item is rendered in full 3D when hold.
	 */
	public boolean isFull3D()
	{
		return true;
	}
}