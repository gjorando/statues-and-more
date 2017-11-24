/**
 * Technical Class that stores the id/metadata of a block and his associated color.
 */

package net.minecraft.src.statues;

import java.awt.Color;
import net.minecraft.src.*;

public class ColorBlock
{
	int idBlock, metadataBlock;
	Color ColorBlock;
	
	/**
	 * Constructor for this class
	 * @param id of the block
	 * @param metadata of the block
	 * @param associated color of the block
	 */
	public ColorBlock(int id, int metadata, Color color)
	{
		this.idBlock = id;
		this.metadataBlock = metadata;
		this.ColorBlock = color;
		
	}
}
