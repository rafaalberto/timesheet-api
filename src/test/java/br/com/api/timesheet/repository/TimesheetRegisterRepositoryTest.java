package br.com.api.timesheet.repository;

import br.com.api.timesheet.entity.TimesheetRegister;
import br.com.api.timesheet.enumeration.TimesheetTypeEnum;
import br.com.api.timesheet.utils.Constants;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
@ActiveProfiles("test")
public class TimesheetRegisterRepositoryTest {

    public static final String TIME_IN = "2019-03-09 05:35";
    public static final String LUNCH_START = "2019-03-09 08:05";
    public static final String LUNCH_END = "2019-03-09 11:05";
    public static final String TIME_OUT = "2019-03-09 19:00";

    public static final String TIME_DAY_OFF = "2019-03-10 00:00";
    public static final String TIME_OFF = "00:00";

    public static final String HOURS_WORKED = "10:25";
    public static final String HOURS_JOURNEY = "08:00";
    public static final String EXTRA_HOURS = "02:25";

    @Autowired
    private TimesheetRegisterRepository timesheetRegisterRepository;

    @Test
    public void shouldCreateTimesheetRegisterRegular() {
        TimesheetRegister registerCreated = timesheetRegisterRepository.save(getTimesheetRegisterRegular());
        assertThat(registerCreated.getId()).isNotNull();
        assertThat(registerCreated.getHoursWorked().toString()).isEqualTo(HOURS_WORKED);
        assertThat(registerCreated.getHoursJourney().toString()).isEqualTo(HOURS_JOURNEY);
        assertThat(registerCreated.getExtraHours().toString()).isEqualTo(EXTRA_HOURS);
        assertThat(registerCreated.getWeeklyRest().toString()).isEqualTo(TIME_OFF);
    }

    @Test
    public void shouldCreateTimesheetRegisterDayOff() {
        TimesheetRegister registerCreated = timesheetRegisterRepository.save(getTimesheetRegisterDayOff());
        assertThat(registerCreated.getId()).isNotNull();
        assertThat(registerCreated.getHoursWorked().toString()).isEqualTo(TIME_OFF);
        assertThat(registerCreated.getHoursJourney().toString()).isEqualTo(TIME_OFF);
        assertThat(registerCreated.getExtraHours().toString()).isEqualTo(TIME_OFF);
        assertThat(registerCreated.getWeeklyRest().toString()).isEqualTo(HOURS_JOURNEY);
    }

    @Test
    public void shouldCreateTimesheetRegisterHoliday() {
        TimesheetRegister registerCreated = timesheetRegisterRepository.save(getTimesheetRegisterHoliday());
        assertThat(registerCreated.getId()).isNotNull();
        assertThat(registerCreated.getHoursWorked().toString()).isEqualTo(HOURS_WORKED);
        assertThat(registerCreated.getHoursJourney().toString()).isEqualTo(HOURS_JOURNEY);
        assertThat(registerCreated.getExtraHours().toString()).isEqualTo(HOURS_WORKED);
        assertThat(registerCreated.getWeeklyRest().toString()).isEqualTo(TIME_OFF);
    }

    @After
    public void tearDown() {
        timesheetRegisterRepository.deleteAll();
    }

    private TimesheetRegister getTimesheetRegisterRegular() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(Constants.DATE_TIME_FORMAT);
        TimesheetRegister timesheetRegister = new TimesheetRegister();
        timesheetRegister.setTypeEnum(TimesheetTypeEnum.REGULAR);
        timesheetRegister.setTimeIn(LocalDateTime.parse(TIME_IN, formatter));
        timesheetRegister.setLunchStart(LocalDateTime.parse(LUNCH_START, formatter));
        timesheetRegister.setLunchEnd(LocalDateTime.parse(LUNCH_END, formatter));
        timesheetRegister.setTimeOut(LocalDateTime.parse(TIME_OUT, formatter));
        timesheetRegister.setHoursJourney(LocalTime.parse(HOURS_JOURNEY, DateTimeFormatter.ofPattern(Constants.TIME_FORMAT)));
        return timesheetRegister;
    }

    private TimesheetRegister getTimesheetRegisterDayOff() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(Constants.DATE_TIME_FORMAT);
        TimesheetRegister timesheetRegister = new TimesheetRegister();
        timesheetRegister.setTypeEnum(TimesheetTypeEnum.DAY_OFF);
        timesheetRegister.setTimeIn(LocalDateTime.parse(TIME_DAY_OFF, formatter));
        timesheetRegister.setLunchStart(LocalDateTime.parse(TIME_DAY_OFF, formatter));
        timesheetRegister.setLunchEnd(LocalDateTime.parse(TIME_DAY_OFF, formatter));
        timesheetRegister.setTimeOut(LocalDateTime.parse(TIME_DAY_OFF, formatter));
        timesheetRegister.setHoursJourney(LocalTime.parse(HOURS_JOURNEY, DateTimeFormatter.ofPattern(Constants.TIME_FORMAT)));
        return timesheetRegister;
    }

    private TimesheetRegister getTimesheetRegisterHoliday() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(Constants.DATE_TIME_FORMAT);
        TimesheetRegister timesheetRegister = new TimesheetRegister();
        timesheetRegister.setTypeEnum(TimesheetTypeEnum.HOLIDAY);
        timesheetRegister.setTimeIn(LocalDateTime.parse(TIME_IN, formatter));
        timesheetRegister.setLunchStart(LocalDateTime.parse(LUNCH_START, formatter));
        timesheetRegister.setLunchEnd(LocalDateTime.parse(LUNCH_END, formatter));
        timesheetRegister.setTimeOut(LocalDateTime.parse(TIME_OUT, formatter));
        timesheetRegister.setHoursJourney(LocalTime.parse(HOURS_JOURNEY, DateTimeFormatter.ofPattern(Constants.TIME_FORMAT)));
        return timesheetRegister;
    }

}
