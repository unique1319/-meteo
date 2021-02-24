package com.wrh.meteo.component.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.awt.*;

/**
 * @author wrh
 * @version 1.0
 * @date 2021/2/24 14:26
 * @describe
 */
@Getter
@AllArgsConstructor
public enum LegendEnum {

    RAIN_1H("1h降水色卡",
            "mm",
            new double[]{0.1, 1.6, 7, 15, 40, 50},
            new String[]{"#ffffff","#a5f38d", "#3db93f", "#63b8f9", "#0000fe", "#f305ee", "#810040"},
            new String[]{"无降水", "0.1~10", "10~25", "25~50", "50~100", "100~250", "≥250"}),
//    RAIN_3H(),
//    RAIN_6H(),
//    RAIN_12H(),
//    RAIN_24H(),
//    TEM(),
//    MXT(),
//    MNT(),
    ;
    private final String legendName;
    private final String unit;
    private final double[] isoValues;
    private final String[] colors;
    private final String[] legendTexts;
}
