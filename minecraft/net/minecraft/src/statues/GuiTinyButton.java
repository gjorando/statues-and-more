/**
 * GUI button class for the tiny button (eg. used in the statue)
 */

package net.minecraft.src.statues;

import net.minecraft.src.*;
import net.minecraft.client.Minecraft;
import org.lwjgl.opengl.GL11;

public class GuiTinyButton extends GuiButton
{
	private int longueur = 80;
	
	public GuiTinyButton(int idBouton, int posX, int posY, int tailleX, String contenu)
	{
	super(idBouton, posX, posY, tailleX, 10, contenu);
	longueur = tailleX;
	displayString = contenu;
	}
    
	/**
	 * Draws this button to the screen.
	 */
    public void drawButton(Minecraft par1Minecraft, int par2, int par3)
    {
    	FontRenderer fontrenderer = par1Minecraft.fontRenderer;
    	
        if (!drawButton)
        {
            return;
        }
 
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, par1Minecraft.renderEngine.getTexture("/gui/TinyButtonGui.png"));
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        boolean flag = par2 >= xPosition && par3 >= yPosition && par2 < xPosition + width && par3 < yPosition + height;
        int i = 0;
 
        if (flag)
        {
            i += height;
        }
 
        drawTexturedModalRect(xPosition, yPosition, 0, i, width, height);
        drawTexturedModalRect(xPosition + width / 2, yPosition, 200 - width / 2 , i, width / 2, height);
        
        int j = 0xe0e0e0;

        if (!enabled)
        {
            j = 0xffa0a0a0;
        }
        else if (flag)
        {
            j = 0xffffa0;
        }
        
        drawCenteredString(fontrenderer, displayString, xPosition + width / 2, yPosition + (height - 8) / 2, j);
    }
}