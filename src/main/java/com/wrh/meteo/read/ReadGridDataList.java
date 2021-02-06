package com.wrh.meteo.read;

import com.hxgis.meteodata.comdata.GridData;

import java.io.File;
import java.util.List;

/**
 * @author wrh
 * @version 1.0
 * @date 2021/2/6 9:35
 * @describe
 */
public interface ReadGridDataList extends ReadBase {
    @SuppressWarnings("unchecked")
    @Override
    List<GridData> read(File file);
}
