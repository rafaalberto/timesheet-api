package br.com.api.timesheet.utils;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.time.Duration;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeUnit;

import static br.com.api.timesheet.utils.DateUtils.*;
import static java.time.LocalDateTime.parse;
import static java.time.LocalTime.ofSecondOfDay;
import static java.time.format.DateTimeFormatter.ofPattern;
import static org.apache.commons.lang3.time.DurationFormatUtils.formatDuration;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@ActiveProfiles("test")
public class DateUtilsTest {

    private static final double TIME_IN_MINUTES = 60.0;

    private DateTimeFormatter formatter;

    @Before
    public void setUp() {
        formatter = DateTimeFormatter.ofPattern(DATE_TIME_FORMAT);
    }

    @Test
    public void shouldVerifyIfIsNightShift() {
        assertTrue(isNightShift(parse("2019-01-01 04:30", formatter), parse("2019-01-01 05:30", formatter)));
        assertFalse(isNightShift(parse("2019-01-01 08:00", formatter), parse("2019-01-01 11:00", formatter)));
        assertTrue(isNightShift(parse("2019-01-01 22:30", formatter), parse("2019-01-02 05:00", formatter)));
        assertFalse(isNightShift(parse("2019-01-01 18:00", formatter), parse("2019-01-01 22:00", formatter)));
        assertTrue(isNightShift(parse("2019-01-01 21:00", formatter), parse("2019-01-02 05:01", formatter)));
        assertFalse(isNightShift(parse("2019-01-01 05:01", formatter), parse("2019-01-01 08:00", formatter)));
    }

    @Test
    public void shoudCalculateNightShift() {
        assertThat(ofSecondOfDay(calculateNightShift(parse("2019-01-01 22:00", formatter), parse("2019-01-02 06:35", formatter))).toString()).isEqualTo("07:00");
        assertThat(ofSecondOfDay(calculateNightShift(parse("2019-01-01 20:00", formatter), parse("2019-01-01 23:15", formatter))).toString()).isEqualTo("01:15");
        assertThat(ofSecondOfDay(calculateNightShift(parse("2019-01-01 21:00", formatter), parse("2019-01-02 04:00", formatter))).toString()).isEqualTo("06:00");
        assertThat(ofSecondOfDay(calculateNightShift(parse("2019-01-01 01:00", formatter), parse("2019-01-01 05:35", formatter))).toString()).isEqualTo("04:00");
        assertThat(ofSecondOfDay(calculateNightShift(parse("2019-01-01 21:50", formatter), parse("2019-01-02 00:00", formatter))).toString()).isEqualTo("02:00");
    }


    @Test
    public void shouldCalculatePaidNightTime() {
        assertThat(formatDuration(calculatePaidNightTime(LocalTime.parse("00:42", ofPattern(TIME_FORMAT))).toMillis(), TIME_FORMAT)).isEqualTo("00:06");
        assertThat(formatDuration(calculatePaidNightTime(LocalTime.parse("01:10", ofPattern(TIME_FORMAT))).toMillis(), TIME_FORMAT)).isEqualTo("00:10");
        assertThat(formatDuration(calculatePaidNightTime(LocalTime.parse("00:33", ofPattern(TIME_FORMAT))).toMillis(), TIME_FORMAT)).isEqualTo("00:04");
        assertThat(formatDuration(calculatePaidNightTime(LocalTime.parse("00:35", ofPattern(TIME_FORMAT))).toMillis(), TIME_FORMAT)).isEqualTo("00:05");
        assertThat(formatDuration(calculatePaidNightTime(LocalTime.parse("00:40", ofPattern(TIME_FORMAT))).toMillis(), TIME_FORMAT)).isEqualTo("00:05");
        assertThat(formatDuration(calculatePaidNightTime(LocalTime.parse("07:00", ofPattern(TIME_FORMAT))).toMillis(), TIME_FORMAT)).isEqualTo("01:00");
        assertThat(formatDuration(calculatePaidNightTime(LocalTime.parse("01:20", ofPattern(TIME_FORMAT))).toMillis(), TIME_FORMAT)).isEqualTo("00:11");
    }

    @Test
    public void shouldConvertNanosecondsToDecimalHours() {
        BigInteger timeInNanoseconds = new BigInteger("27180000000000");
        long timeInMinutes = TimeUnit.NANOSECONDS.toMinutes(timeInNanoseconds.longValue());
        double timeInDecimalHours = timeInMinutes / TIME_IN_MINUTES;
        BigDecimal decimalHoursRounded = new BigDecimal(timeInDecimalHours).setScale(2, RoundingMode.HALF_UP);
        assertThat(decimalHoursRounded.doubleValue()).isEqualTo(7.55);
    }

    @Test
    public void shouldConvertStringToDuration() {
        String time = "PT01H05M";
        Duration duration = Duration.parse(time);
        assertThat(duration.toMinutes()).isEqualTo(65);
    }
}
