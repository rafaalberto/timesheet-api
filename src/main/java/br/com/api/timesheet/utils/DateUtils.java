package br.com.api.timesheet.utils;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static java.time.Duration.between;
import static java.time.LocalDateTime.of;
import static java.time.format.DateTimeFormatter.ofPattern;

public class DateUtils {

    public static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm";
    public static final String DATE_FORMAT_PT_BR = "dd/MM/yyyy";
    public static final String TIME_FORMAT = "HH:mm";
    public static final String START_TIME_NIGHT_SHIFT = "22:00";
    public static final String END_TIME_NIGHT_SHIFT = "05:00";

    public static final int START_HOUR_NIGHT_SHIFT = 22;
    public static final int END_HOUR_NIGHT_SHIFT = 5;

    public static final String FULL_NIGHT_SHIFT_TIME = "07:00";
    public static final int MINUTES = 60;
    public static final int SECONDS = 60;

    public static boolean isNightShift(LocalDateTime startPeriod, LocalDateTime endPeriod) {
        LocalDateTime startNightShift = of(startPeriod.toLocalDate(), LocalTime.parse(START_TIME_NIGHT_SHIFT, ofPattern(DateUtils.TIME_FORMAT)));
        LocalDateTime endNightShift = of(startPeriod.toLocalDate(), LocalTime.parse(END_TIME_NIGHT_SHIFT, ofPattern(DateUtils.TIME_FORMAT)));

        return startPeriod.isAfter(startNightShift) || startPeriod.isBefore(endNightShift) ||
                endPeriod.isAfter(startNightShift) || endPeriod.isBefore(endNightShift);
    }

    public static long calculateNightShift(LocalDateTime startDateTime, LocalDateTime endDateTime) {
        if (isStartOutOfNightShift(startDateTime)) {
            startDateTime = startDateTime.withHour(START_HOUR_NIGHT_SHIFT).withMinute(BigDecimal.ZERO.intValue());
        }

        if (isEndOutOfNightShift(endDateTime)) {
            endDateTime = endDateTime.withHour(END_HOUR_NIGHT_SHIFT).withMinute(BigDecimal.ZERO.intValue());
        }

        return between(startDateTime, endDateTime).getSeconds();
    }

    public static Duration calculatePaidNightTime(LocalTime workedNightShift) {
        LocalTime fullNightShift = LocalTime.parse(FULL_NIGHT_SHIFT_TIME, ofPattern(DateUtils.TIME_FORMAT));
        double paidNightTime = ((double) workedNightShift.toSecondOfDay() / (double) fullNightShift.toSecondOfDay()) * MINUTES * SECONDS;
        return Duration.ofSeconds((long) paidNightTime);
    }

    public static long convertStringtoNanos(String timeInString) {
        String timeArray[] = timeInString.split(":");
        String time = "PT" + timeArray[0] + "H" + timeArray[1] + "M";
        Duration duration = Duration.parse(time);
        return duration.toNanos();
    }

    private static boolean isStartOutOfNightShift(LocalDateTime startDateTime) {
        return !startDateTime.isBefore(startDateTime.withHour(END_HOUR_NIGHT_SHIFT).withMinute(BigDecimal.ZERO.intValue()))
                && startDateTime.isBefore(startDateTime.withHour(START_HOUR_NIGHT_SHIFT).withMinute(BigDecimal.ZERO.intValue()));
    }

    private static boolean isEndOutOfNightShift(LocalDateTime endDateTime) {
        return endDateTime.isAfter(endDateTime.withHour(END_HOUR_NIGHT_SHIFT).withMinute(BigDecimal.ZERO.intValue()))
                && !endDateTime.isAfter(endDateTime.withHour(START_HOUR_NIGHT_SHIFT).withMinute(BigDecimal.ZERO.intValue()));
    }

}
