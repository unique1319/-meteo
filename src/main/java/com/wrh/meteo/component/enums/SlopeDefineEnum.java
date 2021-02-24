package com.wrh.meteo.component.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author wrh
 * @version 1.0
 * @date 2021/2/24 13:52
 * @describe 坡度定义枚举类
 */
@Getter
@AllArgsConstructor
public enum SlopeDefineEnum {

    PY("平原", 0.0, 0.5),
    WXP("微斜坡", 0.5, 2.0),
    HXP("缓斜坡", 2.00, 5.0),
    XP("斜坡", 5.0, 15.0),
    DP("陡坡", 15.0, 35.0),
    QP("峭坡", 35.0, 55.0),
    CZB("垂直壁", 55.0, 90.0),
    ;
    private final String name;
    private final Double low;
    private final Double upper;
}
