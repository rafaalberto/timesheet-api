package br.com.api.timesheet.dto;

import br.com.api.timesheet.utils.DateUtils;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.text.NumberFormat;
import java.util.Locale;

import static br.com.api.timesheet.utils.DateUtils.*;
import static java.text.NumberFormat.*;
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
