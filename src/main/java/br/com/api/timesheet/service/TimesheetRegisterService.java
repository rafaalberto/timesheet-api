package br.com.api.timesheet.service;

import br.com.api.timesheet.entity.TimesheetRegister;
import br.com.api.timesheet.repository.TimesheetRegisterRepository;
import br.com.api.timesheet.resource.timesheetRegister.TimesheetRequest;
import br.com.api.timesheet.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@Service
public class TimesheetRegisterService {

    private TimesheetRegisterRepository timesheetRegisterRepository;

    public TimesheetRegisterService(@Autowired TimesheetRegisterRepository timesheetRegisterRepository) {
        this.timesheetRegisterRepository = timesheetRegisterRepository;
    }

    public TimesheetRegister save(TimesheetRequest request) {
        return timesheetRegisterRepository.save(getTimeSheetRegister(request));
    }

    private TimesheetRegister getTimeSheetRegister(TimesheetRequest request) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(Constants.DATE_TIME_FORMAT);
        TimesheetRegister register = new TimesheetRegister();
        request.getId().ifPresent(id -> register.setId(id));
        register.setTypeEnum(request.getType());
        register.setTimeIn(LocalDateTime.parse(request.getTimeIn(), formatter));
        register.setLunchStart(LocalDateTime.parse(request.getLunchStart(), formatter));
        register.setLunchEnd(LocalDateTime.parse(request.getLunchEnd(), formatter));
        register.setTimeOut(LocalDateTime.parse(request.getTimeOut(), formatter));
        register.setHoursJourney(LocalTime.parse(request.getHoursJourney(), DateTimeFormatter.ofPattern(Constants.TIME_FORMAT)));
        register.setSumula90(LocalTime.parse(request.getSumula90(), DateTimeFormatter.ofPattern(Constants.TIME_FORMAT)));
        return register;
    }

}
