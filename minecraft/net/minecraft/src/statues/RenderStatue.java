/**
 * Render class for the statue
 */

package net.minecraft.src.statues;

import net.minecraft.src.*;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

public class RenderStatue extends TileEntitySpecialRenderer
{
	private ModelStatue statue;
	private ModelArmor inner;
	private ModelArmor outer;
	private ModelScreeper creeper;
	private ModelAcreeper armorcreep;
	private ModelCroix croix;
	private ModelMassif massif;
	private ModelSskeleton skeleton;
	private ModelPanneau panneau;
	private static String armorArray[];
	protected TailleTileRender tailleTile; // Used in the texture pack resolution detection
	
	public RenderStatue()
	{
		tailleTile = new TailleTileRender();
		tailleTile.setTileSize();
		
		statue = new ModelStatue();
		inner = new ModelArmor(0.5F);
		outer = new ModelArmor(1F);
		creeper = new ModelScreeper();
		armorcreep = new ModelAcreeper(0.5F);
		croix = new ModelCroix();
		massif = new ModelMassif();	
		skeleton = new ModelSskeleton();
		panneau = new ModelPanneau();
	}
	
	/**
	 * Render a model at the given coordinates
	 */
	public void renderAModelAt(TileEntityStatue  tileentity1, double d, double d1, double d2, float f)
	{   
		int i = 0;
		
		/*
		 * get the armor array of the player
		 */
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
		
		/*
		 * Set the orientation
		 */
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
		
		/*
		 * W.I.P: calculs for the future "stalker" option for the head(head-headshot)
		 */
		float f5 = (tileentity1.getBlockMetadata() * 360) / 16;
		float f6 = 0.0F;
		float f7 = 0.0F;
		
		double d3 = tileentity1.getMc().renderViewEntity.posX - ((double)tileentity1.xCoord + 0.5D);
		double d4 = tileentity1.getMc().renderViewEntity.posZ - ((double)tileentity1.zCoord + 0.5D);
		double d5 = ((double)tileentity1.yCoord + 1.46D) - (tileentity1.getMc().renderViewEntity.posY + (double)tileentity1.getMc().renderViewEntity.getEyeHeight());
		double d6 = MathHelper.sqrt_double(d3 * d3 + d4 * d4);
		f6 = (float)((Math.atan2(d4, d3) * 180D) / Math.PI) - 90F - f5;
		f7 = (float)((Math.atan2(d5, d6) * 180D) / Math.PI);
		
		/*
		 * Check if there is a block in the slab slot
		 */
		boolean step;
		if(tileentity1.getStackInSlot(4) == null)
		{
			step = false;
		}
		else
		{
			step = true;
		}
		
		int button = tileentity1.getButtonValue();
		float flag = 0.0625F;
		
		/*
		 * Get and bind texture for render
		 */
		if(button == 4)
		{
			tileentity1.getMc().renderEngine.obtainImageData(tileentity1.getSkinURL(), new ImageBufferDownload());
		}
		if(button == 5)
		{
			tileentity1.getMc().renderEngine.obtainImageData(tileentity1.getTextField2(), new ImageBufferDownload());
		}
		int valeur = tileentity1.getSkin();
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, valeur);
		
		/*
		 * Render the statue depending of the "button" value
		 */
		switch (button)
		{
		case 0: // the statue was just placed
			massif.renderModel(flag);
		break;
		case 1: // nothing but a toaster! #smosh
			// I said nothing dude
		break;
		case 2: // Steve the human
		case 3: // Léon the zombie
		case 4: // A daft player, eg. you :3
		case 5: // A naked skin url
		case 9: // A stone statue of Steve
			statue.stepExists(step);
            statue.renderModel(flag);
		break;
		case 6: // Sssssheyhan the creeper
			creeper.stepExists(step);
			creeper.renderModel(flag);
		break;
		case 7: // A stupid cross
			croix.stepExists(step);
			croix.renderModel(flag);
		break;
		case 8: // Napoleon BONE-aparte the skeleton
			skeleton.stepExists(step);
			skeleton.renderModel(flag);
		break;
		}
		
		/*
		 * Bind texture for armor on the statue based on the armorType, then render it
		 */
		for(int armorValue = 0 ; armorValue < 4 ; armorValue++)
		{
			ItemStack itemstack = tileentity1.getStackInSlot(armorValue);
			if (itemstack == null || !(Item.itemsList[itemstack.itemID] instanceof ItemArmor))
			{
				continue;
			}
			
			ItemArmor itemarmor = (ItemArmor)Item.itemsList[itemstack.itemID];
			int k = itemarmor.armorType;
			bindTextureByName((new StringBuilder("/armor/")).append(armorArray[itemarmor.renderIndex]).append("_").append(k != 2 ? 1 : 2).append(".png").toString());
			
			/*
			 * Render the armor piece
			 */
			switch (button)
			{
			case 1: // nothing but a toaster! #smosh
			case 2: // Steve the human
			case 3: // Leon the zombie
			case 4: // A daft player, eg. you :3
			case 5: // A naked skin url
			case 7: // A stupid cross
			case 8: // Napoleon BONE-aparte the skeleton
			case 9: // A stone statue of Steve
				ModelArmor modelarmor;
				modelarmor = k != 2 ? outer : inner;
				modelarmor.tete.showModel = k == 0;
				modelarmor.corps.showModel = k == 1 || k == 2;
				modelarmor.bras_droit.showModel = k == 1;
				modelarmor.bras_gauche.showModel = k == 1;
				modelarmor.jambe_droite.showModel = k == 2 || k == 3;
				modelarmor.jambe_gauche.showModel = k == 2 || k == 3;
				
				if(button == 7)
				{
					modelarmor.tete.rotateAngleX = 0.3F;
				}
				else
				{
					modelarmor.tete.rotateAngleX = 0F;
				}
				
				modelarmor.stepExists(step);
				modelarmor.renderModel(flag);
			break;
			case 6: // Sssssheyhan the creeper
				armorcreep.tete.showModel = k == 0;
				armorcreep.Shape1.showModel = k == 1 || k == 2;
				armorcreep.patte1.showModel = k == 2 || k == 3;
				armorcreep.patte2.showModel = k == 2 || k == 3;
				armorcreep.patte3.showModel = k == 2 || k == 3;
				armorcreep.patte4.showModel = k == 2 || k == 3;
				armorcreep.stepExists(step);
				armorcreep.renderModel(flag);
			break;
			}
		}
		
		/*
		 * Render the sign if necessary
		 */
		if(tileentity1.getTextField3().length() != 0)
		{
			panneau.stick.showModel = !step;
			panneau.stick2.showModel = !step;
			bindTextureByName("/dolfinsbizou/sign.png");
			panneau.renderModel(flag);
		}
		
		GL11.glPushMatrix();
		
		/*
		 * Render the item held by statue if necessary
		 */
		ItemStack slotHand = tileentity1.getStackInSlot(6);
		if (slotHand != null)
		{
			int slotID = slotHand.itemID;
			this.renderItem(tileentity1, tileentity1.getMc().thePlayer, slotHand, 0);
		}
		
		GL11.glPopMatrix();
		
		/*
		 * Draw the text on the sign
		 */
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
		GL11.glEnable(GL11.GL_CULL_FACE);
		GL11.glPopMatrix();  
	}
	
	/**
	 * Technical method used to render an item in the right hand of the statue 
	 */
    private void renderItem(TileEntityStatue tile, EntityLiving par1EntityLiving, ItemStack par2ItemStack, int par3)
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
            if (i == 133 || i == 117 || i == 101)
    		{
            	i = 21;
    		}
            float f = ((float)((i % 16) * tailleTile.int_size) + 0.0F) / tailleTile.float_size16;
            float f1 = ((float)((i % 16) * tailleTile.int_size) + tailleTile.float_sizeMinus0_01) / tailleTile.float_size16;
            float f2 = ((float)((i / 16) * tailleTile.int_size) + 0.0F) / tailleTile.float_size16;
            float f3 = ((float)((i / 16) * tailleTile.int_size) + tailleTile.float_sizeMinus0_01) / tailleTile.float_size16;
            float f4 = 0.0F;
            float f5 = 0.3F;
            GL11.glEnable(GL12.GL_RESCALE_NORMAL);
			if (Item.itemsList[par2ItemStack.itemID] instanceof ItemHoe || Item.itemsList[par2ItemStack.itemID] instanceof ItemAxe)
            { //this is to correctly orient hoes and axes
            	float f6 = 0.9F;
	            GL11.glScalef(f6, f6, f6);
	            GL11.glTranslatef(-0.36F, -0.45F + factor3D, -0.4F);
	            GL11.glRotatef(270F, 0.0F, 1.0F, 0.0F);
	            GL11.glRotatef(0F, 1F, 0.0F, 0F);
	            GL11.glRotatef(45F, 0F, 0F, 1F);
            }
            else if (Item.itemsList[par2ItemStack.itemID].isFull3D())
            {
	            float f6 = 0.9F;
	            GL11.glScalef(f6, f6, f6);
	            GL11.glTranslatef(-0.36F, 0.9F + factor3D, -0.4F);
	            GL11.glRotatef(270F, 0.0F, 1.0F, 0.0F);
	            GL11.glRotatef(180F, 1F, 0.0F, 0F);
	            GL11.glRotatef(45F, 0F, 0F, 1F);
            }
            else if(par2ItemStack.itemID == Item.bow.shiftedIndex || par2ItemStack.itemID == Item.arrow.shiftedIndex)
            { //this is to correctly orient bow/arrow
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

    /**
	 * Technical method used by the method private void renderItem(TileEntityStatue, EntityLiving, ItemStack, int) to render an item in 2D
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

        for (int i = 0; i < tailleTile.int_size; i++)
        {
            float f2 = (float)i / tailleTile.float_size;
            float f6 = (par2 + (par4 - par2) * f2) - tailleTile.float_texNudge;
            float f10 = f * f2;
            par1Tessellator.addVertexWithUV(f10, 0.0D, 0.0F - f1, f6, par5);
            par1Tessellator.addVertexWithUV(f10, 0.0D, 0.0D, f6, par5);
            par1Tessellator.addVertexWithUV(f10, 1.0D, 0.0D, f6, par3);
            par1Tessellator.addVertexWithUV(f10, 1.0D, 0.0F - f1, f6, par3);
        }

        par1Tessellator.draw();
        par1Tessellator.startDrawingQuads();
        par1Tessellator.setNormal(1.0F, 0.0F, 0.0F);

        for (int j = 0; j < tailleTile.int_size; j++)
        {
            float f3 = (float)j / tailleTile.float_size;
            float f7 = (par2 + (par4 - par2) * f3) - tailleTile.float_texNudge;
            float f11 = f * f3 + tailleTile.float_reciprocal;
            par1Tessellator.addVertexWithUV(f11, 1.0D, 0.0F - f1, f7, par3);
            par1Tessellator.addVertexWithUV(f11, 1.0D, 0.0D, f7, par3);
            par1Tessellator.addVertexWithUV(f11, 0.0D, 0.0D, f7, par5);
            par1Tessellator.addVertexWithUV(f11, 0.0D, 0.0F - f1, f7, par5);
        }

        par1Tessellator.draw();
        par1Tessellator.startDrawingQuads();
        par1Tessellator.setNormal(0.0F, 1.0F, 0.0F);

        for (int k = 0; k < tailleTile.int_size; k++)
        {
            float f4 = (float)k / tailleTile.float_size;
            float f8 = (par5 + (par3 - par5) * f4) - tailleTile.float_texNudge;
            float f12 = f * f4 + tailleTile.float_reciprocal;
            par1Tessellator.addVertexWithUV(0.0D, f12, 0.0D, par2, f8);
            par1Tessellator.addVertexWithUV(f, f12, 0.0D, par4, f8);
            par1Tessellator.addVertexWithUV(f, f12, 0.0F - f1, par4, f8);
            par1Tessellator.addVertexWithUV(0.0D, f12, 0.0F - f1, par2, f8);
        }

        par1Tessellator.draw();
        par1Tessellator.startDrawingQuads();
        par1Tessellator.setNormal(0.0F, -1F, 0.0F);

        for (int l = 0; l < tailleTile.int_size; l++)
        {
            float f5 = (float)l / tailleTile.float_size;
            float f9 = (par5 + (par3 - par5) * f5) - tailleTile.float_texNudge;
            float f13 = f * f5;
            par1Tessellator.addVertexWithUV(f, f13, 0.0D, par4, f9);
            par1Tessellator.addVertexWithUV(0.0D, f13, 0.0D, par2, f9);
            par1Tessellator.addVertexWithUV(0.0D, f13, 0.0F - f1, par2, f9);
            par1Tessellator.addVertexWithUV(f, f13, 0.0F - f1, par4, f9);
        }

        par1Tessellator.draw();
    }
    
    /**
	 * Render a TileEntity at the given coordinates
	 */
    public void renderTileEntityAt(TileEntity tileentity, double d, double d1, double d2, float f)
    {
    	renderAModelAt((TileEntityStatue )tileentity, d, d1, d2, f);
    }
}
