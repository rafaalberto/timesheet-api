package br.com.api.timesheet.resource.employee;

import br.com.api.timesheet.enumeration.StatusEnum;

import java.util.Optional;

public class EmployeeRequest {

    private Integer page;
    private Integer size;
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

    public static final class Builder {
        private Integer page;
        private Integer size;
        private String name;
        private String recordNumber;
        private Long companyId;
        private StatusEnum status;

        private Builder() {
        }

        public static Builder builder() {
            return new Builder();
        }

        public Builder withPage(Integer page) {
            this.page = page;
            return this;
        }

        public Builder withSize(Integer size) {
            this.size = size;
            return this;
        }

        public Builder withName(String name) {
            this.name = name;
            return this;
        }

        public Builder withRecordNumber(String recordNumber) {
            this.recordNumber = recordNumber;
            return this;
        }

        public Builder withCompanyId(Long companyId) {
            this.companyId = companyId;
            return this;
        }

        public Builder withStatus(StatusEnum status) {
            this.status = status;
            return this;
        }

        public EmployeeRequest build() {
            EmployeeRequest employeeRequest = new EmployeeRequest();
            employeeRequest.page = this.page;
            employeeRequest.size = this.size;
            employeeRequest.name = this.name;
            employeeRequest.recordNumber = this.recordNumber;
            employeeRequest.status = this.status;
            employeeRequest.companyId = this.companyId;
            return employeeRequest;
        }
    }
}