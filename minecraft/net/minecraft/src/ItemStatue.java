package net.minecraft.src;

public class ItemStatue extends ItemReed
{
	public ItemStatue(int par1, Block par2Block)
	{
		super(par1, par2Block);
		setHasSubtypes(true);
		setMaxDamage(0);
		maxStackSize = 1;
	}	
	
	/**
     * Returns the metadata of the block which this Item (ItemBlock) can place
     */
    public int getMetadata(int par1)
    {
        return par1;
    }
}
