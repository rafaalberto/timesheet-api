package br.com.api.timesheet.resource.employee;

import br.com.api.timesheet.enumeration.StatusEnum;

import java.util.Optional;

public class EmployeeRequest {

    private Integer page;
    private Integer size;
    private String name;
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

    public Optional<StatusEnum> getStatus() { return Optional.ofNullable(status); }

    public static final class Builder {
        private Integer page;
        private Integer size;
        private String name;
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

        public Builder withStatus(StatusEnum status) {
            this.status = status;
            return this;
        }

        public EmployeeRequest build() {
            EmployeeRequest positionRequest = new EmployeeRequest();
            positionRequest.page = this.page;
            positionRequest.size = this.size;
            positionRequest.name = this.name;
            positionRequest.status = this.status;
            return positionRequest;
        }
    }
}