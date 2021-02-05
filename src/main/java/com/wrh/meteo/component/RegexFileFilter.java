package com.wrh.meteo.component;

import org.apache.commons.io.filefilter.IOFileFilter;

import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author wrh
 * @version 1.0
 * @date 2021/2/5 11:24
 */
public class RegexFileFilter implements IOFileFilter {

    private final String regex;

    public RegexFileFilter(String regex) {
        this.regex = regex;
    }

    @Override
    public boolean accept(File file, String s) {
        return false;
    }

    @Override
    public boolean accept(File file) {
        String fileName = file.getName();
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(fileName);
        return m.find();
    }
}
