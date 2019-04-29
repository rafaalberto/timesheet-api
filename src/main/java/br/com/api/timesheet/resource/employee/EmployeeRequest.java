package br.com.api.timesheet.resource.employee;

import java.util.Optional;

public class EmployeeRequest {

    private Integer page;
    private Integer size;

    private Long id;
    private String name;
    private String recordNumber;
    private Long positionId;
    private Long companyId;
    private String costCenter;
    private Double costHour;

    public Optional<Integer> getPage() {
        return Optional.ofNullable(page);
    }

    public Optional<Integer> getSize() {
        return Optional.ofNullable(size);
    }

    public Optional<Long> getId() {
        return Optional.ofNullable(id);
    }

    public Optional<String> getName() {
        return Optional.ofNullable(name);
    }

    public Optional<String> getRecordNumber() {
        return Optional.ofNullable(recordNumber);
    }

    public Optional<Long> getPositionId() {
        return Optional.ofNullable(positionId);
    }

    public Optional<Long> getCompanyId() {
        return Optional.ofNullable(companyId);
    }

    public Optional<String> getCostCenter() {
        return Optional.ofNullable(costCenter);
    }

    public Optional<Double> getCostHour() {
        return Optional.ofNullable(costHour);
    }

    public static final class Builder {
        private Integer page;
        private Integer size;
        private Long id;
        private String name;
        private String recordNumber;
        private Long positionId;
        private Long companyId;
        private String costCenter;
        private Double costHour;

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

        public Builder withId(Long id) {
            this.id = id;
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

        public Builder withPositionId(Long positionId) {
            this.positionId = positionId;
            return this;
        }

        public Builder withCompanyId(Long companyId) {
            this.companyId = companyId;
            return this;
        }

        public Builder withCostCenter(String costCenter) {
            this.costCenter = costCenter;
            return this;
        }

        public Builder withCostHour(Double costHour) {
            this.costHour = costHour;
            return this;
        }

        public EmployeeRequest build() {
            EmployeeRequest positionRequest = new EmployeeRequest();
            positionRequest.page = this.page;
            positionRequest.size = this.size;
            positionRequest.id = this.id;
            positionRequest.name = this.name;
            positionRequest.recordNumber = this.recordNumber;
            positionRequest.positionId = this.positionId;
            positionRequest.companyId = this.companyId;
            positionRequest.costCenter = this.costCenter;
            positionRequest.costHour = this.costHour;
            return positionRequest;
        }
    }
}
