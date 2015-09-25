package com.youzijie.util;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by lei on 2015/7/23.
 */
public class StringFormatter {
    public static String format(String content, Map<String, String> templates) {
        return format(content, templates, false);
    }

    public static String format(String content, Map<String, String> templates, boolean dollar_mark) {
        Pattern pattern = Pattern.compile("\\{(.+?)\\}");
        if (dollar_mark) {
            pattern = Pattern.compile("\\$(.+?)\\$");
        }
        Matcher matcher = pattern.matcher(content);

        StringBuilder builder = new StringBuilder();
        int i = 0;
        while (matcher.find()) {
            String value = templates.get(matcher.group(1));
            builder.append(content.substring(i, matcher.start()));
            if (value == null) {
                builder.append(matcher.group(0));
            } else {
                builder.append(value);
            }
            i = matcher.end();
        }
        builder.append(content.substring(i, content.length()));
        return builder.toString();
    }
}
