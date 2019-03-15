package br.com.api.timesheet.entity;

import br.com.api.timesheet.enumeration.TimesheetTypeEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static br.com.api.timesheet.enumeration.TimesheetTypeEnum.DAY_OFF;
import static br.com.api.timesheet.enumeration.TimesheetTypeEnum.HOLIDAY;
import static br.com.api.timesheet.utils.DateUtils.calculateNightShift;
import static br.com.api.timesheet.utils.DateUtils.isNightShift;
import static br.com.api.timesheet.utils.DateUtils.calculatePaidNightTime;
import static java.time.Duration.between;
import static java.time.LocalTime.ofSecondOfDay;

@Entity
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Table(name = TimesheetRegister.TABLE_NAME)
public class TimesheetRegister implements Serializable {

    static final String TABLE_NAME = "timesheet_register";
    private static final String SEQUENCE_NAME = "seq_timesheet_register";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = SEQUENCE_NAME)
    @SequenceGenerator(name = SEQUENCE_NAME, sequenceName = SEQUENCE_NAME, allocationSize = 1)
    private Long id;

    @Column(name = "type", nullable = false, length = 2)
    private TimesheetTypeEnum type;

    @Column(name = "time_in")
    private LocalDateTime timeIn;

    @Column(name = "lunch_start")
    private LocalDateTime lunchStart;

    @Column(name = "lunch_end")
    private LocalDateTime lunchEnd;

    @Column(name = "time_out")
    private LocalDateTime timeOut;

    @Column(name = "hours_worked")
    private LocalTime hoursWorked;

    @Column(name = "hours_journey")
    private LocalTime hoursJourney;

    @Column(name = "extra_hours")
    private LocalTime extraHours;

    @Column(name = "weekly_rest")
    private LocalTime weeklyRest;

    @Column(name = "sumula_90")
    private LocalTime sumula90;

    @Column(name = "night_shift")
    private LocalTime nightShift;

    @Column(name = "paid_night_time")
    private LocalTime paidNightTime;

    @PrePersist @PreUpdate
    public void calculateHours() {
        resetValues();

        long firstPeriod = between(timeIn, lunchStart).getSeconds();
        long secondPeriod = between(lunchEnd, timeOut).getSeconds();
        hoursWorked = ofSecondOfDay(firstPeriod + secondPeriod);

        long extraHoursDuration = between(hoursJourney, hoursWorked).getSeconds();
        extraHours = extraHoursDuration > BigDecimal.ZERO.intValue() ? ofSecondOfDay(extraHoursDuration) : ofSecondOfDay(BigDecimal.ZERO.intValue());

        if(type.equals(DAY_OFF)){
            weeklyRest = hoursJourney;
            hoursJourney = ofSecondOfDay(BigDecimal.ZERO.intValue());
        }else if(type.equals(HOLIDAY)){
            extraHours = hoursWorked;
        }

        firstPeriod = isNightShift(timeIn, lunchStart) ? calculateNightShift(timeIn, lunchStart) : BigDecimal.ZERO.longValue();
        secondPeriod = isNightShift(lunchEnd, timeOut) ? calculateNightShift(lunchEnd, timeOut) : BigDecimal.ZERO.longValue();
        nightShift = ofSecondOfDay(firstPeriod + secondPeriod);

        paidNightTime = nightShift.toSecondOfDay() > BigDecimal.ZERO.intValue() ? calculatePaidNightTime(nightShift) : ofSecondOfDay(BigDecimal.ZERO.intValue());

        //TODO(1) Holiday is a weekly rest?
    }

    private void resetValues() {
        hoursWorked = ofSecondOfDay(BigDecimal.ZERO.intValue());
        extraHours = ofSecondOfDay(BigDecimal.ZERO.intValue());
        weeklyRest = ofSecondOfDay(BigDecimal.ZERO.intValue());
        nightShift = ofSecondOfDay(BigDecimal.ZERO.intValue());
        paidNightTime = ofSecondOfDay(BigDecimal.ZERO.intValue());
    }

}

