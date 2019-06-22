package br.com.api.timesheet.repository;

import br.com.api.timesheet.dto.TimesheetReport;
import br.com.api.timesheet.entity.Company;
import br.com.api.timesheet.entity.Employee;
import br.com.api.timesheet.entity.Position;
import br.com.api.timesheet.entity.TimesheetRegister;
import br.com.api.timesheet.enumeration.StatusEnum;
import br.com.api.timesheet.utils.DateUtils;
import org.junit.After;
import org.junit.Before;
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
public class TimesheetRegisterRepositoryDocketTest {

    private static final Integer YEAR_REFERENCE = 2019;
    private static final Integer MONTH_REFERENCE = 6;
    private static final String RECORD_NUMBER = "1703";

    @Autowired
    private TimesheetRegisterRepository timesheetRegisterRepository;

    @Autowired
    private PositionRepository positionRepository;

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    private void setPosition() {
        Position position = new Position();
        position.setTitle("Driver");
        positionRepository.save(position);
    }

    private void setCompany() {
        Company company = new Company();
        company.setDocument("123");
        company.setName("Company 1");
        companyRepository.save(company);
    }

    private void setEmployee() {
        Employee employee = new Employee();
        employee.setId(1L);
        employee.setCompany(companyRepository.findByDocument("123").get());
        employee.setPosition(positionRepository.findByTitle("Driver").get());
        employee.setName("Rafael");
        employee.setRecordNumber("1703");
        employee.setStatus(StatusEnum.ACTIVE);
        employeeRepository.save(employee);
    }

    @Test
    public void shoudSaveAndListReport() {
        setPosition();
        setCompany();
        setEmployee();

        timesheetRegisterRepository.save(getTimesheetRegisterRegular1());
        timesheetRegisterRepository.save(getTimesheetRegisterRegular2());
        timesheetRegisterRepository.save(getTimesheetRegisterRegular3());
        timesheetRegisterRepository.save(getTimesheetRegisterRegular4());
        timesheetRegisterRepository.save(getTimesheetRegisterDayOff1());
        timesheetRegisterRepository.save(getTimesheetRegisterDayOff2());
        timesheetRegisterRepository.save(getTimesheetRegisterHoliday1());

        Collection<TimesheetReport> reports = timesheetRegisterRepository.listReport(1L, 2019, 6);

        assertRegularTimesheet(reports);
        assertDayOffTimesheet(reports);
        assertHolidayTimesheet(reports);
    }

    @After
    public void tearDown(){
        timesheetRegisterRepository.findAll().stream().forEach(r -> timesheetRegisterRepository.delete(r));
        employeeRepository.findAll().stream().forEach(r -> employeeRepository.delete(r));
        positionRepository.findAll().forEach(r -> positionRepository.delete(r));
        companyRepository.findAll().forEach(r -> companyRepository.delete(r));
    }

    private TimesheetRegister getTimesheetRegisterRegular1() {
        DateTimeFormatter formatter = ofPattern(DateUtils.DATE_TIME_FORMAT);
        TimesheetRegister timesheetRegister = new TimesheetRegister();
        timesheetRegister.setEmployee(employeeRepository.findByRecordNumber(RECORD_NUMBER).get());
        timesheetRegister.setYearReference(YEAR_REFERENCE);
        timesheetRegister.setMonthReference(MONTH_REFERENCE);
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
        timesheetRegister.setEmployee(employeeRepository.findByRecordNumber(RECORD_NUMBER).get());
        timesheetRegister.setYearReference(YEAR_REFERENCE);
        timesheetRegister.setMonthReference(MONTH_REFERENCE);
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
        timesheetRegister.setEmployee(employeeRepository.findByRecordNumber(RECORD_NUMBER).get());
        timesheetRegister.setYearReference(YEAR_REFERENCE);
        timesheetRegister.setMonthReference(MONTH_REFERENCE);
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
        timesheetRegister.setEmployee(employeeRepository.findByRecordNumber(RECORD_NUMBER).get());
        timesheetRegister.setYearReference(YEAR_REFERENCE);
        timesheetRegister.setMonthReference(MONTH_REFERENCE);
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
        timesheetRegister.setEmployee(employeeRepository.findByRecordNumber(RECORD_NUMBER).get());
        timesheetRegister.setYearReference(YEAR_REFERENCE);
        timesheetRegister.setMonthReference(MONTH_REFERENCE);
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
        timesheetRegister.setEmployee(employeeRepository.findByRecordNumber(RECORD_NUMBER).get());
        timesheetRegister.setYearReference(YEAR_REFERENCE);
        timesheetRegister.setMonthReference(MONTH_REFERENCE);
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
        timesheetRegister.setEmployee(employeeRepository.findByRecordNumber(RECORD_NUMBER).get());
        timesheetRegister.setYearReference(YEAR_REFERENCE);
        timesheetRegister.setMonthReference(MONTH_REFERENCE);
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
