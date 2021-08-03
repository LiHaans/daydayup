package lh.test.hadoop;

import org.elasticsearch.hadoop.cfg.Settings;
import org.elasticsearch.hadoop.hive.HiveValueReader;

import org.apache.hadoop.hive.serde2.io.TimestampWritable;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Auther: lihang
 * @Date: 2021-07-29 12:45
 * @Description:
 */
public class EsValueReader extends HiveValueReader {
    private String dateFormat;
    private static final String DEFALUT_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
    private static final String DEFALUT_DATE_FORMAT1 = "yyyy-MM-dd HH:mm:ss.SSS";
    @Override
    public void setSettings(Settings settings) {
        super.setSettings(settings);
        dateFormat = settings.getProperty("es.date.format");
    }

    @Override
    protected Object parseDate(String value, boolean richDate) {
        if (value != null && value.trim().length() > 0 && DEFALUT_DATE_FORMAT.equalsIgnoreCase(dateFormat)) {
            return (richDate ? new TimestampWritable(new Timestamp(parseDate(value,
                    DEFALUT_DATE_FORMAT).getTime())) : parseString(value));
        }

        if (value != null && value.trim().length() > 0 && DEFALUT_DATE_FORMAT1.equalsIgnoreCase(dateFormat)) {
            return (richDate ? new TimestampWritable(new Timestamp(parseDate(value,
                    DEFALUT_DATE_FORMAT1).getTime())) : parseString(value));
        }

        /**如果没有设置日期格式，通过默认的方式支持，以避免使用新的ValueReader后影响到其它的外部表**/
        return super.parseDate(value, richDate);

    }

    /**
     * 解析日期,根据指定的格式进行解析.<br>
     * 如果解析错误,则返回null
     * @param stringDate 日期字符串
     * @param format 日期格式
     * @return 日期类型
     */
    private static Date parseDate(String stringDate, String format) {
        if (stringDate == null) {
            return null;
        }
        try {
            return parseDate(stringDate, new String[] { format });
        } catch (ParseException e) {
            return null;
        }
    }

    public static Date parseDate(String str, String... parsePatterns) throws ParseException {
        return parseDateWithLeniency(str, parsePatterns, true);
    }

    private static Date parseDateWithLeniency(
            String str, String[] parsePatterns, boolean lenient) throws ParseException {
        if (str == null || parsePatterns == null) {
            throw new IllegalArgumentException("Date and Patterns must not be null");
        }

        SimpleDateFormat parser = new SimpleDateFormat();
        parser.setLenient(lenient);
        ParsePosition pos = new ParsePosition(0);
        for (String parsePattern : parsePatterns) {

            String pattern = parsePattern;

            // LANG-530 - need to make sure 'ZZ' output doesn't get passed to SimpleDateFormat
            if (parsePattern.endsWith("ZZ")) {
                pattern = pattern.substring(0, pattern.length() - 1);
            }

            parser.applyPattern(pattern);
            pos.setIndex(0);

            String str2 = str;
            // LANG-530 - need to make sure 'ZZ' output doesn't hit SimpleDateFormat as it will ParseException
            if (parsePattern.endsWith("ZZ")) {
                str2 = str.replaceAll("([-+][0-9][0-9]):([0-9][0-9])$", "$1$2");
            }

            Date date = parser.parse(str2, pos);
            if (date != null && pos.getIndex() == str2.length()) {
                return date;
            }
        }
        throw new ParseException("Unable to parse the date: " + str, -1);
    }

    
}
