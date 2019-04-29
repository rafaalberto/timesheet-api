package br.com.api.timesheet.service;

import br.com.api.timesheet.dto.TimesheetDocket;
import br.com.api.timesheet.dto.TimesheetReport;
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

    public static final int REPORT_SIZE = 7;

    private TimesheetRegisterService timesheetRegisterService;

    @Mock
    private TimesheetRegisterRepository timesheetRegisterRepository;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        timesheetRegisterService = new TimesheetRegisterService(timesheetRegisterRepository);
    }

    @Test
    public void shouldListReportDocket(){
        when(timesheetRegisterRepository.listReport()).thenReturn(listReport());
        Collection<TimesheetDocket> listDocket = timesheetRegisterService.listDocket();
        assertThat(listDocket.size()).isEqualTo(REPORT_SIZE);

        TimesheetDocket regular = listDocket.stream().filter(f -> f.getTypeCode().equals(REGULAR_HOURS.getCode())).findFirst().get();
        assertThat(regular.getTotalHoursFormatted()).isEqualTo("31:30");
        assertThat(regular.getTotalCost()).isEqualTo("R$220,50");

        TimesheetDocket weeklyRest = listDocket.stream().filter(f -> f.getTypeCode().equals(WEEKLY_REST.getCode())).findFirst().get();
        assertThat(weeklyRest.getTotalHoursFormatted()).isEqualTo("16:00");
        assertThat(weeklyRest.getTotalCost()).isEqualTo("R$112,00");

        TimesheetDocket extraHoursPart = listDocket.stream().filter(f -> f.getTypeCode().equals(EXTRA_HOURS_PART.getCode())).findFirst().get();
        assertThat(extraHoursPart.getTotalHoursFormatted()).isEqualTo("02:30");
        assertThat(extraHoursPart.getTotalCost()).isEqualTo("R$0,00");

        TimesheetDocket extraHoursFull = listDocket.stream().filter(f -> f.getTypeCode().equals(EXTRA_HOURS_FULL.getCode())).findFirst().get();
        assertThat(extraHoursFull.getTotalHoursFormatted()).isEqualTo("07:00");
        assertThat(extraHoursFull.getTotalCost()).isEqualTo("R$0,00");

        TimesheetDocket sumula90 = listDocket.stream().filter(f -> f.getTypeCode().equals(SUMULA_90.getCode())).findFirst().get();
        assertThat(sumula90.getTotalHoursFormatted()).isEqualTo("05:00");
        assertThat(sumula90.getTotalCost()).isEqualTo("R$0,00");

        TimesheetDocket nightShift = listDocket.stream().filter(f -> f.getTypeCode().equals(NIGHT_SHIFT.getCode())).findFirst().get();
        assertThat(nightShift.getTotalHoursFormatted()).isEqualTo("18:30");
        assertThat(nightShift.getTotalCost()).isEqualTo("R$0,00");

        TimesheetDocket paidNightTime = listDocket.stream().filter(f -> f.getTypeCode().equals(PAID_NIGHT_TIME.getCode())).findFirst().get();
        assertThat(paidNightTime.getTotalHoursFormatted()).isEqualTo("02:38");
        assertThat(paidNightTime.getTotalCost()).isEqualTo("R$0,00");

    }

    private List<TimesheetReport> listReport() {
        List<TimesheetReport> reports = new ArrayList<>();

        TimesheetReport regularReport = new TimesheetReport();
        regularReport.setType(REGULAR);
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
