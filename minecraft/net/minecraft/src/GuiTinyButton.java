    package net.minecraft.src;
     
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
            boolean flag = par2 >= xPosition && par3 >= yPosition && par2 < xPosition + field_52008_a && par3 < yPosition + field_52007_b;
            int i = 0;
     
            if (flag)
            {
                i += field_52007_b;
            }
     
            drawTexturedModalRect(xPosition, yPosition, 0, i, field_52008_a, field_52007_b);
            drawTexturedModalRect(xPosition + field_52008_a / 2, yPosition, 200 - field_52008_a / 2 , i, field_52008_a / 2, field_52007_b);
            
            int j = 0xe0e0e0;

            if (!enabled)
            {
                j = 0xffa0a0a0;
            }
            else if (flag)
            {
                j = 0xffffa0;
            }
            
            drawCenteredString(fontrenderer, displayString, xPosition + field_52008_a / 2, yPosition + (field_52007_b - 8) / 2, j);
        }
    }
