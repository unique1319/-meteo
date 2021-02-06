package com.wrh.meteo;

import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 * @author wrh
 * @version 1.0
 * @date 2021/2/6 13:42
 * @describe
 */
public class NumberUtil {

    public static BigDecimal round(BigDecimal b, int scale) {
        return b.setScale(scale, BigDecimal.ROUND_HALF_UP);
    }

    public static double round(double d, int scale) {
        return Math.round(d * Math.pow(10, scale)) * Math.pow(10, -scale);
    }

    public static String round(String s, int scale) {
        DecimalFormat df = new DecimalFormat("#." + StrUtil.repeat("0", scale));
        return df.format(s);
    }

    public static String round(Object d, int scale) {
        return String.format("%." + scale + "f", d);
    }
}
