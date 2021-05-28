package br.com.api.timesheet.resource.employee;

import br.com.api.timesheet.enumeration.StatusEnum;
import lombok.Builder;
import lombok.Data;

import java.util.Optional;

@Builder
@Data
public class EmployeeRequest {

    private Integer page;
    private Integer size;
    private Long id;
    private String name;
    private String recordNumber;
    private Long companyId;
    private StatusEnum status;

    public Optional<Integer> getPage() {
        return Optional.ofNullable(page);
    }

    public Optional<Integer> getSize() {
        return Optional.ofNullable(size);
    }

    public Optional<String> getName() {
        return Optional.ofNullable(name);
    }

    public Optional<String> getRecordNumber() {
        return Optional.ofNullable(recordNumber);
    }

    public Optional<Long> getCompanyId() {
        return Optional.ofNullable(companyId);
    }

    public Optional<StatusEnum> getStatus() { return Optional.ofNullable(status); }

}