package com.golaxy;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SubSql {

    public static void main(String[] args) {
        String sql = "insert into aaa select desc('file_name', file_name, 'file_id', file_id) from bbb";

        String fileName = "file_name";

        String replaceStr = "Func(" + fileName +")";

        Pattern compile = Pattern.compile(fileName);
        Matcher matcher = compile.matcher(sql);
        while (matcher.find()) {
            String group = matcher.group();
            int start = matcher.start();
            int end = matcher.end();

            System.out.println(String.format("group: %s, start: %s, end: %s", group, start, end));
        }
    }
}
