package net.minecraft.src;

public class ItemMagicPowder extends Item
{

	protected ItemMagicPowder(int par1)
	{
		super(par1);
		// TODO Auto-generated constructor stub
	}
	
	public boolean hasEffect(ItemStack par1ItemStack)
    {
        return true;
    }

    public EnumRarity getRarity(ItemStack par1ItemStack)
    {
        return EnumRarity.uncommon;
    }
}
