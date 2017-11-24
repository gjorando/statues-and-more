package net.minecraft.src;

import org.lwjgl.opengl.GL11;

public class GuiGiantStatue extends GuiScreen
{
	private int xSize = 256, ySize = 128;
	World monde;
	private int x, y, z;
	private GuiTextField textfield;

	public GuiGiantStatue(World world, int i, int j, int k)
	{
		monde = world;
		x = i;
		y = j;
		z = k;
	}
	
	public void initGui()
    {
		controlList.clear();
		int centrageX = (width - xSize) / 2;
		int centrageY = (height - ySize) / 2;
		controlList.add(new GuiButton(100, centrageX+152, centrageY+105, 40, 20, "Done"));
		controlList.add(new GuiButton(101, centrageX+111, centrageY+105, 40, 20, "Abort"));
		textfield = new GuiTextField(fontRenderer, centrageX+70, centrageY+24, 80, 20);
		textfield.setMaxStringLength(500); //skin
		textfield.setText("player name");
    }
	
	protected void mouseClicked(int i, int j, int k)
    {
        super.mouseClicked(i, j, k);
        textfield.mouseClicked(i, j, k);
    }
	
	protected void keyTyped(char c, int i)
    {
       if (i == 1)
       {
    	   monde.setBlockWithNotify(x, y, z, 0);
           mc.displayGuiScreen(null);
           mc.setIngameFocus();
       }
       textfield.func_50037_a(c, i);
    }
	
	public void drawScreen(int i, int j, float f)
    {
        GL11.glDisable(2896 /*GL_LIGHTING*/);
        GL11.glDisable(2929 /*GL_DEPTH_TEST*/);
        GL11.glColor3f(1.0F, 1.0F, 1.0F);
        drawDefaultBackground();
        drawBackgroundImage();
        textfield.drawTextBox();
        String inv = "Name of the player skin that will be used for", prob = "Doesn't works yet, just click on \"done\"!", temp = "The textures are temporary";
        fontRenderer.drawString(inv, ((width - xSize)/2), ((height - ySize)/2)-7, 0x808080);
        fontRenderer.drawString(prob, ((width - xSize)/2) + (xSize - fontRenderer.getStringWidth(prob))/2, ((height - ySize)/2)+5, 0x000000);
        fontRenderer.drawString(temp, ((width - xSize)/2) + (xSize - fontRenderer.getStringWidth(temp))/2, ((height - ySize)/2)+15, 0x000000);
        super.drawScreen(i, j, f);
        GL11.glEnable(2896 /*GL_LIGHTING*/);
        GL11.glEnable(2929 /*GL_DEPTH_TEST*/);
    }
	
	protected void drawBackgroundImage()
   {
      int displayX = (width - xSize)/2;
      int displayY = (height - ySize)/2;
      
      int imgId = mc.renderEngine.getTexture("/gui/geant.png");
                mc.renderEngine.bindTexture(imgId);
                drawTexturedModalRect(displayX, displayY, 0, 0, xSize, ySize);
   }
	
	public void actionPerformed(GuiButton button)
    {
    	switch (button.id)
    	{
    	case 100:
    		for(int i = 1 ; i <= 8 ; i++) //generates legs
    		{
    			for(int j = 0 ; j < 12 ; j++)
    			{
	    			for(int k = 1 ; k <= 4 ; k++)
	    			{
	    				if(monde.getBlockId(x + i, y + j, z + k) == 0)
	    				{
	    					monde.setBlockWithNotify(x + i, y + j, z + k, Block.blockClay.blockID);
	    				}
	    			}
    			}
    		}
    		
    		for(int i = 1 ; i <= 8 ; i++) //generates body
    		{
    			for(int j = 0 ; j < 12 ; j++)
    			{
	    			for(int k = 1 ; k <= 4 ; k++)
	    			{
	    				if(monde.getBlockId(x + i, y + 12 + j, z + k) == 0)
	    				{
	    					monde.setBlockWithNotify(x + i, y + 12 + j, z + k, Block.blockClay.blockID);
	    				}
	    			}
    			}
    		}
    		
    		for(int i = 1 ; i <= 4 ; i++) //generates left arm
    		{
    			for(int j = 0 ; j < 12 ; j++)
    			{
	    			for(int k = 1 ; k <= 4 ; k++)
	    			{
	    				if(monde.getBlockId(x - 4 + i, y + 12 + j, z + k) == 0)
	    				{
	    					monde.setBlockWithNotify(x - 4 + i, y + 12 + j, z + k, Block.blockClay.blockID);
	    				}
	    			}
    			}
    		}
    		
    		for(int i = 1 ; i <= 4 ; i++) //generates right arm
    		{
    			for(int j = 0 ; j < 12 ; j++)
    			{
	    			for(int k = 1 ; k <= 4 ; k++)
	    			{
	    				if(monde.getBlockId(x + 8 + i, y + 12 + j, z + k) == 0)
	    				{
	    					monde.setBlockWithNotify(x + 8 + i, y + 12 + j, z + k, Block.blockClay.blockID);
	    				}
	    			}
    			}
    		}
    		
    		for(int i = 1 ; i <= 8 ; i++) //generates head
    		{
    			for(int j = 0 ; j < 8 ; j++)
    			{
	    			for(int k = 1 ; k <= 8 ; k++)
	    			{
	    				if(monde.getBlockId(x + i, y + 24 + j, z - 2 + k) == 0)
	    				{
	    					monde.setBlockWithNotify(x + i, y + 24 + j, z - 2 + k, Block.blockClay.blockID);
	    				}
	    			}
    			}
    		}
    		
    		mc.displayGuiScreen(null);
    	break;
    	case 101:
    		mc.displayGuiScreen(null);
    	break;
    	}
    	monde.setBlockWithNotify(x, y, z, 0);
    }
}
