package net.minecraft.src;


import org.lwjgl.opengl.GL11;

public class Render_statue extends TileEntitySpecialRenderer
{
	private Model_statue statue;
	private Model_armor inner;
	private Model_armor outer;
    private static String armorArray[];

	
	   public Render_statue()
	   {
	      statue = new Model_statue();
	      inner = new Model_armor(0.5F);
	      outer = new Model_armor(0.8F);
	   }
	    
	    public void renderAModelAt(TileEntity_statue  tileentity1, double d, double d1, double d2, float f)
        {   
          int i = 0;
          float f5 = (tileentity1.getBlockMetadata() * 360) / 16;
         
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
           
           for (int iFor = -1; iFor < 4; iFor++)
           {
               Model_statue modelstatue;
               Model_armor modelarmor;
               
               if (iFor == -1)
               {
            	   bindTextureByName("/dolfinsbizou/statue.png");
                   modelstatue = statue;
                   modelstatue.renderModel(0.0625F);
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
                   
                   /*
                   if(k != 2){modelarmor = outer;}else{modelarmor = inner;}
                   if(k == 0){modelarmor.tete.showModel = true;}
                   if(k == 1 || k == 2){modelarmor.corps.showModel = true;}
                   if(k == 1){modelarmor.bras_droit.showModel = true; modelarmor.bras_gauche.showModel = true;}
                   if(k == 2 || k == 3){modelarmor.jambe_droite.showModel = true; modelarmor.jambe_gauche.showModel = true;}//*/
                   //*
                   modelarmor = k != 2 ? outer : inner;
                   modelarmor.tete.showModel = k == 0;
                   modelarmor.corps.showModel = k == 1 || k == 2;
                   modelarmor.bras_droit.showModel = k == 1;
                   modelarmor.bras_gauche.showModel = k == 1;
                   modelarmor.jambe_droite.showModel = k == 2 || k == 3;
                   modelarmor.jambe_gauche.showModel = k == 2 || k == 3;//*/
                   modelarmor.renderModel(0.0625F);
               }

           }
           GL11.glPushMatrix();
           GL11.glPopMatrix();
           GL11.glPopMatrix();
           
        }
	   
	   public void renderTileEntityAt(TileEntity tileentity, double d, double d1, double d2, float f)
	   {
	      renderAModelAt((TileEntity_statue )tileentity, d, d1, d2, f);
	   }
}
