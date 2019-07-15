//package br.com.api.timesheet.repository;
//
//import br.com.api.timesheet.entity.Company;
//import br.com.api.timesheet.entity.Employee;
//import br.com.api.timesheet.entity.Position;
//import br.com.api.timesheet.entity.TimesheetRegister;
//import br.com.api.timesheet.enumeration.StatusEnum;
//import br.com.api.timesheet.utils.DateUtils;
//import org.junit.After;
//import org.junit.Before;
//import org.junit.Ignore;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
//import org.springframework.test.context.ActiveProfiles;
//import org.springframework.test.context.junit4.SpringRunner;
//
//import java.time.LocalTime;
//import java.time.format.DateTimeFormatter;
//
//import static br.com.api.timesheet.enumeration.TimesheetTypeEnum.*;
//import static java.time.Duration.ofSeconds;
//import static java.time.LocalDateTime.parse;
//import static java.time.format.DateTimeFormatter.ofPattern;
//import static org.assertj.core.api.Assertions.assertThat;
//
//
//@RunWith(SpringRunner.class)
//@DataJpaTest
//@ActiveProfiles("test")
//public class TimesheetRegisterRepositoryDailyTest {
//
//    private static final Integer YEAR_REFERENCE = 2019;
//    private static final Integer MONTH_REFERENCE = 6;
//    private static final String RECORD_NUMBER = "1703";
//
//    @Autowired
//    private TimesheetRegisterRepository timesheetRegisterRepository;
//
//    @Autowired
//    private PositionRepository positionRepository;
//
//    @Autowired
//    private CompanyRepository companyRepository;
//
//    @Autowired
//    private EmployeeRepository employeeRepository;
//
//    @Before
//    public void setUp() {
//        setPosition();
//        setCompany();
////        setEmployee();
//    }
//
//    private void setPosition() {
//        Position position = new Position();
//        position.setId(1L);
//        position.setTitle("Assistent");
//        positionRepository.save(position);
//    }
//
//    private void setCompany() {
//        Company company = new Company();
//        company.setId(1L);
//        company.setDocument("31.749.356/0001-56");
//        company.setName("Company 1");
//        companyRepository.save(company);
//    }
//
////    private void setEmployee() {
////        Employee employee = getEmployee();
////        employeeRepository.save(employee);
////    }
//
//    private Employee getEmployee() {
//        Employee employee = new Employee();
//        employee.setId(1L);
//        employee.setCompany(companyRepository.findByDocument("31.749.356/0001-56").get());
//
//        Position position = new Position();
//        position.setId(1L);
//        position.setTitle("Assistent");
//
//        employee.setPosition(position);
//        employee.setName("Rafael");
//        employee.setRecordNumber(RECORD_NUMBER);
//        employee.setStatus(StatusEnum.ACTIVE);
//        return employee;
//    }
//
//    @Test
//    public void shouldCreateTimesheetRegisterRegular1() {
//        TimesheetRegister registerCreated = timesheetRegisterRepository.save(getTimesheetRegisterRegular1());
//        assertThat(registerCreated.getId()).isNotNull();
//        assertThat(registerCreated.getHoursWorked()).isEqualTo("09:00");
//        assertThat(registerCreated.getHoursJourney()).isEqualTo("08:00");
//        assertThat(registerCreated.getExtraHours()).isEqualTo("01:00");
//        assertThat(registerCreated.getWeeklyRest()).isEqualTo("00:00");
//        assertThat(registerCreated.getNightShift()).isEqualTo("00:00");
//        assertThat(registerCreated.getPaidNightTime()).isEqualTo("00:00");
//    }
//
//    @Test
//    public void shouldCreateTimesheetRegisterRegular2() {
//        TimesheetRegister registerCreated = timesheetRegisterRepository.save(getTimesheetRegisterRegular2());
//        assertThat(registerCreated.getId()).isNotNull();
//        assertThat(registerCreated.getHoursWorked()).isEqualTo("08:30");
//        assertThat(registerCreated.getHoursJourney()).isEqualTo("08:00");
//        assertThat(registerCreated.getExtraHours()).isEqualTo("00:30");
//        assertThat(registerCreated.getWeeklyRest()).isEqualTo("00:00");
//        assertThat(registerCreated.getNightShift()).isEqualTo("01:30");
//        assertThat(registerCreated.getPaidNightTime()).isEqualTo("00:12");
//    }
//
//    @Test
//    public void shouldCreateTimesheetRegisterRegular3() {
//        TimesheetRegister registerCreated = timesheetRegisterRepository.save(getTimesheetRegisterRegular3());
//        assertThat(registerCreated.getId()).isNotNull();
//        assertThat(registerCreated.getHoursWorked()).isEqualTo("09:00");
//        assertThat(registerCreated.getHoursJourney()).isEqualTo("08:00");
//        assertThat(registerCreated.getExtraHours()).isEqualTo("01:00");
//        assertThat(registerCreated.getWeeklyRest()).isEqualTo("00:00");
//        assertThat(registerCreated.getNightShift()).isEqualTo("06:00");
//        assertThat(registerCreated.getPaidNightTime()).isEqualTo("00:51");
//    }
//
//    @Test
//    public void shouldCreateTimesheetRegisterRegular4() {
//        TimesheetRegister registerCreated = timesheetRegisterRepository.save(getTimesheetRegisterRegular4());
//        assertThat(registerCreated.getId()).isNotNull();
//        assertThat(registerCreated.getHoursWorked()).isEqualTo("05:00");
//        assertThat(registerCreated.getHoursJourney()).isEqualTo("08:00");
//        assertThat(registerCreated.getExtraHours()).isEqualTo("00:00");
//        assertThat(registerCreated.getWeeklyRest()).isEqualTo("00:00");
//        assertThat(registerCreated.getNightShift()).isEqualTo("05:00");
//        assertThat(registerCreated.getPaidNightTime()).isEqualTo("00:42");
//    }
//
//    @Test
//    public void shouldCreateTimesheetRegisterDayOff1() {
//        TimesheetRegister registerCreated = timesheetRegisterRepository.save(getTimesheetRegisterDayOff1());
//        assertThat(registerCreated.getId()).isNotNull();
//        assertThat(registerCreated.getHoursWorked()).isEqualTo("00:00");
//        assertThat(registerCreated.getHoursJourney()).isEqualTo("00:00");
//        assertThat(registerCreated.getExtraHours()).isEqualTo("00:00");
//        assertThat(registerCreated.getWeeklyRest()).isEqualTo("08:00");
//        assertThat(registerCreated.getNightShift()).isEqualTo("00:00");
//        assertThat(registerCreated.getPaidNightTime()).isEqualTo("00:00");
//    }
//
//    @Test
//    public void shouldCreateTimesheetRegisterDayOff2() {
//        TimesheetRegister registerCreated = timesheetRegisterRepository.save(getTimesheetRegisterDayOff2());
//        assertThat(registerCreated.getId()).isNotNull();
//        assertThat(registerCreated.getHoursWorked()).isEqualTo("00:00");
//        assertThat(registerCreated.getHoursJourney()).isEqualTo("00:00");
//        assertThat(registerCreated.getExtraHours()).isEqualTo("00:00");
//        assertThat(registerCreated.getWeeklyRest()).isEqualTo("08:00");
//        assertThat(registerCreated.getNightShift()).isEqualTo("00:00");
//        assertThat(registerCreated.getPaidNightTime()).isEqualTo("00:00");
//    }
//
//    @Test
//    public void shouldCreateTimesheetRegisterHoliday1() {
//        TimesheetRegister registerCreated = timesheetRegisterRepository.save(getTimesheetRegisterHoliday1());
//        assertThat(registerCreated.getId()).isNotNull();
//        assertThat(registerCreated.getHoursWorked()).isEqualTo("07:00");
//        assertThat(registerCreated.getHoursJourney()).isEqualTo("08:00");
//        assertThat(registerCreated.getExtraHours()).isEqualTo("07:00");
//        assertThat(registerCreated.getWeeklyRest()).isEqualTo("00:00");
//        assertThat(registerCreated.getNightShift()).isEqualTo("06:00");
//        assertThat(registerCreated.getPaidNightTime()).isEqualTo("00:51");
//    }
//
//    @After
//    public void tearDown(){
//        timesheetRegisterRepository.findAll().stream().forEach(r -> timesheetRegisterRepository.delete(r));
////        employeeRepository.findAll().stream().forEach(r -> employeeRepository.delete(r));
//        positionRepository.findAll().forEach(r -> positionRepository.delete(r));
//        companyRepository.findAll().forEach(r -> companyRepository.delete(r));
//    }
//
//    private TimesheetRegister getTimesheetRegisterRegular1() {
//        DateTimeFormatter formatter = ofPattern(DateUtils.DATE_TIME_FORMAT);
//        TimesheetRegister timesheetRegister = new TimesheetRegister();
//        timesheetRegister.setEmployee(getEmployee());
//        timesheetRegister.setYearReference(YEAR_REFERENCE);
//        timesheetRegister.setMonthReference(MONTH_REFERENCE);
//        timesheetRegister.setType(REGULAR);
//        timesheetRegister.setTimeIn(parse("2019-01-01 08:00", formatter));
//        timesheetRegister.setLunchStart(parse("2019-01-01 12:00", formatter));
//        timesheetRegister.setLunchEnd(parse("2019-01-01 13:00", formatter));
//        timesheetRegister.setTimeOut(parse("2019-01-01 18:00", formatter));
//        timesheetRegister.setHoursJourney(ofSeconds(LocalTime.parse("08:00", ofPattern(DateUtils.TIME_FORMAT)).toSecondOfDay()));
//        timesheetRegister.setSumula90(ofSeconds(LocalTime.parse("01:00", ofPattern(DateUtils.TIME_FORMAT)).toSecondOfDay()));
//        return timesheetRegister;
//    }
//
//    private TimesheetRegister getTimesheetRegisterRegular2() {
//        DateTimeFormatter formatter = ofPattern(DateUtils.DATE_TIME_FORMAT);
//        TimesheetRegister timesheetRegister = new TimesheetRegister();
//        timesheetRegister.setEmployee(getEmployee());
//        timesheetRegister.setYearReference(YEAR_REFERENCE);
//        timesheetRegister.setMonthReference(MONTH_REFERENCE);
//        timesheetRegister.setType(REGULAR);
//        timesheetRegister.setTimeIn(parse("2019-01-02 14:00", formatter));
//        timesheetRegister.setLunchStart(parse("2019-01-02 19:00", formatter));
//        timesheetRegister.setLunchEnd(parse("2019-01-02 20:00", formatter));
//        timesheetRegister.setTimeOut(parse("2019-01-02 23:30", formatter));
//        timesheetRegister.setHoursJourney(ofSeconds(LocalTime.parse("08:00", ofPattern(DateUtils.TIME_FORMAT)).toSecondOfDay()));
//        timesheetRegister.setSumula90(ofSeconds(LocalTime.parse("01:00", ofPattern(DateUtils.TIME_FORMAT)).toSecondOfDay()));
//        return timesheetRegister;
//    }
//
//    private TimesheetRegister getTimesheetRegisterRegular3() {
//        DateTimeFormatter formatter = ofPattern(DateUtils.DATE_TIME_FORMAT);
//        TimesheetRegister timesheetRegister = new TimesheetRegister();
//        timesheetRegister.setEmployee(getEmployee());
//        timesheetRegister.setYearReference(YEAR_REFERENCE);
//        timesheetRegister.setMonthReference(MONTH_REFERENCE);
//        timesheetRegister.setType(REGULAR);
//        timesheetRegister.setTimeIn(parse("2019-01-03 21:30", formatter));
//        timesheetRegister.setLunchStart(parse("2019-01-04 02:00", formatter));
//        timesheetRegister.setLunchEnd(parse("2019-01-04 03:00", formatter));
//        timesheetRegister.setTimeOut(parse("2019-01-04 07:30", formatter));
//        timesheetRegister.setHoursJourney(ofSeconds(LocalTime.parse("08:00", ofPattern(DateUtils.TIME_FORMAT)).toSecondOfDay()));
//        timesheetRegister.setSumula90(ofSeconds(LocalTime.parse("01:00", ofPattern(DateUtils.TIME_FORMAT)).toSecondOfDay()));
//        return timesheetRegister;
//    }
//
//    private TimesheetRegister getTimesheetRegisterRegular4() {
//        DateTimeFormatter formatter = ofPattern(DateUtils.DATE_TIME_FORMAT);
//        TimesheetRegister timesheetRegister = new TimesheetRegister();
//        timesheetRegister.setEmployee(getEmployee());
//        timesheetRegister.setYearReference(YEAR_REFERENCE);
//        timesheetRegister.setMonthReference(MONTH_REFERENCE);
//        timesheetRegister.setType(REGULAR);
//        timesheetRegister.setTimeIn(parse("2019-01-04 23:00", formatter));
//        timesheetRegister.setLunchStart(parse("2019-01-05 02:00", formatter));
//        timesheetRegister.setLunchEnd(parse("2019-01-05 03:00", formatter));
//        timesheetRegister.setTimeOut(parse("2019-01-05 05:00", formatter));
//        timesheetRegister.setHoursJourney(ofSeconds(LocalTime.parse("08:00", ofPattern(DateUtils.TIME_FORMAT)).toSecondOfDay()));
//        timesheetRegister.setSumula90(ofSeconds(LocalTime.parse("01:00", ofPattern(DateUtils.TIME_FORMAT)).toSecondOfDay()));
//        return timesheetRegister;
//    }
//
//    private TimesheetRegister getTimesheetRegisterDayOff1() {
//        DateTimeFormatter formatter = ofPattern(DateUtils.DATE_TIME_FORMAT);
//        TimesheetRegister timesheetRegister = new TimesheetRegister();
//        timesheetRegister.setEmployee(getEmployee());
//        timesheetRegister.setYearReference(YEAR_REFERENCE);
//        timesheetRegister.setMonthReference(MONTH_REFERENCE);
//        timesheetRegister.setType(DAY_OFF);
//        timesheetRegister.setTimeIn(parse("2019-01-05 00:00", formatter));
//        timesheetRegister.setLunchStart(parse("2019-01-05 00:00", formatter));
//        timesheetRegister.setLunchEnd(parse("2019-01-05 00:00", formatter));
//        timesheetRegister.setTimeOut(parse("2019-01-05 00:00", formatter));
//        timesheetRegister.setHoursJourney(ofSeconds(LocalTime.parse("08:00", ofPattern(DateUtils.TIME_FORMAT)).toSecondOfDay()));
//        timesheetRegister.setSumula90(ofSeconds(0));
//        return timesheetRegister;
//    }
//
//    private TimesheetRegister getTimesheetRegisterDayOff2() {
//        DateTimeFormatter formatter = ofPattern(DateUtils.DATE_TIME_FORMAT);
//        TimesheetRegister timesheetRegister = new TimesheetRegister();
//        timesheetRegister.setEmployee(getEmployee());
//        timesheetRegister.setYearReference(YEAR_REFERENCE);
//        timesheetRegister.setMonthReference(MONTH_REFERENCE);
//        timesheetRegister.setType(DAY_OFF);
//        timesheetRegister.setTimeIn(parse("2019-01-06 00:00", formatter));
//        timesheetRegister.setLunchStart(parse("2019-01-06 00:00", formatter));
//        timesheetRegister.setLunchEnd(parse("2019-01-06 00:00", formatter));
//        timesheetRegister.setTimeOut(parse("2019-01-06 00:00", formatter));
//        timesheetRegister.setHoursJourney(ofSeconds(LocalTime.parse("08:00", ofPattern(DateUtils.TIME_FORMAT)).toSecondOfDay()));
//        timesheetRegister.setSumula90(ofSeconds(0));
//        return timesheetRegister;
//    }
//
//    private TimesheetRegister getTimesheetRegisterHoliday1() {
//        DateTimeFormatter formatter = ofPattern(DateUtils.DATE_TIME_FORMAT);
//        TimesheetRegister timesheetRegister = new TimesheetRegister();
//        timesheetRegister.setEmployee(getEmployee());
//        timesheetRegister.setYearReference(YEAR_REFERENCE);
//        timesheetRegister.setMonthReference(MONTH_REFERENCE);
//        timesheetRegister.setType(HOLIDAY);
//        timesheetRegister.setTimeIn(parse("2019-01-07 22:00", formatter));
//        timesheetRegister.setLunchStart(parse("2019-01-08 02:00", formatter));
//        timesheetRegister.setLunchEnd(parse("2019-01-08 03:00", formatter));
//        timesheetRegister.setTimeOut(parse("2019-01-08 06:00", formatter));
//        timesheetRegister.setHoursJourney(ofSeconds(LocalTime.parse("08:00", ofPattern(DateUtils.TIME_FORMAT)).toSecondOfDay()));
//        timesheetRegister.setSumula90(ofSeconds(LocalTime.parse("01:00", ofPattern(DateUtils.TIME_FORMAT)).toSecondOfDay()));
//        return timesheetRegister;
//    }
//
//}
