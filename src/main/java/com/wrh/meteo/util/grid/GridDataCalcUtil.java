package com.wrh.meteo.util.grid;

import com.hxgis.meteodata.comdata.GridData;
import org.springframework.lang.NonNull;

import java.util.Arrays;

public class GridDataCalcUtil {

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
            return (float) Arrays.stream(gridDatas).mapToDouble(g -> g.getGrid()[i][j]).sum();
        }

        static float gridDataAvg(int i, int j, GridData... gridDatas) {
            return (float) Arrays.stream(gridDatas).mapToDouble(g -> g.getGrid()[i][j]).average().getAsDouble();
        }

        static float gridDataMax(int i, int j, GridData... gridDatas) {
            return (float) Arrays.stream(gridDatas).mapToDouble(g -> g.getGrid()[i][j]).max().getAsDouble();
        }

        static float gridDataMin(int i, int j, GridData... gridDatas) {
            return (float) Arrays.stream(gridDatas).mapToDouble(g -> g.getGrid()[i][j]).min().getAsDouble();
        }
    }
}
