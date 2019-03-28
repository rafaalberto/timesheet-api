package br.com.api.timesheet.enumeration;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ReportTypeEnum {

    REGULAR_HOURS("2", "Horas normais"),
    WEEKLY_REST("3", "D.S.R."),
    EXTRA_HOURS_PART("13", "Horas Extras (50%)"),
    EXTRA_HOURS_FULL("14", "Horas Extras (100%)"),
    SUMULA_90("18", "Sumula 90"),
    NIGHT_SHIFT("19", "Adicional Noturno (20%)"),
    PAID_NIGHT_SHIFT("20", "H.N.R. (50%)");

    private final String code;
    private final String description;
}
