package br.com.api.timesheet.enumeration;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum StatusEnum {

    ACTIVE("A", "Ativo"),
    INACTIVE("I", "Inativo");

    private final String code;
    private final String description;
}
