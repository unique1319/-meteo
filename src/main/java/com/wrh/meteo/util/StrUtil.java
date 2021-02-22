package com.wrh.meteo.util;

/**
 * @author wrh
 * @version 1.0
 * @date 2021/2/5 10:25
 * @describe
 */
public class StrUtil {

    public static final String SPACE = " ";
    public static final String TAB = "\t";
    public static final String DOT = ".";
    public static final String DOUBLE_DOT = "..";
    public static final String SLASH = "/";
    public static final String BACKSLASH = "\\";
    public static final String EMPTY = "";
    public static final String NULL = "null";
    public static final String CR = "\r";
    public static final String LF = "\n";
    public static final String CRLF = "\r\n";
    public static final String UNDERLINE = "_";
    public static final String DASHED = "-";
    public static final String COMMA = ",";
    public static final String DELIM_START = "{";
    public static final String DELIM_END = "}";
    public static final String BRACKET_START = "[";
    public static final String BRACKET_END = "]";
    public static final String COLON = ":";
    public static final String AT = "@";
    public static final String HTML_NBSP = "&nbsp;";
    public static final String HTML_AMP = "&amp;";
    public static final String HTML_QUOTE = "&quot;";
    public static final String HTML_APOS = "&apos;";
    public static final String HTML_LT = "&lt;";
    public static final String HTML_GT = "&gt;";
    public static final String EMPTY_JSON = "{}";

    public static String repeat(CharSequence str, int count) {
        if (null == str) {
            return null;
        } else if (count <= 0) {
            return "";
        } else if (count != 1 && str.length() != 0) {
            int len = str.length();
            long longSize = (long) len * (long) count;
            int size = (int) longSize;
            if ((long) size != longSize) {
                throw new ArrayIndexOutOfBoundsException("Required String length is too large: " + longSize);
            } else {
                char[] array = new char[size];
                str.toString().getChars(0, len, array, 0);
                int n;
                for (n = len; n < size - n; n <<= 1) {
                    System.arraycopy(array, 0, array, n, n);
                }
                System.arraycopy(array, 0, array, n, size - n);
                return new String(array);
            }
        } else {
            return str.toString();
        }
    }

    public static void main(String[] args) {
        System.out.println(repeat("t",3));
    }
}
