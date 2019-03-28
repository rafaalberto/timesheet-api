package br.com.api.timesheet.dto;

import br.com.api.timesheet.enumeration.ReportTypeEnum;
import br.com.api.timesheet.enumeration.TimesheetTypeEnum;
import br.com.api.timesheet.utils.DateUtils;
import lombok.AllArgsConstructor;
import lombok.Data;

import static java.time.Duration.ofNanos;
import static org.apache.commons.lang3.time.DurationFormatUtils.formatDuration;

@Data
@AllArgsConstructor
public class TimesheetDocket {

    private String type;
    private long hoursTotal;

    public String getHoursTotal() {
        return formatDuration(ofNanos(hoursTotal).toMillis(), DateUtils.TIME_FORMAT);
    }
}
