package net.minecraft.src;


import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

public class Render_showcase extends TileEntitySpecialRenderer
{
	private Model_showcase showcase;
	
	   public Render_showcase()
	   {
	      showcase = new Model_showcase();
	   }
	    
	    public void renderAModelAt(TileEntity_showcase  tileentity1, double d, double d1, double d2, float f)
        {   
          int i = 0;
          
           mod_statue.showcase.setBlockBounds(0F, 0F, 0F, 1F, 1.5F, 1F);
           
           Block block = tileentity1.getBlockType();
           i = tileentity1.getBlockMetadata(); 
           GL11.glPushMatrix();
           GL11.glDisable(GL11.GL_CULL_FACE);
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
           
           	   
           float rotateLid = tileentity1.prevLidAngle + (tileentity1.lidAngle - tileentity1.prevLidAngle) * f;

           rotateLid = 1.1F - rotateLid;
           rotateLid = 1.1F - rotateLid * rotateLid * rotateLid;
           
           int valeur = tileentity1.getMc().renderEngine.getTexture("/dolfinsbizou/showcase.png");
            	   
           GL11.glBindTexture(GL11.GL_TEXTURE_2D, valeur);
           showcase.Lid.rotateAngleX = -((rotateLid * (float)Math.PI) / 2.0F);
           showcase.renderModel(0.0625F);
           
           GL11.glPushMatrix();
           ItemStack slotHand = tileentity1.getStackInSlot(0);
           if (slotHand != null)
           {
	           int slotID = slotHand.itemID;
	           this.renderItem(tileentity1, tileentity1.getMc().thePlayer, slotHand, 0);
           }
           GL11.glPopMatrix();
           GL11.glEnable(GL11.GL_CULL_FACE);
           GL11.glPopMatrix();
           
        }
	   	    
	    /**
	     * The new renderItem, compatible with HDTextures
	     */
	    
	    //*
	    public void renderItem(TileEntity_showcase tile, EntityLiving par1EntityLiving, ItemStack par2ItemStack, int par3)
	    {
	        GL11.glPushMatrix();
			RenderBlocks renderBlocksInstance = new RenderBlocks();
	           
	        if (par2ItemStack.itemID < 256 && RenderBlocks.renderItemIn3d(Block.blocksList[par2ItemStack.itemID].getRenderType()))
	        {
	            GL11.glBindTexture(GL11.GL_TEXTURE_2D, tile.getMc().renderEngine.getTexture("/terrain.png"));
	            float f6 = 0.3F; //block
	            GL11.glScalef(f6, f6, f6);
	            GL11.glTranslatef(-0.1F, 1.65F, -0.3F);
	            GL11.glRotatef(180+23F, 1F, 0.0F, 0F);
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
	            if (i == Item.bow.iconIndex || i == 133 || i == 117 || i == 101)
        		{
	            	i = Item.bow.iconIndex;
        		}
	            float f = ((float)((i % 16) * TailleTileRender.int_size) + 0.0F) / TailleTileRender.float_size16;
	            float f1 = ((float)((i % 16) * TailleTileRender.int_size) + TailleTileRender.float_sizeMinus0_01) / TailleTileRender.float_size16;
	            float f2 = ((float)((i / 16) * TailleTileRender.int_size) + 0.0F) / TailleTileRender.float_size16;
	            float f3 = ((float)((i / 16) * TailleTileRender.int_size) + TailleTileRender.float_sizeMinus0_01) / TailleTileRender.float_size16;
	            float f4 = 0.0F;
	            float f5 = 0.3F;
	            GL11.glEnable(GL12.GL_RESCALE_NORMAL);
	            /*
				if (Item.itemsList[par2ItemStack.itemID] instanceof ItemHoe || Item.itemsList[par2ItemStack.itemID] instanceof ItemAxe)
	            { //this is to correctly orient hoes and axes
	            	float f6 = 0.9F;
		            GL11.glScalef(f6, f6, f6);
		            GL11.glTranslatef(-0.36F, -0.45F, -0.4F);
		            GL11.glRotatef(270F, 0.0F, 1.0F, 0.0F);
		            GL11.glRotatef(0F, 1F, 0.0F, 0F);
		            GL11.glRotatef(45F, 0F, 0F, 1F);
	            }
	            else//*/
	            	if (Item.itemsList[par2ItemStack.itemID].isFull3D()) //items 3D
	            {
		            float f6 = 0.9F;
		            GL11.glScalef(f6, f6, f6);
		            GL11.glTranslatef(0F, 1F, -0.7F);
		            GL11.glRotatef(-67F, 1F, 0F, 0F);
		            GL11.glRotatef(225F, 0F, 0F, 1F);
	            }
	            else if(par2ItemStack.itemID == Item.bow.shiftedIndex || par2ItemStack.itemID == Item.arrow.shiftedIndex)
	            { //this is to correctly orient bow/arrow
	            	float f6 = 0.9F;
		            GL11.glScalef(f6, f6, f6);
		            GL11.glScalef(f6, f6, f6);
		            GL11.glTranslatef(0F, 1F, -0.7F);
		            GL11.glRotatef(-67F, 1F, 0F, 0F);
		            GL11.glRotatef(225F, 0F, 0F, 1F);
	            }
	            else // item 2D
	            {
	            	float f6 = 0.36F;
		            GL11.glScalef(f6, f6, f6);
		            GL11.glTranslatef(-0.4F, 1.9F, -0.12F);
		            GL11.glRotatef(180F, 1F, 0.0F, 0F);
	            }
	            renderItemIn2D(tessellator, f1, f2, f, f3);

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
	                renderItemIn2D(tessellator, 0.0F, 0.0F, 1.0F, 1.0F);
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

	        for (int i = 0; i < TailleTileRender.int_size; i++)
	        {
	            float f2 = (float)i / TailleTileRender.float_size;
	            float f6 = (par2 + (par4 - par2) * f2) - TailleTileRender.float_texNudge;
	            float f10 = f * f2;
	            par1Tessellator.addVertexWithUV(f10, 0.0D, 0.0F - f1, f6, par5);
	            par1Tessellator.addVertexWithUV(f10, 0.0D, 0.0D, f6, par5);
	            par1Tessellator.addVertexWithUV(f10, 1.0D, 0.0D, f6, par3);
	            par1Tessellator.addVertexWithUV(f10, 1.0D, 0.0F - f1, f6, par3);
	        }

	        par1Tessellator.draw();
	        par1Tessellator.startDrawingQuads();
	        par1Tessellator.setNormal(1.0F, 0.0F, 0.0F);

	        for (int j = 0; j < TailleTileRender.int_size; j++)
	        {
	            float f3 = (float)j / TailleTileRender.float_size;
	            float f7 = (par2 + (par4 - par2) * f3) - TailleTileRender.float_texNudge;
	            float f11 = f * f3 + TailleTileRender.float_reciprocal;
	            par1Tessellator.addVertexWithUV(f11, 1.0D, 0.0F - f1, f7, par3);
	            par1Tessellator.addVertexWithUV(f11, 1.0D, 0.0D, f7, par3);
	            par1Tessellator.addVertexWithUV(f11, 0.0D, 0.0D, f7, par5);
	            par1Tessellator.addVertexWithUV(f11, 0.0D, 0.0F - f1, f7, par5);
	        }

	        par1Tessellator.draw();
	        par1Tessellator.startDrawingQuads();
	        par1Tessellator.setNormal(0.0F, 1.0F, 0.0F);

	        for (int k = 0; k < TailleTileRender.int_size; k++)
	        {
	            float f4 = (float)k / TailleTileRender.float_size;
	            float f8 = (par5 + (par3 - par5) * f4) - TailleTileRender.float_texNudge;
	            float f12 = f * f4 + TailleTileRender.float_reciprocal;
	            par1Tessellator.addVertexWithUV(0.0D, f12, 0.0D, par2, f8);
	            par1Tessellator.addVertexWithUV(f, f12, 0.0D, par4, f8);
	            par1Tessellator.addVertexWithUV(f, f12, 0.0F - f1, par4, f8);
	            par1Tessellator.addVertexWithUV(0.0D, f12, 0.0F - f1, par2, f8);
	        }

	        par1Tessellator.draw();
	        par1Tessellator.startDrawingQuads();
	        par1Tessellator.setNormal(0.0F, -1F, 0.0F);

	        for (int l = 0; l < TailleTileRender.int_size; l++)
	        {
	            float f5 = (float)l / TailleTileRender.float_size;
	            float f9 = (par5 + (par3 - par5) * f5) - TailleTileRender.float_texNudge;
	            float f13 = f * f5;
	            par1Tessellator.addVertexWithUV(f, f13, 0.0D, par4, f9);
	            par1Tessellator.addVertexWithUV(0.0D, f13, 0.0D, par2, f9);
	            par1Tessellator.addVertexWithUV(0.0D, f13, 0.0F - f1, par2, f9);
	            par1Tessellator.addVertexWithUV(f, f13, 0.0F - f1, par4, f9);
	        }

	        par1Tessellator.draw();
	    }//*/
	    
	    
	   public void renderTileEntityAt(TileEntity tileentity, double d, double d1, double d2, float f)
	   {
	      renderAModelAt((TileEntity_showcase )tileentity, d, d1, d2, f);
	   }
}
