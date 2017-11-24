package net.minecraft.src.statues;

import net.minecraft.src.*;

import java.awt.image.BufferedImage;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Properties;

import javax.imageio.ImageIO;

import net.minecraft.client.Minecraft;

public final class TailleTileRender
{
	public static int int_size;
    public static int int_sizeMinus1;
    public static int int_sizeHalf;
    public static int int_glBufferSize = 65536;
    public static int int_numPixels;
    public static int int_numBytes;
    public static int int_numPixelsMinus1;
    public static int int_compassNeedleMin;
    public static int int_compassNeedleMax;
    public static int int_compassCrossMin;
    public static int int_compassCrossMax;
    public static int int_flameHeight;
    public static int int_flameHeightMinus1;
    public static int int_flameArraySize;
    public static float float_size;
    public static float float_sizeMinus1;
    public static float float_sizeMinus0_01;
    public static float float_sizeHalf;
    public static float float_size16;
    public static float float_reciprocal;
    public static float float_texNudge;
    public static float float_flameNudge;
    public static double double_size;
    public static double double_sizeMinus1;
    public static double double_compassCenterMin;
    public static double double_compassCenterMax;
    private static boolean debug = false;
    private static boolean enableResizing = true;
    private static final HashMap expectedColumns = new HashMap();
    private static TexturePackImplementation texturePack;
    
    public TailleTileRender()
    {
    }

    public static boolean setTileSize()
    {
        int var0 = getTileSize();

        if (var0 == TailleTileRender.int_size)
        {
            debug("tile size %d unchanged", new Object[] {Integer.valueOf(var0)});
            return false;
        }
        else
        {
            debug("setting tile size to %d (was %d)", new Object[] {Integer.valueOf(var0), Integer.valueOf(TailleTileRender.int_size)});
            TailleTileRender.setTileSize(var0);
            return true;
        }
    }
    
    public static void setTileSize(int var0)
    {
        int_size = var0;
        int_sizeMinus1 = var0 - 1;
        int_sizeHalf = var0 / 2;
        int_glBufferSize = Math.max(int_glBufferSize, 1024 * var0 * var0);
        int_numPixels = var0 * var0;
        int_numBytes = 4 * int_numPixels;
        int_numPixelsMinus1 = int_numPixels - 1;
        int_compassNeedleMin = var0 / -2;
        int_compassNeedleMax = var0;
        int_compassCrossMin = var0 / -4;
        int_compassCrossMax = var0 / 4;
        int_flameHeight = var0 + 4;
        int_flameHeightMinus1 = int_flameHeight - 1;
        int_flameArraySize = var0 * int_flameHeight;
        float_size = (float)int_size;
        float_sizeMinus1 = float_size - 1.0F;
        float_sizeMinus0_01 = float_size - 0.01F;
        float_sizeHalf = float_size / 2.0F;
        float_size16 = float_size * 16.0F;
        float_reciprocal = 1.0F / float_size;
        float_texNudge = 1.0F / (float_size * float_size * 2.0F);

        if (var0 < 64)
        {
            float_flameNudge = 1.0F + 0.96F / float_size;
        }
        else
        {
            float_flameNudge = 1.0F + 1.28F / float_size;
        }

        double_size = (double)int_size;
        double_sizeMinus1 = double_size - 1.0D;
        double_compassCenterMin = double_size / 2.0D - 0.5D;
        double_compassCenterMax = double_size / 2.0D + 0.5D;
    }

    private static int getTileSize()
    {
        int var0 = 0;
        enableResizing = false;
        Iterator var1 = expectedColumns.entrySet().iterator();

        while (var1.hasNext())
        {
            Entry var2 = (Entry)var1.next();
            BufferedImage var3 = getImage((String)var2.getKey());

            if (var3 != null)
            {
                int var4 = var3.getWidth() / ((Integer)var2.getValue()).intValue();
                debug("  %s tile size is %d", new Object[] {var2.getKey(), Integer.valueOf(var4)});
                var0 = Math.max(var0, var4);
            }
        }

        enableResizing = true;
        return var0 > 0 ? var0 : 16;
    }
    
    public static BufferedImage getImage(String var0)
    {
        return getImageImpl(var0);
    }
    
    protected static BufferedImage getImageImpl(String var1)
    {
        InputStream var2 = getInputStream(var1);
        BufferedImage var3 = null;

        if (var2 != null)
        {
            try
            {
                var3 = ImageIO.read(var2);
            }
            catch (IOException var8)
            {
                error("could not read %s", new Object[] {var1});
                var8.printStackTrace();
            }
            finally
            {
                close((Closeable)var2);
            }
        }

        return var3;
    }
    
    protected static InputStream getInputStreamImpl(String var1)
    {
        if (texturePack == null)
        {
            TexturePackImplementation var2 = getCurrentTexturePack();
            return var2 == null ? TailleTileRender.class.getResourceAsStream(var1) : var2.getResourceAsStream(var1);
        }
        else
        {
            return texturePack.getResourceAsStream(var1);
        }
    }
    
    public static InputStream getInputStream(String var0)
    {
        return getInputStreamImpl(var0);
    }
    
    static TexturePackImplementation getCurrentTexturePack()
    {
        Minecraft var0 = ModLoader.getMinecraftInstance();

        if (var0 == null)
        {
            return null;
        }
        else
        {
            TexturePackList var1 = var0.texturePackList;
            return (TexturePackImplementation) (var1 == null ? null : var1.getSelectedTexturePack());
        }
    }
    
    private static void dump()
    {
        Field[] var0 = TailleTileRender.class.getDeclaredFields();
        int var1 = var0.length;

        for (int var2 = 0; var2 < var1; ++var2)
        {
            Field var3 = var0[var2];

            if (var3.getName().contains("_"))
            {
                try
                {
                    debug("%s = %s", new Object[] {var3.getName(), var3.get((Object)null)});
                }
                catch (Exception var5)
                {
                    debug("%s: %s", new Object[] {var3.getName(), var5.toString()});
                }
            }
        }
    }
    
    public static void error(String var0, Object ... var1)
    {
        System.out.printf("ERROR: " + var0 + "\n", var1);
    }
    
    public static void close(Closeable var0)
    {
        if (var0 != null)
        {
            try
            {
                var0.close();
            }
            catch (IOException var2)
            {
                var2.printStackTrace();
            }
        }
    }
    
    public static void debug(String var0, Object ... var1)
    {
        if (debug)
        {
            System.out.printf(var0 + "\n", var1);
        }
    }
    
    static
    {
        setTileSize(16);
    }
}
