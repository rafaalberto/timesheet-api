package br.com.api.timesheet.service;

import br.com.api.timesheet.dto.TimesheetDailyReport;
import br.com.api.timesheet.dto.TimesheetReport;
import br.com.api.timesheet.entity.TimesheetRegister;
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

    public Collection<TimesheetDailyReport> listDailyReport() {
        List<TimesheetDailyReport> dailyReport = new ArrayList();
        List<TimesheetRegister> registers = timesheetRegisterRepository.findAll(new Sort(Sort.Direction.DESC,"timeIn"));
        if(!registers.isEmpty()){
            registers.stream().forEach(register -> {
                TimesheetDailyReport report = new TimesheetDailyReport();
                report.setType(register.getType().getDescription());
                report.setDate(ofPattern(DateUtils.DATE_FORMAT_PT_BR).format(register.getTimeIn()));
                report.setEntry(fetchEntry(register));
                report.setHoursWorked(ofPattern(DateUtils.TIME_FORMAT).format(register.getHoursWorked()));
                report.setHoursJourney(ofPattern(DateUtils.TIME_FORMAT).format(register.getHoursJourney()));
                report.setExtraHours(ofPattern(DateUtils.TIME_FORMAT).format(register.getExtraHours()));
                report.setWeeklyRest(ofPattern(DateUtils.TIME_FORMAT).format(register.getWeeklyRest()));
                report.setSumula90(ofPattern(DateUtils.TIME_FORMAT).format(register.getSumula90()));
                report.setNightShift(ofPattern(DateUtils.TIME_FORMAT).format(register.getNightShift()));
                report.setPaidNightTime(ofPattern(DateUtils.TIME_FORMAT).format(register.getPaidNightTime()));
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
        register.setHoursJourney(LocalTime.parse(request.getHoursJourney(), ofPattern(DateUtils.TIME_FORMAT)));
        register.setSumula90(LocalTime.parse(request.getSumula90(), ofPattern(DateUtils.TIME_FORMAT)));
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
