/**
 * mod_* Class for Statues!... & more! mod
 */

package net.minecraft.src;

import java.awt.Color;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.server.MinecraftServer;
import net.minecraft.src.statues.*;

public class mod_statue extends BaseMod
{
	/*
	 * blocks IDs
	 */
	public static int statueID = 140;
	public static int showcaseID = 141;
	public static int stuffedMobID = 142;
	public static int hugeID = 143;
	
	/*
	 * items IDs
	 */
	public static int itemChiselID = 501;
	public static int itemHammerID = 502;
	public static int itemMagicPowderID = 503;
	
	/*
	 * item blocks IDs
	 */
	public static int itemStatueID = 500;
	public static int itemShowcaseID = 504;
	public static int itemStuffedMobID = 505;
	
	/*
	 * container IDs
	 */
	public static int statueContainerID = 6084;
	public static int showcaseContainerID = 6085;
	
	/*
	 * declaration/initialisation of blocks
	 */
	public static final Block genStatueGiant = new BlockGenStatue(hugeID, 23).setHardness(1F).setResistance(0.1F).setBlockName("genStatueGiant");
	public static final Block statue = new BlockStatue(statueID, net.minecraft.src.statues.TileEntityStatue.class, Material.rock).setHardness(1F).setResistance(1F).setBlockName("statue");
	public static final Block showcase = new BlockShowcase(showcaseID, net.minecraft.src.statues.TileEntityShowcase.class, Material.rock).setHardness(1F).setResistance(1F).setBlockName("showcase");
	
	/*
	 * declaration/initialisation of items
	 */
	public static final Item burin = new ItemBurin(itemChiselID).setIconIndex(ModLoader.addOverride("/gui/items.png", "/dolfinsbizou/burin.png")).setItemName("burin");
	public static final Item marteau = new ItemMarteau(itemHammerID).setIconIndex(ModLoader.addOverride("/gui/items.png", "/dolfinsbizou/marteau.png")).setItemName("marteau");
	public static final Item poudreMagique = new ItemMagicPowder(itemMagicPowderID).setIconIndex(ModLoader.addOverride("/gui/items.png", "/dolfinsbizou/poudremagique.png")).setItemName("poudreMagique");
	
	/*
	 * declaration/initialisation of item blocks
	 */
	public static final Item itemStatue = new ItemStatue(itemStatueID, statue).setIconIndex(ModLoader.addOverride("/gui/items.png", "/dolfinsbizou/itemstatue.png")).setItemName("itemStatue");
	public static final Item itemShowcase = new ItemShowcase(itemShowcaseID, showcase).setIconIndex(ModLoader.addOverride("/gui/items.png", "/dolfinsbizou/itemshowcase.png")).setItemName("itemShowcase");
	
	/*
	 * declaration of textures
	 */
	public static int genstatueupdown;
	public static int genstatuerear;
	public static int genstatuefront;
	public static int genstatuelateral;
	public static int textNull;
	public static int slotDye;
	public static int slotStandardBlock;
	public static int slotHand;
	
	/*
	 * declaration of the Array used in the Pixel2Block conversion
	 */
	public static ArrayList<ColorBlock> ListBlocks = new ArrayList();
	
	/**
	 * Returns the version of the mod
	 */
	public String getVersion()
	{
		return "4pre-alpha";
		
	}
	
	/**
	 * Technical method, used to perform some actions on mod init
	 */
	public void load()
	{
		this.registerBlocks();
		this.addNames();
		this.addRecipes();
		this.addTextures();
		this.registerTileEntities();
		this.addBlockColors();
		this.registerPacketChannels();
		this.registerContainerIDs();
	}
	
	/**
	 * Technical method, register blocks
	 */
	private void registerBlocks()
	{
		ModLoader.registerBlock(genStatueGiant);
	}
	
	/**
	 * Technical method, add names of blocks and items
	 */
	private void addNames()
	{
		ModLoader.addName(genStatueGiant, "Giant statue generator");
		
		ModLoader.addName(burin, "Chisel");
		ModLoader.addName(marteau, "Hammer");
		ModLoader.addName(poudreMagique, "Mysterious powder");
		
		ModLoader.addName(itemStatue, "Statue");
		ModLoader.addName(itemShowcase, "Showcase");
	}
	
	/**
	 * Technical method, add recipes and shapeless recipes of blocks and items
	 */
	private void addRecipes()
	{
		ModLoader.addRecipe(new ItemStack(burin, 1), new Object[]
		{
			"  I",
			" S ",
			"   ",
			Character.valueOf('S'), Item.stick,
			Character.valueOf('I'), Item.ingotIron
		});
		
		ModLoader.addRecipe(new ItemStack(marteau, 1), new Object[]
		{
			" I ",
			" SI",
			"S  ",
			Character.valueOf('S'), Item.stick,
			Character.valueOf('I'), Item.ingotIron
		});
		
		ModLoader.addShapelessRecipe(new ItemStack(poudreMagique, 9), new Object[]
		{
			Block.planks, Block.stone, Block.cobblestone, 
			Block.sand, Item.diamond, Block.wood, 
			Item.coal, Block.gravel, Block.dirt
		});
		
		ModLoader.addRecipe(new ItemStack(genStatueGiant, 1), new Object[]
				{
					"GGG",
					"WPW",
					"S S",
					Character.valueOf('S'), Item.stick,
					Character.valueOf('W'), Block.wood,
					Character.valueOf('P'), mod_statue.poudreMagique,
					Character.valueOf('G'), Block.thinGlass
				});
		
		ModLoader.addRecipe(new ItemStack(itemStatue, 1), new Object[]
				{
					"SSS",
					"SPS",
					"DDD",
					Character.valueOf('S'), Block.stone,
					Character.valueOf('D'), new ItemStack(Block.stoneSingleSlab, 1, 0),
					Character.valueOf('P'), mod_statue.poudreMagique
				});
		
		ModLoader.addRecipe(new ItemStack(itemShowcase, 1), new Object[]
				{
					"GGG",
					"WPW",
					"S S",
					Character.valueOf('S'), Item.stick,
					Character.valueOf('W'), Block.wood,
					Character.valueOf('G'), Block.thinGlass,
					Character.valueOf('P'), mod_statue.poudreMagique
				});
	}
	
	/**
	 * Technical method, add textures of the mod
	 */
	private void addTextures()
	{
		genstatueupdown = ModLoader.addOverride("/terrain.png", "/dolfinsbizou/genstatueupdown.png");
		genstatuelateral = ModLoader.addOverride("/terrain.png", "/dolfinsbizou/genstatuelateral.png");
		genstatuefront = ModLoader.addOverride("/terrain.png", "/dolfinsbizou/genstatuefront.png");
		genstatuerear = ModLoader.addOverride("/terrain.png", "/dolfinsbizou/genstatuerear.png");
		textNull = ModLoader.addOverride("/terrain.png", "/dolfinsbizou/null.png");
		slotDye = ModLoader.addOverride("/gui/items.png", "/dolfinsbizou/slotdye.png");
		slotStandardBlock = ModLoader.addOverride("/gui/items.png", "/dolfinsbizou/slotstandardblock.png");
		slotHand = ModLoader.addOverride("/gui/items.png", "/dolfinsbizou/slothand.png");
	}
	
	/**
	 * 
	 */
	private void registerTileEntities()
	{
		RenderStatue render_statue = new RenderStatue();
		ModLoader.registerTileEntity(net.minecraft.src.statues.TileEntityStatue.class, "TileEntityStatue", render_statue);
		
		RenderShowcase render_showcase = new RenderShowcase();
		ModLoader.registerTileEntity(net.minecraft.src.statues.TileEntityShowcase.class, "TileEntityShowcase", render_showcase);
	}
	
	/**
	 * Technical method, add block colors to the ArrayList<ColorBlock> ListBlocks
	 */
	private void addBlockColors()
	{
		//Color variations
			ListBlocks.add(new ColorBlock(Block.cloth.blockID, 14, new Color(0x792728))); //red wool variation
			ListBlocks.add(new ColorBlock(Block.cloth.blockID, 8, new Color(0x443F34))); //light gray wool variation
			ListBlocks.add(new ColorBlock(Block.cloth.blockID, 12, new Color(0x683F2A))); //brown wool variation
			ListBlocks.add(new ColorBlock(Block.sandStone.blockID, 0, new Color(0xD09780))); //sandstone variation
			ListBlocks.add(new ColorBlock(Block.cloth.blockID, 1, new Color(0x805300))); //orange wool darker
			ListBlocks.add(new ColorBlock(Block.cloth.blockID, 1, new Color(0xFFD280))); //orange wool lighter
			ListBlocks.add(new ColorBlock(Block.cloth.blockID, 2, new Color(0x800080))); //magenta wool darker
			ListBlocks.add(new ColorBlock(Block.cloth.blockID, 2, new Color(0xFF80FF))); //magenta wool lighter
			ListBlocks.add(new ColorBlock(Block.cloth.blockID, 3, new Color(0x3C5B80))); //light blue wool darker
			ListBlocks.add(new ColorBlock(Block.cloth.blockID, 3, new Color(0xBDDBFF))); //light blue wool lighter
			ListBlocks.add(new ColorBlock(Block.cloth.blockID, 4, new Color(0x808000))); //yellow wool darker
			ListBlocks.add(new ColorBlock(Block.cloth.blockID, 4, new Color(0xFFFF80))); //yellow wool lighter
			ListBlocks.add(new ColorBlock(Block.cloth.blockID, 5, new Color(0x008000))); //lime wool darker
			ListBlocks.add(new ColorBlock(Block.cloth.blockID, 5, new Color(0x80FF80))); //lime wool lighter
			//ListBlocks.add(new ColorBlock(Block.cloth.blockID, 6, new Color(0x806065))); //pink wool darker
			//ListBlocks.add(new ColorBlock(Block.cloth.blockID, 6, new Color(0xFFE0E6))); //pink wool lighter
			//ListBlocks.add(new ColorBlock(Block.cloth.blockID, 9, new Color(0x008080))); //cyan wool darker
			//ListBlocks.add(new ColorBlock(Block.cloth.blockID, 9, new Color(0x80FFFF))); //cyan wool lighter
			ListBlocks.add(new ColorBlock(Block.cloth.blockID, 10, new Color(0x33004D))); //purple wool darker
			ListBlocks.add(new ColorBlock(Block.cloth.blockID, 10, new Color(0x804D99))); //purple wool lighter
			ListBlocks.add(new ColorBlock(Block.cloth.blockID, 11, new Color(0x000080))); //blue wool darker
			ListBlocks.add(new ColorBlock(Block.cloth.blockID, 11, new Color(0x8080FF))); //blue wool lighter
			ListBlocks.add(new ColorBlock(Block.cloth.blockID, 12, new Color(0x2E1E09))); //brown wool darker
			ListBlocks.add(new ColorBlock(Block.cloth.blockID, 12, new Color(0xB87722))); //brown wool lighter
			ListBlocks.add(new ColorBlock(Block.cloth.blockID, 13, new Color(0x004000))); //green wool darker
			ListBlocks.add(new ColorBlock(Block.cloth.blockID, 13, new Color(0x233B15))); //green wool kaki
			ListBlocks.add(new ColorBlock(Block.cloth.blockID, 13, new Color(0x373D1B))); //green wool kaki 2
			ListBlocks.add(new ColorBlock(Block.cloth.blockID, 13, new Color(0x3D5F25))); //green wool kaki 3
			ListBlocks.add(new ColorBlock(Block.cloth.blockID, 13, new Color(0x373D1B))); //green wool kaki 4
			ListBlocks.add(new ColorBlock(Block.cloth.blockID, 13, new Color(0x009900))); //green wool lighter
			ListBlocks.add(new ColorBlock(Block.cloth.blockID, 14, new Color(0x800000))); //red wool darker
			ListBlocks.add(new ColorBlock(Block.cloth.blockID, 14, new Color(0xFF8080))); //red wool lighter
		
		//Wool strict
			ListBlocks.add(new ColorBlock(Block.cloth.blockID, 0, new Color(0xFFFFFF))); //white wool
			ListBlocks.add(new ColorBlock(Block.cloth.blockID, 1, new Color(0xFFA500))); //orange wool
	        ListBlocks.add(new ColorBlock(Block.cloth.blockID, 2, new Color(0xFF00FF))); //magenta wool
	        ListBlocks.add(new ColorBlock(Block.cloth.blockID, 3, new Color(0x77B5FE))); //light blue wool
	        ListBlocks.add(new ColorBlock(Block.cloth.blockID, 4, new Color(0xFFFF00))); //yellow wool
	        ListBlocks.add(new ColorBlock(Block.cloth.blockID, 5, new Color(0x00FF00))); //lime wool
	        ListBlocks.add(new ColorBlock(Block.cloth.blockID, 6, new Color(0xFFC0CB))); //pink wool
	        ListBlocks.add(new ColorBlock(Block.cloth.blockID, 7, new Color(0x808080))); //gray wool
	        ListBlocks.add(new ColorBlock(Block.cloth.blockID, 8, new Color(0xCECECE))); //light gray wool
	        ListBlocks.add(new ColorBlock(Block.cloth.blockID, 9, new Color(0x00FFFF))); //cyan wool
	        ListBlocks.add(new ColorBlock(Block.cloth.blockID, 10, new Color(0x660099))); //purple wool
	        ListBlocks.add(new ColorBlock(Block.cloth.blockID, 11, new Color(0x0000FF))); //blue wool
	        ListBlocks.add(new ColorBlock(Block.cloth.blockID, 12, new Color(0x5B3B11))); //brown wool
	        ListBlocks.add(new ColorBlock(Block.cloth.blockID, 13, new Color(0x008000))); //green wool
	        ListBlocks.add(new ColorBlock(Block.cloth.blockID, 14, new Color(0xFF0000))); //red wool
	        ListBlocks.add(new ColorBlock(Block.cloth.blockID, 15, new Color(0x000000))); //black wool
        
        //others
	        ListBlocks.add(new ColorBlock(Block.sandStone.blockID, 0, new Color(0xE9D08D))); //sandstone
	        ListBlocks.add(new ColorBlock(Block.sandStone.blockID, 0, new Color(0xFDC463))); //another sandstone
	        ListBlocks.add(new ColorBlock(Block.sandStone.blockID, 0, new Color(0xFBA67B))); //another sandstone
	}

	/**
	 * Technical method, register packet channels
	 */
	private void registerPacketChannels()
	{
		ModLoader.registerPacketChannel(this, "TE|Statue");
		ModLoader.registerPacketChannel(this, "TE|Showcase");
	}
	
	/**
	 * Technical method, register container IDs
	 */
	private void registerContainerIDs()
	{
		ModLoader.registerContainerID(this, statueContainerID);
		ModLoader.registerContainerID(this, showcaseContainerID);
	}
	
	/**
	 * *doesn't work yet* Technical method, load tileentity datas for the server
	 */
	/*
	public void serverCustomPayload(NetServerHandler var1, Packet250CustomPayload var2)
    {
        if (var2.channel.equals("TE|Statue"))
        {
            EntityPlayerMP var3 = var1.getPlayer();

            if (var3.theItemInWorldManager.getGameType() != EnumGameType.CREATIVE)
            {
                return;
            }

            try
            {
                DataInputStream var4 = new DataInputStream(new ByteArrayInputStream(var2.data));
                int var5 = var4.readInt();
                int var6 = var4.readInt();
                int var7 = var4.readInt();
                byte var8 = (byte)var4.read();
                int buttonValueD = var4.read();
                //String textfield1D = var4.;
                MinecraftServer var11 = MinecraftServer.getServer();
                WorldServer var12 = var11.worldServerForDimension(var8);

                if (var12 == null || var12.getBlockId(var5, var6, var7) != statue.blockID)
                {
                    return;
                }

                TileEntity var13 = var11.worldServerForDimension(var8).getBlockTileEntity(var5, var6, var7);

                if (var13 instanceof TileEntityStatue)
                {
                    TileEntityStatue var14 = (TileEntityStatue)var13;
                    var14.setButtonValue(buttonValueD);
                    //var14.skin = var10;
                    var11.getConfigurationManager().sendPacketToAllPlayersInDimension(var2, var8);
                }
            }
            catch (IOException var15)
            {
                var15.printStackTrace();
            }
        }
    }//*/

	/**
	 * *doesn't work yet* Technical method, load TileEntity datas for the client
	 */
	/*
    public void clientCustomPayload(NetClientHandler var1, Packet250CustomPayload var2)
    {
        if (var2.channel.equals("TE|Statue"))
        {
            try
            {
                DataInputStream var3 = new DataInputStream(new ByteArrayInputStream(var2.data));
                int var4 = var3.readInt();
                int var5 = var3.readInt();
                int var6 = var3.readInt();
                byte var7 = (byte)var3.read();
                int buttonValueD = var3.read();
                //String textfield1D = var3.;
                Minecraft mc = ModLoader.getMinecraftInstance();
                EntityClientPlayerMP var11 = mc.thePlayer;

                if (var11.dimension != var7)
                {
                    return;
                }

                TileEntity var12 = var11.worldObj.getBlockTileEntity(var4, var5, var6);

                if (var12 instanceof TileEntityStatue)
                {
                    TileEntityStatue var13 = (TileEntityStatue)var12;
                    var13.setButtonValue(buttonValueD);
                    //var13.skin = var10;
                }
            }
            catch (IOException var14)
            {
                var14.printStackTrace();
            }
        }
    }//*/
	
	/**
	 * Technical method, return the associated GUI for a container ID
	 */
    public GuiContainer getContainerGUI(EntityClientPlayerMP var1, int var2, int var3, int var4, int var5)
    {
        if (var2 == statueContainerID)
        {
            TileEntity var6 = var1.worldObj.getBlockTileEntity(var3, var4, var5);

            if (var6 instanceof TileEntityStatue)
            {
               return new GuiStatue(var1.inventory, (TileEntityStatue)var6, ModLoader.getMinecraftInstance().theWorld, var3, var4, var5);
            }
        }
        else if (var2 == showcaseContainerID)
        {
            TileEntity var6 = var1.worldObj.getBlockTileEntity(var3, var4, var5);

            if (var6 instanceof TileEntityShowcase)
            {
               return new GuiShowcase(var1.inventory, (TileEntityShowcase)var6, ModLoader.getMinecraftInstance().theWorld, var3, var4, var5);
            }
        }

        return null;
    }
}