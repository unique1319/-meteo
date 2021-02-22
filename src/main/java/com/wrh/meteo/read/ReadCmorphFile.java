package com.wrh.meteo.read;

import com.hxgis.meteodata.comdata.GridData;
import com.hxgis.meteodata.common.Binary.LittleEndianReader;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.compress.compressors.bzip2.BZip2CompressorInputStream;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author wrh
 * @version 1.0
 * @date 2020/9/29 11:18
 * @describe Cmorph文件解析方法
 * DSET ../CRT/0.25deg-3HLY/%y4/%y4%m2/CMORPH_V1.0_ADJ_0.25deg-3HLY_%y4%m2%d2
 * OPTIONS template little_endian
 * UNDEF  -999.0
 * TITLE  Precipitation estimates
 * XDEF 1440 LINEAR    0.125  0.25
 * YDEF  480 LINEAR  -59.875  0.25
 * ZDEF   01 LEVELS 1
 * TDEF 999999 LINEAR  00z01jan1998  3hr
 * VARS 1
 * cmorph   1   99 RAW CMORPH integrated satellite precipitation estimates [mm/3hr]
 * ENDVARS
 * **  precipitation for the first time step every day (00Z) is the
 * **  accumulation for 00:00Z-02:59Z
 */

public class ReadCmorphFile {

    public static List<GridData> read(File file) {
        List<GridData> gridDataList = new ArrayList<>();
        BufferedInputStream dis = null;
        BZip2CompressorInputStream bz2is = null;
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(file);
            bz2is = new BZip2CompressorInputStream(fis);
            dis = new BufferedInputStream(bz2is);
            LittleEndianReader leReader = new LittleEndianReader(dis);
            int lats = 480;
            int lons = 1440;

            for (int t = 0; t < 8; t++) {
                GridData gridData = new GridData();
                float[][] grid = new float[lats][lons];
                float[] gridLat = new float[lats];
                float[] gridLon = new float[lons];

                for (int i = 0; i < lats; i++) {
                    gridLat[i] = -59.875f + 0.25f * i;
                    for (int j = 0; j < lons; j++) {
                        gridLon[j] = 0.125f + 0.25f * j;
                        grid[i][j] = leReader.readFloat();
                    }
                }
                gridData.setyNum(lats);
                gridData.setxNum(lons);
                gridData.setyRes(0.25f);
                gridData.setxRes(0.25f);
                gridData.setStartX(gridLon[0]);
                gridData.setEndX(gridLon[gridLon.length - 1]);
                gridData.setStartY(gridLat[0]);
                gridData.setEndY(gridLat[gridLat.length - 1]);
                gridData.setGrid(grid);
                gridDataList.add(gridData);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (bz2is != null) {
                try {
                    bz2is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (dis != null) {
                try {
                    dis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return gridDataList;
    }
}
