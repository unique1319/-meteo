package com.wrh.meteo.component.geo;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

/**
 * @author wrh
 * @version 1.0
 * @date 2021/2/23 14:00
 * @describe
 */
public class ReadProGeoJson {

    /**
     * 获取省份边界 GeoJson
     *
     * @param adminCode 行政区码（例如湖北：420000）
     * @return GeoJson
     */
    public static JSONObject getBoundaryGeoJsonRead(String adminCode) {
        String filePath = "public/GeoAtlas/" + adminCode + ".json";
        InputStream dataStream = ReadProGeoJson.class.getClassLoader().getResourceAsStream(filePath);
        try {
            assert dataStream != null;
            String json = IOUtils.toString(dataStream, StandardCharsets.UTF_8);
            return JSONObject.parseObject(json);
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

    public static void main(String[] args) {
        JSONObject jo = getBoundaryGeoJsonRead("420000");
        System.out.println("finish");
    }

}
