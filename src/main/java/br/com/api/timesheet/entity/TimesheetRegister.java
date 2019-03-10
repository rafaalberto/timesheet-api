package br.com.api.timesheet.entity;

import br.com.api.timesheet.enumeration.TimesheetTypeEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigInteger;
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

    @PrePersist
    @PreUpdate
    public void calculateHoursWorked() {
        this.hoursWorked = LocalTime.ofSecondOfDay(BigInteger.ZERO.longValue());
        long firstPeriod = Duration.between(timeIn, lunchStart).getSeconds();
        long secondPeriod = Duration.between(lunchEnd, timeOut).getSeconds();
        this.hoursWorked = LocalTime.ofSecondOfDay(firstPeriod + secondPeriod);
    }

}

