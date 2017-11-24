/**
 * Technical class that converts a pixel color into a minecraft block, with a predefined ArrayList of ColorBlock
 */
package net.minecraft.src.statues;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import net.minecraft.src.*;

public class Pixel2BlockHelper
{
	public static final int ALPHA = 1, RED = 2, GREEN = 3, BLUE = 4; //Macros
	
	private static ArrayList<ColorBlock> colorBlocks;
	
	/**
	 * Constructor for this class
	 * @param List of the block used in the recognition
	 */
	public Pixel2BlockHelper(ArrayList<ColorBlock> blocks)
	{
		this.colorBlocks = blocks;
	}
	
	/**
	 * Pixel RGBA values
	 * @param pixel value in hexadecimal
	 * @param a macro, RED/GREEN/BLUE/ALPHA
	 * @return the Alpha/Red/Green/Blue value of the first argument, depending on the second argument
	 */
	public static int getPixelRGBA(int pixel, int value) {
	    int alpha = (pixel >> 24) & 0xff;
	    int red = (pixel >> 16) & 0xff;
	    int green = (pixel >> 8) & 0xff;
	    int blue = (pixel) & 0xff;
	    switch(value)
	    {
	    case ALPHA:
	    	return alpha;
	    case RED:
	    	return red;
	    case GREEN:
	    	return green;
	    case BLUE:
	    	return blue;
	    default:
	    	return -1;
	    }
	  }
	
	/**
	 * returns the distance from a color col for the color defined by (R,G,B)
	 * @param color control
	 * @param Red value of the color to test
	 * @param Green value of the color to test
	 * @param Blue value of the color to test
	 * @return the distance between the position col and the position p(R;G;B) 
	 */
	public static int getDistanceSquared(Color col, int R, int G, int B)
    {                                                                               
            return (R-col.getRed())*(R-col.getRed())
                            + (G-col.getGreen())*(G-col.getGreen())
                            + (B-col.getBlue())*(B-col.getBlue());
    }
	
	/**
	 * get the nearest block id/metadata of similar color with a pixel in the image tex, of coordinates (x;y)
	 * @param tex: image used
	 * @param x: x coordinate of the pixel
	 * @param y: y coordinate of the pixel
	 * @return the nearest block id in an array with first field = block id ; and second field = block metadata
	 */
	public static ArrayList getNearestClothID(BufferedImage tex, int x, int y)
	{
		int RGB = tex.getRGB(x, y);
		int R = getPixelRGBA(RGB, RED);
		int G = getPixelRGBA(RGB, GREEN);
		int B = getPixelRGBA(RGB, BLUE);
		int A = getPixelRGBA(RGB, ALPHA);
		ArrayList resultat = new ArrayList();
		resultat.add(-1);
		
		if (A == 0)
		{
			return resultat;
		}
		else
		{
			if(colorBlocks.isEmpty())
			{
                return resultat;
			}
			resultat.remove(0);
	        ColorBlock result = colorBlocks.get(0);
	        int minDist = getDistanceSquared(result.ColorBlock, R, G, B);
	       
	        for(ColorBlock col : colorBlocks)
	        {
	                if(getDistanceSquared(col.ColorBlock, R, G, B) < minDist)
	                {
	                        minDist = getDistanceSquared(col.ColorBlock, R, G, B);
	                        result = col;
	                }
	        }
	        resultat.add(result.idBlock);
	        resultat.add(result.metadataBlock);
	        return resultat;
		}
	}
}
