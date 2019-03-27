package br.com.api.timesheet.dto;

import br.com.api.timesheet.enumeration.TimesheetTypeEnum;
import br.com.api.timesheet.utils.DateUtils;
import lombok.AllArgsConstructor;
import lombok.Data;

import static java.time.Duration.ofNanos;
import static org.apache.commons.lang3.time.DurationFormatUtils.formatDuration;

@Data
@AllArgsConstructor
public class TimesheetReport {

    private TimesheetTypeEnum typeEnum;
    private long hoursWorked;
    private long hoursJourney;
    private long weeklyRest;
    private long extraHours;
    private long sumula90;
    private long nightShift;
    private long paidNightTime;

    public String getHoursWorked() {
        return formatDuration(ofNanos(hoursWorked).toMillis(), DateUtils.TIME_FORMAT);
    }

    public String getHoursJourney() {
        return formatDuration(ofNanos(hoursJourney).toMillis(), DateUtils.TIME_FORMAT);
    }

    public String getWeeklyRest() {
        return formatDuration(ofNanos(weeklyRest).toMillis(), DateUtils.TIME_FORMAT);
    }

    public String getExtraHours() {
        return formatDuration(ofNanos(extraHours).toMillis(), DateUtils.TIME_FORMAT);
    }

    public String getSumula90() {
        return formatDuration(ofNanos(sumula90).toMillis(), DateUtils.TIME_FORMAT);
    }

    public String getNightShift() {
        return formatDuration(ofNanos(nightShift).toMillis(), DateUtils.TIME_FORMAT);
    }

    public String getPaidNightTime() {
        return formatDuration(ofNanos(paidNightTime).toMillis(), DateUtils.TIME_FORMAT);
    }
}
