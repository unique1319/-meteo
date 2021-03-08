package com.wrh.meteo.component.vecute;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hxgis.draw.contour.bean.FloatImageDiscreteData;
import com.wrh.meteo.component.enums.ProInfoEnum;
import com.wrh.meteo.component.test.GetTestData;
import com.wrh.meteo.draw.DrawImg;
import com.wrh.meteo.draw.help.JFrameHelp;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wrh
 * @version 1.0
 * @date 2021/3/6 14:07
 * @describe
 */
@Slf4j
class AlgorithmTest {

    @Test
    void vecutePoints() throws Exception {
        List<VecutePoint> pList = getVecutePointData();
        int pointNum = pList.size();
        long startMillis = System.currentTimeMillis();
        pList = Algorithm.vecutePoints(pList, 10, 5);
        long endMillis = System.currentTimeMillis();
        List<FloatImageDiscreteData> discreteData = new ArrayList<>();
        int index = 0;
        for (VecutePoint point : pList) {
            log.info(point.toString());
            FloatImageDiscreteData d = new FloatImageDiscreteData();
            d.setLon((float) point.getX());
            d.setLat((float) point.getY());
            d.setValue((float) point.getO());
            discreteData.add(d);
            index = index + point.getSum();
            log.info(point.toString());
        }
        log.info(String.valueOf(index));
        log.warn(pointNum + "个点位抽稀后数据数量：" + pList.size());
        double timeSpan = (endMillis - startMillis) / 1000.0;
        log.warn(pointNum + "个点位抽稀消耗时间（秒）：" + timeSpan);
        JFrameHelp.showImageFrame(DrawImg.drawPointData(discreteData, ProInfoEnum.BCWH, false));
    }

    private List<VecutePoint> getVecutePointData() {
        JSONArray siteR1hData = GetTestData.getTestSiteR1hData();
        List<VecutePoint> vecuteObjectData = new ArrayList<>();
        for (int i = 0; i < siteR1hData.size(); i++) {
            JSONObject s = siteR1hData.getJSONObject(i);
            float v = s.getFloatValue("val");
            if (v >= 999f || v < 0.1) continue;
            VecutePoint vo = new VecutePoint();
            vo.setLocation(s.getFloat("lon"), s.getFloat("lat"));
            vo.setO(v);
            vecuteObjectData.add(vo);
        }
        return vecuteObjectData;
    }
}