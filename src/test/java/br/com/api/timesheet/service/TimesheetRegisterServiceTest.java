package br.com.api.timesheet.service;

import br.com.api.timesheet.dto.TimesheetDocket;
import br.com.api.timesheet.dto.TimesheetDocketItem;
import br.com.api.timesheet.dto.TimesheetReport;
import br.com.api.timesheet.entity.Employee;
import br.com.api.timesheet.repository.TimesheetRegisterRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static br.com.api.timesheet.enumeration.ReportTypeEnum.*;
import static br.com.api.timesheet.enumeration.TimesheetTypeEnum.*;
import static br.com.api.timesheet.utils.DateUtils.convertStringtoNanos;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

public class TimesheetRegisterServiceTest {

    public static final int REPORT_SIZE = 8;

    private TimesheetRegisterService timesheetRegisterService;

    @Mock
    private TimesheetRegisterRepository timesheetRegisterRepository;

    @Mock
    private EmployeeService employeeService;

    @Mock
    private BonusService bonusService;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        timesheetRegisterService = new TimesheetRegisterService(
                timesheetRegisterRepository, employeeService, bonusService);
    }

    @Test
    public void shouldListReportDocket(){
        when(timesheetRegisterRepository.listReport(1L, 2019, 6)).thenReturn(listReport());
        TimesheetDocket docket = timesheetRegisterService.listDocket(1L, 2019, 6);
        assertThat(docket.getItems().size()).isEqualTo(REPORT_SIZE);

        TimesheetDocketItem regular = docket.getItems().stream().filter(f -> f.getTypeCode().equals(REGULAR_HOURS.getCode())).findFirst().get();
        assertThat(regular.getTotalHoursFormatted()).isEqualTo("31:30");
        assertThat(regular.getTotalCostFormatted()).isEqualTo("R$ 220,50");

        TimesheetDocketItem weeklyRest = docket.getItems().stream().filter(f -> f.getTypeCode().equals(WEEKLY_REST.getCode())).findFirst().get();
        assertThat(weeklyRest.getTotalHoursFormatted()).isEqualTo("16:00");
        assertThat(weeklyRest.getTotalCostFormatted()).isEqualTo("R$ 112,00");

        TimesheetDocketItem weeklyRestComplement = docket.getItems().stream().filter(f -> f.getTypeCode().equals(WEEKLY_REST_COMPLEMENT.getCode())).findFirst().get();
        assertThat(weeklyRestComplement.getTotalHoursFormatted()).isEqualTo("00:00");
        assertThat(weeklyRestComplement.getTotalCostFormatted()).isEqualTo("R$ 116,78");

        TimesheetDocketItem extraHoursPart = docket.getItems().stream().filter(f -> f.getTypeCode().equals(EXTRA_HOURS_PART.getCode())).findFirst().get();
        assertThat(extraHoursPart.getTotalHoursFormatted()).isEqualTo("02:30");
        assertThat(extraHoursPart.getTotalCostFormatted()).isEqualTo("R$ 17,50");

        TimesheetDocketItem extraHoursFull = docket.getItems().stream().filter(f -> f.getTypeCode().equals(EXTRA_HOURS_FULL.getCode())).findFirst().get();
        assertThat(extraHoursFull.getTotalHoursFormatted()).isEqualTo("07:00");
        assertThat(extraHoursFull.getTotalCostFormatted()).isEqualTo("R$ 98,00");

        TimesheetDocketItem sumula90 = docket.getItems().stream().filter(f -> f.getTypeCode().equals(SUMULA_90.getCode())).findFirst().get();
        assertThat(sumula90.getTotalHoursFormatted()).isEqualTo("05:00");
        assertThat(sumula90.getTotalCostFormatted()).isEqualTo("R$ 35,00");

        TimesheetDocketItem nightShift = docket.getItems().stream().filter(f -> f.getTypeCode().equals(NIGHT_SHIFT.getCode())).findFirst().get();
        assertThat(nightShift.getTotalHoursFormatted()).isEqualTo("18:30");
        assertThat(nightShift.getTotalCostFormatted()).isEqualTo("R$ 51,80");

        TimesheetDocketItem paidNightTime = docket.getItems().stream().filter(f -> f.getTypeCode().equals(PAID_NIGHT_TIME.getCode())).findFirst().get();
        assertThat(paidNightTime.getTotalHoursFormatted()).isEqualTo("02:38");
        assertThat(paidNightTime.getTotalCostFormatted()).isEqualTo("R$ 27,62");

        TimesheetDocket timesheetDocket = new TimesheetDocket();
        timesheetDocket.setTotal(docket.getItems().stream().mapToDouble(item -> item.getTotalCost()).sum());
        assertThat(timesheetDocket.getTotalFormatted()).isEqualTo("R$ 679,20");
    }

    private List<TimesheetReport> listReport() {
        List<TimesheetReport> reports = new ArrayList<>();

        TimesheetReport regularReport = new TimesheetReport();
        regularReport.setType(REGULAR);
        regularReport.setCostHour(7.00);
        regularReport.setHoursWorked(convertStringtoNanos("31:30"));
        regularReport.setHoursJourney(convertStringtoNanos("32:00"));
        regularReport.setWeeklyRest(convertStringtoNanos("00:00"));
        regularReport.setExtraHours(convertStringtoNanos("02:30"));
        regularReport.setSumula90(convertStringtoNanos("04:00"));
        regularReport.setNightShift(convertStringtoNanos("12:30"));
        regularReport.setPaidNightTime(convertStringtoNanos("01:47"));
        reports.add(regularReport);

        TimesheetReport dayOffReport = new TimesheetReport();
        dayOffReport.setType(DAY_OFF);
        dayOffReport.setCostHour(7.00);
        dayOffReport.setHoursWorked(convertStringtoNanos("00:00"));
        dayOffReport.setHoursJourney(convertStringtoNanos("00:00"));
        dayOffReport.setWeeklyRest(convertStringtoNanos("16:00"));
        dayOffReport.setExtraHours(convertStringtoNanos("00:00"));
        dayOffReport.setSumula90(convertStringtoNanos("00:00"));
        dayOffReport.setNightShift(convertStringtoNanos("00:00"));
        dayOffReport.setPaidNightTime(convertStringtoNanos("00:00"));
        reports.add(dayOffReport);

        TimesheetReport holidayReport = new TimesheetReport();
        holidayReport.setType(HOLIDAY);
        holidayReport.setCostHour(7.00);
        holidayReport.setHoursWorked(convertStringtoNanos("07:00"));
        holidayReport.setHoursJourney(convertStringtoNanos("08:00"));
        holidayReport.setWeeklyRest(convertStringtoNanos("00:00"));
        holidayReport.setExtraHours(convertStringtoNanos("07:00"));
        holidayReport.setSumula90(convertStringtoNanos("01:00"));
        holidayReport.setNightShift(convertStringtoNanos("06:00"));
        holidayReport.setPaidNightTime(convertStringtoNanos("00:51"));
        reports.add(holidayReport);

        return reports;
    }

}
