/**
 * Model Techne class for the creeper statue armor
 */

package net.minecraft.src.statues;

import net.minecraft.src.*;

public class ModelAcreeper extends ModelBase
{
    ModelRenderer tete;
    ModelRenderer patte1;
    ModelRenderer patte2;
    ModelRenderer patte3;
    ModelRenderer patte4;
    ModelRenderer Shape1;
  
    public ModelAcreeper()
    {
        this(0.0F);
    }  
    
    public ModelAcreeper(float par1)
    {
    	textureWidth = 64;
    	textureHeight = 32;
    	
    	tete = new ModelRenderer(this, 0, 0);
    	tete.addBox(0F, 0F, 0F, 8, 8, 8, par1);
    	tete.setRotationPoint(-4F, -10F, -4F);
    	tete.setTextureSize(64, 32);
    	tete.mirror = true;
    	setRotation(tete, 0F, 0F, 0F);
    	
    	patte1 = new ModelRenderer(this, 0, 22);
    	patte1.mirror = true;
    	patte1.addBox(0F, 0F, 0F, 4, 6, 4, par1);
    	patte1.setRotationPoint(-4F, 10F, -6F);
    	patte1.setTextureSize(64, 32);
    	setRotation(patte1, 0F, 0F, 0F);
    	
    	patte2 = new ModelRenderer(this, 0, 22);
    	patte2.addBox(0F, 0F, 0F, 4, 6, 4, par1);
    	patte2.setRotationPoint(0F, 10F, -6F);
    	patte2.setTextureSize(64, 32);
    	patte2.mirror = true;
    	setRotation(patte2, 0F, 0F, 0F);
    	
    	patte3 = new ModelRenderer(this, 0, 22);
    	patte3.mirror = true;
    	patte3.addBox(0F, 0F, 0F, 4, 6, 4, par1);
    	patte3.setRotationPoint(0F, 10F, 6F);
    	patte3.setTextureSize(64, 32);
    	setRotation(patte3, 0F, 3.141593F, 0F);
    	
    	patte4 = new ModelRenderer(this, 0, 22);
    	patte4.addBox(0F, 0F, 0F, 4, 6, 4, par1);
    	patte4.setRotationPoint(4F, 10F, 6F);
    	patte4.setTextureSize(64, 32);
    	patte4.mirror = true;
    	setRotation(patte4, 0F, 3.141593F, 0F);
    	
    	Shape1 = new ModelRenderer(this, 16, 16);
    	Shape1.addBox(0F, 0F, 0F, 8, 12, 4, par1);
    	Shape1.setRotationPoint(-4F, -2F, -2F);
    	Shape1.setTextureSize(64, 32);
    	Shape1.mirror = true;
    	setRotation(Shape1, 0F, 0F, 0F);
    }
    
    /**
	 * Render a static model as defined in the constructor
	 */
    public void renderModel (float f)
    {
    	tete.render(f);
    	patte1.render(f);
    	patte2.render(f);
	    patte3.render(f);
	    patte4.render(f);
	    Shape1.render(f);
    }
    
    /**
	 * Sets the models various rotation angles then renders the model.
	 */
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
    {
    	super.render(entity, f, f1, f2, f3, f4, f5);
    	setRotationAngles(f, f1, f2, f3, f4, f5);
    	tete.render(f5);
    	patte1.render(f5);
    	patte2.render(f5);
    	patte3.render(f5);
    	patte4.render(f5);
    	Shape1.render(f5);
    }
    
    /**
	 * Sets the model rotation.
	 */
    private void setRotation(ModelRenderer model, float x, float y, float z)
    {
    	model.rotateAngleX = x;
    	model.rotateAngleY = y;
    	model.rotateAngleZ = z;
    }
    
    /**
	 * Sets the models various rotation angles.
	 */
    public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5)
    {
    	super.setRotationAngles(f, f1, f2, f3, f4, f5);
    }
    
    /**
	 * Change the position of the statue depending of if there is a step or not.
	 */
    public void stepExists(boolean exists)
    {
    	if(!exists)
    	{
    		this.patte1.rotationPointY = 18F;
    		this.patte2.rotationPointY = 18F;
    		this.patte3.rotationPointY = 18F;
    		this.patte4.rotationPointY = 18F;
    		this.Shape1.rotationPointY = 6F;
    		this.tete.rotationPointY = -2F;
    	}
    	else
    	{
    		this.patte1.rotationPointY = 10F;
    		this.patte2.rotationPointY = 10F;
    		this.patte3.rotationPointY = 10F;
    		this.patte4.rotationPointY = 10F;
    		this.Shape1.rotationPointY = -2F;
    		this.tete.rotationPointY = -10F;
    	}
    }
}