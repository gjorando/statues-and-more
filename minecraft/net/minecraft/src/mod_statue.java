package net.minecraft.src;

import java.util.Random;
import net.minecraft.client.Minecraft;

public class mod_statue extends BaseMod
{
	public static final Block statue = new Statue(128, net.minecraft.src.TileEntity_statue.class).setHardness(1F).setResistance(1F).setBlockName("statue");
	public static final Item itemStatue = new ItemStatue(500, statue).setIconIndex(ModLoader.addOverride("/gui/items.png", "/dolfinsbizou/itemstatue.png")).setItemName("itemStatue");
	public static final Item burin = new ItemBurin(501).setIconIndex(ModLoader.addOverride("/gui/items.png", "/dolfinsbizou/burin.png")).setItemName("burin");
	public static final Item marteau = new ItemMarteau(502).setIconIndex(ModLoader.addOverride("/gui/items.png", "/dolfinsbizou/marteau.png")).setItemName("marteau");
	public static final Item poudreMagique = new ItemMagicPowder(503).setIconIndex(ModLoader.addOverride("/gui/items.png", "/dolfinsbizou/poudremagique.png")).setItemName("poudreMagique");

	
	
	public String getVersion()
	{
		return "2r2";
	}
	
	public void load()
	{
		Render_statue render_statue = new Render_statue();

		ModLoader.registerTileEntity(net.minecraft.src.TileEntity_statue.class, "TileEntity_statue", render_statue);
		
		ModLoader.addName(itemStatue, "Statue");

		ModLoader.addRecipe(new ItemStack(itemStatue, 1), new Object[]
		                {
		            "SSS",
		            "SPS",
		            "WWW",
		Character.valueOf('S'), Block.stone, Character.valueOf('W'), Block.wood, Character.valueOf('P'), mod_statue.poudreMagique
		                });
		
		ModLoader.addName(burin, "Chisel");

		ModLoader.addRecipe(new ItemStack(burin, 1), new Object[]
		                {
		            "  I",
		            " S ",
		            "S  ",
		Character.valueOf('S'), Item.stick, Character.valueOf('I'), Item.ingotIron
		                });
		
		ModLoader.addName(marteau, "Hammer");

		ModLoader.addRecipe(new ItemStack(marteau, 1), new Object[]
		                {
		            " II",
		            " SI",
		            "S  ",
		Character.valueOf('S'), Item.stick, Character.valueOf('I'), Item.ingotIron
		                });
		
		ModLoader.addName(poudreMagique, "Mysterious powder");

		ModLoader.addShapelessRecipe(new ItemStack(poudreMagique, 9), new Object[]
		                {
		            Block.planks, Block.stone, Block.cobblestone, 
		            Block.sand, Item.diamond, Block.wood, 
		            Item.coal, Item.blazePowder, Block.dirt,
		                });
	}
	
}
