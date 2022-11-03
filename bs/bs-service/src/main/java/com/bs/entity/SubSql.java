package com.bs.entity;

import io.netty.util.internal.StringUtil;
import org.apache.commons.lang3.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SubSql {

    public static void main(String[] args) {
        String sql = "insert into aaa select desc('file_name', file_name, 'file_id', file_id) from bbb";

        StringBuffer stringBuffer = new StringBuffer(sql);

        String fileName = "insert";

        String replaceStr = "Func(" + fileName +")";

        Pattern compile = Pattern.compile(fileName);
        Matcher matcher = compile.matcher(stringBuffer);
        while (matcher.find()) {
            String group = matcher.group();
            int start = matcher.start();
            int end = matcher.end();

            System.out.println(String.format("group: %s, start: %s, value: %s, end: %s, value: %s", group, start,stringBuffer.charAt(start), end, stringBuffer.charAt(end)));
            System.out.println(" ".equals(stringBuffer.charAt(start)));
            System.out.println(" ".equals(stringBuffer.charAt(end)));
            if ( !(((start-1)>0 && (Character.isLetterOrDigit(stringBuffer.charAt(start-1)) || "'".equals((String.valueOf(stringBuffer.charAt(start - 1)))))
            || (end< stringBuffer.length() && (Character.isLetterOrDigit(stringBuffer.charAt(end)) || "'".equals(String.valueOf(stringBuffer.charAt(end)))))) )
            ) {
                stringBuffer.replace(start, end, replaceStr);
            }

        }

        System.out.println(stringBuffer.toString());
    }
}
