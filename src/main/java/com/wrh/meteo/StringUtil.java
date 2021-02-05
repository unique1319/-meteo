package com.wrh.meteo;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author wrh
 * @version 1.0
 * @date 2021/2/5 10:25
 */
public class StringUtil {

    public final static String DOLLAR_DOLLAR_REGEX = "(?<=\\u0024\\{).*?(?=})"; // 正则匹配${}表达式

    /**
     * 根据正则匹配字符串
     *
     * @param str   字符串
     * @param regex 匹配的正则
     * @return List<String> 匹配到的字符串集合
     */
    public static List<String> getDollarModels(String str, String regex) {
        Pattern r = Pattern.compile(regex);
        Matcher m = r.matcher(str);
        List<String> matchStrs = new ArrayList<>();
        while (m.find()) {
            matchStrs.add(m.group());
        }
        return matchStrs;
    }

    public static void main(String[] args) {
        String str = "E:/data/${yyyy}/{MM}/${dd}/";
        List<String> models = getDollarModels(str, DOLLAR_DOLLAR_REGEX);
        System.out.println(models.toString());
    }
}
