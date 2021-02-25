package com.wrh.meteo.component.topojson;

import com.wrh.meteo.component.geo.ReadProGeoJson;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author wrh
 * @version 1.0
 * @date 2021/2/25 9:15
 * @describe
 */
class GetTopoJsonTest {

    @Test
    void convertGeoJsonToTopoJson() throws IOException {
        String geojson = ReadProGeoJson.getBoundaryGeoJsonStringRead("420000");
        FileUtils.write(new File("C:\\Users\\Administrator\\Desktop\\test\\topojson.json"), GetTopoJson.convertGeoJsonToTopoJson(geojson), "utf-8");
    }
}