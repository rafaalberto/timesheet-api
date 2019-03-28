package br.com.api.timesheet.service;

import br.com.api.timesheet.dto.TimesheetDailyReport;
import br.com.api.timesheet.dto.TimesheetDocket;
import br.com.api.timesheet.dto.TimesheetReport;
import br.com.api.timesheet.entity.TimesheetRegister;
import br.com.api.timesheet.enumeration.ReportTypeEnum;
import br.com.api.timesheet.enumeration.TimesheetTypeEnum;
import br.com.api.timesheet.repository.TimesheetRegisterRepository;
import br.com.api.timesheet.resource.timesheetRegister.TimesheetRequest;
import br.com.api.timesheet.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static br.com.api.timesheet.enumeration.ReportTypeEnum.*;
import static java.time.Duration.ofSeconds;
import static java.time.LocalDateTime.parse;
import static java.time.format.DateTimeFormatter.ofPattern;

@Service
public class TimesheetRegisterService {

    public static final String SEPARATOR_CHARACTER = "-";
    private TimesheetRegisterRepository timesheetRegisterRepository;

    public TimesheetRegisterService(@Autowired TimesheetRegisterRepository timesheetRegisterRepository) {
        this.timesheetRegisterRepository = timesheetRegisterRepository;
    }

    public TimesheetRegister save(TimesheetRequest request) {
        return timesheetRegisterRepository.save(getTimeSheetRegister(request));
    }

    public Collection<TimesheetReport> listReport() {
        return timesheetRegisterRepository.listReport();
    }

    public Collection<TimesheetDocket> listDocket() {
        Collection<TimesheetReport> report = listReport();
        Collection<TimesheetDocket> dockets = new ArrayList<>();
        dockets.add(new TimesheetDocket(REGULAR_HOURS.getDescription(), getTotalHoursWorked(report)));
        dockets.add(new TimesheetDocket(WEEKLY_REST.getDescription(), getTotalWeeklyRest(report)));
        dockets.add(new TimesheetDocket(EXTRA_HOURS_PART.getDescription(), getTotalExtraHoursPart(report)));
        dockets.add(new TimesheetDocket(EXTRA_HOURS_FULL.getDescription(), getTotalExtraHoursFull(report)));
        dockets.add(new TimesheetDocket(SUMULA_90.getDescription(), getTotalSumula90(report)));
        dockets.add(new TimesheetDocket(NIGHT_SHIFT.getDescription(), getTotalNightShift(report)));
        dockets.add(new TimesheetDocket(PAID_NIGHT_SHIFT.getDescription(), getTotalPaidNightTime(report)));

        return dockets;
    }

    public Collection<TimesheetDailyReport> listDailyReport() {
        List<TimesheetDailyReport> dailyReport = new ArrayList();
        List<TimesheetRegister> registers = timesheetRegisterRepository.findAll(new Sort(Sort.Direction.DESC,"timeIn"));
        if(!registers.isEmpty()){
            registers.stream().forEach(register -> {
                TimesheetDailyReport report = new TimesheetDailyReport();
                report.setType(register.getType().getDescription());
                report.setDate(ofPattern(DateUtils.DATE_FORMAT_PT_BR).format(register.getTimeIn()));
                report.setEntry(fetchEntry(register));
                report.setHoursWorked(register.getHoursWorked());
                report.setHoursJourney(register.getHoursJourney());
                report.setExtraHours(register.getExtraHours());
                report.setWeeklyRest(register.getWeeklyRest());
                report.setSumula90(register.getSumula90());
                report.setNightShift(register.getNightShift());
                report.setPaidNightTime(register.getPaidNightTime());
                dailyReport.add(report);
            });
        }
        return dailyReport;
    }

    private TimesheetRegister getTimeSheetRegister(TimesheetRequest request) {
        DateTimeFormatter formatter = ofPattern(DateUtils.DATE_TIME_FORMAT);
        TimesheetRegister register = new TimesheetRegister();
        request.getId().ifPresent(id -> register.setId(id));
        register.setType(request.getType());
        register.setTimeIn(parse(request.getTimeIn(), formatter));
        register.setLunchStart(parse(request.getLunchStart(), formatter));
        register.setLunchEnd(parse(request.getLunchEnd(), formatter));
        register.setTimeOut(parse(request.getTimeOut(), formatter));
        register.setHoursJourney(ofSeconds(LocalTime.parse(request.getHoursJourney(), ofPattern(DateUtils.TIME_FORMAT)).toSecondOfDay()));
        register.setSumula90(ofSeconds(LocalTime.parse(request.getSumula90(), ofPattern(DateUtils.TIME_FORMAT)).toSecondOfDay()));
        return register;
    }

    private String fetchEntry(TimesheetRegister register) {
        DateTimeFormatter formatter = ofPattern(DateUtils.TIME_FORMAT);
        return formatter.format(register.getTimeIn()).concat(SEPARATOR_CHARACTER)
            .concat(formatter.format(register.getLunchStart())).concat(SEPARATOR_CHARACTER)
            .concat(formatter.format(register.getLunchEnd())).concat(SEPARATOR_CHARACTER)
            .concat(formatter.format(register.getTimeOut()));
    }

    private long getTotalPaidNightTime(Collection<TimesheetReport> report) {
        return report.stream().mapToLong(r -> r.getPaidNightTime()).sum();
    }

    private long getTotalNightShift(Collection<TimesheetReport> report) {
        return report.stream().mapToLong(r -> r.getNightShift()).sum();
    }

    private long getTotalSumula90(Collection<TimesheetReport> report) {
        return report.stream().mapToLong(r -> r.getSumula90()).sum();
    }

    private long getTotalExtraHoursFull(Collection<TimesheetReport> report) {
        return report.stream()
                .filter(r -> r.getType().equals(TimesheetTypeEnum.DAY_OFF) || r.getType().equals(TimesheetTypeEnum.HOLIDAY))
                .mapToLong(r -> r.getExtraHours()).sum();
    }

    private long getTotalExtraHoursPart(Collection<TimesheetReport> report) {
        return report.stream()
                .filter(r -> r.getType().equals(TimesheetTypeEnum.REGULAR))
                .mapToLong(r -> r.getExtraHours()).sum();
    }

    private long getTotalWeeklyRest(Collection<TimesheetReport> report) {
        return report.stream().mapToLong(r -> r.getWeeklyRest()).sum();
    }

    private long getTotalHoursWorked(Collection<TimesheetReport> report) {
        return report.stream()
                .filter(r -> r.getType().equals(TimesheetTypeEnum.REGULAR))
                .mapToLong(r -> r.getHoursWorked()).sum();
    }

}
