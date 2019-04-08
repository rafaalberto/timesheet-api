package br.com.api.timesheet.dto;

import br.com.api.timesheet.utils.DateUtils;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import static java.time.Duration.ofNanos;
import static org.apache.commons.lang3.time.DurationFormatUtils.formatDuration;

@Data
public class TimesheetDocket {

    private String typeCode;
    private String typeDescription;
    private long totalHours;
    private double costPerHour;
    private String totalCost;

    public TimesheetDocket(String typeCode, String typeDescription, long totalHours, double costPerHour) {
        this.typeCode = typeCode;
        this.typeDescription = typeDescription;
        this.totalHours = totalHours;
        this.costPerHour = costPerHour;
    }

    public String getTotalHoursFormatted() {
        return formatDuration(ofNanos(totalHours).toMillis(), DateUtils.TIME_FORMAT);
    }

    public String getTotalCost() {
        return "" + totalHours * costPerHour;
    }

}
