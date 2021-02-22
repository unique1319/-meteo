package com.wrh.meteo.util;

/**
 * @author wrh
 * @version 1.0
 * @date 2021/2/22 14:26
 * @describe
 */
public class ArrayUtil {

    public static float[][][] doubleArrayToFloatArray(double[][][] array) {
        int lenX = array.length;
        int lenY = array[0].length;
        int lenK = array[0][0].length;
        float[][][] f = new float[lenX][lenY][lenK];
        for (int i = 0; i < lenX; i++) {
            for (int j = 0; j < lenY; j++) {
                for (int k = 0; k < lenK; k++) {
                    f[i][j][k] = Float.parseFloat(String.valueOf(array[i][j][k]));
                }
            }
        }
        return f;
    }

    public static float[][] doubleArrayToFloatArray(double[][] array) {
        int lenX = array.length;
        int lenY = array[0].length;
        float[][] f = new float[lenX][lenY];
        for (int i = 0; i < lenX; i++) {
            for (int j = 0; j < lenY; j++) {
                f[i][j] = Float.parseFloat(String.valueOf(array[i][j]));
            }
        }
        return f;
    }

    public static float[] doubleArrayToFloatArray(double[] array) {
        float[] f = new float[array.length];
        for (int i = 0; i < array.length; i++) {
            f[i] = Float.parseFloat(String.valueOf(array[i]));
        }
        return f;
    }

}
