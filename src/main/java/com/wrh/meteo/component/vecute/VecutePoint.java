package com.wrh.meteo.component.vecute;

import java.awt.*;
import java.awt.geom.Point2D;

/**
 * 抽稀返回对象
 *
 * @author zl
 */
public class VecutePoint extends Point2D.Double {
    /**
     * 初始化构造函数，数量设为1
     */
    public VecutePoint() {
        this.sum = 1;
    }

    /**
     * 抽稀后的数量，用来记录落在某一个小举行块内的总数
     */
    private int sum;

    private Object o;

    public int getSum() {
        return sum;
    }

    public void setSum(int sum) {
        this.sum = sum;
    }

    public Object getO() {
        return o;
    }

    public void setO(Object o) {
        this.o = o;
    }

    @Override
    public String toString() {
        return "矩阵内抽稀点数量：" + sum + ",X:" + getX() + ",Y:" + getY();
    }

}
