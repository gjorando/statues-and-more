package net.minecraft.src;


import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

public class Render_statue extends TileEntitySpecialRenderer
{
	private Model_statue statue;
	private Model_armor inner;
	private Model_armor outer;
	private Model_Screeper creeper;
	private Model_Acreeper armorcreep;
	private Model_croix croix;
	private Model_massif massif;
	private Model_skeleton skeleton;
	private Model_panneau panneau;
    private static String armorArray[];
	
	   public Render_statue()
	   {
	      statue = new Model_statue();
	      inner = new Model_armor(0.5F);
	      outer = new Model_armor(1F);
	      creeper = new Model_Screeper();
	      armorcreep = new Model_Acreeper(0.5F);
	      croix = new Model_croix();
	      massif = new Model_massif();	
	      skeleton = new Model_skeleton();
	      panneau = new Model_panneau();
	   }
	    
	    public void renderAModelAt(TileEntity_statue  tileentity1, double d, double d1, double d2, float f)
        {   
          int i = 0;
         
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
          
           Block block = tileentity1.getBlockType();
           i = tileentity1.getBlockMetadata(); 
           GL11.glPushMatrix();
           GL11.glTranslatef((float) d + 0.5F, (float) d1 +1.5F, (float) d2 +0.5F );
           if (i == 0)
           {
               GL11.glRotatef(0, 0.0F, 1F, 0.0F);
               GL11.glRotatef(180, 0.0F, 0.0F, 1F);
           }

           if (i == 1)
           {
               GL11.glRotatef(90F, 0.0F, 1.0F, 0.0F);
               GL11.glRotatef(180F, 1.0F, 0.0F, 0.0F);
           }

           if (i == 2)
           {
               GL11.glRotatef(180F, 0.0F, 1.0F, 0.0F);
               GL11.glRotatef(180F, 0.0F, 0F, 1.0F);
           }

           if (i == 3)
           {
               GL11.glRotatef(270F, 0.0F, 1.0F, 0.0F);
               GL11.glRotatef(180F, 1.0F, 0F, 0.0F);
           }
           
           boolean step;
       	   if(tileentity1.getStackInSlot(4) == null)
       	   {
       		   step = false;
       	   }
       	   else
       	   {
       		   step = true;
       	   }
           
           for (int iFor = -1; iFor <= 4; iFor++)
           {
               Model_statue modelstatue;
               Model_armor modelarmor;
               Model_Screeper modelcreeper;
           	   Model_Acreeper modelarmorcreep;
           	   Model_croix modelcroix;
           	   Model_massif modelmassif;
           	   Model_skeleton modelskeleton;
           	   Model_panneau modelpanneau;
               
           	   int button = tileentity1.getButtonValue();
           	   
               if (iFor == -1)
               {
            	   
            	   if(button == 4)
            	   {
            		   tileentity1.getMc().renderEngine.obtainImageData(tileentity1.getSkinURL(), new ImageBufferDownload());
            	   }
            	   else if(button == 5)
            	   {
            		   tileentity1.getMc().renderEngine.obtainImageData(tileentity1.getTextField2(), new ImageBufferDownload());
            	   }
            	   
            	   int valeur = tileentity1.getSkin();
            	   
            	   GL11.glBindTexture(GL11.GL_TEXTURE_2D, valeur);
            	   
            	   if(button == 0)
            	   {
            		   modelmassif = massif;
            		   modelmassif.renderModel(0.0625F);
            	   }
            	   if(button == 2 || button == 3 || button == 4 || button == 5)
            	   {
            		   modelstatue = statue;
            		   modelstatue.stepExists(step);
	                   modelstatue.renderModel(0.0625F);
            	   }
            	   else if(button == 6)
            	   {
            		   modelcreeper = creeper;
            		   modelcreeper.stepExists(step);
            		   modelcreeper.renderModel(0.0625F);
            	   }
            	   else if(button == 7)
            	   {
            		   modelcroix = croix;
            		   modelcroix.stepExists(step);
            		   modelcroix.renderModel(0.0625F);
            	   }
            	   else if(button == 8)
            	   {
            		   modelskeleton = skeleton;
            		   modelskeleton.stepExists(step);
            		   modelskeleton.renderModel(0.0625F);
            	   }
               }
               else if (iFor == 0 || iFor == 1 || iFor == 2 || iFor == 3)
               {
            	   ItemStack itemstack = tileentity1.getStackInSlot(iFor);
            	   
                   if (itemstack == null || !(Item.itemsList[itemstack.itemID] instanceof ItemArmor))
                   {
                       continue;
                   }

                   ItemArmor itemarmor = (ItemArmor)Item.itemsList[itemstack.itemID];
                   int k = itemarmor.armorType;
                   bindTextureByName((new StringBuilder("/armor/")).append(armorArray[itemarmor.renderIndex]).append("_").append(k != 2 ? 1 : 2).append(".png").toString());
                   if(button == 1 || button == 2 || button == 3 || button == 4 || button == 5 || button == 7 || button == 8)
                   {
	                   modelarmor = k != 2 ? outer : inner;
	                   modelarmor.tete.showModel = k == 0;
	                   modelarmor.corps.showModel = k == 1 || k == 2;
	                   modelarmor.bras_droit.showModel = k == 1;
	                   modelarmor.bras_gauche.showModel = k == 1;
	                   modelarmor.jambe_droite.showModel = k == 2 || k == 3;
	                   modelarmor.jambe_gauche.showModel = k == 2 || k == 3;//*/
	                   if(button == 7)
	                   {
	                	   modelarmor.tete.rotateAngleX = 0.3F;
	                   }
	                   else
	                   {
	                	   modelarmor.tete.rotateAngleX = 0F;
	                   }
	                   modelarmor.stepExists(step);
	                   modelarmor.renderModel(0.0625F);
	                   
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
	                   modelarmorcreep.stepExists(step);
                	   modelarmorcreep.renderModel(0.0625F);
                   }
               }
               else if(iFor == 4 && tileentity1.getTextField3().length() != 0)
               {
            	   modelpanneau = panneau;
            	   modelpanneau.stick.showModel = !step;
            	   modelpanneau.stick2.showModel = !step;
            	   bindTextureByName("/dolfinsbizou/sign.png");
            	   modelpanneau.renderModel(0.0625F);
               }
           }
           GL11.glPushMatrix();
           ItemStack slotHand = tileentity1.getStackInSlot(6);
           if (slotHand != null)
           {
	           int slotID = slotHand.itemID;
	           this.renderItem(tileentity1, tileentity1.getMc().thePlayer, slotHand, 0);
           }
           GL11.glPopMatrix();
           FontRenderer fontrenderer = getFontRenderer();
           int size = 11, pixel = 5*16;
           float f2 = 0.7777777F;
           float fact = (f2*0.01666667F);
           String text = tileentity1.getTextField3();
           GL11.glTranslatef(-0.5F, 1.2F, -0.570F);
           GL11.glScalef(fact, fact, fact);
           GL11.glNormal3f(0.0F, 0.0F, -1F * fact);
           GL11.glDepthMask(false);
           GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
           int color = tileentity1.getTextColor();
           fontrenderer.drawString(text, (pixel - fontrenderer.getStringWidth(text)) / 2, 0, color);
           GL11.glDepthMask(true);
           GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
           GL11.glPopMatrix();
           
        }
	   
	    public void renderItem(TileEntity_statue tile, EntityLiving par1EntityLiving, ItemStack par2ItemStack, int par3)
	    {
	        GL11.glPushMatrix();
	        RenderBlocks renderBlocksInstance = new RenderBlocks();
	        float factor, factor3D, factorBlock;
	           if(tile.getStackInSlot(4) != null)
	           {
	        	   factor = 0F;
	        	   factor3D = 0F;
	        	   factorBlock = 1.7F;
	           }
	           else
	           {
	        	   factor = 1.4F;
	        	   factor3D = 0.55F;
	        	   factorBlock = 0F;
	           }
	           
	        if (par2ItemStack.itemID < 256 && RenderBlocks.renderItemIn3d(Block.blocksList[par2ItemStack.itemID].getRenderType()))
	        {
	            GL11.glBindTexture(GL11.GL_TEXTURE_2D, tile.getMc().renderEngine.getTexture("/terrain.png"));
	            float f6 = 0.3F;
	            GL11.glScalef(f6, f6, f6);
	            GL11.glTranslatef(-1.2F, 2.8F - factorBlock, -0.6F);
	            GL11.glRotatef(45F, 0.0F, 1.0F, 0.0F);
	            GL11.glRotatef(180F, 1F, 0.0F, 0F);
	            GL11.glRotatef(-10F, 0F, 0F, 1F);
	            GL11.glRotatef(10F, 1F, 0F, 0F);
	            renderBlocksInstance.renderBlockAsItem(Block.blocksList[par2ItemStack.itemID], par2ItemStack.getItemDamage(), 1.0F);
	        }
	        else
	        {
	            if (par2ItemStack.itemID < 256)
	            {
	                GL11.glBindTexture(GL11.GL_TEXTURE_2D, tile.getMc().renderEngine.getTexture("/terrain.png"));
	            }
	            else
	            {
	                GL11.glBindTexture(GL11.GL_TEXTURE_2D, tile.getMc().renderEngine.getTexture("/gui/items.png"));
	            }

	            Tessellator tessellator = Tessellator.instance;
	            int i = par1EntityLiving.getItemIcon(par2ItemStack, par3);
	            float f = ((float)((i % 16) * 16) + 0.0F) / 256F;
	            float f1 = ((float)((i % 16) * 16) + 15.99F) / 256F;
	            float f2 = ((float)((i / 16) * 16) + 0.0F) / 256F;
	            float f3 = ((float)((i / 16) * 16) + 15.99F) / 256F;
	            float f4 = 0.0F;
	            float f5 = 0.3F;
	            GL11.glEnable(GL12.GL_RESCALE_NORMAL);
	            if (Item.itemsList[par2ItemStack.itemID].isFull3D())
	            {
		            float f6 = 0.9F;
		            GL11.glScalef(f6, f6, f6);
		            GL11.glTranslatef(-0.36F, 0.9F + factor3D, -0.4F);
		            GL11.glRotatef(270F, 0.0F, 1.0F, 0.0F);
		            GL11.glRotatef(180F, 1F, 0.0F, 0F);
		            GL11.glRotatef(45F, 0F, 0F, 1F);
	            }
	            else if(par2ItemStack.itemID == Item.bow.shiftedIndex)
	            {
	            	float f6 = 0.9F;
		            GL11.glScalef(f6, f6, f6);
		            GL11.glTranslatef(-0.46F, -0.55F + factor3D, 0F);
		            GL11.glRotatef(270F, 0.0F, 1.0F, 0.0F);
		            GL11.glRotatef(0F, 1F, 0.0F, 0F);
		            GL11.glRotatef(45F, 0F, 0F, 1F);
	            }
	            else
	            {
	            	float f6 = 0.36F;
		            GL11.glScalef(f6, f6, f6);
		            GL11.glTranslatef(-1.6F, 0.5F + factor, -0.1F);
		            GL11.glRotatef(12F, 0F, 0F, 1F);
		            GL11.glRotatef(270F, 1F, 0.0F, 0F);
	            }
	            this.renderItemIn2D(tessellator, f1, f2, f, f3);

	            if (par2ItemStack != null && par2ItemStack.hasEffect() && par3 == 0)
	            {
	                GL11.glDepthFunc(GL11.GL_EQUAL);
	                GL11.glDisable(GL11.GL_LIGHTING);
	                tile.getMc().renderEngine.bindTexture(tile.getMc().renderEngine.getTexture("%blur%/misc/glint.png"));
	                GL11.glEnable(GL11.GL_BLEND);
	                GL11.glBlendFunc(GL11.GL_SRC_COLOR, GL11.GL_ONE);
	                float f7 = 0.76F;
	                GL11.glColor4f(0.5F * f7, 0.25F * f7, 0.8F * f7, 1.0F);
	                GL11.glMatrixMode(GL11.GL_TEXTURE);
	                GL11.glPushMatrix();
	                float f8 = 0.125F;
	                GL11.glScalef(f8, f8, f8);
	                float f9 = ((float)(System.currentTimeMillis() % 3000L) / 3000F) * 8F;
	                GL11.glTranslatef(f9, 0.0F, 0.0F);
	                GL11.glRotatef(-50F, 0.0F, 0.0F, 1.0F);
	                renderItemIn2D(tessellator, 0.0F, 0.0F, 1.0F, 1.0F);
	                GL11.glPopMatrix();
	                GL11.glPushMatrix();
	                GL11.glScalef(f8, f8, f8);
	                f9 = ((float)(System.currentTimeMillis() % 4873L) / 4873F) * 8F;
	                GL11.glTranslatef(-f9, 0.0F, 0.0F);
	                GL11.glRotatef(10F, 0.0F, 0.0F, 1.0F);
	                this.renderItemIn2D(tessellator, 0.0F, 0.0F, 1.0F, 1.0F);
	                GL11.glPopMatrix();
	                GL11.glMatrixMode(GL11.GL_MODELVIEW);
	                GL11.glDisable(GL11.GL_BLEND);
	                GL11.glEnable(GL11.GL_LIGHTING);
	                GL11.glDepthFunc(GL11.GL_LEQUAL);
	            }

	            GL11.glDisable(GL12.GL_RESCALE_NORMAL);
	        }

	        GL11.glPopMatrix();
	    }

	    /**
	     * Renders an item held in hand as a 2D texture with thickness
	     */
	    private void renderItemIn2D(Tessellator par1Tessellator, float par2, float par3, float par4, float par5)
	    {
	        float f = 1.0F;
	        float f1 = 0.0625F;
	        par1Tessellator.startDrawingQuads();
	        par1Tessellator.setNormal(0.0F, 0.0F, 1.0F);
	        par1Tessellator.addVertexWithUV(0.0D, 0.0D, 0.0D, par2, par5);
	        par1Tessellator.addVertexWithUV(f, 0.0D, 0.0D, par4, par5);
	        par1Tessellator.addVertexWithUV(f, 1.0D, 0.0D, par4, par3);
	        par1Tessellator.addVertexWithUV(0.0D, 1.0D, 0.0D, par2, par3);
	        par1Tessellator.draw();
	        par1Tessellator.startDrawingQuads();
	        par1Tessellator.setNormal(0.0F, 0.0F, -1F);
	        par1Tessellator.addVertexWithUV(0.0D, 1.0D, 0.0F - f1, par2, par3);
	        par1Tessellator.addVertexWithUV(f, 1.0D, 0.0F - f1, par4, par3);
	        par1Tessellator.addVertexWithUV(f, 0.0D, 0.0F - f1, par4, par5);
	        par1Tessellator.addVertexWithUV(0.0D, 0.0D, 0.0F - f1, par2, par5);
	        par1Tessellator.draw();
	        par1Tessellator.startDrawingQuads();
	        par1Tessellator.setNormal(-1F, 0.0F, 0.0F);

	        for (int i = 0; i < 16; i++)
	        {
	            float f2 = (float)i / 16F;
	            float f6 = (par2 + (par4 - par2) * f2) - 0.001953125F;
	            float f10 = f * f2;
	            par1Tessellator.addVertexWithUV(f10, 0.0D, 0.0F - f1, f6, par5);
	            par1Tessellator.addVertexWithUV(f10, 0.0D, 0.0D, f6, par5);
	            par1Tessellator.addVertexWithUV(f10, 1.0D, 0.0D, f6, par3);
	            par1Tessellator.addVertexWithUV(f10, 1.0D, 0.0F - f1, f6, par3);
	        }

	        par1Tessellator.draw();
	        par1Tessellator.startDrawingQuads();
	        par1Tessellator.setNormal(1.0F, 0.0F, 0.0F);

	        for (int j = 0; j < 16; j++)
	        {
	            float f3 = (float)j / 16F;
	            float f7 = (par2 + (par4 - par2) * f3) - 0.001953125F;
	            float f11 = f * f3 + 0.0625F;
	            par1Tessellator.addVertexWithUV(f11, 1.0D, 0.0F - f1, f7, par3);
	            par1Tessellator.addVertexWithUV(f11, 1.0D, 0.0D, f7, par3);
	            par1Tessellator.addVertexWithUV(f11, 0.0D, 0.0D, f7, par5);
	            par1Tessellator.addVertexWithUV(f11, 0.0D, 0.0F - f1, f7, par5);
	        }

	        par1Tessellator.draw();
	        par1Tessellator.startDrawingQuads();
	        par1Tessellator.setNormal(0.0F, 1.0F, 0.0F);

	        for (int k = 0; k < 16; k++)
	        {
	            float f4 = (float)k / 16F;
	            float f8 = (par5 + (par3 - par5) * f4) - 0.001953125F;
	            float f12 = f * f4 + 0.0625F;
	            par1Tessellator.addVertexWithUV(0.0D, f12, 0.0D, par2, f8);
	            par1Tessellator.addVertexWithUV(f, f12, 0.0D, par4, f8);
	            par1Tessellator.addVertexWithUV(f, f12, 0.0F - f1, par4, f8);
	            par1Tessellator.addVertexWithUV(0.0D, f12, 0.0F - f1, par2, f8);
	        }

	        par1Tessellator.draw();
	        par1Tessellator.startDrawingQuads();
	        par1Tessellator.setNormal(0.0F, -1F, 0.0F);

	        for (int l = 0; l < 16; l++)
	        {
	            float f5 = (float)l / 16F;
	            float f9 = (par5 + (par3 - par5) * f5) - 0.001953125F;
	            float f13 = f * f5;
	            par1Tessellator.addVertexWithUV(f, f13, 0.0D, par4, f9);
	            par1Tessellator.addVertexWithUV(0.0D, f13, 0.0D, par2, f9);
	            par1Tessellator.addVertexWithUV(0.0D, f13, 0.0F - f1, par2, f9);
	            par1Tessellator.addVertexWithUV(f, f13, 0.0F - f1, par4, f9);
	        }

	        par1Tessellator.draw();
	    }
	    
	   public void renderTileEntityAt(TileEntity tileentity, double d, double d1, double d2, float f)
	   {
	      renderAModelAt((TileEntity_statue )tileentity, d, d1, d2, f);
	   }
}
