/**
 * Model Techne class for the sign on the statue
 */

package net.minecraft.src.statues;

import net.minecraft.src.*;

public class ModelPanneau extends ModelBase
{
	ModelRenderer sign;
	ModelRenderer stick;
	ModelRenderer stick2;
	
	public ModelPanneau()
	{
		textureWidth = 64;
		textureHeight = 32;
		
		sign = new ModelRenderer(this, 0, 0);
		sign.addBox(0F, 0F, 0F, 16, 4, 1);
		sign.setRotationPoint(-8F, 18F, -9F);
		setRotation(sign, 0F, 0F, 0F);
		
		stick = new ModelRenderer(this, 34, 0);
		stick.addBox(0F, 0F, 0F, 1, 2, 1);
		stick.setRotationPoint(-8F, 22F, -9F);
		setRotation(stick, 0F, 0F, 0F);
		
		stick2 = new ModelRenderer(this, 34, 0);
		stick2.addBox(0F, 0F, 0F, 1, 2, 1);
		stick2.setRotationPoint(7F, 22F, -9F);
		setRotation(stick2, 0F, 0F, 0F);
	}
	
	/**
	 * Render a static model as defined in the constructor
	 */
	public void renderModel(float f)
	{
		stick.render(f);
		stick2.render(f);
		sign.render(f);
	}

	/**
	 * Sets the models various rotation angles then renders the model.
	 */
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
	{
		super.render(entity, f, f1, f2, f3, f4, f5);
		setRotationAngles(f, f1, f2, f3, f4, f5, entity);
		stick.render(f5);
		stick2.render(f);
		sign.render(f5);
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