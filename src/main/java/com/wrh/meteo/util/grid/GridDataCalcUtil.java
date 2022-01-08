package com.wrh.meteo.util.grid;

import com.hxgis.meteodata.comdata.GridData;
import com.wrh.meteo.util.NumberUtil;
import org.springframework.lang.NonNull;

import java.util.Arrays;
import java.util.OptionalDouble;

public class GridDataCalcUtil {

    public static float INVALID_VAL = -999999f;

    public static GridData sum(@NonNull GridData... gridDatas) {
        return gridDataCalc(GridCalc::gridDataSum, gridDatas);
    }

    public static GridData avg(@NonNull GridData... gridDatas) {
        return gridDataCalc(GridCalc::gridDataAvg, gridDatas);
    }

    public static GridData max(@NonNull GridData... gridDatas) {
        return gridDataCalc(GridCalc::gridDataMax, gridDatas);
    }

    public static GridData min(@NonNull GridData... gridDatas) {
        return gridDataCalc(GridCalc::gridDataMin, gridDatas);
    }

    public static GridData gridDataCalc(GridCalc gridCalc, @NonNull GridData... gridDatas) {
        GridData gridData = gridDatas[0];
        int xs = gridData.getxNum();
        int ys = gridData.getyNum();
        float[][] newGrid = new float[ys][xs];
        for (int i = 0; i < ys; i++) {
            for (int j = 0; j < xs; j++) {
                newGrid[i][j] = gridCalc.calc(i, j, gridDatas);
            }
        }
        gridData.setGrid(newGrid);
        return gridData;
    }

    @FunctionalInterface
    public interface GridCalc {
        float calc(int i, int j, GridData... gridDatas);

        static float gridDataSum(int i, int j, GridData... gridDatas) {
            float result = (float) Arrays.stream(gridDatas).mapToDouble(g -> g.getGrid()[i][j]).filter(v -> Math.abs(v) < Math.abs(INVALID_VAL)).sum();
            return (float) NumberUtil.round(result, 1);
        }

        static float gridDataAvg(int i, int j, GridData... gridDatas) {
            OptionalDouble average = Arrays.stream(gridDatas).mapToDouble(g -> g.getGrid()[i][j]).filter(v -> Math.abs(v) < Math.abs(INVALID_VAL)).average();
            return average.isPresent() ? (float) NumberUtil.round(average.getAsDouble(), 1) : INVALID_VAL;
        }

        static float gridDataMax(int i, int j, GridData... gridDatas) {
            OptionalDouble max = Arrays.stream(gridDatas).mapToDouble(g -> g.getGrid()[i][j]).filter(v -> Math.abs(v) < Math.abs(INVALID_VAL)).max();
            return max.isPresent() ? (float) NumberUtil.round(max.getAsDouble(), 1) : INVALID_VAL;
        }

        static float gridDataMin(int i, int j, GridData... gridDatas) {
            OptionalDouble min = Arrays.stream(gridDatas).mapToDouble(g -> g.getGrid()[i][j]).filter(v -> Math.abs(v) < Math.abs(INVALID_VAL)).min();
            return min.isPresent() ? (float) NumberUtil.round(min.getAsDouble(), 1) : INVALID_VAL;
        }
    }
}
