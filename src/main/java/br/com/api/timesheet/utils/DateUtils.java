package br.com.api.timesheet.utils;

import java.time.LocalDateTime;
import java.time.LocalTime;

import static java.time.LocalDateTime.of;
import static java.time.format.DateTimeFormatter.ofPattern;

public class DateUtils {

    public static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm";
    public static final String TIME_FORMAT = "HH:mm";
    public static final String START_HOUR_NIGHT_SHIFT = "22:00";
    public static final String END_HOUR_NIGHT_SHIFT = "05:00";

    public static boolean isNightShift(LocalDateTime startPeriod, LocalDateTime endPeriod) {
        LocalDateTime startNightShift = of(startPeriod.toLocalDate(), LocalTime.parse(START_HOUR_NIGHT_SHIFT, ofPattern(DateUtils.TIME_FORMAT)));
        LocalDateTime endNightShift = of(startPeriod.toLocalDate(), LocalTime.parse(END_HOUR_NIGHT_SHIFT, ofPattern(DateUtils.TIME_FORMAT)));

        return startPeriod.isAfter(startNightShift) || startPeriod.isBefore(endNightShift) ||
                endPeriod.isAfter(startNightShift) || endPeriod.isBefore(endNightShift);
    }
}
