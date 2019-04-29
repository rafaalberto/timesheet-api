package br.com.api.timesheet.resource.employee;

import br.com.api.timesheet.entity.Company;
import br.com.api.timesheet.entity.Position;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
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

    public Optional<String> getName() {
        return Optional.ofNullable(name);
    }

    public static final class Builder {
        private Integer page;
        private Integer size;
        private String name;

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

        public EmployeeRequest build() {
            EmployeeRequest positionRequest = new EmployeeRequest();
            positionRequest.page = this.page;
            positionRequest.size = this.size;
            positionRequest.name = this.name;
            return positionRequest;
        }
    }
}
