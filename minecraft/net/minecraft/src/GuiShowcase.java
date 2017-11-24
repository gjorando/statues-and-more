package net.minecraft.src;

import net.minecraft.client.Minecraft;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

public class GuiShowcase extends GuiContainer
{
    public final InventoryPlayer invg;
    public final TileEntity_showcase tile;
    World monde;
	private int x, y, z;
	
    public GuiShowcase(InventoryPlayer inv, TileEntity_showcase teshowcase, World par2, int i, int j, int k)
    {
        super(new ContainerShowcase(inv, teshowcase));
        invg = inv;
        tile = teshowcase;
        xSize = 256;
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
        fontRenderer.drawString(tile.getInvName(), 72, -7, 0x808080);
        GL11.glEnable(2896 /*GL_LIGHTING*/);
        GL11.glEnable(2929 /*GL_DEPTH_TEST*/);
    }
    
    protected void keyTyped(char c, int i)
    {
    	if (i == 1 || i == mc.gameSettings.keyBindInventory.keyCode)
        {
            mc.thePlayer.closeScreen();
            tile.numUsingPlayers--;
        }
    }
    
	public void drawScreen(int i, int j, float f)
    {
        super.drawScreen(i, j, f);
        
        GL11.glDisable(2896 /*GL_LIGHTING*/);
        GL11.glDisable(2929 /*GL_DEPTH_TEST*/);
        GL11.glColor3f(1.0F, 1.0F, 1.0F);
        GL11.glEnable(2896 /*GL_LIGHTING*/);
        GL11.glEnable(2929 /*GL_DEPTH_TEST*/);
    }
    
    protected void drawGuiContainerBackgroundLayer(float f, int i, int j)
    {   
        int k = mc.renderEngine.getTexture("/gui/showcase.png");
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        mc.renderEngine.bindTexture(k);
        int l = (width - xSize) / 2;
        int i1 = (height - ySize) / 2;
        drawTexturedModalRect(l, i1, 0, 0, xSize, ySize);
    }
}
