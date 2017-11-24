package net.minecraft.src;

import java.lang.reflect.Field;

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

    public TailleTileRender()
    {
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

    static
    {
        setTileSize(16);
    }
}
