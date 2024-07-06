package utils;

import lombok.NonNull;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import java.util.TimeZone;

public interface IPropertyGet {
    Logger LOGGER = LogManager.getLogger(IPropertyGet.class);
    String DEFAULT_DATE_FORMAT = "M/d/yyyy";
    String DEFAULT_TIME_FORMAT = "h:mm a";
    String DEFAULT_DATETIME_FORMAT = "M/d/yyyy h:mm a";
    TimeZone DEFAULT_TIMEZONE = TimeZone.getTimeZone("UTC");

    default String get(String... params) {
        if (this instanceof Enum<?>) {
            String key = ((Enum<?>) this).name();
            return PropertyHelper.get(getClass(), key, params);  // Assuming this method is properly defined elsewhere
        }
        return null;
    }

    default Date parseDate(String value, String format, TimeZone timeZone) {
        DateFormat dateFormat = new SimpleDateFormat(format);
        dateFormat.setTimeZone(timeZone);
        try {
            return dateFormat.parse(value);
        } catch (ParseException e) {
            LOGGER.error("Failed to parse date: " + value + " with format: " + format, e);
            return null;
        }
    }

    default Date getDate(String format) {
        return parseDate(get(), format, DEFAULT_TIMEZONE);
    }

    default Date getDate() {
        return getDate(DEFAULT_DATE_FORMAT);
    }

    default Date getDateTime(String format, TimeZone timeZone) {
        return parseDate(get(), format, timeZone);
    }

    default Date getDateTime() {
        return getDateTime(DEFAULT_DATETIME_FORMAT, DEFAULT_TIMEZONE);
    }

    default Date getTime(String format, TimeZone timeZone) {
        return parseDate(get(), format, timeZone);
    }

    default Date getTime() {
        return getTime(DEFAULT_TIME_FORMAT, DEFAULT_TIMEZONE);
    }

    default Integer getInt() {
        try {
            return Integer.parseInt(get());
        } catch (NumberFormatException e) {
            LOGGER.error("Failed to parse integer", e);
            return null;
        }
    }

    default Long getLong() {
        try {
            return Long.parseLong(get());
        } catch (NumberFormatException e) {
            LOGGER.error("Failed to parse long", e);
            return null;
        }
    }

    default Double getDouble() {
        try {
            return Double.parseDouble(get());
        } catch (NumberFormatException e) {
            LOGGER.error("Failed to parse double", e);
            return null;
        }
    }

    default Boolean getBoolean() {
        return Boolean.parseBoolean(get());
    }
}