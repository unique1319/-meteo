package com.wrh.meteo.util;

import org.junit.jupiter.api.Test;
import org.springframework.util.ObjectUtils;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author wrh
 * @version 1.0
 * @date 2021/3/5 14:24
 * @describe
 */
class ArrayUtilTest {

    @Test
    void isEmpty() {
        double[] f = new double[]{1.2d, 2.3d, 3.4d, 2.5d};
        System.out.println(ObjectUtils.nullSafeToString(f));
    }

    @Test
    void arrayToList() {
        Float[] f = new Float[]{1.2f, 2.3f, 3.4f, 2.5f};
        List<Float> list = ArrayUtil.arrayToList(f);
        Float[] array = ArrayUtil.listToArray(list, Float.class);
        System.out.println(ObjectUtils.nullSafeToString(array));
    }

    @Test
    void arrayHandle() {
        float[][] f = new float[][]{{1.2f, 2.3f}, {3.4f, 2.5f}};
        ArrayUtil.arrayHandle(f, (fv) -> fv + 1f);
        System.out.println("finish");
    }

    @Test
    void arrayHandle2() {
        double[][] f = new double[][]{{1.2f, 2.3f}, {3.4f, 2.5f}};
        ArrayUtil.arrayHandle(f, ArrayUtil.ArrayHandle::handle_centigrade_to_kelvin);
        System.out.println("finish");
    }
}