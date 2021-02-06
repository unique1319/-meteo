package com.wrh.meteo.read;

import com.hxgis.meteodata.comdata.GridData;

import java.io.File;
import java.util.List;
import java.util.Map;

/**
 * @author wrh
 * @version 1.0
 * @date 2021/2/6 9:35
 * @describe
 */
public interface ReadGridDataMap extends ReadBase {
    @SuppressWarnings("unchecked")
    @Override
    Map<String,GridData> read(File file);
}
