/**
 * GUI class for the giant statue generator
 */

package net.minecraft.src.statues;

import java.util.ArrayList;
import java.util.Random;
import net.minecraft.src.*;
import org.lwjgl.opengl.GL11;

public class GuiGiantStatue extends GuiScreen
{
	private int xSize = 176, ySize = 105;
	private int x, y, z;
	
	private GuiTextField textfield;
	private GuiButton buttonDone;
	private String playerName;
	private String skinURL;
	private ThreadDownloadImageData texture;
	private World monde;
	private Random rand;

	public GuiGiantStatue(World world, int i, int j, int k)
	{
		rand = new Random();
		monde = world;
		x = i;
		y = j;
		z = k;
		playerName = "";
	}
	
	/**
     * Returns true if this GUI should pause the game when it is displayed in single-player
     */
	public boolean doesGuiPauseGame()
	{
		return true;
	}
	
	/**
     * Adds the buttons (and other controls) to the screen in question.
     */
	public void initGui()
	{
		controlList.clear();
		int centrageX = (width - xSize) / 2;
		int centrageY = (height - ySize) / 2;
		controlList.add(buttonDone = new GuiButton(100, centrageX+10, centrageY+75, 35, 20, "Done"));
		buttonDone.enabled = false;
		controlList.add(new GuiButton(101, centrageX+132, centrageY+75, 35, 20, "Abort"));
		textfield = new GuiTextField(fontRenderer, centrageX+46, centrageY+75, 84, 20);
		textfield.setMaxStringLength(500); //skin
		textfield.setText("<player name>");
		playerName = textfield.getText();
	}
	
	/**
     * Called when the mouse is clicked.
     */
	protected void mouseClicked(int i, int j, int k)
	{
	    super.mouseClicked(i, j, k);
	    textfield.mouseClicked(i, j, k);
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
		textfield.textboxKeyTyped(c, i);
		playerName = textfield.getText();
	}
	
	/**
     * Draws the screen and all the components in it.
     */
	public void drawScreen(int i, int j, float f)
	{
		GL11.glDisable(2896 /*GL_LIGHTING*/);
		GL11.glDisable(2929 /*GL_DEPTH_TEST*/);
		GL11.glColor3f(1.0F, 1.0F, 1.0F);
		
		drawDefaultBackground();
		drawBackgroundImage();
		
		int displayX = (width - xSize)/2;
		int displayY = (height - ySize)/2;
		
		if (playerName != null && ((String) playerName).length() > 0)
		{
			skinURL = (new StringBuilder()).append("http://s3.amazonaws.com/MinecraftSkins/").append(playerName).append(".png").toString();
		}
		else
		{
			skinURL = "http://www.perdu.com";
		}
		
		texture = mc.renderEngine.obtainImageData(skinURL, new ImageBufferDownload());
		int idTexture = mc.renderEngine.getTextureForDownloadableImage(skinURL, "/dolfinsbizou/noweb.png");
		mc.renderEngine.bindTexture(idTexture);
		if (idTexture != mc.renderEngine.getTexture("/dolfinsbizou/noweb.png")) 
		{ 
			buttonDone.enabled = true;
		} 
		else 
		{ 
			buttonDone.enabled = false; 
		}
		
		drawImage(displayX+56, displayY+12, 0, 0, 64, 32);
		textfield.drawTextBox();
		super.drawScreen(i, j, f);
		
		GL11.glEnable(2896 /*GL_LIGHTING*/);
		GL11.glEnable(2929 /*GL_DEPTH_TEST*/);
	}
	
	
	/**
	 * Draws an image bound firstly with bindTexture(int)
	 * @param posX
	 * @param posY
	 * @param xStart
	 * @param yStart
	 * @param width
	 * @param height
	 */
	public void drawImage(int x, int y, int xDecalage, int yDecalage, int maxX, int maxY)
	{
		float fx = (float)1/maxX;
		float fy = (float)1/maxY;
		Tessellator tessellator = Tessellator.instance;
		tessellator.startDrawingQuads();
		tessellator.addVertexWithUV(x + 0, y + maxY, zLevel, (float)(xDecalage + 0) * fx, (float)(yDecalage + maxY) * fy);
		tessellator.addVertexWithUV(x + maxX, y + maxY, zLevel, (float)(xDecalage + maxX) * fx, (float)(yDecalage + maxY) * fy);
		tessellator.addVertexWithUV(x + maxX, y + 0, zLevel, (float)(xDecalage + maxX) * fx, (float)(yDecalage + 0) * fy);
		tessellator.addVertexWithUV(x + 0, y + 0, zLevel, (float)(xDecalage + 0) * fx, (float)(yDecalage + 0) * fy);
		tessellator.draw();
	}
	
	/**
	 * Draw the background of the GUI
	 */
	protected void drawBackgroundImage()
	{
		int displayX = (width - xSize)/2;
		int displayY = (height - ySize)/2;
		
		int imgId = mc.renderEngine.getTexture("/gui/geant.png");
		mc.renderEngine.bindTexture(imgId);
		drawTexturedModalRect(displayX, displayY, 0, 0, xSize, ySize);
	}
	
	/**
     * Fired when a control is clicked. This is the equivalent of ActionListener.actionPerformed(ActionEvent e).
     */
	public void actionPerformed(GuiButton button)
	{
		switch (button.id)
		{
		case 100:
			mc.displayGuiScreen(null);
			
			int u;
			Pixel2BlockHelper converter = new Pixel2BlockHelper(mod_statue.ListBlocks);
			int blockMeta = monde.getBlockMetadata(x, y, z);
	
	    	/*
			 * Head
			 */
			
			//Start of: front face head
			u = 15;
			for(int i = 1 ; i <= 8 ; i++) //generates head
			{
				int v = 15;
				for(int j = 0 ; j < 8 ; j++)
				{
	    			for(int k = 1 ; k <= 1 ; k++)
	    			{
	    				if(getBlockIdWithOrientation(i, 24 + j, - 2 + k, blockMeta) == 0)
	    				{
	    					ArrayList cloth;
	    					int id, metadata;
	    					if(texture.image != null)
	    					{
	    						cloth = converter.getNearestClothID(texture.image, u, v);
	    						id = (Integer) cloth.get(0);
	    						if(id!=-1){metadata = (Integer) cloth.get(1);}
	    						else{metadata = 0;}
	    					}
	    					else
	    					{
	    						mc.thePlayer.addChatMessage("A fatal error was encountered.");
	    						return;
	    					}
	    					
	    					if(id != -1){setBlockAndMetadataWithOrientation(i, 24 + j, - 2 + k, id, metadata, blockMeta);}
	    				}
	    			}
	    			v--;
				}
				u--;
			}
			//End of: front face head
			
			//Start of: rear face head
			u = 24;
			for(int i = 1 ; i <= 8 ; i++) //generates head
			{
				int v = 15;
				for(int j = 0 ; j < 8 ; j++)
				{
	    			for(int k = 1 ; k <= 1 ; k++)
	    			{
	    				if(getBlockIdWithOrientation(i, 24 + j, 5 + k, blockMeta) == 0)
	    				{
	    					ArrayList cloth;
	    					int id, metadata;
	    					if(texture.image != null)
	    					{
	    						cloth = converter.getNearestClothID(texture.image, u, v);
	    						id = (Integer) cloth.get(0);
	    						if(id!=-1){metadata = (Integer) cloth.get(1);}
	    						else{metadata = 0;}
	    					}
	    					else
	    					{
	    						mc.thePlayer.addChatMessage("A fatal error was encountered.");
	    						return;
	    					}
	    					
	    					if(id != -1){setBlockAndMetadataWithOrientation(i, 24 + j, 5 + k, id, metadata, blockMeta);}
	    				}
	    			}
	    			v--;
				}
				u++;
			}
			//End of: rear face head
			
			//Start of: left face head
			for(int i = 1 ; i <= 1 ; i++) //generates head
			{
				int v = 15;
				for(int j = 0 ; j < 8 ; j++)
				{
					u = 16;
	    			for(int k = 1 ; k <= 8 ; k++)
	    			{
	    				if(getBlockIdWithOrientation(i, 24 + j, - 2 + k, blockMeta) == 0)
	    				{
	    					ArrayList cloth;
	    					int id, metadata;
	    					if(texture.image != null)
	    					{
	    						cloth = converter.getNearestClothID(texture.image, u, v);
	    						id = (Integer) cloth.get(0);
	    						if(id!=-1){metadata = (Integer) cloth.get(1);}
	    						else{metadata = 0;}
	    					}
	    					else
	    					{
	    						mc.thePlayer.addChatMessage("A fatal error was encountered.");
	    						return;
	    					}
	    					
	    					if(id != -1){setBlockAndMetadataWithOrientation(i, 24 + j, - 2 + k, id, metadata, blockMeta);}
	    				}
	    				u++;
	    			}
	    			v--;
				}
			}
			//End of: left face head
			
			//Start of: right face head
			for(int i = 1 ; i <= 1 ; i++) //generates head
			{
				int v = 15;
				for(int j = 0 ; j < 8 ; j++)
				{
					u = 7;
	    			for(int k = 1 ; k <= 8 ; k++)
	    			{
	    				if(getBlockIdWithOrientation(7 + i, 24 + j, - 2 + k, blockMeta) == 0)
	    				{
	    					ArrayList cloth;
	    					int id, metadata;
	    					if(texture.image != null)
	    					{
	    						cloth = converter.getNearestClothID(texture.image, u, v);
	    						id = (Integer) cloth.get(0);
	    						if(id!=-1){metadata = (Integer) cloth.get(1);}
	    						else{metadata = 0;}
	    					}
	    					else
	    					{
	    						mc.thePlayer.addChatMessage("A fatal error was encountered.");
	    						return;
	    					}
	    					
	    					if(id != -1){setBlockAndMetadataWithOrientation(7 + i, 24 + j, - 2 + k, id, metadata, blockMeta);}
	    				}
	    				u--;
	    			}
	    			v--;
				}
			}
			//End of: right face head
			
			//Start of: top face head
			u = 15;
			for(int i = 1 ; i <= 8 ; i++) //generates head
			{
				int v = 7;
				for(int j = 1 ; j <= 1 ; j++)
				{
	    			for(int k = 0 ; k < 8 ; k++)
	    			{
	    				if(getBlockIdWithOrientation(i, 30 + j, - 1 + k, blockMeta) == 0)
	    				{
	    					ArrayList cloth;
	    					int id, metadata;
	    					if(texture.image != null)
	    					{
	    						cloth = converter.getNearestClothID(texture.image, u, v);
	    						id = (Integer) cloth.get(0);
	    						if(id!=-1){metadata = (Integer) cloth.get(1);}
	    						else{metadata = 0;}
	    					}
	    					else
	    					{
	    						mc.thePlayer.addChatMessage("A fatal error was encountered.");
	    						return;
	    					}
	    					
	    					if(id != -1){setBlockAndMetadataWithOrientation(i, 30 + j, - 1 + k, id, metadata, blockMeta);}
	    				}
	    				v--;
	    			}
				}
				u--;
			}
			//End of: top face head
			
			//Start of: bottom face head
			u = 23;
			for(int i = 1 ; i <= 8 ; i++) //generates head
			{
				int v = 0;
				for(int j = 1 ; j <= 1 ; j++)
				{
	    			for(int k = 0 ; k < 8 ; k++)
	    			{
	    				if(getBlockIdWithOrientation(i, 23 + j, - 1 + k, blockMeta) == 0)
	    				{
	    					ArrayList cloth;
	    					int id, metadata;
	    					if(texture.image != null)
	    					{
	    						cloth = converter.getNearestClothID(texture.image, u, v);
	    						id = (Integer) cloth.get(0);
	    						if(id!=-1){metadata = (Integer) cloth.get(1);}
	    						else{metadata = 0;}
	    					}
	    					else
	    					{
	    						mc.thePlayer.addChatMessage("A fatal error was encountered.");
	    						return;
	    					}
	    					
	    					if(id != -1){setBlockAndMetadataWithOrientation(i, 23 + j,- 1 + k, id, metadata, blockMeta);}
	    				}
	    				v++;
	    			}
	    		}
				u--;
			}
			//End of: bottom face head
			
			mc.thePlayer.addChatMessage("Head was successfully generated.");
			
			/*
			 * Hat
			 */
			
			//Start of: front face head
			u = 47;
			for(int i = 1 ; i <= 8 ; i++) //generates head
			{
				int v = 15;
				for(int j = 0 ; j < 8 ; j++)
				{
	    			for(int k = 1 ; k <= 1 ; k++)
	    			{
	    				
	    					ArrayList cloth;
	    					int id, metadata;
	    					if(texture.image != null)
	    					{
	    						cloth = converter.getNearestClothID(texture.image, u, v);
	    						id = (Integer) cloth.get(0);
	    						if(id!=-1){metadata = (Integer) cloth.get(1);}
	    						else{metadata = 0;}
	    					}
	    					else
	    					{
	    						mc.thePlayer.addChatMessage("A fatal error was encountered.");
	    						return;
	    					}
	    					
	    					if(id != -1){setBlockAndMetadataWithOrientation(i, 24 + j, - 2 + k, id, metadata, blockMeta);}
	    				
	    			}
	    			v--;
				}
				u--;
			}
			//End of: front face head
			
			//Start of: rear face head
			u = 56;
			for(int i = 1 ; i <= 8 ; i++) //generates head
			{
				int v = 15;
				for(int j = 0 ; j < 8 ; j++)
				{
	    			for(int k = 1 ; k <= 1 ; k++)
	    			{
	    				
	    					ArrayList cloth;
	    					int id, metadata;
	    					if(texture.image != null)
	    					{
	    						cloth = converter.getNearestClothID(texture.image, u, v);
	    						id = (Integer) cloth.get(0);
	    						if(id!=-1){metadata = (Integer) cloth.get(1);}
	    						else{metadata = 0;}
	    					}
	    					else
	    					{
	    						mc.thePlayer.addChatMessage("A fatal error was encountered.");
	    						return;
	    					}
	    					
	    					if(id != -1){setBlockAndMetadataWithOrientation(i, 24 + j, 5 + k, id, metadata, blockMeta);}
	    				
	    			}
	    			v--;
				}
				u++;
			}
			//End of: rear face head
			
			//Start of: left face head
			for(int i = 1 ; i <= 1 ; i++) //generates head
			{
				int v = 15;
				for(int j = 0 ; j < 8 ; j++)
				{
					u = 48;
	    			for(int k = 1 ; k <= 8 ; k++)
	    			{
	    				
	    					ArrayList cloth;
	    					int id, metadata;
	    					if(texture.image != null)
	    					{
	    						cloth = converter.getNearestClothID(texture.image, u, v);
	    						id = (Integer) cloth.get(0);
	    						if(id!=-1){metadata = (Integer) cloth.get(1);}
	    						else{metadata = 0;}
	    					}
	    					else
	    					{
	    						mc.thePlayer.addChatMessage("A fatal error was encountered.");
	    						return;
	    					}
	    					
	    					if(id != -1){setBlockAndMetadataWithOrientation(i, 24 + j, - 2 + k, id, metadata, blockMeta);}
	    				
	    				u++;
	    			}
	    			v--;
				}
			}
			//End of: left face head
			
			//Start of: right face head
			for(int i = 1 ; i <= 1 ; i++) //generates head
			{
				int v = 15;
				for(int j = 0 ; j < 8 ; j++)
				{
					u = 39;
	    			for(int k = 1 ; k <= 8 ; k++)
	    			{
	    				
	    					ArrayList cloth;
	    					int id, metadata;
	    					if(texture.image != null)
	    					{
	    						cloth = converter.getNearestClothID(texture.image, u, v);
	    						id = (Integer) cloth.get(0);
	    						if(id!=-1){metadata = (Integer) cloth.get(1);}
	    						else{metadata = 0;}
	    					}
	    					else
	    					{
	    						mc.thePlayer.addChatMessage("A fatal error was encountered.");
	    						return;
	    					}
	    					
	    					if(id != -1){setBlockAndMetadataWithOrientation(7 + i, 24 + j, - 2 + k, id, metadata, blockMeta);}
	    				
	    				u--;
	    			}
	    			v--;
				}
			}
			//End of: right face head
			
			//Start of: top face head
			u = 47;
			for(int i = 1 ; i <= 8 ; i++) //generates head
			{
				int v = 7;
				for(int j = 1 ; j <= 1 ; j++)
				{
	    			for(int k = 0 ; k < 8 ; k++)
	    			{
	    				
	    					ArrayList cloth;
	    					int id, metadata;
	    					if(texture.image != null)
	    					{
	    						cloth = converter.getNearestClothID(texture.image, u, v);
	    						id = (Integer) cloth.get(0);
	    						if(id!=-1){metadata = (Integer) cloth.get(1);}
	    						else{metadata = 0;}
	    					}
	    					else
	    					{
	    						mc.thePlayer.addChatMessage("A fatal error was encountered.");
	    						return;
	    					}
	    					
	    					if(id != -1){setBlockAndMetadataWithOrientation(i, 30 + j, - 1 + k, id, metadata, blockMeta);}
	    				
	    				v--;
	    			}
				}
				u--;
			}
			//End of: top face head
			
			//Start of: bottom face head
			u = 55;
			for(int i = 1 ; i <= 8 ; i++) //generates head
			{
				int v = 0;
				for(int j = 1 ; j <= 1 ; j++)
				{
	    			for(int k = 0 ; k < 8 ; k++)
	    			{
	    				
	    					ArrayList cloth;
	    					int id, metadata;
	    					if(texture.image != null)
	    					{
	    						cloth = converter.getNearestClothID(texture.image, u, v);
	    						id = (Integer) cloth.get(0);
	    						if(id!=-1){metadata = (Integer) cloth.get(1);}
	    						else{metadata = 0;}
	    					}
	    					else
	    					{
	    						mc.thePlayer.addChatMessage("A fatal error was encountered.");
	    						return;
	    					}
	    					
	    					if(id != -1){setBlockAndMetadataWithOrientation(i, 23 + j,- 1 + k, id, metadata, blockMeta);}
	    				v++;
	    			}
	    		}
				u--;
			}
			//End of: bottom face head
			
			mc.thePlayer.addChatMessage("Hat was successfully added.");
			
			/*
			 * Body
			 */
			
			
			//Start of: front face
			u = 27;
			for(int i = 1 ; i <= 8 ; i++)
			{
				int v = 31;
				for(int j = 0 ; j < 12 ; j++)
				{
	    			for(int k = 1 ; k <= 1 ; k++)
	    			{
	    				if(getBlockIdWithOrientation(i, 12 + j, k, blockMeta) == 0)
	    				{
	    					ArrayList cloth;
	    					int id, metadata;
	    					if(texture.image != null)
	    					{
	    						cloth = converter.getNearestClothID(texture.image, u, v);
	    						id = (Integer) cloth.get(0);
	    						if(id!=-1){metadata = (Integer) cloth.get(1);}
	    						else{metadata = 0;}
	    					}
	    					else
	    					{
	    						mc.thePlayer.addChatMessage("A fatal error was encountered.");
	    						return;
	    					}
	    					
	    					if(id != -1){setBlockAndMetadataWithOrientation(i, 12 + j, k, id, metadata, blockMeta);}
	    				}
	    			}
	    			v--;
				}
				u--;
			}
			//End of: front face
			
			//Start of: rear face
			u = 32;
			for(int i = 1 ; i <= 8 ; i++)
			{
				int v = 31;
				for(int j = 0 ; j < 12 ; j++)
				{
	    			for(int k = 1 ; k <= 1 ; k++)
	    			{
	    				if(getBlockIdWithOrientation(i, 12 + j, k+3, blockMeta) == 0)
	    				{
	    					ArrayList cloth;
	    					int id, metadata;
	    					if(texture.image != null)
	    					{
	    						cloth = converter.getNearestClothID(texture.image, u, v);
	    						id = (Integer) cloth.get(0);
	    						if(id!=-1){metadata = (Integer) cloth.get(1);}
	    						else{metadata = 0;}
	    					}
	    					else
	    					{
	    						mc.thePlayer.addChatMessage("A fatal error was encountered.");
	    						return;
	    					}
	    					
	    					if(id != -1){setBlockAndMetadataWithOrientation(i, 12 + j, k+3, id, metadata, blockMeta);}
	    				}
	    			}
	    			v--;
				}
				u++;
			}
			//End of: rear face
			
			//Start of: left face
			for(int i = 1 ; i <= 1 ; i++) 
			{
				int v = 31;
				for(int j = 0 ; j < 12 ; j++)
				{
					u = 28;
	    			for(int k = 1 ; k <= 4 ; k++)
	    			{
	    				if(getBlockIdWithOrientation(i, 12 + j, k, blockMeta) == 0)
	    				{
	    					ArrayList cloth;
	    					int id, metadata;
	    					if(texture.image != null)
	    					{
	    						cloth = converter.getNearestClothID(texture.image, u, v);
	    						id = (Integer) cloth.get(0);
	    						if(id!=-1){metadata = (Integer) cloth.get(1);}
	    						else{metadata = 0;}
	    					}
	    					else
	    					{
	    						mc.thePlayer.addChatMessage("A fatal error was encountered.");
	    						return;
	    					}
	    					
	    					if(id != -1){setBlockAndMetadataWithOrientation(i, 12 + j, k, id, metadata, blockMeta);}
	    				}
	    				u++;
	    			}
	    			v--;
				}
			}
			//End of: left face
			
			//Start of: right face
			for(int i = 1 ; i <= 1 ; i++)
			{
				int v = 31;
				for(int j = 0 ; j < 12 ; j++)
				{
					u = 19;
	    			for(int k = 1 ; k <= 4 ; k++)
	    			{
	    				if(getBlockIdWithOrientation(i + 7, 12 + j, k, blockMeta) == 0)
	    				{
	    					ArrayList cloth;
	    					int id, metadata;
	    					if(texture.image != null)
	    					{
	    						cloth = converter.getNearestClothID(texture.image, u, v);
	    						id = (Integer) cloth.get(0);
	    						if(id!=-1){metadata = (Integer) cloth.get(1);}
	    						else{metadata = 0;}
	    					}
	    					else
	    					{
	    						mc.thePlayer.addChatMessage("A fatal error was encountered.");
	    						return;
	    					}
	    					
	    					if(id != -1){setBlockAndMetadataWithOrientation(i + 7, 12 + j, k, id, metadata, blockMeta);}
	    				}
	    				u--;
	    			}
	    			v--;
				}
			}
			//End of: right face
			
			//Start of: top face
			u = 27;
			for(int i = 1 ; i <= 8 ; i++)
			{
				int v = 19;
				for(int j = 1 ; j <= 1 ; j++)
				{
	    			for(int k = 0 ; k < 4 ; k++)
	    			{
	    				if(getBlockIdWithOrientation(i, 22 + j, 1 + k, blockMeta) == 0)
	    				{
	    					ArrayList cloth;
	    					int id, metadata;
	    					if(texture.image != null)
	    					{
	    						cloth = converter.getNearestClothID(texture.image, u, v);
	    						id = (Integer) cloth.get(0);
	    						if(id!=-1){metadata = (Integer) cloth.get(1);}
	    						else{metadata = 0;}
	    					}
	    					else
	    					{
	    						mc.thePlayer.addChatMessage("A fatal error was encountered.");
	    						return;
	    					}
	    					
	    					if(id != -1){setBlockAndMetadataWithOrientation(i, 22 + j, 1 + k, id, metadata, blockMeta);}
	    				}
	    				v--;
	    			}
				}
				u--;
			}
			//End of: top face
			
			//Start of: bottom face
			u = 35;
			for(int i = 1 ; i <= 8 ; i++)
			{
				int v = 16;
				for(int j = 1 ; j <= 1 ; j++)
				{
	    			for(int k = 0 ; k < 4 ; k++)
	    			{
	    				if(getBlockIdWithOrientation(i, 11 + j, 1+ k, blockMeta) == 0)
	    				{
	    					ArrayList cloth;
	    					int id, metadata;
	    					if(texture.image != null)
	    					{
	    						cloth = converter.getNearestClothID(texture.image, u, v);
	    						id = (Integer) cloth.get(0);
	    						if(id!=-1){metadata = (Integer) cloth.get(1);}
	    						else{metadata = 0;}
	    					}
	    					else
	    					{
	    						mc.thePlayer.addChatMessage("A fatal error was encountered.");
	    						return;
	    					}
	    					
	    					if(id != -1){setBlockAndMetadataWithOrientation(i, 11 + j, 1 +k, id, metadata, blockMeta);}
	    				}
	    				v++;
	    			}
	    		}
				u--;
			}
			//End of: bottom face
			
			mc.thePlayer.addChatMessage("Body was successfully generated.");
			
			/*
			 * Right arm
			 */
			
			//Start of: front face
			u = 47;
			for(int i = 1 ; i <= 4 ; i++)
			{
				int v = 31;
				for(int j = 0 ; j < 12 ; j++)
				{
	    			for(int k = 1 ; k <= 1 ; k++)
	    			{
	    				if(getBlockIdWithOrientation(i + 8, 12 + j, k, blockMeta) == 0)
	    				{
	    					ArrayList cloth;
	    					int id, metadata;
	    					if(texture.image != null)
	    					{
	    						cloth = converter.getNearestClothID(texture.image, u, v);
	    						id = (Integer) cloth.get(0);
	    						if(id!=-1){metadata = (Integer) cloth.get(1);}
	    						else{metadata = 0;}
	    					}
	    					else
	    					{
	    						mc.thePlayer.addChatMessage("A fatal error was encountered.");
	    						return;
	    					}
	    					
	    					if(id != -1){setBlockAndMetadataWithOrientation(i + 8, 12 + j, k, id, metadata, blockMeta);}
	    				}
	    			}
	    			v--;
				}
				u--;
			}
			//End of: front face
			
			//Start of: rear face
			u = 52;
			for(int i = 1 ; i <= 4 ; i++)
			{
				int v = 31;
				for(int j = 0 ; j < 12 ; j++)
				{
	    			for(int k = 1 ; k <= 1 ; k++)
	    			{
	    				if(getBlockIdWithOrientation(i + 8, 12 + j, k + 3, blockMeta) == 0)
	    				{
	    					ArrayList cloth;
	    					int id, metadata;
	    					if(texture.image != null)
	    					{
	    						cloth = converter.getNearestClothID(texture.image, u, v);
	    						id = (Integer) cloth.get(0);
	    						if(id!=-1){metadata = (Integer) cloth.get(1);}
	    						else{metadata = 0;}
	    					}
	    					else
	    					{
	    						mc.thePlayer.addChatMessage("A fatal error was encountered.");
	    						return;
	    					}
	    					
	    					if(id != -1){setBlockAndMetadataWithOrientation(i + 8, 12 + j, k+3, id, metadata, blockMeta);}
	    				}
	    			}
	    			v--;
				}
				u++;
			}
			//End of: rear face
			
			//Start of: right face
			for(int i = 1 ; i <= 1 ; i++) 
			{
				int v = 31;
				for(int j = 0 ; j < 12 ; j++)
				{
					u = 43;
	    			for(int k = 1 ; k <= 4 ; k++)
	    			{
	    				if(getBlockIdWithOrientation(i + 11, 12 + j, k, blockMeta) == 0)
	    				{
	    					ArrayList cloth;
	    					int id, metadata;
	    					if(texture.image != null)
	    					{
	    						cloth = converter.getNearestClothID(texture.image, u, v);
	    						id = (Integer) cloth.get(0);
	    						if(id!=-1){metadata = (Integer) cloth.get(1);}
	    						else{metadata = 0;}
	    					}
	    					else
	    					{
	    						mc.thePlayer.addChatMessage("A fatal error was encountered.");
	    						return;
	    					}
	    					
	    					if(id != -1){setBlockAndMetadataWithOrientation(i + 11, 12 + j, k, id, metadata, blockMeta);}
	    				}
	    				u--;
	    			}
	    			v--;
				}
			}
			//End of: right face
			
			//Start of: left face
			for(int i = 1 ; i <= 1 ; i++)
			{
				int v = 31;
				for(int j = 0 ; j < 12 ; j++)
				{
					u = 48;
	    			for(int k = 1 ; k <= 4 ; k++)
	    			{
	    				if(getBlockIdWithOrientation(i + 8, 12 + j, k, blockMeta) == 0)
	    				{
	    					ArrayList cloth;
	    					int id, metadata;
	    					if(texture.image != null)
	    					{
	    						cloth = converter.getNearestClothID(texture.image, u, v);
	    						id = (Integer) cloth.get(0);
	    						if(id!=-1){metadata = (Integer) cloth.get(1);}
	    						else{metadata = 0;}
	    					}
	    					else
	    					{
	    						mc.thePlayer.addChatMessage("A fatal error was encountered.");
	    						return;
	    					}
	    					
	    					if(id != -1){setBlockAndMetadataWithOrientation(i + 8, 12 + j, k, id, metadata, blockMeta);}
	    				}
	    				u++;
	    			}
	    			v--;
				}
			}
			//End of: left face
			
			//Start of: top face
			u = 47;
			for(int i = 1 ; i <= 4 ; i++)
			{
				int v = 19;
				for(int j = 1 ; j <= 1 ; j++)
				{
	    			for(int k = 0 ; k < 4 ; k++)
	    			{
	    				if(getBlockIdWithOrientation(i + 8, 22 + j, 1 + k, blockMeta) == 0)
	    				{
	    					ArrayList cloth;
	    					int id, metadata;
	    					if(texture.image != null)
	    					{
	    						cloth = converter.getNearestClothID(texture.image, u, v);
	    						id = (Integer) cloth.get(0);
	    						if(id!=-1){metadata = (Integer) cloth.get(1);}
	    						else{metadata = 0;}
	    					}
	    					else
	    					{
	    						mc.thePlayer.addChatMessage("A fatal error was encountered.");
	    						return;
	    					}
	    					
	    					if(id != -1){setBlockAndMetadataWithOrientation(i + 8, 22 + j, 1 + k, id, metadata, blockMeta);}
	    				}
	    				v--;
	    			}
				}
				u--;
			}
			//End of: top face
			
			//Start of: bottom face
			u = 51;
			for(int i = 1 ; i <= 4 ; i++)
			{
				int v = 16;
				for(int j = 1 ; j <= 1 ; j++)
				{
	    			for(int k = 0 ; k < 4 ; k++)
	    			{
	    				if(getBlockIdWithOrientation(i + 8, 11 + j, 1 + k, blockMeta) == 0)
	    				{
	    					ArrayList cloth;
	    					int id, metadata;
	    					if(texture.image != null)
	    					{
	    						cloth = converter.getNearestClothID(texture.image, u, v);
	    						id = (Integer) cloth.get(0);
	    						if(id!=-1){metadata = (Integer) cloth.get(1);}
	    						else{metadata = 0;}
	    					}
	    					else
	    					{
	    						mc.thePlayer.addChatMessage("A fatal error was encountered.");
	    						return;
	    					}
	    					
	    					if(id != -1){setBlockAndMetadataWithOrientation(i + 8, 11 + j, 1 + k, id, metadata, blockMeta);}
	    				}
	    				v++;
	    			}
	    		}
				u--;
			}
			
			mc.thePlayer.addChatMessage("Right arm was successfully generated.");
			
			/*
			 * Left arm
			 */
			
			//Start of: front face
			u = 44;
			for(int i = 1 ; i <= 4 ; i++)
			{
				int v = 31;
				for(int j = 0 ; j < 12 ; j++)
				{
	    			for(int k = 1 ; k <= 1 ; k++)
	    			{
	    				if(getBlockIdWithOrientation(i - 4, 12 + j, k, blockMeta) == 0)
	    				{
	    					ArrayList cloth;
	    					int id, metadata;
	    					if(texture.image != null)
	    					{
	    						cloth = converter.getNearestClothID(texture.image, u, v);
	    						id = (Integer) cloth.get(0);
	    						if(id!=-1){metadata = (Integer) cloth.get(1);}
	    						else{metadata = 0;}
	    					}
	    					else
	    					{
	    						mc.thePlayer.addChatMessage("A fatal error was encountered.");
	    						return;
	    					}
	    					
	    					if(id != -1){setBlockAndMetadataWithOrientation(i - 4, 12 + j, k, id, metadata, blockMeta);}
	    				}
	    			}
	    			v--;
				}
				u++;
			}
			//End of: front face
			
			//Start of: rear face
			u = 55;
			for(int i = 1 ; i <= 4 ; i++)
			{
				int v = 31;
				for(int j = 0 ; j < 12 ; j++)
				{
	    			for(int k = 1 ; k <= 1 ; k++)
	    			{
	    				if(getBlockIdWithOrientation(i - 4, 12 + j, k + 3, blockMeta) == 0)
	    				{
	    					ArrayList cloth;
	    					int id, metadata;
	    					if(texture.image != null)
	    					{
	    						cloth = converter.getNearestClothID(texture.image, u, v);
	    						id = (Integer) cloth.get(0);
	    						if(id!=-1){metadata = (Integer) cloth.get(1);}
	    						else{metadata = 0;}
	    					}
	    					else
	    					{
	    						mc.thePlayer.addChatMessage("A fatal error was encountered.");
	    						return;
	    					}
	    					
	    					if(id != -1){setBlockAndMetadataWithOrientation(i - 4, 12 + j, k+3, id, metadata, blockMeta);}
	    				}
	    			}
	    			v--;
				}
				u--;
			}
			//End of: rear face
			
			//Start of: left face
			for(int i = 1 ; i <= 1 ; i++) 
			{
				int v = 31;
				for(int j = 0 ; j < 12 ; j++)
				{
					u = 43;
	    			for(int k = 1 ; k <= 4 ; k++)
	    			{
	    				if(getBlockIdWithOrientation(i - 4, 12 + j, k, blockMeta) == 0)
	    				{
	    					ArrayList cloth;
	    					int id, metadata;
	    					if(texture.image != null)
	    					{
	    						cloth = converter.getNearestClothID(texture.image, u, v);
	    						id = (Integer) cloth.get(0);
	    						if(id!=-1){metadata = (Integer) cloth.get(1);}
	    						else{metadata = 0;}
	    					}
	    					else
	    					{
	    						mc.thePlayer.addChatMessage("A fatal error was encountered.");
	    						return;
	    					}
	    					
	    					if(id != -1){setBlockAndMetadataWithOrientation(i - 4, 12 + j, k, id, metadata, blockMeta);}
	    				}
	    				u--;
	    			}
	    			v--;
				}
			}
			//End of: left face
			
			//Start of: right face
			for(int i = 1 ; i <= 1 ; i++)
			{
				int v = 31;
				for(int j = 0 ; j < 12 ; j++)
				{
					u = 48;
	    			for(int k = 1 ; k <= 4 ; k++)
	    			{
	    				if(getBlockIdWithOrientation(i - 1, 12 + j, k, blockMeta) == 0)
	    				{
	    					ArrayList cloth;
	    					int id, metadata;
	    					if(texture.image != null)
	    					{
	    						cloth = converter.getNearestClothID(texture.image, u, v);
	    						id = (Integer) cloth.get(0);
	    						if(id!=-1){metadata = (Integer) cloth.get(1);}
	    						else{metadata = 0;}
	    					}
	    					else
	    					{
	    						mc.thePlayer.addChatMessage("A fatal error was encountered.");
	    						return;
	    					}
	    					
	    					if(id != -1){setBlockAndMetadataWithOrientation(i - 1, 12 + j, k, id, metadata, blockMeta);}
	    				}
	    				u++;
	    			}
	    			v--;
				}
			}
			//End of: right face
			
			//Start of: top face
			u = 44;
			for(int i = 1 ; i <= 4 ; i++)
			{
				int v = 19;
				for(int j = 1 ; j <= 1 ; j++)
				{
	    			for(int k = 0 ; k < 4 ; k++)
	    			{
	    				if(getBlockIdWithOrientation(i - 4, 22 + j, 1 + k, blockMeta) == 0)
	    				{
	    					ArrayList cloth;
	    					int id, metadata;
	    					if(texture.image != null)
	    					{
	    						cloth = converter.getNearestClothID(texture.image, u, v);
	    						id = (Integer) cloth.get(0);
	    						if(id!=-1){metadata = (Integer) cloth.get(1);}
	    						else{metadata = 0;}
	    					}
	    					else
	    					{
	    						mc.thePlayer.addChatMessage("A fatal error was encountered.");
	    						return;
	    					}
	    					
	    					if(id != -1){setBlockAndMetadataWithOrientation(i - 4, 22 + j, 1 + k, id, metadata, blockMeta);}
	    				}
	    				v--;
	    			}
				}
				u++;
			}
			//End of: top face
			
			//Start of: bottom face
			u = 48;
			for(int i = 1 ; i <= 4 ; i++)
			{
				int v = 16;
				for(int j = 1 ; j <= 1 ; j++)
				{
	    			for(int k = 0 ; k < 4 ; k++)
	    			{
	    				if(getBlockIdWithOrientation(i - 4, 11 + j, 1 + k, blockMeta) == 0)
	    				{
	    					ArrayList cloth;
	    					int id, metadata;
	    					if(texture.image != null)
	    					{
	    						cloth = converter.getNearestClothID(texture.image, u, v);
	    						id = (Integer) cloth.get(0);
	    						if(id!=-1){metadata = (Integer) cloth.get(1);}
	    						else{metadata = 0;}
	    					}
	    					else
	    					{
	    						mc.thePlayer.addChatMessage("A fatal error was encountered.");
	    						return;
	    					}
	    					
	    					if(id != -1){setBlockAndMetadataWithOrientation(i - 4, 11 + j, 1 + k, id, metadata, blockMeta);}
	    				}
	    				v++;
	    			}
	    		}
				u++;
			}
			
			mc.thePlayer.addChatMessage("Left arm was successfully generated.");
			
			/*
			 * Right leg
			 */
			
			//Start of: front face
			u = 7;
			for(int i = 1 ; i <= 4 ; i++)
			{
				int v = 31;
				for(int j = 0 ; j < 12 ; j++)
				{
	    			for(int k = 1 ; k <= 1 ; k++)
	    			{
	    				if(getBlockIdWithOrientation(i + 4, j, k, blockMeta) == 0)
	    				{
	    					ArrayList cloth;
	    					int id, metadata;
	    					if(texture.image != null)
	    					{
	    						cloth = converter.getNearestClothID(texture.image, u, v);
	    						id = (Integer) cloth.get(0);
	    						if(id!=-1){metadata = (Integer) cloth.get(1);}
	    						else{metadata = 0;}
	    					}
	    					else
	    					{
	    						mc.thePlayer.addChatMessage("A fatal error was encountered.");
	    						return;
	    					}
	    					
	    					if(id != -1){setBlockAndMetadataWithOrientation(i + 4, j, k, id, metadata, blockMeta);}
	    				}
	    			}
	    			v--;
				}
				u--;
			}
			//End of: front face
			
			//Start of: rear face
			u = 12;
			for(int i = 1 ; i <= 4 ; i++)
			{
				int v = 31;
				for(int j = 0 ; j < 12 ; j++)
				{
	    			for(int k = 1 ; k <= 1 ; k++)
	    			{
	    				if(getBlockIdWithOrientation(i + 4, j, k + 3, blockMeta) == 0)
	    				{
	    					ArrayList cloth;
	    					int id, metadata;
	    					if(texture.image != null)
	    					{
	    						cloth = converter.getNearestClothID(texture.image, u, v);
	    						id = (Integer) cloth.get(0);
	    						if(id!=-1){metadata = (Integer) cloth.get(1);}
	    						else{metadata = 0;}
	    					}
	    					else
	    					{
	    						mc.thePlayer.addChatMessage("A fatal error was encountered.");
	    						return;
	    					}
	    					
	    					if(id != -1){setBlockAndMetadataWithOrientation(i + 4, j, k+3, id, metadata, blockMeta);}
	    				}
	    			}
	    			v--;
				}
				u++;
			}
			//End of: rear face
			
			//Start of: right face
			for(int i = 1 ; i <= 1 ; i++) 
			{
				int v = 31;
				for(int j = 0 ; j < 12 ; j++)
				{
					u = 3;
	    			for(int k = 1 ; k <= 4 ; k++)
	    			{
	    				if(getBlockIdWithOrientation(i + 7, j, k, blockMeta) == 0)
	    				{
	    					ArrayList cloth;
	    					int id, metadata;
	    					if(texture.image != null)
	    					{
	    						cloth = converter.getNearestClothID(texture.image, u, v);
	    						id = (Integer) cloth.get(0);
	    						if(id!=-1){metadata = (Integer) cloth.get(1);}
	    						else{metadata = 0;}
	    					}
	    					else
	    					{
	    						mc.thePlayer.addChatMessage("A fatal error was encountered.");
	    						return;
	    					}
	    					
	    					if(id != -1){setBlockAndMetadataWithOrientation(i + 7, j, k, id, metadata, blockMeta);}
	    				}
	    				u--;
	    			}
	    			v--;
				}
			}
			//End of: right face
			
			//Start of: left face
			for(int i = 1 ; i <= 1 ; i++)
			{
				int v = 31;
				for(int j = 0 ; j < 12 ; j++)
				{
					u = 8;
	    			for(int k = 1 ; k <= 4 ; k++)
	    			{
	    				if(getBlockIdWithOrientation(i + 4, j, k, blockMeta) == 0)
	    				{
	    					ArrayList cloth;
	    					int id, metadata;
	    					if(texture.image != null)
	    					{
	    						cloth = converter.getNearestClothID(texture.image, u, v);
	    						id = (Integer) cloth.get(0);
	    						if(id!=-1){metadata = (Integer) cloth.get(1);}
	    						else{metadata = 0;}
	    					}
	    					else
	    					{
	    						mc.thePlayer.addChatMessage("A fatal error was encountered.");
	    						return;
	    					}
	    					
	    					if(id != -1){setBlockAndMetadataWithOrientation(i + 4, j, k, id, metadata, blockMeta);}
	    				}
	    				u++;
	    			}
	    			v--;
				}
			}
			//End of: left face
			
			//Start of: top face
			u = 7;
			for(int i = 1 ; i <= 4 ; i++)
			{
				int v = 19;
				for(int j = 1 ; j <= 1 ; j++)
				{
	    			for(int k = 0 ; k < 4 ; k++)
	    			{
	    				if(getBlockIdWithOrientation(i + 4, 10 + j, 1 + k, blockMeta) == 0)
	    				{
	    					ArrayList cloth;
	    					int id, metadata;
	    					if(texture.image != null)
	    					{
	    						cloth = converter.getNearestClothID(texture.image, u, v);
	    						id = (Integer) cloth.get(0);
	    						if(id!=-1){metadata = (Integer) cloth.get(1);}
	    						else{metadata = 0;}
	    					}
	    					else
	    					{
	    						mc.thePlayer.addChatMessage("A fatal error was encountered.");
	    						return;
	    					}
	    					
	    					if(id != -1){setBlockAndMetadataWithOrientation(i + 4, 10 + j, 1 + k, id, metadata, blockMeta);}
	    				}
	    				v--;
	    			}
				}
				u--;
			}
			//End of: top face
			
			//Start of: bottom face
			u = 11;
			for(int i = 1 ; i <= 4 ; i++)
			{
				int v = 16;
				for(int j = 1 ; j <= 1 ; j++)
				{
	    			for(int k = 0 ; k < 4 ; k++)
	    			{
	    				if(getBlockIdWithOrientation(i + 4, j - 1, 1 + k, blockMeta) == 0)
	    				{
	    					ArrayList cloth;
	    					int id, metadata;
	    					if(texture.image != null)
	    					{
	    						cloth = converter.getNearestClothID(texture.image, u, v);
	    						id = (Integer) cloth.get(0);
	    						if(id!=-1){metadata = (Integer) cloth.get(1);}
	    						else{metadata = 0;}
	    					}
	    					else
	    					{
	    						mc.thePlayer.addChatMessage("A fatal error was encountered.");
	    						return;
	    					}
	    					
	    					if(id != -1){setBlockAndMetadataWithOrientation(i + 4, j - 1, 1 + k, id, metadata, blockMeta);}
	    				}
	    				v++;
	    			}
	    		}
				u--;
			}
			
			mc.thePlayer.addChatMessage("Right leg was successfully generated.");
			
			/*
			 * Left leg
			 */
			
			//Start of: front face
			u = 4;
			for(int i = 1 ; i <= 4 ; i++)
			{
				int v = 31;
				for(int j = 0 ; j < 12 ; j++)
				{
	    			for(int k = 1 ; k <= 1 ; k++)
	    			{
	    				if(getBlockIdWithOrientation(i, j, k, blockMeta) == 0)
	    				{
	    					ArrayList cloth;
	    					int id, metadata;
	    					if(texture.image != null)
	    					{
	    						cloth = converter.getNearestClothID(texture.image, u, v);
	    						id = (Integer) cloth.get(0);
	    						if(id!=-1){metadata = (Integer) cloth.get(1);}
	    						else{metadata = 0;}
	    					}
	    					else
	    					{
	    						mc.thePlayer.addChatMessage("A fatal error was encountered.");
	    						return;
	    					}
	    					
	    					if(id != -1){setBlockAndMetadataWithOrientation(i, j, k, id, metadata, blockMeta);}
	    				}
	    			}
	    			v--;
				}
				u++;
			}
			//End of: front face
			
			//Start of: rear face
			u = 15;
			for(int i = 1 ; i <= 4 ; i++)
			{
				int v = 31;
				for(int j = 0 ; j < 12 ; j++)
				{
	    			for(int k = 1 ; k <= 1 ; k++)
	    			{
	    				if(getBlockIdWithOrientation(i, j, k + 3, blockMeta) == 0)
	    				{
	    					ArrayList cloth;
	    					int id, metadata;
	    					if(texture.image != null)
	    					{
	    						cloth = converter.getNearestClothID(texture.image, u, v);
	    						id = (Integer) cloth.get(0);
	    						if(id!=-1){metadata = (Integer) cloth.get(1);}
	    						else{metadata = 0;}
	    					}
	    					else
	    					{
	    						mc.thePlayer.addChatMessage("A fatal error was encountered.");
	    						return;
	    					}
	    					
	    					if(id != -1){setBlockAndMetadataWithOrientation(i, j, k+3, id, metadata, blockMeta);}
	    				}
	    			}
	    			v--;
				}
				u--;
			}
			//End of: rear face
			
			//Start of: left face
			for(int i = 1 ; i <= 1 ; i++) 
			{
				int v = 31;
				for(int j = 0 ; j < 12 ; j++)
				{
					u = 3;
	    			for(int k = 1 ; k <= 4 ; k++)
	    			{
	    				if(getBlockIdWithOrientation(i, j, k, blockMeta) == 0)
	    				{
	    					ArrayList cloth;
	    					int id, metadata;
	    					if(texture.image != null)
	    					{
	    						cloth = converter.getNearestClothID(texture.image, u, v);
	    						id = (Integer) cloth.get(0);
	    						if(id!=-1){metadata = (Integer) cloth.get(1);}
	    						else{metadata = 0;}
	    					}
	    					else
	    					{
	    						mc.thePlayer.addChatMessage("A fatal error was encountered.");
	    						return;
	    					}
	    					
	    					if(id != -1){setBlockAndMetadataWithOrientation(i, j, k, id, metadata, blockMeta);}
	    				}
	    				u--;
	    			}
	    			v--;
				}
			}
			//End of: left face
			
			//Start of: right face
			for(int i = 1 ; i <= 1 ; i++)
			{
				int v = 31;
				for(int j = 0 ; j < 12 ; j++)
				{
					u = 8;
	    			for(int k = 1 ; k <= 4 ; k++)
	    			{
	    				if(getBlockIdWithOrientation(i + 3, j, k, blockMeta) == 0)
	    				{
	    					ArrayList cloth;
	    					int id, metadata;
	    					if(texture.image != null)
	    					{
	    						cloth = converter.getNearestClothID(texture.image, u, v);
	    						id = (Integer) cloth.get(0);
	    						if(id!=-1){metadata = (Integer) cloth.get(1);}
	    						else{metadata = 0;}
	    					}
	    					else
	    					{
	    						mc.thePlayer.addChatMessage("A fatal error was encountered.");
	    						return;
	    					}
	    					
	    					if(id != -1){setBlockAndMetadataWithOrientation(i + 3, j, k, id, metadata, blockMeta);}
	    				}
	    				u++;
	    			}
	    			v--;
				}
			}
			//End of: right face
			
			//Start of: top face
			u = 4;
			for(int i = 1 ; i <= 4 ; i++)
			{
				int v = 19;
				for(int j = 1 ; j <= 1 ; j++)
				{
	    			for(int k = 0 ; k < 4 ; k++)
	    			{
	    				if(getBlockIdWithOrientation(i, 10 + j, 1 + k, blockMeta) == 0)
	    				{
	    					ArrayList cloth;
	    					int id, metadata;
	    					if(texture.image != null)
	    					{
	    						cloth = converter.getNearestClothID(texture.image, u, v);
	    						id = (Integer) cloth.get(0);
	    						if(id!=-1){metadata = (Integer) cloth.get(1);}
	    						else{metadata = 0;}
	    					}
	    					else
	    					{
	    						mc.thePlayer.addChatMessage("A fatal error was encountered.");
	    						return;
	    					}
	    					
	    					if(id != -1){setBlockAndMetadataWithOrientation(i, 10 + j, 1 + k, id, metadata, blockMeta);}
	    				}
	    				v--;
	    			}
				}
				u++;
			}
			//End of: top face
			
			//Start of: bottom face
			u = 8;
			for(int i = 1 ; i <= 4 ; i++)
			{
				int v = 16;
				for(int j = 1 ; j <= 1 ; j++)
				{
	    			for(int k = 0 ; k < 4 ; k++)
	    			{
	    				if(getBlockIdWithOrientation(i, j - 1, 1 + k, blockMeta) == 0)
	    				{
	    					ArrayList cloth;
	    					int id, metadata;
	    					if(texture.image != null)
	    					{
	    						cloth = converter.getNearestClothID(texture.image, u, v);
	    						id = (Integer) cloth.get(0);
	    						if(id!=-1){metadata = (Integer) cloth.get(1);}
	    						else{metadata = 0;}
	    					}
	    					else
	    					{
	    						mc.thePlayer.addChatMessage("A fatal error was encountered.");
	    						return;
	    					}
	    					
	    					if(id != -1){setBlockAndMetadataWithOrientation(i, j - 1, 1 + k, id, metadata, blockMeta);}
	    				}
	    				v++;
	    			}
	    		}
				u++;
			}
			
			mc.thePlayer.addChatMessage("Left leg was successfully generated.");
			
			
			monde.setBlockWithNotify(x, y, z, Block.web.blockID);
			
			double d = rand.nextGaussian() * 0.02D;
		    double d1 = rand.nextGaussian() * 0.02D;
		    double d2 = rand.nextGaussian() * 0.02D;
		    for (int part = 1 ; part < 80 ; part++)
		    {
		    	monde.spawnParticle("explode", (x + (double)(rand.nextFloat() * 1.5 * 2.0F)) - (double)1.5, y + (double)(rand.nextFloat() * 1.5), (z + (double)(rand.nextFloat() * 1.5 * 2.0F)) - (double)1.5, d, d1, d2);
		    }
		break;
		case 101:
			mc.displayGuiScreen(null);
		break;
		}
	}
	
	/**
	 * set a block and metadata based on the pos/height/offset and normal orientation
	 * @param par2pos: pos
	 * @param par3height: height
	 * @param par4offset: offset
	 * @param par5blockID: block id
	 * @param par6blockMetadata: block metadata
	 * @param par7dir: normal orientation
	 */
	public void setBlockAndMetadataWithOrientation(int par2pos, int par3height, int par4offset, int par5blockID, int par6blockMetadata, int par7dir)
	{
        switch(par7dir)
        {
        case 0:
            monde.setBlockAndMetadataWithNotify(x+par2pos, y+par3height, z+par4offset, par5blockID, par6blockMetadata);
            break;
        case 2:
    		monde.setBlockAndMetadataWithNotify(x-par2pos, y+par3height, z-par4offset, par5blockID, par6blockMetadata);
            break;
        case 1:
    		monde.setBlockAndMetadataWithNotify(x-par4offset, y+par3height, z+par2pos, par5blockID, par6blockMetadata);
            break;
        case 3:
    		monde.setBlockAndMetadataWithNotify(x+par4offset, y+par3height, z-par2pos, par5blockID, par6blockMetadata);
            break;
        }
	}
	
	/**
	 * get a block id based on the pos/height/offset and normal orientation
	 * @param par1pos: pos
	 * @param par3height: height
	 * @param par4offset: offset
	 * @param par7dir: normal orientation
	 * @return the block id
	 */
	public int getBlockIdWithOrientation(int par1pos, int par3height, int par4offset, int par7dir)
	{
		switch(par7dir)
	    {
	    case 0:
            return monde.getBlockId(x+par1pos, y+par3height, z+par4offset);
	    case 2:
    		return monde.getBlockId(x-par1pos, y+par3height, z-par4offset);
	    case 1:
    		return monde.getBlockId(x-par4offset, y+par3height, z+par1pos);
	    case 3:
    		return monde.getBlockId(x+par4offset, y+par3height, z-par1pos);
	    }
		return 0;
	}
}
