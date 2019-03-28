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
import springfox.documentation.spring.web.plugins.Docket;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.time.Duration.ofSeconds;
import static java.time.LocalDateTime.parse;
import static java.time.format.DateTimeFormatter.ofPattern;
import static java.util.stream.Stream.*;

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

        long hoursWorked  = report.stream()
                .filter(r -> r.getTypeEnum().equals(TimesheetTypeEnum.REGULAR))
                .mapToLong(r -> r.getHoursWorked()).sum();

        long weeklyRest = report.stream()
                .filter(r -> r.getTypeEnum().equals(TimesheetTypeEnum.DAY_OFF))
                .mapToLong(r -> r.getWeeklyRest()).sum();

        long extraHoursPart = report.stream()
                .filter(r -> r.getTypeEnum().equals(TimesheetTypeEnum.REGULAR))
                .mapToLong(r -> r.getExtraHours()).sum();

        long extraHoursFull = report.stream()
                .filter(r -> r.getTypeEnum().equals(TimesheetTypeEnum.DAY_OFF) || r.getTypeEnum().equals(TimesheetTypeEnum.HOLIDAY))
                .mapToLong(r -> r.getExtraHours()).sum();

        dockets.add(new TimesheetDocket(ReportTypeEnum.REGULAR_HOURS.getDescription(), hoursWorked));
        dockets.add(new TimesheetDocket(ReportTypeEnum.WEEKLY_REST.getDescription(), weeklyRest));
        dockets.add(new TimesheetDocket(ReportTypeEnum.EXTRA_HOURS_PART.getDescription(), extraHoursPart));
        dockets.add(new TimesheetDocket(ReportTypeEnum.EXTRA_HOURS_FULL.getDescription(), extraHoursFull));

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

}
