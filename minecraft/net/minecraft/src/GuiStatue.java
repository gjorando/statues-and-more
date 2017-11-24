package net.minecraft.src;

import net.minecraft.client.Minecraft;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

public class GuiStatue extends GuiContainer
{
    public final InventoryPlayer invg;
    public final TileEntity_statue tile;
    World monde;
	private int x, y, z;
	private GuiTextField textfield3;
    
    public GuiStatue(InventoryPlayer inv, TileEntity_statue testatue, World par2, int i, int j, int k)
    {
        super(new ContainerStatue(inv, testatue));
        invg = inv;
        tile = testatue;
        xSize = 176;
        ySize = 202;
        monde = par2;
		x = i;
		y = j;
		z = k;
    }

    protected void drawGuiContainerForegroundLayer()
    {
        int l = (width - xSize) / 2;
        int i1 = (height - ySize) / 2;
        //This is for armor render, doesn't works correctly yet
        /*
        Model_statue statue = new Model_statue();
	    Model_armor inner = new Model_armor(0.5F);
	    Model_armor outer = new Model_armor(1F);
	    Model_Screeper creeper = new Model_Screeper();
	    Model_Acreeper armorcreep = new Model_Acreeper(0.5F);
	    Model_croix croix = new Model_croix();
	    Model_skeleton skeleton = new Model_skeleton();
	    String armorArray[] = null;
	    
	    if (armorArray == null)
        {
            try
            {
                armorArray = (String[]) ModLoader.getPrivateValue(net.minecraft.src.RenderPlayer.class, null, 3);
            }
            catch (Throwable throwable)
            {
                throwable.printStackTrace();
            }
        }

        if (armorArray == null)
        {
            return;
        }
	    
        GL11.glEnable(32826);
        GL11.glEnable(2903);
        GL11.glPushMatrix();
        GL11.glTranslatef(97.5F, 43F, 20F);
        float f1 = 30F;
        GL11.glScalef(f1, f1, f1);
        GL11.glRotatef(0F, 0F, 0F, 1F);
        RenderHelper.enableStandardItemLighting();
        
        for (int iFor = 0; iFor <= 4; iFor++)
        {
            Model_statue modelstatue;
            Model_armor modelarmor;
            Model_Screeper modelcreeper;
        	Model_Acreeper modelarmorcreep;
            Model_croix modelcroix;
        	Model_massif modelmassif;
        	Model_skeleton modelskeleton;
            
        	   int button = tile.getButtonValue();
        	   
            if (iFor == 4)
            {
         	   
         	   if(button == 4)
         	   {
         		   tile.getMc().renderEngine.obtainImageData(tile.getSkinURL(), new ImageBufferDownload());
         	   }
         	   else if(button == 5)
         	   {
         		   tile.getMc().renderEngine.obtainImageData(tile.getTextField2(), new ImageBufferDownload());
         	   }
         	   
         	   int valeur = tile.getSkin();
         	   
         	   GL11.glBindTexture(GL11.GL_TEXTURE_2D, valeur);
         	   
         	   if(button == 2 || button == 3 || button == 4 || button == 5)
         	   {
         		   modelstatue = statue;
	                   modelstatue.renderModel(0.0625F);
         	   }
         	   else if(button == 6)
         	   {
         		   modelcreeper = creeper;
         		   modelcreeper.renderModel(0.0625F);
         	   }
         	   else if(button == 7)
         	   {
         		   modelcroix = croix;
         		   modelcroix.renderModel(0.0625F);
         	   }
         	   else if(button == 8)
         	   {
         		   modelskeleton = skeleton;
         		   modelskeleton.renderModel(0.0625F);
         	   }
            }
            if (iFor == 0 || iFor == 1 || iFor == 2 || iFor == 3)
            {
                ItemStack itemstack = tile.getStackInSlot(iFor);
                
                if (itemstack == null || !(Item.itemsList[itemstack.itemID] instanceof ItemArmor))
                {
                    continue;
                }

                ItemArmor itemarmor = (ItemArmor)Item.itemsList[itemstack.itemID];
                int k = itemarmor.armorType;
                int armorTex = mc.renderEngine.getTexture((new StringBuilder("/armor/")).append(armorArray[itemarmor.renderIndex]).append("_").append(k != 2 ? 1 : 2).append(".png").toString());
                GL11.glBindTexture(GL11.GL_TEXTURE_2D, armorTex);
                if(button == 1 || button == 2 || button == 3 || button == 4 || button == 5 || button == 7 || button == 8)
                {
	                   modelarmor = k != 2 ? outer : inner;
	                   modelarmor.tete.showModel = k == 0;
	                   modelarmor.corps.showModel = k == 1 || k == 2;
	                   modelarmor.bras_droit.showModel = k == 1;
	                   modelarmor.bras_gauche.showModel = k == 1;
	                   modelarmor.jambe_droite.showModel = k == 2 || k == 3;
	                   modelarmor.jambe_gauche.showModel = k == 2 || k == 3;
	                   modelarmor.renderModel(0.0625F);
	                   if(button == 7)
	                   {
	                	   modelarmor.tete.rotateAngleX = 0.3F;
	                   }
	                   else
	                   {
	                	   modelarmor.tete.rotateAngleX = 0F;
	                   }
                }
                else if(button == 6)
                {
             	   modelarmorcreep = armorcreep;
             	   modelarmorcreep.tete.showModel = k == 0;
	                   modelarmorcreep.Shape1.showModel = k == 1 || k == 2;
	                   modelarmorcreep.patte1.showModel = k == 2 || k == 3;
	                   modelarmorcreep.patte2.showModel = k == 2 || k == 3;
	                   modelarmorcreep.patte3.showModel = k == 2 || k == 3;
	                   modelarmorcreep.patte4.showModel = k == 2 || k == 3;
             	   modelarmorcreep.renderModel(0.0625F);
                }
            }

        }
        GL11.glPopMatrix();
        RenderHelper.disableStandardItemLighting();
        GL11.glDisable(32826);//*/
        
        GL11.glDisable(2896 /*GL_LIGHTING*/);
        GL11.glDisable(2929 /*GL_DEPTH_TEST*/);
        GL11.glColor3f(1.0F, 1.0F, 1.0F);
        fontRenderer.drawString("Slab text:", 71, 81, 0x000000);
        fontRenderer.drawString(tile.getInvName(), 48, -7, 0x808080);
        GL11.glEnable(2896 /*GL_LIGHTING*/);
        GL11.glEnable(2929 /*GL_DEPTH_TEST*/);
    }

    public void initGui()
    {
    	super.initGui();
    	
    	int centreX = (width - xSize) / 2;
    	int centreY = (height - ySize) / 2;
    	
    	controlList.clear();
    	textfield3 = new GuiTextField(fontRenderer, centreX+71, centreY+90, 52, 10);
		textfield3.setMaxStringLength(16); //slab text
		textfield3.setText(tile.getTextField3());
    }
    
    protected void mouseClicked(int i, int j, int k)
    {
        super.mouseClicked(i, j, k);
        textfield3.mouseClicked(i, j, k);
    }
	
	protected void keyTyped(char c, int i)
    {
		if (i == 1)
        {
            mc.displayGuiScreen(null);
            mc.setIngameFocus();
        }
       textfield3.func_50037_a(c, i);
       tile.setTextField3(textfield3.getText());
    }
    
	public void drawScreen(int i, int j, float f)
    {
        super.drawScreen(i, j, f);
        
        GL11.glDisable(2896 /*GL_LIGHTING*/);
        GL11.glDisable(2929 /*GL_DEPTH_TEST*/);
        GL11.glColor3f(1.0F, 1.0F, 1.0F);
        textfield3.drawTextBox();
        GL11.glEnable(2896 /*GL_LIGHTING*/);
        GL11.glEnable(2929 /*GL_DEPTH_TEST*/);
    }
    
    protected void drawGuiContainerBackgroundLayer(float f, int i, int j)
    {   
        int k = mc.renderEngine.getTexture("/gui/statue.png");
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        mc.renderEngine.bindTexture(k);
        int l = (width - xSize) / 2;
        int i1 = (height - ySize) / 2;
        drawTexturedModalRect(l, i1, 0, 0, xSize, ySize);
    }
}
