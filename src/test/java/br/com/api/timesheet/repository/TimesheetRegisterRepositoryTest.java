package br.com.api.timesheet.repository;

import br.com.api.timesheet.dto.TimesheetReport;
import br.com.api.timesheet.entity.TimesheetRegister;
import br.com.api.timesheet.utils.DateUtils;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Collection;

import static br.com.api.timesheet.enumeration.TimesheetTypeEnum.*;
import static java.time.Duration.ofSeconds;
import static java.time.LocalDateTime.parse;
import static java.time.format.DateTimeFormatter.ofPattern;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
@ActiveProfiles("test")
public class TimesheetRegisterRepositoryTest {

    @Autowired
    private TimesheetRegisterRepository timesheetRegisterRepository;

    @Test
    public void shouldCreateTimesheetRegisterRegular1() {
        TimesheetRegister registerCreated = timesheetRegisterRepository.save(getTimesheetRegisterRegular1());
        assertThat(registerCreated.getId()).isNotNull();
        assertThat(registerCreated.getHoursWorked()).isEqualTo("09:00");
        assertThat(registerCreated.getHoursJourney()).isEqualTo("08:00");
        assertThat(registerCreated.getExtraHours()).isEqualTo("01:00");
        assertThat(registerCreated.getWeeklyRest()).isEqualTo("00:00");
        assertThat(registerCreated.getNightShift()).isEqualTo("00:00");
        assertThat(registerCreated.getPaidNightTime()).isEqualTo("00:00");
    }

    @Test
    public void shouldCreateTimesheetRegisterRegular2() {
        TimesheetRegister registerCreated = timesheetRegisterRepository.save(getTimesheetRegisterRegular2());
        assertThat(registerCreated.getId()).isNotNull();
        assertThat(registerCreated.getHoursWorked()).isEqualTo("08:30");
        assertThat(registerCreated.getHoursJourney()).isEqualTo("08:00");
        assertThat(registerCreated.getExtraHours()).isEqualTo("00:30");
        assertThat(registerCreated.getWeeklyRest()).isEqualTo("00:00");
        assertThat(registerCreated.getNightShift()).isEqualTo("01:30");
        assertThat(registerCreated.getPaidNightTime()).isEqualTo("00:12");
    }

    @Test
    public void shouldCreateTimesheetRegisterRegular3() {
        TimesheetRegister registerCreated = timesheetRegisterRepository.save(getTimesheetRegisterRegular3());
        assertThat(registerCreated.getId()).isNotNull();
        assertThat(registerCreated.getHoursWorked()).isEqualTo("09:00");
        assertThat(registerCreated.getHoursJourney()).isEqualTo("08:00");
        assertThat(registerCreated.getExtraHours()).isEqualTo("01:00");
        assertThat(registerCreated.getWeeklyRest()).isEqualTo("00:00");
        assertThat(registerCreated.getNightShift()).isEqualTo("06:00");
        assertThat(registerCreated.getPaidNightTime()).isEqualTo("00:51");
    }

    @Test
    public void shouldCreateTimesheetRegisterRegular4() {
        TimesheetRegister registerCreated = timesheetRegisterRepository.save(getTimesheetRegisterRegular4());
        assertThat(registerCreated.getId()).isNotNull();
        assertThat(registerCreated.getHoursWorked()).isEqualTo("05:00");
        assertThat(registerCreated.getHoursJourney()).isEqualTo("08:00");
        assertThat(registerCreated.getExtraHours()).isEqualTo("00:00");
        assertThat(registerCreated.getWeeklyRest()).isEqualTo("00:00");
        assertThat(registerCreated.getNightShift()).isEqualTo("05:00");
        assertThat(registerCreated.getPaidNightTime()).isEqualTo("00:42");
    }

    @Test
    public void shouldCreateTimesheetRegisterDayOff1() {
        TimesheetRegister registerCreated = timesheetRegisterRepository.save(getTimesheetRegisterDayOff1());
        assertThat(registerCreated.getId()).isNotNull();
        assertThat(registerCreated.getHoursWorked()).isEqualTo("00:00");
        assertThat(registerCreated.getHoursJourney()).isEqualTo("00:00");
        assertThat(registerCreated.getExtraHours()).isEqualTo("00:00");
        assertThat(registerCreated.getWeeklyRest()).isEqualTo("08:00");
        assertThat(registerCreated.getNightShift()).isEqualTo("00:00");
        assertThat(registerCreated.getPaidNightTime()).isEqualTo("00:00");
    }

    @Test
    public void shouldCreateTimesheetRegisterDayOff2() {
        TimesheetRegister registerCreated = timesheetRegisterRepository.save(getTimesheetRegisterDayOff2());
        assertThat(registerCreated.getId()).isNotNull();
        assertThat(registerCreated.getHoursWorked()).isEqualTo("00:00");
        assertThat(registerCreated.getHoursJourney()).isEqualTo("00:00");
        assertThat(registerCreated.getExtraHours()).isEqualTo("00:00");
        assertThat(registerCreated.getWeeklyRest()).isEqualTo("08:00");
        assertThat(registerCreated.getNightShift()).isEqualTo("00:00");
        assertThat(registerCreated.getPaidNightTime()).isEqualTo("00:00");
    }

    @Test
    public void shouldCreateTimesheetRegisterHoliday1() {
        TimesheetRegister registerCreated = timesheetRegisterRepository.save(getTimesheetRegisterHoliday1());
        assertThat(registerCreated.getId()).isNotNull();
        assertThat(registerCreated.getHoursWorked()).isEqualTo("07:00");
        assertThat(registerCreated.getHoursJourney()).isEqualTo("08:00");
        assertThat(registerCreated.getExtraHours()).isEqualTo("07:00");
        assertThat(registerCreated.getWeeklyRest()).isEqualTo("00:00");
        assertThat(registerCreated.getNightShift()).isEqualTo("06:00");
        assertThat(registerCreated.getPaidNightTime()).isEqualTo("00:51");
    }

    @Test
    public void shoudSaveAndListReport() {
        timesheetRegisterRepository.save(getTimesheetRegisterRegular1());
        timesheetRegisterRepository.save(getTimesheetRegisterRegular2());
        timesheetRegisterRepository.save(getTimesheetRegisterRegular3());
        timesheetRegisterRepository.save(getTimesheetRegisterRegular4());
        timesheetRegisterRepository.save(getTimesheetRegisterDayOff1());
        timesheetRegisterRepository.save(getTimesheetRegisterDayOff2());
        timesheetRegisterRepository.save(getTimesheetRegisterHoliday1());

        Collection<TimesheetReport> reports = timesheetRegisterRepository.listReport();

        assertRegularTimesheet(reports);
        assertDayOffTimesheet(reports);
        assertHolidayTimesheet(reports);
    }

    @After
    public void tearDown() {
        timesheetRegisterRepository.deleteAll();
    }

    private TimesheetRegister getTimesheetRegisterRegular1() {
        DateTimeFormatter formatter = ofPattern(DateUtils.DATE_TIME_FORMAT);
        TimesheetRegister timesheetRegister = new TimesheetRegister();
        timesheetRegister.setType(REGULAR);
        timesheetRegister.setTimeIn(parse("2019-01-01 08:00", formatter));
        timesheetRegister.setLunchStart(parse("2019-01-01 12:00", formatter));
        timesheetRegister.setLunchEnd(parse("2019-01-01 13:00", formatter));
        timesheetRegister.setTimeOut(parse("2019-01-01 18:00", formatter));
        timesheetRegister.setHoursJourney(ofSeconds(LocalTime.parse("08:00", ofPattern(DateUtils.TIME_FORMAT)).toSecondOfDay()));
        timesheetRegister.setSumula90(ofSeconds(LocalTime.parse("01:00", ofPattern(DateUtils.TIME_FORMAT)).toSecondOfDay()));
        return timesheetRegister;
    }

    private TimesheetRegister getTimesheetRegisterRegular2() {
        DateTimeFormatter formatter = ofPattern(DateUtils.DATE_TIME_FORMAT);
        TimesheetRegister timesheetRegister = new TimesheetRegister();
        timesheetRegister.setType(REGULAR);
        timesheetRegister.setTimeIn(parse("2019-01-02 14:00", formatter));
        timesheetRegister.setLunchStart(parse("2019-01-02 19:00", formatter));
        timesheetRegister.setLunchEnd(parse("2019-01-02 20:00", formatter));
        timesheetRegister.setTimeOut(parse("2019-01-02 23:30", formatter));
        timesheetRegister.setHoursJourney(ofSeconds(LocalTime.parse("08:00", ofPattern(DateUtils.TIME_FORMAT)).toSecondOfDay()));
        timesheetRegister.setSumula90(ofSeconds(LocalTime.parse("01:00", ofPattern(DateUtils.TIME_FORMAT)).toSecondOfDay()));
        return timesheetRegister;
    }

    private TimesheetRegister getTimesheetRegisterRegular3() {
        DateTimeFormatter formatter = ofPattern(DateUtils.DATE_TIME_FORMAT);
        TimesheetRegister timesheetRegister = new TimesheetRegister();
        timesheetRegister.setType(REGULAR);
        timesheetRegister.setTimeIn(parse("2019-01-03 21:30", formatter));
        timesheetRegister.setLunchStart(parse("2019-01-04 02:00", formatter));
        timesheetRegister.setLunchEnd(parse("2019-01-04 03:00", formatter));
        timesheetRegister.setTimeOut(parse("2019-01-04 07:30", formatter));
        timesheetRegister.setHoursJourney(ofSeconds(LocalTime.parse("08:00", ofPattern(DateUtils.TIME_FORMAT)).toSecondOfDay()));
        timesheetRegister.setSumula90(ofSeconds(LocalTime.parse("01:00", ofPattern(DateUtils.TIME_FORMAT)).toSecondOfDay()));
        return timesheetRegister;
    }

    private TimesheetRegister getTimesheetRegisterRegular4() {
        DateTimeFormatter formatter = ofPattern(DateUtils.DATE_TIME_FORMAT);
        TimesheetRegister timesheetRegister = new TimesheetRegister();
        timesheetRegister.setType(REGULAR);
        timesheetRegister.setTimeIn(parse("2019-01-04 23:00", formatter));
        timesheetRegister.setLunchStart(parse("2019-01-05 02:00", formatter));
        timesheetRegister.setLunchEnd(parse("2019-01-05 03:00", formatter));
        timesheetRegister.setTimeOut(parse("2019-01-05 05:00", formatter));
        timesheetRegister.setHoursJourney(ofSeconds(LocalTime.parse("08:00", ofPattern(DateUtils.TIME_FORMAT)).toSecondOfDay()));
        timesheetRegister.setSumula90(ofSeconds(LocalTime.parse("01:00", ofPattern(DateUtils.TIME_FORMAT)).toSecondOfDay()));
        return timesheetRegister;
    }

    private TimesheetRegister getTimesheetRegisterDayOff1() {
        DateTimeFormatter formatter = ofPattern(DateUtils.DATE_TIME_FORMAT);
        TimesheetRegister timesheetRegister = new TimesheetRegister();
        timesheetRegister.setType(DAY_OFF);
        timesheetRegister.setTimeIn(parse("2019-01-05 00:00", formatter));
        timesheetRegister.setLunchStart(parse("2019-01-05 00:00", formatter));
        timesheetRegister.setLunchEnd(parse("2019-01-05 00:00", formatter));
        timesheetRegister.setTimeOut(parse("2019-01-05 00:00", formatter));
        timesheetRegister.setHoursJourney(ofSeconds(LocalTime.parse("08:00", ofPattern(DateUtils.TIME_FORMAT)).toSecondOfDay()));
        timesheetRegister.setSumula90(ofSeconds(0));
        return timesheetRegister;
    }

    private TimesheetRegister getTimesheetRegisterDayOff2() {
        DateTimeFormatter formatter = ofPattern(DateUtils.DATE_TIME_FORMAT);
        TimesheetRegister timesheetRegister = new TimesheetRegister();
        timesheetRegister.setType(DAY_OFF);
        timesheetRegister.setTimeIn(parse("2019-01-06 00:00", formatter));
        timesheetRegister.setLunchStart(parse("2019-01-06 00:00", formatter));
        timesheetRegister.setLunchEnd(parse("2019-01-06 00:00", formatter));
        timesheetRegister.setTimeOut(parse("2019-01-06 00:00", formatter));
        timesheetRegister.setHoursJourney(ofSeconds(LocalTime.parse("08:00", ofPattern(DateUtils.TIME_FORMAT)).toSecondOfDay()));
        timesheetRegister.setSumula90(ofSeconds(0));
        return timesheetRegister;
    }

    private TimesheetRegister getTimesheetRegisterHoliday1() {
        DateTimeFormatter formatter = ofPattern(DateUtils.DATE_TIME_FORMAT);
        TimesheetRegister timesheetRegister = new TimesheetRegister();
        timesheetRegister.setType(HOLIDAY);
        timesheetRegister.setTimeIn(parse("2019-01-07 22:00", formatter));
        timesheetRegister.setLunchStart(parse("2019-01-08 02:00", formatter));
        timesheetRegister.setLunchEnd(parse("2019-01-08 03:00", formatter));
        timesheetRegister.setTimeOut(parse("2019-01-08 06:00", formatter));
        timesheetRegister.setHoursJourney(ofSeconds(LocalTime.parse("08:00", ofPattern(DateUtils.TIME_FORMAT)).toSecondOfDay()));
        timesheetRegister.setSumula90(ofSeconds(LocalTime.parse("01:00", ofPattern(DateUtils.TIME_FORMAT)).toSecondOfDay()));
        return timesheetRegister;
    }

    private void assertRegularTimesheet(Collection<TimesheetReport> reports) {
        TimesheetReport regular = reports.stream().filter(f -> f.getType().equals(REGULAR)).findFirst().get();
        assertThat(regular.getHoursWorkedFormatted()).isEqualTo("31:30");
        assertThat(regular.getHoursJourneyFormatted()).isEqualTo("32:00");
        assertThat(regular.getWeeklyRestFormatted()).isEqualTo("00:00");
        assertThat(regular.getExtraHoursFormatted()).isEqualTo("02:30");
        assertThat(regular.getSumula90Formatted()).isEqualTo("04:00");
        assertThat(regular.getNightShiftFormatted()).isEqualTo("12:30");
        assertThat(regular.getPaidNightTimeFormatted()).isEqualTo("01:47");
    }

    private void assertDayOffTimesheet(Collection<TimesheetReport> reports) {
        TimesheetReport dayOff = reports.stream().filter(f -> f.getType().equals(DAY_OFF)).findFirst().get();
        assertThat(dayOff.getHoursWorkedFormatted()).isEqualTo("00:00");
        assertThat(dayOff.getHoursJourneyFormatted()).isEqualTo("00:00");
        assertThat(dayOff.getWeeklyRestFormatted()).isEqualTo("16:00");
        assertThat(dayOff.getExtraHoursFormatted()).isEqualTo("00:00");
        assertThat(dayOff.getSumula90Formatted()).isEqualTo("00:00");
        assertThat(dayOff.getNightShiftFormatted()).isEqualTo("00:00");
        assertThat(dayOff.getPaidNightTimeFormatted()).isEqualTo("00:00");
    }

    private void assertHolidayTimesheet(Collection<TimesheetReport> reports) {
        TimesheetReport holiday = reports.stream().filter(f -> f.getType().equals(HOLIDAY)).findFirst().get();
        assertThat(holiday.getHoursWorkedFormatted()).isEqualTo("07:00");
        assertThat(holiday.getHoursJourneyFormatted()).isEqualTo("08:00");
        assertThat(holiday.getWeeklyRestFormatted()).isEqualTo("00:00");
        assertThat(holiday.getExtraHoursFormatted()).isEqualTo("07:00");
        assertThat(holiday.getSumula90Formatted()).isEqualTo("01:00");
        assertThat(holiday.getNightShiftFormatted()).isEqualTo("06:00");
        assertThat(holiday.getPaidNightTimeFormatted()).isEqualTo("00:51");
    }

}
