package br.com.api.timesheet.enumeration;

import br.com.api.timesheet.dto.OfficeHours;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

import static br.com.api.timesheet.enumeration.PeriodEnum.ENTRESSAFRA;
import static br.com.api.timesheet.enumeration.PeriodEnum.SAFRA;
import static java.util.Arrays.stream;
import static java.util.stream.Collectors.toList;

@Getter
@RequiredArgsConstructor
public enum OfficeHoursEnum {

    HOUR_A("A", "06:00-10:00-11:00-14:00", SAFRA),
    HOUR_B("B", "14:00-18:00-19:00-22:00", SAFRA),
    HOUR_C("C", "22:00-01:00-02:00-06:00", SAFRA),
    HOUR_U("U", "07:00-12:00-13:00-17:00", ENTRESSAFRA);

    private final String code;
    private final String description;
    private final PeriodEnum period;

    public static List<OfficeHours> fetchByPeriod(PeriodEnum period) {
        List<OfficeHours> officeHours = new ArrayList<>();
        stream(values()).filter(hour -> hour.period.equals(period)).collect(toList()).forEach(officeHour -> {
            officeHours.add(new OfficeHours(officeHour.toString(), officeHour.getDescription()));
        });
        return officeHours;
    }
}
