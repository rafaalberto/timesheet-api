package br.com.api.timesheet.dto;

import static br.com.api.timesheet.utils.DateUtils.TIME_FORMAT;
import static java.time.Duration.ofNanos;
import static org.apache.commons.lang3.time.DurationFormatUtils.formatDuration;

import br.com.api.timesheet.enumeration.PeriodEnum;
import br.com.api.timesheet.enumeration.TimesheetTypeEnum;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TimesheetReport {

  private TimesheetTypeEnum type;

  @JsonIgnore
  private boolean dangerousness;

  @JsonIgnore
  private PeriodEnum period;

  @JsonIgnore
  private Double costHour;

  @JsonIgnore
  private long hoursWorked;

  @JsonIgnore
  private long hoursJourney;

  @JsonIgnore
  private long hoursAdjustment;

  @JsonIgnore
  private long weeklyRest;

  @JsonIgnore
  private long extraHours;

  @JsonIgnore
  private long sumula90;

  @JsonIgnore
  private long nightShift;

  @JsonIgnore
  private long paidNightTime;

  public String getHoursWorkedFormatted() {
    return formatDuration(ofNanos(hoursWorked).toMillis(), TIME_FORMAT);
  }

  public String getHoursJourneyFormatted() {
    return formatDuration(ofNanos(hoursJourney).toMillis(), TIME_FORMAT);
  }

  public String getHoursAdjustmentFormatted() {
    return formatDuration(ofNanos(hoursAdjustment).toMillis(), TIME_FORMAT);
  }

  public String getWeeklyRestFormatted() {
    return formatDuration(ofNanos(weeklyRest).toMillis(), TIME_FORMAT);
  }

  public String getExtraHoursFormatted() {
    return formatDuration(ofNanos(extraHours).toMillis(), TIME_FORMAT);
  }

  public String getSumula90Formatted() {
    return formatDuration(ofNanos(sumula90).toMillis(), TIME_FORMAT);
  }

  public String getNightShiftFormatted() {
    return formatDuration(ofNanos(nightShift).toMillis(), TIME_FORMAT);
  }

  public String getPaidNightTimeFormatted() {
    return formatDuration(ofNanos(paidNightTime).toMillis(), TIME_FORMAT);
  }
}
