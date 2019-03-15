package br.com.api.timesheet.service;

import br.com.api.timesheet.entity.TimesheetRegister;
import br.com.api.timesheet.repository.TimesheetRegisterRepository;
import br.com.api.timesheet.dto.TimesheetReport;
import br.com.api.timesheet.resource.timesheetRegister.TimesheetRequest;
import br.com.api.timesheet.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Collection;

import static java.time.LocalDateTime.parse;
import static java.time.format.DateTimeFormatter.ofPattern;

@Service
public class TimesheetRegisterService {

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

}
