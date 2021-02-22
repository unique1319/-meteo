package com.wrh.meteo.read;

import com.hxgis.meteodata.comdata.GridData;
import com.wrh.meteo.util.ArrayUtil;
import com.wrh.meteo.util.NumberUtil;
import org.springframework.util.Assert;
import ucar.nc2.NetcdfFile;
import ucar.nc2.Variable;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * @author wrh
 * @version 1.0
 * @date 2021/2/6 10:01
 * @describe 读取NC文件
 */
public class ReadNcFile {

    public static List<GridData> read(File file, String fullName) {
        int scale = 3;
        NetcdfFile openNC = null;
        List<GridData> list = new ArrayList<>();

        try {
            openNC = NetcdfFile.open(file.getAbsolutePath());
            int dimensions = openNC.getDimensions().size();
            Assert.isTrue(dimensions == 3, "不支持的维度! dimensions:" + dimensions);
            Variable gd = openNC.findVariable(fullName);
            Variable la = openNC.findVariable("latitude");
            Variable lo = openNC.findVariable("longitude");
            Variable t = openNC.findVariable("time");
            float[][][] grid = null;
            if ("FLOAT".equals(gd.getDataType().name())) {
                grid = ((float[][][]) gd.read().copyToNDJavaArray());
            } else if ("DOUBLE".equals(gd.getDataType().name())) {
                grid = ArrayUtil.doubleArrayToFloatArray(((double[][][]) gd.read().copyToNDJavaArray()));
            } else {
                throw new Exception("格点解析异常，数据类型未指定：" + gd.getDataType().name());
            }
            float[] lat = null;
            if ("FLOAT".equals(la.getDataType().name())) {
                lat = (float[]) la.read().copyTo1DJavaArray();
            } else if ("DOUBLE".equals(la.getDataType().name())) {
                lat = ArrayUtil.doubleArrayToFloatArray((double[]) la.read().copyToNDJavaArray());
            } else {
                throw new Exception("格点解析异常，数据类型未指定：" + la.getDataType().name());
            }
            float[] lon = null;
            if ("FLOAT".equals(lo.getDataType().name())) {
                lon = (float[]) lo.read().copyTo1DJavaArray();
            } else if ("DOUBLE".equals(lo.getDataType().name())) {
                lon = ArrayUtil.doubleArrayToFloatArray((double[]) lo.read().copyToNDJavaArray());
            } else {
                throw new Exception("格点解析异常，数据类型未指定：" + lo.getDataType().name());
            }
            int[] time = (int[]) t.read().copyTo1DJavaArray();

            float startY = NumberUtil.round(BigDecimal.valueOf(lat[0]), scale).floatValue();
            float endY = NumberUtil.round(BigDecimal.valueOf(lat[lat.length - 1]), scale).floatValue();
            float yRes = NumberUtil.round(BigDecimal.valueOf(lat[1] - lat[0]), scale).floatValue();
            float startX = NumberUtil.round(BigDecimal.valueOf(lon[0]), scale).floatValue();
            float endX = NumberUtil.round(BigDecimal.valueOf(lon[lon.length - 1]), scale).floatValue();
            float xRes = NumberUtil.round(BigDecimal.valueOf(lon[1] - lon[0]), scale).floatValue();

            for (int i = 0; i < time.length; i++) {
                int timeNumber = time[i];
                GridData g = new GridData();
                g.setGrid(grid[i]);
                g.setStartY(startY);
                g.setEndY(endY);
                g.setyNum(lat.length);
                g.setyRes(yRes);
                g.setStartX(startX);
                g.setEndX(endX);
                g.setxNum(lon.length);
                g.setxRes(xRes);
                list.add(g);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (openNC != null) {
                try {
                    openNC.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return list;
    }

    public static void main(String[] args) {
        String filePath = "C:\\Users\\Administrator\\Desktop\\test\\P_CMPA_NRT_CHN_0P05_HOR-PRE-2018010104.nc";
        read(new File(filePath), "rain");
    }

}
