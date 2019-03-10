package br.com.api.timesheet.enumeration;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum TimesheetTypeEnum {

    REGULAR("RG", "NORMAL"),
    HOLIDAY("HO", "FERIADO"),
    DAY_OFF("DO", "FOLGA");

    private final String code;
    private final String description;
}
