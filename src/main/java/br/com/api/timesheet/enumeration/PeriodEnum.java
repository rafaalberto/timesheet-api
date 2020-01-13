package br.com.api.timesheet.enumeration;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PeriodEnum {

    SAFRA("S", "Safra"),
    ENTRESSAFRA("E", "Entressafra");

    private final String code;
    private final String description;
}
