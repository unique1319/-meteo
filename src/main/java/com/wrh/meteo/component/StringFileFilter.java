package com.wrh.meteo.component;

import org.apache.commons.io.filefilter.IOFileFilter;

import java.io.File;

/**
 * @author wrh
 * @version 1.0
 * @date 2021/2/5 11:24
 */
public class StringFileFilter implements IOFileFilter {

    private final String strFilter;

    public StringFileFilter(String strFilter) {
        this.strFilter = strFilter;
    }

    @Override
    public boolean accept(File file, String s) {
        return false;
    }

    @Override
    public boolean accept(File file) {
        String fileName = file.getName();
        return fileName.contains(strFilter);
    }

}
