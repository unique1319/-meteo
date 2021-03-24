package com.wrh.meteo.util;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wrh
 * @version 1.0
 * @date 2021/3/23 10:49
 * @describe
 */
public class CollectionUtil {

    /**
     * 分割 list
     *
     * @param datas     List<T>
     * @param splitSize 分割长度
     * @param <T>       泛型对象
     * @return 分割后的集合
     */
    public static <T> List<List<T>> spliceArrays(List<T> datas, int splitSize) {
        if (datas == null || splitSize < 1) {
            return null;
        }
        int totalSize = datas.size();
        int count = (totalSize % splitSize == 0) ?
                (totalSize / splitSize) : (totalSize / splitSize + 1);

        List<List<T>> rows = new ArrayList<>();

        for (int i = 0; i < count; i++) {
            List<T> cols = datas.subList(i * splitSize,
                    (i == count - 1) ? totalSize : splitSize * (i + 1));
            rows.add(cols);
            System.out.println(cols);
        }
        return rows;
    }

}
