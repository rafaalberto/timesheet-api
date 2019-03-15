package br.com.api.timesheet.utils;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.format.DateTimeFormatter;

import static br.com.api.timesheet.utils.DateUtils.*;
import static java.time.LocalDateTime.parse;
import static java.time.LocalTime.ofSecondOfDay;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@ActiveProfiles("test")
public class DateUtilsTest {

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
        assertThat(ofSecondOfDay(calculateNightShift(
                parse("2019-01-01 22:00", formatter), parse("2019-01-02 06:35", formatter))).toString()).isEqualTo("07:00");
        assertThat(ofSecondOfDay(calculateNightShift(
                parse("2019-01-01 20:00", formatter), parse("2019-01-01 23:15", formatter))).toString()).isEqualTo("01:15");
        assertThat(ofSecondOfDay(calculateNightShift(
                parse("2019-01-01 21:00", formatter), parse("2019-01-02 04:00", formatter))).toString()).isEqualTo("06:00");
        assertThat(ofSecondOfDay(calculateNightShift(
                parse("2019-01-01 01:00", formatter), parse("2019-01-01 05:35", formatter))).toString()).isEqualTo("04:00");
        assertThat(ofSecondOfDay(calculateNightShift(
                parse("2019-01-01 21:50", formatter), parse("2019-01-02 00:00", formatter))).toString()).isEqualTo("02:00");
    }
}
