package br.com.api.timesheet.utils;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import static java.time.LocalDateTime.parse;
import java.time.format.DateTimeFormatter;

@RunWith(SpringRunner.class)
@ActiveProfiles("test")
public class DateUtilsTest {

    @Test
    public void shouldVerifyIfIsNightShift() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DateUtils.DATE_TIME_FORMAT);
        assertTrue(new DateUtils().isNightShift(parse("2019-01-01 08:00", formatter), parse("2019-01-01 23:00", formatter)));
        assertFalse(new DateUtils().isNightShift(parse("2019-01-01 08:00", formatter), parse("2019-01-01 11:00", formatter)));
        assertTrue(new DateUtils().isNightShift(parse("2019-01-01 22:30", formatter), parse("2019-01-02 05:00", formatter)));
        assertFalse(new DateUtils().isNightShift(parse("2019-01-01 18:00", formatter), parse("2019-01-01 22:00", formatter)));
        assertTrue(new DateUtils().isNightShift(parse("2019-01-01 21:00", formatter), parse("2019-01-02 05:01", formatter)));
        assertFalse(new DateUtils().isNightShift(parse("2019-01-01 05:01", formatter), parse("2019-01-01 08:00", formatter)));
    }
}
