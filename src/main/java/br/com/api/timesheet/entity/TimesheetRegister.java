package br.com.api.timesheet.entity;

import br.com.api.timesheet.enumeration.TimesheetTypeEnum;
import br.com.api.timesheet.utils.Constants;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.io.Serializable;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;

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
    private TimesheetTypeEnum typeEnum;

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

    @PrePersist @PreUpdate
    public void calculateHours() {
        resetValues();

        long firstPeriod = Duration.between(timeIn, lunchStart).getSeconds();
        long secondPeriod = Duration.between(lunchEnd, timeOut).getSeconds();
        hoursWorked = LocalTime.ofSecondOfDay(firstPeriod + secondPeriod);

        long extraHoursDuration = Duration.between(hoursJourney, hoursWorked).getSeconds();
        extraHours = extraHoursDuration > Constants.ZERO ? LocalTime.ofSecondOfDay(extraHoursDuration) : LocalTime.ofSecondOfDay(Constants.ZERO);

        if(typeEnum.equals(TimesheetTypeEnum.DAY_OFF)){
            weeklyRest = hoursJourney;
            hoursJourney = LocalTime.ofSecondOfDay(Constants.ZERO);
        }else if(typeEnum.equals(TimesheetTypeEnum.HOLIDAY)){
            extraHours = hoursWorked;
        }

        //TODO(1) Holiday is a weekly rest?
    }

    private void resetValues() {
        hoursWorked = LocalTime.ofSecondOfDay(Constants.ZERO);
        extraHours = LocalTime.ofSecondOfDay(Constants.ZERO);
        weeklyRest = LocalTime.ofSecondOfDay(Constants.ZERO);
    }

}

