/**
 * GUI class for the basic GUI of the statue
 */

package net.minecraft.src.statues;

import net.minecraft.src.*;
import net.minecraft.client.Minecraft;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

public class GuiStatue extends GuiContainer
{
    public final InventoryPlayer invg;
    public final TileEntityStatue tile;
    World monde;
	private int x, y, z;
	private GuiTextField textfield3;
    
    public GuiStatue(InventoryPlayer inv, TileEntityStatue testatue, World par2, int i, int j, int k)
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

    /**
     * Draw the foreground layer for the GuiContainer (everything in front of the items)
     */
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

    /**
     * Adds the buttons (and other controls) to the screen in question.
     */
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
    
    /**
     * Called when the mouse is clicked.
     */
    protected void mouseClicked(int i, int j, int k)
    {
        super.mouseClicked(i, j, k);
        textfield3.mouseClicked(i, j, k);
    }
	
    /**
     * Fired when a key is typed. This is the equivalent of KeyListener.keyTyped(KeyEvent e).
     */
	protected void keyTyped(char c, int i)
    {
		if (i == 1)
        {
            mc.displayGuiScreen(null);
            mc.setIngameFocus();
        }
       textfield3.textboxKeyTyped(c, i);
       tile.setTextField3(textfield3.getText());
    }
    
	/**
     * Draws the screen and all the components in it.
     */
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
    
	/**
     * Draw the background layer for the GuiContainer (everything behind the items)
     */
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
