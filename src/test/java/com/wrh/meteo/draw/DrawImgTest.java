package com.wrh.meteo.draw;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hxgis.draw.contour.WContourIsoTracer;
import com.hxgis.draw.contour.bean.FloatImageDiscreteData;
import com.hxgis.draw.contour.bean.FloatInterpolateType;
import com.hxgis.meteodata.comdata.GridData;
import com.wrh.meteo.component.enums.LegendEnum;
import com.wrh.meteo.component.enums.ProInfoEnum;
import com.wrh.meteo.draw.help.JFrameHelp;
import com.wrh.meteo.component.test.GetTestData;
import com.wrh.meteo.read.ReadGrb2File;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

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

    @Test
    void drawPointData() throws Exception {
        List<FloatImageDiscreteData> discreteData = getDiscreteData();
        JFrameHelp.showImageFrame(DrawImg.drawPointData(discreteData, ProInfoEnum.BCWH, false));
    }

    @Test
    void drawPointDataWithInterpolater() throws Exception {
        List<FloatImageDiscreteData> discreteData = getDiscreteData();
        JFrameHelp.showImageFrame(DrawImg.drawPointData(discreteData, ProInfoEnum.BCWH, LegendEnum.RAIN_1H, true));
    }

    private List<FloatImageDiscreteData> getDiscreteData() {
        JSONArray siteR1hData = GetTestData.getTestSiteR1hData();
        List<FloatImageDiscreteData> discreteData = new ArrayList<>();
        for (int i = 0; i < siteR1hData.size(); i++) {
            JSONObject s = siteR1hData.getJSONObject(i);
            float v = s.getFloatValue("val");
            if (v >= 999f || v < 0.1) continue;
            FloatImageDiscreteData d = new FloatImageDiscreteData();
            d.setVal(new FloatInterpolateType(v));
            d.setLon(s.getFloat("lon"));
            d.setLat(s.getFloat("lat"));
            discreteData.add(d);
        }
        return discreteData;
    }
}