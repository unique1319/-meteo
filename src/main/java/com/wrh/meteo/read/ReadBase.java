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
public interface ReadBase {
    <T> T read(File file);
}
