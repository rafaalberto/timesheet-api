package br.com.api.timesheet.resource.timesheetregister;

import br.com.api.timesheet.dto.TimesheetDailyReport;
import br.com.api.timesheet.dto.TimesheetDocket;
import br.com.api.timesheet.dto.TimesheetReport;
import br.com.api.timesheet.entity.TimesheetRegister;
import br.com.api.timesheet.service.TimesheetRegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collection;

@CrossOrigin(origins = "http://localhost:8080")
@RestController
public class TimesheetRegisterResource {

    @Autowired
    private TimesheetRegisterService timesheetRegisterService;

    public TimesheetRegisterResource(@Autowired TimesheetRegisterService timesheetRegisterService) {
        this.timesheetRegisterService = timesheetRegisterService;
    }

    @GetMapping("/timesheet/report/{employee}/{year}/{month}")
    public ResponseEntity<Collection<TimesheetReport>> listReport(
            @PathVariable Long employee, @PathVariable Integer year, @PathVariable Integer month) {
        Collection<TimesheetReport> timesheetReports = timesheetRegisterService.listReport(employee, year, month);
        return ResponseEntity.ok(timesheetReports);
    }

    @GetMapping("/timesheet/daily/{employee}/{year}/{month}/{asc}")
    public ResponseEntity<Collection<TimesheetDailyReport>> listDailyReport(
            @PathVariable Long employee, @PathVariable Integer year, @PathVariable Integer month, @PathVariable boolean asc) {
        Collection<TimesheetDailyReport> timesheetReports = timesheetRegisterService.listDailyReport(employee, year, month, asc);
        return ResponseEntity.ok(timesheetReports);
    }

    @GetMapping("/timesheet/docket/{employee}/{year}/{month}")
    public ResponseEntity<TimesheetDocket> listDocket(
            @PathVariable Long employee, @PathVariable Integer year, @PathVariable Integer month) {
        TimesheetDocket timesheetDocket = timesheetRegisterService.listDocket(employee, year, month);
        return ResponseEntity.ok(timesheetDocket);
    }

    @PostMapping("/timesheet")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<TimesheetRegister> create(@Valid @RequestBody TimesheetRequest request) {
        return new ResponseEntity<>(timesheetRegisterService.save(request), HttpStatus.CREATED);
    }

    @PutMapping("/timesheet/{id}")
    public ResponseEntity<TimesheetRegister> update(@PathVariable Long id, @Valid @RequestBody TimesheetRequest request) {
        request.setId(id);
        return new ResponseEntity<>(timesheetRegisterService.save(request), HttpStatus.OK);
    }

    @DeleteMapping("/timesheet/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        timesheetRegisterService.delete(id);
    }

}
