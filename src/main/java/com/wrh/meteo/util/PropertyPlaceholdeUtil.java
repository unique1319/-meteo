package com.wrh.meteo.util;

import org.springframework.util.PropertyPlaceholderHelper;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author wrh
 * @version 1.0
 * @date 2020/10/13 14:28
 */
public final class PropertyPlaceholdeUtil {

    private PropertyPlaceholdeUtil() {
    }

    private static final PropertyPlaceholderHelper fixedHelper = new PropertyPlaceholderHelper("${", "}");
    private static final PropertyPlaceholderHelper unFixedHelper = new PropertyPlaceholderHelper("#[", "]");

    public static String resolveByTimeFmt(String str, LocalDateTime t) {
        return fixedHelper.replacePlaceholders(str, fmt -> t.format(DateTimeFormatter.ofPattern(fmt)));
    }

    public static String resolveUnFixed2Regex(String str) {
        return unFixedHelper.replacePlaceholders(str, fmt -> ".*");
    }

    public static void main(String[] args) {
        String str = "Z_NWGD_C_BABJ_#[yyyyMMddHHmmss]_P_RFFC_SCMOC-TMP01_${yyyyMMddHHmm}_02401.GRB2";
        LocalDateTime now = LocalDateTime.now();
        System.out.println(resolveUnFixed2Regex(str));
        System.out.println(resolveByTimeFmt(str, now));
    }

}
