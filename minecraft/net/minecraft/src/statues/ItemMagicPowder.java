/**
 * Item class for the mysterious powder
 */

package net.minecraft.src.statues;

import net.minecraft.src.*;

public class ItemMagicPowder extends Item
{
	public ItemMagicPowder(int par1)
	{
		super(par1);
		this.setCreativeTab(CreativeTabs.tabMaterials);
	}
	
	/**
	 * Return true if the item has an effect (so add the glint texture)
	 */
	public boolean hasEffect(ItemStack par1ItemStack)
	{
		return true;
	}
	
	/**
	 * Return an item rarity from EnumRarity
	 */
	public EnumRarity getRarity(ItemStack par1ItemStack)
	{
		return EnumRarity.uncommon;
	}
}