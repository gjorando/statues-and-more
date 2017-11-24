package net.minecraft.src;


import org.lwjgl.opengl.GL11;

public class Render_statue extends TileEntitySpecialRenderer
{
	private Model_statue statue;
	private Model_armor inner;
	private Model_armor outer;
	private Model_slab slab;
	private Model_Screeper creeper;
	private Model_Acreeper armorcreep;
	private Model_croix croix;
	private Model_massif massif;
    private static String armorArray[];
	
	   public Render_statue()
	   {
	      statue = new Model_statue();
	      inner = new Model_armor(0.5F);
	      outer = new Model_armor(1F);
	      slab = new Model_slab();
	      creeper = new Model_Screeper();
	      armorcreep = new Model_Acreeper(0.5F);
	      croix = new Model_croix();
	      massif = new Model_massif();	      
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
           
           for (int iFor = -2; iFor < 4; iFor++)
           {
               Model_statue modelstatue;
               Model_armor modelarmor;
               Model_slab modelslab;
               Model_Screeper modelcreeper;
           	   Model_Acreeper modelarmorcreep;
           	   Model_croix modelcroix;
           	   Model_massif modelmassif;
               
           	   int button = tileentity1.getButtonValue();
           	   
               if (iFor == -2)
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
	                   modelstatue.renderModel(0.0625F);
            	   }
            	   else if(button == 6)
            	   {
            		   modelcreeper = creeper;
            		   modelcreeper.renderModel(0.0625F);
            	   }
            	   else if(button == 7)
            	   {
            		   modelcroix = croix;
            		   modelcroix.renderModel(0.0625F);
            	   }
               }
               else if(iFor == -1)
               {
            	   bindTextureByName("/dolfinsbizou/slab.png");
            	   modelslab = slab;
            	   modelslab.renderModel(0.0625F);
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
                   if(button == 1 || button == 2 || button == 3 || button == 4 || button == 5 || button == 7)
                   {
	                   modelarmor = k != 2 ? outer : inner;
	                   modelarmor.tete.showModel = k == 0;
	                   modelarmor.corps.showModel = k == 1 || k == 2;
	                   modelarmor.bras_droit.showModel = k == 1;
	                   modelarmor.bras_gauche.showModel = k == 1;
	                   modelarmor.jambe_droite.showModel = k == 2 || k == 3;
	                   modelarmor.jambe_gauche.showModel = k == 2 || k == 3;//*/
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
                	   modelarmorcreep.renderModel(0.0625F);
                   }
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
