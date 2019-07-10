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
    private double totalCost;

    public TimesheetDocket(String typeCode, String typeDescription, long totalHours, double costPerHour) {
        this.typeCode = typeCode;
        this.typeDescription = typeDescription;
        this.totalHours = totalHours;
        this.costPerHour = costPerHour;
        this.totalCost = convertNanostoDecimalHours(totalHours) * costPerHour;
    }

    public TimesheetDocket(String typeCode, String typeDescription, double totalCost) {
        this.typeCode = typeCode;
        this.typeDescription = typeDescription;
        this.totalCost = totalCost;
    }

    public String getTotalHoursFormatted() {
        return formatDuration(ofNanos(totalHours).toMillis(), TIME_FORMAT);
    }

    public String getTotalCostFormatted() {
        return getCurrencyInstance(new Locale("pt", "BR")).format(getTotalCost());
    }

}
