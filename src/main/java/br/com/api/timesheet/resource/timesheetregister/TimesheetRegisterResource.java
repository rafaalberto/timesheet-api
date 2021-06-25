package br.com.api.timesheet.resource.timesheetregister;

import br.com.api.timesheet.dto.TimesheetDailyReport;
import br.com.api.timesheet.dto.TimesheetDocket;
import br.com.api.timesheet.dto.TimesheetReport;
import br.com.api.timesheet.entity.TimesheetRegister;
import br.com.api.timesheet.service.TimesheetRegisterService;
import java.util.Collection;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "http://localhost:8080")
@RestController
public class TimesheetRegisterResource {

  @Autowired
  private TimesheetRegisterService timesheetRegisterService;

  public TimesheetRegisterResource(@Autowired TimesheetRegisterService timesheetRegisterService) {
    this.timesheetRegisterService = timesheetRegisterService;
  }

  /**
   * List timesheet report.
   * @param employee - employee
   * @param year - year
   * @param month - month
   * @return
   */
  @GetMapping("/timesheet/report/{employee}/{year}/{month}")
  public ResponseEntity<Collection<TimesheetReport>> listReport(
          @PathVariable Long employee, @PathVariable Integer year, @PathVariable Integer month) {
    Collection<TimesheetReport> timesheetReports =
            timesheetRegisterService.listReport(employee, year, month);
    return ResponseEntity.ok(timesheetReports);
  }

  /**
   * List daily report.
   * @param employee - employee
   * @param year - year
   * @param month - month
   * @param asc - asc
   * @return
   */
  @GetMapping("/timesheet/daily/{employee}/{year}/{month}/{asc}")
  public ResponseEntity<Collection<TimesheetDailyReport>> listDailyReport(
          @PathVariable Long employee, @PathVariable Integer year, @PathVariable Integer month,
          @PathVariable boolean asc) {
    Collection<TimesheetDailyReport> timesheetReports =
            timesheetRegisterService.listDailyReport(employee, year, month, asc);
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
  public ResponseEntity<TimesheetRegister> update(@PathVariable Long id,
      @Valid @RequestBody TimesheetRequest request) {
    request.setId(id);
    return new ResponseEntity<>(timesheetRegisterService.save(request), HttpStatus.OK);
  }

  @DeleteMapping("/timesheet/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void delete(@PathVariable Long id) {
    timesheetRegisterService.delete(id);
  }

}
