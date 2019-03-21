package br.com.api.timesheet.repository;

import br.com.api.timesheet.entity.TimesheetRegister;
import br.com.api.timesheet.enumeration.TimesheetTypeEnum;
import br.com.api.timesheet.utils.DateUtils;
import org.apache.commons.lang3.time.DurationFormatUtils;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.Duration;
import java.time.LocalTime;
import java.time.Period;
import java.time.format.DateTimeFormatter;

import static java.time.LocalDateTime.parse;
import static java.time.format.DateTimeFormatter.ofPattern;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
@ActiveProfiles("test")
public class TimesheetRegisterRepositoryTest {

    public static final String TIME_IN = "2019-01-01 14:18";
    public static final String LUNCH_START = "2019-01-01 19:00";
    public static final String LUNCH_END = "2019-01-01 20:00";
    public static final String TIME_OUT = "2019-01-01 22:42";

    public static final String TIME_DAY_OFF = "2019-03-10 00:00";
    public static final String TIME_OFF = "00:00";

    public static final String HOURS_WORKED = "07:24";
    public static final String HOURS_JOURNEY = "07:20";
    public static final String EXTRA_HOURS = "00:04";
    public static final String NIGHT_SHIFT = "00:42";
    public static final String PAID_NIGHT_TIME = "00:06";

    @Autowired
    private TimesheetRegisterRepository timesheetRegisterRepository;

    @Test
    public void shouldCreateTimesheetRegisterRegular() {
        TimesheetRegister registerCreated = timesheetRegisterRepository.save(getTimesheetRegisterRegular());

//        Duration duration = Duration.ofSeconds(115200);
//        assertThat(DurationFormatUtils.formatDuration(duration.toMillis(), "HH:mm")).isEqualTo("32:00");

        assertThat(registerCreated.getId()).isNotNull();
        assertThat(registerCreated.getHoursWorked().toString()).isEqualTo(HOURS_WORKED);
        assertThat(registerCreated.getHoursJourney().toString()).isEqualTo(HOURS_JOURNEY);
        assertThat(registerCreated.getExtraHours().toString()).isEqualTo(EXTRA_HOURS);
        assertThat(registerCreated.getWeeklyRest().toString()).isEqualTo(TIME_OFF);
        assertThat(registerCreated.getNightShift().toString()).isEqualTo(NIGHT_SHIFT);
        assertThat(registerCreated.getPaidNightTime().toString()).isEqualTo(PAID_NIGHT_TIME);
    }

    @Test
    public void shouldCreateTimesheetRegisterDayOff() {
        TimesheetRegister registerCreated = timesheetRegisterRepository.save(getTimesheetRegisterDayOff());
        assertThat(registerCreated.getId()).isNotNull();
        assertThat(registerCreated.getHoursWorked().toString()).isEqualTo(TIME_OFF);
        assertThat(registerCreated.getHoursJourney().toString()).isEqualTo(TIME_OFF);
        assertThat(registerCreated.getExtraHours().toString()).isEqualTo(TIME_OFF);
        assertThat(registerCreated.getWeeklyRest().toString()).isEqualTo(HOURS_JOURNEY);
        assertThat(registerCreated.getNightShift().toString()).isEqualTo(TIME_OFF);
        assertThat(registerCreated.getPaidNightTime().toString()).isEqualTo(TIME_OFF);
    }

    @Test
    public void shouldCreateTimesheetRegisterHoliday() {
        TimesheetRegister registerCreated = timesheetRegisterRepository.save(getTimesheetRegisterHoliday());
        assertThat(registerCreated.getId()).isNotNull();
        assertThat(registerCreated.getHoursWorked().toString()).isEqualTo(HOURS_WORKED);
        assertThat(registerCreated.getHoursJourney().toString()).isEqualTo(HOURS_JOURNEY);
        assertThat(registerCreated.getExtraHours().toString()).isEqualTo(HOURS_WORKED);
        assertThat(registerCreated.getWeeklyRest().toString()).isEqualTo(TIME_OFF);
        assertThat(registerCreated.getNightShift().toString()).isEqualTo(NIGHT_SHIFT);
        assertThat(registerCreated.getPaidNightTime().toString()).isEqualTo(PAID_NIGHT_TIME);
    }

    @After
    public void tearDown() {
        timesheetRegisterRepository.deleteAll();
    }

    private TimesheetRegister getTimesheetRegisterRegular() {
        DateTimeFormatter formatter = ofPattern(DateUtils.DATE_TIME_FORMAT);
        TimesheetRegister timesheetRegister = new TimesheetRegister();
        timesheetRegister.setType(TimesheetTypeEnum.REGULAR);
        timesheetRegister.setTimeIn(parse(TIME_IN, formatter));
        timesheetRegister.setLunchStart(parse(LUNCH_START, formatter));
        timesheetRegister.setLunchEnd(parse(LUNCH_END, formatter));
        timesheetRegister.setTimeOut(parse(TIME_OUT, formatter));
        timesheetRegister.setHoursJourney(LocalTime.parse(HOURS_JOURNEY, ofPattern(DateUtils.TIME_FORMAT)));
        return timesheetRegister;
    }

    private TimesheetRegister getTimesheetRegisterDayOff() {
        DateTimeFormatter formatter = ofPattern(DateUtils.DATE_TIME_FORMAT);
        TimesheetRegister timesheetRegister = new TimesheetRegister();
        timesheetRegister.setType(TimesheetTypeEnum.DAY_OFF);
        timesheetRegister.setTimeIn(parse(TIME_DAY_OFF, formatter));
        timesheetRegister.setLunchStart(parse(TIME_DAY_OFF, formatter));
        timesheetRegister.setLunchEnd(parse(TIME_DAY_OFF, formatter));
        timesheetRegister.setTimeOut(parse(TIME_DAY_OFF, formatter));
        timesheetRegister.setHoursJourney(LocalTime.parse(HOURS_JOURNEY, ofPattern(DateUtils.TIME_FORMAT)));
        return timesheetRegister;
    }

    private TimesheetRegister getTimesheetRegisterHoliday() {
        DateTimeFormatter formatter = ofPattern(DateUtils.DATE_TIME_FORMAT);
        TimesheetRegister timesheetRegister = new TimesheetRegister();
        timesheetRegister.setType(TimesheetTypeEnum.HOLIDAY);
        timesheetRegister.setTimeIn(parse(TIME_IN, formatter));
        timesheetRegister.setLunchStart(parse(LUNCH_START, formatter));
        timesheetRegister.setLunchEnd(parse(LUNCH_END, formatter));
        timesheetRegister.setTimeOut(parse(TIME_OUT, formatter));
        timesheetRegister.setHoursJourney(LocalTime.parse(HOURS_JOURNEY, ofPattern(DateUtils.TIME_FORMAT)));
        return timesheetRegister;
    }
}
