package net.minecraft.src;

import net.minecraft.client.Minecraft;
import org.lwjgl.opengl.GL11;

public class GuiStatue extends GuiContainer
{
    public final InventoryPlayer invg;
    public final TileEntity_statue tile;

    public GuiStatue(InventoryPlayer inv, TileEntity_statue testatue)
    {
        super(new ContainerStatue(inv, testatue));
        invg = inv;
        tile = testatue;
        xSize = 176;
        ySize = 184;
    }

    protected void drawGuiContainerForegroundLayer()
    {
        fontRenderer.drawString(tile.getInvName(), 48, -7, 0x808080);
    }


    public void drawScreen(int i, int j, float f)
    {
        super.drawScreen(i, j, f);
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
