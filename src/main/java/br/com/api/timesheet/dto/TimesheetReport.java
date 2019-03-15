package br.com.api.timesheet.dto;

import br.com.api.timesheet.enumeration.TimesheetTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalTime;

@Data
@AllArgsConstructor
public class TimesheetReport {

    private final TimesheetTypeEnum typeEnum;
    private final LocalTime hoursWorked;
    private final LocalTime hoursJourney;
    private final LocalTime weeklyRest;
    private final LocalTime extraHours;
    private final LocalTime sumula90;
    private final LocalTime nightShift;
    private final LocalTime paidNightTime;
}
