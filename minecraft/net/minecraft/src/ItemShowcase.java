package net.minecraft.src;

public class ItemShowcase extends ItemReed
{
	public ItemShowcase(int par1, Block par2Block)
	{
		super(par1, par2Block);
		setHasSubtypes(true);
		setMaxDamage(0);
		maxStackSize = 64;
	}	
	
	/**
     * Returns the metadata of the block which this Item (ItemBlock) can place
     */
    public int getMetadata(int par1)
    {
        return par1;
    }
}
