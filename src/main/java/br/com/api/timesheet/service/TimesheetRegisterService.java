package br.com.api.timesheet.service;

import br.com.api.timesheet.dto.TimesheetDailyReport;
import br.com.api.timesheet.dto.TimesheetDocket;
import br.com.api.timesheet.dto.TimesheetReport;
import br.com.api.timesheet.entity.TimesheetRegister;
import br.com.api.timesheet.resource.timesheetregister.TimesheetRequest;
import java.util.Collection;

public interface TimesheetRegisterService {
    TimesheetRegister save(TimesheetRequest request);
    TimesheetRegister findById(Long id);
    void delete(Long id);
    Collection<TimesheetReport> listReport(Long employee, Integer year, Integer month);
    Collection<TimesheetDailyReport> listDailyReport(Long employee, Integer year, Integer month, boolean asc);
    TimesheetDocket listDocket(Long employeeId, Integer year, Integer month);
}
