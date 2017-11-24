package net.minecraft.src;

import java.util.Random;
import java.util.List;
import net.minecraft.client.Minecraft;

public class mod_statue extends BaseMod
{
	@MLProp (min=1.0D, max=255.0D, info = "The block statue", name = "statueID")
	public static int statueID = 128;
	@MLProp (min=1.0D, max=255.0D, info = "The showcase statue", name = "showcaseID")
	public static int showcaseID = 130;
	@MLProp (min=1.0D, max=255.0D, info = "The giant statue generator block", name = "hugeID")
	public static int hugeID = 129;
	@MLProp (min=500.0D, max=1000.0D, info = "The item statue (used to place a block statue)", name = "itemStatueID")
	public static int itemStatueID = 500;
	@MLProp (min=500.0D, max=1000.0D, info = "The item chisel", name = "itemChiselID")
	public static int itemChiselID = 501;
	@MLProp (min=500.0D, max=1000.0D, info = "The item hammer", name = "itemHammerID")
	public static int itemHammerID = 502;
	@MLProp (min=500.0D, max=1000.0D, info = "The item magic powder", name = "itemMagicPowderID")
	public static int itemMagicPowderID = 503;
	@MLProp (min=500.0D, max=1000.0D, info = "The item showcase (used to place a block showcase)", name = "itemShowcaseID")
	public static int itemShowcaseID = 504;
	
	public static final Block statue = new Statue(statueID, net.minecraft.src.TileEntity_statue.class, Material.rock).setHardness(1F).setResistance(1F).setBlockName("statue");
	public static final Block showcase = new Showcase(showcaseID, net.minecraft.src.TileEntity_showcase.class, Material.rock).setHardness(1F).setResistance(1F).setBlockName("showcase");
	public static final Block genStatueGiant = new BlockGenStatue(hugeID, ModLoader.addOverride("/terrain.png", "/dolfinsbizou/itemstatue.png")).setHardness(1F).setResistance(0.1F).setBlockName("genStatueGiant");
	public static final Item itemStatue = new ItemStatue(itemStatueID, statue).setIconIndex(ModLoader.addOverride("/gui/items.png", "/dolfinsbizou/itemstatue.png")).setItemName("itemStatue");
	public static final Item itemShowcase = new ItemShowcase(itemShowcaseID, showcase).setIconIndex(ModLoader.addOverride("/gui/items.png", "/dolfinsbizou/itemshowcase.png")).setItemName("itemShowcase");
	public static final Item burin = new ItemBurin(itemChiselID).setIconIndex(ModLoader.addOverride("/gui/items.png", "/dolfinsbizou/burin.png")).setItemName("burin");
	public static final Item marteau = new ItemMarteau(itemHammerID).setIconIndex(ModLoader.addOverride("/gui/items.png", "/dolfinsbizou/marteau.png")).setItemName("marteau");
	public static final Item poudreMagique = new ItemMagicPowder(itemMagicPowderID).setIconIndex(ModLoader.addOverride("/gui/items.png", "/dolfinsbizou/poudremagique.png")).setItemName("poudreMagique");
	public static int slotStandardBlock;
	public static int textNull;
	public static int slotDye;
	public static int slotHand;
	
	public String getVersion()
	{
		return "3r1 W.I.P update";
	}
	
	public void load()
	{    
		ModLoader.setInGUIHook(this, true, true);
		ModLoader.setInGameHook(this, true, true);
		 
		Render_statue render_statue = new Render_statue();
		Render_showcase render_showcase = new Render_showcase();

		ModLoader.registerTileEntity(net.minecraft.src.TileEntity_statue.class, "TileEntity_statue", render_statue);
		ModLoader.registerTileEntity(net.minecraft.src.TileEntity_showcase.class, "TileEntity_showcase", render_showcase);
		
		ModLoader.addName(itemStatue, "Statue");

		ModLoader.addRecipe(new ItemStack(itemStatue, 1), new Object[]
		                {
		            "SSS",
		            "SPS",
		            "DDD",
		Character.valueOf('S'), Block.stone, Character.valueOf('P'), mod_statue.poudreMagique, 
		Character.valueOf('D'), new ItemStack(Block.stairSingle, 1, 0)
		                });
		
		ModLoader.addName(itemShowcase, "Showcase");

		ModLoader.addRecipe(new ItemStack(itemShowcase, 1), new Object[]
		                {
		            "GGG",
		            "WPW",
		            "S S",
		Character.valueOf('S'), Item.stick, Character.valueOf('W'), Block.wood, 
		Character.valueOf('G'), Block.thinGlass, Character.valueOf('P'), mod_statue.poudreMagique
		                });
		
		ModLoader.addName(burin, "Chisel");

		ModLoader.addRecipe(new ItemStack(burin, 1), new Object[]
		                {
		            "  I",
		            " S ",
		            "   ",
		Character.valueOf('S'), Item.stick, Character.valueOf('I'), Item.ingotIron
		                });
		
		ModLoader.addName(marteau, "Hammer");

		ModLoader.addRecipe(new ItemStack(marteau, 1), new Object[]
		                {
		            " I ",
		            " SI",
		            "S  ",
		Character.valueOf('S'), Item.stick, Character.valueOf('I'), Item.ingotIron
		                });
		
		ModLoader.addName(poudreMagique, "Mysterious powder");

		ModLoader.addShapelessRecipe(new ItemStack(poudreMagique, 9), new Object[]
		                {
		            Block.planks, Block.stone, Block.cobblestone, 
		            Block.sand, Item.diamond, Block.wood, 
		            Item.coal, Block.gravel, Block.dirt,
		                });
		
		ModLoader.registerBlock(genStatueGiant);
		ModLoader.addName(genStatueGiant, "Giant statue generator");
		
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
                list.add(new ItemStack(itemShowcase, 1, 0));
                list.add(new ItemStack(genStatueGiant));
                list.add(new ItemStack(marteau, 1, 0));
                list.add(new ItemStack(burin, 1, 0));
                list.add(new ItemStack(poudreMagique, 1, 0));
            }
        }
        return true;
    }
 
 private static GuiScreen lastGuiOpen;
	
}
