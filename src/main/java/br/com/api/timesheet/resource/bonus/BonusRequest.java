package br.com.api.timesheet.resource.bonus;

import lombok.Data;

import java.util.Optional;

@Data
public class BonusRequest {

    private Long id;
    private Long employeeId;
    private Integer monthReference;
    private Integer yearReference;
    private String description;
    private Double cost;

    public Optional<Long> getId() {
        return Optional.ofNullable(id);
    }
    public Optional<Long> getEmployeeId() { return Optional.ofNullable(employeeId); }

}
