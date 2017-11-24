package net.minecraft.src;

import java.util.Random;
import net.minecraft.client.Minecraft;

public class mod_statue extends BaseMod
{
	public static final Block statue = new Statue(128, net.minecraft.src.TileEntity_statue.class).setHardness(1F).setResistance(1F).setBlockName("statue");
	public static final Item itemStatue = new ItemStatue(500, statue).setIconIndex(ModLoader.addOverride("/gui/items.png", "/dolfinsbizou/itemstatue.png")).setItemName("itemStatue");
	
	
	public String getVersion()
	{
		return "1a2";
	}
	
	public void load()
	{
		Render_statue render_statue = new Render_statue();

		ModLoader.registerTileEntity(net.minecraft.src.TileEntity_statue.class, "TileEntity_statue", render_statue);
		
		ModLoader.addName(itemStatue, "Statue");

		ModLoader.addRecipe(new ItemStack(itemStatue, 1), new Object[]
		                {
		            "PPP",
		            "PSP",
		            "PWP",
		Character.valueOf('P'), Block.thinGlass, Character.valueOf('S'), Block.blockSteel, Character.valueOf('W'), Block.wood
		                });
	}
	
}
