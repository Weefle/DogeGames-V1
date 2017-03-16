package net.dogegames.dogebungee.helper;

import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class DateHelper {
    public static final SimpleDateFormat UNTIL_FORMAT = new SimpleDateFormat("EEE dd MMM yyyy HH:mm", new DateFormatSymbols(Locale.FRENCH));
    private static final Pattern TIME_PATTERN = Pattern.compile("(?:(?<years>\\d++)y)?+(?:(?<months>\\d++)mo)?+(?:(?<days>\\d++)d)?+(?:(?<hours>\\d++)h)?+(?:(?<minutes>\\d++)m)?+(?:(?<seconds>\\d++)s)?+", Pattern.CASE_INSENSITIVE);
    private static final Pattern TIME_PATTERN_SPLIT = Pattern.compile("(\\d++(:?y|mo|d|h|m|s))", Pattern.CASE_INSENSITIVE);

    // We prevent fdp
    private DateHelper() {
    }

    /**
     * @param input A date (1y2d...)
     * @return The calendar.
     */
    public static Calendar parseDiffCalendar(String input) {
        boolean valid = false;
        Calendar calendar = Calendar.getInstance();
        Matcher m = TIME_PATTERN_SPLIT.matcher(input);

        while (m.find()) {
            Matcher matcher = TIME_PATTERN.matcher(input.substring(m.start(), m.end()));
            if (matcher.find()) {
                valid = true;
                calendar.add(Calendar.YEAR, parseInt(matcher.group("years")));
                calendar.add(Calendar.MONTH, parseInt(matcher.group("months")));
                calendar.add(Calendar.DAY_OF_YEAR, parseInt(matcher.group("days")));
                calendar.add(Calendar.HOUR, parseInt(matcher.group("hours")));
                calendar.add(Calendar.MINUTE, parseInt(matcher.group("minutes")));
                calendar.add(Calendar.SECOND, parseInt(matcher.group("seconds")));
            }
        }
        return valid ? calendar : null;
    }

    private static int parseInt(String s) {
        return s == null ? 0 : Integer.parseInt(s);
    }
}
