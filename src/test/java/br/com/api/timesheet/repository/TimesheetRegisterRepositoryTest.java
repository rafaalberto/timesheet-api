package br.com.api.timesheet.repository;

import br.com.api.timesheet.entity.TimesheetRegister;
import br.com.api.timesheet.entity.User;
import br.com.api.timesheet.enumeration.ProfileEnum;
import br.com.api.timesheet.enumeration.TimesheetTypeEnum;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
@ActiveProfiles("test")
public class TimesheetRegisterRepositoryTest {

    public static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm";

    public static final String TIME_IN = "2019-03-09 05:35";
    public static final String LUNCH_START = "2019-03-09 08:05";
    public static final String LUNCH_END = "2019-03-09 11:05";
    public static final String TIME_OUT = "2019-03-09 19:00";
    public static final String HOURS_WORKED = "10:25";

    @Autowired
    private TimesheetRegisterRepository timesheetRegisterRepository;

    private TimesheetRegister timesheetRegister;

    private DateTimeFormatter formatter;

    @Before
    public void setUp() {
        formatter = DateTimeFormatter.ofPattern(DATE_TIME_FORMAT);
        timesheetRegister = new TimesheetRegister();
        timesheetRegister.setTypeEnum(TimesheetTypeEnum.REGULAR);
        timesheetRegister.setTimeIn(LocalDateTime.parse(TIME_IN, formatter));
        timesheetRegister.setLunchStart(LocalDateTime.parse(LUNCH_START, formatter));
        timesheetRegister.setLunchEnd(LocalDateTime.parse(LUNCH_END, formatter));
        timesheetRegister.setTimeOut(LocalDateTime.parse(TIME_OUT, formatter));
        timesheetRegister = timesheetRegisterRepository.save(this.timesheetRegister);
    }

    @Test
    public void shouldCreateTimesheetRegister() {
        TimesheetRegister timesheetCreated = timesheetRegisterRepository.save(this.timesheetRegister);
        assertThat(timesheetCreated.getId()).isNotNull();
        assertThat(timesheetCreated.getHoursWorked().toString()).isEqualTo(HOURS_WORKED);
    }

    @After
    public void tearDown() {
        timesheetRegisterRepository.deleteAll();
    }

}
