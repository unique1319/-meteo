package com.wrh.meteo.draw;

import com.hxgis.draw.contour.WContourIsoTracer;
import com.hxgis.meteodata.comdata.GridData;
import com.wrh.meteo.component.enums.LegendEnum;
import com.wrh.meteo.component.enums.ProInfoEnum;
import com.wrh.meteo.component.help.JFrameHelp;
import com.wrh.meteo.read.ReadGrb2File;
import org.junit.jupiter.api.Test;

import java.io.File;

/**
 * @author wrh
 * @version 1.0
 * @date 2021/2/24 17:42
 * @describe
 */
class DrawImgTest {

    @Test
    void drawGridData() throws Exception {
        String filePath = "E:\\ART\\PRE_FAST_DAY\\20191202\\Z_SURF_C_BABJ_20191202081101_P_CMPA_FAST_CHN_0P05_DAY-PRE-2019120208.GRB2";
        GridData gridData = ReadGrb2File.read(new File(filePath)).get(0);
        gridData = gridData.cut(ProInfoEnum.BCWH.getMinLon(), ProInfoEnum.BCWH.getMinLat(), ProInfoEnum.BCWH.getMaxLon(), ProInfoEnum.BCWH.getMaxLat());
        JFrameHelp.showImageFrame(DrawImg.drawGridData(gridData, ProInfoEnum.BCWH, LegendEnum.RAIN_1H, false, WContourIsoTracer.class));
    }
}