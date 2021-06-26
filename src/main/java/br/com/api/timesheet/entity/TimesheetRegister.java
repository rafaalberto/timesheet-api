package br.com.api.timesheet.entity;

import static br.com.api.timesheet.enumeration.TimesheetTypeEnum.DAY_OFF;
import static br.com.api.timesheet.enumeration.TimesheetTypeEnum.HOLIDAY;
import static br.com.api.timesheet.utils.DateUtils.calculateNightShift;
import static br.com.api.timesheet.utils.DateUtils.calculatePaidNightTime;
import static br.com.api.timesheet.utils.DateUtils.isNightShift;
import static java.time.Duration.between;
import static java.time.Duration.ofSeconds;
import static java.time.LocalTime.ofSecondOfDay;
import static org.apache.commons.lang3.time.DurationFormatUtils.formatDuration;

import br.com.api.timesheet.enumeration.PeriodEnum;
import br.com.api.timesheet.enumeration.TimesheetTypeEnum;
import br.com.api.timesheet.utils.DateUtils;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
@AllArgsConstructor
@Table(name = TimesheetRegister.TABLE_NAME)
public class TimesheetRegister implements Serializable {

  static final String TABLE_NAME = "timesheet_register";
  private static final long serialVersionUID = 2503096874494106030L;
  private static final String SEQUENCE_NAME = "seq_timesheet_register";

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = SEQUENCE_NAME)
  @SequenceGenerator(name = SEQUENCE_NAME, sequenceName = SEQUENCE_NAME, allocationSize = 1)
  private Long id;

  @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "employee_id")
  private Employee employee;

  @Column(name = "month_reference", length = 2)
  private Integer monthReference;

  @Column(name = "year_reference", length = 4)
  private Integer yearReference;

  @Column(name = "type", nullable = false, length = 2)
  private TimesheetTypeEnum type;

  @Column(name = "cost_hour", precision = 10, scale = 2)
  private Double costHour;

  @Column(name = "dangerousness", nullable = false, length = 1)
  private boolean dangerousness;

  @Column(name = "time_in")
  private LocalDateTime timeIn;

  @Column(name = "lunch_start")
  private LocalDateTime lunchStart;

  @Column(name = "lunch_end")
  private LocalDateTime lunchEnd;

  @Column(name = "time_out")
  private LocalDateTime timeOut;

  @Column(name = "hours_worked")
  private Duration hoursWorked;

  @Column(name = "hours_journey")
  private Duration hoursJourney;

  @Column(name = "hours_adjustment")
  private Duration hoursAdjustment;

  @Column(name = "extra_hours")
  private Duration extraHours;

  @Column(name = "weekly_rest")
  private Duration weeklyRest;

  @Column(name = "sumula_90")
  private Duration sumula90;

  @Column(name = "night_shift")
  private Duration nightShift;

  @Column(name = "paid_night_time")
  private Duration paidNightTime;

  @Column(name = "notes")
  private String notes;

  @Column(name = "period", nullable = false, length = 1)
  private PeriodEnum period;

  public String getHoursWorked() {
    return formatDuration(hoursWorked.toMillis(), DateUtils.TIME_FORMAT);
  }

  public String getHoursJourney() {
    return formatDuration(hoursJourney.toMillis(), DateUtils.TIME_FORMAT);
  }

  public String getHoursAdjustment() {
    return formatDuration(hoursAdjustment.toMillis(), DateUtils.TIME_FORMAT);
  }

  public String getExtraHours() {
    return formatDuration(extraHours.toMillis(), DateUtils.TIME_FORMAT);
  }

  public String getWeeklyRest() {
    return formatDuration(weeklyRest.toMillis(), DateUtils.TIME_FORMAT);
  }

  public String getSumula90() {
    return formatDuration(sumula90.toMillis(), DateUtils.TIME_FORMAT);
  }

  public String getNightShift() {
    return formatDuration(nightShift.toMillis(), DateUtils.TIME_FORMAT);
  }

  public String getPaidNightTime() {
    return formatDuration(paidNightTime.toMillis(), DateUtils.TIME_FORMAT);
  }

  /**
   * Calculate hours.
   */
  @PrePersist
  @PreUpdate
  public void calculateHours() {
    resetValues();

    long firstPeriod = between(timeIn, lunchStart).getSeconds();
    long secondPeriod = between(lunchEnd, timeOut).getSeconds();
    hoursWorked = ofSeconds(firstPeriod + secondPeriod);

    long extraHoursDuration = hoursWorked != null
            ? hoursWorked.getSeconds() - hoursJourney.getSeconds() : BigDecimal.ZERO.intValue();
    extraHours = extraHoursDuration > BigDecimal.ZERO.intValue()
            ? ofSeconds(extraHoursDuration) : ofSeconds(BigDecimal.ZERO.intValue());

    if (extraHours.compareTo(hoursAdjustment) >= BigDecimal.ZERO.intValue()) {
      extraHours = extraHours.minus(hoursAdjustment);
    }

    if (type.equals(DAY_OFF)) {
      weeklyRest = hoursJourney;
      hoursJourney = ofSeconds(BigDecimal.ZERO.intValue());
    } else if (type.equals(HOLIDAY)) {
      extraHours = hoursWorked;
    }

    firstPeriod = isNightShift(timeIn, lunchStart)
            ? calculateNightShift(timeIn, lunchStart) : BigDecimal.ZERO.longValue();
    secondPeriod = isNightShift(lunchEnd, timeOut)
            ? calculateNightShift(lunchEnd, timeOut) : BigDecimal.ZERO.longValue();
    nightShift = ofSeconds(firstPeriod + secondPeriod);

    paidNightTime = nightShift.getSeconds() > BigDecimal.ZERO.intValue()
            ? calculatePaidNightTime(ofSecondOfDay(nightShift.getSeconds()))
            : ofSeconds(BigDecimal.ZERO.intValue());
  }

  private void resetValues() {
    hoursWorked = ofSeconds((BigDecimal.ZERO.intValue()));
    extraHours = ofSeconds(BigDecimal.ZERO.intValue());
    weeklyRest = ofSeconds(BigDecimal.ZERO.intValue());
    nightShift = ofSeconds(BigDecimal.ZERO.intValue());
    paidNightTime = ofSeconds(BigDecimal.ZERO.intValue());
  }

}

