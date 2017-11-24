/**
 * Model Techne class for the massive statue
 */

package net.minecraft.src.statues;

import net.minecraft.src.*;

public class ModelMassif extends ModelBase
{
	ModelRenderer Shape1;
	
	public ModelMassif()
	{
		this(0.0F);
	}
	
	public ModelMassif(float par1)
	{
		textureWidth = 64;
		textureHeight = 64;
		
		Shape1 = new ModelRenderer(this, 0, 0);
		Shape1.addBox(0F, 0F, 0F, 16, 24, 16);
		Shape1.setRotationPoint(-8F, -8F, -8F);
		Shape1.setTextureSize(64, 64);
		Shape1.mirror = true;
		setRotation(Shape1, 0F, 0F, 0F);
	}
	
	/**
	 * Render a static model as defined in the constructor
	 */
	public void renderModel(float f)
	{
		Shape1.render(f);
	}
	
	/**
	 * Sets the models various rotation angles then renders the model.
	 */
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
	{
		super.render(entity, f, f1, f2, f3, f4, f5);
		setRotationAngles(f, f1, f2, f3, f4, f5, entity);
		Shape1.render(f);
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
}