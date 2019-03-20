package br.com.api.timesheet.dto;

import br.com.api.timesheet.enumeration.TimesheetTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalTime;

@Data
@AllArgsConstructor
public class TimesheetReport {

    private TimesheetTypeEnum typeEnum;
    private LocalTime hoursWorked;
    private LocalTime hoursJourney;
    private LocalTime weeklyRest;
    private LocalTime extraHours;
    private LocalTime sumula90;
    private LocalTime nightShift;
    private LocalTime paidNightTime;
}
