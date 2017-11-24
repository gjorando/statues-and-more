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
        ySize = 226;
        monde = par2;
		x = i;
		y = j;
		z = k;
    }

    protected void drawGuiContainerForegroundLayer()
    {   
        GL11.glDisable(2896 /*GL_LIGHTING*/);
        GL11.glDisable(2929 /*GL_DEPTH_TEST*/);
        GL11.glColor3f(1.0F, 1.0F, 1.0F);
        fontRenderer.drawString("Slab text:", 49, 103, 0x000000);
        fontRenderer.drawString(tile.getInvName(), 72, -7, 0x808080);
        GL11.glEnable(2896 /*GL_LIGHTING*/);
        GL11.glEnable(2929 /*GL_DEPTH_TEST*/);
    }

    public void initGui()
    {
    	super.initGui();
    	int centreX = (width - xSize) / 2;
    	int centreY = (height - ySize) / 2;
    	
    	controlList.clear();
    	textfield3 = new GuiTextField(fontRenderer, centreX+49, centreY+111, 59, 10);
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
