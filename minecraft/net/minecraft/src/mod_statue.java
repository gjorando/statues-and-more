package net.minecraft.src;

import java.util.Random;
import java.util.List;
import net.minecraft.client.Minecraft;

public class mod_statue extends BaseMod
{
	public static final Block statue = new Statue(128, net.minecraft.src.TileEntity_statue.class, Material.rock).setHardness(1F).setResistance(1F).setBlockName("statue");
	public static final Item itemStatue = new ItemStatue(500, statue).setIconIndex(ModLoader.addOverride("/gui/items.png", "/dolfinsbizou/itemstatue.png")).setItemName("itemStatue");
	public static final Item burin = new ItemBurin(501).setIconIndex(ModLoader.addOverride("/gui/items.png", "/dolfinsbizou/burin.png")).setItemName("burin");
	public static final Item marteau = new ItemMarteau(502).setIconIndex(ModLoader.addOverride("/gui/items.png", "/dolfinsbizou/marteau.png")).setItemName("marteau");
	public static final Item poudreMagique = new ItemMagicPowder(503).setIconIndex(ModLoader.addOverride("/gui/items.png", "/dolfinsbizou/poudremagique.png")).setItemName("poudreMagique");
	public static int slotStandardBlock;
	public static int textNull;
	public static int slotDye;
	public static int slotHand;
	
	public String getVersion()
	{
		return "3r";
	}
	
	public void load()
	{
		ModLoader.setInGUIHook(this, true, true);
		ModLoader.setInGameHook(this, true, true);
		 
		Render_statue render_statue = new Render_statue();

		ModLoader.registerTileEntity(net.minecraft.src.TileEntity_statue.class, "TileEntity_statue", render_statue);
		
		ModLoader.addName(itemStatue, "Statue");

		ModLoader.addRecipe(new ItemStack(itemStatue, 1), new Object[]
		                {
		            "SSS",
		            "SPS",
		            "DDD",
		Character.valueOf('S'), Block.stone, Character.valueOf('P'), mod_statue.poudreMagique, 
		Character.valueOf('D'), new ItemStack(Block.stairSingle, 1, 0)
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
		slotStandardBlock = ModLoader.addOverride("/gui/items.png", "/dolfinsbizou/slotstandardblock.png");
		slotDye = ModLoader.addOverride("/gui/items.png", "/dolfinsbizou/slotdye.png");
		textNull = ModLoader.addOverride("/terrain.png", "/dolfinsbizou/null.png");
		slotHand = ModLoader.addOverride("/gui/items.png", "/dolfinsbizou/slothand.png");
	}
	
	@Override
    public boolean onTickInGame(float f, Minecraft minecraft)
    {
        if(minecraft.currentScreen == null)
        {
            lastGuiOpen = null;
        }
        return true;
    }
 
 
 
   @Override
    public boolean onTickInGUI(float f, Minecraft minecraft, GuiScreen guiscreen)
    {
        if((guiscreen instanceof GuiContainerCreative) && !(lastGuiOpen instanceof GuiContainerCreative))
        {
            lastGuiOpen = guiscreen;
            Container container = ((GuiContainer)guiscreen).inventorySlots;
            List list = ((ContainerCreative)container).itemList;
            {
                list.add(new ItemStack(itemStatue, 1, 0));
                list.add(new ItemStack(marteau, 1, 0));
                list.add(new ItemStack(burin, 1, 0));
                list.add(new ItemStack(poudreMagique, 1, 0));
            }
        }
        return true;
    }
 
 private static GuiScreen lastGuiOpen;
	
}
