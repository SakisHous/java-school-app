package gr.aueb.cf.schoolappsoa.service.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * A utility class that formats a string date
 * object in {@link java.util.Date} or {@link java.sql.Date}
 * and vice-versa.
 *
 * @author Thanasis Chousiadas
 */
public class DateUtil {
    private static final ThreadLocal<DateFormat> dateFormat = new ThreadLocal<>() {
        @Override
        public DateFormat get() {
            return super.get();
        }

        @Override
        protected DateFormat initialValue() {
            return new SimpleDateFormat("dd-MM-YYYY");
        }
    };

    /**
     * No instances of this should be available.
     */
    private DateUtil() {
    }

    /**
     * Formatter parse a date String to Date of
     * java.util.Date class.
     *
     * @param dateStr date in String format.
     * @return a Date object of java.util.Date class.
     * @throws ParseException
     */
    public static Date toDate(String dateStr) throws ParseException {
        return getDateFormat().parse(dateStr);
    }

    /**
     * Converts a java.util.Date object to java.sql.Date object.
     *
     * @param date java.util.Date object.
     * @return java.sql.Date object.
     */
    public static java.sql.Date toSQLDate(Date date) {
        return new java.sql.Date(date.getTime());
    }

    /**
     * Converts to String a java.util.Date object.
     *
     * @param date the given date.
     * @return a String with date.
     */
    public static String toString(Date date) {
        return getDateFormat().format(date);
    }

    /**
     * Converts a java.sql.Date object to String.
     *
     * @param date the given java.sql.Date object.
     * @return a String with date.
     */
    public static String toString(java.sql.Date date) {
        return getDateFormat().format(date);
    }

    /**
     * Returns a formatter.
     *
     * @return DateFormat formatter
     */
    public static DateFormat getDateFormat() {
        return dateFormat.get();
    }
}
