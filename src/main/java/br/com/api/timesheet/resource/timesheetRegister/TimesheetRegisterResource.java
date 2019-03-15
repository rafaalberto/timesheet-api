package br.com.api.timesheet.resource.timesheetRegister;

import br.com.api.timesheet.entity.TimesheetRegister;
import br.com.api.timesheet.dto.TimesheetReport;
import br.com.api.timesheet.service.TimesheetRegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collection;

@CrossOrigin(origins = "*")
@RestController
public class TimesheetRegisterResource {

    @Autowired
    private TimesheetRegisterService timesheetRegisterService;

    public TimesheetRegisterResource(@Autowired TimesheetRegisterService timesheetRegisterService) {
        this.timesheetRegisterService = timesheetRegisterService;
    }

    @GetMapping("/timesheet")
    public ResponseEntity<Collection<TimesheetReport>> listReport() {
        Collection<TimesheetReport> timesheetReports = timesheetRegisterService.listReport();
        return ResponseEntity.ok(timesheetReports);
    }

    @PostMapping("/timesheet")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<TimesheetRegister> create(@Valid @RequestBody TimesheetRequest request) {
        return new ResponseEntity<TimesheetRegister>(timesheetRegisterService.save(request), HttpStatus.CREATED);
    }

    @PutMapping("/timesheet/{id}")
    public ResponseEntity<TimesheetRegister> update(@PathVariable Long id, @Valid @RequestBody TimesheetRequest request) {
        request.setId(id);
        return new ResponseEntity<TimesheetRegister>(timesheetRegisterService.save(request), HttpStatus.OK);
    }

}
