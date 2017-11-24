package net.minecraft.src;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

public class GuiSculpt extends GuiScreen
{
	private int xSize = 216, ySize = 164;
	TileEntity_statue testatue;
	World monde;
	private int x, y, z;
	private GuiTextField textfield, textfield2;
	
	//Params world and i,j,k are the specified world and x,y,z position of the statue ; used to play a sound by making a choice in the GUI, but not still used
	public GuiSculpt(TileEntity_statue par1, World par2, int i, int j, int k)
	{
		testatue = par1;
		monde = par2;
		x = i;
		y = j;
		z = k;
	}

	public void initGui()
    {
		controlList.clear();
		int centrageX = (width - xSize) / 2;
		int centrageY = (height - ySize) / 2;
		int firstButtonY = 24;
		controlList.add(new GuiTinyButton(100, centrageX+152, centrageY+130, 40, "Back"));
		controlList.add(new GuiTinyButton(10, centrageX+148, centrageY+firstButtonY, 44, "Nothing"));
		controlList.add(new GuiTinyButton(11, centrageX+24, centrageY+firstButtonY, 44, "Steve"));
		controlList.add(new GuiTinyButton(12, centrageX+24, centrageY+firstButtonY+11, 44, "Zombie"));
		controlList.add(new GuiTinyButton(13, centrageX+24, centrageY+firstButtonY+106, 44, "Skin"));
		controlList.add(new GuiTinyButton(14, centrageX+24, centrageY+firstButtonY+95, 44, "URL"));
		textfield = new GuiTextField(fontRenderer, centrageX+70, centrageY+firstButtonY+106, 80, 10);
		textfield.setMaxStringLength(30); //skin
		textfield.setText(testatue.getTextField1());
		textfield2 = new GuiTextField(fontRenderer, centrageX+70, centrageY+firstButtonY+95, 121, 10);
		textfield2.setMaxStringLength(500); //url
		textfield2.setText(testatue.getTextField2());
		controlList.add(new GuiTinyButton(15, centrageX+24, centrageY+firstButtonY+22, 44, "Creeper"));
		controlList.add(new GuiTinyButton(16, centrageX+24, centrageY+firstButtonY+44, 44, "Cross"));
		controlList.add(new GuiTinyButton(17, centrageX+24, centrageY+firstButtonY+33, 44, "Skeleton"));
    }

	public boolean doesGuiPauseGame()
	{
		return true;
	}
	
	protected void mouseClicked(int i, int j, int k)
    {
        super.mouseClicked(i, j, k);
        textfield.mouseClicked(i, j, k);
        textfield2.mouseClicked(i, j, k);
    }
	
	protected void keyTyped(char c, int i)
    {
       super.keyTyped(c, i);
        textfield.func_50037_a(c, i);
        textfield2.func_50037_a(c, i);
        testatue.setTextField2(textfield2.getText());
        testatue.setTextField1(textfield.getText());
    }
	
	public void drawScreen(int i, int j, float f)
    {
        GL11.glDisable(2896 /*GL_LIGHTING*/);
        GL11.glDisable(2929 /*GL_DEPTH_TEST*/);
        GL11.glColor3f(1.0F, 1.0F, 1.0F);
        drawDefaultBackground();
        drawBackgroundImage();
        textfield.drawTextBox();
        textfield2.drawTextBox();
        fontRenderer.drawString("Sculpt ", ((width - xSize)/2)+55, ((height - ySize)/2)+13, 0x808080);
        super.drawScreen(i, j, f);
        GL11.glEnable(2896 /*GL_LIGHTING*/);
        GL11.glEnable(2929 /*GL_DEPTH_TEST*/);
    }
   
   protected void drawBackgroundImage()
   {
      int displayX = (width - xSize)/2;
      int displayY = (height - ySize)/2;
      
      int imgId = mc.renderEngine.getTexture("/gui/sculpt.png");
                mc.renderEngine.bindTexture(imgId);
                drawTexturedModalRect(displayX, displayY, 0, 0, xSize, ySize);
   }
    
    public void actionPerformed(GuiButton button)
    {
    	switch (button.id)
    	{
    	case 100:
    		mc.displayGuiScreen(null);
    	break;
    	case 10:
    		testatue.setButtonValue(1);
    	break;
    	case 11:
    		testatue.setButtonValue(2);
    	break;
    	case 12:
    		testatue.setButtonValue(3);
    		// This is to play a sound by making a choice, but as it's a test, it's commented (using fields world, x, y, z)
    		//monde.playSoundEffect((double)x + 0.5D, (double)y + 0.5D, (double)z + 0.5D, "mob.zombie", 1.0F, monde.rand.nextFloat() * 0.1F + 0.9F);
    	break;
    	case 13:
    		testatue.setTextField1(textfield.getText());
    		testatue.setButtonValue(4);
    	break;
    	case 14:
    		testatue.setTextField2(textfield2.getText());
    		testatue.setButtonValue(5);
    	break;
    	case 15:
    		testatue.setButtonValue(6);
    	break;
    	case 16:
    		testatue.setButtonValue(7);
    	break;
    	case 17:
    		testatue.setButtonValue(8);
    	break;
    	}
    }

}
