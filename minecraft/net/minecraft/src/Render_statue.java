package net.minecraft.src;

import org.lwjgl.opengl.GL11;

public class Render_statue extends TileEntitySpecialRenderer
{
	
	private Model_statue statue;
	
	   public Render_statue()
	   {
	      statue = new Model_statue();
	   }
	    
	    public void renderAModelAt(TileEntity_statue  tileentity1, double d, double d1, double d2, float f)
        {   
          int i = 0;
         
          Block block = tileentity1.getBlockType();
            i = tileentity1.getBlockMetadata();     
           mod_statue.statue.setBlockBounds(0F, 0F, 0F, 1F, 3F, 1F);
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
           
           bindTextureByName("/dolfinsbizou/statue.png");
           GL11.glPushMatrix();
           statue.renderModel(0.0625F);
           GL11.glPopMatrix();
           GL11.glPopMatrix();
          
        }
	   
	   public void renderTileEntityAt(TileEntity tileentity, double d, double d1, double d2, float f)
	   {
	      renderAModelAt((TileEntity_statue )tileentity, d, d1, d2, f);
	   }
}
