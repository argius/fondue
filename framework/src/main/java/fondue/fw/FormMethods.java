package fondue.fw;

import java.sql.Timestamp;
import java.text.ParseException;
import java.util.Date;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.commons.lang3.time.FastDateFormat;

public interface FormMethods {

    default <T> String convertFrom(Class<? extends T> c, Object value) {
        if (value == null) {
            return "";
        }
        if (value instanceof Date) {
            return FastDateFormat.getInstance("yyyyMMddHHmmss").format(value);
        }
        if (value instanceof String) {
            return (String) value;
        }
        if (value instanceof CharSequence) {
            return String.valueOf(value);
        }
        if (value instanceof Number) {
            return String.valueOf(value);
        }
        return String.valueOf(value);
    }

    default <T> T convertTo(Class<? extends T> c, String value) {
        try {
            if (StringUtils.isEmpty(value)) {
                return null;
            }
            if (c == String.class) {
                return c.cast(value);
            }
            if (c == Short.class) {
                if (StringUtils.isBlank(value)) {
                    return null;
                }
                return c.cast(Short.parseShort(value));
            }
            if (c == Integer.class) {
                if (StringUtils.isBlank(value)) {
                    return null;
                }
                return c.cast(Integer.parseInt(value));
            }
            if (c == Long.class) {
                if (StringUtils.isBlank(value)) {
                    return null;
                }
                return c.cast(Long.parseLong(value));
            }
            if (c == Timestamp.class) {
                if (StringUtils.isBlank(value)) {
                    return null;
                }
                return c.cast(new Timestamp(DateUtils.parseDate(value, "yyyyMMddHHmmss").getTime()));
            }
        } catch (ParseException e) {
            // ignore, so far
        }
        return (T) null;
    }

    default String toString(FormMethods o) {
        return ReflectionToStringBuilder.toString(o);
    }
}
