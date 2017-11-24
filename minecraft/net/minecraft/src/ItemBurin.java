package net.minecraft.src;

public class ItemBurin extends Item
{

	protected ItemBurin(int par1)
	{
		super(par1);
		setMaxDamage(19);
		maxStackSize = 1;
	}
	
	public boolean isFull3D()
	{
		return true;
	}

}
