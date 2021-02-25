package com.wrh.meteo.topojson;

import com.wrh.meteo.util.StrUtil;
import com.wrh.meteo.util.StringUtil;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * @author wrh
 * @version 1.0
 * @date 2021/2/23 17:12
 * @describe GeoJson字符串转换为TopoJson字符串
 */
public class GetTopoJson {

    /**
     * GeoJson字符串转换为TopoJson字符串
     *
     * @param geojson GeoJson字符串
     * @return topojson
     */
    public static String convertGeoJsonToTopoJson(String geojson) {
        return convertGeoJsonToTopoJson(geojson, false);
    }

    /**
     * GeoJson字符串转换为TopoJson字符串
     *
     * @param geojson  GeoJson字符串
     * @param isFormat Json格式化
     * @return topojson
     */
    public static String convertGeoJsonToTopoJson(String geojson, boolean isFormat) {
        ScriptEngineManager manager = new ScriptEngineManager();
        ScriptEngine engine = manager.getEngineByName("javascript");
        String topology = null;
        FileReader reader = null;
        try {
            String root = System.getProperty("user.dir");
            reader = new FileReader(root + File.separator + "topojson.js");
            engine.eval(reader);
            if (engine instanceof Invocable) {
                Invocable invoke = (Invocable) engine;
                topology = (String) invoke.invokeFunction("convertGeojsonToTopojson", geojson);
            }
            if (isFormat) {
                return topology;
            } else {
                assert topology != null;
                return topology.replaceAll(StringUtil.MUlTI_SPACE_REGEX, "").replaceAll(StrUtil.CR, "");
            }
        } catch (ScriptException | NoSuchMethodException | IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
