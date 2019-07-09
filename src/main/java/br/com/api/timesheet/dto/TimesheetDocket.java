package br.com.api.timesheet.dto;

import lombok.Data;

import java.util.Locale;

import static br.com.api.timesheet.utils.DateUtils.TIME_FORMAT;
import static br.com.api.timesheet.utils.DateUtils.convertNanostoDecimalHours;
import static java.text.NumberFormat.getCurrencyInstance;
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
        return formatDuration(ofNanos(totalHours).toMillis(), TIME_FORMAT);
    }

    public String getTotalCost() {
        return getCurrencyInstance(new Locale("pt", "BR")).format(convertNanostoDecimalHours(totalHours) * costPerHour);
    }

}
