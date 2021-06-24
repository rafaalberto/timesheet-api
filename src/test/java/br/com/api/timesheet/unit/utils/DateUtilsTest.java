package br.com.api.timesheet.unit.utils;

import br.com.api.timesheet.utils.DateUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.lang.reflect.Constructor;
import java.math.BigInteger;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

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

  private DateTimeFormatter formatter;

  @Before
  public void setUp() {
    formatter = DateTimeFormatter.ofPattern(DATE_TIME_FORMAT);
  }

  @Test(expected = java.lang.IllegalAccessException.class)
  public void shouldThrowExceptionWhenAccessPrivateConstructor() throws Exception {
    Constructor constructor = DateUtils.class.getDeclaredConstructor();
    constructor.newInstance();
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
  public void shouldCalculateNightShift() {
    assertThat(ofSecondOfDay(calculateNightShift(parse("2019-01-01 22:00", formatter), parse("2019-01-02 06:35", formatter))).toString()).hasToString("07:00");
    assertThat(ofSecondOfDay(calculateNightShift(parse("2019-01-01 20:00", formatter), parse("2019-01-01 23:15", formatter))).toString()).hasToString("01:15");
    assertThat(ofSecondOfDay(calculateNightShift(parse("2019-01-01 21:00", formatter), parse("2019-01-02 04:00", formatter))).toString()).hasToString("06:00");
    assertThat(ofSecondOfDay(calculateNightShift(parse("2019-01-01 01:00", formatter), parse("2019-01-01 05:35", formatter))).toString()).hasToString("04:00");
    assertThat(ofSecondOfDay(calculateNightShift(parse("2019-01-01 21:50", formatter), parse("2019-01-02 00:00", formatter))).toString()).hasToString("02:00");
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
  public void shouldConvertStringToDuration() {
    assertThat(convertStringToNanos("07:33")).isEqualTo(new BigInteger("27180000000000").longValue());
  }

  @Test
  public void shouldConvertNanosecondsToDecimalHours() {
    BigInteger timeInNanoseconds = new BigInteger("27180000000000");
    assertThat(convertNanosToDecimalHours(timeInNanoseconds.longValue())).isEqualTo(7.55);
  }
}
