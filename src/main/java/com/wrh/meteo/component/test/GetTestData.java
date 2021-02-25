package com.wrh.meteo.component.test;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.wrh.meteo.component.geo.ReadProGeoJson;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

/**
 * @author wrh
 * @version 1.0
 * @date 2021/2/25 10:17
 * @describe
 */
public class GetTestData {

    public static JSONArray getTestSiteR1hData() {
        String filePath = "public/test/site-r1h.json";
        InputStream dataStream = ReadProGeoJson.class.getClassLoader().getResourceAsStream(filePath);
        try {
            assert dataStream != null;
            return JSONObject.parseObject(IOUtils.toString(dataStream, StandardCharsets.UTF_8))
                    .getJSONObject("resultData")
                    .getJSONArray("sites");
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            try {
                assert dataStream != null;
                dataStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
