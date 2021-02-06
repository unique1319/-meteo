package com.wrh.meteo.read.impl;

import com.hxgis.meteodata.comdata.GridData;
import com.wrh.meteo.read.ReadGridData;
import com.wrh.meteo.read.ReadGridDataList;
import lombok.Data;
import ucar.ma2.Array;
import ucar.nc2.dt.GridCoordSystem;
import ucar.nc2.dt.GridDatatype;
import ucar.nc2.dt.grid.GeoGrid;
import ucar.nc2.dt.grid.GridDataset;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author wrh
 * @version 1.0
 * @date 2021/2/6 10:01
 * @describe
 */
public class ReadGrb2Impl implements ReadGridDataList {

    @Override
    public List<GridData> read(File file) {
        List<GridData> list = new ArrayList<>();
        AccessGRBData grbData = null;
        try {
            grbData = new AccessGRBData(file.getAbsolutePath());
            grbData.readFile();
            GeoGrid geoGrid = (GeoGrid) grbData.getGrids().get(0);
            GridCoordSystem coordinateSystem = geoGrid.getCoordinateSystem();
            Array latArray = null;
            Array lonArray = null;
            int tsize = 0, latSize = 0, lonSize = 0;
            if (coordinateSystem.getTimeAxis() != null) {
                Array tArray = coordinateSystem.getTimeAxis().read(); // time
                tsize = (int) tArray.getSize();
            }
            if (coordinateSystem.getYHorizAxis() != null) {
                latArray = coordinateSystem.getYHorizAxis().read(); // lat
                latSize = (int) latArray.getSize();
            }
            if (coordinateSystem.getXHorizAxis() != null) {
                lonArray = coordinateSystem.getXHorizAxis().read(); // lon
                lonSize = (int) lonArray.getSize();
            }
            for (int t = 0; t < tsize; t++) {
                GridData g = new GridData();
                if (latArray != null) {
                    g.setStartY(latArray.getFloat(0));
                    g.setEndY(latArray.getFloat(latSize - 1));
                    g.setyNum(latSize);
                    g.setyRes(Float.parseFloat(NumberUtil.roundStr(latArray.getFloat(1) - latArray.getFloat(0), 3)));
                }
                if (lonArray != null) {
                    g.setStartX(lonArray.getFloat(0));
                    g.setEndX(lonArray.getFloat(latSize - 1));
                    g.setxNum(lonSize);
                    g.setxRes(Float.parseFloat(NumberUtil.roundStr(lonArray.getFloat(1) - lonArray.getFloat(0), 3)));
                }
                if ("FLOAT".equals(geoGrid.getDataType().name())) {
                    float[][] grid = (float[][]) geoGrid.readYXData(t, 0).copyToNDJavaArray();
                    g.setGrid(grid);
                } else if ("DOUBLE".equals(geoGrid.getDataType().name())) {
                    double[][] grid = (double[][]) geoGrid.readYXData(t, 0).copyToNDJavaArray();
                    g.setGrid(doubleArrayToFloatArray(grid));
                } else {
                    throw new Exception("格点解析异常，数据类型未指定：" + geoGrid.getDataType().name());
                }

                list.add(g);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (grbData != null) {
                grbData.dispose();
            }
        }
        return list;
    }

    @Data
    private static class AccessGRBData {
        private String filePath;
        private List<GridDatatype> grids;
        private GridDataset dataset;

        public AccessGRBData(String filePath) {
            this.filePath = filePath;
        }

        public void readFile() {
            try {
                dataset = GridDataset.open(filePath);
                grids = dataset.getGrids();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public void dispose() {
            if (dataset != null) {
                try {
                    dataset.close();
                    File gbx9file = new File(filePath + ".gbx9");
                    if (gbx9file.exists()) {
                        gbx9file.delete();
                    }
                    File ncxfile = new File(filePath + ".ncx3");
                    if (ncxfile.exists()) {
                        ncxfile.delete();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }
}
