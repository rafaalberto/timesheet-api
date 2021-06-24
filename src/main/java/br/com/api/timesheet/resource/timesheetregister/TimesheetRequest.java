package br.com.api.timesheet.resource.timesheetregister;

import br.com.api.timesheet.enumeration.PeriodEnum;
import br.com.api.timesheet.enumeration.TimesheetTypeEnum;
import lombok.Data;

import java.util.Optional;

@Data
public class TimesheetRequest {

  private Long id;
  private Long employeeId;
  private Integer monthReference;
  private Integer yearReference;
  private String costHour;
  private TimesheetTypeEnum type;
  private String timeIn;
  private String lunchStart;
  private String lunchEnd;
  private String timeOut;
  private String hoursJourney;
  private String hoursAdjustment;
  private String sumula90;
  private boolean dangerousness;
  private String notes;
  private PeriodEnum period;

  public Optional<Long> getId() {
    return Optional.ofNullable(id);
  }

  public Optional<Long> getEmployeeId() {
    return Optional.ofNullable(employeeId);
  }

}
