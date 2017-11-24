package net.minecraft.src.statues;

import net.minecraft.src.*;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Iterator;

import javax.imageio.ImageIO;

import net.minecraft.client.Minecraft;

public final class TailleTileRender
{
    public static int int_size;
    public static int int_sizeMinus1;
    public static int int_sizeHalf;
    public static int int_glBufferSize = 0x10000;
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
    private static HashMap expectedColumns;

    public TailleTileRender()
    {
    }

    public static boolean setTileSize()
    {
        debug("\nchanging skin to %s", new Object[]
                {
                    getTexturePackName(getSelectedTexturePack())
                });
        int i = getTileSize();

        if (i == int_size)
        {
            debug("tile size %d unchanged", new Object[]
                    {
                        Integer.valueOf(i)
                    });
            return false;
        }
        else
        {
            debug("setting tile size to %d (was %d)", new Object[]
                    {
                        Integer.valueOf(i), Integer.valueOf(int_size)
                    });
            setTileSize(i);
            return true;
        }
    }
    
    public static void setTileSize(int i)
    {
        int_size = i;
        int_sizeMinus1 = i - 1;
        int_sizeHalf = i / 2;
        int_glBufferSize = Math.max(int_glBufferSize, 1024 * i * i);
        int_numPixels = i * i;
        int_numBytes = 4 * int_numPixels;
        int_numPixelsMinus1 = int_numPixels - 1;
        int_compassNeedleMin = i / -2;
        int_compassNeedleMax = i;
        int_compassCrossMin = i / -4;
        int_compassCrossMax = i / 4;
        int_flameHeight = i + 4;
        int_flameHeightMinus1 = int_flameHeight - 1;
        int_flameArraySize = i * int_flameHeight;
        float_size = int_size;
        float_sizeMinus1 = float_size - 1.0F;
        float_sizeMinus0_01 = float_size - 0.01F;
        float_sizeHalf = float_size / 2.0F;
        float_size16 = float_size * 16F;
        float_reciprocal = 1.0F / float_size;
        float_texNudge = 1.0F / (float_size * float_size * 2.0F);

        if (i < 64)
        {
            float_flameNudge = 1.0F + 0.96F / float_size;
        }
        else
        {
            float_flameNudge = 1.0F + 1.28F / float_size;
        }

        double_size = int_size;
        double_sizeMinus1 = double_size - 1.0D;
        double_compassCenterMin = double_size / 2D - 0.5D;
        double_compassCenterMax = double_size / 2D + 0.5D;
    }

    public static TexturePackImplementation getSelectedTexturePack()
    {
        Minecraft var0 = ModLoader.getMinecraftInstance();
        return (TexturePackImplementation) (var0 == null ? null : (var0.texturePackList == null ? null : var0.texturePackList.getSelectedTexturePack()));
    }
    
    public static int getTileSize(TexturePackBase texturepackbase)
    {
        int i = 0;
        Iterator iterator = expectedColumns.entrySet().iterator();

        do
        {
            if (!iterator.hasNext())
            {
                break;
            }

            java.util.Map.Entry entry = (java.util.Map.Entry)iterator.next();
            InputStream inputstream = null;

            try
            {
                try
                {
                    inputstream = getResourceAsStream(texturepackbase, (String)entry.getKey());

                    if (inputstream != null)
                    {
                        java.awt.image.BufferedImage bufferedimage = ImageIO.read(inputstream);
                        int j = bufferedimage.getWidth() / ((Integer)entry.getValue()).intValue();
                        debug("  %s tile size is %d", new Object[]
                                {
                                    entry.getKey(), Integer.valueOf(j)
                                });
                        i = Math.max(i, j);
                    }
                }
                catch (Exception exception)
                {
                    exception.printStackTrace();
                }

                continue;
            }
            finally
            {
                close(inputstream);
            }
        }
        while (true);

        return i <= 0 ? 16 : i;
    }
    
    public static String getTexturePackName(TexturePackImplementation var0)
    {
        return var0 == null ? "Default" : var0.func_77536_b();
    }
    
    public static void debug(String s, Object aobj[])
    {
    	boolean debug = true;
        if (debug)
        {
            System.out.printf((new StringBuilder()).append(s).append("\n").toString(), aobj);
        }
    }
    
    public static void close(Closeable closeable)
    {
        if (closeable != null)
        {
            try
            {
                closeable.close();
            }
            catch (IOException ioexception)
            {
                ioexception.printStackTrace();
            }
        }
    }
    
    static
    {
        expectedColumns = new HashMap();
        expectedColumns.put("/terrain.png", Integer.valueOf(16));
        expectedColumns.put("/gui/items.png", Integer.valueOf(16));
        expectedColumns.put("/misc/dial.png", Integer.valueOf(1));
    }
    
    public static int getTileSize()
    {
        return getTileSize(getSelectedTexturePack());
    }
    
    public static InputStream getResourceAsStream(TexturePackBase texturepackbase, String s)
    {
        InputStream inputstream = null;

        if (texturepackbase != null)
        {
            try
            {
                inputstream = texturepackbase.getResourceAsStream(s);
            }
            catch (Exception exception)
            {
                exception.printStackTrace();
            }
        }

        if (inputstream == null)
        {
            inputstream = getResourceAsStream(s);
        }

        if (inputstream == null && s.startsWith("/anim/custom_"))
        {
            inputstream = getResourceAsStream(texturepackbase, s.substring(5));
        }

        if (inputstream == null && isRequiredResource(s))
        {
            inputstream = Thread.currentThread().getContextClassLoader().getResourceAsStream(s);
            warn("falling back on thread class loader for %s: %s", new Object[]
                    {
                        s, inputstream != null ? "success" : "failed"
                    });
        }

        return inputstream;
    }
    
    public static InputStream getResourceAsStream(String s)
    {
        return getResourceAsStream(getSelectedTexturePack(), s);
    }
    
    public static boolean isRequiredResource(String s)
    {
        return !s.startsWith("/custom_") && !s.startsWith("/anim/custom_") && !s.equals("/terrain_nh.png") && !s.equals("/terrain_s.png") && !s.matches("^/font/.*\\.properties$") && !s.matches("^/mob/.*\\d+.png$");
    }
    
    public static void warn(String s, Object aobj[])
    {
        System.out.printf((new StringBuilder()).append("WARNING: ").append(s).append("\n").toString(), aobj);
    }
}
